// VHS camcorder emulation (GLSL ES 1.00).
//
// Everything here is generated from hashes and trigonometric functions: there is no noise texture
// to tile, so nothing repeats and nothing costs texture memory. The order of the passes matters and
// follows what an actual tape does to a picture:
//
//   1. the tape is stretched over a drum, so the geometry bows very slightly (barrel warp);
//   2. the head switch at the bottom of the frame tears the picture sideways once per revolution;
//   3. luma is recorded almost separately from chroma and at a much lower bandwidth, which is why
//      colours smear sideways and bleed over the edges of bright objects;
//   4. the drum never runs at exactly the right speed, so a tracking band drifts up the frame and
//      nudges whatever is under it;
//   5. tape dropout loses short horizontal slices to noise;
//   6. the signal is soft, warm and slightly washed out, so add a touch of grain, scanlines, a
//      little highlight smear, lifted blacks and a vignette.
//
// The temptation with an effect like this is to push every value until the tape is unmistakable,
// which ends with a picture nobody can see through. This is a *good* tape in a *working* camcorder:
// the geometry is straight, the tracking is mostly right, and the character comes from softness,
// a little chroma smear and a little grain. Every displacement below is under a pixel or two at
// 1280 wide, which is exactly how far a real deck is out.
#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_uv;

uniform sampler2D u_tex;
uniform vec2 u_resolution;
uniform float u_time;        // seconds, full range (wrapped on the CPU for mediump)
uniform float u_grainTime;   // seconds modulo 64, safe for mediump
uniform float u_intensity;   // 0 = off, 1 = full camcorder
uniform float u_tracking;    // 0..1, how far the tracking band has drifted
uniform float u_glitch;      // 0..1, spikes when the entity is near / tape is bad
uniform float u_scanline;    // scanline strength

// Cheap hash noise. No texture, no repetition, deterministic per pixel and frame.
float hash(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    f = f * f * (3.0 - 2.0 * f);
    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));
    return mix(mix(a, b, f.x), mix(c, d, f.x), f.y);
}

