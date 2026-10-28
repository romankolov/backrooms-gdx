package com.backrooms.gdx.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

/**
 * VHS camcorder pass: the world is drawn into an offscreen buffer, then a fullscreen shader turns it
 * into tape.
 *
 * <p>The whole effect is procedural (see {@code assets/shaders/vhs.frag}), so it costs no textures
 * and no downloads. What it does cost is fill rate: the pass samples the frame a dozen times per
 * pixel, so on a phone the frame is rendered slightly below native resolution. That is free in
 * terms of looks here - tape has nowhere near that much detail to begin with.
 */
public final class VhsEffect implements Disposable {

    /** Knobs the game feeds in. */
    private float tracking;
    private float glitch;
    private float glitchTarget;
    private float intensity = 1f;
    private final float scanline;

    private final ShaderProgram shader;
    private final Mesh quad;
    private FrameBuffer buffer;
    private int screenWidth = 1;
    private int screenHeight = 1;
    private int renderWidth = 1;
    private int renderHeight = 1;
    private float scale = 0.85f;
    private boolean ready;

    public VhsEffect(float scanline) {
        this.scanline = scanline;
        quad = new Mesh(true, 4, 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"));
        quad.setVertices(new float[] {-1f, -1f, 1f, -1f, 1f, 1f, -1f, 1f});
        quad.setIndices(new short[] {0, 1, 2, 0, 2, 3});

        shader = new ShaderProgram(Gdx.files.internal("shaders/vhs.vert"),
                Gdx.files.internal("shaders/vhs.frag"));
        if (!shader.isCompiled()) {
            Gdx.app.error("BackroomsGL", "VHS shader failed: " + shader.getLog());
        } else {
            ready = true;
        }
    }

    public boolean isReady() {
        return ready;
    }

    /** Relative resolution of the offscreen buffer: 1 renders at native size. */
    public void setScale(float scale) {
        float wanted = Math.max(0.5f, Math.min(1f, scale));
        if (Math.abs(wanted - this.scale) > 0.001f) {
            this.scale = wanted;
            resize(screenWidth, screenHeight);
        }
    }

    public void resize(int width, int height) {
        screenWidth = Math.max(1, width);
        screenHeight = Math.max(1, height);
        int rw = Math.max(2, Math.round(screenWidth * scale));
        int rh = Math.max(2, Math.round(screenHeight * scale));
        if (buffer != null && rw == renderWidth && rh == renderHeight) {
            return;
        }
        renderWidth = rw;
        renderHeight = rh;
        if (buffer != null) {
            buffer.dispose();
        }
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, renderWidth, renderHeight, true);
        buffer.setFilter(com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest,
                com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest);
    }

    /** Size the camera should render at while the effect is on. */
    public int renderWidth() {
        return renderWidth;
    }

    public int renderHeight() {
        return renderHeight;
    }

    public void setIntensity(float intensity) {
        this.intensity = Math.max(0f, Math.min(1f, intensity));
    }

    public float intensity() {
        return intensity;
    }

    /** 0 = no extra glitches, 1 = the tape is in trouble (the entity is close, or something happened). */
    /** Current glitch amount, 0..1. */
    public float glitch() {
        return glitch;
    }

    public void setGlitch(float glitch) {
        this.glitchTarget = Math.max(0f, Math.min(1f, glitch));
    }

    public void update(float delta) {
        // The tracking band drifts slowly upward and occasionally slips back: a drum that is never
        // quite in sync. Faster and messier while the tape is struggling.
        tracking += delta * (0.035f + 0.09f * glitch);
        if (tracking > 1f) {
            tracking -= 1f;
        }
        // Ease the glitch so a spike decays instead of snapping.
        glitch += (glitchTarget - glitch) * Math.min(1f, delta * 4f);
        glitchTarget *= 1f - Math.min(1f, delta * 0.6f);
    }

    /** Binds the offscreen buffer. Everything drawn until {@link #end} lands on the tape. */
    public void begin() {
        buffer.begin();
    }

    /**
     * Unbinds the buffer and draws it to the screen through the shader.
     *
     * @param time full clock, seconds
     */
    public void end(float time) {
        buffer.end();
        Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        shader.begin();
        buffer.getColorBufferTexture().bind(0);
        shader.setUniformi("u_tex", 0);
        shader.setUniformf("u_resolution", renderWidth, renderHeight);
        shader.setUniformf("u_time", time % 64f);
        shader.setUniformf("u_grainTime", time % 64f);
        shader.setUniformf("u_intensity", intensity);
        shader.setUniformf("u_tracking", tracking);
        shader.setUniformf("u_glitch", glitch);
        shader.setUniformf("u_scanline", scanline);
        quad.render(shader, GL20.GL_TRIANGLES);
        shader.end();
    }

    @Override
    public void dispose() {
        if (buffer != null) {
            buffer.dispose();
        }
        quad.dispose();
        shader.dispose();
    }
}
