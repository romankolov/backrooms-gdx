package com.backrooms.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

/** Shared UI resources and the handful of sound effects the game uses. */
public final class Assets implements Disposable {

    public final SpriteBatch batch = new SpriteBatch();
    public final ShapeRenderer shapes = new ShapeRenderer();
    public BitmapFont font;
    public BitmapFont fontBold;

    public final Sound[] footsteps = new Sound[4];
    /** The heavy, wet steps of the thing in the corridors. */
    /**
     * The thing's small noises, in the order the controller asks for them: the creak of it leaning
     * on something, its chittering, something being dragged, and its breathing.
     */
    public Sound buzz;
    public Sound tapeHiss;

    public Assets() {
        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"));
        fontBold = new BitmapFont(Gdx.files.internal("ui/font_bold.fnt"));
        font.getData().markupEnabled = false;
        fontBold.getData().markupEnabled = false;
        for (int i = 0; i < footsteps.length; i++) {
            footsteps[i] = Gdx.audio.newSound(Gdx.files.internal("sounds/footstep_" + (i + 1) + ".ogg"));
        }
        try {
            buzz = Gdx.audio.newSound(Gdx.files.internal("sounds/light_buzz.ogg"));
            tapeHiss = Gdx.audio.newSound(Gdx.files.internal("sounds/tape_hiss.ogg"));
        } catch (Exception e) {
            Gdx.app.error("Assets", "Could not load ambience: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        if (font != null) font.dispose();
        if (fontBold != null) fontBold.dispose();
        for (Sound s : footsteps) {
            if (s != null) s.dispose();
        }
        if (buzz != null) buzz.dispose();
        if (tapeHiss != null) tapeHiss.dispose();
    }
}
