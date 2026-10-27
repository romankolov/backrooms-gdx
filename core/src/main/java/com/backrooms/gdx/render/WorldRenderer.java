package com.backrooms.gdx.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.backrooms.gdx.chunk.ChunkBuild;
import com.backrooms.gdx.world.Chunk;
import com.backrooms.gdx.world.ChunkUploader;
import com.backrooms.gdx.world.World;

/**
 * Draws the world: one shader, six tiled surface textures, one baked lightmap per chunk.
 * A whole chunk is a single draw call and everything behind the camera is culled on the CPU.
 */
public final class WorldRenderer implements ChunkUploader, Disposable {

    private static final int UNIT_FLOOR = 0;
    private static final int UNIT_FLOOR_N = 1;
    private static final int UNIT_WALL = 2;
    private static final int UNIT_WALL_N = 3;
    private static final int UNIT_CEIL = 4;
    private static final int UNIT_CEIL_N = 5;
    private static final int UNIT_LIGHTMAP = 6;

    /**
     * The colour the corridors fade into, expressed as it finally appears on screen (sRGB 0..1).
     * The shader mixes fog <em>before</em> exposure, filmic tone mapping and gamma, so the value it
     * receives has to be inverted through that whole grade - see {@link #fogInput}. Using the
     * display value directly made the distance wash out to pale cream.
     */
    public static final float FOG_COLOR_R = 0.170f;
    public static final float FOG_COLOR_G = 0.158f;
    public static final float FOG_COLOR_B = 0.108f;

    /** Displayed fog colour -> the linear value the shader must mix towards. */
    public static float fogInput(float display, float exposure) {
        double target = Math.pow(Math.max(display, 0f), 1.0 / 0.4545);
        return (float) (inverseAces(target) / exposure);
    }

    private static double acesCurve(double x) {
        double v = (x * (2.51 * x + 0.03)) / (x * (2.43 * x + 0.59) + 0.14);
        return Math.max(0.0, Math.min(1.0, v));
    }

    private static double inverseAces(double y) {
        double lo = 0.0;
        double hi = 16.0;
        for (int i = 0; i < 40; i++) {
            double mid = 0.5 * (lo + hi);
            if (acesCurve(mid) < y) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        return 0.5 * (lo + hi);
    }

    private static final VertexAttribute[] ATTRIBUTES = {
            new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"),
            new VertexAttribute(VertexAttributes.Usage.Normal, 3, "a_normal"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_uv"),
            new VertexAttribute(VertexAttributes.Usage.Generic, 2, "a_lm"),
            new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_mat"),
            new VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_aux"),
    };

    private final ShaderProgram shader;
    private final Texture floorTex;
    private final Texture floorNormal;
    private final Texture wallTex;
    private final Texture wallNormal;
    private final Texture ceilTex;
    private final Texture ceilNormal;

    private final Matrix4 projView = new Matrix4();
    private final Vector3 tmp = new Vector3();

    private int drawnChunks;
    private int drawnVertices;
    private int skippedNoMesh;
    private int skippedCulled;
    private boolean ready;
    private String shaderLog = "";

    public WorldRenderer() {
        floorTex = load("textures/carpet_diffuse.jpg", true);
        floorNormal = load("textures/carpet_normal.jpg", true);
        wallTex = load("textures/wall_diffuse.jpg", true);
        wallNormal = load("textures/wall_normal.jpg", true);
        ceilTex = load("textures/ceiling_diffuse.jpg", true);
        ceilNormal = load("textures/ceiling_normal.jpg", true);

        shader = new ShaderProgram(Gdx.files.internal("shaders/world.vert"),
                Gdx.files.internal("shaders/world.frag"));
        if (!shader.isCompiled()) {
            shaderLog = shader.getLog().replace('\n', '|');
            Gdx.app.error("BackroomsGL", "SHADER FAILED: " + shaderLog);
        } else {
            ready = true;
            Gdx.app.log("BackroomsGL", "world shader compiled");
        }
    }

    private static Texture load(String path, boolean mipmaps) {
        Texture texture = new Texture(Gdx.files.internal(path), mipmaps);
        if (mipmaps) {
            texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
            texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
            texture.setAnisotropicFilter(4f);
        } else {
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
        }
        return texture;
    }

    public boolean isReady() {
        return ready;
    }

    public int drawnChunks() {
        return drawnChunks;
    }

    public int drawnVertices() {
        return drawnVertices;
    }

    /** One-line summary used by the perf log so a device-only render failure is visible in logcat. */
    public String statusLine() {
        return "ready=" + ready + " drawn=" + drawnChunks + " noMesh=" + skippedNoMesh
                + " culled=" + skippedCulled + (shaderLog.isEmpty() ? "" : " shaderLog=" + shaderLog);
    }

    /** Binds the shader and the shared textures. */
    public void begin(Camera camera, float time, Vector3 cameraPos, Vector3 torchPos,
                      Vector3 torchDir, float torchOn, float fogDensity, float exposure,
                      float normalStrength, float lightRange, float debug) {
        drawnChunks = 0;
        drawnVertices = 0;
        skippedNoMesh = 0;
        skippedCulled = 0;
        if (!ready) {
            return;
        }
        camera.update();
        projView.set(camera.projection).mul(camera.view);

        shader.begin();
        shader.setUniformMatrix("u_projView", projView);
        // The full clock goes to the vertex shader (highp); the fragment shader gets a wrapped
        // value that mediump can hold exactly. See the precision notes in the shader sources.
        shader.setUniformf("u_time", time);
        shader.setUniformf("u_grainTime", time % 64f);
        shader.setUniformf("u_camPos", cameraPos.x, cameraPos.y, cameraPos.z);
        shader.setUniformf("u_flashPos", torchPos.x, torchPos.y, torchPos.z);
        shader.setUniformf("u_flashDir", torchDir.x, torchDir.y, torchDir.z);
        shader.setUniformf("u_flashOn", torchOn);
        shader.setUniformf("u_fogColor", fogInput(FOG_COLOR_R, exposure),
                fogInput(FOG_COLOR_G, exposure), fogInput(FOG_COLOR_B, exposure));
        shader.setUniformf("u_fogDensity", fogDensity);
        shader.setUniformf("u_exposure", exposure);
        shader.setUniformf("u_normalStrength", normalStrength);
        shader.setUniformf("u_lightRange", lightRange);
        shader.setUniformf("u_debug", debug);
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bind(UNIT_FLOOR, floorTex, "u_floorTex");
        bind(UNIT_FLOOR_N, floorNormal, "u_floorNormal");
        bind(UNIT_WALL, wallTex, "u_wallTex");
        bind(UNIT_WALL_N, wallNormal, "u_wallNormal");
        bind(UNIT_CEIL, ceilTex, "u_ceilTex");
        bind(UNIT_CEIL_N, ceilNormal, "u_ceilNormal");
        shader.setUniformi("u_lightmap", UNIT_LIGHTMAP);
    }

    private void bind(int unit, Texture texture, String uniform) {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + unit);
        texture.bind();
        shader.setUniformi(uniform, unit);
    }

    public void draw(World world, Camera camera) {
        if (!ready) {
            return;
        }
        com.badlogic.gdx.utils.Array<Chunk> chunks = world.live();
        for (int i = 0; i < chunks.size; i++) {
            Chunk chunk = chunks.get(i);
            if (chunk.mesh == null || chunk.lightmap == null) {
                skippedNoMesh++;
                continue;
            }
            float hw = (chunk.maxX - chunk.minX) * 0.5f;
            float hh = (chunk.maxY - chunk.minY) * 0.5f;
            float hd = (chunk.maxZ - chunk.minZ) * 0.5f;
            if (!camera.frustum.boundsInFrustum(chunk.minX + hw, chunk.minY + hh,
                    chunk.minZ + hd, hw, hh, hd)) {
                skippedCulled++;
                continue;
            }
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + UNIT_LIGHTMAP);
            chunk.lightmap.bind();
            chunk.mesh.render(shader, GL20.GL_TRIANGLES);
            drawnChunks++;
            drawnVertices += chunk.mesh.getNumVertices();
        }
    }

