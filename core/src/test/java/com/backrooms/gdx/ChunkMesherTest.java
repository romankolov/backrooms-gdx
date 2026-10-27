package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.backrooms.gdx.chunk.ChunkBuild;
import com.backrooms.gdx.chunk.ChunkMesher;
import com.backrooms.gdx.light.LightBaker;
import com.backrooms.gdx.world.ChunkData;
import com.backrooms.gdx.world.ChunkSampler;
import com.backrooms.gdx.world.WorldGen;

public class ChunkMesherTest {

    private static final int STRIDE = 12;

    @Test
    public void meshIsWellFormed() {
        WorldGen gen = new WorldGen(555);
        for (int chunk = -2; chunk <= 2; chunk++) {
            ChunkData data = ChunkSampler.sample(gen, chunk, chunk * 3 - 1);
            ChunkBuild build = ChunkMesher.build(gen, data);

            assertTrue("chunks must have geometry", build.vertexCount() > 0);
            assertEquals("indices come in triangles", 0, build.indexCount() % 3);
            assertEquals("every quad uses two triangles", build.vertexCount() * 6 / 4,
                    build.indexCount());

            int vertices = build.vertexCount();
            for (int i = 0; i < build.indices.length; i++) {
                assertTrue("index out of range", build.indices[i] >= 0
                        && build.indices[i] < vertices);
            }
            for (int v = 0; v < vertices; v++) {
                int base = v * STRIDE;
                for (int c = 0; c < 3; c++) {
                    assertFalse("vertex position is NaN", Float.isNaN(build.vertices[base + c]));
                }
                float nx = build.vertices[base + 3];
                float ny = build.vertices[base + 4];
                float nz = build.vertices[base + 5];
                assertTrue("normals must be axis aligned",
                        Math.abs(Math.abs(nx) + Math.abs(ny) + Math.abs(nz) - 1f) < 1e-4f);
                assertTrue("material id in range", build.vertices[base + 10] >= 0f
                        && build.vertices[base + 10] <= 3f);
                float u = build.vertices[base + 8];
                float w = build.vertices[base + 9];
                assertTrue("lightmap uv in range", u > -0.2f && u < 1.2f);
                assertTrue("lightmap uv in range", w > -0.2f && w < 1.2f);
            }
        }
    }

    @Test
    public void boundsCoverTheChunk() {
        WorldGen gen = new WorldGen(77);
        ChunkData data = ChunkSampler.sample(gen, 0, 0);
        ChunkBuild build = ChunkMesher.build(gen, data);
        assertTrue(build.minX <= 0f && build.maxX >= WorldGen.CHUNK_SIZE);
        assertTrue(build.minZ <= 0f && build.maxZ >= WorldGen.CHUNK_SIZE);
        assertTrue(build.minY < build.maxY);
        assertEquals(-0.5f, build.minX, 0.001f);
        assertEquals(WorldGen.CHUNK_SIZE + 0.5f, build.maxX, 0.001f);
    }

    @Test
    public void lightmapIsFullyPacked() {
        WorldGen gen = new WorldGen(21);
        ChunkData data = ChunkSampler.sample(gen, 1, 1);
        ChunkBuild build = ChunkMesher.build(gen, data);
        assertEquals(LightBaker.TEXELS * LightBaker.TEXELS, build.lightmap.length);

        boolean bright = false;
        boolean dark = false;
        for (int texel : build.lightmap) {
            int r = (texel >>> 24) & 0xff;
            if (r > 60) bright = true;
            if (r < 20) dark = true;
        }
        assertTrue("there should be lit areas", bright);
        assertTrue("there should be shadowed areas", dark);
    }

    @Test
    public void neighbourChunksAgreeOnTheirSharedEdge() {
        WorldGen gen = new WorldGen(31337);
        ChunkData left = ChunkSampler.sample(gen, 0, 0);
        ChunkData right = ChunkSampler.sample(gen, 1, 0);
        // The padding of one chunk is the first cell of its neighbour, and the wall on the seam
        // line is the same wall for both.
        assertEquals(left.floorAt(ChunkData.SIZE - 1, 2), right.floorAt(1, 2), 0f);
        assertEquals(left.vWallAt(ChunkData.SIZE, 2), right.vWallAt(2, 2));
        assertEquals(left.ceilAt(ChunkData.SIZE - 1, 5), right.ceilAt(1, 5), 0f);
    }
}
