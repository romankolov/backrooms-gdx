#!/usr/bin/env python3
"""Cut the game's surface textures and light fittings out of `backrooms_vr.glb`.

The model in the repository root is a whole baked Backrooms room from Sketchfab
("Backrooms VR" by carlcapu9, CC-BY-4.0). Its meshes are merged per material, so what is worth
taking from it for an infinite level is the *look* of its surfaces and its light fittings: the
mustard wall paint, the olive-grey carpet, the cream ceiling tiles, the round ceiling lamp.

The textures in the file are atlases with the room's lighting baked in - a bright band where a lamp
was, a dark corner - and they carry only about twenty texels per metre, which is far too coarse to
tile a wall you can walk up to. So each tile is built in four passes:

  1. **crop** the largest clean square of the atlas for that material (black is not a surface: the
     atlases are laid out on black, and the black triangles are the back faces of the bake);
  2. **de-light** it by dividing out its own heavily blurred brightness, so the bake stops being
     part of the pattern;
  3. **make it seamless** by rolling it half a turn and feathering the seam cross;
  4. **add micro-detail** - a deterministic value-noise grain (fibres for the carpet, paper for the
     wall, pores for the ceiling) which is what keeps it crisp at arm's length.

Normal maps are derived from the finished colour tile (a Sobel over its luminance), because the
export carries none.

    python3 tools/extract_vr_assets.py [--glb backrooms_vr.glb] [--out assets/textures]

Everything is deterministic: re-running it on the same model gives the same bytes.
"""

import argparse
import io
import json
import math
import os
import struct

SIZE = 1024


# -------------------------------------------------------------------------------------------------
# glb
# -------------------------------------------------------------------------------------------------

def read_glb(path):
    data = open(path, 'rb').read()
    if struct.unpack('<I', data[:4])[0] != 0x46546C67:
        raise SystemExit('%s is not a GLB' % path)
    off = 12
    doc = None
    blob = None
    while off < len(data):
        length, kind = struct.unpack('<II', data[off:off + 8])
        if kind == 0x4E4F534A:
            doc = json.loads(data[off + 8:off + 8 + length])
        elif kind == 0x004E4942:
            blob = data[off + 8:off + 8 + length]
        off += 8 + length
    return doc, blob


def image_bytes(doc, blob, material_name):
    for material in doc['materials']:
        if material.get('name') != material_name:
            continue
        info = (material.get('pbrMetallicRoughness') or {}).get('baseColorTexture')
        if info is None:
            raise SystemExit('material %s has no base colour texture' % material_name)
        image = doc['images'][doc['textures'][info['index']]['source']]
        view = doc['bufferViews'][image['bufferView']]
        start = view.get('byteOffset', 0)
        return blob[start:start + view['byteLength']]
    raise SystemExit('no material named %s' % material_name)


# -------------------------------------------------------------------------------------------------
# deterministic noise (matches nothing in the game: this is a one-off asset pass)
# -------------------------------------------------------------------------------------------------

def _hash(x, y, seed):
    h = (x * 374761393 + y * 668265263 + seed * 1442695040) & 0xffffffff
    h = (h ^ (h >> 13)) * 1274126177 & 0xffffffff
    return ((h ^ (h >> 16)) & 0xffffff) / float(0xffffff)


def value_noise(x, y, seed, period):
    """Tileable value noise: the lattice wraps every `period` cells."""
    x0 = math.floor(x)
    y0 = math.floor(y)
    fx = x - x0
    fy = y - y0
    ux = fx * fx * (3 - 2 * fx)
    uy = fy * fy * (3 - 2 * fy)

    def at(ix, iy):
        return _hash(ix % period, iy % period, seed)

    a = at(x0, y0)
    b = at(x0 + 1, y0)
    c = at(x0, y0 + 1)
    d = at(x0 + 1, y0 + 1)
    return (a + (b - a) * ux) + ((c + (d - c) * ux) - (a + (b - a) * ux)) * uy


def fbm(x, y, seed, period, octaves=4):
    total = 0.0
    amplitude = 1.0
    norm = 0.0
    cells = period
    for octave in range(octaves):
        total += value_noise(x * cells / period, y * cells / period, seed + octave * 977, cells) \
            * amplitude
        norm += amplitude
        amplitude *= 0.5
        cells *= 2
    return total / norm


