package com.backrooms.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.backrooms.gdx.GameSettings;

/**
 * Touch controls: a floating stick on the left, drag-to-look on the right, and buttons.
 * Keyboard and mouse are supported as well so the game can also be played on a desktop build.
 */
public final class TouchControls implements InputProcessor {

    public static final int BTN_NONE = -1;
    public static final int BTN_PAUSE = 0;
    public static final int BTN_TORCH = 1;
    public static final int BTN_SPRINT = 2;
    public static final int BTN_JUMP = 3;

    public float moveForward;
    public float moveSide;
    public float lookDeltaX;
    public float lookDeltaY;
    public boolean sprint;
    public boolean jumpRequested;
    public int pressedButton = BTN_NONE;

    public boolean stickActive;
    public float stickOriginX;
    public float stickOriginY;
    public float stickX;
    public float stickY;

    private final GameSettings settings;
    private final Rectangle[] buttons = {new Rectangle(), new Rectangle(), new Rectangle(),
            new Rectangle()};
    private int stickPointer = -1;
    private int lookPointer = -1;
    private int sprintPointer = -1;
    private float lastLookX;
    private float lastLookY;
    private float stickRadius;
    private int screenWidth = 1;
    private int screenHeight = 1;
    private boolean desktop;

    public TouchControls(GameSettings settings) {
        this.settings = settings;
        desktop = Gdx.app.getType() == ApplicationType.Desktop
                || Gdx.app.getType() == ApplicationType.WebGL;
    }

    public boolean isDesktop() {
        return desktop;
    }

    public void resize(int width, int height) {
        screenWidth = Math.max(1, width);
        screenHeight = Math.max(1, height);
        stickRadius = Math.min(width, height) * 0.12f;

        float margin = Math.min(width, height) * 0.035f;
        float size = Math.min(width, height) * 0.115f;
        buttons[BTN_PAUSE].set(width - margin - size, height - margin - size, size, size);
        buttons[BTN_TORCH].set(width - margin - size, height - margin - size * 2.25f, size, size);
        buttons[BTN_SPRINT].set(width - margin - size * 1.25f, margin + size * 0.2f,
                size * 1.25f, size * 1.25f);
        buttons[BTN_JUMP].set(width - margin - size * 2.7f, margin + size * 0.35f, size, size);
    }

    public Rectangle button(int id) {
        return buttons[id];
    }

    /** Clears per frame accumulators. Call once per frame before rendering. */
    public void update(float delta) {
        lookDeltaX = 0f;
        lookDeltaY = 0f;
        jumpRequested = false;
        pressedButton = BTN_NONE;

        if (desktop) {
            boolean forward = Gdx.input.isKeyPressed(Input.Keys.W)
                    || Gdx.input.isKeyPressed(Input.Keys.UP);
            boolean back = Gdx.input.isKeyPressed(Input.Keys.S)
                    || Gdx.input.isKeyPressed(Input.Keys.DOWN);
            boolean left = Gdx.input.isKeyPressed(Input.Keys.A)
                    || Gdx.input.isKeyPressed(Input.Keys.LEFT);
            boolean right = Gdx.input.isKeyPressed(Input.Keys.D)
                    || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            moveForward = (forward ? 1f : 0f) - (back ? 1f : 0f);
            moveSide = (right ? 1f : 0f) - (left ? 1f : 0f);
            sprint = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                jumpRequested = true;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                pressedButton = BTN_TORCH;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                pressedButton = BTN_PAUSE;
            }
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                lookDeltaX += Gdx.input.getDeltaX() * 0.0042f;
                lookDeltaY += Gdx.input.getDeltaY() * 0.0034f;
            }
        }
        if (stickActive) {
            float dx = stickX - stickOriginX;
            float dy = stickY - stickOriginY;
            float len = (float) Math.hypot(dx, dy);
            float max = stickRadius;
            if (len > max) {
                dx *= max / len;
                dy *= max / len;
                len = max;
            }
            float dead = 0.14f;
            float nx = dx / max;
            float ny = dy / max;
            if (Math.abs(nx) < dead) nx = 0f;
            if (Math.abs(ny) < dead) ny = 0f;
            moveSide = nx;
            moveForward = ny;
            if (len > max * 0.85f) {
                sprint = true;
            }
        } else if (!desktop) {
            moveForward = 0f;
            moveSide = 0f;
            sprint = false;
        }
        lookDeltaX *= settings.sensitivity;
        lookDeltaY *= settings.sensitivity;
    }

    // ---------------------------------------------------------------------------------------------
    // input processor
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float y = screenHeight - screenY;
        int hit = hitButton(screenX, y);
        if (hit != BTN_NONE) {
            pressedButton = hit;
            if (hit == BTN_SPRINT) {
                sprint = true;
                sprintPointer = pointer;
            }
            if (hit == BTN_JUMP) {
                jumpRequested = true;
            }
            if (hit == BTN_SPRINT || hit == BTN_JUMP) {
                return true;
            }
            return true;
        }
        if (pointer == 0 && screenX < screenWidth * 0.45f) {
            stickPointer = pointer;
            stickActive = true;
            stickOriginX = screenX;
            stickOriginY = y;
            stickX = screenX;
            stickY = y;
            return true;
        }
        if (lookPointer == -1) {
            lookPointer = pointer;
            lastLookX = screenX;
            lastLookY = y;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float y = screenHeight - screenY;
        if (pointer == stickPointer) {
            stickX = screenX;
            stickY = y;
            return true;
        }
        if (pointer == lookPointer) {
            lookDeltaX += (screenX - lastLookX) * 0.0042f;
            lookDeltaY += (lastLookY - y) * 0.0034f;
            lastLookX = screenX;
            lastLookY = y;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == stickPointer) {
            stickPointer = -1;
            stickActive = false;
            return true;
        }
        if (pointer == lookPointer) {
            lookPointer = -1;
            return true;
        }
        if (pointer == sprintPointer) {
            sprintPointer = -1;
            sprint = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (desktop && (keycode == Input.Keys.SPACE)) {
            jumpRequested = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return touchUp(screenX, screenY, pointer, button);
    }

    private int hitButton(float x, float y) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].contains(x, y)) {
                return i;
            }
        }
        return BTN_NONE;
    }

    public float stickOffsetX() {
        return MathUtils.clamp(stickX - stickOriginX, -stickRadius, stickRadius);
    }

    public float stickOffsetY() {
        return MathUtils.clamp(stickY - stickOriginY, -stickRadius, stickRadius);
    }

    public float stickRadius() {
        return stickRadius;
    }

    public boolean isSprintTouched() {
        return sprint;
    }
}
