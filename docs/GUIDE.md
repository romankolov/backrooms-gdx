# Building an infinite Backrooms level in Java + libGDX

This is the implementation guide for the game in this repository: a first person level that never
ends, generated from a seed, streamed in chunks and finished with baked fluorescent light. It is
written as five phases, in the order they have to be built, and every phase ends with something you
can look at on a phone.

Everything here is the code that actually ships in `core/src/main/java`. Where a snippet is
shortened, the class named above it is the full version.

---

## What the five phases are

| Phase | You build | You can see at the end of it |
|---|---|---|
| 1 | App skeleton and the chunk streaming ring | A flat grey floor that follows you, at 60 fps |
| 2 | The generator: rooms, corridors, features | An endless yellow level with walls and halls |
| 3 | The mesher: cells to triangles | Real walls, holes, steps and ceiling lamps |
| 4 | Baked lighting | Light pools under the tubes, dark corners |
| 5 | Renderer, player, audio, packaging | The game, as an APK on a phone |

Two rules run through all of it, and every "pitfall" note below is really one of them in disguise:

1. **The render thread is the only thread allowed to touch OpenGL**, and it may not allocate.
2. **Generation is a pure function of (seed, x, z).** No `Random`, no state, no order dependence.
   Two chunks generated on two different phones must be identical, and a chunk generated twice must
   be identical to itself.

---

## Architecture

```
                       ┌──────────────────────────────┐
                       │        BackroomsGame         │  Game, owns Assets + screens
                       └──────────────┬───────────────┘
                                      │
                 ┌────────────────────┴────────────────────┐
                 │                                      │
        ┌────────▼────────┐                    ┌────────▼────────┐
        │  TitleScreen    │                    │   PlayScreen    │  GL thread
        └─────────────────┘                    └────────┬────────┘
                                                        │
        ┌───────────────────────────────────────────────┼───────────────────────────┐
        │                                               │                           │
 ┌──────▼──────┐   ┌─────────────┐   ┌──────────────┐  ┌▼────────────┐   ┌──────────▼────────┐
 │   Player    │   │  Touch-     │   │ WorldRenderer│  │  VhsEffect  │   │   Hud / VhsOsd    │
 │  physics    │   │  Controls   │   │  + culling   │  │  FBO post   │   │  crosshair, menu  │
 └──────┬──────┘   └─────────────┘   └──────┬───────┘  └─────────────┘   └───────────────────┘
        │                                   │ upload(Chunk, ChunkBuild)  ▲ GL thread only
        │                                   │                            │
        │                          ┌────────┴─────────┐                  │
        │                          │       World      │  chunk ring, budgets
        │                          └────────┬─────────┘
        │                                   │ submit(Chunk)
        │                          ┌────────▼─────────┐
        │                          │  worker threads  │  WorldGen → ChunkData → ChunkMesher
        │                          │  (2, low prio)   │  → LightBaker → ChunkBuild
        │                          └────────┬─────────┘
        │                                   │ ChunkBuild (float[] + short[] + int[] lightmap)
        └──────── read-only queries ──▶ WorldGen (pure, seeded, stateless)
```

The important line in that picture is the one marked *GL thread only*, and its mirror image below:
a worker produces a plain `ChunkBuild` of Java arrays and never learns that a GPU exists.

---

# Phase 1 — Skeleton and the streaming ring

**Goal:** a window (and later an APK) that renders a floor and streams chunks around the player
without ever stalling. Everything later is bolted onto this loop.

## 1.1 The Gradle layout

Three modules: `core` (all the game), `android` (launcher + manifest), `desktop` (the same game on a
JVM, which is what the screenshot CI job runs).

```gradle
// build.gradle (root)
ext {
    gdxVersion = "1.13.1"
    appName    = "Backrooms"
    appPackage = "com.backrooms.gdx"
}

// core/build.gradle
dependencies {
    api "com.badlogicgames.gdx:gdx:$gdxVersion"
    testImplementation "junit:junit:4.13.2"
}
```

`core` has no third-party dependency at all. That is not an accident: everything on screen is either
generated geometry or a texture the project owns, so there is no loader, no scene format and no
reflection in the hot path.

Android needs `configChanges` for orientation and keyboard-less input, `minSdk 21` and
`android:hardwareAccelerated="true"` (the default). The launcher class must be fully qualified in the
manifest, or the APK will install and then not start:

```xml
<activity android:name="com.backrooms.gdx.android.AndroidLauncher" ... />
```

