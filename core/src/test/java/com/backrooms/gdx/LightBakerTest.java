package com.backrooms.gdx;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.backrooms.gdx.light.LightBaker;
import com.backrooms.gdx.util.Hash;
import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.ChunkSampler;
import com.backrooms.gdx.world.WorldGen;

public class LightBakerTest {

    @Test
    public void wallsBlockLight() {
        WorldGen gen = new WorldGen(2024);
        // Find a wall with open space on both sides.
        int wx = 0;
        int wz = 0;
        boolean found = false;
        for (int i = -40; i < 40 && !found; i++) {
            for (int j = -40; j < 40 && !found; j++) {
                if (gen.vWall(i, j) && Math.abs(gen.floorY(i - 1, j) - gen.floorY(i, j)) < 0.3f) {
                    wx = i;
                    wz = j;
                    found = true;
                }
            }
        }
        assertTrue("test needs a wall", found);

        float y = gen.floorY(wx, wz) + 1.4f;
        // Straight through the wall: blocked.
        assertTrue(LightBaker.blocked(gen, wx - 0.6f, y, wz + 0.5f, wx + 0.6f, y, wz + 0.5f));
        // Along the room on the same side: open.
        assertFalse(LightBaker.blocked(gen, wx - 0.6f, y, wz + 0.5f, wx - 0.6f, y, wz + 0.2f));
    }

    @Test
    public void brightnessPeaksUnderAFixture() {
        WorldGen gen = new WorldGen(11);
        int fx = 0;
        int fz = 0;
        boolean found = false;
        for (int i = -80; i < 80 && !found; i += WorldGen.LIGHT_SPACING) {
            for (int j = -80; j < 80 && !found; j += WorldGen.LIGHT_SPACING) {
                if (!gen.fixtureAt(i, j) || !gen.fixtureAlive(i, j)) {
                    continue;
                }
                // Require some elbow room so the measurement is not confused by other tubes.
                boolean isolated = true;
                for (int di = -3; di <= 3 && isolated; di++) {
                    for (int dj = -3; dj <= 3; dj++) {
                        if (di == 0 && dj == 0) continue;
                        int oi = i + di * WorldGen.LIGHT_SPACING;
                        int oj = j + dj * WorldGen.LIGHT_SPACING;
                        if (Math.abs(di) + Math.abs(dj) < 3 && gen.fixtureAt(oi, oj)) {
                            isolated = false;
                            break;
                        }
                    }
                }
                if (isolated) {
                    fx = i;
                    fz = j;
                    found = true;
                }
            }
        }
        assertTrue("test needs an isolated fixture", found);

        int cx = Hash.floorDiv(fx, WorldGen.CHUNK_CELLS);
        int cz = Hash.floorDiv(fz, WorldGen.CHUNK_CELLS);
        ChunkData data = ChunkSampler.sample(gen, cx, cz);
        int[] lightmap = new int[LightBaker.TEXELS * LightBaker.TEXELS];
        LightBaker.bake(gen, data, lightmap);

        int near = sampleBrightness(lightmap, data, fx + 0.5f, fz + 0.5f, cx, cz);
        int far = sampleBrightness(lightmap, data, fx + 0.5f + 5f, fz + 0.5f, cx, cz);
        assertTrue("the floor under a tube must be the brightest part (" + near + " vs " + far + ")",
                near > far);
    }

    private int sampleBrightness(int[] lightmap, ChunkData data, float wx, float wz,
                                 int chunkX, int chunkZ) {
        float x0 = chunkX * WorldGen.CHUNK_SIZE - LightBaker.PAD;
        float z0 = chunkZ * WorldGen.CHUNK_SIZE - LightBaker.PAD;
        int ti = (int) ((wx - x0) / LightBaker.TEXEL_SIZE);
        int tj = (int) ((wz - z0) / LightBaker.TEXEL_SIZE);
        int size = LightBaker.TEXELS;
        int best = 0;
        for (int j = Math.max(0, tj - 2); j <= Math.min(size - 1, tj + 2); j++) {
            for (int i = Math.max(0, ti - 2); i <= Math.min(size - 1, ti + 2); i++) {
                best = Math.max(best, (lightmap[j * size + i] >>> 24) & 0xff);
            }
        }
        return best;
    }
}
