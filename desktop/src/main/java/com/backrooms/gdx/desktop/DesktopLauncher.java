package com.backrooms.gdx.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.backrooms.gdx.BackroomsGame;

/**
 * Desktop launcher. Besides being convenient for development it doubles as the screenshot harness
 * used by CI:
 *
 * <pre>
 * ./gradlew :desktop:run -Dbackrooms.autostart=1 -Dbackrooms.capture=screenshots -Dbackrooms.frames=320
 * </pre>
 */
public final class DesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("The Backrooms");
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 0);

        String captureDir = System.getProperty("backrooms.capture");
        BackroomsGame game = new BackroomsGame();
        if (captureDir != null && captureDir.length() > 0) {
            int frames = Integer.getInteger("backrooms.frames", 320);
            new Lwjgl3Application(new CaptureWrapper(game, captureDir, frames), config);
        } else {
            new Lwjgl3Application(game, config);
        }
    }

    /** Renders normally and dumps a few PNGs, then quits. Used to eyeball the renderer in CI. */
    private static final class CaptureWrapper implements ApplicationListener {
        private static final int[] SHOTS = {60, 160, 260};

        private final ApplicationListener delegate;
        private final String directory;
        private final int stopAfter;
        private int frame;
        private int taken;

        CaptureWrapper(ApplicationListener delegate, String directory, int stopAfter) {
            this.delegate = delegate;
            this.directory = directory;
            this.stopAfter = stopAfter;
        }

        @Override
        public void create() {
            delegate.create();
        }

        @Override
        public void resize(int width, int height) {
            delegate.resize(width, height);
        }

        @Override
        public void render() {
            delegate.render();
            frame++;
            if (taken < SHOTS.length && frame == SHOTS[taken]) {
                writeFrame("shot" + taken);
                taken++;
            }
            if (frame >= Math.max(stopAfter, SHOTS[SHOTS.length - 1] + 1)) {
                if (taken < SHOTS.length) {
                    writeFrame("shot" + taken);
                }
                Gdx.app.exit();
            }
        }

        private void writeFrame(String name) {
            int width = Gdx.graphics.getBackBufferWidth();
            int height = Gdx.graphics.getBackBufferHeight();
            byte[] pixels = ScreenUtils.getFrameBufferPixels(true);
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
            java.io.File dir = new java.io.File(directory);
            if (!dir.exists() && !dir.mkdirs()) {
                Gdx.app.error("Capture", "could not create " + directory);
            }
            PixmapIO.writePNG(Gdx.files.absolute(new java.io.File(dir, name + ".png").getAbsolutePath()),
                    pixmap);
            pixmap.dispose();
            Gdx.app.log("Capture", "wrote " + name + ".png");
        }

        @Override
        public void pause() {
            delegate.pause();
        }

        @Override
        public void resume() {
            delegate.resume();
        }

        @Override
        public void dispose() {
            delegate.dispose();
        }
    }
}