## 1.2 The cell, and why a chunk is 16 cells

The whole world is a grid of **1 m × 1 m cells**. A wall is the *edge between two cells*, not a cell
of its own, which is what keeps rooms integers and corridors exactly one cell wide.

```java
public static final float CELL        = 1f;
public static final int   CHUNK_CELLS = 16;
public static final float CHUNK_SIZE  = CHUNK_CELLS * CELL;
```

16 is the compromise: a small chunk means small mesh buffers and short upload spikes; a large chunk
means fewer draw calls. At 16 m you get roughly 60–120 visible chunks inside a 60 m fog distance,
which is fine for one draw call each on a mid-range phone.

`ChunkData` holds the CPU side of one chunk **with a one cell border**, so the faces on the chunk
seam can be culled exactly like interior faces:

```java
public final class ChunkData {
    public static final int PAD  = 1;
    public static final int SIZE = WorldGen.CHUNK_CELLS + 2 * PAD;   // 18

    public final float[]   floor  = new float[SIZE * SIZE];
    public final float[]   ceil   = new float[SIZE * SIZE];
    public final boolean[] fixture = new boolean[SIZE * SIZE];
    public final boolean[] vWall  = new boolean[(SIZE + 1) * SIZE];  // edge between (li-1,lj),(li,lj)
    public final boolean[] hWall  = new boolean[SIZE * (SIZE + 1)];
    ...
}
```

`vWall[li + lj * (SIZE+1)]` is the vertical wall *line* at `li`, i.e. the edge between cell
`li - 1` and cell `li`. Storing walls on lines rather than on cells removes the entire class of
"does this cell have a north wall or does its neighbour" bugs.

## 1.3 The ring

`World` owns every chunk near the player and nothing else:

```java
public void update(float playerX, float playerZ, int uploadBudget) {
    int pcx = worldToChunk(playerX), pcz = worldToChunk(playerZ);

    // 1. request missing chunks, nearest ring first, never more than maxInFlight
    int budget = maxInFlight - inFlight.get();
    for (int ring = 0; ring <= radius && budget > 0; ring++) { ... submit(chunk); budget--; }

    // 2. upload at most uploadBudget finished builds per frame
    for (int i = 0; i < uploadBudget; i++) {
        ChunkBuild b = completed.poll();
        if (b == null) break;
        uploader.upload(b.chunk, b);        // GL work, right here, on this thread
        b.chunk.state = Chunk.READY;
    }

    // 3. release chunks outside radius + 1
}
```

Three budgets, and the game is playable because of all three:

* **`maxInFlight = threads * 2`** — the workers are never more than a couple of chunks ahead, so
  memory does not spike when you turn around.
* **`uploadBudget`** (2 per frame) — a chunk upload is a `glBufferData`, which is the one thing in
  this design that can cost milliseconds. Two per frame is invisible; ten is a stutter.
* **ring order** — the nearest missing chunk is always the next one built, so the world fills in
  from the player outwards rather than in scanline order.

States: `PENDING → BUILDING → READY`, and `ChunkBuild` is the only thing that crosses the boundary.

## 1.4 Pitfalls in phase 1

* **Do not call GL from a worker.** `Mesh`, `Texture`, `ShaderProgram` are not thread safe and the
  Android driver will not forgive you. The worker fills `float[]`; the render thread owns the `Mesh`.
* **`ObjectMap<Long, Chunk>` boxes a `Long` per lookup.** At 60 fps with ~100 chunks that is
  thousands of allocations per second — survivable but pointless. Use a `LongMap`/`IntMap` keyed on
  `(chunkX & 0xffff) << 16 | (chunkZ & 0xffff)` for the inner loop.
* **`ConcurrentLinkedQueue.poll()` allocates nothing** (good), but `Array.removeIndex` on `live`
  shifts; iterate backwards when releasing.
* **Never `new ChunkData()` per frame.** One `ChunkData` per live chunk, reused across rebuilds.
* **Cap the worker pool at 3** and lower its priority. On a 4-core phone, a 4th mesher thread steals
  the frame.
* An `ExecutorService` with a daemon `ThreadFactory` means the app still exits when Android kills it.

**Checkpoint:** the pause menu already prints the measurement that matters all the way through this
guide — `123 fps  17 chunks drawn  0 queued`.

---

# Phase 2 — The generator

**Goal:** an endless level that is interesting to walk through. Deterministic, seamless, and never
encloses the player.

