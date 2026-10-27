package com.backrooms.gdx.world;

import com.backrooms.gdx.util.Hash;

/**
 * The infinite backrooms: a pure, stateless description of the level.
 *
 * <p>The map is a height field (floors, stairs, ledges and holes) cut up by thin walls that run along
 * the integer grid lines. Every wall, height and ceiling light is derived from a hash of its integer
 * coordinates, so any chunk can be produced in isolation and the world never ends in any direction.
 */
public final class WorldGen {

    /** Size of one floor cell in metres. */
    public static final float CELL = 1f;
    /** Cells per chunk edge. A chunk is therefore 16x16 metres. */
    public static final int CHUNK_CELLS = 16;
    /** Chunk size in metres. */
    public static final float CHUNK_SIZE = CHUNK_CELLS * CELL;
    /** Thin wall half-thickness. Walls are boxes 16cm thick. */
    public static final float WALL_HALF_THICKNESS = 0.08f;
    /** Height of a room from floor to ceiling. */
    public static final float ROOM_HEIGHT = 2.9f;
    /** Room grid period, in cells. */
    public static final int MACRO = 6;
    /** Light fixtures sit on this lattice (in cells), one per room. */
    public static final int LIGHT_SPACING = MACRO;
    /** Fixtures are placed on cells with this phase inside the room lattice. */
    public static final int LIGHT_PHASE = 3;
    /** Height quantisation of the terrain; also the tread height of the stairs. */
    public static final float HEIGHT_STEP = 0.25f;
    /** Highest the player can step up without jumping. */
    public static final float STEP_HEIGHT = 0.62f;

    private final int seed;

    public WorldGen(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    // ---------------------------------------------------------------------------------------------
    // terrain
    // ---------------------------------------------------------------------------------------------

    /** Smooth, unquantised floor height at a world position (metres). */
    public float rawHeight(float x, float z) {
        float t = Hash.fbm2(seed ^ 0x1b873593, x * 0.030f, z * 0.030f);
        float h = 0f;
        if (t > 0.12f) {
            h = (t - 0.12f) * 2.6f;              // terraces and stair cases climbing up
        } else if (t < -0.15f) {
            h = (t + 0.15f) * 3.0f;              // shallow dips
        }
        float pit = Hash.fbm3(seed ^ 0x2545f491, x * 0.019f + 11f, z * 0.019f - 7f);
        if (pit > 0.40f) {
            h -= (pit - 0.40f) * 13f;            // the occasional hole in the floor
        }
        return Hash.clamp(h, -7.5f, 3.2f);
    }

    /** Quantised floor height of a cell; the visible stair treads. */
    public float floorY(int cellX, int cellZ) {
        return quantise(rawHeight(cellX + 0.5f, cellZ + 0.5f));
    }

    public float ceilY(int cellX, int cellZ) {
        return floorY(cellX, cellZ) + roomHeight(cellX, cellZ);
    }

    /** Rooms vary slightly in height, which reads as different ceiling tiles. */
    public float roomHeight(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x5bd1e995, Hash.floorDiv(cellX, 5), Hash.floorDiv(cellZ, 5));
        return ROOM_HEIGHT + ((h >>> 8) & 7) * 0.06f;
    }

    public static float quantise(float h) {
        return Math.round(h / HEIGHT_STEP) * HEIGHT_STEP;
    }

    // ---------------------------------------------------------------------------------------------
    // walls
    // ---------------------------------------------------------------------------------------------

    /** Wall on the vertical line {@code x = cellX}, spanning {@code z in [cellZ, cellZ+1)}. */
    public boolean vWall(int cellX, int cellZ) {
        if (vMacroWall(cellX)) {
            return !vDoorway(cellX, cellZ);
        }
        return vClutter(cellX, cellZ);
    }

    /** Wall on the horizontal line {@code z = cellZ}, spanning {@code x in [cellX, cellX+1)}. */
    public boolean hWall(int cellX, int cellZ) {
        if (hMacroWall(cellZ)) {
            return !hDoorway(cellX, cellZ);
        }
        return hClutter(cellX, cellZ);
    }

