package com.backrooms.gdx.entity;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.world.WorldGen;

/**
 * The thing in the corridors: where it is, what it is doing, and how the player escapes it.
 *
 * <p>This class is pure logic - it never touches a GL context, which is what makes it testable. The
 * visuals live in {@code EntityRenderer}.
 *
 * <p>It works in three beats, and the beats are what make it frightening rather than merely
 * annoying:
 *
 * <ol>
 *   <li><b>Absent.</b> Most of the time there is nothing. The level is empty, and the emptiness is
 *       the point.</li>
 *   <li><b>Stalking.</b> It appears a few rooms away, out of sight, and closes slowly - slower than
 *       the player walks. It does not need to catch up yet. It only needs to keep finding you, and
 *       every second it can see you it gets a little bolder. This phase is the warning.</li>
 *   <li><b>Hunting.</b> Once it has watched you for long enough it stops being patient: it comes at
 *       a run, faster than a walk and slower than a sprint. It cannot track you through walls, so
 *       breaking line of sight for a few seconds loses it - and losing it is the only way out.</li>
 * </ol>
 *
 * <p>Everything spatial is done on the generator's cell grid, so the AI never needs the streamed
 * world to be loaded: it can think about cells the renderer has not built yet.
 */
public final class EntityController {

    public enum State {
        /** Not in the world right now. */
        ABSENT,
        /** Drifting toward the player, keeping quiet. */
        STALK,
        /** Coming straight at the player; must be lost. */
        HUNT
    }

    /** How close it gets before it is allowed to start stalking. */
    private static final float SPAWN_MIN = 12f;
    private static final float SPAWN_MAX = 26f;
    /** Beyond this it gives up and forgets where the player is. */
    private static final float ABANDON_RANGE = 40f;
    /** Seconds of being seen before stalking escalates into a hunt. */
    private static final float STALK_TO_HUNT = 18f;
    /** Seeing the player counts less while stalking: it has to get close to learn enough. */
    private static final float STALK_LEARN_RANGE = 17f;
    /** Seconds out of sight, while hunted, before it loses the trail and leaves. */
    private static final float LOSE_TIME = 5.5f;
    /** Distance at which it grabs the player. */
    private static final float CAPTURE_DISTANCE = 0.9f;

    public static final float STALK_SPEED = 1.25f;
    public static final float HUNT_SPEED = 3.95f;
    /** A short burst when it is nearly on top of the player, so standing still is not safe. */
    public static final float LUNGE_SPEED = 4.6f;
    private static final float LUNGE_RANGE = 5.5f;

    private static final int WINDOW = 40;              // cells searched in each direction
    private static final int SIDE = WINDOW * 2 + 1;
    private static final int MAX_PATH = 384;

    private final WorldGen gen;
    private final Random random;

    public final Vector3 position = new Vector3();
    public float yaw;

    private State state = State.ABSENT;
    private boolean enabled = true;
    private float dormant;
    private float stalkTime;
    private float lostTime;
    private float bobPhase;
    private float speedNow;
    private boolean captured;
    private float presence;

    // Breadth-first search scratch space. Allocated once: this runs on the render thread.
    private final int[] frontier = new int[SIDE * SIDE];
    private final int[] cameFrom = new int[SIDE * SIDE];
    private final int[] pathX = new int[MAX_PATH];
    private final int[] pathZ = new int[MAX_PATH];
    private int pathLength;
    private int pathIndex;
    private float repathTimer;

