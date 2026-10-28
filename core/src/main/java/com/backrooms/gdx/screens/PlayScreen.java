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
import com.backrooms.gdx.render.VhsEffect;
import com.backrooms.gdx.render.WorldRenderer;
import com.backrooms.gdx.ui.Hud;
import com.backrooms.gdx.ui.VhsOsd;
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
    private final VhsEffect vhs;
    private final VhsOsd osd;
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
    private boolean waitingForWorld = true;
    private float waited;
    private float fogDensity;
    private float tapeTime;
    private boolean vhsOn;
    private float exposure = 0.95f;
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
        this.vhs = new VhsEffect(1f);
        this.osd = new VhsOsd();
        this.vhsOn = settings.vhs;
        // -Dbackrooms.vhs=0 renders the raw frame, which is what a screenshot wants when the
        // effect itself is being worked on.
        String vhsFlag = System.getProperty("backrooms.vhs");
        if (vhsFlag != null) {
            vhsOn = vhsFlag.trim().matches("1|true|yes|on");
        }

        float[] spawn = new float[3];
        world.gen().findSpawn(spawn);
        // Screenshots: -Dbackrooms.pit=1 walks out to the nearest pit and stands on its rim.
        if (System.getProperty("backrooms.pit") != null) {
            float[] rim = findPitRim(world.gen(), 0, 0);
            if (rim != null) {
                spawn[0] = rim[0];
                spawn[1] = world.gen().floorY(com.backrooms.gdx.util.Hash.floorInt(rim[0]),
                        com.backrooms.gdx.util.Hash.floorInt(rim[1]));
                spawn[2] = rim[1];
                Gdx.app.log("BackroomsInput", String.format(java.util.Locale.US,
                        "pit at %.1f,%.1f (floor %.2f)", rim[0], rim[1], spawn[1]));
            }
        }
        // Screenshots and debugging: -Dbackrooms.spawn=x,z drops the player at a known cell.
        String at = System.getProperty("backrooms.spawn");
        if (at != null) {
            String[] parts = at.trim().split(",");
            if (parts.length == 2) {
                try {
                    float x = Float.parseFloat(parts[0].trim());
                    float z = Float.parseFloat(parts[1].trim());
                    spawn[0] = x;
                    spawn[1] = world.gen().floorY(com.backrooms.gdx.util.Hash.floorInt(x),
                            com.backrooms.gdx.util.Hash.floorInt(z));
                    spawn[2] = z;
                } catch (NumberFormatException ignored) {
                    // keep the generated spawn
                }
            }
        }
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
                    // Android back / desktop escape: pause instead of killing the activity.
                    setPaused(!paused);
                    return true;
                }
                return paused;
            }
        };
        input = new InputMultiplexer(menuInput, controls);
        audio.setEnabled(settings.audio);
        audio.setTapeEnabled(settings.audio && vhsOn);
    }

    /**
     * Finds the nearest cell with a pit south of it, and returns a point 1.5 m south of that pit so
     * the camera looks straight into it. Used by the screenshot job, which cannot guess where the
     * holes are.
     */
    private static float[] findPitRim(com.backrooms.gdx.world.WorldGen gen, int fromX, int fromZ) {
        for (int ring = 0; ring < 80; ring++) {
            for (int dz = -ring; dz <= ring; dz++) {
                for (int dx = -ring; dx <= ring; dx++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) {
                        continue;
                    }
                    int cx = fromX + dx;
                    int cz = fromZ + dz;
                    // Stand to the south (+Z) of a single pit: the default heading looks down -Z.
                    if (gen.isHole(cx, cz) && !gen.isHole(cx, cz + 1) && !gen.isHole(cx, cz + 2)
                            && !gen.isHole(cx + 1, cz) && !gen.isHole(cx - 1, cz)
                            && gen.isOpenSpace(cx, cz + 1) && gen.isOpenSpace(cx, cz + 2)) {
                        return new float[] {cx + 0.5f, cz + 1.5f};
                    }
                }
            }
        }
        return null;
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
        vhs.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controls.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hud.refreshLabels(settings);
        audio.setEnabled(settings.audio);
        logHitTargets();
    }

    /**
     * Prints the touch buttons in screen coordinates (origin top left, the way {@code adb shell
     * input tap} wants them). The emulator smoke test reads these to press the real buttons instead
     * of guessing where they are, which is how the dead-button bug would have been caught.
     */
    private void logHitTargets() {
        StringBuilder line = new StringBuilder();
        line.append("screen=").append(controls.screenWidth()).append('x')
                .append(controls.screenHeight());
        String[] names = {"pause", "torch", "sprint", "jump"};
        for (int id = 0; id < names.length; id++) {
            com.badlogic.gdx.math.Rectangle r = controls.button(id);
            int cx = Math.round(r.x + r.width * 0.5f);
            int cy = Math.round(Gdx.graphics.getHeight() - (r.y + r.height * 0.5f));
            line.append(' ').append(names[id]).append('=').append(cx).append(',').append(cy);
        }
        line.append(" stickZone=").append(Math.round(controls.screenWidth() * 0.45f));
        Gdx.app.log("BackroomsInput", line.toString());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        vhs.resize(width, height);
        controls.resize(width, height);
        hud.resize(width, height);
        logHitTargets();
    }

    @Override
    public void render(float delta) {
        // Simulation uses a clamped step so a hitch cannot teleport the player, but the perf
        // counter has to see the real frame time or a slow device looks like a fast one.
        float rawDelta = delta;
        delta = Math.min(delta, 0.06f);
        time += delta;

        // Always drain the input queue, even while paused, so nothing stale fires on resume.
        controls.update(delta);
        if (!paused) {
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
                    waitingForWorld = true;
                }
            } else if (waitingForWorld) {
                // Hold the fade until the chunks around the player are on the GPU, so the level
                // never opens on an empty void.
                waited += delta;
                if (world.isAreaReady(player.position.x, player.position.z) || waited > 6f) {
                    waitingForWorld = false;
                }
                fade = 1f;
            } else if (fade > 0f) {
                fade = Math.max(0f, fade - delta * 1.1f);
            }
        }

        if (tape) {
            // An old tape is never quite clean: every so often the tracking slips for a moment.
            if (!paused && random.nextFloat() < delta * 0.10f) {
                vhs.setGlitch(0.35f + random.nextFloat() * 0.25f);
            }
            vhs.update(delta);
            audio.setTapeLevel(1f, vhs.glitch());
        }

        statTimer += rawDelta;
        statFrames++;
        if (rawDelta > 0.0001f) {
            statWorst = Math.max(statWorst, (int) (1f / rawDelta));
        }
        if (statTimer >= 2f) {
            Gdx.app.log("Perf", String.format(java.util.Locale.US,
                    "fps=%.1f worst=%d drawn=%d/%d queue=%d build=%dms verts=%dk "
                            + "cam=%.1f,%.1f,%.1f yaw=%.2f pitch=%.2f torch=%d %s",
                    statFrames / statTimer, statWorst, renderer.drawnChunks(), world.loadedCount(),
                    world.pendingCount(), world.lastBuildMillis(), renderer.drawnVertices() / 1000,
                    camera.position.x, camera.position.y, camera.position.z,
                    player.yaw, player.pitch, player.torchOn ? 1 : 0, renderer.statusLine()));
            statTimer = 0f;
            statFrames = 0;
            statWorst = 0;
        }

        player.applyTo(camera);
        player.cameraDirection(torchDir);
        torchPos.set(camera.position).mulAdd(torchDir, 0.15f);

        // With the VHS pass on, the world is drawn into an offscreen buffer and the tape shader puts
        // it on screen afterwards. The debug material view bypasses it: colours have to be readable.
        boolean tape = vhsOn && vhs.isReady() && !debugView;
        if (tape) {
            vhs.begin();                       // also sets the viewport to the buffer
        } else {
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
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

        if (tape) {
            vhs.end(time);
            // The camcorder's own overlay, drawn crisp on top of the tape: this is the camera's
            // character generator, not part of the recording.
            tapeTime += delta;
            assets.batch.begin();
            osd.draw(assets.batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    time, tapeTime, vhs.glitch());
            assets.batch.end();
        }

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
        audio.setTapeEnabled(settings.audio && !paused && vhsOn);
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
        waited = 0f;
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
        vhs.dispose();
        osd.dispose();
        if (Gdx.input.getInputProcessor() == input) {
            Gdx.input.setInputProcessor(null);
        }
    }
}
