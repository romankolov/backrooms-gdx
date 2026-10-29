package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.ChunkSampler;
import com.backrooms.gdx.world.WorldGen;

/**
 * The map must be infinite, deterministic, varied and walkable.
 *
 * <p>Variety is the point of the generator: a level that is only ever the same room gives itself
 * away after a minute. These tests pin the shape of the level at both ends - most of it is still the
 * flat, walled office it should be, but every kind of room appears, and the player can reach almost
 * all of it.
 */
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

    /**
     * Every height in the level is a floor or a ceiling the player can read off the generator, and
     * no ceiling ever comes down onto the floor. Holes are deep enough that you cannot climb back
     * out of one, which is what makes falling in a way of travelling rather than an annoyance.
     */
    @Test
    public void everyHeightIsAFloorOrACeiling() {
        WorldGen gen = new WorldGen(99);
        int holes = 0;
        int cells = 0;
        float lowest = 0f;
        float highest = 0f;
        for (int i = -200; i < 200; i += 3) {
            for (int j = -200; j < 200; j += 3) {
                float f = gen.floorY(i, j);
                float c = gen.ceilY(i, j);
                cells++;
                assertFalse("floor is NaN", Float.isNaN(f));
                assertTrue("floor above the ceiling: " + i + "," + j, c - f > 2.5f);
                lowest = Math.min(lowest, f);
                highest = Math.max(highest, c);
                if (gen.isHole(i, j)) {
                    holes++;
                    assertEquals("hole bottom", -WorldGen.HOLE_DEPTH, f, 0f);
                } else {
                    // Ordinary floor, or a terrace of a sunken room.
                    assertTrue("floor height " + f + " at " + i + "," + j,
                            f <= 0.001f && f >= -WorldGen.TERRACE_RINGS * WorldGen.TERRACE_STEP - 0.001f);
                }
            }
        }
        assertEquals("the deepest floor is the bottom of a hole", -WorldGen.HOLE_DEPTH, lowest, 0f);
        assertEquals("the tallest ceiling is a grand hall", WorldGen.GRAND_CEIL, highest, 0f);
        float ratio = holes / (float) cells;
        assertTrue("holes should exist: " + ratio, ratio > 0.005f);
        assertTrue("holes should stay rare: " + ratio, ratio < 0.12f);
    }

    /**
     * Falling is permanent, so a hole is never something you simply walk around: no hole may be
     * shallower than the player can climb, and none may be inside a corridor.
     */
    @Test
    public void holesDropFurtherThanThePlayerCanClimb() {
        WorldGen gen = new WorldGen(20241027);
        assertTrue("a hole must be deeper than a jump",
                WorldGen.HOLE_DEPTH > 2f);
        for (int i = -120; i < 120; i++) {
            for (int j = -120; j < 120; j++) {
                if (gen.isCorridor(i, j)) {
                    assertFalse("corridors must stay walkable: " + i + "," + j, gen.isHole(i, j));
                    assertEquals("corridors are level", 0f, gen.floorY(i, j), 0f);
                }
            }
        }
    }

    /**
     * Long halls are part of the point: a corridor should run for tens of metres without a break,
     * and it should also stop. Both, or the level is only ever a grid you can count.
     */
    @Test
    public void corridorsRunLongAndSometimesStop() {
        WorldGen gen = new WorldGen(4242);
        int longest = 0;
        int shortRuns = 0;
        for (int j = -120; j < 120; j += 7) {
            int run = 0;
            for (int i = -220; i < 220; i++) {
                if (gen.isCorridor(i, j)) {
                    run++;
                    longest = Math.max(longest, run);
                } else {
                    if (run > 0 && run <= 12) {
                        shortRuns++;
                    }
                    run = 0;
                }
            }
        }
        assertTrue("corridors should run for a long way somewhere, longest " + longest,
                longest >= 24);
        assertTrue("corridors should also stop somewhere, found " + shortRuns + " short runs",
                shortRuns > 0);
    }

    /** Most of the level is still the office: only then does the strange part feel strange. */
    @Test
    public void mostOfTheLevelIsStillPlainOffice() {
        WorldGen gen = new WorldGen(7);
        int[] counts = new int[WorldGen.KIND_COUNT];
        int rooms = 0;
        for (int i = -300; i < 300; i += 4) {
            for (int j = -300; j < 300; j += 4) {
                if (gen.isCorridor(i, j)) {
                    continue;
                }
                counts[gen.kindAt(i, j)]++;
                rooms++;
            }
        }
        float office = counts[WorldGen.KIND_OFFICE] / (float) rooms;
        assertTrue("the office should dominate, was " + office, office > 0.5f);
        assertTrue("but not everything, was " + office, office < 0.75f);
        for (int kind = 0; kind < WorldGen.KIND_COUNT; kind++) {
            assertTrue(WorldGen.kindName(kind) + " must appear somewhere, was "
                    + counts[kind] / (float) rooms, counts[kind] / (float) rooms > 0.02f);
        }
    }

    /** The terraces of a sunken room have to be steps the player can take, not ledges. */
    @Test
    public void terracesStepDownByLessThanThePlayersStride() {
        WorldGen gen = new WorldGen(31337);
        assertTrue("a terrace must be a step, not a wall", WorldGen.TERRACE_STEP < 0.55f);
        int terraces = 0;
        for (int i = -200; i < 200; i++) {
            for (int j = -200; j < 200; j++) {
                float f = gen.floorY(i, j);
                if (f >= -0.001f || gen.isHole(i, j)) {
                    continue;                      // flat floor, or the bottom of a pit
                }
                terraces++;
                float east = gen.floorY(i + 1, j);
                float south = gen.floorY(i, j + 1);
                assertTrue("steps between terraces: " + f + " to " + east,
                        Math.abs(east - f) <= WorldGen.TERRACE_STEP + 0.001f
                                || east <= -WorldGen.HOLE_DEPTH + 0.001f);
                assertTrue("steps between terraces: " + f + " to " + south,
                        Math.abs(south - f) <= WorldGen.TERRACE_STEP + 0.001f
                                || south <= -WorldGen.HOLE_DEPTH + 0.001f);
            }
        }
        assertTrue("sunken rooms should exist", terraces > 1000);
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
        for (int seed = 0; seed < 20; seed++) {
            WorldGen gen = new WorldGen(seed * 7919 + 42);
            float[] spawn = new float[3];
            gen.findSpawn(spawn);
            int i = (int) Math.floor(spawn[0]);
            int j = (int) Math.floor(spawn[2]);
            assertTrue("spawn cell must be free of walls", gen.isOpenSpace(i, j));
            assertTrue("spawn must not be over a hole", gen.isSolidGround(i, j));
            assertEquals(gen.floorY(i, j), spawn[1], 0.001f);
        }
    }

    /**
     * Rooms may be closed off - a room you can see and never enter is exactly the sort of thing this
     * level does - but they may not be the rule. Flood fill a neighbourhood and insist that almost
     * all of it can be walked to from the spawn point.
     */
    @Test
    public void almostEverythingIsReachableOnFoot() {
        WorldGen gen = new WorldGen(20241027);
        final int radius = 48;
        Set<Long> seen = new HashSet<>();
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        seen.add(key(0, 0));
        queue.add(new int[] {0, 0});
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int[] cx = {cell[0] + 1, cell[0] - 1, cell[0], cell[0]};
            int[] cz = {cell[1], cell[1], cell[1] + 1, cell[1] - 1};
            for (int k = 0; k < 4; k++) {
                int nx = cx[k];
                int nz = cz[k];
                if (nx <= -radius || nx >= radius || nz <= -radius || nz >= radius) {
                    continue;
                }
                if (gen.isHole(nx, nz) || seen.contains(key(nx, nz))) {
                    continue;
                }
                if (Math.abs(gen.floorY(nx, nz) - gen.floorY(cell[0], cell[1])) > 0.55f) {
                    continue;
                }
                boolean open = nx > cell[0] ? !gen.vWall(nx, cell[1])
                        : nx < cell[0] ? !gen.vWall(cell[0], cell[1])
                        : nz > cell[1] ? !gen.hWall(cell[0], nz) : !gen.hWall(cell[0], cell[1]);
                if (!open) {
                    continue;
                }
                seen.add(key(nx, nz));
                queue.add(new int[] {nx, nz});
            }
        }
        int walkable = 0;
        for (int i = -radius; i < radius; i++) {
            for (int j = -radius; j < radius; j++) {
                if (!gen.isHole(i, j)) {
                    walkable++;
                }
            }
        }
        float reachable = seen.size() / (float) walkable;
        assertTrue("most of the level must be walkable from the spawn, was " + reachable,
                reachable > 0.9f);
    }

    private static long key(int x, int z) {
        return ((long) x << 32) ^ (z & 0xffffffffL);
    }

    @Test
    public void chunkSamplingMatchesDirectQueries() {
        WorldGen gen = new WorldGen(2024);
        ChunkData data = ChunkSampler.sample(gen, -3, 5);
        for (int lj = 0; lj < ChunkData.SIZE; lj++) {
            for (int li = 0; li < ChunkData.SIZE; li++) {
                float expected = gen.floorY(data.cellX0 + li, data.cellZ0 + lj);
                assertEquals(expected, data.floorAt(li, lj), 0f);
                assertEquals(gen.ceilY(data.cellX0 + li, data.cellZ0 + lj),
                        data.ceilAt(li, lj), 0f);
            }
        }
        // Neighbouring chunks must agree about the cells and walls they share.
        ChunkData east = ChunkSampler.sample(gen, -2, 5);
        assertEquals(data.floorAt(ChunkData.SIZE - 1, 3), east.floorAt(1, 3), 0f);
        assertEquals(data.vWallAt(ChunkData.SIZE, 3), east.vWallAt(2, 3));
    }
}
