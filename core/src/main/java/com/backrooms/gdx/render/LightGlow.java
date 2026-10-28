package com.backrooms.gdx.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.backrooms.gdx.world.WorldGen;

/**
 * The glow around the ceiling tubes.
 *
 * <p>The world shader draws the panel itself as an emissive rectangle, but emission alone reads as
 * white card rather than a light. What sells it is the spill: the diffuser lit evenly across its
 * whole face, its edges softening, and a short falloff onto the tiles around it. That is a separate
 * additive pass, one quad per nearby fixture, lying in the ceiling plane at the panel's own height
 * and slightly oversized so the spill has somewhere to go. It is cheap (a few dozen quads, one draw
 * call) and needs no blur pass, no extra framebuffer and no dynamic lights in the world shader.
 *
 * <p>Fixtures that flicker use the same flicker curve as the panel geometry, so the halo breathes
 * with its tube instead of staying lit while the light around it dies.
 */
public final class LightGlow implements Disposable {

    /** How far the glow is drawn. Beyond this the fog has eaten the fixture anyway. */
    private static final float RANGE = 30f;
    /** Half the width of the diffuser geometry, in metres (see {@code ChunkMesher}). */
    private static final float PANEL_HALF = 0.46f;
    /** The glow quad is this much wider than the panel, so the spill has room to fade. */
    private static final float GLOW_SCALE = 1.75f;
    private static final int MAX_FIXTURES = 96;
    private static final int FLOATS_PER_VERTEX = 7;      // centre(3) + corner(2) + size + strength
    private static final int VERTICES_PER_QUAD = 6;

    private static final float[] CORNERS = {
            -1f, -1f, 1f, -1f, 1f, 1f,
            -1f, -1f, 1f, 1f, -1f, 1f,
    };

    private final ShaderProgram shader;
    private final Mesh mesh;
    private final float[] vertices = new float[MAX_FIXTURES * VERTICES_PER_QUAD * FLOATS_PER_VERTEX];
    private final Matrix4 projView = new Matrix4();
    private final Vector3 forward = new Vector3();
    private boolean ready;
    private int quads;

    public LightGlow() {
        shader = new ShaderProgram(Gdx.files.internal("shaders/glow.vert"),
                Gdx.files.internal("shaders/glow.frag"));
        if (!shader.isCompiled()) {
            Gdx.app.error("BackroomsGL", "glow shader failed: " + shader.getLog());
        } else {
            ready = true;
        }
        mesh = new Mesh(false, MAX_FIXTURES * VERTICES_PER_QUAD, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_center"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 2, "a_corner"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_size"),
                new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_strength"));
    }

    public boolean isReady() {
        return ready;
    }

    /** Number of haloes drawn last frame, for the perf line. */
    public int quadCount() {
        return quads;
    }

