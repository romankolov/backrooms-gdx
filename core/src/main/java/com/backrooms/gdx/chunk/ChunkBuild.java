package com.backrooms.gdx.chunk;

/** Immutable result of building one chunk: geometry, baked light and bounds. */
public final class ChunkBuild {

    public final int chunkX;
    public final int chunkZ;
    /** World position the mesh coordinates are relative to; the renderer adds it back. */
    public final float originX;
    public final float originZ;
    public final float[] vertices;
    public final short[] indices;
    /** Packed RGBA8888 irradiance for this chunk, {@code LightBaker.TEXELS^2} texels. */
    public final int[] lightmap;

    public final float minX;
    public final float minY;
    public final float minZ;
    public final float maxX;
    public final float maxY;
    public final float maxZ;

    ChunkBuild(int chunkX, int chunkZ, float originX, float originZ,
               float[] vertices, short[] indices, int[] lightmap,
               float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.originX = originX;
        this.originZ = originZ;
        this.vertices = vertices;
        this.indices = indices;
        this.lightmap = lightmap;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public int vertexCount() {
        return vertices.length / 12;
    }

    public int indexCount() {
        return indices.length;
    }
}
