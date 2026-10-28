package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        float pad = WorldGen.WALL_HALF_THICKNESS + 0.05f;
        assertEquals(-pad, build.minX, 0.001f);
        assertEquals(WorldGen.CHUNK_SIZE + pad, build.maxX, 0.001f);
        assertEquals(-pad, build.minZ, 0.001f);
        assertEquals(WorldGen.CHUNK_SIZE + pad, build.maxZ, 0.001f);
    }

    @Test
    public void lightmapIsFullyPacked() {
        WorldGen gen = new WorldGen(21);
        ChunkData data = ChunkSampler.sample(gen, 1, 1);
        ChunkBuild build = ChunkMesher.build(gen, data);
        assertEquals(LightBaker.TEXELS * LightBaker.TEXELS, build.lightmap.length);

        int min = 255;
        int max = 0;
        for (int texel : build.lightmap) {
            int r = (texel >>> 24) & 0xff;
            min = Math.min(min, r);
            max = Math.max(max, r);
        }
        assertTrue("there should be lit areas, brightest texel was " + max, max > 90);
        assertTrue("the lightmap must have contrast, min " + min + " max " + max,
                max - min > 60);
    }

    /**
     * Phones interpolate varyings at mediump (10 bit mantissa). A world coordinate of a few
     * hundred metres loses centimetres of accuracy across a 16 m face, which made the tiling tear
     * after a minute of walking, so geometry is stored relative to its chunk. Guard that.
     */
    @Test
    public void geometryIsStoredRelativeToItsChunk() {
        WorldGen gen = new WorldGen(4242);
        for (int c : new int[] {0, 1, 7, -13, 96}) {
            ChunkBuild build = ChunkMesher.build(gen, ChunkSampler.sample(gen, c, -c));
            assertEquals(c * WorldGen.CHUNK_SIZE, build.originX, 0f);
            assertEquals(-c * WorldGen.CHUNK_SIZE, build.originZ, 0f);
            float pad = WorldGen.WALL_HALF_THICKNESS + 0.01f;
            for (int v = 0; v < build.vertexCount(); v++) {
                int base = v * STRIDE;
                float x = build.vertices[base];
                float z = build.vertices[base + 2];
                float u = build.vertices[base + 6];
                float w = build.vertices[base + 7];
                assertTrue("x must be chunk local: " + x, x >= -pad
                        && x <= WorldGen.CHUNK_SIZE + pad);
                assertTrue("z must be chunk local: " + z, z >= -pad
                        && z <= WorldGen.CHUNK_SIZE + pad);
                assertTrue("texture u must stay small: " + u, Math.abs(u) < 24f);
                assertTrue("texture v must stay small: " + w, Math.abs(w) < 40f);
            }
        }
    }

    /** Chunk local UVs must not move the wallpaper: the pattern still follows the world grid. */
    @Test
    public void textureCoordinatesStillFollowTheWorldGrid() {
        WorldGen gen = new WorldGen(4242);
        for (int c : new int[] {0, 3, -8, 96}) {
            ChunkBuild build = ChunkMesher.build(gen, ChunkSampler.sample(gen, c, c));
            float originX = build.originX;
            for (int v = 0; v < build.vertexCount(); v++) {
                int base = v * STRIDE;
                float ny = Math.abs(build.vertices[base + 4]);
                if (ny < 0.5f) {
                    continue;                    // only the horizontal faces tile on X and Z
                }
                float tile = 2f;                 // carpet and ceiling
                double world = build.vertices[base] + originX + 0.001;
                double local = build.vertices[base + 6] + 0.001;
                double expected = world / tile - Math.floor(world / tile);
                double actual = local / tile - Math.floor(local / tile);
                assertTrue("u leaves the world grid at " + world + ": " + actual
                        + " vs " + expected, Math.abs(expected - actual) < 0.02);
            }
        }
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
