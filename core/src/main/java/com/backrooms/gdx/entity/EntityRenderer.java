package com.backrooms.gdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
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
 * Draws the thing in the corridors.
 *
 * <p>It is deliberately featureless: a solid black silhouette, no texture, no detail, nothing for
 * the eye to lock onto. Against the lit wallpaper that reads as a person-shaped absence rather than
 * a monster, which is the whole trick - the imagination does the rest. The mesh is the "Accurate
 * Backrooms Bacteria v2 with Animations" model by Unschooling with Fin (CC-BY-4.0, see
 * {@code assets/models/entity-LICENSE.txt}), with its material forced to black; the rig is what
 * gives the silhouette its long-limbed, hunched shape and its walk.
 *
 * <p>The asset is normalised by {@code tools/prepare_entity_model.sh}: metres, Y up, feet at the
 * origin, 2.1 m tall, facing -Z. That cannot be worked out at run time - a skinned mesh's bounds are
 * not the bounds it draws, because the skinning matrices decide the size - so the conversion
 * measures it where the skin can be evaluated.
 *
 * <p>A missing or unloadable file falls back to a figure built from boxes, also black, so the
 * encounter never depends on the asset.
 */
public final class EntityRenderer implements Disposable {

    /** Where the rigged model is picked up. */
    public static final String GLB_PATH = "models/entity.glb";

    /** How tall the creature stands, in metres. The corridors are 3 m. */
    private static final float TARGET_HEIGHT = 2.1f;
    /**
     * The model's own forward, in degrees around the vertical, measured from the game's forward.
     * A rig that was authored facing +Z instead of -Z needs a half turn here.
     */
    private static final float FACING_DEGREES = 0f;

    private final EntityController entity;

    // --- rigged model ----------------------------------------------------------------------------
    private SceneManager scenes;
    private SceneAsset asset;
    private Scene scene;
    private AnimationController animation;
    private String walkClip;
    private String idleClip;
    private String currentClip;
    private float clipTimeScale = 1f;
    private final Matrix4 transform = new Matrix4();
    private float baseScale = 1f;
    /** Which way the model faces relative to the game's forward: see {@link #FACING_DEGREES}. */
    private float facingOffset = FACING_DEGREES;

    // --- procedural fallback ---------------------------------------------------------------------
    private ModelBatch batch;
    private Model model;
    private ModelInstance instance;

    private boolean ready;
    private boolean modelLoaded;
    private float pendingDelta;

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
        return "idle=" + idleClip + " walk=" + walkClip + " playing=" + currentClip;
    }

    /** Animation update. Kept separate from drawing so a paused game does not animate. */
    public void update(float delta) {
        pendingDelta = delta;
    }

    /**
     * Draws the entity into whatever is currently bound.
     *
     * @param width  render target width
     * @param height render target height
     */
    public void render(Camera camera, int width, int height) {
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

        if (modelLoaded) {
            updateClip();
            transform.setToTranslation(x, y, z)
                    .rotate(Vector3.Y, entity.yaw * MathUtils.radiansToDegrees + facingOffset)
                    .scale(baseScale, baseScale, baseScale);
            scene.modelInstance.transform.set(transform);
            scenes.setCamera(camera);
            scenes.update(pendingDelta);
            pendingDelta = 0f;
            scenes.render();
            return;
        }

        instance.transform.setToTranslation(x, y, z);
        instance.transform.rotate(Vector3.Y, entity.yaw * MathUtils.radiansToDegrees);
        batch.begin(camera);
        batch.render(instance);            // no lights: a black body stays black
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
            blacken(scene.modelInstance.materials);
            // No lights in the environment on purpose: the body is black, so every light in the
            // scene would multiply into nothing anyway. Leaving them out saves a shader branch.
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
     * Forces every material to solid black and drops any texture it may carry: the silhouette is
     * the whole design, and a model shipped with a skin should still come out as a shadow.
     */
    private static void blacken(Array<Material> materials) {
        if (materials == null) {
            return;
        }
        for (Material material : materials) {
            material.set(ColorAttribute.createDiffuse(0f, 0f, 0f, 1f));
            material.set(ColorAttribute.createAmbient(0f, 0f, 0f, 1f));
            material.set(ColorAttribute.createSpecular(0f, 0f, 0f, 1f));
            material.set(ColorAttribute.createEmissive(0f, 0f, 0f, 1f));
            material.remove(TextureAttribute.Diffuse);
            material.remove(TextureAttribute.Emissive);
            material.remove(TextureAttribute.Normal);
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
        baseScale = property("backrooms.entityscale", 1f);
        Gdx.app.log("Entity", "model ready: " + GLB_PATH + " expecting " + TARGET_HEIGHT
                + " m (scale x" + baseScale + ", yaw +" + facingOffset + " deg), clips: "
                + clipLine());
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
        boolean moving = entity.speed() > 0.3f;
        String wanted = moving ? walkClip : idleClip;
        // It walks slowly and it does not hurry: the animation runs at the creature's own pace,
        // never faster.
        float wantedSpeed = moving
                ? MathUtils.clamp(entity.speed() / 1.6f, 0.55f, 1.15f)
                : 1f;
        if (!wanted.equals(currentClip)) {
            animation.animate(wanted, 1, wantedSpeed, null, 0.4f);
            currentClip = wanted;
            clipTimeScale = wantedSpeed;
        } else if (Math.abs(wantedSpeed - clipTimeScale) > 0.12f) {
            animation.animate(wanted, -1, wantedSpeed, null, 0.3f);
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
     * A tall figure, built out of boxes: long arms, narrow torso, a head that is slightly too small
     * for the shoulders. Solid black, like the model, so it reads as the same absence.
     */
    private static Model buildFigure() {
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        Material skin = new Material(ColorAttribute.createDiffuse(0f, 0f, 0f, 1f),
                ColorAttribute.createAmbient(0f, 0f, 0f, 1f),
                ColorAttribute.createSpecular(0f, 0f, 0f, 1f));

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

        return builder.end();
    }
}
