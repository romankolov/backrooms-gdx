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
    public void fixturesLightTheFloorAroundThem() {
        WorldGen gen = new WorldGen(11);
        // Pick a fixture that sits well inside its chunk so both the "under the tube" window
        // and the overall average can be measured on one lightmap.
        int fx = 0;
        int fz = 0;
        boolean found = false;
        for (int i = -80; i < 80 && !found; i += WorldGen.LIGHT_SPACING) {
            for (int j = -80; j < 80 && !found; j += WorldGen.LIGHT_SPACING) {
                int lx = Hash.floorMod(i, WorldGen.CHUNK_CELLS);
                int lz = Hash.floorMod(j, WorldGen.CHUNK_CELLS);
                if (lx < 4 || lx > 11 || lz < 4 || lz > 11) {
                    continue;
                }
                if (gen.fixtureAt(i, j) && gen.fixtureAlive(i, j) && gen.cellSlope(i, j) < 0.3f) {
                    fx = i;
                    fz = j;
                    found = true;
                }
            }
        }
        assertTrue("test needs a fixture in the middle of a chunk", found);

        int cx = Hash.floorDiv(fx, WorldGen.CHUNK_CELLS);
        int cz = Hash.floorDiv(fz, WorldGen.CHUNK_CELLS);
        ChunkData data = ChunkSampler.sample(gen, cx, cz);
        int[] lightmap = new int[LightBaker.TEXELS * LightBaker.TEXELS];
        LightBaker.bake(gen, data, lightmap);

        float near = meanBrightness(lightmap, data, fx + 0.5f, fz + 0.5f, cx, cz, 3);
        float all = meanBrightness(lightmap, data, fx + 0.5f, fz + 0.5f, cx, cz, 1000);
        assertTrue("the carpet under a tube must be brighter than the chunk average ("
                + near + " vs " + all + ")", near > all * 1.2f);
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
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (window < 100 && (Math.abs(i - ti) > window || Math.abs(j - tj) > window)) {
                    continue;
                }
                int r = (lightmap[j * size + i] >>> 24) & 0xff;
                int b = lightmap[j * size + i] & 0xff;
                sum += r + b;
                count += 2;
            }
        }
        return count == 0 ? 0f : sum / (float) count;
    }
}
