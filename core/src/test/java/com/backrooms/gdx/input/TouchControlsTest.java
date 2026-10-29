package com.backrooms.gdx.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.backrooms.gdx.GameSettings;

/**
 * The touch controls must keep what the event handlers record until the screen reads it.
 *
 * <p>This is the regression test for the bug that made the phone build feel half dead: the right
 * hand could not look around and no button did anything, because {@code update()} cleared the
 * accumulators at the top of the frame - and on Android the events are dispatched just before
 * {@code render}, so every drag and every button press was thrown away. Only the stick survived,
 * because it stores a position instead of an event.
 */
public class TouchControlsTest {

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1920;
    private static final float SENSITIVITY = 1f;

    private TouchControls controls;

    @Before
    public void setUp() {
        GameSettings settings = new GameSettings();
        settings.sensitivity = SENSITIVITY;
        controls = new TouchControls(settings, false);
        controls.resize(WIDTH, HEIGHT);
    }

    /** Taps the middle of a button, converting to the screen coordinates libGDX delivers. */
    private void tap(int id) {
        float cx = controls.button(id).x + controls.button(id).width * 0.5f;
        float cy = controls.button(id).y + controls.button(id).height * 0.5f;
        controls.touchDown((int) cx, (int) (HEIGHT - cy), 7, 0);
        controls.touchUp((int) cx, (int) (HEIGHT - cy), 7, 0);
    }

    @Test
    public void draggingOnTheRightLooksAround() {
        // Right half, away from the buttons. Player::addLook does yaw -= dx and pitch -= dy.
        controls.touchDown(900, 1200, 0, 0);
        controls.touchDragged(700, 1200, 0);       // 200 px to the left
        controls.update(1f / 60f);
        assertTrue("dragging left must turn the camera, got " + controls.lookDeltaX,
                controls.lookDeltaX < -0.5f);

        controls.touchDragged(700, 1100, 0);       // 100 px up the screen
        controls.update(1f / 60f);
        assertTrue("dragging up must look up, got " + controls.lookDeltaY,
                controls.lookDeltaY < -0.2f);
    }

    @Test
    public void lookDeltasAreNotRepeatedOnTheNextFrame() {
        controls.touchDown(900, 1200, 0, 0);
        controls.touchDragged(800, 1200, 0);
        controls.update(1f / 60f);
        assertTrue(controls.lookDeltaX < 0f);

        controls.update(1f / 60f);
        assertEquals("a drag must only be applied once", 0f, controls.lookDeltaX, 0f);
    }

    @Test
    public void everyButtonReportsOnce() {
        for (int id : new int[] {TouchControls.BTN_TORCH, TouchControls.BTN_PAUSE,
                TouchControls.BTN_JUMP}) {
            tap(id);
            controls.update(1f / 60f);
            assertEquals("button " + id + " must be reported", id, controls.pressedButton);
            controls.update(1f / 60f);
            assertEquals("button " + id + " must only fire once", TouchControls.BTN_NONE,
                    controls.pressedButton);
        }
    }

    @Test
    public void theJumpButtonJumpsOnce() {
        tap(TouchControls.BTN_JUMP);
        controls.update(1f / 60f);
        assertTrue("the jump button must request a jump", controls.jumpRequested);
        controls.update(1f / 60f);
        assertFalse("one tap, one jump", controls.jumpRequested);
    }

    @Test
    public void theRunButtonIsHeldNotTapped() {
        float cx = controls.button(TouchControls.BTN_SPRINT).x
                + controls.button(TouchControls.BTN_SPRINT).width * 0.5f;
        float cy = controls.button(TouchControls.BTN_SPRINT).y
                + controls.button(TouchControls.BTN_SPRINT).height * 0.5f;
        int screenY = (int) (HEIGHT - cy);

        controls.touchDown((int) cx, screenY, 3, 0);
        for (int i = 0; i < 5; i++) {
            controls.update(1f / 60f);
            assertTrue("running must stay on while the button is held", controls.sprint);
        }
        controls.touchUp((int) cx, screenY, 3, 0);
        controls.update(1f / 60f);
        assertFalse("running must stop when the finger lifts", controls.sprint);
    }

    @Test
    public void twoThumbsWorkAtTheSameTime() {
        // Right thumb looks first, then the left one grabs the stick: neither may steal the other.
        controls.touchDown(880, 1200, 0, 0);
        controls.touchDown(200, 1500, 1, 0);
        controls.update(1f / 60f);
        assertTrue("the stick must engage with the second finger", controls.stickActive);

        controls.touchDragged(200, 1300, 1);      // push the stick forward
        controls.touchDragged(760, 1200, 0);      // and keep looking to the left
        controls.update(1f / 60f);

        assertTrue("pushing the stick up walks forward, got " + controls.moveForward,
                controls.moveForward > 0.4f);
        assertEquals("no sideways drift", 0f, controls.moveSide, 0.001f);
        assertTrue("the look drag must still register", controls.lookDeltaX < 0f);
    }

    @Test
    public void theLeftSideNeverStealsTheLookPointer() {
        // A drag that starts on the right must keep turning even after it crosses to the left.
        controls.touchDown(900, 1200, 0, 0);
        controls.touchDragged(300, 1200, 0);
        controls.update(1f / 60f);
        assertFalse("crossing over must not start walking", controls.stickActive);
        assertTrue(controls.lookDeltaX < 0f);
    }
}
