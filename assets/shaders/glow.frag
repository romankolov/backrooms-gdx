// Additive glow for the fluorescent ceiling panels (GLSL ES 1.00).
//
// A fluorescent fitting does not glow as a ball: the whole 0.92 m diffuser is lit, its brightness is
// even across the face, and the light only spills a little past the edges onto the tiles around it.
// So the falloff here is rectangular - Chebyshev distance from the panel's centre, not Euclidean -
// which keeps the glow square, flat against the ceiling, and the same height as the panel it
// belongs to.
//
// Drawn with (GL_ONE, GL_ONE) blending: whatever is behind it only gets brighter.
#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_local;
varying float v_strength;

/** Half the width of the panel itself, in metres, passed in by the renderer. */
uniform float u_panelHalf;

void main() {
    // Square panels, so the distance to the edge is the larger of the two axes.
    float edge = max(abs(v_local.x), abs(v_local.y));

    // Only a gentle lift across the diffuser: the panel geometry underneath already carries the
    // brightness, the tube stripes and the frame, and drowning those in additive white is what made
    // the fitting look like a glowing card. This pass is here for the spill.
    float face = 0.35 * (1.0 - smoothstep(u_panelHalf * 0.86, u_panelHalf * 1.04, edge));
    // The spill onto the surrounding tiles: a short, square falloff, not a halo.
    float spill = exp(-max(edge - u_panelHalf, 0.0) * 7.0) * 0.45;

    float amount = (face + spill * 0.95) * v_strength;
    if (amount < 0.002) {
        discard;
    }
    vec3 tint = vec3(1.0, 0.975, 0.925);
    gl_FragColor = vec4(tint * amount, 1.0);
}
