# The Backrooms — an infinite libGDX world for Android

An endless, procedurally generated Backrooms walker written in Java with [libGDX](https://libgdx.com).
You can walk (or run, or fall) in any direction forever: the level streams in around you and is
thrown away behind you, so the map never ends and memory never grows.

![A room in Level 0](screenshots/shot0.png)
![Looking down a corridor](screenshots/shot2.png)

---

## What is in the box

| | |
| --- | --- |
| **Infinite map** | There is no tile lattice and nothing repeats. The level is a **binary space partition** of the plane: every room is cut, again and again, at a random position along its long axis until the pieces are room sized, so a doorway leads into a four-metre slot, three such slots lead into a hall, and no two rooms share a size or a corner. Two more layers keep it habitable: **avenues**, halls two or three metres wide that run straight through macro after macro and then stop dead in a wall, and **rooms**, each of a kind decided by its own rectangle - plain office (about six in ten), a tall hall, terraces stepping down, pits in the carpet, a forest of pillars, a comb of short walls, or a room with no working light at all. Every cut carries a doorway, which is what makes the whole thing connected; one cut in four carries a second, which is what makes loops. Every wall, door, light, hole and terrace is a pure hash of its integer coordinates and the world seed, so any chunk can be generated on its own and always comes out the same. |
| **Streaming** | Chunks (16 × 16 m) are sampled, meshed and light-baked on background threads, uploaded at most two per frame, and released again behind you. Draw distance adjustable in game (2–6 chunks). |
| **Baked lighting** | Soft fluorescent pools, contact shadows in corners and a bounce pass, all baked per chunk at 2 texels/m into an 8 KB lightmap (irradiance on floors in RGB, on walls in alpha). Zero dynamic lights at runtime. |
| **Materials** | The wall, carpet, ceiling and lamp of `backrooms_vr.glb`, de-lit and made seamless by `tools/extract_vr_assets.py` (the model's own bake is divided out, so the lamp's hot spot stops being part of the pattern), with normal maps derived from the colour and procedural grain added back for arm's length detail. Ceiling fittings are the model's own photographed lamp, mapped whole across each panel, with the glass masked as an emitter: a real fitting with a rim, not a white rectangle. |
| **Lighting model** | Warm fluorescent tubes, a handheld torch cone, distance haze, ACES tone mapping, vignette and a touch of sensor grain. |
| **VHS camcorder** | The whole frame goes through an offscreen pass that emulates tape — a *good* tape in a working deck: geometry straight to within a pixel, a little chroma smear from the low chroma bandwidth, a tracking band that nudges rather than tears, soft grain, faint scanlines and a warm slightly-lifted grade. Plus a camcorder OSD (REC lamp, SP, date, tape counter) drawn from a hand-built 5x7 character generator, and synthesised tape hiss. Toggle it in the pause menu (`VHS LOOK`) or with `-Dbackrooms.vhs=0`. |
| **Alone** | There is nothing else in here for now. The stalker that used to pass by has been taken out before this release; what is left is the level itself, the light and the sound. |
| **Audio** | Everything is synthesised in `tools/make_assets.py`; no third-party samples. Footsteps pick a sample at random, then *echo off the room they happen in*: eight rays measure the walls around you, the nearest two surfaces become early reflections (delayed by distance and panned to the side they came from) and the average distance sets a diffuse tail, so a corridor snaps and a hall rings. The only room sound is the fluorescent buzz of the nearest working tube, fading in with distance; the tape hiss belongs to the camcorder. |
| **Controls** | Touch: floating stick on the left, drag-to-look anywhere on the right, run (hold), jump, torch and pause buttons — all fingers at once. Keyboard and mouse also work (desktop build). The emulator job drives the real controls and checks the numbers the app logs, so a dead button or a dead look drag fails CI instead of shipping. |
| **Platforms** | Android (`minSdk 24`, arm64 + armv7 + x86_64) and a desktop launcher used for development and screenshots. |

## Screenshots

Rendered by CI on software OpenGL (llvmpipe), 1280×720, seed 20241027:

![A corridor](screenshots/shot1.png)

A hole in the carpet — they are not rare, and falling in is how the level moves you somewhere else:

![A hole in the floor](screenshots/hole/shot0.png)

Captured by CI from the real Android build on an API 30 emulator (touch controls):

![Running on Android](screenshots/android/ingame.png)

`python3 tools/map_preview.py out.png 20241027 168` also draws a top-down plan of any seed:

![Floor plan](docs/floorplan.png)

---

## Building

### Requirements

* JDK 17
* Android SDK with platform 35 and build-tools (only needed for the APK)
* Nothing else — the Gradle wrapper is checked in

### APK

```sh
./gradlew :android:assembleDebug        # android/build/outputs/apk/debug/*.apk
./gradlew :android:assembleRelease      # unsigned release build
```

Install on a connected device:

```sh
adb install -r android/build/outputs/apk/debug/android-debug.apk
```

### Desktop (development)

```sh
./gradlew :desktop:run
```

Handy system properties for the desktop run:

| Property | Effect |
| --- | --- |
| `-Dbackrooms.autostart=1` | skip the title screen |
| `-Dbackrooms.seed=1234` | force a world seed |
| `-Dbackrooms.spin=1` | slowly rotate the view (used by the screenshot job) |
| `-Dbackrooms.yaw=45 -Dbackrooms.pitch=-7` | starting heading / pitch in degrees |
| `-Dbackrooms.spawn=0.5,-29.5` | drop the player at a known position (screenshots, debugging) |
| `-Dbackrooms.debug=1` | material colour view, magenta = hole in the geometry |
| `-Dbackrooms.hud=1` | show the performance readout |
| `-Dbackrooms.vhs=0` | render the raw frame without the camcorder pass |
| `-Dbackrooms.pit=1` | spawn on the rim of the nearest floor pit |
| `-Dbackrooms.capture=dir -Dbackrooms.frames=270` | render `frames` frames, dump PNGs into `dir`, quit |

### Tests

```sh
./gradlew :core:test
```

39 unit tests cover the generator (determinism, seeds, rooms coming in many sizes and shapes, no
sealed cell, every kind of room appearing, pits that never block the way through, terraces that are
steps rather than ledges, halls that run and halls that stop, and that nine tenths of the level
around the spawn can actually be walked to), the mesher (winding, index bounds, bounds, lightmap,
the height of every kind of room), the light baker (occlusion, blockages), the player physics
(landing, thin-wall collision, 1200 steps of random walking without ever clipping through a wall or
sinking through the floor), the streaming that builds and releases chunks, the shader rules that only
bite on GLES2, the camcorder's 5x7 glyph table, and the acoustics (propagation delay, and that
different rooms really do answer differently).

