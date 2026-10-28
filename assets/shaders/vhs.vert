// Fullscreen pass for the VHS camcorder look (GLSL ES 1.00).
// A plain quad in clip space: the fragment shader does all the work.
#ifdef GL_ES
precision highp float;
#endif

attribute vec2 a_position;
varying vec2 v_uv;

void main() {
    // a_position is in [-1,1]; flip y so the texture is sampled the right way up.
    v_uv = vec2(a_position.x * 0.5 + 0.5, 0.5 - a_position.y * 0.5);
    gl_Position = vec4(a_position, 0.0, 1.0);
}
