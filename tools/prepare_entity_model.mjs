/**
 * Turns a rigged Sketchfab glTF into the one model the game ships.
 *
 * Two things have to change before the GLB is usable on a phone:
 *
 * 1. The skeleton is far too big. libGDX sends bone matrices as a `mat4 u_bones[N]` uniform array,
 *    so every bone costs four vertex uniform vectors, and GLES2 only guarantees a small budget of
 *    them (256 on most modern GPUs, 128 on older ones). 135 bones is 540 vectors - the shader will
 *    not link anywhere, and gdx-gltf refuses to even build one past its configured limit of 24.
 *    The fix is to collapse the least useful joints into their parents and to rewrite the skin
 *    weights to match; the silhouette survives because the weight of a removed joint is
 *    transferred to the nearest joint that is still there. Two dozen joints is enough for a
 *    crouched, long-limbed creature: spine, head, both arms and both legs.
 *
 * 2. The mesh is 231k triangles for a creature you mostly see in the dark. gltf-transform's
 *    simplifier is run before this script (see tools/prepare_entity_model.sh); by the time we get
 *    here the mesh is already down to a sane size.
 *
 * Texture coordinates go too: this model has no textures at all (one flat material), so a UV set
 * for every vertex is four hundred kilobytes of nothing.
 *
 * Usage: node tools/prepare_entity_model.mjs input.glb output.glb [maxBones]
 */
import { createRequire } from 'node:module';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

// glTF-Transform is tooling, not a dependency of the game, so it is not vendored in the repository.
// GLTF_MODULES points at a directory whose node_modules contains @gltf-transform/core; without it we
// look next to this script and in the working directory.
const here = dirname(fileURLToPath(import.meta.url));
const candidates = [
  process.env.GLTF_MODULES,
  here,
  process.cwd(),
].filter(Boolean);
let core = null;
for (const base of candidates) {
  try {
    core = createRequire(join(resolve(base), 'noop.js'))('@gltf-transform/core');
    break;
  } catch (error) {
    // try the next candidate
  }
}
if (!core) {
  console.error('could not find @gltf-transform/core: run `npm install --no-save @gltf-transform/cli`');
  process.exit(1);
}
const { NodeIO } = core;

const [, , input, output, maxBonesArg] = process.argv;
if (!input || !output) {
  console.error('usage: node prepare_entity_model.mjs input.glb output.glb [maxBones]');
  process.exit(1);
}
const MAX_BONES = Number(maxBonesArg || 44);

const io = new NodeIO();
const doc = await io.read(input);
const root = doc.getRoot();

// ---------------------------------------------------------------- measure joint usage
const skins = root.listSkins();
if (skins.length !== 1) {
  console.error(`expected exactly one skin, found ${skins.length}`);
  process.exit(1);
}
const skin = skins[0];
const joints = skin.listJoints();
const jointIndex = new Map(joints.map((node, i) => [node, i]));
const usage = new Float64Array(joints.length);

for (const mesh of root.listMeshes()) {
  for (const prim of mesh.listPrimitives()) {
    const jointsAcc = prim.getAttribute('JOINTS_0');
    const weightsAcc = prim.getAttribute('WEIGHTS_0');
    if (!jointsAcc || !weightsAcc) continue;
    const j = jointsAcc.getArray();
    const w = weightsAcc.getArray();
    const normalized = weightsAcc.getNormalized();
    const count = jointsAcc.getCount();
    for (let v = 0; v < count; v++) {
      for (let k = 0; k < 4; k++) {
        const idx = v * 4 + k;
        let weight = w[idx];
        if (normalized) weight /= 255;         // weights quantised into unsigned bytes
        usage[j[idx]] += weight;
      }
    }
  }
}

