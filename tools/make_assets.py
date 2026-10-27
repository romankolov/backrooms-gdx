#!/usr/bin/env python3
"""Builds the runtime assets for the Backrooms game.

Textures come from https://github.com/amini-allight/backrooms-textures (CC0 1.0).
They are downscaled/re-encoded here so the APK stays small:
  * diffuse maps: 1024px for wall/carpet, 512px for ceiling
  * normal maps: 512px
  * the fluorescent panel's emission mask is packed into the alpha channel of a
    single PNG so the shader only needs one sampler for it.

Audio is synthesised from scratch (no third-party samples): muffled carpet
footsteps, a fluorescent tube buzz loop and a low room tone loop.

Usage:  python3 tools/make_assets.py [path/to/backrooms-textures]
"""
import math
import os
import random
import subprocess
import sys
import urllib.request
import wave

from PIL import Image, ImageFilter

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ASSETS = os.path.join(ROOT, "assets")
TEX_OUT = os.path.join(ASSETS, "textures")
SFX_OUT = os.path.join(ASSETS, "sounds")
SOURCE_REPO = "https://github.com/amini-allight/backrooms-textures"

try:
    import numpy as np
except ImportError:  # pragma: no cover
    sys.exit("numpy is required: pip install numpy")

try:
    import soundfile as sf
except ImportError:  # pragma: no cover
    sys.exit("soundfile is required: pip install soundfile")


def log(msg):
    print("[assets] " + msg)


# --------------------------------------------------------------------------
# textures
# --------------------------------------------------------------------------
def find_source(cli_arg):
    if cli_arg:
        return cli_arg
    for cand in ("/tmp/backrooms-textures", os.path.join(ROOT, "backrooms-textures")):
        if os.path.isdir(os.path.join(cand, "img")):
            return cand
    dest = os.path.join(ROOT, "backrooms-textures")
    log("cloning %s" % SOURCE_REPO)
    subprocess.check_call(["git", "clone", "--depth", "1", SOURCE_REPO + ".git", dest])
    return dest


def convert(src, dst, size, quality=90, keep_alpha=False):
    im = Image.open(src)
    im = im.convert("RGBA" if keep_alpha else "RGB")
    if size and im.size != (size, size):
        im = im.resize((size, size), Image.LANCZOS)
    if keep_alpha:
        im.save(dst, optimize=True)
    else:
        im.save(dst, quality=quality, subsampling=1)
    log("%-34s %4dpx  %6.1f KiB" % (os.path.basename(dst), im.size[0], os.path.getsize(dst) / 1024.0))


def convert_from_image(im, dst, size, quality=90):
    im = im.convert("RGB")
    if size and im.size != (size, size):
        im = im.resize((size, size), Image.LANCZOS)
    im.save(dst, quality=quality, subsampling=1)
    log("%-34s %4dpx  %6.1f KiB" % (os.path.basename(dst), im.size[0],
                                    os.path.getsize(dst) / 1024.0))


def sharpen_normal(im, strength=1.0):
    """Normal maps survive downscaling better with a touch of contrast."""
    from PIL import ImageEnhance
    return ImageEnhance.Contrast(im).enhance(1.0 + 0.25 * strength)


