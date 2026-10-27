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
    public void ambientOcclusionDarkensTheFloorNearWalls() {
        WorldGen gen = new WorldGen(11);
        int chunkX = 0;
        int chunkZ = 0;
        int wallX = 0;
        int wallZ = 0;
        boolean found = false;
        int margin = 4;
        for (int cx = -6; cx <= 6 && !found; cx++) {
            for (int cz = -6; cz <= 6 && !found; cz++) {
                for (int li = margin; li <= WorldGen.CHUNK_CELLS - margin && !found; li++) {
                    for (int lj = margin; lj <= WorldGen.CHUNK_CELLS - margin && !found; lj++) {
                        int i = cx * WorldGen.CHUNK_CELLS + li;
                        int j = cz * WorldGen.CHUNK_CELLS + lj;
                        if (!gen.vWall(i, j)) {
                            continue;
                        }
                        float a = gen.floorY(i - 1, j);
                        float b = gen.floorY(i, j);
                        if (Math.abs(a - b) < 0.01f && gen.cellSlope(i - 1, j) < 0.01f
                                && gen.cellSlope(i, j) < 0.01f) {
                            chunkX = cx;
                            chunkZ = cz;
                            wallX = i;
                            wallZ = j;
                            found = true;
                        }
                    }
                }
            }
        }
        assertTrue("test needs a flat wall inside a chunk", found);

        ChunkData data = ChunkSampler.sample(gen, chunkX, chunkZ);
        int[] lightmap = new int[LightBaker.TEXELS * LightBaker.TEXELS];
        LightBaker.bake(gen, data, lightmap);

        float nearWall = meanBrightness(lightmap, data, wallX - 0.3f, wallZ + 0.5f, chunkX, chunkZ,
                1);
        float openFloor = meanBrightness(lightmap, data, wallX - 1.7f, wallZ + 0.5f, chunkX, chunkZ,
                1);
        assertTrue("corners must be occluded: near wall " + nearWall + " vs open floor "
                + openFloor, nearWall < openFloor * 0.85f);
    }

    private float meanBrightness(int[] lightmap, ChunkData data, float wx, float wz,
                                 int chunkX, int chunkZ, int window) {
        float x0 = chunkX * WorldGen.CHUNK_SIZE - LightBaker.PAD;
        float z0 = chunkZ * WorldGen.CHUNK_SIZE - LightBaker.PAD;
        int ti = (int) ((wx - x0) / LightBaker.TEXEL_SIZE);
        int tj = (int) ((wz - z0) / LightBaker.TEXEL_SIZE);
        int size = LightBaker.TEXELS;
        int sum = 0;
        int count = 0;
        for (int j = Math.max(0, tj - window); j <= Math.min(size - 1, tj + window); j++) {
            for (int i = Math.max(0, ti - window); i <= Math.min(size - 1, ti + window); i++) {
                int texel = lightmap[j * size + i];
                sum += ((texel >>> 24) & 0xff) + ((texel >>> 16) & 0xff) + ((texel >>> 8) & 0xff);
                count += 3;
            }
        }
        return count == 0 ? 0f : sum / (float) count;
    }
}
