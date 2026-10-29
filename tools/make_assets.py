#!/usr/bin/env python3
"""Builds the runtime assets for the Backrooms game.

Textures come from https://github.com/amini-allight/backrooms-textures (CC0 1.0).
They are downscaled/re-encoded here so the APK stays small:
  * diffuse maps: 1024px for wall/carpet, 512px for ceiling
  * normal maps: 512px
  * the fluorescent panel's emission mask is packed into the alpha channel of a
    single PNG so the shader only needs one sampler for it.

Audio is synthesised from scratch (no third-party samples): muffled carpet
footsteps, a fluorescent tube buzz loop, a low room tone loop, the tape hiss of
the VHS camcorder, and the wet steps and roar of the thing in the corridors.

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


def make_tape_hiss(duration=6.0):
    """The sound of a worn VHS tape: broadband hiss, mains hum, and a slow wow/flutter."""
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(17)

    # Tape hiss is not white noise: the head gap rolls off the top end and the preamp rolls off the
    # bottom, so it sits in a band around a few kHz.
    white = rng.uniform(-1.0, 1.0, n)
    bright = white - one_pole_lowpass(white, 400.0)          # high-pass at ~400 Hz
    hiss = one_pole_lowpass(bright, 6500.0) * 0.55

    # The transport adds a low rumble and the electronics a little 50 Hz hum.
    rumble = (np.sin(2 * np.pi * 31.0 * t) * 0.02
              + np.sin(2 * np.pi * 33.7 * t + 0.9) * 0.015)
    hum = np.sin(2 * np.pi * 50.0 * t) * 0.012 + np.sin(2 * np.pi * 100.0 * t) * 0.006

    # Wow and flutter: the speed wanders, which smears the hiss and makes it breathe.
    wow = 1.0 + 0.18 * np.sin(2 * np.pi * 0.35 * t) + 0.10 * np.sin(2 * np.pi * 2.7 * t + 1.3)
    # Occasional dropouts, when the tape loses contact with the head for a few milliseconds.
    drop = np.ones(n)
    for start in rng.integers(0, n - 400, size=max(4, int(duration))):
        length = int(rng.integers(150, 400))
        drop[start:start + length] *= 0.25
    signal = (hiss * wow + rumble + hum) * drop
    return signal


def one_pole_highpass(x, cutoff):
    return x - one_pole_lowpass(x, cutoff)


def soft_clip(x, drive=2.0):
    """tanh saturation: what makes a growl sound like it is tearing out of a throat."""
    return np.tanh(x * drive) / np.tanh(drive)


def reverb(x, decay=0.45, size=0.075):
    """
    Cheap Schroeder reverb: four parallel comb filters, then scaled to a fixed fraction of the dry
    signal's level. Not a real impulse response, but it turns a dry animal noise into something
    happening in a big tiled room, which is the whole point of the roar. Scaling to the dry level
    (rather than hand-tuning the feedback gains) keeps it predictable: a comb bank has a resonant
    gain of its own, and guessing at it once produced a tail a hundred times louder than the roar.
    """
    n = len(x)
    out = np.zeros(n)
    for delay_seconds, gain in ((size, 0.82), (size * 1.31, 0.79), (size * 1.73, 0.76),
                                (size * 2.13, 0.72)):
        d = int(SR * delay_seconds)
        if d < 8:
            continue
        buf = np.zeros(n)
        for i in range(n):
            buf[i] = x[i] + (buf[i - d] * gain if i >= d else 0.0)
        out += buf
    out /= 4.0
    dry = float(np.sqrt(np.mean(x * x))) + 1e-9
    wet = float(np.sqrt(np.mean(out * out))) + 1e-9
    return out * (decay * dry / wet)


def make_entity_step(rng, tone=1.0, duration=0.9):
    """
    A step from something that is not a person: much heavier than the player's, and wet - the
    Backrooms lifeforms are described as finding you by sound, so the step has to carry.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR

    # Sub-bass impact: a short downward sweep, the weight of the thing landing.
    sweep = 46.0 * tone
    phase = 2 * np.pi * (sweep * t - 14.0 * t * t)
    impact = np.sin(phase) * np.exp(-t * 7.0)

    # The body: a low resonance that rings a little longer.
    body = (np.sin(2 * np.pi * 71.0 * tone * t) * np.exp(-t * 5.0) * 0.5
            + np.sin(2 * np.pi * 113.0 * tone * t + 0.7) * np.exp(-t * 6.5) * 0.3)

    # Wetness: filtered noise that squelches as it lands, with a resonant edge.
    noise = rng.uniform(-1.0, 1.0, n)
    squelch = one_pole_lowpass(noise, 900.0) * np.exp(-t * 9.0)
    squelch += one_pole_highpass(one_pole_lowpass(noise, 2600.0), 700.0) * np.exp(-t * 16.0) * 0.4

    # A gurgle under it, so it reads as organic rather than as a door slamming.
    gurgle = np.sin(2 * np.pi * (150.0 + 40.0 * np.sin(2 * np.pi * 7.0 * t)) * t) * np.exp(-t * 8.0) * 0.12

    signal = impact * 0.9 + body * 0.5 + squelch * 0.55 + gurgle
    return soft_clip(signal * 1.3, 1.4)


