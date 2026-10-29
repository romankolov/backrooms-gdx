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

// ---------------------------------------------------------------- drop stray geometry
// The Sketchfab scene is not only the creature: it also contains a chunk of staging prop a long way
// off, skinned to the root joint. Left in place it dominates the bounding box, so the game measures
// the model as 245 units long, scales it to 2.1 m on that axis, and the creature itself ends up
// four centimetres tall. The body is one connected lump of geometry and the staging is not, so the
// parts that overlap each other are kept and anything floating on its own is thrown away.
{
  const prims = [];
  for (const mesh of root.listMeshes()) {
    for (const prim of mesh.listPrimitives()) {
      const position = prim.getAttribute('POSITION');
      if (!position) continue;
      prims.push({
        mesh,
        prim,
        count: position.getCount(),
        min: position.getMin([]),
        max: position.getMax([]),
      });
    }
  }

  const overlaps = (a, b) => [0, 1, 2].every((axis) => {
    const pad = (a.max[axis] - a.min[axis] + b.max[axis] - b.min[axis]) * 0.05;
    return a.max[axis] >= b.min[axis] - pad && a.min[axis] <= b.max[axis] + pad;
  });

  // Union-find over "touches", then keep the cluster with the most vertices in it.
  const parent = prims.map((_, i) => i);
  const find = (i) => (parent[i] === i ? i : (parent[i] = find(parent[i])));
  for (let i = 0; i < prims.length; i++) {
    for (let j = i + 1; j < prims.length; j++) {
      if (overlaps(prims[i], prims[j])) parent[find(i)] = find(j);
    }
  }
  const weight = new Map();
  for (const [i, p] of prims.entries()) {
    const rootIndex = find(i);
    weight.set(rootIndex, (weight.get(rootIndex) || 0) + p.count);
  }
  let bestRoot = 0;
  for (const [rootIndex, count] of weight) {
    if (count > (weight.get(bestRoot) || 0)) bestRoot = rootIndex;
  }
  for (const [i, p] of prims.entries()) {
    if (find(i) === bestRoot) continue;
    console.log(`dropped stray geometry ${p.mesh.getName()}: ${p.count} vertices`
      + ` at ${p.min.map((v) => v.toFixed(1)).join(',')}`
      + `..${p.max.map((v) => v.toFixed(1)).join(',')}`);
    p.mesh.removePrimitive(p.prim);
  }
  for (const mesh of root.listMeshes()) {
    if (mesh.listPrimitives().length === 0) mesh.dispose();
  }
}

// ---------------------------------------------------------------- make it a silhouette
// The entity is meant to read as a person-shaped absence, so it ships without any surface at all:
// no base colour, no texture coordinates, no maps. Black albedo also means it needs no lighting,
// which is why the renderer draws it with an empty environment.
for (const material of root.listMaterials()) {
  material.setBaseColorFactor([0, 0, 0, 1]);
  material.setMetallicFactor(0);
  material.setRoughnessFactor(0.9);
  material.setEmissiveFactor([0, 0, 0]);
  material.setBaseColorTexture(null);
  material.setEmissiveTexture(null);
  material.setNormalTexture(null);
  material.setOcclusionTexture(null);
  material.setMetallicRoughnessTexture(null);
  console.log(`material ${material.getName()} -> solid black, no textures`);
}

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

