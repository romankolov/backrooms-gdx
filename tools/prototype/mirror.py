"""Faithful mirror of WorldGen.java (Java int semantics)."""
MACRO = 32
CORRIDOR_WIDTH = 3
FEATURE_MARGIN = 1
SEED = 20241027
HOLE_DEPTH = 6.0
TERRACE_STEP = 0.5
TERRACE_RINGS = 4

def fmod(a, b):
    return a % b        # Python's % is already floor-mod, like Java's Math.floorMod

def s32(x):
    x &= 0xffffffff
    return x - 0x100000000 if x >= 0x80000000 else x
def u32(x): return x & 0xffffffff
def jdiv(a, b): return a // b

def hash2(seed, x, y):
    h = s32(seed * 0x27d4eb2d + x)
    h = s32(u32(h << 13) | (u32(h) >> 19))
    h = s32(h * 0x85ebca6b)
    h = s32(h ^ (u32(h) >> 15))
    h = s32(h * 0xc2b2ae35 + y)
    h = s32(u32(h << 11) | (u32(h) >> 21))
    h = s32(h * 0x27d4eb2d)
    h = s32(h ^ (u32(h) >> 13))
    h = s32(h * 0x165667b1)
    h = s32(h ^ (u32(h) >> 16))
    return h

def hash3(seed, x, y, z): return hash2(hash2(seed, x, y), z, s32(0x9e3779b9))
def rand01(seed, x, y): return (u32(hash2(seed, x, y)) >> 8) * (1.0 / 16777216.0)
def floor_i4(v):
    i = int(v)
    return i - 1 if v < i else i
def value_noise(seed, x, y):
    ix, iy = floor_i4(x), floor_i4(y)
    fx, fy = x - ix, y - iy
    u = fx*fx*(3-2*fx); v = fy*fy*(3-2*fy)
    a = rand01(seed, ix, iy)*2-1; b = rand01(seed, ix+1, iy)*2-1
    c = rand01(seed, ix, iy+1)*2-1; d = rand01(seed, ix+1, iy+1)*2-1
    ab = a + (b-a)*u; cd = c + (d-c)*u
    return ab + (cd-ab)*v
def fbm2(seed, x, y):
    return (value_noise(seed, x, y) + 0.5*value_noise(s32(seed ^ 0x5f356495), x*2+17.3, y*2-9.1))*0.6667

KIND_PLAIN, KIND_GRAND, KIND_SUNKEN, KIND_PITS, KIND_PILLARS, KIND_SPURS, KIND_DARK = range(7)

class Q:
    def __init__(self):
        self.leaf = (0,0,0,0); self.kind = 0; self.pos = 0; self.axis = 0; self.node = (0,0,0,0)

def avenue_offset(stem, index):
    return 3 + (u32(hash2(s32(SEED ^ stem), index, 0x5a5a)) >> 8) % (MACRO - 9)
def avenue_width(stem, index):
    return 2 + ((u32(hash2(s32(SEED ^ stem), index, 0x5a5a)) >> 20) & 1)
def avenue_present(salt, a, b):
    if (hash2(s32(SEED ^ salt ^ 0x2222), a, jdiv(b, 4)) & 255) >= 236: return False
    return (hash2(s32(SEED ^ salt ^ 0x3333), a, b) & 255) < 226
def column_avenue(mx, mz): return avenue_present(0x5555, mx, mz)
def row_avenue(mx, mz): return avenue_present(0x6666, mz, mx)

