package com.backrooms.gdx.entity;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.world.WorldGen;

/**
 * The thing in the corridors: where it is, what it is doing, and why you only ever half see it.
 *
 * <p>This class is pure logic - it never touches a GL context, which is what makes it testable. The
 * visuals live in {@code EntityRenderer}.
 *
 * <p>It is a stalker and nothing else. There is no chase, no lunge and no way to be caught: the
 * whole point of it is the moment of doubt, not a game over. Each appearance is one pass:
 *
 * <ol>
 *   <li><b>Nothing.</b> Most of the time the level is empty, and the emptiness is the point. It
 *       waits between thirty seconds and a minute and a half.</li>
 *   <li><b>Out of sight.</b> It appears ten to eighteen metres ahead of where you are looking, on
 *       the far side of a wall, so it is never seen arriving.</li>
 *   <li><b>The pass.</b> It walks across the corridor ahead of you at a stroll, either looking at
 *       you or looking where it is going, and it never speeds up.</li>
 *   <li><b>The wall.</b> It keeps walking until something solid takes it out of your sight. Only
 *       then does it go - it does not blink out while you are watching it.</li>
 * </ol>
 *
 * <p>Everything spatial is done on the generator's cell grid, so the AI never needs the streamed
 * world to be loaded: it can think about cells the renderer has not built yet.
 */
public final class EntityController {

    public enum State {
        /** Not in the world right now. */
        ABSENT,
        /** Walking its one pass across the player's view. */
        STALK
    }

    /** How far ahead of the player it appears. */
    private static final float SPAWN_MIN = 10f;
    private static final float SPAWN_MAX = 18f;
    /** How far it walks across the view, in total. */
    private static final float PASS_MIN = 9f;
    private static final float PASS_MAX = 16f;
    /** Seconds of nothing between appearances. */
    private static final float GONE_MIN = 28f;
    private static final float GONE_MAX = 85f;

    public static final float STALK_SPEED = 1.15f;

    /** One step per stride, in metres. Long legs. */
    private static final float STRIDE = 1.15f;
    /** Beyond this it is lost in the haze and might as well be gone. */
    private static final float SIGHT_RANGE = 30f;
    /** It has to be out of sight for this long - not just flicker behind a doorway. */
    private static final float HIDDEN_TO_VANISH = 0.5f;
    /** And it has to have actually walked somewhere before it can leave. */
    private static final float MIN_TRAVEL = 2f;
    /** A pass that has not shown itself by now went the wrong way: drop it. */
    private static final float NO_SHOW_TIMEOUT = 22f;
    /** Hard cap, so a pass can never become furniture. */
    private static final float MAX_PASS_TIME = 45f;
    /** If it is this far away, whatever it is doing no longer matters. */
    private static final float ABANDON_RANGE = 60f;

    /** A rare, distant vocalisation while it walks. */
    private static final float ROAR_MIN = 14f;
    private static final float ROAR_MAX = 34f;

    /** How often it makes one of its small noises while it is in the level. */
    private static final float CUE_MIN = 5f;
    private static final float CUE_MAX = 13f;
    /** Cues are waited on, not played at the moment they happen: this is a queue, not a flag. */
    private static final int CUE_KINDS = 4;
    private static final int CUE_QUEUE = 4;

    private static final int WINDOW = 40;              // cells searched in each direction
    private static final int SIDE = WINDOW * 2 + 1;
    private static final int MAX_PATH = 384;
    /** How many cells one route search may look at. A route is short; the search is bounded. */
    private static final int SEARCH_BUDGET = 2600;
    /**
     * Looking for somewhere to appear is a search over the level, and a bad neighbourhood can eat
     * tens of milliseconds. A slice of it runs per frame instead, so the picture never stalls while
     * it decides where to stand.
     */
    private static final int APPEAR_ATTEMPTS_PER_FRAME = 10;
    private static final int APPEAR_ATTEMPTS_TOTAL = 140;

    private final WorldGen gen;
    private final Random random;

