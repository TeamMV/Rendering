#version 400

in vec4 fColor;
in vec2 fTexCoords;
in float fTexID;

uniform sampler2D TEX_SAMPLER[16];

void main(){
    if(fTexID > 0){
        vec4 c = texture(TEX_SAMPLER[int(fTexID)], fTexCoords);

        gl_FragColor = vec4(c.x + fColor.x, c.y + fColor.y, c.z + fColor.z, c.w);
    }
    else {
        gl_FragColor = fColor;
    }
}