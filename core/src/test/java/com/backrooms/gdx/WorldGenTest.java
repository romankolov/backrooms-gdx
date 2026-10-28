package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.ChunkSampler;
import com.backrooms.gdx.world.WorldGen;

/** The map must be infinite, deterministic and walkable. */
public class WorldGenTest {

    @Test
    public void generationIsDeterministic() {
        WorldGen a = new WorldGen(1234);
        WorldGen b = new WorldGen(1234);
        for (int i = -40; i < 40; i++) {
            for (int j = -40; j < 40; j++) {
                assertEquals("floor at " + i + "," + j, a.floorY(i, j), b.floorY(i, j), 0f);
                assertEquals("ceil at " + i + "," + j, a.ceilY(i, j), b.ceilY(i, j), 0f);
                assertEquals("vwall at " + i + "," + j, a.vWall(i, j), b.vWall(i, j));
                assertEquals("hwall at " + i + "," + j, a.hWall(i, j), b.hWall(i, j));
                assertEquals("fixture at " + i + "," + j, a.fixtureAt(i, j), b.fixtureAt(i, j));
            }
        }
    }

    @Test
    public void differentSeedsMakeDifferentWorlds() {
        WorldGen a = new WorldGen(1);
        WorldGen b = new WorldGen(2);
        int differences = 0;
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 60; j++) {
                if (a.vWall(i, j) != b.vWall(i, j) || a.floorY(i, j) != b.floorY(i, j)) {
                    differences++;
                }
            }
        }
        assertTrue("worlds should differ between seeds", differences > 20);
    }

    @Test
    public void theFloorAndTheCeilingAreFlat() {
        WorldGen gen = new WorldGen(99);
        int holes = 0;
        int cells = 0;
        for (int i = -200; i < 200; i += 3) {
            for (int j = -200; j < 200; j += 3) {
                float f = gen.floorY(i, j);
                float c = gen.ceilY(i, j);
                cells++;
                // Level 0 has exactly two floor levels: the floor and the bottom of a hole.
                assertEquals("ceiling must be flat: " + i + "," + j, WorldGen.ROOM_HEIGHT, c, 0f);
                if (gen.isHole(i, j)) {
                    holes++;
                    assertEquals("hole bottom", -WorldGen.HOLE_DEPTH, f, 0f);
                } else {
                    assertEquals("floor must be flat: " + i + "," + j, 0f, f, 0f);
                }
                assertFalse(Float.isNaN(f));
            }
        }
        float ratio = holes / (float) cells;
        assertTrue("holes should exist: " + ratio, ratio > 0.002f);
        assertTrue("holes should stay rare: " + ratio, ratio < 0.12f);
    }

    @Test
    public void wallsAreRareEnoughToWalkAround() {
        WorldGen gen = new WorldGen(7);
        int walls = 0;
        int cells = 0;
        for (int i = -60; i < 60; i++) {
            for (int j = -60; j < 60; j++) {
                cells++;
                if (gen.vWall(i, j)) walls++;
                if (gen.hWall(i, j)) walls++;
            }
        }
        float ratio = walls / (float) (cells * 2);
        assertTrue("too few walls: " + ratio, ratio > 0.05f);
        assertTrue("too many walls: " + ratio, ratio < 0.55f);
    }

    @Test
    public void spawnIsInsideOpenSpace() {
        WorldGen gen = new WorldGen(42);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);
        int i = (int) Math.floor(spawn[0]);
        int j = (int) Math.floor(spawn[2]);
        assertTrue("spawn cell must be free of walls", gen.isOpenSpace(i, j));
        assertEquals(gen.floorY(i, j), spawn[1], 0.001f);
    }

    @Test
    public void chunkSamplingMatchesDirectQueries() {
        WorldGen gen = new WorldGen(2024);
        ChunkData data = ChunkSampler.sample(gen, -3, 5);
        for (int lj = 0; lj < ChunkData.SIZE; lj++) {
            for (int li = 0; li < ChunkData.SIZE; li++) {
                float expected = gen.floorY(data.cellX0 + li, data.cellZ0 + lj);
                assertEquals(expected, data.floorAt(li, lj), 0f);
            }
        }
        // Neighbouring chunks must agree about the cells and walls they share.
        ChunkData east = ChunkSampler.sample(gen, -2, 5);
        assertEquals(data.floorAt(ChunkData.SIZE - 1, 3), east.floorAt(1, 3), 0f);
        assertEquals(data.vWallAt(ChunkData.SIZE, 3), east.vWallAt(2, 3));
    }
}
