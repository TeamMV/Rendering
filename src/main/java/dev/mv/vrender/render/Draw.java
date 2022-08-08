package dev.mv.vrender.render;

public class Draw {

    Batch batch;
    float r = 0.0f, g = 0.0f, b = 0.0f, a = 1.0f;
    int w, h;

    public Draw(int w, int h){
        batch = new Batch(1000);
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
        float ax = convertCoords(x, w);
        float ay = convertCoords(y, h);
        float ax2 = convertCoords(x + width, w);
        float ay2 = convertCoords(y + height, h);

        batch.addVertices(new float[][]{
                {ax, ay2, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax, ay, 0.0f,      r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax2, ay, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax2, ay2, 0.0f,    r, g, b, a, 0.0f, 0.0f, 0.0f}
        });
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        float ax1 = convertCoords(x1, w);
        float ay1 = convertCoords(y1, w);
        float ax2 = convertCoords(x2, w);
        float ay2 = convertCoords(y2, w);
        float ax3 = convertCoords(x3, w);
        float ay3 = convertCoords(y3, w);

        batch.addVertices(new float[][]{
                {ax1, ay1, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax2, ay2, 0.0f,      r, g, b, a, 0.0f, 0.0f, 0.0f},
                {ax3, ay3, 0.0f,     r, g, b, a, 0.0f, 0.0f, 0.0f}
        });
    }

    public void draw(){
        batch.finish();
        batch.render();
    }

    private float convertCoords(int val, int fullVal){
        return (float)val / (float)fullVal * 2.0f - 1.0f;
    }
}