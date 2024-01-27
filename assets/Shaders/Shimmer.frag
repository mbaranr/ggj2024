#version 150
#ifdef GL_ES
    #define PRECISION mediump
    precision PRECISION float;
    precision PRECISION int;
#else
    #define PRECISION
    #define as 10
#endif

#define FREQUENCY_FACTOR 5

varying vec2 v_textCoords