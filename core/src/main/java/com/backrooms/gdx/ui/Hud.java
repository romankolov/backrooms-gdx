package com.backrooms.gdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.GameSettings;
import com.backrooms.gdx.input.TouchControls;

/** All 2D drawing: crosshair, touch controls, debug readout and the pause menu. */
public final class Hud {

    public static final int BTN_RESUME = 0;
    public static final int BTN_TORCH = 1;
    public static final int BTN_AUDIO = 2;
    public static final int BTN_DISTANCE = 3;
    public static final int BTN_SENSITIVITY = 4;
    public static final int BTN_VHS = 5;
    public static final int BTN_QUIT = 6;
    public static final int BTN_COUNT = 7;

    private static final Color PANEL = new Color(0.06f, 0.055f, 0.04f, 0.86f);
    private static final Color PANEL_EDGE = new Color(0.85f, 0.80f, 0.55f, 0.55f);
    private static final Color TEXT = new Color(0.96f, 0.93f, 0.78f, 1f);
    private static final Color TEXT_DIM = new Color(0.72f, 0.69f, 0.55f, 1f);

    private final Assets assets;
    private final Rectangle[] menu = new Rectangle[BTN_COUNT];
    private final String[] labels = new String[BTN_COUNT];
    private final GlyphLayout layout = new GlyphLayout();
    private int width = 1;
    private int height = 1;
    /** Measured a moment ago by the game, shown in the pause menu so a phone can be checked. */
    private String stats = "";

    public Hud(Assets assets) {
        this.assets = assets;
        for (int i = 0; i < BTN_COUNT; i++) {
            menu[i] = new Rectangle();
        }
    }

    /** One line of numbers under the pause menu, so performance can be read off the device. */
    public void setStats(String stats) {
        this.stats = stats == null ? "" : stats;
    }

    public void resize(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        float panelWidth = Math.min(width * 0.56f, 520f);
        float rowHeight = Math.min(height * 0.085f, 58f);
        float gap = rowHeight * 0.28f;
        float x = (width - panelWidth) * 0.5f;
        float total = BTN_COUNT * (rowHeight + gap);
        float y = height * 0.5f + total * 0.5f - rowHeight;
        for (int i = 0; i < BTN_COUNT; i++) {
            menu[i].set(x, y, panelWidth, rowHeight);
            y -= rowHeight + gap;
        }
    }

    public void refreshLabels(GameSettings settings) {
        labels[BTN_RESUME] = "RESUME";
        labels[BTN_TORCH] = "TORCH: " + (settings.torch ? "ON" : "OFF");
        labels[BTN_AUDIO] = "AUDIO: " + (settings.audio ? "ON" : "OFF");
        labels[BTN_DISTANCE] = "VIEW DISTANCE: " + settings.renderDistance;
        labels[BTN_SENSITIVITY] = "SENSITIVITY: "
                + String.format(java.util.Locale.US, "%.1f", settings.sensitivity);
        labels[BTN_VHS] = "VHS LOOK: " + (settings.vhs ? "ON" : "OFF");
        labels[BTN_QUIT] = "QUIT";
    }