    public EntityController(WorldGen gen, long randomSeed) {
        this.gen = gen;
        this.random = new Random(randomSeed);
        this.dormant = 22f + random.nextFloat() * 26f;
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

    /** Seconds of stalking accumulated, for the HUD/debug line. */
    public float stalkTime() {
        return stalkTime;
    }

    /** Seconds since it last saw the player while hunting. */
    public float lostTime() {
        return lostTime;
    }

    /**
     * 0..1: how much of a presence it is right now, by distance and by intent. The camera uses this
     * to start tearing the tape long before the thing is visible.
     */
    public float presence() {
        return presence;
    }

    /** Set for exactly one update when it reaches the player. The caller is expected to react. */
    public boolean consumeCapture() {
        boolean was = captured;
        captured = false;
        return was;
    }

    /** Puts it into the world at a given spot - used by tests and by the screenshot job. */
    public void forceSpawn(float x, float z, State newState) {
        position.set(x, gen.floorY(MathUtils.floor(x), MathUtils.floor(z)), z);
        state = newState;
        dormant = 0f;
        stalkTime = newState == State.HUNT ? STALK_TO_HUNT : 0f;
        lostTime = 0f;
        pathLength = 0;
        pathIndex = 0;
        repathTimer = 0f;
    }

    /** Removes it from the world and sets how long until it may come back. */
    public void vanish(float cooldown) {
        state = State.ABSENT;
        dormant = cooldown;
        pathLength = 0;
        pathIndex = 0;
        speedNow = 0f;
        // presence is left alone: the tape should keep tearing for a moment after it is gone.
    }

    /**
     * @param delta  seconds
     * @param player the player's feet position
     */
    public void update(float delta, Vector3 player) {
        speedNow = 0f;
        float dist = distance(player);

        if (!enabled) {
            // Still let the picture calm down after a toggle: the tape should not stay torn
            // because the player switched the encounter off mid-scare.
            if (presence > 0.001f) {
                presence += (0f - presence) * Math.min(1f, delta * 2.5f);
            }
            return;
        }

        switch (state) {
            case ABSENT:
                dormant -= delta;
                if (dormant <= 0f && !spawn(player)) {
                    dormant = 2f;                  // nowhere good to appear yet; try again shortly
                }
                break;

            case STALK: {
                if (dist > ABANDON_RANGE) {
                    vanish(18f + random.nextFloat() * 25f);
                    break;
                }
                boolean seen = dist < STALK_LEARN_RANGE && hasLineOfSight(player);
                // Being seen teaches it quickly; merely being close teaches it slowly. Either way
                // the clock is running, which is what makes the quiet phase feel like a countdown.
                stalkTime += delta * (seen ? 1f : 0.22f);
                if (stalkTime >= STALK_TO_HUNT) {
                    state = State.HUNT;
                    lostTime = 0f;
                    break;
                }
                walkToward(player, STALK_SPEED, delta);
                break;
            }

            case HUNT: {
                boolean seen = hasLineOfSight(player);
                lostTime = seen ? 0f : lostTime + delta;
                if (lostTime >= LOSE_TIME) {
                    vanish(14f + random.nextFloat() * 22f);
                    break;
                }
                if (dist > ABANDON_RANGE * 1.6f) {
                    vanish(20f + random.nextFloat() * 25f);
                    break;
                }
                float speed = dist < LUNGE_RANGE && seen ? LUNGE_SPEED : HUNT_SPEED;
                walkToward(player, speed, delta);
                if (distance(player) < CAPTURE_DISTANCE) {
                    captured = true;
                    vanish(28f + random.nextFloat() * 30f);
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
            wanted = near * (state == State.HUNT ? 1f : 0.55f);
        }
        // Ease so a disappearance does not snap the tape back to clean.
        presence += (wanted - presence) * Math.min(1f, delta * 2.5f);
    }

    public float bob() {
        return (float) Math.sin(bobPhase) * 0.03f;
    }

    // ---------------------------------------------------------------------------------------------
    // placement
    // ---------------------------------------------------------------------------------------------

    /** Tries to place it out of sight, a few rooms away. Returns false if nowhere suitable. */
    private boolean spawn(Vector3 player) {
        int px = MathUtils.floor(player.x);
        int pz = MathUtils.floor(player.z);
        for (int attempt = 0; attempt < 90; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0;
            float dist = SPAWN_MIN + random.nextFloat() * (SPAWN_MAX - SPAWN_MIN);
            int cx = px + Math.round((float) Math.cos(angle) * dist);
            int cz = pz + Math.round((float) Math.sin(angle) * dist);
            if (!standable(cx, cz) || blocksSight(cx + 0.5f, cz + 0.5f)) {
                continue;
            }
            // It should not materialise in front of the player: on the first few attempts insist on
            // a broken line of sight, later attempts only refuse a body-on-the-player spawn.
            if (attempt < 70 && hasLineOfSight(player, cx + 0.5f, cz + 0.5f)) {
                continue;
            }
            forceSpawn(cx + 0.5f, cz + 0.5f, State.STALK);
            return true;
        }
        return false;
    }

    private boolean standable(int cx, int cz) {
        return gen.isOpenSpace(cx, cz) && gen.isSolidGround(cx, cz) && !gen.isHole(cx, cz);
    }

    private float groundAt(float x, float z) {
        return gen.floorY(MathUtils.floor(x), MathUtils.floor(z));
    }

    public float distance(Vector3 player) {
        float dx = player.x - position.x;
        float dz = player.z - position.z;
        return (float) Math.sqrt(dx * dx + dz * dz);
    }

    /** Straight-line test on the cell grid: walls are thin, so this only needs the wall planes. */
    public boolean hasLineOfSight(Vector3 player) {
        return hasLineOfSight(player, position.x, position.z);
    }

    /** Would something standing at {@code x,z} see the player? */
    public boolean hasLineOfSight(Vector3 player, float x, float z) {
        // Only the horizontal plane matters: the level is one storey and a pit is shallow, so the
        // eyes see over anything that is not a wall.
        float dx = player.x - x;
        float dz = player.z - z;
        float dist = (float) Math.sqrt(dx * dx + dz * dz);
        if (dist < 0.001f) {
            return true;
        }
        int steps = Math.max(2, Math.round(dist / 0.18f));
        for (int i = 1; i < steps; i++) {
            float t = i / (float) steps;
            if (blocksSight(x + dx * t, z + dz * t)) {
                return false;
            }
        }
        return true;
    }

    /** True if a point sits inside a wall slab. */
    public boolean blocksSight(float x, float z) {
        int cx = MathUtils.floor(x);
        int cz = MathUtils.floor(z);
        float fx = x - cx;
        float fz = z - cz;
        float t = WorldGen.WALL_HALF_THICKNESS * 1.2f;
        if (fx <= t && gen.vWall(cx, cz)) {
            return true;
        }
        if (fx >= 1f - t && gen.vWall(cx + 1, cz)) {
            return true;
        }
        if (fz <= t && gen.hWall(cx, cz)) {
            return true;
        }
        return fz >= 1f - t && gen.hWall(cx, cz + 1);
    }

    // ---------------------------------------------------------------------------------------------
    // movement
    // ---------------------------------------------------------------------------------------------

    private void walkToward(Vector3 player, float speed, float delta) {
        speedNow = speed;
        repathTimer -= delta;
        if (repathTimer <= 0f || pathLength == 0) {
            planPath(MathUtils.floor(player.x), MathUtils.floor(player.z));
            repathTimer = 0.4f + random.nextFloat() * 0.2f;
        }
        followPath(speed * delta, delta);
    }

    private void followPath(float step, float delta) {
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
        // Out of waypoints: face the player, which keeps it looking at you while it closes the gap.
        speedNow *= 0.5f;
    }

    /**
     * Breadth-first search over the cell grid, from the entity's cell to (as close as it can get
     * to) the player's. The search is bounded, which is fine: the entity is never more than a
     * couple of rooms behind, and an unbounded search of an infinite level would not terminate.
     */
    private void planPath(int goalX, int goalZ) {
        pathLength = 0;
        pathIndex = 0;

        int startX = MathUtils.floor(position.x);
        int startZ = MathUtils.floor(position.z);

        int gx = MathUtils.clamp(goalX - startX, -WINDOW + 1, WINDOW - 1);
        int gz = MathUtils.clamp(goalZ - startZ, -WINDOW + 1, WINDOW - 1);

        java.util.Arrays.fill(cameFrom, -1);
        int head = 0;
        int tail = 0;
        int start = index(0, 0);
        cameFrom[start] = start;
        frontier[tail++] = start;

        int best = start;
        float bestScore = Float.MAX_VALUE;

        while (head < tail) {
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
                if (!standable(wx, wz) && !(nx == gx && nz == gz)) {
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
        int[] reversed = new int[MAX_PATH];
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

    /** True if the step between two neighbouring cells is not blocked by a wall. */
    private boolean canStep(int fromX, int fromZ, int toX, int toZ) {
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