def build_textures(src_dir):
    """Re-encodes the CC0 maps at the size the game samples them.

    The world shader uses one tiled texture per surface type (carpet, wallpaper, ceiling
    tiles) plus a normal map for each; all of them are power of two so GLES2 can mipmap
    and repeat them.
    """
    os.makedirs(TEX_OUT, exist_ok=True)
    img = os.path.join(src_dir, "img")

    convert(os.path.join(img, "backrooms-carpet-diffuse.png"),
            os.path.join(TEX_OUT, "carpet_diffuse.jpg"), 1024, 90)
    convert(os.path.join(img, "backrooms-wall-diffuse.png"),
            os.path.join(TEX_OUT, "wall_diffuse.jpg"), 1024, 90)
    convert(os.path.join(img, "backrooms-ceiling-tile-diffuse.png"),
            os.path.join(TEX_OUT, "ceiling_diffuse.jpg"), 512, 90)

    for name, out, size, crop in (("backrooms-carpet-normal.png", "carpet_normal.jpg", 512, 0.0),
                                  ("backrooms-wall-normal.png", "wall_normal.jpg", 512, 0.0),
                                  ("backrooms-ceiling-tile-normal.png", "ceiling_normal.jpg", 512,
                                   0.045)):
        im = Image.open(os.path.join(img, name)).convert("RGB")
        if crop > 0:
            # The ceiling normal map carries a coloured border that reads as a bright grid on
            # the ceiling; trim it before scaling.
            w, h = im.size
            dx, dy = int(w * crop), int(h * crop)
            im = im.crop((dx, dy, w - dx, h - dy))
        im = sharpen_normal(im)
        convert_from_image(im, os.path.join(TEX_OUT, out), size, 90)


