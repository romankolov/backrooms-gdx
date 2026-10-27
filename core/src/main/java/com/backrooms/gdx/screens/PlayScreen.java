package com.backrooms.gdx.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.Assets;
import com.backrooms.gdx.BackroomsGame;
import com.backrooms.gdx.GameSettings;
import com.backrooms.gdx.audio.AudioManager;
import com.backrooms.gdx.input.TouchControls;
import com.backrooms.gdx.player.Player;
import com.backrooms.gdx.render.WorldRenderer;
import com.backrooms.gdx.ui.Hud;
import com.backrooms.gdx.world.World;

/** The game itself: streaming world, first person walking, ambience and HUD. */
public final class PlayScreen extends ScreenAdapter {

    private static final float FALL_DEPTH = -2.1f;

    private final BackroomsGame game;
    private final GameSettings settings;
    private final Assets assets;
    private final WorldRenderer renderer;
    private final World world;
    private final Player player;
    private final TouchControls controls;
    private final AudioManager audio;
    private final Hud hud;
    private final PerspectiveCamera camera = new PerspectiveCamera(74f, 1, 1);
    private final InputAdapter menuInput;
    private final InputMultiplexer input;
    private final Vector3 torchPos = new Vector3();
    private final Vector3 torchDir = new Vector3();
    private final Random random = new Random();
    private final float[] landing = new float[3];

    private float time;
    private float statTimer;
    private long statFrames;
    private int statWorst;
    private boolean paused;
    private float fade = 1f;
    private boolean teleporting;
    private float fogDensity;
    private float exposure = 1.05f;
    private int seed;
    private final boolean spin;
    private final boolean debugView = System.getProperty("backrooms.debug", "0").trim()
            .matches("1|true|yes");

