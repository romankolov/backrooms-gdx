package com.backrooms.gdx.world;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.backrooms.gdx.chunk.ChunkBuild;

/** Render side handle for one chunk: GPU buffers plus its bounds. */
public final class Chunk {

    public static final int PENDING = 0;
    public static final int BUILDING = 1;
    public static final int READY = 2;
    public static final int LIVE = 3;

    public final int chunkX;
    public final int chunkZ;
    public final float centerX;
    public final float centerZ;

    public int state = PENDING;
    public ChunkBuild pendingBuild;

    public Mesh mesh;
    public Texture lightmap;

    public float minX;
    public float minY;
    public float minZ;
    public float maxX;
    public float maxY;
    public float maxZ;

    /** Distance from the player's chunk, refreshed every frame for priority and unloading. */
    public int distance;

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.centerX = chunkX * WorldGen.CHUNK_SIZE + WorldGen.CHUNK_SIZE * 0.5f;
        this.centerZ = chunkZ * WorldGen.CHUNK_SIZE + WorldGen.CHUNK_SIZE * 0.5f;
    }

    public long key() {
        return key(chunkX, chunkZ);
    }

    public static long key(int x, int z) {
        return ((long) x << 32) ^ (z & 0xffffffffL);
    }

    public static int keyX(long key) {
        return (int) (key >> 32);
    }

    public static int keyZ(long key) {
        return (int) key;
    }

    public void dispose() {
        if (mesh != null) {
            mesh.dispose();
            mesh = null;
        }
        if (lightmap != null) {
            lightmap.dispose();
            lightmap = null;
        }
        pendingBuild = null;
        state = PENDING;
    }
}
