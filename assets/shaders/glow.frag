// Additive glow for the fluorescent fixtures (GLSL ES 1.00).
//
// A light is not a bright rectangle: it is the rectangle plus the light spilling off it. The panel
// itself is already drawn by the world shader; this adds the two things it cannot - a hot core that
// blooms a little wider than the tube, and a soft halo that fades into the room. Drawn with
// (GL_ONE, GL_ONE) blending, so whatever is behind it only gets brighter.
#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_corner;
varying float v_strength;

void main() {
    float r = length(v_corner);
    if (r > 1.0) {
        discard;
    }
    // Two falloffs: a long soft one for the room-filling halo, and a short hot one for the tube.
    float halo = pow(1.0 - r, 2.8);
    float core = exp(-r * r * 26.0);
    vec3 color = vec3(1.0, 0.965, 0.88);
    float amount = (halo * 0.55 + core * 1.35) * v_strength;
    gl_FragColor = vec4(color * amount, 1.0);
}