    public PlayScreen(BackroomsGame game, int seed) {
        this.game = game;
        this.settings = game.settings;
        this.assets = game.assets;
        this.seed = seed;
        this.renderer = game.renderer();
        this.world = new World(seed, settings.renderDistance, renderer);
        this.player = new Player(world.gen());
        this.controls = new TouchControls(settings);
        this.audio = new AudioManager(assets, world.gen());
        this.hud = new Hud(assets);

        float[] spawn = new float[3];
        world.gen().findSpawn(spawn);
        player.spawn(spawn[0], spawn[1], spawn[2]);
        player.torchOn = settings.torch;
        String yaw = System.getProperty("backrooms.yaw");
        if (yaw != null) {
            try {
                player.yaw = (float) Math.toRadians(Double.parseDouble(yaw.trim()));
            } catch (NumberFormatException ignored) {
                // keep the default heading
            }
        }
        String pitch = System.getProperty("backrooms.pitch");
        if (pitch != null) {
            try {
                player.pitch = (float) Math.toRadians(Double.parseDouble(pitch.trim()));
            } catch (NumberFormatException ignored) {
                // keep the default
            }
        }
        spin = System.getProperty("backrooms.spin") != null;

        camera.near = 0.05f;
        updateCameraRange();

        menuInput = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!paused) {
                    return false;
                }
                float y = Gdx.graphics.getHeight() - screenY;
                int hit = hud.hitMenu(screenX, y);
                if (hit >= 0) {
                    onMenu(hit);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return paused;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.ESCAPE
                        || keycode == com.badlogic.gdx.Input.Keys.BACK) {
                    setPaused(!paused);
                    return true;
                }
                return paused;
            }
        };
        input = new InputMultiplexer(menuInput, controls);
        audio.setEnabled(settings.audio);
    }

    private void updateCameraRange() {
        float far = (settings.renderDistance + 1) * 16f + 8f;
        camera.far = far;
        fogDensity = 2.15f / far;
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
        controls.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud.refreshLabels(settings);
        audio.setEnabled(settings.audio);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        controls.resize(width, height);
        hud.resize(width, height);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 0.06f);
        time += delta;

        if (!paused) {
            controls.update(delta);
            if (spin) {
                player.yaw += delta * 0.35f;
            }
            player.addLook(controls.lookDeltaX, controls.lookDeltaY);
            player.update(delta, controls.moveForward, controls.moveSide, controls.sprint,
                    controls.jumpRequested);

            if (controls.pressedButton == TouchControls.BTN_PAUSE) {
                setPaused(true);
            } else if (controls.pressedButton == TouchControls.BTN_TORCH) {
                toggleTorch();
            }
        }

        if (!paused) {
            world.update(player.position.x, player.position.z, 2);
            audio.update(delta, player.position.x, player.position.z);
            if (player.takeFootstep(controls.sprint)) {
                audio.footstep(controls.sprint);
            }
            if (player.landingImpact > 0.18f) {
                audio.landing(player.landingImpact);
            }
            if (player.grounded && player.position.y < FALL_DEPTH) {
                startFallThrough();
            }
            if (teleporting) {
                fade = Math.min(1f, fade + delta * 3.2f);
                if (fade >= 0.999f) {
                    relocate();
                }
            } else if (fade > 0f) {
                fade = Math.max(0f, fade - delta * 1.1f);
            }
        }

        statTimer += delta;
        statFrames++;
        if (delta > 0.0001f) {
            statWorst = Math.max(statWorst, (int) (1f / delta));
        }
        if (statTimer >= 2f) {
            Gdx.app.log("Perf", String.format(java.util.Locale.US,
                    "fps=%.1f worst=%d drawn=%d/%d queue=%d build=%dms verts=%dk",
                    statFrames / statTimer, statWorst, renderer.drawnChunks(), world.loadedCount(),
                    world.pendingCount(), world.lastBuildMillis(), renderer.drawnVertices() / 1000));
            statTimer = 0f;
            statFrames = 0;
            statWorst = 0;
        }

        player.applyTo(camera);
        player.cameraDirection(torchDir);
        torchPos.set(camera.position).mulAdd(torchDir, 0.15f);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (debugView) {
            // Anything magenta on screen is a hole: there is no geometry there.
            Gdx.gl.glClearColor(1f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(WorldRenderer.FOG_COLOR_R, WorldRenderer.FOG_COLOR_G,
                    WorldRenderer.FOG_COLOR_B, 1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_BACK);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);

        renderer.begin(camera, time, camera.position, torchPos, torchDir,
                player.torchOn ? 1f : 0f, fogDensity, exposure,
                settings.quality > 0 ? 1.0f : 0.35f, 3.0f,
                debugView ? 1f : 0f);
        renderer.draw(world, camera);
        renderer.end();

        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);

        boolean hudDebug = settings.showDebug || System.getProperty("backrooms.hud", "0")
                .trim().matches("1|true|yes");
        if (hudDebug) {
            hud.drawGameplay(controls, player.torchOn, true, debugLine1(), debugLine2());
        } else {
            hud.drawGameplay(controls, player.torchOn, false, null, null);
        }
        if (paused) {
            hud.drawPause(settings);
        }
        hud.drawFade(fade);
    }

    private String debugLine1() {
        return "FPS " + Gdx.graphics.getFramesPerSecond()
                + "   chunks " + renderer.drawnChunks() + "/" + world.loadedCount()
                + "   queue " + world.pendingCount()
                + "   build " + world.lastBuildMillis() + "ms";
    }

    private String debugLine2() {
        return String.format(java.util.Locale.US, "x %.1f  y %.1f  z %.1f   seed %d   verts %dk",
                player.position.x, player.position.y, player.position.z, seed,
                renderer.drawnVertices() / 1000);
    }

    // ---------------------------------------------------------------------------------------------

    private void toggleTorch() {
        player.torchOn = !player.torchOn;
        settings.torch = player.torchOn;
        settings.save();
    }

    private void setPaused(boolean value) {
        paused = value;
        audio.setEnabled(settings.audio && !paused);
        if (paused) {
            hud.refreshLabels(settings);
        }
    }

    private void onMenu(int id) {
        switch (id) {
            case Hud.BTN_RESUME:
                setPaused(false);
                break;
            case Hud.BTN_TORCH:
                toggleTorch();
                hud.refreshLabels(settings);
                break;
            case Hud.BTN_AUDIO:
                settings.audio = !settings.audio;
                audio.setEnabled(settings.audio);
                settings.save();
                hud.refreshLabels(settings);
                break;
            case Hud.BTN_DISTANCE:
                settings.renderDistance = settings.renderDistance >= 6 ? 2
                        : settings.renderDistance + 1;
                world.setRadius(settings.renderDistance);
                updateCameraRange();
                settings.save();
                hud.refreshLabels(settings);
                break;
            case Hud.BTN_SENSITIVITY:
                settings.sensitivity += 0.25f;
                if (settings.sensitivity > 2.5f) {
                    settings.sensitivity = 0.5f;
                }
                settings.save();
                hud.refreshLabels(settings);
                break;
            case Hud.BTN_QUIT:
                game.showTitle();
                break;
            default:
                break;
        }
    }

    /** Falling into a hole drops you somewhere else in the level, out of sight. */
    private void startFallThrough() {
        if (teleporting) {
            return;
        }
        teleporting = true;
        audio.landing(1f);
    }

    private void relocate() {
        float[] out = new float[3];
        for (int attempt = 0; attempt < 24; attempt++) {
            float x = player.position.x + (random.nextFloat() - 0.5f) * 600f;
            float z = player.position.z + (random.nextFloat() - 0.5f) * 600f;
            if (world.gen().findLanding(x, z, out) && out[1] > -1.5f) {
                player.spawn(out[0], out[1] + 0.05f, out[2]);
                player.velocity.set(0f, 0f, 0f);
                player.yaw = random.nextFloat() * MathUtils.PI2;
                player.pitch = 0f;
                teleporting = false;
                player.landingImpact = 0f;
                return;
            }
        }
        teleporting = false;
    }

    @Override
    public void pause() {
        if (!paused) {
            setPaused(true);
        }
    }

    @Override
    public void dispose() {
        audio.dispose();
        world.dispose();
        if (Gdx.input.getInputProcessor() == input) {
            Gdx.input.setInputProcessor(null);
        }
    }
}
