package com.backrooms.gdx.light;

import com.backrooms.gdx.util.Hash;
import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.WorldGen;

/**
 * Bakes the lighting of one chunk into a small texture.
 *
 * <p>Baked lighting gives soft fluorescent pools, contact shadows in corners and bounce light from
 * the carpet without a single dynamic light on the GPU, which is what keeps this fast on phones.
 * Baked per chunk:
 * <ul>
 *   <li>RGB = irradiance on up-facing surfaces (floors, and ceilings scaled down)</li>
 *   <li>A   = irradiance on vertical surfaces (walls), stored as luminance</li>
 * </ul>
 * Two texels per metre, with one texel of padding on each side so neighbouring chunks line up
 * seamlessly under bilinear filtering.
 */
public final class LightBaker {

    public static final int TEXELS_PER_METER = 2;
    public static final float TEXEL_SIZE = 1f / TEXELS_PER_METER;
    /** Texels per chunk edge including one texel of padding all around. */
    public static final int TEXELS = WorldGen.CHUNK_CELLS * TEXELS_PER_METER + 2;
    /** World size covered by the lightmap, in metres. */
    public static final float SPAN = TEXELS * TEXEL_SIZE;
    /** Padding between the chunk edge and the lightmap border, in metres. */
    public static final float PAD = TEXEL_SIZE;
    /** Multiplier applied in the shader to recover HDR irradiance. */
    public static final float RANGE = 3f;

    private static final float LIGHT_RADIUS = 15f;
    private static final float LIGHT_INTENSITY = 2.7f;
    private static final float FIXTURE_Y_OFFSET = 0.06f;

    private LightBaker() {
    }

    /** Direction table for the short ambient-occlusion rays. */
    private static final float[] AO_DIRS = buildAoDirs();

    private static float[] buildAoDirs() {
        float[] d = new float[4 * 4];
        int n = 0;
        for (int k = 0; k < 4; k++) {
            double a = (k / 4.0) * Math.PI * 2.0 + 0.4;
            // horizontal probe (used for wall occlusion)
            d[n++] = (float) Math.cos(a);
            d[n++] = 0f;
            d[n++] = (float) Math.sin(a);
            d[n++] = 1f;
        }
        return d;
    }

    public static void bake(WorldGen gen, ChunkData data, int[] rgba) {
        float x0 = data.chunkX * WorldGen.CHUNK_SIZE - PAD;
        float z0 = data.chunkZ * WorldGen.CHUNK_SIZE - PAD;

        float[] up = new float[TEXELS * TEXELS * 3];
        float[] side = new float[TEXELS * TEXELS];

        for (int tj = 0; tj < TEXELS; tj++) {
            float wz = z0 + (tj + 0.5f) * TEXEL_SIZE;
            for (int ti = 0; ti < TEXELS; ti++) {
                float wx = x0 + (ti + 0.5f) * TEXEL_SIZE;
                bakeTexel(gen, wx, wz, up, side, tj * TEXELS + ti);
            }
        }

        // Cheap one-bounce: blur the direct result and add a fraction back. Light from the carpet
        // and walls filling the room is what makes fluorescent rooms look washed out.
        float[] bounceUp = new float[up.length];
        float[] bounceSide = new float[side.length];
        blur(up, bounceUp, TEXELS, 3, 3);
        blur(side, bounceSide, TEXELS, 1);
        for (int i = 0; i < TEXELS * TEXELS; i++) {
            float r = up[i * 3] + bounceUp[i * 3] * 0.45f;
            float g = up[i * 3 + 1] + bounceUp[i * 3 + 1] * 0.42f;
            float b = up[i * 3 + 2] + bounceUp[i * 3 + 2] * 0.34f;
            float s = side[i] + bounceSide[i] * 0.5f;
            // A little ordered-ish noise hides 8 bit banding across large flat floors.
            float d = (Hash.rand01(data.chunkX * 7919 + i, data.chunkZ, i) - 0.5f) * 0.006f;
            int ri = encode(r + d);
            int gi = encode(g + d);
            int bi = encode(b + d);
            int ai = encode(s + d);
            rgba[i] = (ri << 24) | (gi << 16) | (bi << 8) | ai;
        }
    }

    private static int encode(float v) {
        int i = (int) (v * (255f / RANGE) + 0.5f);
        return i < 0 ? 0 : (i > 255 ? 255 : i);
    }