# -------------------------------------------------------------------------------------------------
# passes
# -------------------------------------------------------------------------------------------------

def load_png(blob):
    try:
        from PIL import Image
    except ImportError:
        raise SystemExit('this tool needs Pillow: pip install pillow')
    return Image.open(io.BytesIO(blob)).convert('RGB')


def flatten(image, strength=0.9, radius=48, tint=(1.0, 1.0, 1.0), level=0.82):
    """Divide out the image's own low frequency light: both its brightness and its colour.

    The model was lit by warm fluorescent tubes before it was exported, so its textures are a
    *photograph of a lit surface*, not an albedo. Dividing by a heavily blurred copy of each channel
    removes the lamp's falloff and its colour cast together; what is left is the surface's own
    pattern, which is then tinted with the level's palette and scaled to an albedo.
    """
    from PIL import Image, ImageFilter
    import numpy as np
    small = image.resize((max(4, image.width // radius), max(4, image.height // radius)),
                         Image.BILINEAR)
    low = small.resize(image.size, Image.BICUBIC).filter(ImageFilter.GaussianBlur(radius * 0.6))
    a = np.asarray(image).astype(np.float32)
    b = np.maximum(12.0, np.asarray(low).astype(np.float32))
    albedo = np.clip(a / b, 0.0, 4.0) ** strength
    # Squeeze the contrast towards the tint so a baked hotspot cannot survive as a bright patch.
    albedo = albedo ** 0.65
    colour = np.array(tint, dtype=np.float32) * level
    out = albedo * colour[None, None, :] * 255.0
    return Image.fromarray(np.clip(out, 0, 255).astype(np.uint8))


def seamless(image, feather=0.12):
    """Roll the tile half a turn and cross-fade the seam, so its edges meet themselves."""
    from PIL import Image, ImageFilter
    import numpy as np
    w, h = image.size
    rolled = Image.new('RGB', (w, h))
    rolled.paste(image.crop((w // 2, h // 2, w, h)), (0, 0))
    rolled.paste(image.crop((0, h // 2, w // 2, h)), (w // 2, 0))
    rolled.paste(image.crop((w // 2, 0, w, h // 2)), (0, h // 2))
    rolled.paste(image.crop((0, 0, w // 2, h // 2)), (w // 2, h // 2))
    a = np.asarray(rolled).astype(np.float32)
    soft = np.asarray(rolled.filter(ImageFilter.GaussianBlur(
        max(1, int(min(w, h) * feather * 0.6))))).astype(np.float32)
    yy, xx = np.mgrid[0:h, 0:w]
    edge = np.minimum(np.minimum(xx, w - 1 - xx), np.minimum(yy, h - 1 - yy)).astype(np.float32)
    span = max(1, int(min(w, h) * feather))
    weight = (1.0 - np.clip(edge / span, 0, 1))[:, :, None]
    return Image.fromarray(np.clip(a * (1 - weight) + soft * weight, 0, 255).astype(np.uint8))


def add_detail(image, kind, seed, amount=0.10, scale=180.0):
    """Put the high frequencies back: the model is only about twenty texels to the metre."""
    from PIL import Image
    import numpy as np
    a = np.asarray(image).astype(np.float32)
    h, w = a.shape[:2]
    if kind == 'carpet':
        # Fibres: the noise is stretched along one axis and crossed with a finer weave.
        field = 0.62 * noise_field(w, h, scale, 3.0 * scale, seed) \
            + 0.38 * noise_field(w, h, 3.0 * scale, scale, seed + 31)
    elif kind == 'wall':
        # Painted paper: fine grain, with a faint vertical drip.
        field = 0.72 * noise_field(w, h, 2.0 * scale, 2.0 * scale, seed) \
            + 0.28 * noise_field(w, h, 0.8 * scale, 4.0 * scale, seed + 17)
    else:
        # Ceiling tiles: mineral pores, fine and even.
        field = noise_field(w, h, 2.4 * scale, 2.4 * scale, seed, octaves=3)
    gain = 1.0 + (field - 0.5) * 2.0 * amount
    for c in range(3):
        a[:, :, c] *= gain
    return Image.fromarray(np.clip(a, 0, 255).astype(np.uint8))


def noise_field(w, h, cells_x, cells_y, seed, octaves=4):
    """Tileable fractal value noise over an image, as a float array in [0,1]."""
    import numpy as np
    xs = (np.arange(w, dtype=np.float32) + 0.5) / w
    ys = (np.arange(h, dtype=np.float32) + 0.5) / h
    total = np.zeros((h, w), dtype=np.float32)
    norm = 0.0
    amplitude = 1.0
    cx = max(1, int(round(cells_x)))
    cy = max(1, int(round(cells_y)))
    for octave in range(octaves):
        gx = np.arange(cx, dtype=np.float32) / cx
        gy = np.arange(cy, dtype=np.float32) / cy
        lattice = np.array([[_hash(i, j, seed + octave * 977) for i in range(cx)]
                            for j in range(cy)], dtype=np.float32)
        # Smooth interpolation of the lattice, wrapping at the edges so the tile stays seamless.
        fx = xs * cx
        fy = ys * cy
        x0 = np.floor(fx).astype(np.int32)
        y0 = np.floor(fy).astype(np.int32)
        tx = fx - x0
        ty = fy - y0
        ux = (tx * tx * (3 - 2 * tx))[None, :]
        uy = (ty * ty * (3 - 2 * ty))[:, None]
        c00 = lattice[y0 % cy][:, x0 % cx]
        c10 = lattice[y0 % cy][:, (x0 + 1) % cx]
        c01 = lattice[(y0 + 1) % cy][:, x0 % cx]
        c11 = lattice[(y0 + 1) % cy][:, (x0 + 1) % cx]
        top = c00 + (c10 - c00) * ux
        bottom = c01 + (c11 - c01) * ux
        total += (top + (bottom - top) * uy) * amplitude
        norm += amplitude
        amplitude *= 0.5
        cx *= 2
        cy *= 2
    return total / norm


def normal_map(image, strength=2.0):
    """Sobel height to normal. The exports carry none, and a carpet without one is a flat sticker."""
    from PIL import Image
    import numpy as np
    h0 = np.asarray(image.convert('L')).astype(np.float32)
    dx = np.roll(h0, -1, axis=1) - np.roll(h0, 1, axis=1)
    dy = np.roll(h0, -1, axis=0) - np.roll(h0, 1, axis=0)
    nx = -dx * (strength / 255.0)
    ny = -dy * (strength / 255.0)
    nz = np.ones_like(nx)
    length = np.sqrt(nx * nx + ny * ny + nz * nz)
    out = np.stack([128 + 127 * nx / length, 128 + 127 * ny / length,
                    128 + 127 * nz / length], axis=2)
    return Image.fromarray(np.clip(out, 0, 255).astype(np.uint8))


def clean_square(atlas, min_side=192):
    """The largest square of the atlas that carries no black: the atlases are laid out on black."""
    import numpy as np
    luma = np.asarray(atlas.convert('L')).astype(np.int32)
    dark = (luma < 32).astype(np.int32)
    integral = dark.cumsum(axis=0).cumsum(axis=1)
    padded = np.zeros((dark.shape[0] + 1, dark.shape[1] + 1), dtype=np.int64)
    padded[1:, 1:] = integral
    height, width = dark.shape

    def clean(x, y, side):
        total = (padded[y + side, x + side] - padded[y, x + side]
                 - padded[y + side, x] + padded[y, x])
        return total == 0

    best = (min_side, 0, 0)
    for side in range(min(height, width), min_side - 1, -8):
        found = False
        for y in range(0, height - side + 1, 8):
            for x in range(0, width - side + 1, 8):
                if clean(x, y, side):
                    best = (side, x, y)
                    found = True
                    break
            if found:
                break
        if found:
            break
    return best[1], best[2], best[1] + best[0], best[2] + best[0]


def build(glb, material, box, kind, seed, out_dir, name, amount=0.10, tint=(1.0, 1.0, 1.0),
          level=0.82):
    from PIL import Image
    doc, blob = read_glb(glb)
    atlas = load_png(image_bytes(doc, blob, material))
    if box is None:
        box = clean_square(atlas)
    crop = atlas.crop(box)
    crop = crop.resize((SIZE, SIZE), Image.LANCZOS)
    crop = flatten(crop, tint=tint, level=level)
    crop = seamless(crop)
    crop = add_detail(crop, kind, seed, amount)
    crop = seamless(crop, feather=0.07)
    os.makedirs(out_dir, exist_ok=True)
    crop.save(os.path.join(out_dir, name + '_diffuse.jpg'), quality=90, optimize=True)
    normal_map(crop, 1.0 if kind == 'wall' else 2.4).save(
        os.path.join(out_dir, name + '_normal.jpg'), quality=92, optimize=True)
    print('%-10s <- %-13s box %s' % (name, material, box))
    return crop


def build_lamp(glb, out_dir):
    """The round ceiling lamp, on its own, black outside the glass: it is drawn as an emitter."""
    from PIL import Image
    import numpy as np
    doc, blob = read_glb(glb)
    atlas = load_png(image_bytes(doc, blob, 'Lamp'))
    disc = atlas.crop((370, 460, 750, 840)).resize((256, 256), Image.LANCZOS)
    a = np.asarray(disc).astype(np.float32)
    yy, xx = np.mgrid[0:256, 0:256]
    r = np.sqrt((xx - 127.5) ** 2 + (yy - 127.5) ** 2) / 128.0
    # Fade the square off to black so it reads as a recessed fitting, not a sticker with corners.
    mask = np.clip((1.0 - r) / 0.22, 0.0, 1.0)[:, :, None]
    a = a * mask
    lamp = Image.fromarray(np.clip(a, 0, 255).astype(np.uint8))
    lamp.save(os.path.join(out_dir, 'lamp.jpg'), quality=92, optimize=True)
    # The glowing pane itself, without the rim: what the emission multiplies.
    glow = np.clip((a / 255.0) ** 0.8 * 255 * np.clip((0.72 - r) / 0.30, 0, 1)[:, :, None],
                   0, 255)
    Image.fromarray(glow.astype(np.uint8)).save(os.path.join(out_dir, 'lamp_glow.jpg'),
                                                quality=92, optimize=True)
    print('lamp       <- Lamp')
    return lamp


def main():
    here = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    parser = argparse.ArgumentParser()
    parser.add_argument('--glb', default=os.path.join(here, 'backrooms_vr.glb'))
    parser.add_argument('--out', default=os.path.join(here, 'assets', 'textures'))
    parser.add_argument('--preview', default=None)
    args = parser.parse_args()
    if not os.path.exists(args.glb):
        raise SystemExit('no model at %s' % args.glb)

    # The boxes are the largest clean patch of each material, found by measuring which part of the
    # atlas is actually covered by that material's triangles (the rest of the atlas is black).
    built = []
    built.append(('wall', build(args.glb, 'Wall_1', None, 'wall', 101,
                                args.out, 'wall', amount=0.13,
                                tint=(1.0, 0.88, 0.56), level=0.62)))
    built.append(('carpet', build(args.glb, 'Moquette_2', None, 'carpet', 202,
                                  args.out, 'carpet', amount=0.30,
                                  tint=(0.66, 0.60, 0.34), level=0.60)))
    built.append(('ceiling', build(args.glb, 'Ceiling_1', None, 'ceiling', 303,
                                   args.out, 'ceiling', amount=0.10,
                                   tint=(1.0, 0.96, 0.82), level=0.74)))
    build_lamp(args.glb, args.out)

    if args.preview:
        from PIL import Image
        tiles = [(n, t) for n, t in built]
        tiles.append(('lamp', Image.open(os.path.join(args.out, 'lamp.jpg'))))
        cell = 256
        sheet = Image.new('RGB', (cell * len(tiles), cell * 2), (16, 16, 16))
        for i, (name, tile) in enumerate(tiles):
            sheet.paste(tile.resize((cell, cell), Image.LANCZOS), (i * cell, 0))
            tiled = Image.new('RGB', (cell, cell))
            small = tile.resize((cell // 2, cell // 2), Image.LANCZOS)
            for row in range(2):
                for col in range(2):
                    tiled.paste(small, (col * cell // 2, row * cell // 2))
            sheet.paste(tiled, (i * cell, cell))
        sheet.save(args.preview)
        print('preview ->', args.preview)


if __name__ == '__main__':
    main()