    /**
     * Draws every fixture near the camera into whatever target is bound.
     *
     * @param width  render target width
     * @param height render target height
     */
    public void render(Camera camera, WorldGen gen, float time, int width, int height) {
        if (!ready || gen == null) {
            return;
        }
        quads = 0;
        camera.update();
        projView.set(camera.projection).mul(camera.view);
        forward.set(camera.direction);

        int ci = MathUtils.floor(camera.position.x);
        int cj = MathUtils.floor(camera.position.z);
        int i0 = WorldGen.fixtureIndex(camera.position.x - RANGE);
        int i1 = WorldGen.fixtureIndex(camera.position.x + RANGE);
        int j0 = WorldGen.fixtureIndex(camera.position.z - RANGE);
        int j1 = WorldGen.fixtureIndex(camera.position.z + RANGE);

        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                if (quads >= MAX_FIXTURES) {
                    break;
                }
                int cx = WorldGen.fixtureCell(i);
                int cz = WorldGen.fixtureCell(j);
                if (!gen.fixtureAt(cx, cz) || !gen.fixtureAlive(cx, cz)) {
                    continue;
                }
                float x = cx + 0.5f;
                // Flat against the panel, a hair below it so the two do not fight for the same
                // depth: this is the panel glowing, not a bulb hanging under it.
                // The panel geometry itself is drawn at ceil - 0.03; five millimetres below that is
                // close enough to look like the same surface and far enough not to z-fight.
                float y = gen.ceilY(cx, cz) - 0.035f;
                float z = cz + 0.5f;
                float dx = x - camera.position.x;
                float dy = y - camera.position.y;
                float dz = z - camera.position.z;
                float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
                if (distance > RANGE || distance < 0.001f) {
                    continue;
                }
                // Behind the camera: the quad would be clipped anyway, and an oversized additive
                // quad is expensive fill rate on a phone.
                if (dx * forward.x + dy * forward.y + dz * forward.z < -PANEL_HALF * GLOW_SCALE) {
                    continue;
                }
                // A dying tube's panel is dimmed by the same flicker curve in the world shader.
                float power = gen.fixturePower(cx, cz) * flicker(gen, cx, cz, time);
                if (power <= 0.01f) {
                    continue;
                }
                // Fade with distance so a fixture at the edge of the draw distance does not pop.
                float fade = MathUtils.clamp(1f - distance / RANGE, 0f, 1f);
                fade *= fade;
                // Fog: the glow should not survive where the level itself has faded to haze.
                float strength = power * fade * 0.85f;
                // The quad is the panel's own footprint plus the spill margin. It does not grow with
                // distance: a rectangle on the ceiling has a fixed size, and letting it swell with
                // distance is what turned it into a ball hanging under the fitting.
                emit(x, y, z, PANEL_HALF * GLOW_SCALE, strength);
                quads++;
            }
        }

        if (quads == 0) {
            return;
        }

        Gdx.gl.glViewport(0, 0, width, height);
        // Depth test stays on so a light in the next room does not glow through the wall, but nothing
        // is written: this pass only adds light to what is already there. The quad lies almost in
        // the panel's own plane, so LEQUAL is what lets it win the depth test against it.
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);       // purely additive: light adds, never hides
        Gdx.gl.glDepthMask(false);

        shader.begin();
        shader.setUniformMatrix("u_projView", projView);
        shader.setUniformf("u_panelHalf", PANEL_HALF);
        mesh.setVertices(vertices, 0, quads * VERTICES_PER_QUAD * FLOATS_PER_VERTEX);
        mesh.render(shader, GL20.GL_TRIANGLES, 0, quads * VERTICES_PER_QUAD);
        shader.end();

        Gdx.gl.glDepthMask(true);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void emit(float x, float y, float z, float size, float strength) {
        int base = quads * VERTICES_PER_QUAD * FLOATS_PER_VERTEX;
        for (int v = 0; v < VERTICES_PER_QUAD; v++) {
            int o = base + v * FLOATS_PER_VERTEX;
            vertices[o] = x;
            vertices[o + 1] = y;
            vertices[o + 2] = z;
            vertices[o + 3] = CORNERS[v * 2];
            vertices[o + 4] = CORNERS[v * 2 + 1];
            vertices[o + 5] = size;
            vertices[o + 6] = strength;
        }
    }

    /**
     * Mirrors the flicker curve in {@code world.vert}, so halo and panel breathe together. Steady
     * tubes return 1, flickering ones a value that dips and occasionally drops out.
     */
    public static float flicker(WorldGen gen, int cellX, int cellZ, float time) {
        int seed = gen.fixtureFlickerSeed(cellX, cellZ);
        if (seed == 0) {
            return 1f;
        }
        float phase = (seed & 1023) / 1023f;
        float slow = MathUtils.sin(time * 23f + phase * 37f);
        float fast = MathUtils.sin(time * 61f + phase * 13f);
        float base = 0.86f + 0.09f * slow + 0.05f * fast;
        float drop = MathUtils.sin(time * 2.3f + phase * 57f) >= 0.965f ? 1f : 0f;
        return base * (1f - drop * 0.9f);
    }

    @Override
    public void dispose() {
        shader.dispose();
        mesh.dispose();
    }
}
