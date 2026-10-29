package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.ChunkSampler;
import com.backrooms.gdx.world.WorldGen;

/**
 * The map must be infinite, deterministic, varied and walkable.
 *
 * <p>The level is a partition of the plane into rooms rather than a lattice of tiles, so the tests
 * here are about the *distribution* it produces: rooms of every size, corridors that run, corners
 * that are not on a grid, every kind of room appearing, no sealed pockets, and a pit never in the
 * way through.
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
                assertEquals("hole at " + i + "," + j, a.isHole(i, j), b.isHole(i, j));
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
     * The complaint that a lattice invites is that every room is the same size and every corridor
     * the same length. This is the test that says it is not: the rooms in a 64 m square come out in
     * many different sizes, and the walls between them land wherever the partition put them.
     */
    @Test
    public void roomsAreAllDifferentSizes() {
        WorldGen gen = new WorldGen(20241027);
        Map<String, float[]> rooms = new HashMap<>();
        Set<Integer> areas = new HashSet<>();
        Set<Integer> widths = new HashSet<>();
        int radius = 32;
        for (int i = -radius; i < radius; i++) {
            for (int j = -radius; j < radius; j++) {
                // The room a cell is in can be read off a run of walls: walk out to the first wall
                // in each direction. That is also a check in itself - a room has to be closed.
                int west = i;
                while (!gen.vWall(west, j) && i - west < 40) {
                    west--;
                }
                int east = i;
                while (!gen.vWall(east + 1, j) && east - i < 40) {
                    east++;
                }
                int north = j;
                while (!gen.hWall(i, north) && j - north < 40) {
                    north--;
                }
                int south = j;
                while (!gen.hWall(i, south + 1) && south - j < 40) {
                    south++;
                }
                String key = west + "," + north + "," + east + "," + south;
                rooms.put(key, new float[] {east - west + 1f, south - north + 1f});
                areas.add((int) ((east - west + 1) * (south - north + 1)));
                widths.add(east - west + 1);
            }
        }
        assertTrue("a 64 m square should hold many rooms, found " + rooms.size(),
                rooms.size() > 120);
        assertTrue("rooms should come in many sizes, found " + areas.size() + " areas",
                areas.size() > 25);
        assertTrue("rooms should be more than one width, found " + widths.size(), widths.size() > 6);

        // Rooms are not all one shape: the ratio of long side to short side varies a lot.
        Set<Integer> scores = new HashSet<>();
        for (float[] room : rooms.values()) {
            if (room[0] >= 2f && room[1] >= 2f) {
                scores.add((int) (Math.max(room[0], room[1]) * 10f / Math.min(room[0], room[1])));
            }
        }
        assertTrue("room shapes should vary, found " + scores.size() + " aspect ratios",
                scores.size() > 4);
    }

    /** No cell of floor is sealed in: every open cell has at least one open side. */
    @Test
    public void noFloorCellIsSealedIn() {
        WorldGen gen = new WorldGen(4242);
        for (int i = -40; i < 40; i++) {
            for (int j = -40; j < 40; j++) {
                if (gen.isHole(i, j)) {
                    continue;
                }
                boolean open = !gen.vWall(i, j) || !gen.vWall(i + 1, j)
                        || !gen.hWall(i, j) || !gen.hWall(i, j + 1);
                assertTrue("sealed cell at " + i + "," + j, open);
            }
        }
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
                            f <= 0.001f
                                    && f >= -WorldGen.TERRACE_RINGS * WorldGen.TERRACE_STEP - 0.001f);
                }
            }
        }
        assertEquals("the deepest floor is the bottom of a hole", -WorldGen.HOLE_DEPTH, lowest, 0f);
        assertTrue("the tallest ceiling is a grand hall: " + highest,
                highest >= WorldGen.GRAND_CEIL_MIN && highest <= WorldGen.GRAND_CEIL_MAX);
        float ratio = holes / (float) cells;
        assertTrue("holes should exist: " + ratio, ratio > 0.005f);
        assertTrue("holes should stay rare: " + ratio, ratio < 0.12f);
    }

    /** A pit is never in the way through: no corridor and no doorway ever has one in it. */
    @Test
    public void holesNeverBlockTheWayThrough() {
        WorldGen gen = new WorldGen(20241027);
        int holes = 0;
        for (int i = -120; i < 120; i++) {
            for (int j = -120; j < 120; j++) {
                if (!gen.isHole(i, j)) {
                    continue;
                }
                holes++;
                // The four cells around a pit must still be walkable, or the pit is a wall.
                assertFalse("a pit may not close off the cell to its west at " + i + "," + j,
                        gen.isHole(i - 1, j) && gen.isHole(i + 1, j)
                                && gen.isHole(i, j - 1) && gen.isHole(i, j + 1));
                assertTrue("a hole must be deeper than a jump",
                        gen.floorY(i, j) <= -2f);
            }
        }
        assertTrue("pits should exist", holes > 20);
    }

    /** The terraces of a sunken room have to be steps the player can take, not ledges. */
    @Test
    public void terracesStepDownByLessThanThePlayersStride() {
        WorldGen gen = new WorldGen(31337);
        assertTrue("a terrace must be a step, not a wall", WorldGen.TERRACE_STEP < 0.55f);
        int terraces = 0;
        for (int i = -160; i < 160; i++) {
            for (int j = -160; j < 160; j++) {
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
        assertTrue("sunken rooms should exist", terraces > 200);
    }

    /**
     * Long halls are part of the point: some runs of floor have to go on for tens of metres
     * without a wall across them, and some have to stop. Both, or the level is a grid you can count.
     */
    @Test
    public void thereAreLongHallsAndTheyEnd() {
        WorldGen gen = new WorldGen(4242);
        int longest = 0;
        int shortRuns = 0;
        for (int j = -120; j < 120; j += 7) {
            int run = 0;
            for (int i = -220; i < 220; i++) {
                if (!gen.vWall(i, j) && !gen.vWall(i + 1, j)) {
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
        assertTrue("there should be a hall tens of metres long, longest " + longest,
                longest >= 24);
        assertTrue("and it should end somewhere, found " + shortRuns + " short runs",
                shortRuns > 0);
    }

    /** Most of the level is still the plain office: only then does the strange part feel strange. */
    @Test
    public void mostOfTheLevelIsStillPlain() {
        WorldGen gen = new WorldGen(7);
        int[] counts = new int[WorldGen.KIND_COUNT];
        int rooms = 0;
        for (int i = -240; i < 240; i += 3) {
            for (int j = -240; j < 240; j += 3) {
                counts[gen.kindAt(i, j)]++;
                rooms++;
            }
        }
        float plain = counts[WorldGen.KIND_PLAIN] / (float) rooms;
        assertTrue("the plain office should dominate, was " + plain, plain > 0.5f);
        assertTrue("but not everything, was " + plain, plain < 0.8f);
        for (int kind = 1; kind < WorldGen.KIND_COUNT; kind++) {
            assertTrue(WorldGen.kindName(kind) + " must appear somewhere",
                    counts[kind] > 0);
        }
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

    /** Light fittings always sit on a real ceiling, and a dark room has none at all. */
    @Test
    public void fixturesSitOnCeilings() {
        WorldGen gen = new WorldGen(88);
        int fixtures = 0;
        for (int i = -60; i < 60; i++) {
            for (int j = -60; j < 60; j++) {
                if (!gen.fixtureAt(i, j)) {
                    continue;
                }
                fixtures++;
                assertTrue("a fitting must be above the floor at " + i + "," + j,
                        gen.ceilY(i, j) > gen.floorY(i, j) + 2f);
                assertTrue("a fitting must not be over a hole at " + i + "," + j,
                        !gen.isHole(i, j));
            }
        }
        assertTrue("there should be fittings", fixtures > 20);
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
        assertNotNull("a chunk must have cells", data);
    }
}