## 2.1 Why not tiles

The obvious design — a set of prefabs (straight hall, L, T, 4-way, pillar room) with N/S/E/W sockets
and compatibility bitmasks — produces a level that *reads as tiles*: the same three shapes, the same
proportions, everywhere. This generator does the opposite. It partitions the plane recursively,
which means every room is a different size and every corridor a different length, and no two areas
of the map have the same silhouette.

## 2.2 Hash first

There is no `Random` anywhere in the generator. Every decision is a hash of the coordinates:

```java
/** 32-bit integer hash of two coordinates and a seed (a murmur-style finaliser). */
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

/** Floor division that survives negative coordinates. */
public static int floorDiv(int a, int b) {
    int q = a / b;
    if ((a ^ b) < 0 && q * b != a) q--;
    return q;
}
```

**Only `+ - * /` and integer ops.** `Math.sin`, `StrictMath`, `pow` and even `Math.sqrt` can differ
in the last bit between the desktop JVM and ART, and a difference in a split decision moves a wall
by several metres. For the same reason, floats are used for *values*, never for *decisions*: the
decision band is always an integer roll.

## 2.3 The partition

The plane is divided into 32 m macro tiles (`MACRO = 32`, exactly two chunks). Each macro tile is
split recursively until a leaf is small enough, then the leaf is a room.

```java
/** 0 = this rectangle is a room, 1 = split it on x, 2 = split it on z. */
private int splitOf(int x0, int z0, int x1, int z1, Query q) {
    int w = x1 - x0, h = z1 - z0;
    if (w <= 3 && h <= 3) return 0;

    int hh = Hash.hash3(seed ^ 0x4444, x0 * 31 + x1, z0 * 31 + z1, w * 7 + h);
    int big = Math.max(w, h), small = Math.min(w, h);
    boolean leaf;
    if (small <= CORRIDOR_WIDTH) {                 // a corridor: long and thin, left whole
        leaf = !(big >= 17 && ((hh >>> 12) & 3) == 0);
    } else {
        int target = 4 + ((hh >>> 3) % 5);         // this rectangle wants to be 4..8 m across
        leaf = big <= target || (big <= target + 2 && (hh & 127) < 90);
    }
    if (leaf) return 0;

    boolean vertical = (w > h) || (w == h && (hh & 1) == 0);   // always cut the long axis
    int lo = vertical ? x0 : z0, len = vertical ? w : h;
    int pos;
    if (((hh >>> 9) & 255) < 46 && len >= 10) {
        int thin = 2 + ((hh >>> 17) & 1);           // shave a 2-3 cell corridor off the edge
        pos = ((hh >>> 18) & 1) == 0 ? lo + thin : lo + len - thin;
    } else {
        int margin = len >= 10 ? 4 : 3;
        pos = lo + margin + ((hh >>> 8) & 0x7fffffff) % (len - 2 * margin + 1);
    }
    q.splitPos = pos;
    q.splitAxis = vertical ? 1 : 2;
    return q.splitAxis;
}
```

Two details matter:

* **`splitOf` is a pure function of the rectangle.** That is what makes the tree reproducible without
  building it: `leafAt(cx, cz)` walks *down* from the macro tile, one split at a time, until it
  reaches the leaf containing the cell. There is no tree object, no recursion limit and no
  generation order to get wrong. The search is bounded (48 levels) as a belt-and-braces guard against
  a split rule that fails to shrink the rectangle.
* **Every split is a *cut*, and the cut is where the walls go.** `separatingCut(a, b)` finds the
  lowest common ancestor of two neighbouring cells; if the two cells are in different leaves, the
  ancestor's split line is a wall — except at the doorway cell.

```java
private boolean wall(boolean vertical, int line, int row, Query q) {
    int ax = vertical ? line - 1 : line, az = row;
    int bx = vertical ? line : line,     bz = vertical ? row : row - 1;
    int axis = separatingCut(ax, az, bx, bz, q);
    if (axis == 0) {                        // same leaf: pillars, spurs, nothing
        return featureWall(vertical, line, row, q);
    }
    return !doorway(q.node, q.axis, q.pos, vertical ? row : line);
}
```

`doorway()` hashes the cut itself — its rectangle and its position — and opens a 2–3 cell opening
somewhere along it, plus a second one at the far end one time in four (that is where the loops come
from). Because it is keyed on the *cut*, not on the room, both sides of the wall agree whether a
doorway exists. That is the whole trick, and it is what a socket bitmask is trying to buy you with
far less variety.

