#version 400

precision highp float;

attribute vec3 vertices;

layout(location=0) in vec3 aVertPos;
layout(location=1) in float aRotation;
layout(location=2) in vec4 aColor;
layout(location=3) in vec2 aTexCoords;
layout(location=4) in float aTexID;
layout(location=5) in float aCameraMode;
layout(location=6) in vec2 aRotationOrigin;

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

    vec2 acRotationOrigin = vec2(aRotationOrigin.x / uResX * 2.0 - 1.0, aRotationOrigin.y / uResY * 2.0 - 1.0);

    mat4 rot;
    rot[0] = vec4(cos(aRotation), sin(aRotation), 0.0, 0.0);
    rot[1] = vec4(-sin(aRotation), cos(aRotation), 0.0, 0.0);
    rot[2] = vec4(0.0, 0.0, 1.0, 0.0);
    rot[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 trns;
    trns[0] = vec4(1.0, 0.0, 0.0, -acRotationOrigin.x);
    trns[1] = vec4(0.0, 1.0, 0.0, -acRotationOrigin.y);
    trns[2] = vec4(0.0, 0.0, 1.0, 1.0);
    trns[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 trns2;
    trns2[0] = vec4(1.0, 0.0, 0.0, acRotationOrigin.x);
    trns2[1] = vec4(0.0, 1.0, 0.0, acRotationOrigin.y);
    trns2[2] = vec4(0.0, 0.0, 1.0, 1.0);
    trns2[3] = vec4(0.0, 0.0, 0.0, 1.0);

    mat4 model = mat4(1.0);

    if (aRotation != 0) {
        model = trns * rot *  trns2;
    }

    //camMode: 0 = dynamic; 1 = static;
    if (aCameraMode == 0) {
        gl_Position = uProjection * uView * uZoom * model * vec4(aVertPos, 1.0);
    } else {
        gl_Position = vec4(aVertPos, 1.0) * uProjection * model;
        gl_Position += vec4(-1.0, -1.0, -1.0, aVertPos.x + aVertPos.y + aVertPos.z);
        gl_Position.xyz /= gl_Position.w;
    }
}