### Implementation guide

`docs/GUIDE.md` is the phase-by-phase build guide: the streaming ring, the generator, the mesher,
the light bake and the renderer, each with the code that ships here, the class diagram, and the
Android/OpenGL traps (mediump, `short` indices, GL-thread-only uploads, FBO handling).

### CI

`.github/workflows/android.yml` runs the tests, builds debug and release APKs on every push and
uploads them as artifacts; pushing a tag such as `v1.0.0` publishes both APKs to a GitHub release.
`.github/workflows/screenshots.yml` boots the desktop build under Xvfb with software GL, renders the
level and commits the PNGs in `screenshots/` (visual regression by eyeball); it fails instead of
committing if the world shader did not compile or a frame comes out blank.
`.github/workflows/emulator.yml` goes one step further: it boots a real Android emulator, installs
the APK, starts the launcher, waits, drives the touch controls, screenshots the result into
`screenshots/android/` and fails if the app is not running, never drew anything, or logged a crash.
Its diagnostics (emulator log, filtered logcat, shader compile log) are committed to
`docs/ci/emulator-report.md`.

To sign a release build, put these in the environment before running Gradle:
`BACKROOMS_KEYSTORE`, `BACKROOMS_KEYSTORE_PASSWORD`, `BACKROOMS_KEY_ALIAS`,
`BACKROOMS_KEY_PASSWORD`.

---

## How it works

