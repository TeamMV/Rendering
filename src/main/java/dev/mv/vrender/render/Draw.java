package dev.mv.vrender.render;

import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Window;
import org.joml.Vector2f;

public class Draw {

    private float r = 0.0f, g = 0.0f, b = 0.0f, a = 1.0f;
    private int w, h;

    public final static int CAMERA_DYNAMIC = 0, CAMERA_STATIC = 1;
    private float currentCamMode = CAMERA_DYNAMIC;

    private SizeLayout layout = new SizeLayout();

    public Draw(int w, int h, Window win){
        BatchController.init(win, 1000);
        this.w = w;
        this.h = h;
    }

    public void color(int r, int g, int b, int a){
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        this.a = a / 255.0f;
    }

    public void mode(int mode){
        if(mode < 0 && mode > 1){
            throw new IllegalArgumentException("Camera mode cannot be smaller than 0 or bigger than 1!");
        }

        currentCamMode = (float)mode;
    }

    public void rectangle(int x, int y, int width, int height){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        BatchController.addVertices(new float[][]{
                {ax, ay2, 0.0f,     0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode},
                {ax, ay, 0.0f,      0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode},
                {ax2, ay, 0.0f,     0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode},
                {ax2, ay2, 0.0f,    0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode}
        });
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3){

        BatchController.addVertices(new float[][]{
                {x1, y1, 0.0f,    0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode},
                {x2, y2, 0.0f,    0.0f,  r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode},
                {x3, y3, 0.0f,    0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode}
        });
    }

    public void image(int x, int y, int width, int height, Texture tex){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        int texID = BatchController.addTexture(tex);

        BatchController.addVertices(new float[][]{
                {ax, ay2, 0.0f,     0.0f, r, g, b, a, 0.0f, 0.0f, (float)texID, currentCamMode},
                {ax, ay, 0.0f,      0.0f, r, g, b, a, 0.0f, 1.0f, (float)texID, currentCamMode},
                {ax2, ay, 0.0f,     0.0f, r, g, b, a, 1.0f, 1.0f, (float)texID, currentCamMode},
                {ax2, ay2, 0.0f,    0.0f, r, g, b, a, 1.0f, 0.0f, (float)texID, currentCamMode}
        });
    }

    public void image(int x, int y, int width, int height, Texture tex, float rotation){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        int texID = BatchController.addTexture(tex);

        BatchController.addVertices(new float[][]{
                {ax, ay2, 0.0f,  rotation, r, g, b, a, 0.0f, 0.0f, (float)texID, currentCamMode},
                {ax, ay, 0.0f,   rotation, r, g, b, a, 0.0f, 1.0f, (float)texID, currentCamMode},
                {ax2, ay, 0.0f,  rotation, r, g, b, a, 1.0f, 1.0f, (float)texID, currentCamMode},
                {ax2, ay2, 0.0f, rotation, r, g, b, a, 1.0f, 0.0f, (float)texID, currentCamMode}
        });
    }

    public void text(int x, int y, int height, String s, BitmapFont font){

        int texID = BatchController.addTexture(font.getBitmap());
        int charX = 0;

        layout.setFont(font);
        layout.setHeight(height);

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            float ax = x + charX;
            float ay = y;
            float ax2 = x + layout.getWidth(c + "") + charX;
            float ay2 = y + height;

            charX += font.getSpacing() + layout.getWidth(c + "");

            Vector2f[] uvs = font.getUV(c);
            float ux0 = uvs[0].x;
            float ux1 = uvs[1].x;
            float uy1 = uvs[0].y;
            float uy0 = uvs[1].y;

            BatchController.addVertices(new float[][]{
                    {ax, ay2, 0.0f,  0.0f, r, g, b, a, ux0, uy0, (float)texID, currentCamMode},
                    {ax, ay, 0.0f,   0.0f, r, g, b, a, ux0, uy1, (float)texID, currentCamMode},
                    {ax2, ay, 0.0f,  0.0f, r, g, b, a, ux1, uy1, (float)texID, currentCamMode},
                    {ax2, ay2, 0.0f, 0.0f, r, g, b, a, ux1, uy0, (float)texID, currentCamMode}
            });
        }
    }

    public void draw(){
        BatchController.finishAndRender();
        currentCamMode = (float)CAMERA_DYNAMIC;
    }

    private float convertCoords(int val, int fullVal){
        return (float)val / (float)fullVal * 2.0f - 1.0f;
    }
}