package dev.mv.vrender.render;

import dev.mv.vrender.camera.Camera;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Window;

public class Draw {

    private float r = 0.0f, g = 0.0f, b = 0.0f, a = 1.0f;
    private int w, h;

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

    public void rectangle(int x, int y, int width, int height){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        BatchController.addVertices(new float[][]{
                {ax, ay2, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax, ay, 0.0f,      r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax2, ay, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax2, ay2, 0.0f,    r, g, b, a, 0.0f, 0.0f, 0.0f}
        });
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3){

        BatchController.addVertices(new float[][]{
                {x1, y1, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {x2, y2, 0.0f,      r, g, b, a, 0.0f, 0.0f, 0.0f},
                {x3, y3, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f}
        });
    }

    public void image(int x, int y, int width, int height, Texture tex){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        BatchController.addTexture(tex);

        BatchController.addVertices(new float[][]{
                {ax, ay2, 0.0f,     r, g, b, a, 0.0f, 0.0f, (float)tex.getID()},
                {ax, ay, 0.0f,      r, g, b, a, 0.0f, 1.0f, (float)tex.getID()},
                {ax2, ay, 0.0f,     r, g, b, a, 1.0f, 1.0f, (float)tex.getID()},
                {ax2, ay2, 0.0f,    r, g, b, a, 1.0f, 0.0f, (float)tex.getID()}
        });
    }

    public void draw(){
        BatchController.finishAndRender();
    }

    private float convertCoords(int val, int fullVal){
        return (float)val / (float)fullVal * 2.0f - 1.0f;
    }
}