def make_entity_roar(duration=3.6):
    """
    The roar, built the way the series implies it: something enormous and wet, with a human voice
    buried underneath. Three layers - a sub-bass body, a growling sawtooth throat, and a breathy
    rasp with moving formants - saturated, then thrown into a big room.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(23)

    # The throat pitches up as it inhales and falls away as it runs out of air.
    rise = np.clip(t / 0.45, 0.0, 1.0)
    fall = np.clip((duration - t) / 1.6, 0.0, 1.0)
    pitch = (70.0 + 55.0 * rise) * (0.55 + 0.45 * fall)
    # A slow irregular wobble: a voice this big is never steady.
    pitch *= 1.0 + 0.06 * np.sin(2 * np.pi * 3.1 * t) + 0.03 * np.sin(2 * np.pi * 11.7 * t + 1.1)
    phase = 2 * np.pi * np.cumsum(pitch) / SR

    # 1. the body: sine plus its octave, with the growl amplitude modulation on top.
    growl_rate = 27.0 + 9.0 * np.sin(2 * np.pi * 1.7 * t)
    growl = 0.5 + 0.5 * np.sin(2 * np.pi * np.cumsum(growl_rate) / SR)
    body = np.sin(phase) * 0.55 + np.sin(phase * 2.0) * 0.28 + np.sin(phase * 0.5) * 0.22
    body *= 0.55 + 0.45 * growl

    # 2. the throat: a band-limited sawtooth, harsh and tearing.
    sawtooth = 2.0 * (phase / (2 * np.pi) % 1.0) - 1.0
    throat = sawtooth - one_pole_lowpass(sawtooth, 1800.0)   # keep the rasp, drop the fizz
    throat *= 0.35 + 0.3 * growl

    # 3. breath and spit: noise pushed through two sliding formants.
    noise = rng.uniform(-1.0, 1.0, n)
    formant1 = 320.0 + 260.0 * np.clip(t / duration, 0.0, 1.0)
    breath = one_pole_lowpass(noise, 2600.0) - one_pole_lowpass(noise, 300.0)
    # A cheap formant: ring-modulate the noise band with the formant frequency.
    breath *= 0.25 + 0.5 * np.abs(np.sin(2 * np.pi * formant1 * t))
    breath *= 0.55

    signal = body + throat * 0.8 + breath * 0.5

    # The envelope: slammed into it, held while it grinds, then dying away.
    attack = np.clip(t / 0.12, 0.0, 1.0)
    release = np.clip((duration - t) / 1.5, 0.0, 1.0) ** 1.6
    signal *= attack * release

    signal = soft_clip(signal * 2.2, 2.0)
    # A wet bubbled edge, like something forcing air through liquid.
    bubble = 1.0 + 0.16 * np.sin(2 * np.pi * np.cumsum(13.0 + 5.0 * np.sin(2 * np.pi * 0.9 * t)) / SR)
    signal *= bubble

    tail = reverb(signal, decay=0.45, size=0.085)
    return signal + tail * 0.6


def make_entity_creak(duration=3.2):
    """
    Something enormous leaning on something that should not bend. Stick-slip friction is a stream
    of tiny catches and releases, so the creak is noise gated by an irregular pulse train, with a
    resonant band that glides upward as the weight settles.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(51)

    # The catch-and-release rate wanders: nothing alive creaks at a steady tempo.
    rate = 8.0 + 3.0 * np.sin(2 * np.pi * 0.31 * t) + 1.5 * np.sin(2 * np.pi * 0.83 * t + 1.7)
    grit = (0.5 + 0.5 * np.sin(2 * np.pi * np.cumsum(rate) / SR)) ** 3

    noise = rng.uniform(-1.0, 1.0, n)
    # Two fixed resonances, crossfaded as the weight settles: the filters are one-pole, so the glide
    # is done by mixing rather than by moving the cutoff.
    band_low = one_pole_lowpass(noise, 620.0) - one_pole_lowpass(noise, 250.0)
    band_high = one_pole_lowpass(noise, 1400.0) - one_pole_lowpass(noise, 560.0)
    glide = np.clip(t / duration, 0.0, 1.0) ** 1.5
    creak = (band_low * (1.0 - glide) + band_high * glide) * grit * 1.6

    # A tonal pair under the friction, bending as the thing shifts its weight.
    pitch = 150.0 + 70.0 * glide
    tone = np.sin(2 * np.pi * np.cumsum(pitch) / SR + 0.4 * np.sin(2 * np.pi * 3.1 * t))
    creak += tone * grit * 0.30

    # A sub-bass drone that swells in the middle: mass, felt rather than heard.
    creak += np.sin(2 * np.pi * 41.0 * t) * 0.18 * np.exp(-((t - duration * 0.55) ** 2) / 0.36)

    env = np.clip(t / 0.35, 0.0, 1.0) * np.clip((duration - t) / 0.9, 0.0, 1.0) ** 1.4
    signal = soft_clip(creak * 1.2, 1.2) * env
    return signal + reverb(signal, decay=0.5, size=0.09) * 0.5


