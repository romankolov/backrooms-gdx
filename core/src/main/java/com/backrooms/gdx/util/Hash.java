package com.backrooms.gdx.util;

/**
 * Deterministic integer hashing and value noise.
 *
 * <p>Everything the world generator produces is a pure function of integer grid coordinates and the
 * world seed, which is what makes the map infinite: any chunk can be generated on its own, in any
 * order, on any thread, and always comes out the same.
 */
public final class Hash {

    private Hash() {
    }

    /** 32-bit integer hash of two coordinates and a seed (variant of the xxhash/murmur finaliser). */
    public static int hash2(int seed, int x, int y) {
        int h = seed;
        h = h * 0x27d4eb2d + x;
        h = (h << 13) | (h >>> 19);
        h *= 0x85ebca6b;
        h ^= h >>> 15;
        h = h * 0xc2b2ae35 + y;
        h = (h << 11) | (h >>> 21);
        h *= 0x27d4eb2d;
        h ^= h >>> 13;
        h *= 0x165667b1;
        h ^= h >>> 16;
        return h;
    }

    public static int hash3(int seed, int x, int y, int z) {
        return hash2(hash2(seed, x, y), z, 0x9e3779b9);
    }

    /** Uniform float in [0,1). */
    public static float rand01(int seed, int x, int y) {
        return (hash2(seed, x, y) >>> 8) * (1.0f / 16777216.0f);
    }

    public static int floorDiv(int a, int b) {
        int q = a / b;
        if ((a ^ b) < 0 && q * b != a) {
            q--;
        }
        return q;
    }

    public static int floorMod(int a, int b) {
        int m = a % b;
        return m < 0 ? m + b : m;
    }

    public static float floor(float v) {
        int i = (int) v;
        return (v < i) ? i - 1 : i;
    }

    public static int floorInt(float v) {
        int i = (int) v;
        return (v < i) ? i - 1 : i;
    }

    public static float clamp(float v, float min, float max) {
        return v < min ? min : (v > max ? max : v);
    }

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float smoothstep(float edge0, float edge1, float x) {
        float t = clamp((x - edge0) / (edge1 - edge0), 0f, 1f);
        return t * t * (3f - 2f * t);
    }

    /** Smooth lattice value noise in [-1,1]. */
    public static float valueNoise(int seed, float x, float y) {
        int ix = floorInt(x);
        int iy = floorInt(y);
        float fx = x - ix;
        float fy = y - iy;
        float u = fx * fx * (3f - 2f * fx);
        float v = fy * fy * (3f - 2f * fy);
        float a = rand01(seed, ix, iy) * 2f - 1f;
        float b = rand01(seed, ix + 1, iy) * 2f - 1f;
        float c = rand01(seed, ix, iy + 1) * 2f - 1f;
        float d = rand01(seed, ix + 1, iy + 1) * 2f - 1f;
        return lerp(lerp(a, b, u), lerp(c, d, u), v);
    }

    /** Two-octave fractal value noise in roughly [-1,1]. */
    public static float fbm2(int seed, float x, float y) {
        float sum = valueNoise(seed, x, y);
        sum += 0.5f * valueNoise(seed ^ 0x5f356495, x * 2f + 17.3f, y * 2f - 9.1f);
        return sum * 0.6667f;
    }

    public static float fbm3(int seed, float x, float y) {
        float sum = valueNoise(seed, x, y);
        sum += 0.5f * valueNoise(seed ^ 0x5f356495, x * 2f + 17.3f, y * 2f - 9.1f);
        sum += 0.25f * valueNoise(seed ^ 0x2f9c3d11, x * 4f - 5.7f, y * 4f + 3.9f);
        return sum * 0.5714f;
    }

    /** Wraps a float into [0,1). Used for texture tiling in tests. */
    public static float fract(float v) {
        return v - (float) Math.floor(v);
    }
}