    public int hitMenu(float x, float y) {
        for (int i = 0; i < BTN_COUNT; i++) {
            if (menu[i].contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    public Rectangle menuButton(int id) {
        return menu[id];
    }

    private void textScale(float scale) {
        assets.font.getData().setScale(scale);
        assets.fontBold.getData().setScale(scale);
    }

    private float scale() {
        return Math.max(0.55f, Math.min(1.35f, height / 1100f));
    }

    // ---------------------------------------------------------------------------------------------
    // in game
    // ---------------------------------------------------------------------------------------------

    public void drawGameplay(TouchControls controls, boolean torchOn, boolean debug,
                             String debugLine1, String debugLine2) {
        ShapeRenderer shapes = assets.shapes;
        SpriteBatch batch = assets.batch;
        float s = scale();
        textScale(s);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // joystick
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        if (controls.stickActive) {
            float r = controls.stickRadius();
            shapes.setColor(0.9f, 0.87f, 0.7f, 0.10f);
            shapes.circle(controls.stickOriginX, controls.stickOriginY, r, 48);
            shapes.setColor(0.95f, 0.92f, 0.75f, 0.30f);
            shapes.circle(controls.stickOriginX, controls.stickOriginY, r * 0.42f, 36);
            shapes.setColor(0.98f, 0.95f, 0.8f, 0.55f);
            shapes.circle(controls.stickOriginX + controls.stickOffsetX(),
                    controls.stickOriginY + controls.stickOffsetY(), r * 0.24f, 28);
        }

        if (!controls.isDesktop()) {
            drawTouchButton(shapes, controls.button(TouchControls.BTN_PAUSE));
            drawTouchButton(shapes, controls.button(TouchControls.BTN_TORCH));
            drawTouchButton(shapes, controls.button(TouchControls.BTN_SPRINT));
            drawTouchButton(shapes, controls.button(TouchControls.BTN_JUMP));
        }

        // crosshair
        float cx = width * 0.5f;
        float cy = height * 0.5f;
        shapes.setColor(1f, 0.98f, 0.85f, 0.35f);
        shapes.rect(cx - 6f * s, cy - 1f * s, 12f * s, 2f * s);
        shapes.rect(cx - 1f * s, cy - 6f * s, 2f * s, 12f * s);
        shapes.end();

        if (debug) {
            // A dark strip keeps the readout legible over bright wallpaper.
            float lh = assets.font.getLineHeight() * s;
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(0f, 0f, 0f, 0.35f);
            shapes.rect(0f, height - lh * 2.6f - 10f * s, width, lh * 2.6f + 10f * s);
            shapes.end();
        }

        batch.begin();
        if (!controls.isDesktop()) {
            drawButtonLabel(batch, controls.button(TouchControls.BTN_PAUSE), "II");
            drawButtonLabel(batch, controls.button(TouchControls.BTN_TORCH), torchOn ? "F" : "f");
            drawButtonLabel(batch, controls.button(TouchControls.BTN_SPRINT), ">>");
            drawButtonLabel(batch, controls.button(TouchControls.BTN_JUMP), "^");
        }
        if (debug) {
            BitmapFont font = assets.font;
            font.setColor(TEXT_DIM);
            float line = font.getLineHeight() * s * 1.2f;
            font.draw(batch, debugLine1, 18f * s, height - 12f * s);
            if (debugLine2 != null) {
                font.draw(batch, debugLine2, 18f * s, height - 12f * s - line);
            }
        }
        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawTouchButton(ShapeRenderer shapes, Rectangle r) {
        shapes.setColor(0.9f, 0.86f, 0.68f, 0.13f);
        shapes.rect(r.x, r.y, r.width, r.height);
        shapes.setColor(0.9f, 0.86f, 0.68f, 0.35f);
        shapes.rect(r.x, r.y, r.width, 2f);
        shapes.rect(r.x, r.y + r.height - 2f, r.width, 2f);
        shapes.rect(r.x, r.y, 2f, r.height);
        shapes.rect(r.x + r.width - 2f, r.y, 2f, r.height);
    }

    private void drawButtonLabel(SpriteBatch batch, Rectangle r, String label) {
        BitmapFont font = assets.fontBold;
        font.setColor(0.95f, 0.92f, 0.75f, 0.75f);
        layout.setText(font, label);
        font.draw(batch, label, r.x + (r.width - layout.width) * 0.5f,
                r.y + (r.height + layout.height) * 0.5f);
    }

    public void drawFade(float alpha) {
        if (alpha <= 0.002f) {
            return;
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        assets.shapes.begin(ShapeRenderer.ShapeType.Filled);
        assets.shapes.setColor(0f, 0f, 0f, Math.min(1f, alpha));
        assets.shapes.rect(0f, 0f, width, height);
        assets.shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    // ---------------------------------------------------------------------------------------------
    // pause menu
    // ---------------------------------------------------------------------------------------------

    public void drawPause(GameSettings settings) {
        float s = scale();
        textScale(s);
        refreshLabels(settings);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ShapeRenderer shapes = assets.shapes;
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.55f);
        shapes.rect(0f, 0f, width, height);
        for (int i = 0; i < BTN_COUNT; i++) {
            Rectangle r = menu[i];
            shapes.setColor(PANEL);
            shapes.rect(r.x, r.y, r.width, r.height);
            shapes.setColor(PANEL_EDGE);
            shapes.rect(r.x, r.y, r.width, 2f);
            shapes.rect(r.x, r.y + r.height - 2f, r.width, 2f);
        }
        shapes.end();

        SpriteBatch batch = assets.batch;
        batch.begin();
        BitmapFont font = assets.fontBold;
        font.setColor(TEXT);
        layout.setText(font, "PAUSED");
        font.draw(batch, "PAUSED", (width - layout.width) * 0.5f, menu[0].y + menu[0].height
                + 70f * s);

        BitmapFont row = assets.font;
        for (int i = 0; i < BTN_COUNT; i++) {
            Rectangle r = menu[i];
            row.setColor(i == BTN_QUIT ? TEXT_DIM : TEXT);
            layout.setText(row, labels[i]);
            row.draw(batch, labels[i], r.x + (r.width - layout.width) * 0.5f,
                    r.y + (r.height + layout.height) * 0.5f);
        }
        assets.font.setColor(TEXT_DIM);
        float hint = 26f * s;
        assets.font.draw(batch, "Fluorescent buzz and footstep audio are generated procedurally.",
                24f * s, hint);
        assets.font.draw(batch, "Textures: Amini Allight, CC0 1.0  -  built with libGDX",
                24f * s, hint + 24f * s);
        if (!stats.isEmpty()) {
            // Top right of the panel: the numbers a phone should be judged by.
            layout.setText(assets.font, stats);
            assets.font.draw(batch, stats, width - layout.width - 24f * s,
                    menu[0].y + menu[0].height + 70f * s);
        }
        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
