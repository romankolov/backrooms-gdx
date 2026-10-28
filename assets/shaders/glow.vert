// Additive light billboards (GLSL ES 1.00).
//
// One quad per ceiling fixture, expanded on the CPU into camera-facing corners, so the glow always
// faces the viewer and costs no geometry. Everything that makes it look like a light rather than a
// disc lives in the fragment shader.
#ifdef GL_ES
precision highp float;
#endif

attribute vec3 a_center;
attribute vec2 a_corner;
attribute float a_size;
attribute float a_strength;

uniform mat4 u_projView;
uniform vec3 u_right;
uniform vec3 u_up;

varying vec2 v_corner;
varying float v_strength;

void main() {
    vec3 world = a_center + (u_right * a_corner.x + u_up * a_corner.y) * a_size;
    v_corner = a_corner;
    v_strength = a_strength;
    gl_Position = u_projView * vec4(world, 1.0);
}
