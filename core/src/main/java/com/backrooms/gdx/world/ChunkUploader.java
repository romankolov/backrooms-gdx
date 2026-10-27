package com.backrooms.gdx.world;

import com.backrooms.gdx.chunk.ChunkBuild;

/** Implemented by the renderer: moves a finished build onto the GPU (must run on the GL thread). */
public interface ChunkUploader {
    void upload(Chunk chunk, ChunkBuild build);
}
