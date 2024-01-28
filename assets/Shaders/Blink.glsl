#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_resolution;
uniform float u_time;

void main() {
    vec2 uv = v_texCoords/u_resolution.xy-.5;
    float a = abs(sin(u_time))*(uv.y/tan(uv.y))*(uv.x/tan(uv.x));
    gl_FragColor = vec4(a,a,a,1.0);
}