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

    /**
     * Over a whole chunk, texels that sit close to a wall must be darker than texels in the middle
     * of a room: that is the baked contact shadow which makes the rooms read as three dimensional.
     */
    @Test
    public void wallsDarkenTheFloorAroundThem() {
        WorldGen gen = new WorldGen(11);
        int chunkX = 2;
        int chunkZ = -1;
        ChunkData data = ChunkSampler.sample(gen, chunkX, chunkZ);
        int[] lightmap = new int[LightBaker.TEXELS * LightBaker.TEXELS];
        LightBaker.bake(gen, data, lightmap);

        long edgeSum = 0;
        int edgeCount = 0;
        long openSum = 0;
        int openCount = 0;
        int size = LightBaker.TEXELS;
        for (int tj = 0; tj < size; tj++) {
            float wz = chunkZ * WorldGen.CHUNK_SIZE - LightBaker.PAD
                    + (tj + 0.5f) * LightBaker.TEXEL_SIZE;
            for (int ti = 0; ti < size; ti++) {
                float wx = chunkX * WorldGen.CHUNK_SIZE - LightBaker.PAD
                        + (ti + 0.5f) * LightBaker.TEXEL_SIZE;
                int r = (lightmap[tj * size + ti] >>> 24) & 0xff;
                if (r == 0) {
                    continue;   // texel inside a wall
                }
                if (nearWall(gen, wx, wz)) {
                    edgeSum += r;
                    edgeCount++;
                } else {
                    openSum += r;
                    openCount++;
                }
            }
        }
        assertTrue("need both kinds of texel", edgeCount > 40 && openCount > 40);
        float edge = edgeSum / (float) edgeCount;
        float open = openSum / (float) openCount;
        assertTrue("floor next to walls must be darker (" + edge + " vs " + open + ")",
                edge < open * 0.92f);
    }

    private boolean nearWall(WorldGen gen, float wx, float wz) {
        int i = (int) Math.floor(wx);
        int j = (int) Math.floor(wz);
        float dLeft = Math.abs(wx - i);
        float dRight = Math.abs(wx - (i + 1));
        float dBack = Math.abs(wz - j);
        float dFront = Math.abs(wz - (j + 1));
        if (dLeft < 0.35f && gen.vWall(i, j)) return true;
        if (dRight < 0.35f && gen.vWall(i + 1, j)) return true;
        if (dBack < 0.35f && gen.hWall(i, j)) return true;
        if (dFront < 0.35f && gen.hWall(i, j + 1)) return true;
        return false;
    }
}
