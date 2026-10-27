package com.backrooms.gdx;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.backrooms.gdx.render.WorldRenderer;
import com.backrooms.gdx.screens.PlayScreen;
import com.backrooms.gdx.screens.TitleScreen;

/**
 * Entry point shared by every launcher.
 *
 * <p>Graphical resources live here so a new run does not have to re-upload the 15 MB of wallpaper,
 * carpet and ceiling textures every time the player returns to the title screen.
 */
public final class BackroomsGame extends Game {

    public Assets assets;
    public GameSettings settings;

    private WorldRenderer renderer;
    private PlayScreen playScreen;
    private TitleScreen titleScreen;

    @Override
    public void create() {
        settings = new GameSettings();
        settings.load();
        assets = new Assets();
        if (System.getProperty("backrooms.autostart") != null
                && System.getProperty("backrooms.title") == null) {
            startGame();
        } else {
            titleScreen = new TitleScreen(this);
            setScreen(titleScreen);
        }
    }

    public WorldRenderer renderer() {
        if (renderer == null) {
            renderer = new WorldRenderer();
        }
        return renderer;
    }

    public void startGame() {
        int seed = new Random().nextInt();
        String forcedSeed = System.getProperty("backrooms.seed");
        if (forcedSeed != null) {
            try {
                seed = Integer.parseInt(forcedSeed.trim());
            } catch (NumberFormatException ignored) {
                // keep the random seed
            }
        }
        if (playScreen != null) {
            playScreen.dispose();
        }
        playScreen = new PlayScreen(this, seed);
        setScreen(playScreen);
        if (titleScreen != null) {
            titleScreen.dispose();
            titleScreen = null;
        }
    }

    public void showTitle() {
        if (playScreen != null) {
            playScreen.dispose();
            playScreen = null;
        }
        if (titleScreen == null) {
            titleScreen = new TitleScreen(this);
        }
        setScreen(titleScreen);
    }

    @Override
    public void pause() {
        super.pause();
        if (playScreen != null) {
            playScreen.pause();
        }
    }

    @Override
    public void dispose() {
        if (playScreen != null) {
            playScreen.dispose();
            playScreen = null;
        }
        if (renderer != null) {
            renderer.dispose();
            renderer = null;
        }
        if (assets != null) {
            assets.dispose();
        }
        Gdx.app.log("Backrooms", "bye");
    }
}
