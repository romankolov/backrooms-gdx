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

    public static final float FOG_COLOR_R = 0.235f;
    public static final float FOG_COLOR_G = 0.215f;
    public static final float FOG_COLOR_B = 0.135f;

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
    private boolean ready;

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
            Gdx.app.error("WorldRenderer", "Shader failed: " + shader.getLog());
        } else {
            ready = true;
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

    /** Binds the shader and the shared textures. */
    public void begin(Camera camera, float time, Vector3 cameraPos, Vector3 torchPos,
                      Vector3 torchDir, float torchOn, float fogDensity, float exposure,
                      float normalStrength, float lightRange, float debug) {
        drawnChunks = 0;
        drawnVertices = 0;
        if (!ready) {
            return;
        }
        camera.update();
        projView.set(camera.projection).mul(camera.view);

        shader.begin();
        shader.setUniformMatrix("u_projView", projView);
        shader.setUniformf("u_time", time);
        shader.setUniformf("u_camPos", cameraPos.x, cameraPos.y, cameraPos.z);
        shader.setUniformf("u_flashPos", torchPos.x, torchPos.y, torchPos.z);
        shader.setUniformf("u_flashDir", torchDir.x, torchDir.y, torchDir.z);
        shader.setUniformf("u_flashOn", torchOn);
        shader.setUniformf("u_fogColor", FOG_COLOR_R, FOG_COLOR_G, FOG_COLOR_B);
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
                continue;
            }
            float hw = (chunk.maxX - chunk.minX) * 0.5f;
            float hh = (chunk.maxY - chunk.minY) * 0.5f;
            float hd = (chunk.maxZ - chunk.minZ) * 0.5f;
            if (!camera.frustum.boundsInFrustum(chunk.minX + hw, chunk.minY + hh,
                    chunk.minZ + hd, hw, hh, hd)) {
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
