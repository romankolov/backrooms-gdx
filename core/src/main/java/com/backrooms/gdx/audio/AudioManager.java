package com.backrooms.gdx.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.util.Hash;
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
                buzzId = assets.buzz.loop(Math.min(1f, buzzVolume), 0.97f + random.nextFloat() * 0.06f);
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
        int spacing = WorldGen.LIGHT_SPACING;
        int ci = Hash.floorInt(x);
        int cj = Hash.floorInt(z);
        float best = -1f;
        int i0 = Hash.floorDiv(ci - 13, spacing);
        int i1 = Hash.floorDiv(ci + 13, spacing);
        int j0 = Hash.floorDiv(cj - 13, spacing);
        int j1 = Hash.floorDiv(cj + 13, spacing);
        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                int cx = i * spacing;
                int cz = j * spacing;
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
