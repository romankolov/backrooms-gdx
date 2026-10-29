package com.backrooms.gdx;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.backrooms.gdx.chunk.ChunkBuild;
import com.backrooms.gdx.world.Chunk;
import com.backrooms.gdx.world.ChunkUploader;
import com.backrooms.gdx.world.World;

/** Background chunk streaming: chunks get built, uploaded and released again. */
public class WorldStreamingTest {

    @Test(timeout = 120000)
    public void buildsAndUnloadsChunksAroundThePlayer() throws Exception {
        final AtomicInteger uploads = new AtomicInteger();
        final AtomicInteger visibleVertices = new AtomicInteger();
        ChunkUploader uploader = new ChunkUploader() {
            @Override
            public void upload(Chunk chunk, ChunkBuild build) {
                uploads.incrementAndGet();
                visibleVertices.addAndGet(build.vertexCount());
            }
        };
        World world = new World(4242, 2, uploader);
        try {
            for (int i = 0; i < 400 && uploads.get() < 9; i++) {
                world.update(0f, 0f, 4);
                Thread.sleep(20);
            }
            assertTrue("chunks should stream in", uploads.get() >= 9);
            assertTrue("chunks should contain geometry", visibleVertices.get() > 1000);
            assertTrue("live list should be populated", world.live().size > 0);

            int before = world.live().size;
            // Teleport far away: everything around the old position must be released.
            for (int i = 0; i < 400 && world.live().size > 0
                    && world.loadedCount() < before; i++) {
                world.update(9000f, 9000f, 4);
                Thread.sleep(10);
            }
            for (int i = 0; i < 100; i++) {
                world.update(9000f, 9000f, 4);
                Thread.sleep(5);
            }
            for (int i = 0; i < world.live().size; i++) {
                Chunk chunk = world.live().get(i);
                assertTrue("chunks far behind the player must be released",
                        World.worldToChunk(9000f) - chunk.chunkX < 4);
            }
        } finally {
            world.dispose();
        }
        // Workers are interrupted by dispose(); give them a moment to unwind.
        for (int i = 0; i < 40 && world.pendingCount() > 0; i++) {
            Thread.sleep(25);
        }
        assertTrue("builds must stop when the world is disposed", world.pendingCount() <= 1);
    }
}
