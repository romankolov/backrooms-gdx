package com.backrooms.gdx.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.world.WorldGen;

/**
 * Everything that makes a noise.
 *
 * <p>Three things are happening here. The ambience loops (the fluorescent buzz of the nearest tube,
 * the tape hiss) are mixed from live distances: the buzz is the only room sound, because a constant
 * drone under everything is the fastest way to make a quiet level feel loud. Footsteps - the player's and the
 * entity's - are played with the position they came from, so a step behind you is on the right. And
 * every step throws an echo whose timing is measured from the walls around it (see
 * {@link Acoustics}): in a tight corridor the slap-back is a few tens of milliseconds behind the
 * step, in a hall the tail arrives later, quieter and longer. Same sample, different room.
 */
public final class AudioManager implements Disposable {

    /** Echo taps waiting to be played. A footstep queues at most a handful. */
    private static final int MAX_PENDING = 32;

    private final Assets assets;
    private final WorldGen gen;
    private long buzzId = -1;
    private long tapeId = -1;
    private boolean tapeWanted;
    private float buzzVolume;
    private float targetBuzz;
    private boolean enabled = true;
    private final java.util.Random random = new java.util.Random(7);

    // The echo queue: a sound, and when it is due and how it should be played.
    private final Sound[] pendingSound = new Sound[MAX_PENDING];
    private final float[] pendingDelay = new float[MAX_PENDING];
    private final float[] pendingVolume = new float[MAX_PENDING];
    private final float[] pendingPitch = new float[MAX_PENDING];
    private final float[] pendingPan = new float[MAX_PENDING];
    private int pending;

    private final Acoustics probe = new Acoustics();
    private float listenerX;
    private float listenerZ;
    private float rightX = 1f;
    private float rightZ;