    public void end() {
        if (ready) {
            shader.end();
        }
        // Leave the state the 2D pass expects: SpriteBatch binds its font and UI textures to unit 0.
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
    }

    @Override
    public void upload(Chunk chunk, ChunkBuild build) {
        if (chunk.mesh != null) {
            chunk.mesh.dispose();
        }
        if (build.vertexCount() == 0) {
            chunk.mesh = null;
        } else {
            Mesh mesh = new Mesh(true, build.vertexCount(), build.indexCount(), ATTRIBUTES);
            mesh.setVertices(build.vertices);
            mesh.setIndices(build.indices);
            chunk.mesh = mesh;
        }

        if (chunk.lightmap != null) {
            chunk.lightmap.dispose();
        }
        com.badlogic.gdx.graphics.Pixmap pixmap = toPixmap(build.lightmap);
        Texture texture = new Texture(pixmap, false);
        pixmap.dispose();
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
        chunk.lightmap = texture;

        chunk.minX = build.minX;
        chunk.minY = build.minY;
        chunk.minZ = build.minZ;
        chunk.maxX = build.maxX;
        chunk.maxY = build.maxY;
        chunk.maxZ = build.maxZ;
    }

    private static com.badlogic.gdx.graphics.Pixmap toPixmap(int[] rgba) {
        int size = com.backrooms.gdx.light.LightBaker.TEXELS;
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(
                size, size, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        java.nio.ByteBuffer buffer = pixmap.getPixels();
        for (int i = 0; i < rgba.length; i++) {
            int c = rgba[i];
            buffer.put((byte) ((c >>> 24) & 0xff));
            buffer.put((byte) ((c >>> 16) & 0xff));
            buffer.put((byte) ((c >>> 8) & 0xff));
            buffer.put((byte) (c & 0xff));
        }
        buffer.position(0);
        return pixmap;
    }

    @Override
    public void dispose() {
        if (shader != null) {
            shader.dispose();
        }
        floorTex.dispose();
        floorNormal.dispose();
        wallTex.dispose();
        wallNormal.dispose();
        ceilTex.dispose();
        ceilNormal.dispose();
    }
}
