package com.backrooms.gdx.world;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.backrooms.gdx.chunk.ChunkBuild;
import com.backrooms.gdx.chunk.ChunkMesher;

/**
 * Streaming world: keeps a ring of chunks around the player.
 *
 * <p>Chunks are generated, meshed and light-baked on background threads; the render thread only
 * uploads a couple of finished chunks per frame so the frame rate never dips. Chunks behind the
 * player are released again, so memory stays flat no matter how far you walk.
 */
public final class World {

    private final WorldGen gen;
    private final ObjectMap<Long, Chunk> chunks = new ObjectMap<>();
    private final Array<Chunk> live = new Array<>();
    private final ConcurrentLinkedQueue<ChunkBuild> completed = new ConcurrentLinkedQueue<>();
    private final ExecutorService pool;
    private final AtomicInteger inFlight = new AtomicInteger();
    private final int maxInFlight;
    private final ChunkUploader uploader;

    private int radius = 3;
    private volatile boolean disposed;
    private int loadedCount;
    private int lastBuildMillis;

    public World(int seed, int radius, ChunkUploader uploader) {
        this.gen = new WorldGen(seed);
        this.radius = radius;
        this.uploader = uploader;
        int threads = Math.max(1, Math.min(3, Runtime.getRuntime().availableProcessors() - 1));
        this.pool = Executors.newFixedThreadPool(threads, new ThreadFactory() {
            private final AtomicInteger n = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "chunk-builder-" + n.incrementAndGet());
                t.setDaemon(true);
                t.setPriority(Thread.NORM_PRIORITY - 1);
                return t;
            }
        });
        this.maxInFlight = threads * 2;
    }

    public WorldGen gen() {
        return gen;
    }

    public int radius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = Math.max(1, Math.min(8, radius));
    }

    public Array<Chunk> live() {
        return live;
    }

    public int loadedCount() {
        return loadedCount;
    }

    public int pendingCount() {
        return inFlight.get();
    }

    public int lastBuildMillis() {
        return lastBuildMillis;
    }

    /** Runs on the render thread. */
    public void update(float playerX, float playerZ, int uploadBudget) {
        if (disposed) {
            return;
        }
        int pcx = worldToChunk(playerX);
        int pcz = worldToChunk(playerZ);
        int r = radius;

        // 1. Request missing chunks, nearest first.
        int budget = maxInFlight - inFlight.get();
        if (budget > 0) {
            for (int ring = 0; ring <= r && budget > 0; ring++) {
                for (int dz = -ring; dz <= ring && budget > 0; dz++) {
                    for (int dx = -ring; dx <= ring && budget > 0; dx++) {
                        if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) {
                            continue;
                        }
                        if (dx * dx + dz * dz > (r + 0.5f) * (r + 0.5f)) {
                            continue;
                        }
                        Chunk chunk = chunks.get(Chunk.key(pcx + dx, pcz + dz));
                        if (chunk == null) {
                            chunk = new Chunk(pcx + dx, pcz + dz);
                            chunks.put(chunk.key(), chunk);
                        }
                        if (chunk.state == Chunk.PENDING) {
                            submit(chunk);
                            budget--;
                        }
                    }
                }
            }
        }

        // 2. Upload finished builds (GPU work, kept to a small budget per frame).
        int uploads = 0;
        ChunkBuild build;
        while (uploads < uploadBudget && (build = completed.poll()) != null) {
            Chunk chunk = chunks.get(Chunk.key(build.chunkX, build.chunkZ));
            int ddx = build.chunkX - pcx;
            int ddz = build.chunkZ - pcz;
            boolean wanted = chunk != null && ddx * ddx + ddz * ddz <= (r + 1) * (r + 1);
            if (wanted) {
                chunk.pendingBuild = build;
                uploader.upload(chunk, build);
                chunk.state = Chunk.LIVE;
                chunk.pendingBuild = null;
                uploads++;
            }
        }

        // 3. Release chunks that fell out of range.
        loadedCount = 0;
        Array<Long> toRemove = null;
        for (ObjectMap.Entry<Long, Chunk> e : chunks) {
            Chunk c = e.value;
            int dx = c.chunkX - pcx;
            int dz = c.chunkZ - pcz;
            c.distance = Math.max(Math.abs(dx), Math.abs(dz));
            if (c.distance > r + 1) {
                if (toRemove == null) {
                    toRemove = new Array<>();
                }
                toRemove.add(e.key);
            } else if (c.state == Chunk.LIVE) {
                loadedCount++;
            }
        }
        if (toRemove != null) {
            for (int i = 0; i < toRemove.size; i++) {
                Chunk c = chunks.remove(toRemove.get(i));
                if (c != null) {
                    c.dispose();
                }
            }
        }

        // 4. Rebuild the render list.
        live.clear();
        for (ObjectMap.Entry<Long, Chunk> e : chunks) {
            if (e.value.state == Chunk.LIVE) {
                live.add(e.value);
            }
        }
    }

    public static int worldToChunk(float v) {
        return (int) Math.floor(v / WorldGen.CHUNK_SIZE);
    }

    private void submit(final Chunk chunk) {
        chunk.state = Chunk.BUILDING;
        inFlight.incrementAndGet();
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    long start = System.currentTimeMillis();
                    ChunkData data = ChunkSampler.sample(gen, chunk.chunkX, chunk.chunkZ);
                    ChunkBuild build = ChunkMesher.build(gen, data);
                    lastBuildMillis = (int) (System.currentTimeMillis() - start);
                    completed.add(build);
                } catch (Throwable t) {
                    // Never let a worker die silently: mark the chunk for a retry.
                    chunk.state = Chunk.PENDING;
                    t.printStackTrace();
                } finally {
                    inFlight.decrementAndGet();
                }
            }
        });
    }

    /** True when every chunk around a position is already on the GPU. */
    public boolean isAreaReady(float x, float z) {
        int pcx = worldToChunk(x);
        int pcz = worldToChunk(z);
        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = -1; dx <= 1; dx++) {
                Chunk c = chunks.get(Chunk.key(pcx + dx, pcz + dz));
                if (c == null || c.state != Chunk.LIVE) {
                    return false;
                }
            }
        }
        return true;
    }

    public void dispose() {
        disposed = true;
        pool.shutdownNow();
        // Queued builds are dropped by shutdownNow() and will never reach their finally block.
        inFlight.set(0);
        for (ObjectMap.Entry<Long, Chunk> e : chunks) {
            e.value.dispose();
        }
        chunks.clear();
        live.clear();
        completed.clear();
    }
}
