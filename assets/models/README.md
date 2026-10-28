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
| Up axis | Y |
| Origin | between the feet, on the floor |
| Facing | `-Z` |
| Height | exactly 2.1 m; the corridors are 3 m |
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

### Why the conversion normalises the model

A skinned mesh does not draw where its node bounds say: the vertices go through the joint matrices,
so the skeleton's own scale decides the size. This rig's armature scales everything by about a
sixth, which means the mesh measures 27 units tall on disk and draws 4.6 - and a game that trusts
the bounding box ends up with a four-centimetre creature. Neither libGDX nor gdx-gltf can measure
the drawn size for you.

So `tools/prepare_entity_model.mjs` skins the mesh in the rest pose, takes the bounds of *that*,
rotates the longest axis upright, scales to 2.1 m, and writes the transform onto the scene root. The
result is a file that is metres, Y up, feet on the origin, facing -Z - and the game places it with
nothing but a translation and a yaw.

A replacement model that does not follow that contract can be corrected at run time with
`-Dbackrooms.entityscale=1.0` and `-Dbackrooms.entityyaw=180`, without touching code.

## Trying it

```sh
./gradlew :desktop:run -Dbackrooms.entity=1
./gradlew :desktop:run -Dbackrooms.entity=1 -Dbackrooms.vhs=0   # judge the model, not the tape
```

The run logs `model ready: models/entity.glb expecting 2.1 m … clips: idle=… walk=… scare=…` when it
picks the file up, and `could not load models/entity.glb: …` plus the placeholder notice when it
does not.

## Rebuilding it

```sh
npm install --no-save @gltf-transform/cli      # tooling, not a game dependency
tools/prepare_entity_model.sh accurate_backrooms_bacteria_v2_with_animations.zip 24
```
