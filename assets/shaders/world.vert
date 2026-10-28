// Backrooms world vertex shader (GLSL ES 1.00, runs on any GLES2 phone).
// Desktop GL 1.20 has no precision statements, so this must stay inside the GL_ES guard.
// highp is always available in vertex shaders; the fragment shader stays mediump, so a uniform
// shared by both stages has to be declared with the same precision in both files.
#ifdef GL_ES
precision highp float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;
attribute vec2 a_lm;
attribute float a_mat;
attribute float a_aux;

uniform mat4 u_projView;
uniform float u_time;
// World position of the chunk this mesh belongs to. Vertices are stored relative to it, so the
// values handed to the fragment stage stay small enough for mediump to interpolate exactly.
uniform vec3 u_origin;

varying vec3 v_pos;
varying vec3 v_normal;
varying vec2 v_uv;
varying vec2 v_lm;
varying float v_mat;
varying float v_aux;
varying float v_emit;

// Old fluorescent ballast: mostly steady with a slow 100 Hz style shimmer and the
// occasional dropout. Driven purely from the clock, so it costs nothing on the GPU.
float flicker(float t, float phase) {
    float slow = sin(t * 23.0 + phase * 37.0);
    float fast = sin(t * 61.0 + phase * 13.0);
    float base = 0.86 + 0.09 * slow + 0.05 * fast;
    float drop = step(0.965, sin(t * 2.3 + phase * 57.0));
    return base * (1.0 - drop * 0.9);
}

void main() {
    // Everything downstream of here works in chunk space; the origin only comes back for the
    // clip space transform, which happens in this highp stage.
    v_pos = a_position;
    v_normal = a_normal;
    v_uv = a_uv;
    v_lm = a_lm;
    v_mat = a_mat;
    v_aux = a_aux;
    v_emit = 0.0;

    if (a_mat > 2.5) {
        if (a_aux < -0.5) {
            v_emit = 1.0;              // steady tube
        } else if (a_aux > 0.999) {
            v_emit = 0.0;              // burned out
        } else {
            v_emit = flicker(u_time, a_aux);
        }
    }

    gl_Position = u_projView * vec4(a_position + u_origin, 1.0);
}