    public final Vector3 position = new Vector3();
    public float yaw;

    private State state = State.ABSENT;
    private boolean enabled = true;
    /** Set for screenshots: it stands where it was put instead of walking. */
    private boolean frozen;
    private float dormant;
    private float passTime;
    private float hiddenFor;
    private float travelled;
    private boolean seenEver;
    private float bobPhase;
    private float walked;
    private float roarTimer;
    private boolean roarPending;
    private float cueTimer;
    private final int[] cueQueue = new int[CUE_QUEUE];
    private int cueCount;
    private float speedNow;
    private float presence;

    // Where this pass is walking to, on the cell grid.
    private int goalX;
    private int goalZ;

    // Breadth-first search scratch space. Allocated once: this runs on the render thread.
    private final int[] frontier = new int[SIDE * SIDE];
    private final int[] cameFrom = new int[SIDE * SIDE];
    private final int[] pathX = new int[MAX_PATH];
    private final int[] pathZ = new int[MAX_PATH];
    /** Route scratch, reused: planning happens during play and must not allocate. */
    private final int[] reversed = new int[MAX_PATH];
    private int appearTries;
    private int pathLength;
    private int pathIndex;
    private float repathTimer;

    public EntityController(WorldGen gen, long randomSeed) {
        this.gen = gen;
        this.random = new Random(randomSeed);
        this.dormant = 20f + random.nextFloat() * 30f;
    }

    public State state() {
        return state;
    }

    /** True while it is in the world at all. */
    public boolean active() {
        return state != State.ABSENT;
    }

    /** Current walking speed, metres per second. Drives the animation. */
    public float speed() {
        return speedNow;
    }

    /** Seconds this appearance has lasted, for the debug line. */
    public float stalkTime() {
        return passTime;
    }