def make_entity_chitter(duration=1.8):
    """
    A mouth that is not a mouth: irregular clicks in the presence band, too fast for a jaw, with a
    wet throat underneath. It is the sound of the thing talking to itself a room away.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(77)
    signal = np.zeros(n)

    pos = 0.0
    while pos < duration:
        start = int(pos * SR)
        if start >= n:
            break
        length = int(SR * rng.uniform(0.006, 0.02))
        length = min(length, n - start)
        burst = rng.uniform(-1.0, 1.0, length)
        burst = one_pole_highpass(one_pole_lowpass(burst, 5200.0), 1400.0)
        burst *= np.hanning(length)
        signal[start:start + length] += burst * rng.uniform(0.25, 1.0) * np.exp(-pos * 0.35)
        pos += rng.uniform(0.012, 0.055)

    throat = one_pole_lowpass(rng.uniform(-1.0, 1.0, n), 420.0) * 0.5
    throat *= 0.4 + 0.6 * np.abs(np.sin(2 * np.pi * 6.5 * t))
    signal += throat * 0.35

    env = np.clip(t / 0.15, 0.0, 1.0) * np.clip((duration - t) / 0.4, 0.0, 1.0)
    signal = soft_clip(signal * 1.6, 1.5) * env
    return signal + reverb(signal, decay=0.42, size=0.07) * 0.55


def make_entity_scrape(duration=2.4):
    """
    Something being dragged along a corridor wall: noise through a band that slides around, gated
    by the body catching and releasing, with the weight of the thing landing on the first beat.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(91)

    noise = rng.uniform(-1.0, 1.0, n)
    band_low = one_pole_lowpass(noise, 900.0) - one_pole_lowpass(noise, 400.0)
    band_high = one_pole_lowpass(noise, 2600.0) - one_pole_lowpass(noise, 1100.0)
    # The resonance slides between two bands, which is what makes it drag rather than hiss.
    slide = 0.5 + 0.5 * np.sin(2 * np.pi * 0.7 * t - 1.2)
    grain_rate = 11.0 + 4.0 * np.sin(2 * np.pi * 0.9 * t)
    grain = 0.35 + 0.65 * np.abs(np.sin(2 * np.pi * np.cumsum(grain_rate) / SR)) ** 2
    drag = (band_low * (1.0 - slide) + band_high * slide) * grain * 2.0

    thud = np.sin(2 * np.pi * 58.0 * t) * np.exp(-t * 9.0) * 0.5
    squelch = one_pole_lowpass(noise, 1200.0) * np.exp(-t * 14.0) * 0.45

    signal = drag + thud + squelch
    env = np.clip(t / 0.1, 0.0, 1.0) * np.clip((duration - t) / 0.7, 0.0, 1.0) ** 1.2
    signal = soft_clip(signal * 1.3, 1.4) * env
    return signal + reverb(signal, decay=0.5, size=0.085) * 0.6


