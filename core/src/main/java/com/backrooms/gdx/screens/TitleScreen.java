package com.backrooms.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.BackroomsGame;

/** Title card: an unlit corridor with the level name and a tap prompt. */
public final class TitleScreen extends ScreenAdapter {

    private final BackroomsGame game;
    private final Assets assets;
    private final GlyphLayout layout = new GlyphLayout();
    private final InputAdapter input = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            enter();
            return true;
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK) {
                Gdx.app.exit();
                return true;
            }
            enter();
            return true;
        }
    };
    private float time;
    private boolean entering;
    private float fade;

    public TitleScreen(BackroomsGame game) {
        this.game = game;
        this.assets = game.assets;
    }

    private void enter() {
        if (entering) {
            return;
        }
        entering = true;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        time += delta;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Gdx.gl.glClearColor(0.05f, 0.045f, 0.03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Warm glow of a fluorescent tube behind the title.
        ShapeRenderer shapes = assets.shapes;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        float cx = width * 0.5f;
        float cy = height * 0.62f;
        for (int i = 10; i >= 1; i--) {
            float t = i / 10f;
            shapes.setColor(0.55f * (1f - t) + 0.10f, 0.50f * (1f - t) + 0.09f,
                    0.28f * (1f - t) + 0.05f, 0.16f);
            shapes.circle(cx, cy, Math.min(width, height) * 0.55f * t, 64);
        }
        shapes.end();

        SpriteBatch batch = assets.batch;
        batch.begin();
        float scale = Math.min(width / 1400f, height / 800f);
        BitmapFont bold = assets.fontBold;
        BitmapFont font = assets.font;

        bold.getData().setScale(scale * 1.5f);
        bold.setColor(0.99f, 0.95f, 0.74f, 0.95f);
        layout.setText(bold, "THE BACKROOMS");
        bold.draw(batch, "THE BACKROOMS", cx - layout.width * 0.5f, cy + layout.height * 0.5f);

        font.getData().setScale(scale * 0.72f);
        font.setColor(0.85f, 0.80f, 0.58f, 0.85f);
        layout.setText(font, "LEVEL 0  -  600 MILLION KM2 OF NEGLECTED OFFICE SPACE");
        font.draw(batch, "LEVEL 0  -  600 MILLION KM2 OF NEGLECTED OFFICE SPACE",
                cx - layout.width * 0.5f, cy - 48f * scale);

        float pulse = 0.55f + 0.35f * MathUtils.sin(time * 2.4f);
        font.getData().setScale(scale * 0.95f);
        font.setColor(0.98f, 0.94f, 0.72f, pulse);
        layout.setText(font, "TAP TO ENTER");
        font.draw(batch, "TAP TO ENTER", cx - layout.width * 0.5f, height * 0.26f);

        font.getData().setScale(scale * 0.52f);
        font.setColor(0.62f, 0.59f, 0.44f, 0.9f);
        String controls = Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Desktop
                ? "WASD to walk   -   mouse to look   -   SHIFT to run   -   SPACE to jump   -   F torch"
                : "left thumb to walk   -   right thumb to look   -   right side buttons to run and jump";
        layout.setText(font, controls);
        font.draw(batch, controls, cx - layout.width * 0.5f, height * 0.16f);

        font.setColor(0.45f, 0.43f, 0.33f, 1f);
        String credits = "textures by Amini Allight (CC0)   -   audio synthesised at build time   -   libGDX";
        layout.setText(font, credits);
        font.draw(batch, credits, cx - layout.width * 0.5f, height * 0.08f);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (entering) {
            fade = Math.min(1f, fade + delta * 2.2f);
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(0f, 0f, 0f, fade);
            shapes.rect(0f, 0f, width, height);
            shapes.end();
            if (fade >= 1f) {
                game.startGame();
            }
        }
    }

    @Override
    public void dispose() {
        if (Gdx.input.getInputProcessor() == input) {
            Gdx.input.setInputProcessor(null);
        }
    }
}