Leaf kinds are also hash rolls, so the map has a mix of:

| kind | what it looks like | share |
|---|---|---|
| `PLAIN` | 3.0 m ceiling, the default room (and every corridor) | ~80 % |
| `PITS` | random square holes to −6 m | ~7 % |
| `GRAND` | 4.2–5.4 m ceiling, taller than the halls around it | ~5 % |
| `SUNKEN` | floor terraced down in 0.5 m rings, ceiling kept | ~5 % |
| `PILLARS` | a lattice of free-standing pillars | ~1 % |
| `SPURS` | 2.8 m ceiling, short stubs of wall inside | ~1 % |
| `DARK` | the tubes are out in here | <1 % |

(Shares by room over a 140 × 140 m region — 717 rooms; by area the plain share is ~77 % because
corridors and small rooms take less space. See `tools/prototype/stats.py`.)

## 2.4 The one rule you must not break

A pit is a hole in the floor. If a pit reaches the doorway, the room is sealed and the player can
walk into a dead pocket of the level that it is impossible to leave. Two guards, both cheap:

```java
private boolean isHoleIn(Query q, int cellX, int cellZ) {
    if (q.kind != KIND_PITS && q.kind != KIND_PLAIN) return false;
    // Never a pit where the room is only a corridor wide: it must stay a way through.
    if (Math.min(q.x1 - q.x0, q.z1 - q.z0) <= CORRIDOR_WIDTH + 1) return false;
    // A ring of floor inside every room keeps every doorway connected.
    if (inset(q, cellX, cellZ) < FEATURE_MARGIN) return false;
    ...
}
```

`FEATURE_MARGIN` is one cell of floor inside every edge; the corridor guard catches the rooms that
are not much wider than a corridor, where a ring would not be enough and the pit would touch both
ends and seal the way through.

To prove it, generate a large region and flood-fill it. The prototype mirror in
`tools/prototype/` does exactly that, and prints:

```
cells 19600   hole ratio 0.0201   rooms 717   mean area 29.6
kinds {PLAIN 578, GRAND 37, SUNKEN 33, PITS 50, PILLARS 9, SPURS 6, DARK 4}
reachable 95.80 %   unreachable rooms 7 of 717
```

Those seven are rooms walled off *by design* — pockets you can see into through a doorway and cannot
reach, which is the Backrooms, not a bug. Anything below ~92 % means the doorway hash has drifted.
Turning the corridor guard off gives 95.42 % and more holes, so it is in the file for a reason.

## 2.5 Spawn and the public API

`findSpawn` spirals out from the origin until it finds a cell that is solid ground, has a 3×3 of
solid ground around it, and is not a pit; `findLanding(x, z)` does the same job for a teleport or a
respawn after falling.

The read-only API the rest of the game uses is deliberately small and cheap enough to call per
frame: `floorY`, `ceilY`, `vWall`, `hWall`, `isHole`, `isPillar`, `isSolidGround`, `fixtureAt`,
`fixtureAlive`, `wallDistance` (for the audio), `hasLineOfSight` (for the light bake).

## 2.6 Pitfalls in phase 2

* **Integer division of negatives.** `-7 / 32 == 0` in Java (truncates toward zero), which puts cell
  −7 in macro tile 0 and cell −32 in macro tile −1 — one tile wider than the others. Always
  `Math.floorDiv(cx, MACRO)`.
* **`splitOf` must not read anything mutable.** A thread-local scratch `Query` is fine; a cached
  `Query` shared between threads is not. Use `ThreadLocal<Query>` or an index per worker.
* **A per-thread leaf cache is worth it.** `leafAt` is six to ten hash steps; the mesher and the
  light bake call it millions of times per chunk. Cache `(lastCellX, lastCellZ, Query)` per thread
  and hit >90 %.
* **Never `new Query()` in a loop.** One per thread, fields reused.
* **Keep the corridor width honest.** `CORRIDOR_WIDTH = 1` cell is what makes halls read as halls.
  Two-cell rooms are what make the pit guard necessary.

---

# Phase 3 — The mesher

**Goal:** turn `ChunkData` into one interleaved vertex buffer per chunk, in a few hundred
microseconds, without allocating per cell.

## 3.1 One buffer, twelve floats per vertex

```java
public static final int STRIDE = 12;
// x y z | nx ny nz | u v | lmU lmV | mat aux
```

