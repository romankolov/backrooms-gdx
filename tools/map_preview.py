#!/usr/bin/env python3
"""Renders a top-down map of the generated level.

This is a direct port of Hash/WorldGen so the layout can be inspected without a device:
floor is the carpet colour, holes are dark, walls are black lines, ceiling fixtures bright dots.

    python3 tools/map_preview.py [out.png] [seed] [cells]
"""
import math
import sys

from PIL import Image, ImageDraw

M32 = 0xFFFFFFFF


def hash2(seed, x, y):
    h = seed & M32
    h = (h * 0x27D4EBD + x) & M32
    h = ((h << 13) | (h >> 19)) & M32
    h = (h * 0x85EBCA6B) & M32
    h ^= h >> 15
    h = (h * 0xC2B2AE35 + y) & M32
    h = ((h << 11) | (h >> 21)) & M32
    h = (h * 0x27D4EBD) & M32
    h ^= h >> 13
    h = (h * 0x165667B1) & M32
    h ^= h >> 16
    return h


def hash3(seed, x, y, z):
    return hash2(hash2(seed, x, y), z, 0x9E3779B9)


def rand01(seed, x, y):
    return (hash2(seed, x, y) >> 8) / 16777216.0


def floor_div(a, b):
    return a // b  # python floors, same as the Java helper


def floor_mod(a, b):
    return a % b


def value_noise(seed, x, y):
    ix, iy = math.floor(x), math.floor(y)
    fx, fy = x - ix, y - iy
    u = fx * fx * (3 - 2 * fx)
    v = fy * fy * (3 - 2 * fy)
    a = rand01(seed, ix, iy) * 2 - 1
    b = rand01(seed, ix + 1, iy) * 2 - 1
    c = rand01(seed, ix, iy + 1) * 2 - 1
    d = rand01(seed, ix + 1, iy + 1) * 2 - 1
    return (a + (b - a) * u) + ((c + (d - c) * u) - (a + (b - a) * u)) * v


def fbm2(seed, x, y):
    return (value_noise(seed, x, y) + 0.5 * value_noise(seed ^ 0x5F356495, x * 2 + 17.3,
                                                        y * 2 - 9.1)) * 0.6667


def fbm3(seed, x, y):
    return (value_noise(seed, x, y)
            + 0.5 * value_noise(seed ^ 0x5F356495, x * 2 + 17.3, y * 2 - 9.1)
            + 0.25 * value_noise(seed ^ 0x2F9C3D11, x * 4 - 5.7, y * 4 + 3.9)) * 0.5714


CELL = 1
CHUNK = 16
MACRO = 7
LIGHT_PHASE = 3
ROOM_HEIGHT = 3.0
HOLE_DEPTH = 2.5


