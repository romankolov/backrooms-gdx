// VHS camcorder emulation (GLSL ES 1.00).
//
// Everything here is generated from hashes and trigonometric functions: there is no noise texture
// to tile, so nothing repeats and nothing costs texture memory. The order of the passes matters and
// follows what an actual tape does to a picture:
//
//   1. the tape is stretched over a drum, so the geometry bows slightly (barrel warp);
//   2. the head switch at the bottom of the frame tears the picture sideways once per revolution;
//   3. luma is recorded almost separately from chroma and at a much lower bandwidth, which is why
//      colours smear sideways and bleed over the edges of bright objects;
//   4. the drum never runs at exactly the right speed, so a tracking band drifts up the frame and
//      displaces whatever is under it;
//   5. tape dropout loses whole scanlines to noise;
//   6. the signal is soft, noisy and slightly washed out, so add grain, scanlines, bloom-ish
//      smear, lifted blacks and a vignette.
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
    vec2 uv = v_uv;

    // ---------------------------------------------------------------- 1. drum curvature
    vec2 centered = uv - 0.5;
    float r2 = dot(centered, centered);
    uv = 0.5 + centered * (1.0 + r2 * 0.055 * strength);

    // ---------------------------------------------------------------- 2. head switching
    // The bottom ~1.5% of a VHS frame is written by the other head while the servo changes over,
    // which shows up as a torn, noisy strip along the bottom edge.
    float bottom = smoothstep(0.985, 1.0, uv.y);
    float tearNoise = hash(vec2(floor(uv.y * u_resolution.y), floor(u_grainTime * 24.0)));
    uv.x += bottom * (0.02 + 0.05 * tearNoise) * strength;

    // ---------------------------------------------------------------- 4. tracking band
    // A soft horizontal band that crawls up the frame, displacing and dirtying what is under it.
    float bandY = fract(u_tracking);
    float band = exp(-pow((uv.y - bandY) * 14.0, 2.0));
    float bandJitter = (noise(vec2(uv.y * 220.0, u_grainTime * 3.0)) - 0.5) * 0.012;
    uv.x += (bandJitter + band * 0.018 * sin(uv.y * 90.0 + u_time * 6.0)) * strength;

    // Occasional bigger dropout: a horizontal streak that shoves the picture sideways.
    float glitchSeed = hash(vec2(floor(u_grainTime * 2.0), 7.0));
    float glitchLine = step(0.96 - u_glitch * 0.25, hash(vec2(floor(uv.y * 60.0),
                                                              floor(u_grainTime * 8.0))));
    uv.x += glitchLine * (0.01 + 0.05 * glitchSeed) * strength;

    // ---------------------------------------------------------------- 3. chroma smear
    // Luma is sampled sharply, chroma is dragged sideways and softened: the classic colour bleed.
    vec2 texel = 1.0 / u_resolution;
    float smear = (0.0035 + 0.004 * band + 0.006 * bottom + 0.006 * u_glitch) * strength;
    vec2 dir = normalize(centered + vec2(0.0001));

    float luma = texture2D(u_tex, uv).g;
    vec2 chromaR = uv + dir * smear + vec2(smear * 0.7, 0.0);
    vec2 chromaB = uv - dir * smear - vec2(smear * 0.7, 0.0);

    float cr = texture2D(u_tex, chromaR).r;
    float cg = texture2D(u_tex, uv + vec2(smear * 0.2, 0.0)).g;
    float cb = texture2D(u_tex, chromaB).b;

    // A wider, softer chroma sample: cheap two-tap box, enough to look like 1.5 MHz bandwidth.
    cr = (cr + texture2D(u_tex, chromaR + vec2(texel.x * 3.0, 0.0)).r) * 0.5;
    cb = (cb + texture2D(u_tex, chromaB - vec2(texel.x * 3.0, 0.0)).b) * 0.5;
    luma = (luma + texture2D(u_tex, uv + vec2(texel.x, 0.0)).g) * 0.5;

    vec3 color = vec3(cr, cg, cb);
    color.g = mix(color.g, luma, 0.25);

    // ---------------------------------------------------------------- 6a. soft bloom smear
    // Tape has no real bloom, but highlights smear: sample a few taps around a bright pixel.
    vec3 glow = vec3(0.0);
    glow += texture2D(u_tex, uv + vec2(2.5, 0.0) * texel).rgb;
    glow += texture2D(u_tex, uv + vec2(-2.5, 0.0) * texel).rgb;
    glow += texture2D(u_tex, uv + vec2(0.0, 1.5) * texel).rgb;
    glow += texture2D(u_tex, uv + vec2(0.0, -1.5) * texel).rgb;
    glow *= 0.25;
    float bright = max(0.0, dot(glow, vec3(0.333)) - 0.72);
    color += glow * bright * 0.9 * strength;

    // ---------------------------------------------------------------- 6b. scanlines and grain
    float scan = sin(gl_FragCoord.y * 3.14159) * 0.5 + 0.5;      // one dark line per 2 pixels
    color *= mix(1.0, mix(0.82, 1.06, scan), u_scanline);

    // Fine tape grain: a per-pixel hash that changes every frame.
    float grain = hash(gl_FragCoord.xy + vec2(u_grainTime * 137.0, u_grainTime * 91.0));
    color += (grain - 0.5) * (0.055 + 0.06 * band + 0.05 * u_glitch) * strength;

    // Dropout: short bright/dark speckles where the oxide has flaked off.
    float dropout = step(0.9975 - u_glitch * 0.004,
                         hash(floor(gl_FragCoord.xy * 0.5) + floor(u_grainTime * 30.0)));
    color = mix(color, vec3(0.06 + 0.9 * dropout), dropout * 0.55 * strength);

    // The tracking band itself is full of noise and loses colour.
    color = mix(color, vec3(dot(color, vec3(0.333))) + (hash(gl_FragCoord.xy) - 0.5) * 0.25,
                band * 0.65 * strength);
    color *= 1.0 - band * 0.12 * strength;

    // ---------------------------------------------------------------- 6c. grade
    // Consumer tape: warm, slightly green, blacks lifted, highlights rolled off, low saturation.
    float lum = dot(color, vec3(0.299, 0.587, 0.114));
    color = mix(vec3(lum), color, 0.86);
    color = color * vec3(1.03, 1.0, 0.94) + vec3(0.022, 0.020, 0.016);
    color = color / (1.0 + max(color - 0.86, 0.0) * 1.6);

    // Vignette.
    float vig = pow(16.0 * v_uv.x * v_uv.y * (1.0 - v_uv.x) * (1.0 - v_uv.y), 0.22);
    color *= mix(1.0, mix(0.72, 1.0, vig), strength);

    // Outside the warped frame the tape is black rather than smeared.
    float outside = step(uv.x, 0.0) + step(1.0, uv.x) + step(uv.y, 0.0) + step(1.0, uv.y);
    color = mix(color, vec3(0.0), clamp(outside, 0.0, 1.0));

    gl_FragColor = vec4(color, 1.0);
}