`mat` is a float so the shader can branch on it with `< 0.5`, `< 1.5`, `< 2.5` — no texture array, no
extra uniform. `aux` is overloaded per material: height ratio for walls, flicker phase for lamps.

`MeshBuilder` starts with small arrays and **doubles** them, then the caller copies out exactly
`floats()` and `indexCount()` into a right-sized array:

```java
float[] vertices = new float[mb.floats()];
System.arraycopy(mb.vertexArray(), 0, vertices, 0, mb.floats());
short[] indices = new short[mb.indexCount()];
System.arraycopy(mb.indexArray(), 0, indices, 0, mb.indexCount());
```

One `MeshBuilder` per chunk, reset per build. Growing arrays 8 or 9 times per chunk is cheaper than
any smarter scheme, and it allocates nothing per cell.

## 3.2 The quad, and winding

Every face in the level is an axis aligned rectangle, described by an origin, two edge vectors and a
normal:

```java
public void rect(float ox, float oy, float oz,
                 float ux, float uy, float uz,
                 float vx, float vy, float vz,
                 float nx, float ny, float nz, int mat, float aux) {
    // Fix the winding so the face points along the normal we asked for.
    if (dot(cross(u, v), n) < 0f) { swap(u, v); }
    ...
    indices[i++] = base; indices[i++] = base + 1; indices[i++] = base + 2;
    indices[i++] = base; indices[i++] = base + 2; indices[i++] = base + 3;
}
```

Because the swap happens here, callers never think about winding — which is exactly how a *culled*
face ends up invisible somewhere it should be visible. Hence:

```java
/**
 * The same rectangle, twice, back to back.
 *
 * Used for the vertical faces that close a height change: the riser of a step, the drop between two
 * ceilings, the end of a wall run. Those faces sit on a cell boundary, a plane the player can be on
 * either side of. A face drawn for only one of them leaves a rectangle of empty background to see
 * through - which is exactly what a doorway into a lower room looked like before this existed.
 */
public void rectBoth(..., float nx, float ny, float nz, int mat, float aux) {
    final float e = 0.002f;                  // a millimetre apart, so they never z-fight
    rect(ox + nx * e, ..., nx, ny, nz, mat, aux);
    rect(ox - nx * e, ..., -nx, -ny, -nz, mat, aux);
}
```

Three families of faces need it: floor risers, ceiling steps, and the end caps of a wall run. They
are rare enough (a few per hundred cells) that the doubled triangles do not matter.

## 3.3 Floors, ceilings, risers

Floors and ceilings are one quad per cell, extended **to the centre line of any adjoining wall** so
that the 16 cm wall never leaves a sliver of void along the top of the wall:

```java
float x0 = x - (d.vWallAt(li,     lj) ? THICK : 0f);
float x1 = x + 1f + (d.vWallAt(li + 1, lj) ? THICK : 0f);
...
mb.rect(x0, f, z0, x1 - x0, 0, 0, 0, 0, z1 - z0, 0, 1, 0, MAT_FLOOR, 0f);
mb.rect(x0, c, z0, x1 - x0, 0, 0, 0, 0, z1 - z0, 0, -1, 0, MAT_CEILING, 0f);
```

Then, for each of the four neighbours, the vertical face that closes the difference — emitted by the
cell that owns the boundary, and skipped where a wall already covers it:

```java
if (nf < f - EPS && !d.vWallAt(li + 1, lj)) {
    mb.rectBoth(x + 1f, nf, z, 0, 0, 1, 0, f - nf, 0, 1, 0, 0, MAT_FLOOR, 0f);
}
if (nc < c - EPS && !d.vWallAt(li + 1, lj)) {
    mb.rectBoth(x + 1f, nc, z, 0, 0, 1, 0, c - nc, 0, 1, 0, 0, MAT_CEILING, 0f);
}
```

Walls are the two faces of the wall line, each sized from its *own* room's floor up to the *taller*
of the two ceilings, which is what makes a 4.8 m hall next to a 3.0 m room look like a hall with a
drop rather than two rooms with a hole above the wall.

## 3.4 Lamps

A fixture is one quad at `ceilY - 0.03`, one metre square, with the fitting's own photograph mapped
across it exactly once:

```java
mb.rectLamp(x + 0.04f, y, z + 0.04f, 0.92f, 0, 0, 0, 0, 0.92f, 0, -1, 0, MAT_PANEL, phase);
```

