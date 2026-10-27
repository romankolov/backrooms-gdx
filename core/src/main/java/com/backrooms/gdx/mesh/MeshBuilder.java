package com.backrooms.gdx.mesh;

/**
 * Growable vertex/index buffer for chunk geometry.
 *
 * <p>Vertex layout (12 floats): position(3) normal(3) worldUV(2) lightmapUV(2) material(1) aux(1).
 * All geometry is axis aligned rectangles, so a single primitive covers every face in the level.
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

    private final float lmOriginX;
    private final float lmOriginZ;
    private final float lmScale;
    private float lmOffX;
    private float lmOffZ;
    private float uvOffU;
    private float uvOffV;

    public MeshBuilder(float lmOriginX, float lmOriginZ, float lmScale) {
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
        float u;
        float v;
        if (ny > 0.5f || ny < -0.5f) {
            u = x;
            v = z;
        } else if (nx > 0.5f || nx < -0.5f) {
            u = z;
            v = y;
        } else {
            u = x;
            v = y;
        }
        u += uvOffU;
        v += uvOffV;
        int i = floatsUsed;
        vertices[i] = x;
        vertices[i + 1] = y;
        vertices[i + 2] = z;
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
}
