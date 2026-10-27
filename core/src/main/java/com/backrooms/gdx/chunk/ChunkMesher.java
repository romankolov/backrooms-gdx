package com.backrooms.gdx.chunk;

import com.backrooms.gdx.light.LightBaker;
import com.backrooms.gdx.mesh.MeshBuilder;
import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.WorldGen;

/**
 * Turns one chunk of cell data into a triangle mesh plus a baked lightmap.
 *
 * <p>Everything that cannot be seen is thrown away: faces between two wall segments, risers covered
 * by a wall, geometry outside the chunk. The result is a single draw call per chunk.
 */
public final class ChunkMesher {

    private static final float EPS = 0.0005f;

    private ChunkMesher() {
    }

    public static ChunkBuild build(WorldGen gen, ChunkData data) {
        float worldX = data.chunkX * WorldGen.CHUNK_SIZE;
        float worldZ = data.chunkZ * WorldGen.CHUNK_SIZE;
        MeshBuilder mb = new MeshBuilder(worldX - LightBaker.PAD, worldZ - LightBaker.PAD,
                1f / LightBaker.SPAN);

        emitFloors(mb, data);
        emitWalls(mb, data);
        emitFixtures(mb, data);

        int[] lightmap = new int[LightBaker.TEXELS * LightBaker.TEXELS];
        LightBaker.bake(gen, data, lightmap);

        float[] vertices = new float[mb.floats()];
        System.arraycopy(mb.vertexArray(), 0, vertices, 0, mb.floats());
        short[] indices = new short[mb.indexCount()];
        System.arraycopy(mb.indexArray(), 0, indices, 0, mb.indexCount());

        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        for (int i = 1; i < vertices.length; i += MeshBuilder.STRIDE) {
            float y = vertices[i];
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }
        if (minY > maxY) {
            minY = 0f;
            maxY = 0f;
        }
        float pad = WorldGen.WALL_HALF_THICKNESS + 0.05f;

        return new ChunkBuild(data.chunkX, data.chunkZ, vertices, indices, lightmap,
                worldX - pad, minY - 0.05f, worldZ - pad,
                worldX + WorldGen.CHUNK_SIZE + pad, maxY + 0.05f,
                worldZ + WorldGen.CHUNK_SIZE + pad);
    }

    // ---------------------------------------------------------------------------------------------
    // floors, ceilings, stairs and holes
    // ---------------------------------------------------------------------------------------------