    /** Turns the whole encounter on or off without losing the cooldown state. */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled && state != State.ABSENT) {
            vanish(1e9f);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    /** Holds it in place while the rest of the encounter logic runs. Used for screenshots. */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * 0..1: how much of a presence it is right now, by distance. The camera uses this to start
     * tearing the tape long before the thing is visible.
     */
    public float presence() {
        return presence;
    }

    /**
     * True when a foot has landed since the last call. The caller plays the sound: a step every
     * stride. A frozen entity does not step.
     */
    public boolean takeFootstep() {
        if (state == State.ABSENT || frozen || speedNow <= 0.01f) {
            return false;
        }
        if (walked < STRIDE) {
            return false;
        }
        walked -= STRIDE;
        return true;
    }

    /**
     * The next small noise it made, or -1 if it has not made one. Kinds: 0 a creak of something
     * leaning, 1 chittering, 2 something dragged, 3 breathing.
     */
    public int takeCue() {
        if (cueCount == 0) {
            return -1;
        }
        int which = cueQueue[0];
        for (int i = 1; i < cueCount; i++) {
            cueQueue[i - 1] = cueQueue[i];
        }
        cueCount--;
        return which;
    }

    private void pushCue(int which, float distance) {
        // Near, it is breathing and clicking; far off, the heavier sounds carry better.
        int kind = distance < 12f ? which : (which % 2 == 0 ? 0 : 2);
        if (cueCount < CUE_QUEUE) {
            cueQueue[cueCount++] = kind;
        }
    }

    /** True when it vocalises - a distant, quiet sound while it walks. */
    public boolean consumeRoar() {
        boolean was = roarPending;
        roarPending = false;
        return was;
    }

    /**
     * Puts it into the world at a given spot, walking a fixed line - used by tests and by the
     * screenshot job. The scripted pass is planned as if the camera were looking at it.
     */
    public void forceSpawn(float x, float z) {
        position.set(x, groundAt(x, z), z);
        state = State.STALK;
        dormant = 0f;
        passTime = 0f;
        hiddenFor = 0f;
        travelled = 0f;
        seenEver = false;
        pathLength = 0;
        pathIndex = 0;
        repathTimer = 0f;
        walked = 0f;
        roarTimer = ROAR_MIN * 0.5f + random.nextFloat() * ROAR_MIN;
        cueTimer = CUE_MIN * 0.6f + random.nextFloat() * CUE_MIN;
        cueCount = 0;
        // Walk away from the cell it was given, so a forced pass also travels.
        float heading = random.nextFloat() * MathUtils.PI2;
        goalX = MathUtils.floor(x + MathUtils.cos(heading) * PASS_MIN);
        goalZ = MathUtils.floor(z + MathUtils.sin(heading) * PASS_MIN);
    }

    /** Removes it from the world and sets how long until it may come back. */
    public void vanish(float cooldown) {
        state = State.ABSENT;
        dormant = cooldown;
        pathLength = 0;
        pathIndex = 0;
        speedNow = 0f;
        walked = 0f;
        travelled = 0f;
        roarPending = false;
        roarTimer = 0f;
        cueTimer = 0f;
        cueCount = 0;
        // presence is left alone: the tape should keep tearing for a moment after it is gone.
    }

    /** @param delta seconds; @param player the player's feet position */
    public void update(float delta, Vector3 player) {
        update(delta, player, 0f);
    }

    /**
     * @param delta  seconds
     * @param player the player's feet position
     * @param yaw    the player's heading in radians, so it appears out of sight *in front*
     */
    public void update(float delta, Vector3 player, float yaw) {
        speedNow = 0f;
        float dist = distance(player);

        if (!enabled) {
            // Still let the picture calm down after a toggle: the tape should not stay torn because
            // the player switched the encounter off mid-pass.
            if (presence > 0.001f) {
                presence += (0f - presence) * Math.min(1f, delta * 2.5f);
            }
            return;
        }

        switch (state) {
            case ABSENT:
                dormant -= delta;
                if (dormant <= 0f) {
                    if (planPass(player, yaw, APPEAR_ATTEMPTS_PER_FRAME)) {
                        appearTries = 0;
                    } else {
                        appearTries += APPEAR_ATTEMPTS_PER_FRAME;
                        if (appearTries >= APPEAR_ATTEMPTS_TOTAL) {
                            appearTries = 0;
                            dormant = 2f;          // nowhere good to appear yet; try again shortly
                        }
                    }
                }
                break;

            case STALK: {
                passTime += delta;
                boolean visible = dist < SIGHT_RANGE && hasLineOfSight(player);
                if (visible) {
                    seenEver = true;
                    hiddenFor = 0f;
                } else {
                    hiddenFor += delta;
                }

                if (!frozen) {
                    walk(STALK_SPEED, delta);
                    roarTimer -= delta;
                    if (roarTimer <= 0f) {
                        roarPending = true;
                        roarTimer = ROAR_MIN + random.nextFloat() * (ROAR_MAX - ROAR_MIN);
                    }
                    // Its small noises are what you hear most of: something leaning its weight on
                    // a wall two rooms away, clicking to itself, dragging itself along.
                    cueTimer -= delta;
                    if (cueTimer <= 0f) {
                        pushCue(random.nextInt(CUE_KINDS), dist);
                        cueTimer = CUE_MIN + random.nextFloat() * (CUE_MAX - CUE_MIN);
                    }
                }

                // The whole trick: it only leaves once a wall has taken it away from the player.
                if (frozen) {
                    break;                         // posed for a screenshot: stays where it was put
                }
                if (seenEver && hiddenFor >= HIDDEN_TO_VANISH && travelled >= MIN_TRAVEL) {
                    vanish(GONE_MIN + random.nextFloat() * (GONE_MAX - GONE_MIN));
                } else if (dist > ABANDON_RANGE) {
                    vanish(GONE_MIN * 0.5f + random.nextFloat() * GONE_MIN);
                } else if (!seenEver && passTime > NO_SHOW_TIMEOUT) {
                    // It walked the whole way without ever coming into view. Nothing happened.
                    vanish(12f + random.nextFloat() * 20f);
                } else if (passTime > MAX_PASS_TIME) {
                    vanish(GONE_MIN + random.nextFloat() * (GONE_MAX - GONE_MIN));
                }
                break;
            }

            default:
                break;
        }

        if (active()) {
            position.y = groundAt(position.x, position.z);
            // The whole body sways as it walks. Cheap, but it reads as movement at a distance.
            bobPhase += delta * (2.4f + speedNow * 1.6f);
        }

        float wanted = 0f;
        if (active()) {
            float near = MathUtils.clamp(1f - dist / 22f, 0f, 1f);
            wanted = near * 0.55f;
        }
        // Ease so a disappearance does not snap the tape back to clean.
        presence += (wanted - presence) * Math.min(1f, delta * 2.5f);
    }

    public float bob() {
        return (float) Math.sin(bobPhase) * 0.03f;
    }

    // ---------------------------------------------------------------------------------------------
    // the pass
    // ---------------------------------------------------------------------------------------------

    /**
     * Plans one appearance: it starts behind a wall in front of the player, and walks across the
     * view to a cell that is behind another wall. Returns false if the level has no room for that
     * here, in which case the caller simply tries again later.
     */
    private boolean planPass(Vector3 player, float yaw, int attempts) {
        // The player's forward, in the same convention as the camera: yaw 0 looks down -Z.
        float forwardX = -MathUtils.sin(yaw);
        float forwardZ = -MathUtils.cos(yaw);

        for (int attempt = 0; attempt < attempts; attempt++) {
            // A direction a little off the centre of the view, so it crosses the corridor ahead
            // rather than walking straight at the player.
            float offset = (0.5f + random.nextFloat() * 0.9f)
                    * (random.nextBoolean() ? 1f : -1f);
            float cos = MathUtils.cos(offset);
            float sin = MathUtils.sin(offset);
            float dirX = forwardX * cos - forwardZ * sin;
            float dirZ = forwardX * sin + forwardZ * cos;

            float distance = SPAWN_MIN + random.nextFloat() * (SPAWN_MAX - SPAWN_MIN);
            float stageX = player.x + dirX * distance;
            float stageZ = player.z + dirZ * distance;

            // The pass runs across the line of sight, so the player sees it side-on.
            float toStageX = stageX - player.x;
            float toStageZ = stageZ - player.z;
            float sideX = -toStageZ;
            float sideZ = toStageX;
            float sideLength = (float) Math.sqrt(sideX * sideX + sideZ * sideZ);
            if (sideLength < 0.001f) {
                continue;
            }
            sideX /= sideLength;
            sideZ /= sideLength;
            if (random.nextBoolean()) {
                sideX = -sideX;
                sideZ = -sideZ;
            }

            float half = (PASS_MIN + random.nextFloat() * (PASS_MAX - PASS_MIN)) * 0.5f;
            float startX = stageX - sideX * half;
            float startZ = stageZ - sideZ * half;
            float endX = stageX + sideX * half;
            float endZ = stageZ + sideZ * half;

            int startCellX = MathUtils.floor(startX);
            int startCellZ = MathUtils.floor(startZ);
            int endCellX = MathUtils.floor(endX);
            int endCellZ = MathUtils.floor(endZ);
            int midCellX = MathUtils.floor(stageX);
            int midCellZ = MathUtils.floor(stageZ);

            if (!standable(startCellX, startCellZ) || !standable(endCellX, endCellZ)
                    || !standable(midCellX, midCellZ)) {
                continue;
            }
            if (gen.hasLineOfSight(startX, startZ, player.x, player.z)
                    || gen.hasLineOfSight(endX, endZ, player.x, player.z)) {
                continue;                          // it would be seen arriving or leaving
            }

            // Plan the walk from where it would appear, and make sure that walk really does come
            // out into the open: the route has to go round the walls, and one that stays hidden the
            // whole way is not worth walking. This is what actually has to be true - the pass must
            // be seen somewhere along the way, not necessarily at its midpoint, which in a corridor
            // two cells wide would almost never be the case.
            position.set(startCellX + 0.5f, gen.floorY(startCellX, startCellZ), startCellZ + 0.5f);
            pathLength = 0;
            pathIndex = 0;
            planPath(endCellX, endCellZ);
            if (!pathCrossesView(player)) {
                continue;
            }

            goalX = endCellX;
            goalZ = endCellZ;
            state = State.STALK;
            passTime = 0f;
            hiddenFor = 0f;
            travelled = 0f;
            seenEver = false;
            walked = 0f;
            pathIndex = 0;
            repathTimer = 0.8f;
            roarTimer = ROAR_MIN + random.nextFloat() * (ROAR_MAX - ROAR_MIN);
            cueTimer = CUE_MIN * 0.6f + random.nextFloat() * CUE_MIN;
            cueCount = 0;
            return true;
        }
        return false;
    }

    /** True if the planned route comes out from behind the walls at some point. */
    private boolean pathCrossesView(Vector3 player) {
        for (int i = 0; i < pathLength; i++) {
            if (gen.hasLineOfSight(pathX[i] + 0.5f, pathZ[i] + 0.5f, player.x, player.z)) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------
    // movement
    // ---------------------------------------------------------------------------------------------

    private void walk(float speed, float delta) {
        if (pathIndex >= pathLength) {
            repathTimer -= delta;
            if (repathTimer <= 0f) {
                planPath(goalX, goalZ);
                repathTimer = 0.8f;
            }
            if (pathIndex >= pathLength) {
                return;                            // arrived at the far side: it just stops walking
            }
        }
        speedNow = speed;
        float beforeX = position.x;
        float beforeZ = position.z;
        followPath(speed * delta);
        travelled += (float) Math.hypot(position.x - beforeX, position.z - beforeZ);
        walked += speed * delta;
    }

    private void followPath(float step) {
        int guard = 0;
        while (pathIndex < pathLength && guard++ < 8) {
            float tx = pathX[pathIndex] + 0.5f;
            float tz = pathZ[pathIndex] + 0.5f;
            float dx = tx - position.x;
            float dz = tz - position.z;
            float d = (float) Math.sqrt(dx * dx + dz * dz);
            if (d < 0.08f) {
                pathIndex++;
                continue;
            }
            if (step > d) {
                step -= d;
                position.x = tx;
                position.z = tz;
                pathIndex++;
                continue;
            }
            if (d > 0.0001f) {
                position.x += dx / d * step;
                position.z += dz / d * step;
                yaw = MathUtils.atan2(dx, dz);
            }
            return;
        }
        speedNow *= 0.5f;
    }

    /**
     * Breadth-first search over the cell grid, from the entity's cell to the goal. The search is
     * bounded, which is fine: the goal is never more than a couple of rooms away, and an unbounded
     * search of an infinite level would not terminate.
     */
    private void planPath(int goalCellX, int goalCellZ) {
        pathLength = 0;
        pathIndex = 0;

        int startX = MathUtils.floor(position.x);
        int startZ = MathUtils.floor(position.z);

        int gx = MathUtils.clamp(goalCellX - startX, -WINDOW + 1, WINDOW - 1);
        int gz = MathUtils.clamp(goalCellZ - startZ, -WINDOW + 1, WINDOW - 1);

        java.util.Arrays.fill(cameFrom, -1);
        int head = 0;
        int tail = 0;
        int start = index(0, 0);
        cameFrom[start] = start;
        frontier[tail++] = start;

        int best = start;
        float bestScore = Float.MAX_VALUE;

        while (head < tail && head < SEARCH_BUDGET) {
            int cell = frontier[head++];
            int cx = cellX(cell);
            int cz = cellZ(cell);
            float score = (cx - gx) * (cx - gx) + (cz - gz) * (cz - gz);
            if (score < bestScore) {
                bestScore = score;
                best = cell;
            }
            if (cx == gx && cz == gz) {
                best = cell;
                break;
            }
            for (int dir = 0; dir < 4; dir++) {
                int nx = cx + (dir == 0 ? 1 : dir == 1 ? -1 : 0);
                int nz = cz + (dir == 2 ? 1 : dir == 3 ? -1 : 0);
                if (nx <= -WINDOW || nx >= WINDOW || nz <= -WINDOW || nz >= WINDOW) {
                    continue;
                }
                int ni = index(nx, nz);
                if (cameFrom[ni] != -1) {
                    continue;
                }
                int wx = startX + nx;
                int wz = startZ + nz;
                if (!walkable(wx, wz) && !(nx == gx && nz == gz)) {
                    continue;
                }
                if (!canStep(startX + cx, startZ + cz, wx, wz)) {
                    continue;
                }
                cameFrom[ni] = cell;
                frontier[tail++] = ni;
            }
        }

        // Walk the chain back to the start and store it forwards.
        int n = 0;
        int cell = best;
        while (cell != start && n < MAX_PATH) {
            reversed[n++] = cell;
            cell = cameFrom[cell];
            if (cell == -1) {
                return;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            int c = reversed[i];
            pathX[pathLength] = startX + cellX(c);
            pathZ[pathLength] = startZ + cellZ(c);
            pathLength++;
        }
    }

    private boolean standable(int cx, int cz) {
        return gen.isOpenSpace(cx, cz) && gen.isSolidGround(cx, cz) && !gen.isHole(cx, cz);
    }

    /**
     * The cheaper test used inside the route search, where it runs thousands of times: free of
     * walls and not over a hole. It does not insist on solid ground all round - the search would
     * rather route past the rim of a pit than fail to route at all.
     */
    private boolean walkable(int cx, int cz) {
        return gen.isOpenSpace(cx, cz) && !gen.isHole(cx, cz);
    }

    private float groundAt(float x, float z) {
        return gen.floorY(MathUtils.floor(x), MathUtils.floor(z));
    }

    public float distance(Vector3 player) {
        float dx = player.x - position.x;
        float dz = player.z - position.z;
        return (float) Math.sqrt(dx * dx + dz * dz);
    }

    /** Straight-line test on the cell grid, from where it is standing. */
    public boolean hasLineOfSight(Vector3 player) {
        return gen.hasLineOfSight(position.x, position.z, player.x, player.z);
    }

    /** Would something standing at {@code x,z} see the player? */
    public boolean hasLineOfSight(Vector3 player, float x, float z) {
        return gen.hasLineOfSight(x, z, player.x, player.z);
    }

    /** True if a point sits inside a wall slab. */
    public boolean blocksSight(float x, float z) {
        return gen.blocksSight(x, z);
    }

    /**
     * True if the step between two neighbouring cells is not blocked by a wall and not a drop the
     * thing would have to climb. It walks terraces happily; it does not walk off a ledge.
     */
    private boolean canStep(int fromX, int fromZ, int toX, int toZ) {
        if (Math.abs(gen.floorY(toX, toZ) - gen.floorY(fromX, fromZ)) > 0.6f) {
            return false;
        }
        int dx = toX - fromX;
        int dz = toZ - fromZ;
        if (dx == 1 && dz == 0) {
            return !gen.vWall(fromX + 1, fromZ);
        }
        if (dx == -1 && dz == 0) {
            return !gen.vWall(fromX, fromZ);
        }
        if (dx == 0 && dz == 1) {
            return !gen.hWall(fromX, fromZ + 1);
        }
        if (dx == 0 && dz == -1) {
            return !gen.hWall(fromX, fromZ);
        }
        return false;
    }

    private static int index(int x, int z) {
        return (z + WINDOW) * SIDE + (x + WINDOW);
    }

    private static int cellX(int index) {
        return index % SIDE - WINDOW;
    }

    private static int cellZ(int index) {
        return index / SIDE - WINDOW;
    }
}
