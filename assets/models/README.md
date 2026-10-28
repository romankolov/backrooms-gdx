# The entity model

`entity.glb` is the creature itself: **"Accurate Backrooms Bacteria v2 with Animations" by
Unschooling with Fin**, CC-BY-4.0, converted from the Sketchfab glTF download (see
`entity-LICENSE.txt` and `assets/CREDITS.md`). `tools/prepare_entity_model.sh` rebuilds it from the
zip; the derived GLB is what ships.

The file is optional at runtime. If it is missing or cannot be loaded, `EntityRenderer` falls back
to the placeholder body built out of boxes in `EntityRenderer.buildFigure()`, so a bad asset costs
you the model, never the level.

## What the loader expects

| | |
| --- | --- |
| Format | binary glTF (`.glb`), one skinned mesh |
| Up axis | Y, metres, origin between the feet — *anything else is measured and corrected on load* |
| Facing | `-Z` at rest is treated as "forward" |
| Height | normalised to 2.1 m; the corridors are 3 m |
| Rig | **at most 24 joints** — see below |
| Animations | at least one. A clip whose name contains `jump`/`scare`/`attack`/`roar` is the lunge, `stalk`/`idle`/`stand`/`breath` is the standing pose, and the longest clip is the locomotion cycle. |
| Size | keep it under ~3 MB |
| Licence | must be redistributable, with the licence copied next to it |

### Why 24 joints

libGDX uploads bone matrices as `mat4 u_bones[N]`, four vertex uniform vectors per joint, and
GLES2 only guarantees a small budget of them. gdx-gltf's default is 24 and it refuses to build a
shader past that, which is the right call for a phone. The source rig has 135 joints, so
`tools/prepare_entity_model.mjs` collapses the least useful ones into their parents and rewrites
the skin weights; the silhouette survives because a removed joint's weight moves to its nearest
surviving ancestor.

### Units and orientation

Sketchfab exports carry their own units and their own idea of which way is up, so nothing is
hard-coded: the model is measured after loading (bounding box), rotated upright, centred on its
feet, and scaled to 2.1 m. If a replacement model needs a different correction, `-Dbackrooms.entityrot=x,y,z`
(degrees, default `-90,0,0` for this Z-up export) and `-Dbackrooms.entityscale=1.0` override it
without touching code.

## Trying it

```sh
./gradlew :desktop:run -Dbackrooms.entity=1
./gradlew :desktop:run -Dbackrooms.entity=1 -Dbackrooms.vhs=0   # judge the model, not the tape
```

The run logs `model loaded: models/entity.glb scale=… clips: idle=… walk=… scare=… playing=…` when it
picks the file up, and `could not load models/entity.glb: …` plus the placeholder notice when it
does not.

## Rebuilding it

```sh
npm install --no-save @gltf-transform/cli      # tooling, not a game dependency
tools/prepare_entity_model.sh accurate_backrooms_bacteria_v2_with_animations.zip 24
```
