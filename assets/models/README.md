# The entity model

`entity.glb` is optional. If it is here, `EntityRenderer` loads it with
[gdx-gltf](https://github.com/mgsx-dev/gdx-gltf), animates it, and lights it with the player's
torch. If it is not here, the game uses the placeholder body that is built out of boxes in
`EntityRenderer.buildFigure()` — so the build never depends on an asset that is not in the
repository, and a broken file can only ever cost you the model, not the level.

## What the loader expects

| | |
| --- | --- |
| Format | binary glTF (`.glb`, single file, textures embedded) |
| Up axis | Y, metres (glTF's default), origin between the feet |
| Facing | `-Z` at rest is treated as "forward" |
| Height | under about 2 m tall; the corridors are 3 m |
| Rig | one armature, skinned mesh, at most 64 bones |
| Animations | at least one; clips containing `walk`/`run`/`move` play while it moves, clips containing `idle`/`stand`/`breath` play while it stands. With no recognisable names the first clip is idle and the second is the walk cycle. |
| Size | keep it under ~2 MB; it is loaded on the level's first frame |
| Licence | must be CC0 or otherwise redistributable, with the licence copied next to it (see `assets/CREDITS.md`) |

Add the licence and the source to `CREDITS.md` when you add the file.

## Trying it

```sh
cp ~/my-creature.glb assets/models/entity.glb
./gradlew :desktop:run -Dbackrooms.entity=1
```

The desktop run prints `[Entity] rigged model loaded: models/entity.glb (idle=…, walk=…)` to the
console when it picks the file up, and `[Entity] could not load models/entity.glb: …` followed by the
placeholder notice when it does not.