def split_of(q, x0, z0, x1, z1):
    mx, mz = jdiv(x0, MACRO), jdiv(z0, MACRO)
    if column_avenue(mx, mz):
        ax = mx*MACRO + avenue_offset(0x1111, mx); th = avenue_width(0x1111, mx)
        if x0 == ax and x1 > ax + th: q.pos = ax + th; return 1
        if x0 < ax and x1 > ax + th: q.pos = ax; return 1
    if row_avenue(mx, mz):
        az = mz*MACRO + avenue_offset(0x7777, mz); th = avenue_width(0x7777, mz)
        if z0 == az and z1 > az + th: q.pos = az + th; return 2
        if z0 < az and z1 > az + th: q.pos = az; return 2
    w, h = x1-x0, z1-z0
    if w <= 3 and h <= 3: return 0
    hh = hash3(s32(SEED ^ 0x4444), s32(x0*31+x1), s32(z0*31+z1), w*7+h)
    big, small = max(w, h), min(w, h)
    if small <= CORRIDOR_WIDTH:
        leaf = not (big >= 17 and ((u32(hh) >> 12) & 3) == 0)
    else:
        target = 4 + ((u32(hh) >> 3) % 5)
        if big <= target: leaf = True
        elif big <= target + 2: leaf = (hh & 127) < 90
        elif big <= 15 and ((u32(hh) >> 16) & 255) < 14: leaf = True
        else: leaf = False
    if leaf: return 0
    vertical = (w > h) or (w == h and (hh & 1) == 0)
    lo, ln = (x0, w) if vertical else (z0, h)
    if ((u32(hh) >> 9) & 255) < 46 and ln >= 10:
        thin = 2 + ((u32(hh) >> 17) & 1)
        pos = lo + thin if ((u32(hh) >> 18) & 1) == 0 else lo + ln - thin
    else:
        margin = 4 if ln >= 10 else 3
        span = max(1, ln - 2*margin + 1)
        pos = lo + margin + (u32(hh) >> 8) % span
    q.pos = pos
    return 1 if vertical else 2

def kind_of(x0, z0, x1, z1):
    w, h = x1-x0, z1-z0
    if min(w, h) <= CORRIDOR_WIDTH: return KIND_PLAIN
    hh = hash3(s32(SEED ^ 0x0abc), s32(x0*13+z0), s32(x1*7+z1), w*5+h)
    roll = fmod(hh, 100)
    narrow = min(w, h)
    if roll < 63: return KIND_PLAIN
    if roll < 70: return KIND_GRAND
    if roll < 78: return KIND_SUNKEN
    if roll < 88: return KIND_PITS
    if roll < 94: return KIND_PILLARS if narrow >= 5 else KIND_PLAIN
    if roll < 98: return KIND_SPURS if narrow >= 5 else KIND_PLAIN
    return KIND_DARK

def leaf_at(cx, cz, q):
    x0 = jdiv(cx, MACRO)*MACRO; z0 = jdiv(cz, MACRO)*MACRO
    x1, z1 = x0+MACRO, z0+MACRO
    for _ in range(48):
        axis = split_of(q, x0, z0, x1, z1)
        if axis == 0: break
        if axis == 1:
            if cx < q.pos: x1 = q.pos
            else: x0 = q.pos
        else:
            if cz < q.pos: z1 = q.pos
            else: z0 = q.pos
    q.leaf = (x0, z0, x1, z1); q.kind = kind_of(x0, z0, x1, z1)
    return q

def separating_cut(a, b, q):
    x0 = jdiv(a[0], MACRO)*MACRO; z0 = jdiv(a[1], MACRO)*MACRO
    x1, z1 = x0+MACRO, z0+MACRO
    for _ in range(48):
        axis = split_of(q, x0, z0, x1, z1)
        if axis == 0: break
        divides = ((a[0] < q.pos) != (b[0] < q.pos)) if axis == 1 else ((a[1] < q.pos) != (b[1] < q.pos))
        if divides:
            q.node = (x0, z0, x1, z1); q.axis = axis
            return axis
        if axis == 1:
            if a[0] < q.pos: x1 = q.pos
            else: x0 = q.pos
        else:
            if a[1] < q.pos: z1 = q.pos
            else: z0 = q.pos
    q.leaf = (x0, z0, x1, z1); q.kind = kind_of(x0, z0, x1, z1); q.axis = 0
    return 0

def doorway(node, axis, pos, along):
    nx0, nz0, nx1, nz1 = node
    h = hash3(s32(SEED ^ 0xd00d), s32(nx0*97+nz0), s32(nx1*31+nz1), pos*7+axis)
    span0, span1 = (nz0, nz1) if axis == 1 else (nx0, nx1)
    ln = span1 - span0
    width = 2 + ((u32(h) >> 8) & 1)
    if width > ln - 2: width = max(1, ln - 2)
    span = max(1, ln - 1 - width)
    start = span0 + 1 + ((u32(h) >> 11) & 0x3ff) % span
    if start <= along < start + width: return True
    if ((u32(h) >> 20) & 3) == 0:
        w2 = 2 + ((u32(h) >> 24) & 1)
        if w2 > ln - 2: w2 = max(1, ln - 2)
        s2 = max(span0+1, span1-1-w2)
        if s2 <= along < s2 + w2 and s2 + w2 <= span1: return True
    return False

