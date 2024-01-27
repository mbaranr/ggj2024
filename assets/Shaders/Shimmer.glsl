#ifdef GL_ES
precision highp float;
#endif

// Passed in values from Java
uniform vec2 center; // Where we start from
uniform float u_time; // effect elapsed time

uniform sampler2D sceneTex; // 0
varying vec2 v_texCoords;

void main()
{
    // get pixel coordinates
    vec2 l_texCoords = v_texCoords;

    vec3 shockParams = vec3(10.0, 0.8, 0.1);

    float offset = (u_time- floor(u_time))/u_time;
    float CurrentTime = (u_time)*(offset);


    //get distance from center
    float distance = distance(v_texCoords, center);

    if ( (distance <= (CurrentTime + shockParams.z)) && (distance >= (CurrentTime - shockParams.z)) ) {
        float diff = (distance - CurrentTime);
        float powDiff = 0.0;
        if(distance>0.05){
            powDiff = 1.0 - pow(abs(diff*shockParams.x), shockParams.y);
        }
        float diffTime = diff  * powDiff;
        vec2 diffUV = normalize(v_texCoords-center);
        //Perform the distortion and reduce the effect over time
        l_texCoords = v_texCoords + ((diffUV * diffTime)/(CurrentTime * distance * 40.0));
    }
    gl_FragColor = texture2D(sceneTex, l_texCoords);

}