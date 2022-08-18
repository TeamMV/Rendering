package dev.mv.vrender.render;

import dev.mv.vrender.camera.Camera;
import dev.mv.vrender.text.Font;
import dev.mv.vrender.text.TextRenderer;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Window;

import javax.management.InvalidAttributeValueException;

public class Draw {

    private float r = 0.0f, g = 0.0f, b = 0.0f, a = 1.0f;
    private int w, h;

    public final static int CAMERA_DYNAMIC = 0, CAMERA_STATIC = 1;
    private float currentCamMode = CAMERA_DYNAMIC;

    private TextRenderer textRenderer;

    public Draw(int w, int h, Window win) {
        this(w, h, win, new Font("src/fonts/Viga.ttf", 20));
    }

    public Draw(int w, int h, Window win, Font font){
        BatchController.init(win, 1000);
        this.w = w;
        this.h = h;
        textRenderer = new TextRenderer(font, this);
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

    public void text(int x, int y, int height, String s){
        textRenderer.draw(x, y, height, s);
    }

    public void draw(){
        BatchController.finishAndRender();
        currentCamMode = (float)CAMERA_DYNAMIC;
    }

    private float convertCoords(int val, int fullVal){
        return (float)val / (float)fullVal * 2.0f - 1.0f;
    }
}