class Gen:
    def __init__(self, seed):
        self.seed = seed

    def is_hole(self, i, j):
        """Compact round pits: two octaves only, about 3% of the floor."""
        return fbm2(self.seed ^ 0x2545F491, i * 0.07 + 11, j * 0.07 - 7) > 0.57

    def floor_y(self, i, j):
        return -HOLE_DEPTH if self.is_hole(i, j) else 0.0

    def ceil_y(self, i, j):
        return ROOM_HEIGHT

    def v_macro(self, x):
        if floor_mod(x, MACRO) != 0:
            return False
        return (hash2(self.seed ^ 0x68E31DA4, floor_div(x, MACRO), 0x51ED) & 255) < 208

    def v_open_border(self, x, z):
        line = floor_div(x, MACRO)
        return (hash2(self.seed ^ 0x3C6EF372, line, floor_div(z, MACRO)) & 255) < 34

    def h_open_border(self, x, z):
        line = floor_div(z, MACRO)
        return (hash2(self.seed ^ 0x3C6EF373, line, floor_div(x, MACRO)) & 255) < 34

    def h_macro(self, z):
        if floor_mod(z, MACRO) != 0:
            return False
        return (hash2(self.seed ^ 0x68E31DA5, floor_div(z, MACRO), 0x51ED) & 255) < 208

    def v_door(self, x, z):
        h = hash2(self.seed ^ 0x1B56C4E9, floor_div(x, MACRO), floor_div(z, MACRO))
        door = h % (MACRO - 1)
        lz = floor_mod(z, MACRO)
        return lz == door or lz == door + 1

    def h_door(self, x, z):
        h = hash2(self.seed ^ 0x1B56C4EA, floor_div(z, MACRO), floor_div(x, MACRO))
        door = h % (MACRO - 1)
        lx = floor_mod(x, MACRO)
        return lx == door or lx == door + 1

    @staticmethod
    def interior(x, z):
        lx, lz = floor_mod(x, MACRO), floor_mod(z, MACRO)
        return 0 < lx < MACRO - 1 and 0 < lz < MACRO - 1

    def divider(self, x, z, vertical):
        """One internal wall per room, in about half of the rooms, with a gap to walk through."""
        local = floor_mod(x if vertical else z, MACRO)
        if local == 0:
            return False
        other = floor_mod(z if vertical else x, MACRO)
        if other == 0:
            return False                                   # doorway zone of the room border
        rx = floor_div(x, MACRO) if vertical else floor_div(z, MACRO)
        rz = floor_div(z, MACRO) if vertical else floor_div(x, MACRO)
        h = hash2(self.seed ^ 0x7FEB352D, rx, rz)
        if (h & 255) >= 236:
            return False                                   # this room is undivided
        if (((h >> 8) & 1) == 1) != vertical:
            return False                                   # divided the other way round
        if 1 + ((h >> 9) % (MACRO - 2)) != local:
            return False
        gap = 1 + ((h >> 15) % (MACRO - 2))
        return not (other == gap or other == (gap + 1) % MACRO)

    def v_clutter(self, x, z):
        return self.divider(x, z, True)

    def h_clutter(self, x, z):
        return self.divider(x, z, False)

    def v_wall(self, x, z):
        if self.v_macro(x):
            return not self.v_door(x, z) and not self.v_open_border(x, z)
        return self.v_clutter(x, z)

    def h_wall(self, x, z):
        if self.h_macro(z):
            return not self.h_door(x, z) and not self.h_open_border(x, z)
        return self.h_clutter(x, z)

    def fixture_at(self, x, z):
        if floor_mod(x - LIGHT_PHASE, MACRO) != 0 or floor_mod(z - LIGHT_PHASE, MACRO) != 0:
            return False
        h = hash2(self.seed ^ 0x2F9C3D11, floor_div(x - LIGHT_PHASE, MACRO),
                  floor_div(z - LIGHT_PHASE, MACRO))
        return (h & 255) < 232

    def solid_ground(self, i, j):
        return not any(self.is_hole(i + di, j + dj)
                       for di, dj in ((0, 0), (1, 0), (-1, 0), (0, 1), (0, -1)))


def main():
    out = sys.argv[1] if len(sys.argv) > 1 else "/tmp/map.png"
    seed = int(sys.argv[2]) if len(sys.argv) > 2 else 20241027
    cells = int(sys.argv[3]) if len(sys.argv) > 3 else 128
    gen = Gen(seed)
    scale = 4
    img = Image.new("RGB", (cells * scale, cells * scale))
    px = img.load()
    # Level 0 is flat: the carpet is one colour and a hole is simply a dark square.
    for j in range(cells):
        for i in range(cells):
            if gen.is_hole(i, j):
                colour = (26, 24, 18)
            else:
                colour = (188, 172, 108)
            for y in range(j * scale, j * scale + scale):
                for x in range(i * scale, i * scale + scale):
                    px[x, y] = colour
    draw = ImageDraw.Draw(img)
    for j in range(cells):
        for i in range(cells):
            if gen.v_wall(i, j):
                draw.rectangle([i * scale - 1, j * scale, i * scale + 1,
                                j * scale + scale], fill=(10, 8, 6))
            if gen.h_wall(i, j):
                draw.rectangle([i * scale, j * scale - 1, i * scale + scale,
                                j * scale + 1], fill=(10, 8, 6))
            if gen.fixture_at(i, j):
                draw.ellipse([i * scale + 1, j * scale + 1, i * scale + scale - 1,
                              j * scale + scale - 1], fill=(255, 255, 220))
    # chunk grid
    for c in range(0, cells + 1, CHUNK):
        draw.line([c * scale, 0, c * scale, cells * scale], fill=(255, 60, 60))
        draw.line([0, c * scale, cells * scale, c * scale], fill=(255, 60, 60))
    img.save(out)
    walls = sum(1 for j in range(cells) for i in range(cells)
                if gen.v_wall(i, j) or gen.h_wall(i, j))
    deep = sum(1 for j in range(cells) for i in range(cells) if gen.is_hole(i, j))
    lights = sum(1 for j in range(cells) for i in range(cells) if gen.fixture_at(i, j))
    print("cells=%d walls=%d (%.1f%%) holes=%d (%.1f%%) lights=%d"
          % (cells * cells, walls, 100.0 * walls / (cells * cells), deep,
             100.0 * deep / (cells * cells), lights))
    print("wrote", out)


if __name__ == "__main__":
    main()
