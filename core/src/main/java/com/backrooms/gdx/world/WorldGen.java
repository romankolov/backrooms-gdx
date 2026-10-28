package com.backrooms.gdx.world;

import com.backrooms.gdx.util.Hash;

/**
 * The infinite backrooms: a pure, stateless description of the level.
 *
 * <p>Level 0 is a single flat storey: one floor level, one ceiling height, no stairs and no ledges,
 * cut up by thin walls that run along the integer grid lines. The only thing that ever interrupts
 * the floor is the occasional hole. Walls, holes and ceiling lights are all derived from a hash of
 * their integer coordinates, so any chunk can be produced in isolation and the world never ends in
 * any direction.
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
    /** Height of a room from floor to ceiling. The same everywhere: Level 0 is flat. */
    public static final float ROOM_HEIGHT = 3.0f;
    /** How far below the floor the bottom of a hole sits. */
    public static final float HOLE_DEPTH = 2.5f;
    /** Room grid period, in cells. */
    public static final int MACRO = 7;
    /** Light fixtures sit on this lattice (in cells), one per room. */
    public static final int LIGHT_SPACING = MACRO;
    /** Fixtures are placed on cells with this phase inside the room lattice. */
    public static final int LIGHT_PHASE = 3;

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

    /**
     * Floor level of a cell. Flat everywhere except inside a hole, where the pit bottom sits
     * {@link #HOLE_DEPTH} metres lower.
     */
    public float floorY(int cellX, int cellZ) {
        return isHole(cellX, cellZ) ? -HOLE_DEPTH : 0f;
    }

    /** Ceiling height of a cell. Flat everywhere, holes included - only the floor drops. */
    public float ceilY(int cellX, int cellZ) {
        return ROOM_HEIGHT;
    }

    /**
     * True where the floor drops away into a pit. Two octaves only, so the blobs come out compact
     * and round instead of as one-cell slivers: about 3% of the floor, in pits a few metres across.
     * Everything outside them is on the same level.
     */
    public boolean isHole(int cellX, int cellZ) {
        return Hash.fbm2(seed ^ 0x2545f491, cellX * 0.07f + 11f, cellZ * 0.07f - 7f) > 0.57f;
    }

    // ---------------------------------------------------------------------------------------------
    // walls
    // ---------------------------------------------------------------------------------------------

    /** Wall on the vertical line {@code x = cellX}, spanning {@code z in [cellZ, cellZ+1)}. */
    public boolean vWall(int cellX, int cellZ) {
        if (vMacroWall(cellX)) {
            return !vDoorway(cellX, cellZ) && !vOpenBorder(cellX, cellZ);
        }
        return vClutter(cellX, cellZ);
    }

    /** Wall on the horizontal line {@code z = cellZ}, spanning {@code x in [cellX, cellX+1)}. */
    public boolean hWall(int cellX, int cellZ) {
        if (hMacroWall(cellZ)) {
            return !hDoorway(cellX, cellZ) && !hOpenBorder(cellX, cellZ);
        }
        return hClutter(cellX, cellZ);
    }

    /** Some room borders are missing entirely, which opens up the big halls of Level 0. */
    private boolean vOpenBorder(int cellX, int cellZ) {
        int line = Hash.floorDiv(cellX, MACRO);
        return (Hash.hash2(seed ^ 0x3c6ef372, line, Hash.floorDiv(cellZ, MACRO)) & 255) < 34;
    }

    private boolean hOpenBorder(int cellX, int cellZ) {
        int line = Hash.floorDiv(cellZ, MACRO);
        return (Hash.hash2(seed ^ 0x3c6ef373, line, Hash.floorDiv(cellX, MACRO)) & 255) < 34;
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

    /**
     * One internal wall per room, in about half of the rooms, with a gap in it. This gives rooms an
     * interior without filling the level with noise.
     */
    private boolean divider(int cellX, int cellZ, boolean vertical) {
        int local = Hash.floorMod(vertical ? cellX : cellZ, MACRO);
        if (local == 0) {
            return false;
        }
        int other = Hash.floorMod(vertical ? cellZ : cellX, MACRO);
        if (other == 0) {
            return false;                       // keep the doorway zone of the room border clear
        }
        int rx = Hash.floorDiv(vertical ? cellX : cellZ, MACRO);
        int rz = Hash.floorDiv(vertical ? cellZ : cellX, MACRO);
        int h = Hash.hash2(seed ^ 0x7feb352d, rx, rz);
        if ((h & 255) >= 236) {
            return false;                       // this room is undivided
        }
        if ((((h >> 8) & 1) == 1) != vertical) {
            return false;                       // divided along the other axis
        }
        if (1 + ((h >> 9) % (MACRO - 2)) != local) {
            return false;
        }
        int gap = 1 + ((h >> 15) % (MACRO - 2));
        return !(other == gap || other == (gap + 1) % MACRO);
    }

    private boolean vClutter(int cellX, int cellZ) {
        return divider(cellX, cellZ, true);
    }

    private boolean hClutter(int cellX, int cellZ) {
        return divider(cellX, cellZ, false);
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

    /** True when the cell and its four neighbours are all on the floor level (no pit underfoot). */
    public boolean isSolidGround(int cellX, int cellZ) {
        return !isHole(cellX, cellZ) && !isHole(cellX + 1, cellZ) && !isHole(cellX - 1, cellZ)
                && !isHole(cellX, cellZ + 1) && !isHole(cellX, cellZ - 1);
    }

    // ---------------------------------------------------------------------------------------------
    // visibility and acoustics
    // ---------------------------------------------------------------------------------------------

    /**
     * True if a point sits inside a wall slab. Walls are thin and axis aligned, so a point test is
     * all the geometry a sight line or a sound path ever needs to check.
     */
    public boolean blocksSight(float x, float z) {
        int cx = Hash.floorInt(x);
        int cz = Hash.floorInt(z);
        float fx = x - cx;
        float fz = z - cz;
        float t = WALL_HALF_THICKNESS * 1.2f;
        if (fx <= t && vWall(cx, cz)) {
            return true;
        }
        if (fx >= 1f - t && vWall(cx + 1, cz)) {
            return true;
        }
        if (fz <= t && hWall(cx, cz)) {
            return true;
        }
        return fz >= 1f - t && hWall(cx, cz + 1);
    }

    /**
     * Straight-line visibility between two points, sampled along the line. Used by the entity (can it
     * see the player?) and by the audio (is that footstep around the corner or through the wall?).
     * Walls are 16 cm thick and the sample step is smaller than that, so nothing is stepped over.
     */
    public boolean hasLineOfSight(float x0, float z0, float x1, float z1) {
        float dx = x1 - x0;
        float dz = z1 - z0;
        float distance = (float) Math.sqrt(dx * dx + dz * dz);
        if (distance < 0.001f) {
            return true;
        }
        int steps = Math.max(2, Math.round(distance / 0.18f));
        for (int i = 1; i < steps; i++) {
            float t = i / (float) steps;
            if (blocksSight(x0 + dx * t, z0 + dz * t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Distance from a point to the first wall in a direction, or {@code maxDistance} if the ray runs
     * out of range first. The march is stepped rather than exact: for reverb an approximate distance
     * to the nearest surfaces is all that is wanted, and this stays allocation free.
     */
    public float wallDistance(float x, float z, float dirX, float dirZ, float maxDistance) {
        float step = 0.35f;
        for (float d = step; d <= maxDistance; d += step) {
            if (blocksSight(x + dirX * d, z + dirZ * d)) {
                return d;
            }
        }
        return maxDistance;
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
                    if (isOpenSpace(c[0], c[1]) && isSolidGround(c[0], c[1])) {
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

    /** Nearest cell with solid floor around it, searching outwards from a position. */
    public boolean findLanding(float x, float z, float[] out) {
        int ci = Hash.floorInt(x);
        int cj = Hash.floorInt(z);
        for (int ring = 0; ring < 12; ring++) {
            for (int k = -ring; k <= ring; k++) {
                int[][] candidates = {{ci + k, cj - ring}, {ci + k, cj + ring},
                        {ci - ring, cj + k}, {ci + ring, cj + k}};
                for (int[] c : candidates) {
                    if (isSolidGround(c[0], c[1])) {
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