void main() {
    float strength = u_intensity;
    float glitch = u_glitch;
    vec2 texel = 1.0 / u_resolution;

    // ---------------------------------------------------------------- 1. drum curvature
    // Very slight: a real deck's geometry error is a pixel at the edges, not a fisheye.
    vec2 centered = v_uv - 0.5;
    float r2 = dot(centered, centered);
    vec2 uv = 0.5 + centered * (1.0 + r2 * 0.004 * strength);

    // ---------------------------------------------------------------- 2. head switching
    // The bottom ~0.8% of a VHS frame is written by the other head while the servo changes over:
    // a thin torn strip along the bottom edge, and nothing above it. v_uv.y = 0 is the bottom of the
    // picture, so the strip lives at the low end of the range.
    float bottom = 1.0 - smoothstep(0.0, 0.005, uv.y);
    float tearNoise = hash(vec2(floor(uv.y * u_resolution.y), floor(u_grainTime * 24.0)));
    uv.x += bottom * (0.001 + 0.003 * tearNoise) * strength;

    // ---------------------------------------------------------------- 4. tracking band
    // A soft horizontal band that crawls up the frame. It shifts what is under it by a pixel or
    // two and adds a little noise - a badly tracked tape, not a broken one.
    float bandY = fract(u_tracking);
    float band = exp(-pow((uv.y - bandY) * 22.0, 2.0));
    float bandJitter = (noise(vec2(uv.y * 220.0, u_grainTime * 3.0)) - 0.5) * 0.0008;
    uv.x += (bandJitter + band * 0.0012 * sin(uv.y * 90.0 + u_time * 6.0)) * strength;

    // Dropout streaks: thin horizontal slices shoved sideways. Rare, and mostly tied to the glitch
    // amount, so they read as damage rather than as a permanent feature of the picture.
    float glitchSeed = hash(vec2(floor(u_grainTime * 2.0), 7.0));
    float streak = step(0.9997 - glitch * 0.02,
                        hash(vec2(floor(uv.y * 90.0), floor(u_grainTime * 4.0))));
    uv.x += streak * (0.001 + 0.006 * glitchSeed) * strength;

    // The warped frame must not sample outside the image: clamp instead of painting a black border,
    // which on a curved CRT is invisible anyway and on a rectangle looks like a broken mask.
    uv = clamp(uv, vec2(0.0), vec2(1.0));

    // ---------------------------------------------------------------- 3. chroma smear
    // Chroma bandwidth is a fraction of luma's, so colour is dragged sideways (never vertically,
    // never radially - the delay line only runs one way) and softened.
    float smear = (0.0007 + 0.0005 * band + 0.0004 * streak + 0.0013 * glitch) * strength;
    float cr = texture2D(u_tex, uv + vec2(smear, 0.0)).r;
    float cg = texture2D(u_tex, uv).g;
    float cb = texture2D(u_tex, uv - vec2(smear, 0.0)).b;

    // Soften the colour a little further with a two-tap average; luma (the green channel) stays
    // sharp, which is what gives the picture its crisp edges and soft, bleeding colours.
    cr = cr * 0.75 + texture2D(u_tex, uv + vec2(smear * 1.6, 0.0)).r * 0.25;
    cb = cb * 0.75 + texture2D(u_tex, uv - vec2(smear * 1.6, 0.0)).b * 0.25;

    vec3 color = vec3(cr, cg, cb);

    // ---------------------------------------------------------------- 6a. highlight smear
    // Tape is soft: bright things trail sideways by a few pixels. This is the whole "glow" budget.
    vec3 trail = vec3(0.0);
    trail += texture2D(u_tex, uv + vec2(1.5, 0.0) * texel).rgb;
    trail += texture2D(u_tex, uv + vec2(3.0, 0.0) * texel).rgb;
    trail += texture2D(u_tex, uv - vec2(1.5, 0.0) * texel).rgb;
    trail *= 0.3333;
    float bright = max(0.0, dot(trail, vec3(0.333)) - 0.90);
    color += trail * bright * 0.20 * strength;

    // ---------------------------------------------------------------- 6b. scanlines and grain
    float scan = sin(gl_FragCoord.y * 3.14159) * 0.5 + 0.5;      // one dark line per 2 pixels
    color *= mix(1.0, mix(0.975, 1.01, scan), u_scanline);

    // Fine tape grain: a per-pixel hash that changes every frame.
    float grain = hash(gl_FragCoord.xy + vec2(u_grainTime * 137.0, u_grainTime * 91.0));
    color += (grain - 0.5) * (0.006 + 0.008 * band + 0.010 * glitch) * strength;

    // Real dropout: a handful of short speckles, not snowfall.
    float dropout = step(0.99992 - glitch * 0.0004,
                         hash(floor(gl_FragCoord.xy * vec2(0.5, 0.5)) + floor(u_grainTime * 24.0)));
    color = mix(color, vec3(0.08 + 0.8 * dropout), dropout * 0.15 * strength);

    // The tracking band loses a little colour and brightness, nothing more.
    color = mix(color, vec3(dot(color, vec3(0.333))) + (hash(gl_FragCoord.xy) - 0.5) * 0.03,
                band * 0.06 * strength);
    color *= 1.0 - band * 0.012 * strength;

    // ---------------------------------------------------------------- 6c. grade
    // Consumer tape: warm, slightly green, blacks lifted, highlights rolled off, low saturation.
    float lum = dot(color, vec3(0.299, 0.587, 0.114));
    color = mix(vec3(lum), color, 0.965);
    color = color * vec3(1.008, 1.0, 0.985) + vec3(0.006, 0.006, 0.005);
    color = color / (1.0 + max(color - 0.94, 0.0) * 0.7);

    // Vignette, mild: the corners of a camcorder lens, not a dark tunnel.
    float vig = pow(16.0 * v_uv.x * v_uv.y * (1.0 - v_uv.x) * (1.0 - v_uv.y), 0.10);
    color *= mix(1.0, mix(0.975, 1.0, vig), strength);

    gl_FragColor = vec4(color, 1.0);
}