    private static void bakeTexel(WorldGen gen, float wx, float wz, float[] up, float[] side, int index) {
        int ci = Hash.floorInt(wx);
        int cj = Hash.floorInt(wz);
        float fy = gen.floorY(ci, cj);
        float py = fy + 0.12f;

        float er = 0f, eg = 0f, eb = 0f;     // up facing
        float sr = 0f, sg = 0f, sb = 0f;     // vertical

        int li0 = Hash.floorDiv(Hash.floorInt(wx - LIGHT_RADIUS) - 1, WorldGen.LIGHT_SPACING);
        int li1 = Hash.floorDiv(Hash.floorInt(wx + LIGHT_RADIUS) + 1, WorldGen.LIGHT_SPACING);
        int lj0 = Hash.floorDiv(Hash.floorInt(wz - LIGHT_RADIUS) - 1, WorldGen.LIGHT_SPACING);
        int lj1 = Hash.floorDiv(Hash.floorInt(wz + LIGHT_RADIUS) + 1, WorldGen.LIGHT_SPACING);

        for (int lj = lj0; lj <= lj1; lj++) {
            int cz = lj * WorldGen.LIGHT_SPACING;
            for (int li = li0; li <= li1; li++) {
                int cx = li * WorldGen.LIGHT_SPACING;
                if (!gen.fixtureAt(cx, cz) || !gen.fixtureAlive(cx, cz)) {
                    continue;
                }
                float lx = cx + 0.5f;
                float lz = cz + 0.5f;
                float ly = gen.ceilY(cx, cz) - FIXTURE_Y_OFFSET;
                float dx = lx - wx;
                float dy = ly - py;
                float dz = lz - wz;
                float distSq = dx * dx + dy * dy + dz * dz;
                if (distSq > LIGHT_RADIUS * LIGHT_RADIUS) {
                    continue;
                }
                float dist = (float) Math.sqrt(distSq);
                if (dist < 0.0001f) {
                    continue;
                }
                if (blocked(gen, wx, py, wz, lx, ly, lz)) {
                    continue;
                }
                float power = gen.fixturePower(cx, cz);
                float atten = LIGHT_INTENSITY * power / (1f + distSq * 0.42f);
                // Fluorescent tubes are warm white with a slight green cast.
                float cr = atten * 1.05f;
                float cg = atten * 1.00f;
                float cb = atten * 0.84f;
                float ndlUp = dy / dist;
                if (ndlUp > 0f) {
                    er += cr * ndlUp;
                    eg += cg * ndlUp;
                    eb += cb * ndlUp;
                }
                float ndlSide = 0.5f * ((Math.abs(dx) + Math.abs(dz)) / dist);
                sr += cr * ndlSide;
                sg += cg * ndlSide;
                sb += cb * ndlSide;
            }
        }

        float aoUp = ambientOcclusion(gen, wx, py, wz, true);
        float aoSide = ambientOcclusion(gen, wx, py + 1.2f, wz, false);

        float upScale = 0.16f + 0.84f * aoUp;
        float sideScale = 0.12f + 0.88f * aoSide;
        float ambient = 0.030f * aoUp;

        up[index * 3] = er * upScale + ambient;
        up[index * 3 + 1] = eg * upScale + ambient * 1.02f;
        up[index * 3 + 2] = eb * upScale + ambient * 0.78f;

        side[index] = 0.2126f * (sr * sideScale + ambient * 0.9f)
                + 0.7152f * (sg * sideScale + ambient)
                + 0.0722f * (sb * sideScale + ambient * 0.9f);
    }

    /** Fraction of short probe rays that escape, in [0,1]. */
    private static float ambientOcclusion(WorldGen gen, float x, float y, float z, boolean upFacing) {
        int open = 0;
        int total = 0;
        for (int k = 0; k < 4; k++) {
            float dx = AO_DIRS[k * 4];
            float dz = AO_DIRS[k * 4 + 2];
            float dy = upFacing ? 0.75f : 0f;
            if (upFacing && (k & 1) == 1) {
                dy = 0.35f;
            }
            float len = 1.9f;
            float tx = x + dx * len;
            float ty = y + dy * len;
            float tz = z + dz * len;
            total++;
            if (!blocked(gen, x, y, z, tx, ty, tz)) {
                open++;
            }
        }
        return total == 0 ? 1f : (float) open / total;
    }