// ---------------------------------------------------------------- stand it up and size it
// The game places this file as metres with the feet at the origin and Y up, and it cannot work the
// size out for itself: a skinned mesh's bounds are not its node bounds. The skinning matrices are
// what decides how big the creature actually draws - this rig scales everything by about a sixth on
// the way through the armature - so the measuring is done here, where the skin can be evaluated.
{
  const I = () => [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1];
  const mul = (a, b) => {
    const out = new Array(16).fill(0);
    for (let c = 0; c < 4; c++) {
      for (let r = 0; r < 4; r++) {
        let sum = 0;
        for (let k = 0; k < 4; k++) sum += a[k * 4 + r] * b[c * 4 + k];
        out[c * 4 + r] = sum;
      }
    }
    return out;
  };
  const local = (node) => {
    const [tx, ty, tz] = node.getTranslation();
    const [qx, qy, qz, qw] = node.getRotation();
    const [sx, sy, sz] = node.getScale();
    const x2 = qx + qx, y2 = qy + qy, z2 = qz + qz;
    const xx = qx * x2, xy = qx * y2, xz = qx * z2;
    const yy = qy * y2, yz = qy * z2, zz = qz * z2;
    const wx = qw * x2, wy = qw * y2, wz = qw * z2;
    return [
      (1 - (yy + zz)) * sx, (xy + wz) * sx, (xz - wy) * sx, 0,
      (xy - wz) * sy, (1 - (xx + zz)) * sy, (yz + wx) * sy, 0,
      (xz + wy) * sz, (yz - wx) * sz, (1 - (xx + yy)) * sz, 0,
      tx, ty, tz, 1,
    ];
  };
  const worlds = new Map();
  const world = (node) => {
    if (worlds.has(node)) return worlds.get(node);
    const parent = node.getParentNode();
    const matrix = parent ? mul(world(parent), local(node)) : local(node);
    worlds.set(node, matrix);
    return matrix;
  };
  const transform = (m, v) => [
    m[0] * v[0] + m[4] * v[1] + m[8] * v[2] + m[12],
    m[1] * v[0] + m[5] * v[1] + m[9] * v[2] + m[13],
    m[2] * v[0] + m[6] * v[1] + m[10] * v[2] + m[14],
  ];

  // Skin every vertex in the rest pose and take the bounds of the result.
  const skin = root.listSkins()[0] || null;
  const joints = skin ? skin.listJoints() : [];
  const ibmAccessor = skin ? skin.getInverseBindMatrices() : null;
  const ibmArray = ibmAccessor ? ibmAccessor.getArray() : null;
  const boneMatrices = joints.map((joint, i) =>
    mul(world(joint), Array.from(ibmArray.slice(i * 16, i * 16 + 16))));

  const min = [Infinity, Infinity, Infinity];
  const max = [-Infinity, -Infinity, -Infinity];
  const grow = (v) => {
    for (let i = 0; i < 3; i++) {
      min[i] = Math.min(min[i], v[i]);
      max[i] = Math.max(max[i], v[i]);
    }
  };
  for (const mesh of root.listMeshes()) {
    for (const prim of mesh.listPrimitives()) {
      const position = prim.getAttribute('POSITION');
      if (!position) continue;
      const pos = position.getArray();
      const count = position.getCount();
      const jointsAcc = prim.getAttribute('JOINTS_0');
      const weightsAcc = prim.getAttribute('WEIGHTS_0');
      if (jointsAcc && weightsAcc) {
        const j = jointsAcc.getArray();
        const w = weightsAcc.getArray();
        const normalized = weightsAcc.getNormalized();
        for (let v = 0; v < count; v++) {
          const p = [pos[v * 3], pos[v * 3 + 1], pos[v * 3 + 2]];
          const out = [0, 0, 0];
          let weightSum = 0;
          for (let k = 0; k < 4; k++) {
            let weight = w[v * 4 + k];
            if (normalized) weight /= 255;
            if (!weight) continue;
            const q = transform(boneMatrices[j[v * 4 + k]], p);
            out[0] += q[0] * weight;
            out[1] += q[1] * weight;
            out[2] += q[2] * weight;
            weightSum += weight;
          }
          grow(weightSum > 0.0001 ? out.map((x) => x / weightSum) : p);
        }
      } else {
        const node = mesh.listPrimitives().length ? null : null;
        for (let v = 0; v < count; v++) grow([pos[v * 3], pos[v * 3 + 1], pos[v * 3 + 2]]);
      }
    }
  }

  const size = [max[0] - min[0], max[1] - min[1], max[2] - min[2]];
  console.log(`skinned bounds ${size.map((v) => v.toFixed(2)).join(' x ')}`
    + ` (min ${min.map((v) => v.toFixed(2)).join(',')})`);

  const TARGET_HEIGHT = 2.1;
  const longest = size.indexOf(Math.max(...size));
  const scale = TARGET_HEIGHT / size[longest];

  // The rotation that turns the longest axis into the vertical one.
  let rotation;
  if (longest === 2) {
    rotation = [1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 1];       // -90 about X: +Y from +Z
  } else if (longest === 0) {
    rotation = [0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1];      // +90 about Z: +Y from +X
  } else {
    rotation = I();
  }

  // Where the eight corners of the box end up after that rotation and the scale.
  const corners = [];
  for (let i = 0; i < 8; i++) {
    const c = transform(rotation, [
      (i & 1 ? max[0] : min[0]) * scale,
      (i & 2 ? max[1] : min[1]) * scale,
      (i & 4 ? max[2] : min[2]) * scale,
    ]);
    corners.push(c);
  }
  const low = [0, 1, 2].map((i) => Math.min(...corners.map((c) => c[i])));
  const high = [0, 1, 2].map((i) => Math.max(...corners.map((c) => c[i])));
  const place = [
    1, 0, 0, 0,
    0, 1, 0, 0,
    0, 0, 1, 0,
    -(low[0] + high[0]) / 2, -low[1], -(low[2] + high[2]) / 2, 1,
  ];

  const normalise = mul(place, mul(rotation, [
    scale, 0, 0, 0,
    0, scale, 0, 0,
    0, 0, scale, 0,
    0, 0, 0, 1,
  ]));

  // Apply it above the whole scene, so the joints inherit it and the skinned vertices follow. The
  // library stores a node as either a matrix or a translation/rotation/scale triple, and setting one
  // clears the other, so the combined transform is decomposed back into a triple.
  const decompose = (m) => {
    const column = (i) => [m[i * 4], m[i * 4 + 1], m[i * 4 + 2]];
    const length = (v) => Math.hypot(v[0], v[1], v[2]);
    const scale = [length(column(0)), length(column(1)), length(column(2))];
    const basis = [0, 1, 2].map((i) => column(i).map((x) => x / (scale[i] || 1)));
    // Rotation matrix -> quaternion (the basis is orthonormal, so this is the standard branch).
    const [r0, r1, r2] = basis;
    const trace = r0[0] + r1[1] + r2[2];
    let q;
    if (trace > 0) {
      const k = Math.sqrt(trace + 1) * 2;
      q = [(r1[2] - r2[1]) / k, (r2[0] - r0[2]) / k, (r0[1] - r1[0]) / k, 0.25 * k];
    } else if (r0[0] > r1[1] && r0[0] > r2[2]) {
      const k = Math.sqrt(1 + r0[0] - r1[1] - r2[2]) * 2;
      q = [0.25 * k, (r1[0] + r0[1]) / k, (r2[0] + r0[2]) / k, (r1[2] - r2[1]) / k];
    } else if (r1[1] > r2[2]) {
      const k = Math.sqrt(1 + r1[1] - r0[0] - r2[2]) * 2;
      q = [(r1[0] + r0[1]) / k, 0.25 * k, (r2[1] + r1[2]) / k, (r2[0] - r0[2]) / k];
    } else {
      const k = Math.sqrt(1 + r2[2] - r0[0] - r1[1]) * 2;
      q = [(r2[0] + r0[2]) / k, (r2[1] + r1[2]) / k, 0.25 * k, (r0[1] - r1[0]) / k];
    }
    return { translation: [m[12], m[13], m[14]], rotation: q, scale };
  };

  for (const node of root.listNodes()) {
    if (node.getParentNode()) continue;
    if (!node.getMesh() && !node.getSkin() && node.listChildren().length === 0) continue;
    const combined = decompose(mul(normalise, local(node)));
    node.setTranslation(combined.translation);
    node.setRotation(combined.rotation);
    node.setScale(combined.scale);
    console.log(`normalised root ${node.getName()}: ${TARGET_HEIGHT} m tall, feet at the origin`
      + ` (scale ${combined.scale.map((v) => v.toFixed(4)).join(',')})`);
  }
}

// ---------------------------------------------------------------- repack
const glb = await io.writeBinary(doc);
const { writeFile } = await import('node:fs/promises');
await writeFile(output, glb);
console.log(`wrote ${output}: ${(glb.byteLength / 1e6).toFixed(2)} MB`);