def ceil_of(q):
    x0, z0, x1, z1 = q.leaf
    w, h = x1-x0, z1-z0
    if q.kind == KIND_GRAND:
        hh = hash3(s32(SEED ^ 0x0f00), x0, z0, x1+z1)
        return 4.2 + fmod(hh, 100) * 0.01 * (5.4 - 4.2)
    if q.kind == KIND_SUNKEN: return 3.4
    if q.kind == KIND_PITS: return 3.4
    if q.kind == KIND_SPURS: return 2.8
    if min(w, h) <= CORRIDOR_WIDTH:
        hh = hash3(s32(SEED ^ 0x0ba2), x0, z0, x1+z1)
        return 2.7 + fmod(hh, 100) * 0.01 * (3.1 - 2.7)
    return 3.0

def floor_of(q, cx, cz):
    if is_hole(cx, cz, q): return -6.0
    if q.kind == KIND_SUNKEN:
        return -min(4, inset(q.leaf, cx, cz)) * 0.5
    return 0.0

def inset(leaf, cx, cz):
    x0, z0, x1, z1 = leaf
    return min(cx-x0, x1-1-cx, cz-z0, z1-1-cz)

def pillar_cell(leaf, kind, cx, cz):
    if kind != KIND_PILLARS: return False
    if inset(leaf, cx, cz) < FEATURE_MARGIN: return False
    return (cx-leaf[0]) % 3 == 1 and (cz-leaf[1]) % 3 == 1

def feature_wall(vertical, line, row, leaf, kind):
    if kind != KIND_SPURS: return False
    x0, z0, x1, z1 = leaf
    w, h = x1-x0, z1-z0
    lx, lz = line-x0, row-z0
    if vertical:
        if lx < FEATURE_MARGIN or lx > w-FEATURE_MARGIN: return False
        if lz < 1 or lz > h-2: return False
        if lx % 2 != 1: return False
        return lz % 4 != (lx//2) % 4
    if lz < FEATURE_MARGIN or lz > h-FEATURE_MARGIN: return False
    if lx < 1 or lx > w-2: return False
    if lz % 2 != 1: return False
    return lx % 4 != (lz//2) % 4

def wall(vertical, line, row, q):
    a = (line-1, row) if vertical else (line, row-1)
    b = (line, row)
    axis = separating_cut(a, b, q)
    if axis == 0:
        if pillar_cell(q.leaf, q.kind, *a) or pillar_cell(q.leaf, q.kind, *b): return True
        return feature_wall(vertical, line, row, q.leaf, q.kind)
    return not doorway(q.node, q.axis, q.pos, row if vertical else line)

def is_hole(cx, cz, q):
    leaf_at(cx, cz, q)
    if q.kind not in (KIND_PITS, KIND_PLAIN): return False
    x0, z0, x1, z1 = q.leaf
    if min(x1-x0, z1-z0) <= CORRIDOR_WIDTH + 1: return False
    if inset(q.leaf, cx, cz) < FEATURE_MARGIN: return False
    if q.kind == KIND_PITS:
        return fbm2(s32(SEED ^ 0x2545f491), cx*0.13+11, cz*0.13-7) > -0.10
    hh = hash3(s32(SEED ^ 0x1791), q.leaf[0], q.leaf[1], q.leaf[2]+q.leaf[3])
    if (hh & 15) != 0: return False
    return fbm2(s32(SEED ^ 0x6f31c9d7), cx*0.31, cz*0.31) > 0.74


LIGHT_SPACING = 6
def fixture_at(cx, cz):
    if cx % LIGHT_SPACING != 0 or cz % LIGHT_SPACING != 0: return False
    h = hash2(s32(SEED ^ 0x2f9c3d11), cx // LIGHT_SPACING, cz // LIGHT_SPACING)
    if (h & 255) >= 226: return False
    q = Q(); leaf_at(cx, cz, q)
    if is_hole(cx, cz, q): return False
    return q.kind != KIND_DARK
