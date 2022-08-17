#version 400

attribute vec3 vertices;

layout(location=0) in vec3 aVertPos;
layout(location=1) in float aRotation;
layout(location=2) in vec4 aColor;
layout(location=3) in vec2 aTexCoords;
layout(location=4) in float aTexID;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexID;

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uZoom;

uniform float uResX;
uniform float uResY;

void main() {
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexID = aTexID;

    vec2 acVertPos = vec2(aVertPos.x / uResX * 2.0 - 1.0, aVertPos.y / uResY * 2.0 - 1.0);

    mat4 rot;
    rot[0] = vec4(cos(aRotation), sin(aRotation), 0.0, 0.0);
    rot[1] = vec4(-sin(aRotation), cos(aRotation), 0.0, 0.0);
    rot[2] = vec4(0.0, 0.0, 1.0, 0.0);
    rot[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 trns;
    trns[0] = vec4(1.0, 0.0, 0.0, -acVertPos.x);
    trns[1] = vec4(0.0, 1.0, 0.0, -acVertPos.y);
    trns[2] = vec4(0.0, 0.0, 1.0, 1.0);
    trns[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 trns2;
    trns2[0] = vec4(1.0, 0.0, 0.0, acVertPos.x);
    trns2[1] = vec4(0.0, 1.0, 0.0, acVertPos.y);
    trns2[2] = vec4(0.0, 0.0, 1.0, 1.0);
    trns2[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 model = trns;

	gl_Position = uProjection * uView * uZoom * vec4(aVertPos, 1.0);
}