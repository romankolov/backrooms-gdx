// Backrooms world fragment shader: normal mapping, baked fluorescent light, torch, fog and grade.
#ifdef GL_ES
precision mediump float;
#endif

varying vec3 v_pos;
varying vec3 v_normal;
varying vec2 v_uv;
varying vec2 v_lm;
varying float v_mat;
varying float v_aux;
varying float v_emit;

uniform sampler2D u_floorTex;
uniform sampler2D u_floorNormal;
uniform sampler2D u_wallTex;
uniform sampler2D u_wallNormal;
uniform sampler2D u_ceilTex;
uniform sampler2D u_ceilNormal;
uniform sampler2D u_lightmap;

// These two are handed over in chunk space (the renderer subtracts u_origin per draw call), so
// fog and the torch stay accurate a kilometre out, where mediump would fall apart.
uniform vec3 u_camPos;
uniform vec3 u_flashPos;
uniform vec3 u_flashDir;
uniform float u_flashOn;
uniform vec3 u_fogColor;
uniform float u_fogDensity;
// NOT u_time: a uniform declared in both stages must share the exact same precision, and
// the raw clock would overflow mediump anyway. The grain only needs a small wrapped value.
uniform float u_grainTime;
uniform float u_exposure;
uniform float u_normalStrength;
uniform vec2 u_resolution;
uniform float u_lightRange;
uniform float u_debug;

float hash12(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * 0.1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

vec3 aces(vec3 x) {
    return clamp((x * (2.51 * x + 0.03)) / (x * (2.43 * x + 0.59) + 0.14), 0.0, 1.0);
}

void main() {
    vec3 albedo;
    vec3 nmap;
    // The lightmap is sqrt encoded to keep gradient precision in the dark end.
    vec4 raw = texture2D(u_lightmap, v_lm);
    vec4 lm = vec4(raw.rgb * raw.rgb, raw.a * raw.a);   // <- decoded irradiance / u_lightRange
    float side = lm.a;
    vec3 irr;
    vec3 emission = vec3(0.0);

    // Vertical faces (stair risers, the sides of holes, ceiling steps) are lit by the wall
    // channel of the lightmap even when they use the carpet or ceiling material.
    float vert = 1.0 - abs(normalize(v_normal).y);
    vec3 wallLight = vec3(1.0, 0.955, 0.80) * side * u_lightRange;

    if (v_mat < 0.5) {
        // ---- wallpaper ----
        vec2 uv = v_uv * 0.8;                // one tile every 1.25m
        albedo = texture2D(u_wallTex, uv).rgb;
        nmap = texture2D(u_wallNormal, uv).rgb;
        float g = mix(0.55, 1.12, pow(v_aux, 0.8));
        irr = wallLight * g * 0.60;
    } else if (v_mat < 1.5) {
        // ---- carpet ----
        vec2 uv = v_uv * 0.5;                // one tile every 2m
        albedo = texture2D(u_floorTex, uv).rgb;
        nmap = texture2D(u_floorNormal, uv).rgb;
        irr = mix(lm.rgb * u_lightRange * 0.70, wallLight * 0.50, vert);
    } else if (v_mat < 2.5) {
        // ---- ceiling tiles ----
        vec2 uv = v_uv * 0.5;
        albedo = texture2D(u_ceilTex, uv).rgb;
        nmap = texture2D(u_ceilNormal, uv).rgb;
        irr = mix(lm.rgb * u_lightRange * 0.30, wallLight * 0.58, vert);
    } else {
        // ---- fluorescent panel ----
        vec2 p = fract(v_pos.xz + 0.5) * 2.0;
        float frame = step(0.10, p.x) * step(0.10, p.y) * step(p.x, 1.90) * step(p.y, 1.90);
        float tubes = 0.72 + 0.28 * sin(p.y * 26.0);
        // The diffuser is not flat: it is brightest where the tube is behind it and dimmer towards
        // the frame, which is what makes it read as something glowing rather than a white sticker.
        float across = length(p - 1.0);
        float diffuser = 1.18 - 0.30 * across;
        albedo = mix(vec3(0.62, 0.62, 0.60), vec3(0.94, 0.93, 0.86), frame);
        nmap = vec3(0.5, 0.5, 1.0);
        irr = lm.rgb * u_lightRange * 0.30;
        emission = vec3(1.0, 0.985, 0.90) * (0.35 + 0.65 * frame) * tubes * diffuser
                 * v_emit * 3.2;
    }

    // ---- normal mapping (surfaces are axis aligned, so the tangent frame is cheap) ----
    vec3 N = normalize(v_normal);
    vec3 T = abs(N.y) > 0.5 ? vec3(1.0, 0.0, 0.0)
                            : (abs(N.x) > 0.5 ? vec3(0.0, 0.0, 1.0) : vec3(1.0, 0.0, 0.0));
    vec3 B = cross(N, T);
    vec3 tn = nmap * 2.0 - 1.0;
    vec3 nb = normalize(T * tn.x * u_normalStrength + B * tn.y * u_normalStrength + N * tn.z);

    // ---- torch ----
    vec3 toFrag = v_pos - u_flashPos;
    float torchDist = length(toFrag);
    vec3 L = toFrag / max(torchDist, 0.001);
    float cone = smoothstep(0.55, 0.96, dot(L, u_flashDir));
    float atten = 1.0 / (1.0 + torchDist * torchDist * 0.10);
    float ndl = max(dot(nb, -L), 0.0);
    irr += vec3(1.0, 0.94, 0.80) * cone * atten * ndl * u_flashOn * 1.5;

    vec3 V = normalize(u_camPos - v_pos);
    vec3 H = normalize(V + vec3(0.0, 1.0, 0.0));
    float gloss = v_mat < 1.5 ? 26.0 : 14.0;
    float spec = pow(max(dot(nb, H), 0.0), gloss) * (v_mat < 1.5 ? 0.55 : 0.22);
    float illum = dot(lm.rgb, vec3(0.3333));   // already decoded

    vec3 color = albedo * irr + emission;
    color += vec3(1.0, 0.97, 0.88) * spec * illum * u_lightRange;

    // ---- haze: the corridor fades out long before the geometry ends ----
    float dist = length(v_pos - u_camPos);
    float fog = 1.0 - exp(-pow(dist * u_fogDensity, 2.0));
    color = mix(color, u_fogColor, clamp(fog, 0.0, 1.0));

    color *= u_exposure;
    color = aces(color);

    // ---- vignette + a little sensor grain: cheap 1990s camcorder feel ----
    vec2 q = gl_FragCoord.xy / u_resolution;
    float vig = pow(16.0 * q.x * q.y * (1.0 - q.x) * (1.0 - q.y), 0.22);
    color *= mix(0.62, 1.0, vig);
    color += (hash12(gl_FragCoord.xy + u_grainTime * 97.3) - 0.5) * 0.018;

    if (u_debug > 0.5) {
        // Material debug view: red wallpaper, green carpet, blue ceiling, yellow panel.
        vec3 c = v_mat < 0.5 ? vec3(1.0, 0.2, 0.2)
               : (v_mat < 1.5 ? vec3(0.2, 1.0, 0.2)
               : (v_mat < 2.5 ? vec3(0.2, 0.4, 1.0) : vec3(1.0, 1.0, 0.2)));
        gl_FragColor = vec4(c, 1.0);
        return;
    }

    gl_FragColor = vec4(pow(max(color, vec3(0.0)), vec3(0.4545)), 1.0);
}
