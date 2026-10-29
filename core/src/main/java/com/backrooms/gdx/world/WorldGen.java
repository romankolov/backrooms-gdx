package com.backrooms.gdx.world;

import com.backrooms.gdx.util.Hash;

/**
 * The infinite backrooms: a pure, stateless description of the level.
 *
 * <p>A chunk can be produced in isolation, on any thread, in any order, and always comes out the
 * same, because everything here is a function of integer grid coordinates and the world seed. That
 * is what makes the map infinite without a map.
 *
 * <p>The layout is built from two layers:
 *
 * <ol>
 *   <li><b>Lanes.</b> Every {@link #BLOCK} cells there is a lane two cells wide. Whether a lane is
 *       actually a corridor is decided per block column and block row, so a corridor usually runs
 *       for many tens of metres - the long halls - and sometimes stops dead at a wall. Two crossing
 *       corridors form a junction automatically.</li>
 *   <li><b>Blocks.</b> The cells a lane does not use belong to a block, and each block is one of
 *       seven kinds: offices, an open hall, a forest of pillars, a grid of holes, a terraced pit,
 *       a comb maze, or a grand hall with a ceiling seven metres up. Blocks of the same kind that
 *       are not separated by a lane merge into one bigger room.</li>
 * </ol>
 *
 * <p>That gives the two things the level needs at once: the flat, walled, endlessly familiar office
 * most of the time (over half of it), and something you were not expecting often enough that you
 * stop trusting the next doorway.
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
    /** Ceiling height of an ordinary room. */
    public static final float ROOM_HEIGHT = 3.0f;
    /** How far below the floor the bottom of a hole sits. Deep enough that you do not get out. */
    public static final float HOLE_DEPTH = 6.0f;

    /** Light fixtures sit on this lattice (in cells). */
    public static final int LIGHT_SPACING = 6;
    /** Fixtures are placed on cells with this phase inside the light lattice. */
    public static final int LIGHT_PHASE = 0;

    /** Cells per block edge, corridor lane included. */
    public static final int BLOCK = 12;
    /** Office rooms are this many cells across; the walls between them sit on its multiples. */
    public static final int ROOM_MODULE = 3;
    /** Rooms per block axis: four of them fill a block exactly. */
    public static final int ROOMS_PER_AXIS = BLOCK / ROOM_MODULE;
    /** Width of a corridor lane, in cells. */
    public static final int CORRIDOR_WIDTH = 2;

    // Block kinds.
    public static final int KIND_OFFICE = 0;
    public static final int KIND_HALL = 1;
    public static final int KIND_PILLARS = 2;
    public static final int KIND_PITS = 3;
    public static final int KIND_SUNKEN = 4;
    public static final int KIND_MAZE = 5;
    public static final int KIND_GRAND = 6;
    public static final int KIND_COUNT = 7;

    // Sides of a room, used by the office partitions.
    private static final int SIDE_EAST = 0;
    private static final int SIDE_WEST = 1;
    private static final int SIDE_SOUTH = 2;
    private static final int SIDE_NORTH = 3;
    private static final int SIDE_COUNT = 4;

    /** Ceiling of a corridor lane: a touch higher than the rooms it runs between. */
    public static final float CORRIDOR_CEIL = 3.15f;
    /** Ceiling of a maze block: cramped, so the corridors feel low. */
    public static final float MAZE_CEIL = 2.65f;
    /** Ceiling of a grand hall. */
    public static final float GRAND_CEIL = 4.6f;
    /** Ceiling of a sunken room: it keeps its height while the floor drops away. */
    public static final float SUNKEN_CEIL = 3.6f;
    /** Each terrace of a sunken room drops this much: under the player's step height, so it walks. */
    public static final float TERRACE_STEP = 0.5f;
    /** How many terraces a sunken room has before it reaches its plateau. */
    public static final int TERRACE_RINGS = 4;

    private final int seed;

    public WorldGen(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    // ---------------------------------------------------------------------------------------------
    // the two layers: lanes and blocks
    // ---------------------------------------------------------------------------------------------

    public static int blockX(int cellX) {
        return Hash.floorDiv(cellX, BLOCK);
    }

    public static int blockZ(int cellZ) {
        return Hash.floorDiv(cellZ, BLOCK);
    }

    public static int localX(int cellX) {
        return Hash.floorMod(cellX, BLOCK);
    }

    public static int localZ(int cellZ) {
        return Hash.floorMod(cellZ, BLOCK);
    }

    /**
     * True when a north-south corridor runs through this block.
     *
     * <p>Which block columns are worth running a corridor down at all is one hash, and whether the
     * corridor is still going when it reaches this particular block is another. A corridor therefore
     * runs for a hundred metres or so, ends in a wall, and picks up again a few blocks later - which
     * is what stops the level from being a grid you can count.
     */
    private boolean laneColumn(int bx, int bz) {
        if ((Hash.hash2(seed ^ 0x51ed270b, bx, 0x2f) & 255) >= 190) {
            return false;
        }
        return (Hash.hash2(seed ^ 0x6a09e667, bx, bz) & 255) < 232;
    }

    /** True when an east-west corridor runs through this block. */
    private boolean laneRow(int bz, int bx) {
        if ((Hash.hash2(seed ^ 0x2fb1a3c7, bz, 0x71) & 255) >= 182) {
            return false;
        }
        return (Hash.hash2(seed ^ 0xbb67ae85, bz, bx) & 255) < 232;
    }

    /** True if the cell is part of a corridor lane. Lanes are always open and always at y = 0. */
    public boolean isCorridor(int cellX, int cellZ) {
        if (localX(cellX) < CORRIDOR_WIDTH && laneColumn(blockX(cellX), blockZ(cellZ))) {
            return true;
        }
        return localZ(cellZ) < CORRIDOR_WIDTH && laneRow(blockZ(cellZ), blockX(cellX));
    }

    /**
     * Which kind of block a cell belongs to. Corridor cells still report their block, but nothing
     * outside the block geometry uses it for them.
     */
    public int kindAt(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x9e3779b9, blockX(cellX), blockZ(cellZ)) & 1023;
        if (h < 600) return KIND_OFFICE;        // 58%: the level everyone remembers
        if (h < 670) return KIND_HALL;          // 7%: one big empty room
        if (h < 740) return KIND_PILLARS;       // 7%: a forest of square pillars
        if (h < 810) return KIND_PITS;          // 7%: a grid of drop-through holes
        if (h < 872) return KIND_SUNKEN;        // 6%: terraces stepping down into the floor
        if (h < 944) return KIND_MAZE;          // 7%: a comb maze of one-metre passages
        return KIND_GRAND;                      // 8%: a hall with a four-and-a-half metre ceiling
    }

    public static String kindName(int kind) {
        switch (kind) {
            case KIND_OFFICE: return "office";
            case KIND_HALL: return "hall";
            case KIND_PILLARS: return "pillars";
            case KIND_PITS: return "pits";
            case KIND_SUNKEN: return "sunken";
            case KIND_MAZE: return "maze";
            case KIND_GRAND: return "grand";
            default: return "?";
        }
    }

    // ---------------------------------------------------------------------------------------------
    // terrain
    // ---------------------------------------------------------------------------------------------

    /** Floor level of a cell. */
    public float floorY(int cellX, int cellZ) {
        if (isHole(cellX, cellZ)) {
            return -HOLE_DEPTH;
        }
        if (isCorridor(cellX, cellZ)) {
            return 0f;
        }
        if (kindAt(cellX, cellZ) == KIND_SUNKEN) {
            return sunkenFloor(localX(cellX), localZ(cellZ));
        }
        return 0f;
    }

    /** Ceiling height of a cell: absolute, so a sunken room keeps its full height. */
    public float ceilY(int cellX, int cellZ) {
        if (isCorridor(cellX, cellZ)) {
            return CORRIDOR_CEIL;
        }
        switch (kindAt(cellX, cellZ)) {
            case KIND_GRAND:
                return GRAND_CEIL;
            case KIND_MAZE:
                return MAZE_CEIL;
            case KIND_SUNKEN:
                return SUNKEN_CEIL;
            default:
                return ROOM_HEIGHT;
        }
    }

    /**
     * True where the floor drops away and there is no way back up: the scattered pits, and the whole
     * grid of them in a {@link #KIND_PITS} block. Falling in is how the level moves you somewhere
     * else, so a hole is never a place you can stand.
     */
    public boolean isHole(int cellX, int cellZ) {
        if (isCorridor(cellX, cellZ)) {
            return false;
        }
        switch (kindAt(cellX, cellZ)) {
            case KIND_PITS:
                return gridPit(localX(cellX), localZ(cellZ));
            case KIND_OFFICE:
            case KIND_HALL:
            case KIND_GRAND:
                // Two octaves only, so the blobs come out compact and round instead of as
                // one-cell slivers: about 3% of these blocks, in pits a few metres across.
                return Hash.fbm2(seed ^ 0x2545f491, cellX * 0.07f + 11f, cellZ * 0.07f - 7f) > 0.57f;
            default:
                return false;
        }
    }

    /** Two-by-two holes on a three-cell lattice: a grid of holes joined by one-cell walkways. */
    private static boolean gridPit(int lx, int lz) {
        return Hash.floorMod(lx, 3) != 0 && Hash.floorMod(lz, 3) != 0;
    }

    /**
     * A room whose floor steps down towards the middle in half-metre terraces. Every step is
     * shorter than the player's stride up, so the whole room can be walked into and out of, and the
     * ceiling stays where it was: the room gets taller the further in you go.
     */
    private static float sunkenFloor(int lx, int lz) {
        int d = Math.min(Math.min(lx, lz), Math.min(BLOCK - 1 - lx, BLOCK - 1 - lz));
        return -Math.min(TERRACE_RINGS, d) * TERRACE_STEP;
    }

    /** True where the cell is the hollow inside of a square pillar. */
    private boolean pillarCell(int cellX, int cellZ) {
        if (kindAt(cellX, cellZ) != KIND_PILLARS) {
            return false;
        }
        return Hash.floorMod(localX(cellX), 3) == 1 && Hash.floorMod(localZ(cellZ), 3) == 1;
    }

    // ---------------------------------------------------------------------------------------------
    // walls
    // ---------------------------------------------------------------------------------------------

    /** Wall on the vertical line {@code x = cellX}, spanning {@code z in [cellZ, cellZ+1)}. */
    public boolean vWall(int cellX, int cellZ) {
        return wall(true, cellX, cellZ);
    }

    /** Wall on the horizontal line {@code z = cellZ}, spanning {@code x in [cellX, cellX+1)}. */
    public boolean hWall(int cellX, int cellZ) {
        return wall(false, cellX, cellZ);
    }

    /**
     * Every wall in the level is decided here, from the two cells on either side of it. The rule is
     * a function of those cells only, which is what keeps neighbouring chunks agreeing about the
     * wall between them.
     */
    private boolean wall(boolean vertical, int line, int row) {
        // (line, row) is the wall's own coordinate: x for a vertical wall, z for a horizontal one,
        // with the other axis along it. The two cells it separates are therefore (line-1, row) and
        // (line, row) in both cases.
        final int ax;
        final int az;
        final int bx;
        final int bz;
        if (vertical) {
            ax = line - 1;
            az = row;
            bx = line;
            bz = row;
        } else {
            ax = line;
            az = row - 1;
            bx = line;
            bz = row;
        }

        boolean corridorA = isCorridor(ax, az);
        boolean corridorB = isCorridor(bx, bz);
        if (corridorA && corridorB) {
            return false;                          // inside a corridor, or where two of them cross
        }
        if (corridorA || corridorB) {
            // A corridor runs alongside a room: the room's own wall, with a doorway into it.
            int roomX = corridorA ? bx : ax;
            int roomZ = corridorA ? bz : az;
            return !doorIntoBlock(vertical, line, row, roomX, roomZ);
        }

        boolean sameBlock = blockX(ax) == blockX(bx) && blockZ(az) == blockZ(bz);
        if (sameBlock) {
            return interiorWall(vertical, line, row, kindAt(ax, az), ax, az, bx, bz);
        }
        return !doorBetweenBlocks(vertical, line, row, bx, bz);
    }

    /**
     * Is there a doorway through this corridor wall into the block on the far side? The position is
     * hashed from the block and which of its four sides this is, so both cells of the two-cell-wide
     * door agree, and every other wall on that side is solid.
     */
    private boolean doorIntoBlock(boolean vertical, int line, int row, int roomX, int roomZ) {
        int bx = blockX(roomX);
        int bz = blockZ(roomZ);
        int side;
        int along;
        if (vertical) {
            side = roomX >= line ? 0 : 1;          // corridor to the block's -x or +x side
            along = localZ(roomZ);
        } else {
            side = roomZ >= row ? 2 : 3;
            along = localX(roomX);
        }
        if (!doorAt(bx, bz, side, along)) {
            return false;
        }
        // A doorway that opens onto a hole or into a pillar is not a doorway.
        return !isHole(roomX, roomZ) && !pillarCell(roomX, roomZ);
    }

    private boolean doorBetweenBlocks(boolean vertical, int line, int row, int bx, int bz) {
        int side;
        int along;
        if (vertical) {
            side = 0;                              // the block to the +x side of the boundary
            along = localZ(bz);
        } else {
            side = 2;
            along = localX(bx);
        }
        if (!doorAt(blockX(bx), blockZ(bz), side, along)) {
            return false;
        }
        return !isHole(bx, bz) && !pillarCell(bx, bz);
    }

    /** Hashed doorway on one side of one block: two cells wide, and some sides have none at all. */
    private boolean doorAt(int bx, int bz, int side, int along) {
        int h = Hash.hash2(seed ^ 0x5bf03635, bx * 4 + side, bz);
        if ((h & 255) >= 224) {
            return false;                          // about one side in eight is solid
        }
        int start = 1 + Hash.floorMod(h >>> 8, BLOCK - 3);
        return along >= start && along < start + 2;
    }

    /** Walls inside a block, which is where each kind of room gets its character. */
    private boolean interiorWall(boolean vertical, int line, int row, int kind,
                                 int ax, int az, int bx, int bz) {
        int wallLine = vertical ? localX(line) : localZ(row);
        int along = vertical ? localZ(row) : localX(line);

        switch (kind) {
            case KIND_PILLARS:
                return pillarCell(ax, az) || pillarCell(bx, bz);

            case KIND_MAZE: {
                // Walls every other line, with a gap of one cell every four whose phase shifts from
                // line to line. Nobody can be sealed in, and the passages are cramped.
                if (Hash.floorMod(wallLine, 2) != 0) {
                    return false;
                }
                int phase = Hash.floorMod(wallLine / 2, 4);
                return Hash.floorMod(along, 4) != phase;
            }

            case KIND_OFFICE:
                return officeWall(vertical, wallLine, along, ax, az);

            case KIND_HALL:
            case KIND_GRAND:
            case KIND_PITS:
            case KIND_SUNKEN:
            default:
                return false;                      // open, dropped or terraced: no walls inside
        }
    }

    /**
     * Office partitions.
     *
     * <p>Walls stand on the multiples of {@link #ROOM_MODULE}, so a block is a grid of small rooms,
     * and every partition has a two-cell doorway somewhere along it. Where a doorway goes is decided
     * by the room it belongs to - each room opens on two of its own walls - which is still a function
     * of integer coordinates, so the two cells either side of a wall always agree about it.
     */
    private boolean officeWall(boolean vertical, int wallLine, int along, int ax, int az) {
        if (wallLine < ROOM_MODULE || Hash.floorMod(wallLine, ROOM_MODULE) != 0
                || wallLine >= BLOCK) {
            return false;
        }
        int bx = blockX(ax);
        int bz = blockZ(az);
        boolean laneAtWest = laneColumn(bx, bz);
        boolean laneAtNorth = laneRow(bz, bx);
        // Along the axis of a corridor lane the first partition is dropped: the room beside the
        // corridor would otherwise be a one-cell sliver running the whole length of the block.
        if (wallLine == ROOM_MODULE && (vertical ? laneAtWest : laneAtNorth)) {
            return false;
        }
        int h = Hash.hash2(seed ^ 0x1b56c4e9, bx * 31 + bz, wallLine * 2 + (vertical ? 1 : 0));
        if ((h & 255) < 34) {
            return false;                          // one partition in eight is missing entirely
        }
        // The two rooms this wall segment separates. The low one is on the -x (or -z) side.
        if (vertical) {
            int rxLow = Hash.floorDiv(wallLine - 1, ROOM_MODULE);
            int rxHigh = Hash.floorDiv(wallLine, ROOM_MODULE);
            int rz = Hash.floorDiv(localZ(az), ROOM_MODULE);
            int spanStart = rz * ROOM_MODULE;
            int inset = laneAtNorth ? CORRIDOR_WIDTH : 0;
            return !roomDoor(bx, bz, rxLow, rz, SIDE_EAST, spanStart, along, inset)
                    && !roomDoor(bx, bz, rxHigh, rz, SIDE_WEST, spanStart, along, inset);
        }
        int rzLow = Hash.floorDiv(wallLine - 1, ROOM_MODULE);
        int rzHigh = Hash.floorDiv(wallLine, ROOM_MODULE);
        int rx = Hash.floorDiv(localX(ax), ROOM_MODULE);
        int spanStart = rx * ROOM_MODULE;
        int inset = laneAtWest ? CORRIDOR_WIDTH : 0;
        return !roomDoor(bx, bz, rx, rzLow, SIDE_SOUTH, spanStart, along, inset)
                && !roomDoor(bx, bz, rx, rzHigh, SIDE_NORTH, spanStart, along, inset);
    }

    /**
     * True if the room at (rx, rz) inside a block opens on {@code side}, and the doorway it cut
     * covers this cell. Walls on the block edge are not available: those are cut by the corridor and
     * neighbour rules instead.
     *
     * <p>{@code inset} is the width of the corridor lane when one runs along this wall: a doorway is
     * never cut into the part of a room that the corridor has taken.
     */
    private boolean roomDoor(int bx, int bz, int rx, int rz, int side, int spanStart, int along,
                             int inset) {
        int mask = availableMask(rx, rz);
        if ((mask & (1 << side)) == 0) {
            return false;
        }
        // Which walls a room opens on must not depend on which wall is asking, or two walls of the
        // same room could both think they are the chosen one - or, worse, neither could.
        int key = Hash.hash3(seed ^ 0x3c6ef372, bx * 16 + rx, bz * 16 + rz, 0);
        if ((chosenSides(mask, key) & (1 << side)) == 0) {
            return false;
        }
        int h = Hash.hash3(seed ^ 0x7f4a7c15, bx * 16 + rx, bz * 16 + rz, side);
        int first = Math.max(spanStart, inset);
        int last = spanStart + ROOM_MODULE - 1;
        int roomWidth = last - first;
        int start = roomWidth <= 0 ? first : first + Hash.floorMod(h >>> 10, roomWidth);
        return along == start || (roomWidth > 0 && along == start + 1);
    }

    private static int availableMask(int rx, int rz) {
        int mask = 0;
        if (rx + 1 < ROOMS_PER_AXIS) mask |= 1 << SIDE_EAST;
        if (rx > 0) mask |= 1 << SIDE_WEST;
        if (rz + 1 < ROOMS_PER_AXIS) mask |= 1 << SIDE_SOUTH;
        if (rz > 0) mask |= 1 << SIDE_NORTH;
        return mask;
    }

    /**
     * The walls a room opens on: two of them, or every one it has if it has fewer than two. Two
     * doors rather than one keeps rooms from clustering into pockets that nothing outside the pocket
     * can reach.
     */
    private static int chosenSides(int mask, int value) {
        int count = Integer.bitCount(mask);
        if (count <= 1) {
            return mask;
        }
        int i = Hash.floorMod(value, count);
        int j = (i + 1 + Hash.floorMod(value >>> 12, count - 1)) % count;
        return (1 << sideAt(mask, i)) | (1 << sideAt(mask, j));
    }

    /** The nth side that a room actually has. */
    private static int sideAt(int mask, int index) {
        for (int side = 0; side < SIDE_COUNT; side++) {
            if ((mask & (1 << side)) != 0) {
                if (index == 0) {
                    return side;
                }
                index--;
            }
        }
        return -1;
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
        return (h & 255) < 226;                   // a few sockets are empty
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

    /**
     * Relative strength of the fixture (warm, slightly uneven brightness). Taller rooms get more
     * tube, so a grand hall is dim but never unlit: without this the inverse square falloff leaves
     * the floor under a four-and-a-half metre ceiling almost black.
     */
    public float fixturePower(int cellX, int cellZ) {
        int h = Hash.hash2(seed ^ 0x77c1a3b5, cellX, cellZ);
        float base = 0.85f + ((h >>> 8) & 63) * (0.3f / 63f);
        float extra = Math.max(0f, ceilY(cellX, cellZ) - ROOM_HEIGHT);
        return base * (1f + 0.32f * extra);
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

    /**
     * Finds a safe spawn near the origin. Waking up in a corridor is the right first impression, so
     * a corridor is preferred; any open floor will do if the seed has none nearby.
     */
    public void findSpawn(float[] out) {
        for (int ring = 0; ring < 8; ring++) {
            for (int k = -ring; k <= ring; k++) {
                int[][] candidates = {{k, -ring}, {k, ring}, {-ring, k}, {ring, k}};
                for (int[] c : candidates) {
                    if (isCorridor(c[0], c[1]) && isOpenSpace(c[0], c[1])) {
                        out[0] = c[0] + 0.5f;
                        out[1] = floorY(c[0], c[1]);
                        out[2] = c[1] + 0.5f;
                        return;
                    }
                }
            }
        }
        for (int ring = 0; ring < 40; ring++) {
            for (int k = -ring; k <= ring; k++) {
                int[][] candidates = {{k, -ring}, {k, ring}, {-ring, k}, {ring, k}};
                for (int[] c : candidates) {
                    if (isOpenSpace(c[0], c[1]) && isSolidGround(c[0], c[1])
                            && floorY(c[0], c[1]) == 0f) {
                        out[0] = c[0] + 0.5f;
                        out[1] = floorY(c[0], c[1]);
                        out[2] = c[1] + 0.5f;
                        return;
                    }
                }
            }
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
                    if (isSolidGround(c[0], c[1]) && !pillarCell(c[0], c[1])) {
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
