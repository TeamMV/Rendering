#version 330

in vec4 fColor;
in vec2 fTexCoords;
in float fTexID;

void main(){
    gl_FragColor = fColor;
}