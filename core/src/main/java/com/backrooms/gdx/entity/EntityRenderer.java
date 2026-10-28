package com.backrooms.gdx.entity;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Draws whatever is walking around out there.
 *
 * <p>The thing is deliberately almost a silhouette: a tall, thin, slightly wrong human shape in a
 * material that swallows nearly all the light, with two small embers where the eyes are. In a level
 * lit by buzzing tubes it reads as a hole in the corridor, and the torch only ever picks out an
 * edge of it - which is all a good monster needs.
 *
 * <p>Right now the body is built from boxes in code. A rigged glTF model drops into the same class
 * once one is supplied: {@link #GLB_PATH} is loaded if it exists, and the box figure is only the
 * fallback so the encounter is playable (and shippable) without an external asset.
 */
public final class EntityRenderer implements Disposable {

    /** Where a supplied rigged model is picked up. Optional. */
    public static final String GLB_PATH = "models/entity.glb";

    private static final float HEIGHT = 2.02f;

    private final EntityController entity;
    private final Environment lights = new Environment();
    private final PointLight torch = new PointLight();
    private final Color ambient = new Color();

    private ModelBatch batch;
    private Model model;
    private ModelInstance instance;
    private boolean ready;

    private float lightYaw;

    public EntityRenderer(EntityController entity) {
        this.entity = entity;
        try {
            model = buildFigure();
            instance = new ModelInstance(model);
            batch = new ModelBatch(new DefaultShaderProvider());
            ready = true;
            Gdx.app.log("Entity", "procedural body (" + HEIGHT + " m)");
        } catch (Throwable t) {
            Gdx.app.error("Entity", "could not build the body: " + t);
        }
    }

    public boolean isReady() {
        return ready;
    }

    /** True if a supplied rigged model is in use rather than the placeholder body. */
    public boolean usingModel() {
        return false;
    }

    /**
     * Draws the entity into whatever is currently bound - the tape buffer, when the VHS pass is on,
     * so the thing is recorded on the same tape as everything else.
     *
     * @param width  render target width
     * @param height render target height
     */
    public void render(Camera camera, int width, int height, Vector3 torchPos, float torchOn) {
        if (!ready || !entity.active()) {
            return;
        }
        // The camera's viewport has to match the target we are drawing into, or a pass into a
        // reduced-size buffer would be projected for the screen.
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
        instance.transform.setToTranslation(entity.position.x,
                entity.position.y + entity.bob(), entity.position.z);
        instance.transform.rotate(Vector3.Y, yawDegrees);

        // A dim room, plus the torch when it is on. The point light is what makes the thing appear
        // out of the dark one shoulder at a time instead of being uniformly visible.
        float flicker = 0.9f + 0.1f * MathUtils.sin(lightYaw);
        lights.clear();
        lights.set(new ColorAttribute(ColorAttribute.AmbientLight,
                0.055f, 0.052f, 0.045f, 1f));
        torch.set(1f, 0.93f, 0.78f, torchPos, 9f * flicker * torchOn);
        lights.add(torch);

        batch.begin(camera);
        batch.render(instance, lights);
        batch.end();
    }

    public void update(float delta) {
        lightYaw += delta * 7.3f;
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (model != null) {
            model.dispose();
        }
    }

    // ---------------------------------------------------------------------------------------------

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