```
core/src/main/java/com/backrooms/gdx
├── world/WorldGen.java        pure, stateless description of the level (no allocation)
├── world/ChunkSampler.java    fills a ChunkData (+1 cell border) from the generator   [worker]
├── chunk/ChunkMesher.java     visible faces only, one mesh per chunk                  [worker]
├── light/LightBaker.java      irradiance bake: fixtures, soft shadows, AO, bounce     [worker]
├── world/World.java           streaming, priority, upload budget, unloading           [render]
├── render/WorldRenderer.java  one shader, 6 tiled textures, one draw call per chunk
├── player/Player.java         first person movement, thin-wall capsule collision
├── input/TouchControls.java   floating stick, drag look, buttons, keyboard
├── render/VhsEffect.java      offscreen buffer + the tape shader
├── ui/VhsOsd.java             camcorder OSD drawn from a 5x7 character generator
├── audio/Acoustics.java       probes the walls around a step and returns the room's answer
├── audio/AudioManager.java    muxer: placed footsteps, echo taps, buzz, tape hiss
└── ui/Hud.java, screens/…     title screen, HUD, pause menu (with an fps and chunk readout)
```

### The level

The level is a **partition, not a tile set**. Each 32 m macro tile is split recursively, always
across its long axis, until every rectangle is a room of its own size — which is why no two rooms
have the same shape and why nothing reads as a prefab.

* **Rooms** are the leaves. Their kind is a hash of the rectangle itself: about six in ten are the
  plain 3 m office, and the rest are tall halls (4.2–5.4 m), sunken rooms that step down in 0.5 m
  terraces, rooms full of pits, pillar rooms, low rooms with a comb of short walls, and the
  occasional room with its lights out.
* **Walls** are the cuts between rectangles, 16 cm thick, drawn to the taller of the two ceilings so
  a hall next to a small room has a drop rather than a hole above its wall. Each cut opens a 2–3 m
  doorway, and one cut in four opens a second one, which is what makes loops.
* **Corridors** happen when a split shaves a 2–3 m strip off the edge of a rectangle, or when a
  long thin leaf is left whole. They vary in width and ceiling height instead of repeating.
* **Pits** drop 6 m and are kept a cell away from every room edge, so a pit never seals a doorway.
  Falling in is how the level moves you somewhere else: you land on the floor below, not on a
  decorative hole.
* **Ceilings** carry the fluorescent fittings on a 6 m lattice: a few sockets are empty, a few
  tubes are burned out, a few flicker, and a dark room has none at all.

Every query is a pure function of the seed and the cell coordinate — a hash, no `Random` — so chunks
can be built in any order, on any thread, and the world is infinite in every direction with no
seams. `WorldGenTest` proves the parts that are easy to get wrong: the same seed gives the same
level, no walkable cell is sealed in, no pit reaches a room edge, and a flood fill from spawn
reaches over 90 % of the level.

### The lighting

Per chunk the baker writes a 34 × 34 lightmap (2 texels per metre plus a border so neighbouring
chunks filter seamlessly):

1. for every texel, the nearby fixtures are attenuated by distance and visibility (a grid DDA that
   stops at walls, floors and ceilings);
2. an ambient occlusion term from five short probe rays;
3. a cheap one-bounce blur added back (light from the carpet filling the room);
4. sqrt encoding for gradient precision in the dark end.

The shader decodes it, applies normal mapping and specular from the diffuse/normal maps, adds the
torch cone, the panel emission with flicker, exponential haze, ACES tone mapping and a vignette.

### Optimisation notes

* one draw call per chunk, all six textures bound once per frame;
* faces between walls, risers under walls and geometry outside the chunk are never emitted;
* CPU frustum culling per chunk (13–18 of 37 loaded chunks drawn in the CI render);
* no dynamic lights, no shadow maps, no lighting recomputation at runtime;
* chunk build ≈ 27–51 ms on the CI runner's CPU, spread over up to two worker threads, two uploads
  per frame maximum;
* per-frame allocations in the hot path are avoided; bitmaps are uploaded once;
* geometry and tile UVs are stored **relative to their chunk** and the camera/torch positions are
  handed to the shader in that same local frame. Phones run fragment shaders at `mediump` (10 bit
  mantissa), so after a few hundred metres a world-space UV would be interpolated with centimetre
  error and the wallpaper would visibly tear; chunk-local values keep it exact.