# --------------------------------------------------------------------------
# icons
# --------------------------------------------------------------------------
def build_icons():
    res = os.path.join(ROOT, "android", "res")
    densities = {"mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}

    def draw(size):
        img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
        px = img.load()
        # Yellow wallpaper backdrop with a dark doorway and a bright tube light.
        base = (196, 188, 108)
        for y in range(size):
            for x in range(size):
                n = ((x * 7 + y * 3) % 11) / 11.0
                shade = 0.88 + 0.12 * n
                # vertical wallpaper stripes
                stripe = 0.93 if (x * 8 // size) % 2 == 0 else 1.0
                px[x, y] = (int(base[0] * shade * stripe), int(base[1] * shade * stripe),
                            int(base[2] * shade * stripe), 255)
        # perspective corridor
        cx, cy = size * 0.5, size * 0.5
        for y in range(size):
            for x in range(size):
                dx = (x - cx) / (size * 0.5)
                dy = (y - cy) / (size * 0.5)
                t = 1.0 / max(0.18, math.hypot(dx, dy))
                f = min(1.0, t / 4.0)
                r, g, b, a = px[x, y]
                dark = 0.45 + 0.55 * (1.0 - f)
                px[x, y] = (int(r * dark), int(g * dark), int(b * dark), a)
        # ceiling light
        lw, lh = max(2, size // 3), max(2, size // 12)
        for y in range(int(size * 0.16), int(size * 0.16) + lh):
            for x in range(int(cx - lw / 2), int(cx + lw / 2)):
                if 0 <= x < size and 0 <= y < size:
                    px[x, y] = (255, 252, 224, 255)
        # corridor floor
        for y in range(int(size * 0.72), size):
            for x in range(size):
                r, g, b, a = px[x, y]
                px[x, y] = (int(r * 0.82), int(g * 0.78), int(b * 0.62), a)
        return img

    for density, size in densities.items():
        d = os.path.join(res, "mipmap-" + density)
        os.makedirs(d, exist_ok=True)
        icon = draw(size)
        icon.save(os.path.join(d, "ic_launcher.png"), optimize=True)
        # round variant: same art, circular mask
        mask = Image.new("L", (size, size), 0)
        mpx = mask.load()
        for y in range(size):
            for x in range(size):
                if math.hypot(x - size / 2.0 + 0.5, y - size / 2.0 + 0.5) <= size / 2.0 - 0.5:
                    mpx[x, y] = 255
        rounded = icon.copy()
        rounded.putalpha(mask)
        rounded.save(os.path.join(d, "ic_launcher_round.png"), optimize=True)
    log("launcher icons written for %d densities" % len(densities))


# --------------------------------------------------------------------------
# audio
# --------------------------------------------------------------------------
SR = 44100


def write_ogg(path, samples, loop=False):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    peak = float(np.max(np.abs(samples))) or 1.0
    samples = samples / peak * 0.85
    if loop:  # crossfade a few ms so the loop point is inaudible
        fade = int(SR * 0.02)
        head = samples[:fade].copy()
        ramp = np.linspace(0.0, 1.0, fade)
        samples[:fade] = samples[:fade] * ramp + samples[-fade:] * (1.0 - ramp)
        samples[-fade:] = samples[-fade:] * (1.0 - ramp) + head * ramp
    sf.write(path, samples.astype(np.float32), SR, format="OGG", subtype="VORBIS")
    log("%-34s %5.2fs  %6.1f KiB" % (os.path.basename(path), len(samples) / SR,
                                     os.path.getsize(path) / 1024.0))


def one_pole_lowpass(x, cutoff):
    a = math.exp(-2.0 * math.pi * cutoff / SR)
    y = np.empty_like(x)
    acc = 0.0
    for i in range(len(x)):
        acc = (1.0 - a) * x[i] + a * acc
        y[i] = acc
    return y


def make_footstep(rng, duration=0.36, tone=1.0):
    n = int(SR * duration)
    t = np.arange(n) / SR
    # muffled heel thud: filtered noise burst + low body resonance
    noise = rng.uniform(-1.0, 1.0, n)
    burst = np.exp(-t * 34.0)
    body = np.sin(2 * np.pi * 82.0 * tone * t) * np.exp(-t * 22.0) * 0.55
    body += np.sin(2 * np.pi * 128.0 * tone * t) * np.exp(-t * 26.0) * 0.25
    # carpet (short, woolly transient)
    carpet = one_pole_lowpass(noise, 1500.0) * burst * 0.5
    # tiny scuff a few ms in
    scuff_start = int(SR * 0.02)
    scuff = np.zeros(n)
    sl = int(SR * 0.09)
    scuff[scuff_start:scuff_start + sl] = one_pole_lowpass(
        rng.uniform(-1.0, 1.0, sl), 4200.0) * np.exp(-np.arange(sl) / SR * 40.0) * 0.18
    click = np.sin(2 * np.pi * 1900 * t) * np.exp(-t * 200.0) * 0.05
    return carpet + body + scuff + click


def make_buzz(duration=4.0):
    n = int(SR * duration)
    t = np.arange(n) / SR
    # UK mains: 50 Hz hum with harmonics, plus the classic 100 Hz ballast buzz
    hum = np.zeros(n)
    for freq, amp in ((50.0, 0.30), (100.0, 0.55), (150.0, 0.18), (200.0, 0.22),
                      (300.0, 0.10), (400.0, 0.06), (600.0, 0.035), (1200.0, 0.02)):
        hum += amp * np.sin(2 * np.pi * freq * t + freq)
    # rectified-buzz grit: harmonics of 100 Hz amplitude-modulating a noise bed
    rng = np.random.default_rng(7)
    noise = one_pole_lowpass(rng.uniform(-1.0, 1.0, n), 3000.0)
    grit = noise * (0.35 + 0.65 * np.abs(np.sin(2 * np.pi * 100.0 * t)))
    grit *= 0.12
    # slow wobble / flicker of an old tube
    wobble = 1.0 + 0.08 * np.sin(2 * np.pi * 0.7 * t) + 0.05 * np.sin(2 * np.pi * 2.3 * t)
    signal = (hum * 0.5 + grit) * wobble
    return signal


def make_room_tone(duration=8.0):
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(11)
    base = (np.sin(2 * np.pi * 41.0 * t) * 0.35
            + np.sin(2 * np.pi * 61.5 * t + 1.1) * 0.22
            + np.sin(2 * np.pi * 97.0 * t + 2.3) * 0.10)
    air = one_pole_lowpass(rng.uniform(-1.0, 1.0, n), 400.0) * 0.5
    tremolo = 1.0 + 0.15 * np.sin(2 * np.pi * 0.11 * t) + 0.08 * np.sin(2 * np.pi * 0.31 * t + 0.7)
    return (base + air) * tremolo * 0.6


def build_audio():
    rng = np.random.default_rng(3)
    for i in range(4):
        step = make_footstep(np.random.default_rng(100 + i), tone=0.94 + 0.05 * i)
        write_ogg(os.path.join(SFX_OUT, "footstep_%d.ogg" % (i + 1)), step)
    write_ogg(os.path.join(SFX_OUT, "light_buzz.ogg"), make_buzz(), loop=True)
    write_ogg(os.path.join(SFX_OUT, "room_tone.ogg"), make_room_tone(), loop=True)


# --------------------------------------------------------------------------
# font atlas (libGDX bitmap font generated from a TTF)
# --------------------------------------------------------------------------
CHARS = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
         "abcdefghijklmnopqrstuvwxyz"
         "0123456789"
         " .,:;!?'\"()[]{}<>/\\|-+=_*#%&@$^~`°"
         "←↑→↓◄►▲▼•·…")


def build_font(ttf_path, out_base, size=48, padding=2):
    from PIL import ImageFont
    os.makedirs(os.path.dirname(out_base), exist_ok=True)
    font = ImageFont.truetype(ttf_path, size)
    glyphs = []
    x, y = padding, padding
    row_h = 0
    sheet_w = 1024
    for ch in CHARS:
        bbox = font.getbbox(ch)
        w = max(1, bbox[2] - bbox[0])
        h = max(1, bbox[3] - bbox[1])
        if x + w + padding > sheet_w:
            x = padding
            y += row_h + padding
            row_h = 0
        glyphs.append((ch, x, y, w, h, bbox))
        x += w + padding
        row_h = max(row_h, h)
    sheet_h = y + row_h + padding
    # round up to a power of two for older GPUs
    pot = 1
    while pot < sheet_h:
        pot *= 2
    sheet_h = pot
    img = Image.new("L", (sheet_w, sheet_h), 0)
    from PIL import ImageDraw
    draw = ImageDraw.Draw(img)
    for ch, gx, gy, w, h, bbox in glyphs:
        draw.text((gx - bbox[0], gy - bbox[1]), ch, font=font, fill=255)
    png = out_base + ".png"
    img.save(png)
    # BMFont text format, ASCII, one page
    ascent, descent = font.getmetrics()
    line_h = ascent + descent
    lines = [
        'info face="BackroomsSans" size=%d bold=0 italic=0 charset="" unicode=1 '
        'stretchH=100 smooth=1 aa=1 padding=0,0,0,0 spacing=1,1 outline=0' % size,
        'common lineHeight=%d base=%d scaleW=%d scaleH=%d pages=1 packed=0 '
        'alphaChnl=0 redChnl=4 greenChnl=4 blueChnl=4' % (line_h, ascent, sheet_w, sheet_h),
        'page id=0 file="%s"' % os.path.basename(png),
        'chars count=%d' % len(glyphs),
    ]
    for ch, gx, gy, w, h, bbox in glyphs:
        code = ord(ch)
        lines.append('char id=%d x=%d y=%d width=%d height=%d xoffset=%d yoffset=%d '
                     'xadvance=%d page=0 chnl=15'
                     % (code, gx, gy, w, h, bbox[0], bbox[1], int(font.getlength(ch))))
    with open(out_base + ".fnt", "w") as fh:
        fh.write("\n".join(lines) + "\n")
    log("%-34s %dx%d  %6.1f KiB" % (os.path.basename(png), sheet_w, sheet_h,
                                    os.path.getsize(png) / 1024.0))


def main():
    src = find_source(sys.argv[1] if len(sys.argv) > 1 else None)
    build_textures(src)
    build_icons()
    build_audio()
    font_ttf = "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
    bold_ttf = "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"
    if os.path.exists(bold_ttf):
        build_font(bold_ttf, os.path.join(ASSETS, "ui", "font_bold"), size=48)
    if os.path.exists(font_ttf):
        build_font(font_ttf, os.path.join(ASSETS, "ui", "font"), size=44)
    log("done")


if __name__ == "__main__":
    main()
