package com.backrooms.gdx.world;

/**
 * CPU side cell data for one chunk, including a one-cell border so that faces on the chunk seam can
 * be culled exactly like interior faces. Everything here is filled by a worker thread and is
 * read-only afterwards.
 */
public final class ChunkData {

    /** Cells of padding around the chunk. */
    public static final int PAD = 1;
    /** Cells per edge including padding. */
    public static final int SIZE = WorldGen.CHUNK_CELLS + 2 * PAD;

    public final int chunkX;
    public final int chunkZ;
    /** World cell coordinate of local cell (0,0). */
    public final int cellX0;
    public final int cellZ0;

    /** Floor height per cell, indexed {@code [lj * SIZE + li]}. */
    public final float[] floor = new float[SIZE * SIZE];
    /** Ceiling height per cell. */
    public final float[] ceil = new float[SIZE * SIZE];
    /** Ceiling fixture present per cell. */
    public final boolean[] fixture = new boolean[SIZE * SIZE];
    /** Fixture phase/seed per cell (0 when steady). */
    public final int[] fixtureSeed = new int[SIZE * SIZE];
    /** False for burned-out tubes: geometry stays, light does not. */
    public final boolean[] fixtureAlive = new boolean[SIZE * SIZE];

    /** Vertical walls: line index {@code li in [0..SIZE]} between cells (li-1,lj) and (li,lj). */
    public final boolean[] vWall = new boolean[(SIZE + 1) * SIZE];
    /** Horizontal walls: line index {@code lj in [0..SIZE]} between cells (li,lj-1) and (li,lj). */
    public final boolean[] hWall = new boolean[SIZE * (SIZE + 1)];

    public ChunkData(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.cellX0 = chunkX * WorldGen.CHUNK_CELLS - PAD;
        this.cellZ0 = chunkZ * WorldGen.CHUNK_CELLS - PAD;
    }

    public float floorAt(int li, int lj) {
        return floor[lj * SIZE + li];
    }

    public float ceilAt(int li, int lj) {
        return ceil[lj * SIZE + li];
    }

    public boolean vWallAt(int li, int lj) {
        return vWall[lj * (SIZE + 1) + li];
    }

    public boolean hWallAt(int li, int lj) {
        return hWall[lj * SIZE + li];
    }

    /** Suggests a per-vertex flicker phase for a fixture, in [0,1). */
    public float fixturePhase(int li, int lj) {
        int seed = fixtureSeed[lj * SIZE + li];
        if (seed == 0) {
            return 0f;
        }
        return (seed >>> 8 & 1023) / 1024f;
    }

    public boolean fixtureSteady(int li, int lj) {
        return fixtureSeed[lj * SIZE + li] == 0;
    }

    public boolean fixtureAliveAt(int li, int lj) {
        return fixtureAlive[lj * SIZE + li];
    }
}