On a mid-range phone the target is 60 fps with a 3 chunk radius; drop the radius in the pause menu
if your device needs it.

### Tuning knobs

| Constant | Where | Meaning |
| --- | --- | --- |
| `CHUNK_CELLS` | `WorldGen` | chunk edge in metres (16) |
| `MACRO` | `WorldGen` | where the partition is restarted, and the lattice avenues run on (32 m) |
| `splitOf()` | `WorldGen` | where the next wall goes: the target room size, how often a corridor is shaved off an edge, and how a corridor is sometimes crossed |
| `kindOf()` | `WorldGen` | the mix of room kinds — plain office is about six rooms in ten |
| `CORRIDOR_WIDTH` | `WorldGen` | widest room that counts as a corridor, and so is never given features (3 m) |
| `FEATURE_MARGIN` | `WorldGen` | floor kept clear round every room edge, so a pit or pillar never blocks a door (1 m) |
| `doorway()` | `WorldGen` | where a cut opens, and how often it opens twice |
| `LIGHT_SPACING` / `LIGHT_PHASE` | `WorldGen` | fixture lattice |
| `ROOM_HEIGHT`, `SUNKEN_CEIL`, `PIT_CEIL`, `SPUR_CEIL` | `WorldGen` | ceiling of each kind of room |
| `CORRIDOR_CEIL_MIN/MAX`, `GRAND_CEIL_MIN/MAX` | `WorldGen` | the range a corridor or a hall may take its height from |
| `HOLE_DEPTH` | `WorldGen` | how far the pits drop (6 m: you do not climb out) |
| `TERRACE_STEP`, `TERRACE_RINGS` | `WorldGen` | how a sunken room steps down (0.5 m, 4 rings) |
| `isHole()` | `WorldGen` | hole shape and density (about 2% of the cells) |
| `LIGHT_INTENSITY`, `FALLOFF`, `RANGE` | `LightBaker` | brightness, falloff and lightmap encoding |
| `TEXELS_PER_METER` | `LightBaker` | lightmap resolution (2) |
| `WALK_SPEED`, `SPRINT_SPEED`, `GRAVITY`, `JUMP_SPEED` | `Player` | movement feel |
| `fogDensity`, `exposure` | `PlayScreen` | haze and exposure |
| `scale` | `VhsEffect` | resolution of the tape buffer (0.85 of the screen by default) |
| `u_scanline`, `u_intensity` | `VhsEffect` | how heavy the camcorder look is |

## Assets

* Surfaces and the ceiling fitting: cut out of "Backrooms VR" by carlcapu9 (CC-BY-4.0) with
  `tools/extract_vr_assets.py`. The 20 MB source model is not committed; put it next to the project
  as `backrooms_vr.glb` to rebuild the textures.
* Audio, icons and the font atlas are generated by `tools/make_assets.py`.
* No third-party runtime dependency beyond libGDX itself.
* See `assets/CREDITS.md`.

## Known limitations / next steps

* Frame rate on real phones is unverified: the emulator renders with a software rasteriser, so its
  numbers (≈4 fps at 1080×1920) say nothing about a GPU. The render distance menu item is the only
  quality switch.
* One GLSL ES trap is worth remembering: a uniform declared in both shader stages must have the
  same precision, and a `precision` statement is ES-only (wrap it in `#ifdef GL_ES`). Both rules are
  enforced by `ShaderPrecisionTest`, because a violation only shows up on the device.
* The second device-only trap is `mediump`: anything the fragment shader interpolates has to stay
  small, which is why chunk geometry is chunk-local. `ChunkMesherTest` guards it.
* The level generator is deliberately *stateless per query*: it re-walks the partition from the
  macro corner every time, with a one-room cache per thread. That keeps chunks independent, but a
  per-chunk memo table would make the light bake noticeably cheaper again.
* The desktop build is a development tool, not a shipped target.
* Multi-threaded chunk building uses up to 2 workers; a job system would scale to more cores.
