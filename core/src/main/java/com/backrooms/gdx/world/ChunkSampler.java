package com.backrooms.gdx.world;

/** Fills a {@link ChunkData} from the (pure) world generator. Safe to call from any thread. */
public final class ChunkSampler {

    private ChunkSampler() {
    }

    public static ChunkData sample(WorldGen gen, int chunkX, int chunkZ) {
        ChunkData d = new ChunkData(chunkX, chunkZ);
        int size = ChunkData.SIZE;

        for (int lj = 0; lj < size; lj++) {
            int cz = d.cellZ0 + lj;
            for (int li = 0; li < size; li++) {
                int cx = d.cellX0 + li;
                int idx = lj * size + li;
                d.floor[idx] = gen.floorY(cx, cz);
                d.ceil[idx] = gen.ceilY(cx, cz);
                d.fixture[idx] = gen.fixtureAt(cx, cz);
                if (d.fixture[idx]) {
                    d.fixtureSeed[idx] = gen.fixtureFlickerSeed(cx, cz);
                    d.fixtureAlive[idx] = gen.fixtureAlive(cx, cz);
                }
            }
        }

        for (int lj = 0; lj < size; lj++) {
            int cz = d.cellZ0 + lj;
            for (int li = 0; li <= size; li++) {
                d.vWall[lj * (size + 1) + li] = gen.vWall(d.cellX0 + li, cz);
            }
        }
        for (int lj = 0; lj <= size; lj++) {
            int cz = d.cellZ0 + lj;
            for (int li = 0; li < size; li++) {
                d.hWall[lj * size + li] = gen.hWall(d.cellX0 + li, cz);
            }
        }
        return d;
    }
}
