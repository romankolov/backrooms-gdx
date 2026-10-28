package com.backrooms.gdx.mesh;

/**
 * Growable vertex/index buffer for chunk geometry.
 *
 * <p>Vertex layout (12 floats): position(3) normal(3) tileUV(2) lightmapUV(2) material(1) aux(1).
 * All geometry is axis aligned rectangles, so a single primitive covers every face in the level.
 *
 * <p>Positions and texture coordinates are stored <em>relative to the chunk</em>. Phones run
 * fragment shaders at {@code mediump} precision (10 bit mantissa, roughly three decimal digits), so
 * a world coordinate of a few hundred metres interpolated across a 16 m face loses centimetres of
 * accuracy and the tiling visibly tears. Keeping every value a phone has to interpolate small fixes
 * that at the source. The offsets subtracted here are whole numbers of texture tiles, so the pattern
 * still lines up across chunk borders; the renderer passes the chunk origin back to the shader.
 */
public final class MeshBuilder {

    public static final int STRIDE = 12;

    public static final int MAT_WALL = 0;
    public static final int MAT_FLOOR = 1;
    public static final int MAT_CEILING = 2;
    public static final int MAT_PANEL = 3;

    private float[] vertices = new float[STRIDE * 1024];
    private short[] indices = new short[1024];
    private int floatsUsed;
    private int indicesUsed;

    /** Wallpaper tile size in metres: the shader scales {@code uv} by 0.8. */
    public static final float WALL_TILE = 1.25f;
    /** Carpet and ceiling tile size in metres: the shader scales {@code uv} by 0.5. */
    public static final float FLOOR_TILE = 2f;

    private final float originX;
    private final float originZ;
    private final float wallBaseX;
    private final float wallBaseZ;
    private final float floorBaseX;
    private final float floorBaseZ;
    private final float lmOriginX;
    private final float lmOriginZ;
    private final float lmScale;
    private float lmOffX;
    private float lmOffZ;
    private float uvOffU;
    private float uvOffV;

    /**
     * @param originX world X of the chunk origin; positions and UVs are stored relative to it
     * @param originZ world Z of the chunk origin
     */
    public MeshBuilder(float originX, float originZ,
                       float lmOriginX, float lmOriginZ, float lmScale) {
        this.originX = originX;
        this.originZ = originZ;
        this.wallBaseX = tileBase(originX, WALL_TILE);
        this.wallBaseZ = tileBase(originZ, WALL_TILE);
        this.floorBaseX = tileBase(originX, FLOOR_TILE);
        this.floorBaseZ = tileBase(originZ, FLOOR_TILE);
        this.lmOriginX = lmOriginX;
        this.lmOriginZ = lmOriginZ;
        this.lmScale = lmScale;
    }

    /** Shifts the tiling of the following rectangles so large surfaces do not repeat in step. */
    public void setUvOffset(float u, float v) {
        this.uvOffU = u;
        this.uvOffV = v;
    }

    /** Nudges the lightmap lookup of the following rectangles, e.g. off a thin wall into the room. */
    public void setLightmapOffset(float dx, float dz) {
        this.lmOffX = dx;
        this.lmOffZ = dz;
    }

    public int vertexCount() {
        return floatsUsed / STRIDE;
    }

    public int indexCount() {
        return indicesUsed;
    }

    public float[] vertexArray() {
        return vertices;
    }

    public short[] indexArray() {
        return indices;
    }

    public int floats() {
        return floatsUsed;
    }

    /**
     * Adds an axis aligned rectangle.
     *
     * @param o  corner of the rectangle
     * @param u  first edge vector (from {@code o})
     * @param v  second edge vector (from {@code o}); the winding is fixed up so that the face points
     *           along {@code n}
     * @param n  face normal
     * @param mat material id
     * @param aux extra vertex value: height ratio for walls, flicker phase for light panels
     */
    public void rect(float ox, float oy, float oz, float ux, float uy, float uz,
                     float vx, float vy, float vz, float nx, float ny, float nz,
                     int mat, float aux) {
        rect(ox, oy, oz, ux, uy, uz, vx, vy, vz, nx, ny, nz, mat, aux, 0f, 0f);
    }