`rectLamp` sets `unitUv` for that quad, so the UVs run 0..1 regardless of where in the world the lamp
is. `phase` is `-1` for a steady tube, `1` for a burned-out one (geometry stays, the light does not),
otherwise `[0,1)` flicker phase.

## 3.5 Pitfalls in phase 3

* **`short` indices cap a chunk at 65 535 vertices.** At ~4 vertices per face a 16 m chunk with lots
  of holes can get close. Check `vertexCount()` in `build()` and, if you ever cross it, split the
  chunk or move to `glDrawElements(GL_UNSIGNED_INT)` (available in GLES3 only — keep the `short`
  path for `minSdk 21`).
* **`Mesh` must be disposed on the GL thread**, including when the chunk is released and when the
  screen goes away. A leaked `Mesh` per chunk is a few hundred MB per minute of walking.
* **Do not build the lighting into the mesh.** Colour per vertex would need re-meshing whenever the
  tube flickers; the lightmap (phase 4) does not.
* **UVs must follow the world grid**, not the chunk. Two adjacent chunks must show one continuous
  wallpaper. `MeshBuilder` subtracts the largest whole number of tiles that fits in the chunk, which
  keeps `u` small (precision!) without moving the pattern.
* **`EPS` matters.** A 1e-4 threshold on `nf < f - EPS` keeps co-planar neighbours from emitting
  degenerate faces.
* **Set the bounding box.** The renderer frustum-culls with it; a `Mesh` with a stale box culls the
  chunk at the wrong moment and the world winks out.

---

# Phase 4 — Baked light

**Goal:** fluorescent pools on the carpet, dark corners, no dynamic lights, on GLES2.

## 4.1 One small texture per chunk

```
  34 × 34 texels, 2 per metre, 1 texel of padding all round
  RGB = irradiance on up-facing surfaces (floor, ceiling, everything horizontal)
  A   = irradiance on vertical surfaces (walls)
```

A chunk is 16 m, so 32 texels plus the border. The padding texel is what makes bilinear filtering
across the chunk seam continuous — without it, every 16 m there is a visible light grid.

Encoding, from `LightBaker`:

```java
public static final int   TEXELS_PER_METER = 2;
public static final int   TEXELS = WorldGen.CHUNK_CELLS * TEXELS_PER_METER + 2;  // 34
public static final float RANGE = 1.6f;     // the shader multiplies back by this
```

Each channel is stored **square rooted** into an 8-bit texture, normalised against `RANGE` (1.6):

```java
/** Irradiance -> 0..255, normalised against RANGE and encoded so the dark end keeps its gradients. */
private static int encode(float v) {
    float t = clamp(v / RANGE, 0f, 1f);
    return (int) (Math.sqrt(t) * 255f + 0.5f);
}
```

Sqrt encoding is the whole reason the dark end of the level has gradients instead of banding: linear
8-bit gives you half your levels to the top 25 % of the range, which is where this level is *not*.
Decode in the shader with `raw.rgb * raw.rgb`.

## 4.2 The bake

```java
Cells   cells    = new Cells(gen, floorInt(x0), floorInt(z0));   // sample the cells once
float[] fixtures = collectFixtures(gen, x0, z0);                 // only fixtures that can reach us

for (int tj = 0; tj < TEXELS; tj++) {
    for (int ti = 0; ti < TEXELS; ti++) {
        float wx = x0 + (ti + 0.5f) * TEXEL_SIZE;
        float wz = z0 + (tj + 0.5f) * TEXEL_SIZE;
        bakeTexel(cells, fixtures, wx, wz, up, side, tj * TEXELS + ti);
    }
}
```

`bakeTexel` sums every collected fixture's contribution: distance falloff, a cosine for height, and
one line-of-sight test through the wall grid so light does not leak into the next room. Soft contact
shadow under a wall is `1 - exp(-d)` against the nearest wall distance, and a small bounce term from
the floor colour keeps ceilings from going black.

The cost is a few hundred microseconds per chunk on a phone, on a worker thread, next to meshing.

## 4.3 In the shader

```glsl
vec4 raw = texture2D(u_lightmap, v_lm);
vec4 lm  = vec4(raw.rgb * raw.rgb, raw.a * raw.a);   // decode
vec3 wallLight = vec3(1.0, 0.955, 0.80) * lm.a * u_lightRange;

// carpet, ceiling: horizontal light, plus the vertical channel on risers
irr = mix(lm.rgb * u_lightRange * k, wallLight * k2, vert);   // vert = 1 - |n.y|
```

