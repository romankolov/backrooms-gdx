package com.backrooms.gdx.world;

import com.backrooms.gdx.util.Hash;

/**
 * The infinite backrooms: a pure, stateless description of the level.
 *
 * <p>A chunk can be produced in isolation, on any thread, in any order, and always comes out the
 * same, because everything here is a function of integer grid coordinates and the world seed. That
 * is what makes the map infinite without a map.
 *
 * <p>There is no tile lattice and no repeating block. The level is a <b>binary space partition</b>
 * of the plane: every 32-metre macro square is cut, again and again, at a random position along a
 * random axis, until the pieces are room sized. The cut positions are hashed from the rectangle
 * itself, so any query can walk down the same tree from a macro corner and land on the same room -
 * no storage, no order, no lock.
 *
 * <p>Two more layers sit on top of that:
 *
 * <ol>
 *   <li><b>Avenues.</b> Some macro rows and columns carry a straight hall two or three metres wide,
 *       which keeps going through macro after macro and then stops dead at a wall. Because the
 *       strip's position is hashed from the row (not the macro), the segments line up and read as
 *       one long corridor - the thing the level is remembered for - while the next row has none.</li>
 *   <li><b>Rooms.</b> Every leaf of the partition is a room, and what kind of room it is comes from
 *       the leaf's own rectangle: plain (most of them), a tall hall, a room of terraces stepping
 *       down, a room with pits in the carpet, a forest of pillars, a comb of short walls, or a room
 *       with no working light at all. Corridors - leaves two or three cells wide - are always
 *       plain, so the familiar flat hallway is still the commonest thing you will see.</li>
 * </ol>
 *
 * <p>Every cut carries a doorway, which is why no room is ever sealed in: the partition is a tree,
 * so one door per cut makes the whole tree connected. A cut occasionally carries a second door,
 * which is what makes loops.
 *
 * <p>The generator keeps one mutable scratch object per thread ({@link Query}) and allocates
 * nothing per query: it is read from two or three worker threads while chunks are built.
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
    /** Ceiling height of an ordinary room: the classic, flat three metres. */
    public static final float ROOM_HEIGHT = 3.0f;
    /** How far below the floor the bottom of a hole sits. Deep enough that you do not get out. */
    public static final float HOLE_DEPTH = 6.0f;

    /** Light fixtures sit on this lattice (in cells). */
    public static final int LIGHT_SPACING = 6;
    /** Fixtures are placed on cells with this phase inside the light lattice. */
    public static final int LIGHT_PHASE = 0;

    /**
     * Cells across one macro square. This is not a tile: nothing repeats every 32 metres except the
     * fact that the partition is (re)started and that avenues may run along a macro edge. Rooms,
     * walls, doors and features are all different on the other side of it.
     */
    public static final int MACRO = 32;

    // Room kinds. A leaf of the partition is one of these.
    /** Flat three metres, no interior walls: the room everyone remembers. */
    public static final int KIND_PLAIN = 0;
    /** A tall hall, four to five and a half metres up. */
    public static final int KIND_GRAND = 1;
    /** Floor stepping down towards the middle in half-metre terraces. */
    public static final int KIND_SUNKEN = 2;
    /** Pits in the carpet. */
    public static final int KIND_PITS = 3;
    /** A forest of square pillars. */
    public static final int KIND_PILLARS = 4;
    /** Short walls jutting in at right angles: a comb. */
    public static final int KIND_SPURS = 5;
    /** Plain walls, but every tube in it is dead. */
    public static final int KIND_DARK = 6;
    public static final int KIND_COUNT = 7;

    /** Each terrace of a sunken room drops this much: under the player's step height, so it walks. */
    public static final float TERRACE_STEP = 0.5f;
    /** How many terraces a sunken room has before it reaches its plateau. */
    public static final int TERRACE_RINGS = 4;
    /** Ceiling of a sunken room: it keeps its height while the floor drops away. */
    public static final float SUNKEN_CEIL = 3.4f;
    /** Ceiling of a room with pits in it. */
    public static final float PIT_CEIL = 3.4f;
    /** Ceiling of a room with a comb of short walls: low, so it feels wrong. */
    public static final float SPUR_CEIL = 2.8f;
    /** Lowest and highest ceilings of a tall hall. */
    public static final float GRAND_CEIL_MIN = 4.2f;
    public static final float GRAND_CEIL_MAX = 5.4f;
    /** Lowest and highest ceilings of a corridor. Corridors vary: they are not one room repeated. */
    public static final float CORRIDOR_CEIL_MIN = 2.7f;
    public static final float CORRIDOR_CEIL_MAX = 3.1f;

    /** A leaf narrower than this is a corridor: always plain, never given features. */
    private static final int CORRIDOR_WIDTH = 3;
    /** How far features are kept from a room wall, in cells, so a doorway is never blocked. */
    private static final int FEATURE_MARGIN = 1;

    private final int seed;

    public WorldGen(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    // ---------------------------------------------------------------------------------------------
    // the partition tree
    // ---------------------------------------------------------------------------------------------

    /**
     * Scratch for one query: the rectangle the descent is standing on, where the next cut is, and
     * (once it has arrived) the room. One of these per thread, reused, so a query allocates nothing.
     */
    private static final class Query {
        int x0;
        int z0;
        int x1;
        int z1;
        int kind;
        int splitAxis;
        int splitPos;
        int nodeX0;
        int nodeZ0;
        int nodeX1;
        int nodeZ1;
        // The room found by the last query. Chunk generation and the light bake ask about runs of
        // neighbouring cells, which are almost always still inside the same room, so this turns
        // most descents into a rectangle test.
        boolean cached;
        int cacheX0;
        int cacheZ0;
        int cacheX1;
        int cacheZ1;
        int cacheKind;
    }

    private final ThreadLocal<Query> queries = new ThreadLocal<Query>() {
        @Override
        protected Query initialValue() {
            return new Query();
        }
    };

    private Query scratch() {
        return queries.get();
    }

    // ---- avenues --------------------------------------------------------------------------------

    /**
     * Cell offset of the avenue inside its macro, and its width in cells. The offset comes from the
     * row (or column) the avenue runs along, not from the macro, which is what makes the segments
     * in neighbouring macros line up into one straight hall.
     */
    private int avenueOffset(int stem, int index) {
        int h = Hash.hash2(seed ^ stem, index, 0x5a5a);
        return 3 + ((h >>> 8) % (MACRO - 9));
    }

    private int avenueWidth(int stem, int index) {
        int h = Hash.hash2(seed ^ stem, index, 0x5a5a);
        return 2 + ((h >>> 20) & 1);
    }

    /**
     * Is there an avenue in this macro? Two hashes, not one: whether the run of four macros has an
     * avenue at all, and then whether it survives as far as this one. That is what gives a hall of
     * a hundred metres that ends, rather than a corridor every N blocks.
     */
    private boolean avenuePresent(int salt, int a, int b) {
        if ((Hash.hash2(seed ^ salt ^ 0x2222, a, Hash.floorDiv(b, 4)) & 255) >= 236) {
            return false;
        }
        return (Hash.hash2(seed ^ salt ^ 0x3333, a, b) & 255) < 226;
    }

    private boolean columnAvenue(int mx, int mz) {
        return avenuePresent(0x5555, mx, mz);
    }

    private boolean rowAvenue(int mx, int mz) {
        return avenuePresent(0x6666, mz, mx);
    }

    /**
     * Where the next cut of this rectangle is.
     *
     * @return 0 for a room (the rectangle is a leaf), 1 for a vertical wall at {@code q.splitPos}
     *         (an x), 2 for a horizontal wall at {@code q.splitPos} (a z)
     */
    private int splitOf(int x0, int z0, int x1, int z1, Query q) {
        int mx = Hash.floorDiv(x0, MACRO);
        int mz = Hash.floorDiv(z0, MACRO);
        // An avenue is a forced cut at a position that depends on the row only, so the segments in
        // neighbouring macros line up into one long hall - and stop where the avenue stops.
        if (columnAvenue(mx, mz)) {
            int ax = mx * MACRO + avenueOffset(0x1111, mx);
            int th = avenueWidth(0x1111, mx);
            if (x0 == ax && x1 > ax + th) {
                q.splitPos = ax + th;
                q.splitAxis = 1;
                return 1;
            }
            if (x0 < ax && x1 > ax + th) {
                q.splitPos = ax;
                q.splitAxis = 1;
                return 1;
            }
        }
        if (rowAvenue(mx, mz)) {
            int az = mz * MACRO + avenueOffset(0x7777, mz);
            int th = avenueWidth(0x7777, mz);
            if (z0 == az && z1 > az + th) {
                q.splitPos = az + th;
                q.splitAxis = 2;
                return 2;
            }
            if (z0 < az && z1 > az + th) {
                q.splitPos = az;
                q.splitAxis = 2;
                return 2;
            }
        }

        int w = x1 - x0;
        int h = z1 - z0;
        if (w <= 3 && h <= 3) {
            return 0;
        }
        int hh = Hash.hash3(seed ^ 0x4444, x0 * 31 + x1, z0 * 31 + z1, w * 7 + h);
        int big = Math.max(w, h);
        int small = Math.min(w, h);
        boolean leaf;
        if (small <= CORRIDOR_WIDTH) {
            // A corridor: long, thin, and left whole unless it is very long, in which case one in
            // four gets a wall across it with a door - a corridor that stops and starts again.
            leaf = !(big >= 17 && ((hh >>> 12) & 3) == 0);
        } else {
            // Each rectangle wants to be a room of its own size, four to eight metres across, so
            // the partition does not converge on one room size the way a fixed rule would.
            int target = 4 + ((hh >>> 3) % 5);
            if (big <= target) {
                leaf = true;
            } else if (big <= target + 2) {
                leaf = (hh & 127) < 90;
            } else if (big <= 15 && ((hh >>> 16) & 255) < 14) {
                leaf = true;                       // rare: a room that kept a whole corner
            } else {
                leaf = false;
            }
        }
        if (leaf) {
            return 0;
        }

        // Always cut the long axis. Cutting across a narrow rectangle is how a level ends up as a
        // field of two-metre slivers.
        boolean vertical = (w > h) || (w == h && (hh & 1) == 0);
        int lo = vertical ? x0 : z0;
        int len = vertical ? w : h;
        int pos;
        if (((hh >>> 9) & 255) < 46 && len >= 10) {
            // Nearly one cut in five shaves off a two or three cell strip along the edge: that
            // strip is a corridor, and it inherits the length of the room it was taken from.
            int thin = 2 + ((hh >>> 17) & 1);
            pos = ((hh >>> 18) & 1) == 0 ? lo + thin : lo + len - thin;
        } else {
            int margin = len >= 10 ? 4 : 3;
            int span = len - 2 * margin + 1;
            if (span < 1) {
                span = 1;
            }
            pos = lo + margin + ((hh >>> 8) & 0x7fffffff) % span;
        }
        q.splitPos = pos;
        q.splitAxis = vertical ? 1 : 2;
        return q.splitAxis;
    }

    /** Runs down the partition to the room that holds this cell. Leaves it in {@code q}. */
    private void leafAt(int cellX, int cellZ, Query q) {
        if (q.cached && cellX >= q.cacheX0 && cellX < q.cacheX1
                && cellZ >= q.cacheZ0 && cellZ < q.cacheZ1) {
            q.x0 = q.cacheX0;
            q.z0 = q.cacheZ0;
            q.x1 = q.cacheX1;
            q.z1 = q.cacheZ1;
            q.kind = q.cacheKind;
            return;
        }
        int x0 = Hash.floorDiv(cellX, MACRO) * MACRO;
        int z0 = Hash.floorDiv(cellZ, MACRO) * MACRO;
        int x1 = x0 + MACRO;
        int z1 = z0 + MACRO;
        for (int depth = 0; depth < 48; depth++) {
            int axis = splitOf(x0, z0, x1, z1, q);
            if (axis == 0) {
                break;
            }
            if (axis == 1) {
                if (cellX < q.splitPos) {
                    x1 = q.splitPos;
                } else {
                    x0 = q.splitPos;
                }
            } else {
                if (cellZ < q.splitPos) {
                    z1 = q.splitPos;
                } else {
                    z0 = q.splitPos;
                }
            }
        }
        q.x0 = x0;
        q.z0 = z0;
        q.x1 = x1;
        q.z1 = z1;
        q.kind = kindOf(x0, z0, x1, z1);
        q.cached = true;
        q.cacheX0 = x0;
        q.cacheZ0 = z0;
        q.cacheX1 = x1;
        q.cacheZ1 = z1;
        q.cacheKind = q.kind;
    }

    /**
     * The cut that separates two neighbouring cells. On return the query holds the rectangle of the
     * node that made the cut (which is what the doorway is hashed from) and the cut itself.
     *
     * @return 0 if both cells are in the same room, in which case the query holds that room
     */
    private int separatingCut(int ax, int az, int bx, int bz, Query q) {
        if (q.cached
                && ax >= q.cacheX0 && ax < q.cacheX1 && az >= q.cacheZ0 && az < q.cacheZ1
                && bx >= q.cacheX0 && bx < q.cacheX1 && bz >= q.cacheZ0 && bz < q.cacheZ1) {
            // Both sides are in the room that was just found: no cut between them.
            q.x0 = q.cacheX0;
            q.z0 = q.cacheZ0;
            q.x1 = q.cacheX1;
            q.z1 = q.cacheZ1;
            q.kind = q.cacheKind;
            return 0;
        }
        int x0 = Hash.floorDiv(ax, MACRO) * MACRO;
        int z0 = Hash.floorDiv(az, MACRO) * MACRO;
        int x1 = x0 + MACRO;
        int z1 = z0 + MACRO;
        for (int depth = 0; depth < 48; depth++) {
            int axis = splitOf(x0, z0, x1, z1, q);
            if (axis == 0) {
                break;
            }
            boolean divides = axis == 1
                    ? ((ax < q.splitPos) != (bx < q.splitPos))
                    : ((az < q.splitPos) != (bz < q.splitPos));
            if (divides) {
                q.nodeX0 = x0;
                q.nodeZ0 = z0;
                q.nodeX1 = x1;
                q.nodeZ1 = z1;
                q.splitAxis = axis;
                return axis;
            }
            if (axis == 1) {
                if (ax < q.splitPos) {
                    x1 = q.splitPos;
                } else {
                    x0 = q.splitPos;
                }
            } else {
                if (az < q.splitPos) {
                    z1 = q.splitPos;
                } else {
                    z0 = q.splitPos;
                }
            }
        }
        q.x0 = x0;
        q.z0 = z0;
        q.x1 = x1;
        q.z1 = z1;
        q.kind = kindOf(x0, z0, x1, z1);
        q.cached = true;
        q.cacheX0 = x0;
        q.cacheZ0 = z0;
        q.cacheX1 = x1;
        q.cacheZ1 = z1;
        q.cacheKind = q.kind;
        return 0;
    }

    /**
     * The doorway a cut makes. Every cut makes one, which is what keeps the level connected; about
     * one in four makes a second, which is what makes loops.
     *
     * @param along the coordinate along the wall (z for a vertical wall, x for a horizontal one)
     */
    private boolean doorway(int nx0, int nz0, int nx1, int nz1, int axis, int pos, int along) {
        int h = Hash.hash3(seed ^ 0xd00d, nx0 * 97 + nz0, nx1 * 31 + nz1, pos * 7 + axis);
        int span0 = axis == 1 ? nz0 : nx0;
        int span1 = axis == 1 ? nz1 : nx1;
        int len = span1 - span0;
        int width = 2 + ((h >>> 8) & 1);
        if (width > len - 2) {
            width = Math.max(1, len - 2);
        }
        int span = len - 1 - width;
        if (span < 1) {
            span = 1;
        }
        int start = span0 + 1 + ((h >>> 11) & 0x3ff) % span;
        if (along >= start && along < start + width) {
            return true;
        }
        if (((h >>> 20) & 3) == 0) {
            int width2 = 2 + ((h >>> 24) & 1);
            if (width2 > len - 2) {
                width2 = Math.max(1, len - 2);
            }
            int start2 = Math.max(span0 + 1, span1 - 1 - width2);
            if (along >= start2 && along < start2 + width2 && start2 + width2 <= span1) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------
    // rooms
    // ---------------------------------------------------------------------------------------------

    /**
     * What kind of room a leaf is. Decided by the room's own rectangle, so it never needs to be
     * stored, and it is the same wherever it is asked from.
     */
    private int kindOf(int x0, int z0, int x1, int z1) {
        int w = x1 - x0;
        int h = z1 - z0;
        if (Math.min(w, h) <= CORRIDOR_WIDTH) {
            return KIND_PLAIN;                     // a corridor is always a corridor
        }
        int hh = Hash.hash3(seed ^ 0x0abc, x0 * 13 + z0, x1 * 7 + z1, w * 5 + h);
        int roll = Hash.floorMod(hh, 100);
        if (roll < 63) {
            return KIND_PLAIN;
        }
        int narrowest = Math.min(w, h);
        if (roll < 70) {
            return KIND_GRAND;
        }
        if (roll < 78) {
            return KIND_SUNKEN;
        }
        if (roll < 88) {
            return KIND_PITS;
        }
        if (roll < 94) {
            return narrowest >= 5 ? KIND_PILLARS : KIND_PLAIN;
        }
        if (roll < 98) {
            return narrowest >= 5 ? KIND_SPURS : KIND_PLAIN;
        }
        return KIND_DARK;
    }

    /** Which kind of room a cell is in. Exposed for the lighting and for tests. */
    public int kindAt(int cellX, int cellZ) {
        Query q = scratch();
        leafAt(cellX, cellZ, q);
        return q.kind;
    }

    public static String kindName(int kind) {
        switch (kind) {
            case KIND_PLAIN: return "plain";
            case KIND_GRAND: return "grand";
            case KIND_SUNKEN: return "sunken";
            case KIND_PITS: return "pits";
            case KIND_PILLARS: return "pillars";
            case KIND_SPURS: return "spurs";
            case KIND_DARK: return "dark";
            default: return "?";
        }
    }

    /** Distance from a cell to the nearest wall of its room, in cells. */
    private static int inset(Query q, int cellX, int cellZ) {
        return Math.min(Math.min(cellX - q.x0, q.x1 - 1 - cellX),
                Math.min(cellZ - q.z0, q.z1 - 1 - cellZ));
    }

    // ---------------------------------------------------------------------------------------------
    // terrain
    // ---------------------------------------------------------------------------------------------

    /** Floor level of a cell. */
    public float floorY(int cellX, int cellZ) {
        Query q = scratch();
        leafAt(cellX, cellZ, q);
        return floorOf(q, cellX, cellZ);
    }

    private float floorOf(Query q, int cellX, int cellZ) {
        if (isHoleIn(q, cellX, cellZ)) {
            return -HOLE_DEPTH;
        }
        if (q.kind == KIND_SUNKEN) {
            return -Math.min(TERRACE_RINGS, inset(q, cellX, cellZ)) * TERRACE_STEP;
        }
        return 0f;
    }

    /** Ceiling height of a cell: absolute, so a sunken room keeps its full height. */
    public float ceilY(int cellX, int cellZ) {
        Query q = scratch();
        leafAt(cellX, cellZ, q);
        return ceilOf(q);
    }

    private float ceilOf(Query q) {
        int w = q.x1 - q.x0;
        int h = q.z1 - q.z0;
        switch (q.kind) {
            case KIND_GRAND: {
                int hh = Hash.hash3(seed ^ 0x0f00, q.x0, q.z0, q.x1 + q.z1);
                return GRAND_CEIL_MIN
                        + Hash.floorMod(hh, 100) * (1f / 100f) * (GRAND_CEIL_MAX - GRAND_CEIL_MIN);
            }
            case KIND_SUNKEN:
                return SUNKEN_CEIL;
            case KIND_PITS:
                return PIT_CEIL;
            case KIND_SPURS:
                return SPUR_CEIL;
            default:
                if (Math.min(w, h) <= CORRIDOR_WIDTH) {
                    int hh = Hash.hash3(seed ^ 0x0ba2, q.x0, q.z0, q.x1 + q.z1);
                    return CORRIDOR_CEIL_MIN
                            + Hash.floorMod(hh, 100) * (1f / 100f)
                                    * (CORRIDOR_CEIL_MAX - CORRIDOR_CEIL_MIN);
                }
                return ROOM_HEIGHT;
        }
    }

    /**
     * True where the floor drops away and there is no way back up: the pits of a pitted room, and
     * the occasional stray one in a plain room. Falling in is how the level moves you somewhere
     * else, so a hole is never a place you can stand.
     */
    public boolean isHole(int cellX, int cellZ) {
        Query q = scratch();
        leafAt(cellX, cellZ, q);
        return isHoleIn(q, cellX, cellZ);
    }

    private boolean isHoleIn(Query q, int cellX, int cellZ) {
        if (q.kind != KIND_PITS && q.kind != KIND_PLAIN) {
            return false;
        }
        if (Math.min(q.x1 - q.x0, q.z1 - q.z0) <= CORRIDOR_WIDTH + 1) {
            return false;                          // never a pit where the corridor is; it must stay a way through
        }
        // A ring of floor inside every room: a pit can never cut a doorway off.
        if (inset(q, cellX, cellZ) < FEATURE_MARGIN) {
            return false;
        }
        if (q.kind == KIND_PITS) {
            return Hash.fbm2(seed ^ 0x2545f491, cellX * 0.13f + 11f, cellZ * 0.13f - 7f) > -0.10f;
        }
        // One plain room in nine hides a stray pit or two.
        int hh = Hash.hash3(seed ^ 0x1791, q.x0, q.z0, q.x1 + q.z1);
        if ((hh & 15) != 0) {
            return false;
        }
        return Hash.fbm2(seed ^ 0x6f31c9d7, cellX * 0.31f, cellZ * 0.31f) > 0.74f;
    }

    /** True where the cell is the hollow inside of a square pillar. */
    private boolean pillarCell(Query q, int cellX, int cellZ) {
        if (q.kind != KIND_PILLARS) {
            return false;
        }
        if (inset(q, cellX, cellZ) < FEATURE_MARGIN) {
            return false;
        }
        int lx = cellX - q.x0;
        int lz = cellZ - q.z0;
        return Hash.floorMod(lx, 3) == 1 && Hash.floorMod(lz, 3) == 1;
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
     * Every wall in the level is decided here, from the two rooms on either side of it. Where those
     * two cells are in different rooms, the wall is the cut that separated them, minus its doorway.
     * Where they are in the same room, the only walls are the room's own features.
     */
    private boolean wall(boolean vertical, int line, int row) {
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
        Query q = scratch();
        int axis = separatingCut(ax, az, bx, bz, q);
        if (axis == 0) {
            // Same room: a pillar is a cell with a wall on every side, and a comb room has its spurs.
            if (pillarCell(q, ax, az) || pillarCell(q, bx, bz)) {
                return true;
            }
            return featureWall(vertical, line, row, q);
        }
        int along = vertical ? row : line;
        return !doorway(q.nodeX0, q.nodeZ0, q.nodeX1, q.nodeZ1, q.splitAxis, q.splitPos, along);
    }

    /** Walls inside a room: the comb of a {@link #KIND_SPURS} room and nothing else. */
    private boolean featureWall(boolean vertical, int line, int row, Query q) {
        if (q.kind != KIND_SPURS) {
            return false;
        }
        int w = q.x1 - q.x0;
        int h = q.z1 - q.z0;
        int lx = line - q.x0;
        int lz = row - q.z0;
        if (vertical) {
            if (lx < FEATURE_MARGIN || lx > w - FEATURE_MARGIN) {
                return false;
            }
            if (lz < 1 || lz > h - 2) {
                return false;
            }
            if (Hash.floorMod(lx, 2) != 1) {
                return false;
            }
            int phase = Hash.floorMod(lx / 2, 4);
            return Hash.floorMod(lz, 4) != phase;
        }
        if (lz < FEATURE_MARGIN || lz > h - FEATURE_MARGIN) {
            return false;
        }
        if (lx < 1 || lx > w - 2) {
            return false;
        }
        if (Hash.floorMod(lz, 2) != 1) {
            return false;
        }
        int phase = Hash.floorMod(lz / 2, 4);
        return Hash.floorMod(lx, 4) != phase;
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

    /** True for a cell inside a pillar: the renderer builds those out of wall segments. */
    public boolean isPillar(int cellX, int cellZ) {
        Query q = scratch();
        leafAt(cellX, cellZ, q);
        return pillarCell(q, cellX, cellZ);
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
     * Straight-line visibility between two points, sampled along the line. Walls are 16 cm thick and
     * the sample step is smaller than that, so nothing is stepped over.
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
        if ((h & 255) >= 226) {
            return false;                          // a few sockets are empty
        }
        // Nothing is hung over a hole: a fitting there lights the bottom of a pit.
        if (isHole(cellX, cellZ)) {
            return false;
        }
        // A room with no working light has no fixtures in it at all.
        return kindAt(cellX, cellZ) != KIND_DARK;
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
        Query q = scratch();
        for (int ring = 0; ring < 24; ring++) {
            for (int k = -ring; k <= ring; k++) {
                for (int side = 0; side < 4; side++) {
                    int cx;
                    int cz;
                    if (side == 0) {
                        cx = k;
                        cz = -ring;
                    } else if (side == 1) {
                        cx = k;
                        cz = ring;
                    } else if (side == 2) {
                        cx = -ring;
                        cz = k;
                    } else {
                        cx = ring;
                        cz = k;
                    }
                    if (!isOpenSpace(cx, cz)) {
                        continue;
                    }
                    leafAt(cx, cz, q);
                    boolean corridor = Math.min(q.x1 - q.x0, q.z1 - q.z0) <= CORRIDOR_WIDTH;
                    if (corridor && !isHoleIn(q, cx, cz)) {
                        out[0] = cx + 0.5f;
                        out[1] = floorOf(q, cx, cz);
                        out[2] = cz + 0.5f;
                        return;
                    }
                }
            }
        }
        for (int ring = 0; ring < 40; ring++) {
            for (int k = -ring; k <= ring; k++) {
                for (int side = 0; side < 4; side++) {
                    int cx = side == 0 || side == 1 ? k : (side == 2 ? -ring : ring);
                    int cz = side == 0 ? -ring : (side == 1 ? ring : k);
                    if (isOpenSpace(cx, cz) && isSolidGround(cx, cz)) {
                        out[0] = cx + 0.5f;
                        out[1] = floorY(cx, cz);
                        out[2] = cz + 0.5f;
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
                for (int side = 0; side < 4; side++) {
                    int cx = side == 0 || side == 1 ? ci + k : (side == 2 ? ci - ring : ci + ring);
                    int cz = side == 0 ? cj - ring : (side == 1 ? cj + ring : cj + k);
                    if (isSolidGround(cx, cz) && !isPillar(cx, cz)) {
                        out[0] = cx + 0.5f;
                        out[1] = floorY(cx, cz);
                        out[2] = cz + 0.5f;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