    public AudioManager(Assets assets, WorldGen gen) {
        this.assets = assets;
        this.gen = gen;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            stopLoops();
        }
    }

    /**
     * Tape hiss follows the VHS effect: it is the sound of the tape the picture is coming off, so
     * it is on exactly when the effect is.
     */
    public void setTapeEnabled(boolean on) {
        tapeWanted = on;
        if (!on) {
            stopTape();
        } else if (tapeId == -1 && enabled && assets.tapeHiss != null) {
            tapeId = assets.tapeHiss.loop(0f);
        }
    }

    private void stopTape() {
        if (tapeId != -1 && assets.tapeHiss != null) {
            assets.tapeHiss.stop(tapeId);
        }
        tapeId = -1;
    }

    /** 0..1: raises the hiss while the tape is glitching. */
    public void setTapeLevel(float level, float glitch) {
        if (tapeId == -1) {
            return;
        }
        assets.tapeHiss.setVolume(tapeId, MathUtils.clamp(0.035f + 0.09f * glitch, 0f, 0.4f) * level);
    }

    /**
     * @param yaw the listener's heading, radians (the same convention as {@code Player.yaw})
     */
    public void update(float delta, float x, float z, float yaw) {
        listenerX = x;
        listenerZ = z;
        // The player's right-hand vector, for panning.
        rightX = MathUtils.cos(yaw);
        rightZ = -MathUtils.sin(yaw);
        updateEchoes(delta);
        if (!enabled) {
            return;
        }
        float nearest = nearestFixture(x, z);
        targetBuzz = nearest < 0f ? 0f : MathUtils.clamp(1f - nearest / 13f, 0f, 1f);
        targetBuzz = targetBuzz * targetBuzz * 0.34f;
        buzzVolume += (targetBuzz - buzzVolume) * Math.min(1f, delta * 4f);

        if (assets.buzz != null) {
            if (buzzId == -1 && buzzVolume > 0.01f) {
                buzzId = assets.buzz.loop(Math.min(1f, buzzVolume), 0.97f + random.nextFloat() * 0.06f, 0f);
            }
            if (buzzId != -1) {
                assets.buzz.setVolume(buzzId, Math.min(1f, buzzVolume));
                if (buzzVolume <= 0.005f) {
                    assets.buzz.stop(buzzId);
                    buzzId = -1;
                }
            }
        }
    }

    /** Backwards-compatible overload for callers that do not track a heading. */
    public void update(float delta, float x, float z) {
        update(delta, x, z, 0f);
    }

    /** Distance to the closest working ceiling fixture, or -1 when there is none nearby. */
    private float nearestFixture(float x, float z) {
        float best = -1f;
        int i0 = WorldGen.fixtureIndex(x - 13f) - 1;
        int i1 = WorldGen.fixtureIndex(x + 13f) + 1;
        int j0 = WorldGen.fixtureIndex(z - 13f) - 1;
        int j1 = WorldGen.fixtureIndex(z + 13f) + 1;
        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                int cx = WorldGen.fixtureCell(i);
                int cz = WorldGen.fixtureCell(j);
                if (!gen.fixtureAt(cx, cz) || !gen.fixtureAlive(cx, cz)) {
                    continue;
                }
                float dx = cx + 0.5f - x;
                float dz = cz + 0.5f - z;
                float d = (float) Math.sqrt(dx * dx + dz * dz);
                if (best < 0f || d < best) {
                    best = d;
                }
            }
        }
        return best;
    }

    // ---------------------------------------------------------------------------------------------
    // footsteps
    // ---------------------------------------------------------------------------------------------

    /** The player's own step: centred, with the room's answer behind it. */
    public void footstep(boolean sprint, float x, float z) {
        if (!enabled) {
            return;
        }
        Sound sound = assets.footsteps[random.nextInt(assets.footsteps.length)];
        if (sound == null) {
            return;
        }
        float volume = sprint ? 0.42f : 0.3f;
        float pitch = 0.92f + random.nextFloat() * 0.16f;
        float pan = (random.nextFloat() - 0.5f) * 0.35f;
        sound.play(volume, pitch, pan);
        // The player is standing in the space that answers, so probe where the step landed.
        scheduleEchoes(sound, probeAt(x, z), volume * 0.5f, pitch * 0.94f, pan, 3);
    }

    public void landing(float strength) {
        if (enabled && assets.footsteps.length > 0) {
            Sound sound = assets.footsteps[0];
            sound.play(MathUtils.clamp(0.5f * strength + 0.15f, 0f, 1f), 0.72f, 0f);
            scheduleEchoes(sound, probeAt(listenerX, listenerZ), 0.35f * strength, 0.7f, 0f, 4);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // echoes
    // ---------------------------------------------------------------------------------------------

    private Acoustics probeAt(float x, float z) {
        return Acoustics.probe(gen, x, z, probe);
    }

    /**
     * Queues the room's answer: a couple of discrete early reflections off the nearest walls, then a
     * diffuse tail whose length is the size of the space. Everything is scheduled, not sampled -
     * libGDX cannot delay a Sound, but it can start one a few milliseconds later.
     */
    private void scheduleEchoes(Sound sound, Acoustics space, float volume, float pitch, float pan,
                                int maxTaps) {
        if (sound == null || volume < 0.006f) {
            return;
        }
        int taps = 0;

        // Early reflections: the two closest surfaces, panned to the side they are on.
        int first = -1;
        int second = -1;
        float firstDistance = Float.MAX_VALUE;
        float secondDistance = Float.MAX_VALUE;
        for (int i = 0; i < Acoustics.RAYS; i++) {
            float d = space.distance[i];
            if (d < firstDistance) {
                secondDistance = firstDistance;
                second = first;
                firstDistance = d;
                first = i;
            } else if (d < secondDistance) {
                secondDistance = d;
                second = i;
            }
        }
        if (first >= 0 && maxTaps > 0) {
            queueRay(sound, space, first, volume, pitch, 1f);
            taps++;
        }
        if (second >= 0 && taps < maxTaps && secondDistance < Acoustics.MAX_RANGE) {
            queueRay(sound, space, second, volume, pitch, 0.6f);
            taps++;
        }

        // The diffuse tail: the room ringing, later and darker the bigger it is.
        float tail = space.tailDelay();
        float tailGain = space.tailGain() * volume;
        queue(sound, tail, tailGain, pitch * 0.82f, pan * 0.5f);
        taps++;
        if (taps < maxTaps && tailGain > 0.02f) {
            queue(sound, tail * 1.9f, tailGain * 0.55f, pitch * 0.72f, pan * 0.3f);
            taps++;
        }
        if (taps < maxTaps && tailGain > 0.05f) {
            queue(sound, tail * 3.1f, tailGain * 0.3f, pitch * 0.64f, 0f);
        }
    }

    /** Queues the reflection of one ray: delayed by its two-way path, panned to its side. */
    private void queueRay(Sound sound, Acoustics space, int ray, float volume, float pitch,
                          float loudness) {
        float metres = space.distance[ray];
        if (metres >= Acoustics.MAX_RANGE) {
            return;
        }
        float delay = MathUtils.clamp(Acoustics.echoDelay(metres), 0.010f, 0.14f);
        float gain = volume * loudness * 0.55f / (1f + metres * 0.35f);
        float side = Acoustics.dirX(ray) * rightX + Acoustics.dirZ(ray) * rightZ;
        queue(sound, delay, gain, pitch, MathUtils.clamp(side * 0.8f, -0.85f, 0.85f));
    }

    private void queue(Sound sound, float delay, float volume, float pitch, float pan) {
        if (pending >= MAX_PENDING || volume < 0.004f) {
            return;
        }
        int slot = pending++;
        pendingSound[slot] = sound;
        pendingDelay[slot] = delay;
        pendingVolume[slot] = volume;
        pendingPitch[slot] = pitch;
        pendingPan[slot] = pan;
    }

    private void updateEchoes(float delta) {
        for (int i = pending - 1; i >= 0; i--) {
            pendingDelay[i] -= delta;
            if (pendingDelay[i] > 0f) {
                continue;
            }
            if (enabled) {
                pendingSound[i].play(MathUtils.clamp(pendingVolume[i], 0f, 1f),
                        pendingPitch[i], pendingPan[i]);
            }
            pending--;
            pendingSound[i] = pendingSound[pending];
            pendingDelay[i] = pendingDelay[pending];
            pendingVolume[i] = pendingVolume[pending];
            pendingPitch[i] = pendingPitch[pending];
            pendingPan[i] = pendingPan[pending];
        }
    }

    /** Number of taps waiting to play; exposed for the tests and the perf line. */
    public int pendingEchoes() {
        return pending;
    }

    private float panFor(float dx, float dz, float distance) {
        if (distance < 0.001f) {
            return 0f;
        }
        float side = (dx * rightX + dz * rightZ) / distance;
        return MathUtils.clamp(side * 0.9f, -1f, 1f);
    }

    private void stopLoops() {
        if (buzzId != -1 && assets.buzz != null) {
            assets.buzz.stop(buzzId);
            buzzId = -1;
        }
        stopTape();
        pending = 0;
    }

    @Override
    public void dispose() {
        stopLoops();
    }

    /** Convenience for logging. */
    public static void log(String message) {
        Gdx.app.log("Audio", message);
    }
}