`vert` is what lets a stair riser, a hole wall or a ceiling step — all of them modelled as carpet or
ceiling material — be lit by the *wall* channel without a second material.

## 4.4 Pitfalls in phase 4

* **Mediump.** `1024` is not representable exactly in `mediump`, and neither is `1/1024`; multiply by
  powers of two or pass a float uniform. All the world uniforms are handed over in **chunk space**
  (the renderer subtracts `u_origin` per draw), because `v_pos` at a kilometre out in mediump is a
  flat line.
* **Texture units are scarce** (8 minimum on GLES2): floor, floor normal, wall, wall normal,
  ceiling, ceiling normal, lamp, lightmap. That is exactly eight, and it is why the lamp is a texture
  and not a second material pass.
* **Bake on the worker, upload on the render thread.** The `int[] rgba` is filled by the worker and
  handed over in `ChunkBuild`, then `glTexImage2D`'d once.
* **One `Texture` per chunk** is simple but means a bind per draw call. If the draw call count ever
  becomes the bottleneck, move the lightmaps into a 4×4 atlas and rebind once per 16 chunks.
* **`GL_LINEAR` + padding texel**, not `GL_CLAMP_TO_EDGE`: clamping is what you want at the world
  edge, but the padding is what keeps the seam invisible.

---

# Phase 5 — Renderer, player, audio, packaging

## 5.1 The renderer

Per frame, in this order:

```java
camera.update();
renderer.begin(camera, time, cameraPos, torchPos, torchDir);   // uniforms, fog, torch
renderer.draw(world, camera);                                  // frustum cull + draw calls
renderer.end();
vhs.begin();                                                   // scene FBO -> screen
```

`draw` walks `world.live()`, skips anything outside the frustum, and draws each remaining chunk with
its own vertex buffer and its own lightmap:

```java
shader.setUniformf("u_origin", chunk.worldX, 0f, chunk.worldZ);
lightmap.bind(UNIT_LIGHTMAP);                 // 7
mesh.render(shader, GL20.GL_TRIANGLES);
```

**Fog is a squared exponential** and is applied *before* the tone map, so distant halls fade into the
same colour as the clear colour and the level appears to go on forever without drawing it:

```glsl
float f = 1.0 - exp(-pow(dist * u_fogDensity, 2.0));
color = mix(color, u_fogColor, clamp(f, 0.0, 1.0));
color *= u_exposure;
color = aces(color);
```

The camcorder look (phase 5.4) is a second pass on an FBO: scanlines, tracking band, chromatic
offset, vignette and grain. Keep it a separate shader — the world shader is the one that has to be
cheap.

## 5.2 The player

The player is a capsule in a grid of 1 m cells, and every query is answered by the generator:

```java
// move, then resolve against the cell grid in x and z separately, then step up
float step = STEP_HEIGHT;   // 0.55 - one terrace ring, or a stair
if (gen.isSolidGround(nx, nz) && gen.floorY(nx, nz) - feetY <= step) { accept }
```

Resolving axes separately is what lets you slide along a wall instead of stopping dead. `STEP_HEIGHT`
of 0.55 is chosen to match the 0.5 m terrace ring of the sunken rooms and the stair risers, so the
player can walk down a terrace without jumping but cannot climb a wall.

Holes are handled by gravity and `findLanding`: fall, then land on the next solid cell below, or
respawn. The pit floor is 6 m down and the level's walls are 3 m, so a fall is survivable but
disorienting — which is the point.

## 5.3 Audio

Every sample in the game is synthesised by `tools/make_assets.py` (numpy, no third-party audio):

```python
write_ogg("footstep_%d.ogg" % i, make_footstep(np.random.default_rng(100 + i), tone=0.94 + 0.05 * i))
write_ogg("light_buzz.ogg",       make_buzz(), loop=True)
write_ogg("tape_hiss.ogg",        make_tape_hiss(), loop=True)
```

The one thing that makes footsteps feel like *this* level is the echo, and the echo is a measurement:

```java
// Measure the room, then play the step dry plus one delayed copy of itself.
Acoustics room = Acoustics.probe(gen, x, z, scratch);      // 16 wall rays, 12 m each
float slap = Acoustics.echoDelay(room.nearest);            // 2 * d / 343 m/s, in seconds
float tail = room.tailDelay();                             // 35 ms in a corridor, 295 ms in a hall
float gain = room.tailGain();
```

