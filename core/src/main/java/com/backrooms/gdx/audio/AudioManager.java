package com.backrooms.gdx.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.world.WorldGen;

/**
 * Footsteps on carpet plus the two ambience loops: the fluorescent buzz of the nearest tube and a
 * low room tone. Both loops are mixed live from the player's distance to the nearest fixture.
 */
public final class AudioManager implements Disposable {

    private final Assets assets;
    private final WorldGen gen;
    private long buzzId = -1;
    private long toneId = -1;
    private long tapeId = -1;
    private boolean tapeWanted;
    private float buzzVolume;
    private float targetBuzz;
    private boolean enabled = true;
    private java.util.Random random = new java.util.Random(7);

    public AudioManager(Assets assets, WorldGen gen) {
        this.assets = assets;
        this.gen = gen;
        if (assets.roomTone != null) {
            toneId = assets.roomTone.loop(0.22f);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            stopLoops();
        } else if (toneId == -1 && assets.roomTone != null) {
            toneId = assets.roomTone.loop(0.22f);
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
        assets.tapeHiss.setVolume(tapeId, MathUtils.clamp(0.10f + 0.18f * glitch, 0f, 0.6f) * level);
    }

    public void update(float delta, float x, float z) {
        if (!enabled) {
            return;
        }
        float nearest = nearestFixture(x, z);
        targetBuzz = nearest < 0f ? 0f : MathUtils.clamp(1f - nearest / 13f, 0f, 1f);
        targetBuzz = targetBuzz * targetBuzz * 0.55f;
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

    public void footstep(boolean sprint) {
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
    }

    public void landing(float strength) {
        if (enabled && assets.footsteps.length > 0) {
            Sound sound = assets.footsteps[0];
            sound.play(MathUtils.clamp(0.5f * strength + 0.15f, 0f, 1f), 0.72f, 0f);
        }
    }

    private void stopLoops() {
        if (buzzId != -1 && assets.buzz != null) {
            assets.buzz.stop(buzzId);
            buzzId = -1;
        }
        if (toneId != -1 && assets.roomTone != null) {
            assets.roomTone.stop(toneId);
            toneId = -1;
        }
        stopTape();
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