    private static void emitFloors(MeshBuilder mb, ChunkData d) {
        int size = ChunkData.SIZE;
        int pad = ChunkData.PAD;
        for (int lj = pad; lj < size - pad; lj++) {
            for (int li = pad; li < size - pad; li++) {
                int idx = lj * size + li;
                float f = d.floor[idx];
                float c = d.ceil[idx];
                float x = d.cellX0 + li;
                float z = d.cellZ0 + lj;

                mb.setLightmapOffset(0f, 0f);
                mb.rect(x, f, z, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, MeshBuilder.MAT_FLOOR, 0f);
                mb.rect(x, c, z, 1f, 0f, 0f, 0f, 0f, 1f, 0f, -1f, 0f, MeshBuilder.MAT_CEILING, 0f);

                // Stair risers and the walls of holes, emitted by the higher cell only.
                float nf;
                float nc;

                nf = d.floor[lj * size + li + 1];
                if (nf < f - EPS && !d.vWallAt(li + 1, lj)) {
                    mb.rect(x + 1f, nf, z, 0f, 0f, 1f, 0f, f - nf, 0f, 1f, 0f, 0f,
                            MeshBuilder.MAT_FLOOR, 0f);
                }
                nf = d.floor[lj * size + li - 1];
                if (nf < f - EPS && !d.vWallAt(li, lj)) {
                    mb.rect(x, nf, z, 0f, 0f, 1f, 0f, f - nf, 0f, -1f, 0f, 0f,
                            MeshBuilder.MAT_FLOOR, 0f);
                }
                nf = d.floor[(lj + 1) * size + li];
                if (nf < f - EPS && !d.hWallAt(li, lj + 1)) {
                    mb.rect(x, nf, z + 1f, 1f, 0f, 0f, 0f, f - nf, 0f, 0f, 0f, 1f,
                            MeshBuilder.MAT_FLOOR, 0f);
                }
                nf = d.floor[(lj - 1) * size + li];
                if (nf < f - EPS && !d.hWallAt(li, lj)) {
                    mb.rect(x, nf, z, 1f, 0f, 0f, 0f, f - nf, 0f, 0f, 0f, -1f,
                            MeshBuilder.MAT_FLOOR, 0f);
                }

                // Ceiling steps, also owned by the higher cell.
                // Ceiling steps are emitted even where a wall stands between the two cells:
                // the wall stops at the highest ceiling, so the step below it must be filled.
                nc = d.ceil[lj * size + li + 1];
                if (nc < c - EPS) {
                    mb.rect(x + 1f, nc, z, 0f, 0f, 1f, 0f, c - nc, 0f, 1f, 0f, 0f,
                            MeshBuilder.MAT_CEILING, 0f);
                }
                nc = d.ceil[lj * size + li - 1];
                if (nc < c - EPS) {
                    mb.rect(x, nc, z, 0f, 0f, 1f, 0f, c - nc, 0f, -1f, 0f, 0f,
                            MeshBuilder.MAT_CEILING, 0f);
                }
                nc = d.ceil[(lj + 1) * size + li];
                if (nc < c - EPS) {
                    mb.rect(x, nc, z + 1f, 1f, 0f, 0f, 0f, c - nc, 0f, 0f, 0f, 1f,
                            MeshBuilder.MAT_CEILING, 0f);
                }
                nc = d.ceil[(lj - 1) * size + li];
                if (nc < c - EPS) {
                    mb.rect(x, nc, z, 1f, 0f, 0f, 0f, c - nc, 0f, 0f, 0f, -1f,
                            MeshBuilder.MAT_CEILING, 0f);
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // walls
    // ---------------------------------------------------------------------------------------------

    private static void emitWalls(MeshBuilder mb, ChunkData d) {
        int size = ChunkData.SIZE;
        int pad = ChunkData.PAD;
        float th = WorldGen.WALL_HALF_THICKNESS;
        int first = pad + 1;
        int last = size - pad;

        for (int lj = pad; lj < size - pad; lj++) {
            for (int li = first; li <= last; li++) {
                if (!d.vWallAt(li, lj)) {
                    continue;
                }
                float x = d.cellX0 + li;
                float z = d.cellZ0 + lj;
                float lo = Math.min(d.floor[lj * size + li - 1], d.floor[lj * size + li]);
                float hi = Math.max(d.ceil[lj * size + li - 1], d.ceil[lj * size + li]);
                float h = hi - lo;
                if (h <= 0f) {
                    continue;
                }
                // Faces on both sides of the thin wall.
                mb.setLightmapOffset(-0.3f, 0f);
                mb.rectGradient(x - th, lo, z, 0f, 0f, 1f, 0f, h, 0f, -1f, 0f, 0f,
                        MeshBuilder.MAT_WALL, d.floor[lj * size + li - 1], 1f / h);
                mb.setLightmapOffset(0.3f, 0f);
                mb.rectGradient(x + th, lo, z, 0f, 0f, 1f, 0f, h, 0f, 1f, 0f, 0f,
                        MeshBuilder.MAT_WALL, d.floor[lj * size + li], 1f / h);
                mb.setLightmapOffset(0f, 0f);
                // End caps where the wall run stops.
                if (!d.vWallAt(li, lj - 1)) {
                    mb.rect(x - th, lo, z, th * 2f, 0f, 0f, 0f, h, 0f, 0f, 0f, -1f,
                            MeshBuilder.MAT_WALL, 0.4f);
                }
                if (!d.vWallAt(li, lj + 1)) {
                    mb.rect(x - th, lo, z + 1f, th * 2f, 0f, 0f, 0f, h, 0f, 0f, 0f, 1f,
                            MeshBuilder.MAT_WALL, 0.4f);
                }
            }
        }

        for (int lj = first; lj <= last; lj++) {
            for (int li = pad; li < size - pad; li++) {
                if (!d.hWallAt(li, lj)) {
                    continue;
                }
                float x = d.cellX0 + li;
                float z = d.cellZ0 + lj;
                float lo = Math.min(d.floor[(lj - 1) * size + li], d.floor[lj * size + li]);
                float hi = Math.max(d.ceil[(lj - 1) * size + li], d.ceil[lj * size + li]);
                float h = hi - lo;
                if (h <= 0f) {
                    continue;
                }
                mb.setLightmapOffset(0f, -0.3f);
                mb.rectGradient(x, lo, z - th, 1f, 0f, 0f, 0f, h, 0f, 0f, 0f, -1f,
                        MeshBuilder.MAT_WALL, d.floor[(lj - 1) * size + li], 1f / h);
                mb.setLightmapOffset(0f, 0.3f);
                mb.rectGradient(x, lo, z + th, 1f, 0f, 0f, 0f, h, 0f, 0f, 0f, 1f,
                        MeshBuilder.MAT_WALL, d.floor[lj * size + li], 1f / h);
                mb.setLightmapOffset(0f, 0f);
                if (!d.hWallAt(li - 1, lj)) {
                    mb.rect(x, lo, z - th, 0f, 0f, th * 2f, 0f, h, 0f, -1f, 0f, 0f,
                            MeshBuilder.MAT_WALL, 0.4f);
                }
                if (!d.hWallAt(li + 1, lj)) {
                    mb.rect(x + 1f, lo, z - th, 0f, 0f, th * 2f, 0f, h, 0f, 1f, 0f, 0f,
                            MeshBuilder.MAT_WALL, 0.4f);
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ceiling fixtures
    // ---------------------------------------------------------------------------------------------

    private static void emitFixtures(MeshBuilder mb, ChunkData d) {
        int size = ChunkData.SIZE;
        int pad = ChunkData.PAD;
        for (int lj = pad; lj < size - pad; lj++) {
            for (int li = pad; li < size - pad; li++) {
                int idx = lj * size + li;
                if (!d.fixture[idx]) {
                    continue;
                }
                float x = d.cellX0 + li;
                float z = d.cellZ0 + lj;
                float y = d.ceil[idx] - 0.03f;
                // aux < -0.5 : steady tube, 1.0 : burned out, otherwise flicker phase in [0,1).
                float phase;
                if (!d.fixtureAliveAt(li, lj)) {
                    phase = 1f;
                } else if (d.fixtureSteady(li, lj)) {
                    phase = -1f;
                } else {
                    phase = d.fixturePhase(li, lj);
                }
                mb.setLightmapOffset(0f, 0f);
                mb.rect(x + 0.04f, y, z + 0.04f, 0.92f, 0f, 0f, 0f, 0f, 0.92f, 0f, -1f, 0f,
                        MeshBuilder.MAT_PANEL, phase);
            }
        }
    }
}
