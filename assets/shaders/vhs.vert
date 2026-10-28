// Fullscreen pass for the VHS camcorder look (GLSL ES 1.00).
// A plain quad in clip space: the fragment shader does all the work.
#ifdef GL_ES
precision highp float;
#endif

attribute vec2 a_position;
varying vec2 v_uv;

void main() {
    // No y flip. The scene was rendered into a framebuffer, whose texel rows run bottom-up just like
    // clip space does, so the mapping is a straight [-1,1] -> [0,1]. Flipping here - the habit from
    // 2D sprite work, where textures are stored top-down - renders the whole level upside down:
    // ceiling below, carpet above, and the entity hanging head-down from the lights.
    v_uv = a_position * 0.5 + 0.5;
    gl_Position = vec4(a_position, 0.0, 1.0);
}
