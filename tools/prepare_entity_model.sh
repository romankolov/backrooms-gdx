#!/usr/bin/env bash
# Rebuilds assets/models/entity.glb from the Sketchfab download.
#
#   tools/prepare_entity_model.sh accurate_backrooms_bacteria_v2_with_animations.zip
#
# Needs Node 18+ and the glTF tooling, which is not a dependency of the game itself:
#
#   npm install --no-save @gltf-transform/cli
#
# See tools/prepare_entity_model.mjs for why the skeleton is rebuilt rather than used as supplied.
set -euo pipefail

zip="${1:-accurate_backrooms_bacteria_v2_with_animations.zip}"
here="$(cd "$(dirname "$0")/.." && pwd)"
work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT

echo "== unpack"
unzip -q "$zip" -d "$work"
[ -f "$work/scene.gltf" ] || { echo "scene.gltf missing from $zip" >&2; exit 1; }

gt="${GLTF_TRANSFORM:-gltf-transform}"

echo "== simplify (231k triangles is a lot of creature)"
"$gt" copy "$work/scene.gltf" "$work/a.glb"
"$gt" weld "$work/a.glb" "$work/b.glb"
"$gt" simplify "$work/b.glb" "$work/c.glb" --ratio 0.15 --error 0.0015
"$gt" resample "$work/c.glb" "$work/d.glb" --tolerance 0.001
"$gt" prune "$work/d.glb" "$work/e.glb"
"$gt" dedup "$work/e.glb" "$work/f.glb"

echo "== reduce the skeleton to what mobile skinning uniforms can hold"
GLTF_MODULES="${GLTF_MODULES:-$(cd "$(dirname "$(command -v "$gt")")/.." 2>/dev/null && pwd || echo "$here")}" \
  node "$here/tools/prepare_entity_model.mjs" "$work/f.glb" "$work/g.glb" 44
"$gt" prune "$work/g.glb" "$work/h.glb"

mkdir -p "$here/assets/models"
cp "$work/h.glb" "$here/assets/models/entity.glb"
cp "$work/license.txt" "$here/assets/models/entity-LICENSE.txt"
ls -l "$here/assets/models/entity.glb"
