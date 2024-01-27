#ifdef GL_ES
precision mediump float;
#endif

uniform float uFlashSpeed;
uniform vec4 uFlashColour;
uniform float time;

void main ( )
{
    float t = sin ( time * uFlashSpeed ) * 0.5f + 0.5f;
    vec4 orig = texture2D ( texture, gl_TexCoord[0].xy );

    gl_FragColor = mix ( orig, uFlashColour, t );
}