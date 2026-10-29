package com.backrooms.gdx.audio;

import com.backrooms.gdx.world.WorldGen;

/**
 * How much a point in the level echoes.
 *
 * <p>A footstep in a bare corridor comes straight back off the walls a few metres away, so the echo
 * is short, bright and attached to the step. In an open hall the reflections arrive from much
 * further out: the delay grows, the tail lengthens and the room sounds big. That difference is the
 * whole of "dynamic according to the walls", and it can be measured cheaply by casting a handful of
 * rays from the listener and looking at where they hit.
 *
 * <p>No allocation: the probe object is reused by {@link #probe}.
 */
public final class Acoustics {

    /** Ray directions: eight around the compass, which is enough to tell a corridor from a hall. */
    public static final int RAYS = 8;
    /** Nothing beyond this matters; the level fades into haze long before it. */
    public static final float MAX_RANGE = 22f;
    /** Speed of sound, metres per second. */
    private static final float SOUND_SPEED = 343f;

    public final float[] distance = new float[RAYS];
    /** Shortest wall distance in any direction: the slap-back off the nearest surface. */
    public float nearest;
    /** Mean distance to the walls: how big the space feels. */
    public float mean;
    /** 0 = pressed against the walls, 1 = a hall where the far wall is out of range. */
    public float openness;

    private static final float[] DIR_X = new float[RAYS];
    private static final float[] DIR_Z = new float[RAYS];

    static {
        for (int i = 0; i < RAYS; i++) {
            double angle = i * Math.PI * 2.0 / RAYS;
            DIR_X[i] = (float) Math.cos(angle);
            DIR_Z[i] = (float) Math.sin(angle);
        }
    }

    public static float dirX(int ray) {
        return DIR_X[ray];
    }

    public static float dirZ(int ray) {
        return DIR_Z[ray];
    }

    /** Measures the space around a point. Returns {@code out}, so callers can chain. */
    public static Acoustics probe(WorldGen gen, float x, float z, Acoustics out) {
        float nearest = MAX_RANGE;
        float sum = 0f;
        for (int i = 0; i < RAYS; i++) {
            float d = gen.wallDistance(x, z, DIR_X[i], DIR_Z[i], MAX_RANGE);
            out.distance[i] = d;
            sum += d;
            if (d < nearest) {
                nearest = d;
            }
        }
        out.nearest = nearest;
        out.mean = sum / RAYS;
        out.derive();
        return out;
    }

    /** Recomputes {@link #openness} from {@link #mean}; split out so it can be tested on its own. */
    public void derive() {
        openness = clamp((mean - 1.5f) / (MAX_RANGE - 1.5f));
    }

    /** Two-way delay to a surface at {@code metres}, in seconds. */
    public static float echoDelay(float metres) {
        return 2f * metres / SOUND_SPEED;
    }

    /**
     * Delay of the diffuse tail, in seconds: how long the room rings for. A tight corridor is dry
     * (about 40 ms), a hall a few rooms wide rings for a quarter of a second.
     */
    public float tailDelay() {
        return 0.035f + 0.26f * openness;
    }

    /** How loud the room is, 0..1: open rooms have more surfaces at a distance to return sound. */
    public float tailGain() {
        return 0.10f + 0.22f * openness;
    }

    private static float clamp(float v) {
        return v < 0f ? 0f : (v > 1f ? 1f : v);
    }
}
