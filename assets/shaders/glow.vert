// Additive light for the fluorescent ceiling panels (GLSL ES 1.00).
//
// This is not a sprite and not a billboard: it is the panel's own rectangle, lying in the ceiling
// plane, a millimetre under the geometry. Expanding it in the horizontal plane is what makes the
// light read as a lit fitting - it is foreshortened exactly like the panel it belongs to, it sits at
// the panel's height, and it goes with the ceiling when you look up at it.
//
// The glow is a little wider than the panel so the spill has somewhere to go; the fragment shader
// knows how wide the panel itself is and keeps the core flat across it.
#ifdef GL_ES
precision highp float;
#endif

attribute vec3 a_center;
attribute vec2 a_corner;
attribute float a_size;
attribute float a_strength;

uniform mat4 u_projView;

varying vec2 v_local;        // metres from the centre of the panel, in the ceiling plane
varying float v_strength;

void main() {
    vec3 world = a_center + vec3(a_corner.x * a_size, 0.0, a_corner.y * a_size);
    v_local = a_corner * a_size;
    v_strength = a_strength;
    gl_Position = u_projView * vec4(world, 1.0);
}
