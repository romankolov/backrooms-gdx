package com.backrooms.gdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 * Draws whatever is walking around out there.
 *
 * <p>The body is a rigged glTF model ({@link #GLB_PATH}) loaded through gdx-gltf: the "Accurate
 * Backrooms Bacteria v2 with Animations" by Unschooling with Fin (CC-BY-4.0, see
 * {@code assets/models/entity-LICENSE.txt}). It has five clips and none of them are called "walk",
 * so the clips are chosen by what they are: the hostile one (JUMPSCARE) for the lunge, the longest
 * one for locomotion, and a held pose for standing. A model that is missing or fails to load falls
 * back to the procedural figure, so the encounter never depends on the asset being there.
 *
 * <p>The asset is normalised by {@code tools/prepare_entity_model.sh}: metres, Y up, feet at the
 * origin, 2.1 m tall, facing -Z. That cannot be worked out at run time - a skinned mesh's bounds are
 * not the bounds it draws, because the skinning matrices decide the size - so the conversion
 * measures it where the skin can be evaluated.
 */
public final class EntityRenderer implements Disposable {

    /** Where the rigged model is picked up. */
    public static final String GLB_PATH = "models/entity.glb";

    /** How tall the creature should stand, in metres. The corridors are 3 m. */
    private static final float TARGET_HEIGHT = 2.1f;
    /**
     * The model's own forward, in degrees around the vertical, measured from the game's forward.
     * A rig that was authored facing +Z instead of -Z needs a half turn here.
     */
    private static final float FACING_DEGREES = 0f;

    private static final Color TORCH_COLOR = new Color(1f, 0.93f, 0.78f, 1f);

    private final EntityController entity;

    // --- rigged model ----------------------------------------------------------------------------
    private SceneManager scenes;
    private SceneAsset asset;
    private Scene scene;
    private AnimationController animation;
    private PointLight torchLight;
    private String walkClip;
    private String idleClip;
    private String scareClip;
    private String currentClip;
    private float clipTimeScale = 1f;
    private final Matrix4 transform = new Matrix4();
    private float baseScale = 1f;
    /** Which way the model faces relative to the game's forward: see {@link #FACING_DEGREES}. */
    private float facingOffset = FACING_DEGREES;

    // --- procedural fallback ---------------------------------------------------------------------
    private final Environment lights = new Environment();
    private final PointLight torch = new PointLight();
    private ModelBatch batch;
    private Model model;
    private ModelInstance instance;

    private boolean ready;
    private boolean modelLoaded;
    private float pendingDelta;
    private float flickerPhase;
    private float scareLock;
    private int debugFrames;

    public EntityRenderer(EntityController entity) {
        this.entity = entity;
        modelLoaded = loadModel();
        // loadModel sets the flag itself when it succeeds; if it did not, there is no model.
        if (!modelLoaded) {
            buildFallback();
        }
    }

    public boolean isReady() {
        return ready;
    }

    /** True if the supplied rigged model is in use rather than the placeholder body. */
    public boolean usingModel() {
        return modelLoaded;
    }

    /** Clip names, for the log line that proves which animation is playing on a device. */
    public String clipLine() {
        if (!modelLoaded) {
            return "placeholder";
        }
        return "idle=" + idleClip + " walk=" + walkClip + " scare=" + scareClip
                + " playing=" + currentClip;
    }

    /** Animation update. Kept separate from drawing so a paused game does not animate. */
    public void update(float delta) {
        pendingDelta = delta;
        flickerPhase += delta * 7.3f;
        if (scareLock > 0f) {
            scareLock = Math.max(0f, scareLock - delta);
        }
    }

    /** Plays the lunge once, then falls back to whatever it was doing. */
    public void lunge() {
        if (modelLoaded && animation != null && scareClip != null) {
            animation.animate(scareClip, 0.12f);
            currentClip = scareClip;
            scareLock = 1.6f;
        }
    }

    /**
     * Draws the entity into whatever is currently bound.
     *
     * @param width  render target width
     * @param height render target height
     */
    public void render(Camera camera, int width, int height, Vector3 torchPos, float torchOn) {
        if (!ready || !entity.active()) {
            return;
        }
        // The camera's viewport has to match the target we are drawing into, or a pass into the
        // reduced-size tape buffer would be projected for the full screen.
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        Gdx.gl.glViewport(0, 0, width, height);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_BACK);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        float x = entity.position.x;
        float y = entity.position.y + entity.bob();
        float z = entity.position.z;
        // The torch is not steady: a dying battery and a cheap filament, so it breathes.
        float flicker = 0.88f + 0.12f * MathUtils.sin(flickerPhase)
                + 0.04f * MathUtils.sin(flickerPhase * 3.7f);

        if (modelLoaded) {
            updateClip();
            // Placement: yaw about the vertical axis, then the model's own scale/offset fix-up.
            transform.setToTranslation(x, y, z)
                    .rotate(Vector3.Y, entity.yaw * MathUtils.radiansToDegrees + facingOffset)
                    .scale(baseScale, baseScale, baseScale);
            scene.modelInstance.transform.set(transform);
            // Inverse-square falloff: a point light this bright reads at three or four metres,
            // which is exactly how close you want the thing to get before it is lit.
            torchLight.position.set(torchPos);
            torchLight.intensity = 34f * flicker * torchOn;
            scenes.setCamera(camera);
            scenes.update(pendingDelta);
            pendingDelta = 0f;
            if (debugFrames < 3) {
                debugFrames++;
                Vector3 probe = new Vector3(x, y + 1f, z);
                camera.project(probe);
                Gdx.app.log("Entity", "draw: entity " + x + "," + y + "," + z
                        + " cam " + camera.position + " dir " + camera.direction
                        + " ndc " + probe + " nodes " + scene.modelInstance.nodes.size
                        + " anims " + scene.modelInstance.animations.size
                        + " mats " + scene.modelInstance.materials.size);
            }
            scenes.render();
            if (debugFrames < 4) {
                int err = Gdx.gl.glGetError();
                if (err != 0) {
                    Gdx.app.error("Entity", "GL error after scene render: 0x" + Integer.toHexString(err));
                }
            }
            return;
        }

        lights.clear();
        lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.055f, 0.052f, 0.045f, 1f));
        torch.set(TORCH_COLOR.r, TORCH_COLOR.g, TORCH_COLOR.b, torchPos, 9f * flicker * torchOn);
        lights.add(torch);

        instance.transform.setToTranslation(x, y, z);
        instance.transform.rotate(Vector3.Y, entity.yaw * MathUtils.radiansToDegrees);
        batch.begin(camera);
        batch.render(instance, lights);
        batch.end();
        pendingDelta = 0f;
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (model != null) {
            model.dispose();
        }
        if (scenes != null) {
            scenes.dispose();
        }
        if (asset != null) {
            asset.dispose();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // rigged model
    // ---------------------------------------------------------------------------------------------

    private boolean loadModel() {
        FileHandle file = Gdx.files.internal(GLB_PATH);
        if (!file.exists()) {
            return false;
        }
        try {
            asset = new GLBLoader().load(file);
            scenes = new SceneManager();
            scene = new Scene(asset.scene);
            scenes.addScene(scene);
            animation = scene.animationController;
            pickClips(asset.animations);
            checkModel();
            // The torch is the only light that matters in here; the flat ambient stands in for the
            // fluorescent wash from the ceiling fixtures above.
            scenes.environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
                    0.20f, 0.20f, 0.21f, 1f));
            torchLight = new PointLight();
            torchLight.set(TORCH_COLOR, new Vector3(), 0f);
            scenes.environment.add(torchLight);
            if (idleClip != null) {
                animation.animate(idleClip, -1, 1f, null, 0f);
                currentClip = idleClip;
            }
            ready = true;
            modelLoaded = true;
            return true;
        } catch (Throwable t) {
            // A malformed or unsupported asset must not stop the game: fall back to the figure.
            Gdx.app.error("Entity", "could not load " + GLB_PATH + ": " + t);
            scenes = null;
            scene = null;
            asset = null;
            return false;
        }
    }

    /**
     * Checks the model against the contract the pipeline promises, and remembers the yaw fix-up.
     *
     * <p>The file is normalised by {@code tools/prepare_entity_model.sh}: metres, Y up, the feet at
     * the origin, 2.1 m tall, facing -Z. None of that can be recovered at run time - a skinned
     * mesh's bounds are not its drawn bounds, because the skinning matrices decide the size - so the
     * conversion measures it, once, where the skin can be evaluated, and the game trusts it. A
     * replacement model that does not follow the contract is corrected with the system properties
     * rather than guessed at.
     */
    private void checkModel() {
        facingOffset = property("backrooms.entityyaw", FACING_DEGREES);
        float scale = property("backrooms.entityscale", 1f);
        baseScale = scale;
        if (!scene.modelInstance.materials.isEmpty()) {
            Gdx.app.log("Entity", "model ready: " + GLB_PATH + " expecting " + TARGET_HEIGHT
                    + " m (scale x" + scale + ", yaw +" + facingOffset + " deg), clips: "
                    + clipLine());
        }
    }

    /** Reads a float system property, falling back when it is absent or malformed. */
    private static float property(String key, float fallback) {
        String value = System.getProperty(key);
        if (value == null) {
            return fallback;
        }
        try {
            return Float.parseFloat(value.trim());
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    /**
     * Clips are matched by what their names suggest, with the longest clip as the locomotion
     * fallback - a walk cycle is long, a transition or a pose is not.
     */
    private void pickClips(Array<Animation> animations) {
        if (animations == null || animations.size == 0) {
            return;
        }
        Animation longest = animations.get(0);
        for (Animation a : animations) {
            if (a.duration > longest.duration) {
                longest = a;
            }
            String id = lower(a.id);
            if (scareClip == null && (id.contains("jump") || id.contains("scare")
                    || id.contains("attack") || id.contains("roar") || id.contains("lunge"))) {
                scareClip = a.id;
            }
            if (idleClip == null && (id.contains("stalk") || id.contains("idle")
                    || id.contains("stand") || id.contains("breath"))) {
                idleClip = a.id;
            }
        }
        walkClip = longest.id;
        if (idleClip == null || idleClip.equals(walkClip)) {
            idleClip = walkClip;
        }
    }

    private static String lower(String text) {
        return text == null ? "" : text.toLowerCase(java.util.Locale.ROOT);
    }

    private void updateClip() {
        if (animation == null || walkClip == null) {
            return;
        }
        if (scareLock > 0f) {
            return;                                    // the lunge owns the body while it plays
        }
        boolean moving = entity.speed() > 0.6f;
        String wanted = moving ? walkClip : idleClip;
        // The animation speed follows the creature: a hunt reads as urgent, a stalk as deliberate.
        float wantedSpeed = moving
                ? MathUtils.clamp(entity.speed() / 2.2f, 0.8f, 2.0f)
                : 1f;
        if (!wanted.equals(currentClip)) {
            animation.animate(wanted, 1, wantedSpeed, null, 0.3f);
            currentClip = wanted;
            clipTimeScale = wantedSpeed;
        } else if (Math.abs(wantedSpeed - clipTimeScale) > 0.15f) {
            animation.animate(wanted, -1, wantedSpeed, null, 0.25f);
            clipTimeScale = wantedSpeed;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // placeholder body
    // ---------------------------------------------------------------------------------------------

    private void buildFallback() {
        try {
            model = buildFigure();
            instance = new ModelInstance(model);
            batch = new ModelBatch(new DefaultShaderProvider());
            ready = true;
            Gdx.app.log("Entity", "no " + GLB_PATH + ", using the placeholder body");
        } catch (Throwable t) {
            Gdx.app.error("Entity", "could not build the body: " + t);
        }
    }

    /**
     * A tall figure, built out of a dozen boxes: long arms, narrow torso, a head that is slightly
     * too small for the shoulders. Flat shaded and very dark on purpose.
     */
    private static Model buildFigure() {
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        Material skin = new Material(ColorAttribute.createDiffuse(0.030f, 0.028f, 0.028f, 1f),
                ColorAttribute.createSpecular(0.02f, 0.02f, 0.02f, 1f),
                new com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute(
                        com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute.Shininess, 6f));
        Material eyes = new Material(ColorAttribute.createDiffuse(0.02f, 0.01f, 0.01f, 1f),
                ColorAttribute.createEmissive(0.85f, 0.10f, 0.05f, 1f));

        long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
        int id = 0;

        builder.node().id = "legs";
        builder.part("legL" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.20f, 0f, -0.07f, 0.13f, 0.98f, 0.14f);
        builder.part("legR" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(0.07f, 0f, -0.07f, 0.13f, 0.98f, 0.14f);
        builder.part("torso" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.21f, 0.94f, -0.12f, 0.42f, 0.72f, 0.24f);
        builder.part("shoulders" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.33f, 1.52f, -0.13f, 0.66f, 0.12f, 0.26f);
        builder.part("armL" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.37f, 0.72f, -0.10f, 0.11f, 0.84f, 0.13f);
        builder.part("armR" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(0.26f, 0.72f, -0.10f, 0.11f, 0.84f, 0.13f);
        builder.part("neck" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.05f, 1.66f, -0.14f, 0.10f, 0.12f, 0.10f);
        builder.part("head" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.10f, 1.77f, -0.19f, 0.20f, 0.23f, 0.21f);
        builder.part("eyeL" + id++, GL20.GL_TRIANGLES, attrs, eyes)
                .box(-0.065f, 1.87f, -0.20f, 0.035f, 0.022f, 0.012f);
        builder.part("eyeR" + id++, GL20.GL_TRIANGLES, attrs, eyes)
                .box(0.030f, 1.87f, -0.20f, 0.035f, 0.022f, 0.012f);

        return builder.end();
    }
}