def make_entity_breath(duration=3.2):
    """
    A long inhale that gets closer, then an exhale with a wheeze in it. Breath is the one signal in
    the level that is unmistakably alive, so it is the one used when the thing is nearest.
    """
    n = int(SR * duration)
    t = np.arange(n) / SR
    rng = np.random.default_rng(113)
    noise = rng.uniform(-1.0, 1.0, n)

    inhale = np.clip(t / (duration * 0.45), 0.0, 1.0)
    low = one_pole_lowpass(noise, 600.0)
    high = one_pole_lowpass(noise, 2200.0) - one_pole_lowpass(noise, 700.0)
    breath = low * (1.0 - inhale) + high * inhale
    breath *= np.where(t < duration * 0.45,
                       np.clip(t / (duration * 0.35), 0.0, 1.0),
                       np.clip((duration - t) / (duration * 0.55), 0.0, 1.0) ** 1.5)

    wheeze = np.sin(2 * np.pi * np.cumsum(1750.0 + 120.0 * np.sin(2 * np.pi * 5.3 * t)) / SR)
    breath += wheeze * 0.05 * (1.0 - inhale) * np.clip((duration - t) / 1.2, 0.0, 1.0)

    # A wet click where the breath turns around.
    turn = int(duration * 0.45 * SR)
    length = min(900, n - turn)
    click = one_pole_highpass(rng.uniform(-1.0, 1.0, length), 900.0) * np.hanning(length) * 0.5
    breath[turn:turn + length] += click

    signal = soft_clip(breath * 2.0, 1.5)
    return signal + reverb(signal, decay=0.45, size=0.08) * 0.55


def build_audio():
    rng = np.random.default_rng(3)
    for i in range(4):
        step = make_footstep(np.random.default_rng(100 + i), tone=0.94 + 0.05 * i)
        write_ogg(os.path.join(SFX_OUT, "footstep_%d.ogg" % (i + 1)), step)
    write_ogg(os.path.join(SFX_OUT, "light_buzz.ogg"), make_buzz(), loop=True)
    write_ogg(os.path.join(SFX_OUT, "room_tone.ogg"), make_room_tone(), loop=True)
    write_ogg(os.path.join(SFX_OUT, "tape_hiss.ogg"), make_tape_hiss(), loop=True)
    for i in range(3):
        step = make_entity_step(np.random.default_rng(200 + i), tone=0.92 + 0.07 * i)
        write_ogg(os.path.join(SFX_OUT, "entity_step_%d.ogg" % (i + 1)), step)
    write_ogg(os.path.join(SFX_OUT, "entity_roar.ogg"), make_entity_roar())
    # The small noises: the thing leaning on a wall, clicking to itself, dragging, breathing.
    write_ogg(os.path.join(SFX_OUT, "entity_creak.ogg"), make_entity_creak())
    write_ogg(os.path.join(SFX_OUT, "entity_chitter.ogg"), make_entity_chitter())
    write_ogg(os.path.join(SFX_OUT, "entity_scrape.ogg"), make_entity_scrape())
    write_ogg(os.path.join(SFX_OUT, "entity_breath.ogg"), make_entity_breath())


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
    # RGBA with a white face and a transparent background: libGDX reads the alpha channel of the
    # page for glyph coverage, so a grayscale PNG would come out as solid blocks.
    img = Image.new("RGBA", (sheet_w, sheet_h), (255, 255, 255, 0))
    from PIL import ImageDraw
    draw = ImageDraw.Draw(img)
    for ch, gx, gy, w, h, bbox in glyphs:
        draw.text((gx - bbox[0], gy - bbox[1]), ch, font=font, fill=(255, 255, 255, 255))
    png = out_base + ".png"
    img.save(png)
    log("%-34s %dx%d" % (os.path.basename(png), sheet_w, sheet_h))
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
