package com.backrooms.gdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Animation;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 * Draws whatever is walking around out there.
 *
 * <p>Two bodies, one class. If a rigged model is dropped at {@link #GLB_PATH} it is loaded through
 * gdx-gltf, lit by the torch, and animated from the model's own clips ({@code walk}/{@code idle},
 * matched loosely). If there is no model - which is the case on a fresh checkout - the fallback is
 * a figure built out of boxes here in code: tall, thin, nearly lightless, with two small embers
 * where the eyes are. That keeps the encounter playable and shippable without an external asset,
 * and means a broken or missing model can never take the game down.
 *
 * <p>Either way it is drawn into whatever framebuffer is bound - the tape buffer when the VHS pass
 * is on - after the level, so walls occlude it properly. Being hidden by a wall is the player's
 * only defence, so it has to be real depth and not a trick.
 */
public final class EntityRenderer implements Disposable {

    /** Where a supplied rigged model is picked up. Optional. */
    public static final String GLB_PATH = "models/entity.glb";

    private static final Color TORCH_COLOR = new Color(1f, 0.93f, 0.78f, 1f);

    private final EntityController entity;

    // --- procedural fallback ------------------------------------------------------------------
    private final Environment lights = new Environment();
    private final PointLight torch = new PointLight();
    private ModelBatch batch;
    private Model model;
    private ModelInstance instance;

    // --- rigged glTF model --------------------------------------------------------------------
    private SceneManager scenes;
    private SceneAsset asset;
    private Scene scene;
    private AnimationController animation;
    private PointLightEx modelTorch;
    private String walkClip;
    private String idleClip;
    private String currentClip;

    private boolean ready;
    private boolean modelLoaded;
    private float pendingDelta;
    private float flickerPhase;

    public EntityRenderer(EntityController entity) {
        this.entity = entity;
        modelLoaded = loadModel();
        if (!modelLoaded) {
            buildFallback();
        }
    }

    public boolean isReady() {
        return ready;
    }

    /** True if a supplied rigged model is in use rather than the placeholder body. */
    public boolean usingModel() {
        return modelLoaded;
    }

    /** Animation update. Kept separate from drawing so a paused game does not animate. */
    public void update(float delta) {
        pendingDelta = delta;
        flickerPhase += delta * 7.3f;
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

        float yawDegrees = entity.yaw * MathUtils.radiansToDegrees;
        float x = entity.position.x;
        float y = entity.position.y + entity.bob();
        float z = entity.position.z;
        // The torch is not steady: a dying battery and a cheap filament, so it breathes.
        float flicker = 0.88f + 0.12f * MathUtils.sin(flickerPhase)
                + 0.04f * MathUtils.sin(flickerPhase * 3.7f);

        if (modelLoaded) {
            scene.modelInstance.transform.setToTranslation(x, y, z);
            scene.modelInstance.transform.rotate(Vector3.Y, yawDegrees);
            if (modelTorch != null) {
                modelTorch.position.set(torchPos);
                modelTorch.intensity = 9f * flicker * torchOn;
            }
            updateClip();
            scenes.setCamera(camera);
            scenes.update(pendingDelta);
            scenes.render();
            return;
        }

        lights.clear();
        lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.055f, 0.052f, 0.045f, 1f));
        torch.set(TORCH_COLOR.r, TORCH_COLOR.g, TORCH_COLOR.b, torchPos, 9f * flicker * torchOn);
        lights.add(torch);

        instance.transform.setToTranslation(x, y, z);
        instance.transform.rotate(Vector3.Y, yawDegrees);
        batch.begin(camera);
        batch.render(instance, lights);
        batch.end();
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
            scenes = new SceneManager(64);
            scenes.setAmbientLight(0.07f);
            scene = new Scene(asset.scene);
            scenes.addScene(scene);
            animation = scene.animationController;
            pickClips(asset.animations);
            modelTorch = new PointLightEx().set(TORCH_COLOR, new Vector3(), 0f, 14f);
            scenes.environment.add(modelTorch);
            ready = true;
            Gdx.app.log("Entity", "rigged model loaded: " + GLB_PATH
                    + " (idle=" + idleClip + ", walk=" + walkClip + ")");
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
     * Clips are matched loosely by name because every modeller names them differently. Anything
     * that looks like walking animates movement, anything that looks like standing animates rest,
     * and if the names say nothing at all the first two clips are used in that order.
     */
    private void pickClips(Array<Animation> animations) {
        if (animations == null || animations.size == 0) {
            return;
        }
        for (Animation a : animations) {
            String id = a.id == null ? "" : a.id.toLowerCase(java.util.Locale.ROOT);
            if (walkClip == null && (id.contains("walk") || id.contains("run") || id.contains("move"))) {
                walkClip = a.id;
            }
            if (idleClip == null && (id.contains("idle") || id.contains("stand")
                    || id.contains("breath"))) {
                idleClip = a.id;
            }
        }
        if (idleClip == null) {
            idleClip = animations.get(0).id;
        }
        if (walkClip == null) {
            walkClip = animations.size > 1 ? animations.get(1).id : idleClip;
        }
    }

    private void updateClip() {
        if (animation == null || walkClip == null) {
            return;
        }
        String wanted = entity.speed() > 0.6f ? walkClip : idleClip;
        if (wanted == null || wanted.equals(currentClip)) {
            return;
        }
        animation.setAnimation(wanted, -1);
        currentClip = wanted;
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

        // legs: thin, slightly bowed, feet planted a little wide
        builder.node().id = "legs";
        builder.part("legL" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.20f, 0f, -0.07f, 0.13f, 0.98f, 0.14f);
        builder.part("legR" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(0.07f, 0f, -0.07f, 0.13f, 0.98f, 0.14f);

        // torso: narrow, and it leans forward a touch
        builder.part("torso" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.21f, 0.94f, -0.12f, 0.42f, 0.72f, 0.24f);

        // shoulders and long arms, hanging past the hips
        builder.part("shoulders" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.33f, 1.52f, -0.13f, 0.66f, 0.12f, 0.26f);
        builder.part("armL" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.37f, 0.72f, -0.10f, 0.11f, 0.84f, 0.13f);
        builder.part("armR" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(0.26f, 0.72f, -0.10f, 0.11f, 0.84f, 0.13f);

        // neck and a head that is too small, set slightly forward
        builder.part("neck" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.05f, 1.66f, -0.14f, 0.10f, 0.12f, 0.10f);
        builder.part("head" + id++, GL20.GL_TRIANGLES, attrs, skin)
                .box(-0.10f, 1.77f, -0.19f, 0.20f, 0.23f, 0.21f);

        // eyes, just behind the face plane so they glow out of the sockets
        builder.part("eyeL" + id++, GL20.GL_TRIANGLES, attrs, eyes)
                .box(-0.065f, 1.87f, -0.20f, 0.035f, 0.022f, 0.012f);
        builder.part("eyeR" + id++, GL20.GL_TRIANGLES, attrs, eyes)
                .box(0.030f, 1.87f, -0.20f, 0.035f, 0.022f, 0.012f);

        return builder.end();
    }
}