A step in a corridor comes back in 20 ms; the same step in a hall comes back at 60 ms with a quarter
of a second of tail behind it. Ambience is only the fluorescent buzz of the *nearest* tube — a constant drone under everything
is the fastest way to make a quiet level feel loud.

## 5.4 The camcorder pass

An FBO the size of the screen, the world rendered into it, then one full-screen quad:

```java
fbo.begin();
world.render();
fbo.end();
Gdx.gl.glDisable(GL20.GL_CULL_FACE);        // a full-screen quad, and the next pass may be flipped
vhsShader.begin();
fbo.getColorBufferTexture().bind(0);
fullScreenQuad.render(vhsShader, GL20.GL_TRIANGLES);
```

It is **off by default on low-end devices and toggled from the pause menu**, because it costs a full
extra pass and a lot of fill rate. The `u_glitch` uniform spikes when the tape slips; the OSD
(`REC`, date, timecode) is drawn in the same font atlas as the HUD.

## 5.5 Packaging, and what CI checks

`./gradlew :android:assembleDebug :android:assembleRelease` produces the APK. The CI workflow
(`.github/workflows/android.yml`) does not trust the build:

1. `:core:test` — the generator, mesher, lightmap, physics and shader-precision unit tests.
2. Unzip the APK and assert `lib/arm64-v8a/libgdx.so`, `armeabi-v7a` and `x86_64` are all there.
3. `aapt2 dump badging` and assert the launcher activity is declared, then grep the dex for the class
   — an APK that installs and shows a black screen is almost always a manifest name that does not
   resolve.
4. Upload the APK as an artifact, and commit the check log to `docs/ci/`.

Every asset lives in `assets/` and is packed into the APK by the `android` module's source set: no
download at runtime, no external storage permission, nothing to fail on first launch.

## 5.6 Pitfalls in phase 5

* **GLES2 needs `precision mediump float;` in the fragment shader**, and any uniform declared in both
  stages must have the *same* precision or the program fails to link on some drivers.
* **`gl_FragCoord` is mediump**; do not feed it a world coordinate.
* **`u_time` grows without bound.** Pass a wrapped value (`u_grainTime`) for anything noise-like.
* **FBO resize on Android** must be handled in `resize()`; a stale FBO after rotation is a black
  screen, not an error.
* **Screenshot capture** must read the FBO, not the back buffer (which is undefined after a swap).
  That is what the `-Dbackrooms.capture` flag in the desktop job does.
* **Audio resources**: dispose `Sound`s and `Music` in `hide()`/`dispose()`, and never on a worker.
* **Do not minify the launcher.** ProGuard/R8 keeps the Android adapter class via the manifest, but
  anything reached only by reflection needs a rule.

---

## Appendix A — Performance budget

| Stage | Where | Budget on a mid-range phone |
|---|---|---|
| `WorldGen` queries | worker | ~20 µs per chunk (cached leaves) |
| `ChunkMesher` | worker | 0.5–1.5 ms per chunk |
| `LightBaker` | worker | 0.5–1 ms per chunk |
| Upload (2 per frame) | GL thread | < 1 ms |
| Draw calls | GL thread | ~60–120 per frame |
| Frame total | | 16.6 ms (60 fps), measured in the pause menu |

## Appendix B — Debug flags

```
-Dbackrooms.spawn=x,y,z     start somewhere specific
-Dbackrooms.pit=1           stand next to a pit
-Dbackrooms.vhs=0|1         force the camcorder pass off or on
-Dbackrooms.frames=120      run N frames then exit (CI)
-Dbackrooms.capture=out     write screenshots (desktop only)
```

## Appendix C — Where the tests live

```
core/src/test/java/com/backrooms/gdx/
    WorldGenTest          determinism, room variety, walkability, fixtures
    ChunkMesherTest       every kind of room meshes at its own height; lamp UVs
    LightBakerTest        light pools, no leaking through walls
    PlayerPhysicsTest     sliding, step height, holes
    AcousticsTest         echo delay from wall distance
    ShaderPrecisionTest   no float literals that mediump cannot hold
    VhsOsdFontTest        the font atlas has every glyph the OSD uses
    WorldStreamingTest    chunk ring loads and releases
```

Run them with `./gradlew :core:test`. They are plain JUnit: no GL context, no Android emulator,
which is what makes them fast enough to run on every push.