const totalWeight = usage.reduce((a, b) => a + b, 0);
console.log(`skin: ${joints.length} joints, ${totalWeight.toFixed(1)} units of influence`);
if (joints.length <= MAX_BONES) {
  console.log('already small enough, nothing to do');
} else {
  // ------------------------------------------------------------ collapse the least used joints
  // Ascending weight, never the root: the joint with the least influence is merged into its
  // parent, and anything that pointed at it is redirected. Walking up repeatedly handles chains.
  const removed = new Set();
  const order = joints
    .map((node, i) => ({ node, i, weight: usage[i] }))
    .filter((entry) => entry.node.getParentNode() !== null)
    .sort((a, b) => a.weight - b.weight);

  const kept = () => joints.length - removed.size;
  for (const entry of order) {
    if (kept() <= MAX_BONES) break;
    removed.add(entry.i);
  }

  // Resolve every joint to the nearest ancestor that survives (the root always does).
  const resolve = (index) => {
    let node = joints[index];
    const seen = new Set();
    while (node && removed.has(jointIndex.get(node)) && !seen.has(node)) {
      seen.add(node);
      node = node.getParentNode();
    }
    const found = node ? jointIndex.get(node) : 0;
    return found === undefined ? 0 : found;
  };
  const remapped = joints.map((_, i) => resolve(i));

  // New joint list, in the original order so the hierarchy stays readable.
  const newJoints = joints.filter((_, i) => !removed.has(i));
  const newIndex = new Map(newJoints.map((node, i) => [node, i]));
  const oldToNew = remapped.map((oldIndex) => newIndex.get(joints[oldIndex]));

  // ------------------------------------------------------------ rewrite the skin weights
  const buffer = root.listBuffers()[0];
  for (const mesh of root.listMeshes()) {
    for (const prim of mesh.listPrimitives()) {
      const jointsAcc = prim.getAttribute('JOINTS_0');
      const weightsAcc = prim.getAttribute('WEIGHTS_0');
      if (!jointsAcc || !weightsAcc) continue;

      const j = jointsAcc.getArray();
      const w = weightsAcc.getArray();
      const normalized = weightsAcc.getNormalized();
      const count = jointsAcc.getCount();
      const outJoints = new Uint16Array(count * 4);
      const outWeights = new Float32Array(count * 4);

      for (let v = 0; v < count; v++) {
        // Merge influences that collapsed onto the same surviving joint.
        const merged = new Map();
        for (let k = 0; k < 4; k++) {
          const idx = v * 4 + k;
          let weight = w[idx];
          if (normalized) weight /= 255;
          if (weight <= 0) continue;
          const target = oldToNew[j[idx]];
          if (target === undefined) continue;
          merged.set(target, (merged.get(target) || 0) + weight);
        }
        // Keep the four strongest and renormalise: the shader reads four influences.
        const top = [...merged.entries()].sort((a, b) => b[1] - a[1]).slice(0, 4);
        const sum = top.reduce((acc, entry) => acc + entry[1], 0) || 1;
        for (let k = 0; k < 4; k++) {
          const entry = top[k];
          outJoints[v * 4 + k] = entry ? entry[0] : 0;
          outWeights[v * 4 + k] = entry ? entry[1] / sum : 0;
        }
      }

      prim.setAttribute('JOINTS_0', doc.createAccessor()
        .setType('VEC4')
        .setBuffer(buffer)
        .setArray(outJoints));
      prim.setAttribute('WEIGHTS_0', doc.createAccessor()
        .setType('VEC4')
        .setBuffer(buffer)
        .setArray(outWeights));
      prim.setAttribute('JOINTS_1', null);
      prim.setAttribute('WEIGHTS_1', null);
      prim.setAttribute('TEXCOORD_0', null);
    }
  }

  // ------------------------------------------------------------ trim the skin itself
  const ibm = skin.getInverseBindMatrices();
  const ibmArray = ibm.getArray();
  const newIbm = new Float32Array(newJoints.length * 16);
  newJoints.forEach((node, i) => {
    newIbm.set(ibmArray.slice(jointIndex.get(node) * 16, jointIndex.get(node) * 16 + 16), i * 16);
  });
  // glTF-Transform has no "reorder joints", so the skin is rebuilt: joint order is the index the
  // JOINTS_0 attribute refers to.
  const newSkin = doc.createSkin(skin.getName() || 'entity')
    .setSkeleton(skin.getSkeleton())
    .setInverseBindMatrices(doc.createAccessor()
      .setType('MAT4')
      .setBuffer(buffer)
      .setArray(newIbm));
  for (const node of newJoints) newSkin.addJoint(node);
  for (const node of root.listNodes()) {
    if (node.getSkin() === skin) node.setSkin(newSkin);
  }
  skin.dispose();
  ibm.dispose();

  console.log(`bones: ${joints.length} -> ${newJoints.length}`);
}

// ---------------------------------------------------------------- repack
const glb = await io.writeBinary(doc);
const { writeFile } = await import('node:fs/promises');
await writeFile(output, glb);
console.log(`wrote ${output}: ${(glb.byteLength / 1e6).toFixed(2)} MB`);