    private boolean vMacroWall(int cellX) {
        if (Hash.floorMod(cellX, MACRO) != 0) return false;
        int line = Hash.floorDiv(cellX, MACRO);
        int h = Hash.hash2(seed ^ 0x68e31da4, line, 0x51ed);
        return (h & 255) < 208;                  // most room borders are walls
    }

    private boolean hMacroWall(int cellZ) {
        if (Hash.floorMod(cellZ, MACRO) != 0) return false;
        int line = Hash.floorDiv(cellZ, MACRO);
        int h = Hash.hash2(seed ^ 0x68e31da5, line, 0x51ed);
        return (h & 255) < 208;
    }

    private boolean vDoorway(int cellX, int cellZ) {
        int line = Hash.floorDiv(cellX, MACRO);
        int block = Hash.floorDiv(cellZ, MACRO);
        int h = Hash.hash2(seed ^ 0x1b56c4e9, line, block);
        int door = Hash.floorMod(h, MACRO - 1);          // 0..4 (two cells wide)
        int lz = Hash.floorMod(cellZ, MACRO);
        return lz == door || lz == door + 1;
    }

    private boolean hDoorway(int cellX, int cellZ) {
        int line = Hash.floorDiv(cellZ, MACRO);
        int block = Hash.floorDiv(cellX, MACRO);
        int h = Hash.hash2(seed ^ 0x1b56c4ea, line, block);
        int door = Hash.floorMod(h, MACRO - 1);
        int lx = Hash.floorMod(cellX, MACRO);
        return lx == door || lx == door + 1;
    }

    /** Short random wall stubs inside a room: the source of the maze-like dead ends. */
    private boolean vClutter(int cellX, int cellZ) {
        if (!inRoomInterior(cellX, cellZ)) return false;
        for (int k = 0; k < 5; k++) {
            int z = cellZ - k;
            int h = Hash.hash2(seed ^ 0x7feb352d, cellX, z);
            if ((h & 0x3f) == 0) {
                int len = 2 + ((h >>> 8) & 3);
                if (k < len) return true;
            }
        }
        return false;
    }

    private boolean hClutter(int cellX, int cellZ) {
        if (!inRoomInterior(cellX, cellZ)) return false;
        for (int k = 0; k < 5; k++) {
            int x = cellX - k;
            int h = Hash.hash2(seed ^ 0x7feb352e, x, cellZ);
            if ((h & 0x3f) == 0) {
                int len = 2 + ((h >>> 8) & 3);
                if (k < len) return true;
            }
        }
        return false;
    }

    /** True when the cell is at least one cell away from every room border, i.e. doors stay clear. */
    private static boolean inRoomInterior(int cellX, int cellZ) {
        int lx = Hash.floorMod(cellX, MACRO);
        int lz = Hash.floorMod(cellZ, MACRO);
        return lx > 0 && lx < MACRO - 1 && lz > 0 && lz < MACRO - 1;
    }

    /** True if nothing blocks movement through this cell centre (used for spawning). */
    public boolean isOpenSpace(int cellX, int cellZ) {
        return !vWall(cellX, cellZ) && !vWall(cellX + 1, cellZ)
                && !hWall(cellX, cellZ) && !hWall(cellX, cellZ + 1);
    }

    /** Slope of the floor around a cell, used to keep lights on flat ground. */
    public float cellSlope(int cellX, int cellZ) {
        float h = floorY(cellX, cellZ);
        float d = 0f;
        d = Math.max(d, Math.abs(floorY(cellX + 1, cellZ) - h));
        d = Math.max(d, Math.abs(floorY(cellX - 1, cellZ) - h));
        d = Math.max(d, Math.abs(floorY(cellX, cellZ + 1) - h));
        d = Math.max(d, Math.abs(floorY(cellX, cellZ - 1) - h));
        return d;
    }

    // ---------------------------------------------------------------------------------------------
    // lights
    // ---------------------------------------------------------------------------------------------