    /**
     * Same as {@link #rect} but the aux value is derived from the vertex height:
     * {@code aux = clamp((y - gradBase) * gradScale, 0, 1)}. Used for the vertical light falloff on
     * walls, where the top of the wall is much brighter than the skirting.
     */
    public void rectGradient(float ox, float oy, float oz, float ux, float uy, float uz,
                             float vx, float vy, float vz, float nx, float ny, float nz,
                             int mat, float gradBase, float gradScale) {
        rect(ox, oy, oz, ux, uy, uz, vx, vy, vz, nx, ny, nz, mat, 0f, gradBase, gradScale);
    }

    private void rect(float ox, float oy, float oz, float ux, float uy, float uz,
                      float vx, float vy, float vz, float nx, float ny, float nz,
                      int mat, float aux, float gradBase, float gradScale) {
        // Ensure the triangle winding matches the requested normal.
        float cx = uy * vz - uz * vy;
        float cy = uz * vx - ux * vz;
        float cz = ux * vy - uy * vx;
        if (cx * nx + cy * ny + cz * nz < 0f) {
            float tx = ux, ty = uy, tz = uz;
            ux = vx; uy = vy; uz = vz;
            vx = tx; vy = ty; vz = tz;
        }

        int base = vertexCount();
        addVertex(ox, oy, oz, nx, ny, nz, mat, aux, gradBase, gradScale);
        addVertex(ox + ux, oy + uy, oz + uz, nx, ny, nz, mat, aux, gradBase, gradScale);
        addVertex(ox + ux + vx, oy + uy + vy, oz + uz + vz, nx, ny, nz, mat, aux, gradBase, gradScale);
        addVertex(ox + vx, oy + vy, oz + vz, nx, ny, nz, mat, aux, gradBase, gradScale);

        if (indicesUsed + 6 > indices.length) {
            short[] bigger = new short[indices.length * 2];
            System.arraycopy(indices, 0, bigger, 0, indicesUsed);
            indices = bigger;
        }
        indices[indicesUsed++] = (short) base;
        indices[indicesUsed++] = (short) (base + 1);
        indices[indicesUsed++] = (short) (base + 2);
        indices[indicesUsed++] = (short) base;
        indices[indicesUsed++] = (short) (base + 2);
        indices[indicesUsed++] = (short) (base + 3);
    }

    private void addVertex(float x, float y, float z, float nx, float ny, float nz,
                           int mat, float aux, float gradBase, float gradScale) {
        if (floatsUsed + STRIDE > vertices.length) {
            float[] bigger = new float[vertices.length * 2];
            System.arraycopy(vertices, 0, bigger, 0, floatsUsed);
            vertices = bigger;
        }
        // Tile UVs follow the world grid so neighbouring chunks line up, but each value has the
        // largest whole number of tiles that fits in this chunk subtracted from it. That keeps the
        // attribute small without moving the pattern: see the class comment.
        boolean wall = mat == MAT_WALL;
        float baseX = wall ? wallBaseX : floorBaseX;
        float baseZ = wall ? wallBaseZ : floorBaseZ;
        float u;
        float v;
        if (ny > 0.5f || ny < -0.5f) {
            u = x - baseX;
            v = z - baseZ;
        } else if (nx > 0.5f || nx < -0.5f) {
            u = z - baseZ;                  // wall running along Z
            v = y;
        } else {
            u = x - baseX;                  // wall running along X
            v = y;
        }
        u += uvOffU;
        v += uvOffV;
        int i = floatsUsed;
        vertices[i] = x - originX;
        vertices[i + 1] = y;
        vertices[i + 2] = z - originZ;
        vertices[i + 3] = nx;
        vertices[i + 4] = ny;
        vertices[i + 5] = nz;
        vertices[i + 6] = u;
        vertices[i + 7] = v;
        vertices[i + 8] = (x + lmOffX - lmOriginX) * lmScale;
        vertices[i + 9] = (z + lmOffZ - lmOriginZ) * lmScale;
        vertices[i + 10] = mat;
        if (gradScale != 0f) {
            float g = (y - gradBase) * gradScale;
            vertices[i + 11] = g < 0f ? 0f : (g > 1f ? 1f : g);
        } else {
            vertices[i + 11] = aux;
        }
        floatsUsed += STRIDE;
    }

    /** Largest whole number of tiles not above {@code worldCoordinate}. */
    static float tileBase(float worldCoordinate, float tile) {
        return (float) (Math.floor(worldCoordinate / (double) tile) * (double) tile);
    }
}