    /**
     * Grid DDA visibility test: walks the segment through the cell lattice and stops at the first
     * wall, floor or ceiling that it crosses.
     */
    public static boolean blocked(WorldGen gen, float x0, float y0, float z0,
                                  float x1, float y1, float z1) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        float dz = z1 - z0;

        int i = Hash.floorInt(x0);
        int j = Hash.floorInt(z0);
        int stepI = dx > 0 ? 1 : (dx < 0 ? -1 : 0);
        int stepJ = dz > 0 ? 1 : (dz < 0 ? -1 : 0);
        float tMaxX = stepI == 0 ? Float.MAX_VALUE
                : ((stepI > 0 ? i + 1 - x0 : x0 - i) / Math.abs(dx));
        float tMaxZ = stepJ == 0 ? Float.MAX_VALUE
                : ((stepJ > 0 ? j + 1 - z0 : z0 - j) / Math.abs(dz));
        float tDeltaX = stepI == 0 ? Float.MAX_VALUE : 1f / Math.abs(dx);
        float tDeltaZ = stepJ == 0 ? Float.MAX_VALUE : 1f / Math.abs(dz);

        float startFloor = gen.floorY(i, j);
        float startCeil = gen.ceilY(i, j);
        if (y0 < startFloor - 0.02f || y0 > startCeil) {
            return true;
        }

        for (int guard = 0; guard < 64; guard++) {
            if (tMaxX > 1f && tMaxZ > 1f) {
                return false;
            }
            if (tMaxX < tMaxZ) {
                float t = tMaxX;
                int line = stepI > 0 ? i + 1 : i;
                float zc = z0 + dz * t;
                int row = Hash.floorInt(zc);
                float yc = y0 + dy * t;
                if (gen.vWall(line, row) && crossesWall(gen, i, row, i + stepI, row, yc)) {
                    return true;
                }
                i += stepI;
                tMaxX += tDeltaX;
                if (y0 + dy * t < gen.floorY(i, j) - 0.02f || y0 + dy * t > gen.ceilY(i, j)) {
                    return true;
                }
            } else {
                float t = tMaxZ;
                int line = stepJ > 0 ? j + 1 : j;
                float xc = x0 + dx * t;
                int col = Hash.floorInt(xc);
                float yc = y0 + dy * t;
                if (gen.hWall(col, line) && crossesWall(gen, col, j, col, j + stepJ, yc)) {
                    return true;
                }
                j += stepJ;
                tMaxZ += tDeltaZ;
                if (y0 + dy * t < gen.floorY(i, j) - 0.02f || y0 + dy * t > gen.ceilY(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean crossesWall(WorldGen gen, int ax, int az, int bx, int bz, float y) {
        float lo = Math.min(gen.floorY(ax, az), gen.floorY(bx, bz));
        float hi = Math.max(gen.ceilY(ax, az), gen.ceilY(bx, bz));
        return y > lo && y < hi;
    }

    private static void blur(float[] src, float[] dst, int size, int radius) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float sum = 0f;
                int count = 0;
                for (int oy = -radius; oy <= radius; oy++) {
                    int sy = y + oy;
                    if (sy < 0 || sy >= size) continue;
                    for (int ox = -radius; ox <= radius; ox++) {
                        int sx = x + ox;
                        if (sx < 0 || sx >= size) continue;
                        sum += src[sy * size + sx];
                        count++;
                    }
                }
                dst[y * size + x] = sum / Math.max(1, count);
            }
        }
    }

    private static void blur(float[] src, float[] dst, int size, int radius, int channels) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float r = 0f, g = 0f, b = 0f;
                int count = 0;
                for (int oy = -radius; oy <= radius; oy++) {
                    int sy = y + oy;
                    if (sy < 0 || sy >= size) continue;
                    for (int ox = -radius; ox <= radius; ox++) {
                        int sx = x + ox;
                        if (sx < 0 || sx >= size) continue;
                        int idx = (sy * size + sx) * 3;
                        r += src[idx];
                        g += src[idx + 1];
                        b += src[idx + 2];
                        count++;
                    }
                }
                int idx = (y * size + x) * 3;
                dst[idx] = r / Math.max(1, count);
                dst[idx + 1] = g / Math.max(1, count);
                dst[idx + 2] = b / Math.max(1, count);
            }
        }
    }
}