    /** True when a fluorescent fixture is mounted on the ceiling of this cell. */
    public boolean fixtureAt(int cellX, int cellZ) {
        if (Hash.floorMod(cellX - LIGHT_PHASE, LIGHT_SPACING) != 0) return false;
        if (Hash.floorMod(cellZ - LIGHT_PHASE, LIGHT_SPACING) != 0) return false;
        int h = Hash.hash2(seed ^ 0x2f9c3d11, fixtureIndex(cellX), fixtureIndex(cellZ));
        return (h & 255) < 232;                   // a few sockets are empty
    }

    /** Index of a fixture along the light lattice. */
    public static int fixtureIndex(float worldCoordinate) {
        return Hash.floorDiv(Hash.floorInt(worldCoordinate) - LIGHT_PHASE, LIGHT_SPACING);
    }

    /** Cell coordinate of a fixture lattice index. */
    public static int fixtureCell(int index) {
        return index * LIGHT_SPACING + LIGHT_PHASE;
    }

    /** False for the burned-out tubes, which still have geometry but no light. */
    public boolean fixtureAlive(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x33d5c7a1, cellX, cellZ);
        return (h & 255) > 12;
    }

    /** Some tubes are dying and flicker; returns a per-fixture seed (0 = steady). */
    public int fixtureFlickerSeed(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x4b8e2f9d, cellX, cellZ);
        return ((h & 255) < 34) ? (h | 1) : 0;
    }

    /** Relative strength of the fixture (warm, slightly uneven brightness). */
    public float fixturePower(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x77c1a3b5, cellX, cellZ);
        return 0.85f + ((h >>> 8) & 63) * (0.3f / 63f);
    }

    public static float fixtureCenterY(WorldGen gen, int cellX, int cellZ) {
        return gen.ceilY(cellX, cellZ) - 0.06f;
    }

    // ---------------------------------------------------------------------------------------------
    // sampling helpers (bulk queries, used by the chunk generator / light baker)
    // ---------------------------------------------------------------------------------------------

    /** Fills the floor heights for {@code size x size} cells starting at (cellX0, cellZ0). */
    public void sampleFloors(int cellX0, int cellZ0, int size, float[] out) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                out[j * size + i] = floorY(cellX0 + i, cellZ0 + j);
            }
        }
    }

    /** Finds a safe spawn cell near the origin: open, roughly flat and not in a hole. */
    public void findSpawn(float[] out) {
        int best = 0;
        for (int ring = 0; ring < 24; ring++) {
            for (int k = -ring; k <= ring; k++) {
                int[][] candidates = {{k, -ring}, {k, ring}, {-ring, k}, {ring, k}};
                for (int[] c : candidates) {
                    if (isOpenSpace(c[0], c[1]) && cellSlope(c[0], c[1]) <= 0.3f
                            && Math.abs(floorY(c[0], c[1])) < 1.2f) {
                        out[0] = c[0] + 0.5f;
                        out[1] = floorY(c[0], c[1]);
                        out[2] = c[1] + 0.5f;
                        return;
                    }
                }
            }
            best = ring;
        }
        out[0] = 0.5f;
        out[1] = floorY(0, 0);
        out[2] = 0.5f;
    }

    /** Nearest cell whose floor is walkable, searching outwards from a position. */
    public boolean findLanding(float x, float z, float[] out) {
        int ci = Hash.floorInt(x);
        int cj = Hash.floorInt(z);
        for (int ring = 0; ring < 12; ring++) {
            for (int k = -ring; k <= ring; k++) {
                int[][] candidates = {{ci + k, cj - ring}, {ci + k, cj + ring},
                        {ci - ring, cj + k}, {ci + ring, cj + k}};
                for (int[] c : candidates) {
                    if (cellSlope(c[0], c[1]) <= 0.55f) {
                        out[0] = c[0] + 0.5f;
                        out[1] = floorY(c[0], c[1]);
                        out[2] = c[1] + 0.5f;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
