package dev.mv.vrender.render;

import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.texture.TextureRegion;
import dev.mv.vrender.window.Window;
import org.joml.Vector2f;

public class Draw {

    public final static int CAMERA_DYNAMIC = 0, CAMERA_STATIC = 1;
    private float r = 0.0f, g = 0.0f, b = 0.0f, a = 1.0f;
    private int w, h;
    private float currentCamMode = CAMERA_DYNAMIC;

    private SizeLayout layout = new SizeLayout();

    public Draw(int w, int h, Window win) {
        BatchController.init(win, 1000);
        this.w = w;
        this.h = h;
    }

    public void color(int r, int g, int b, int a) {
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        this.a = a / 255.0f;
    }

    public void mode(int mode) {
        if (mode < 0 && mode > 1) {
            throw new IllegalArgumentException("Camera mode cannot be smaller than 0 or bigger than 1!");
        }

        currentCamMode = (float) mode;
    }

    public void rectangle(int x, int y, int width, int height) {
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        BatchController.addVertices(new float[][] {
                {ax, ay2, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f},
                {ax, ay, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f},
                {ax2, ay, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f},
                {ax2, ay2, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f}
        });
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3) {

        BatchController.addVertices(new float[][] {
                {x1, y1, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f},
                {x2, y2, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f},
                {x3, y3, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, 0.0f, currentCamMode, 0.0f, 0.0f}
        });
    }

    public void image(int x, int y, int width, int height, Texture tex) {
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        int texID = BatchController.addTexture(tex);

        BatchController.addVertices(new float[][] {
                {ax, ay2, 0.0f, 0.0f, r, g, b, a, 0.0f, 0.0f, (float) texID, currentCamMode, 0.0f, 0.0f},
                {ax, ay, 0.0f, 0.0f, r, g, b, a, 0.0f, 1.0f, (float) texID, currentCamMode, 0.0f, 0.0f},
                {ax2, ay, 0.0f, 0.0f, r, g, b, a, 1.0f, 1.0f, (float) texID, currentCamMode, 0.0f, 0.0f},
                {ax2, ay2, 0.0f, 0.0f, r, g, b, a, 1.0f, 0.0f, (float) texID, currentCamMode, 0.0f, 0.0f}
        });
    }

    public void image(int x, int y, int width, int height, Texture tex, float rotation) {
        image(x, y, width, height, tex, rotation, x + width / 2, y + height / 2);
    }

    public void image(int x, int y, int width, int height, Texture tex, float rotation, int originX, int originY) {
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        float radRotation = (float) (rotation * (Math.PI / 180));

        int texID = BatchController.addTexture(tex);

        BatchController.addVertices(new float[][] {
                {ax, ay2, 0.0f, radRotation, r, g, b, a, 0.0f, 0.0f, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax, ay, 0.0f, radRotation, r, g, b, a, 0.0f, 1.0f, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax2, ay, 0.0f, radRotation, r, g, b, a, 1.0f, 1.0f, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax2, ay2, 0.0f, radRotation, r, g, b, a, 1.0f, 0.0f, (float) texID, currentCamMode, (float) originX, (float) originY}
        });
    }

    public void image(int x, int y, int width, int height, TextureRegion tex){
        image(x, y, width, height, tex, 0.0f, 0, 0);
    }

    public void image(int x, int y, int width, int height, TextureRegion tex, float rotation){
        image(x, y, width, height, tex, rotation, x + width / 2, y + height / 2);
    }

    public void image(int x, int y, int width, int height, TextureRegion tex, float rotation, int originX, int originY){
        float ax = x;
        float ay = y;
        float ax2 = x + width;
        float ay2 = y + height;

        float ux0 = tex.getUV()[0];
        float ux1 = tex.getUV()[1];
        float uy1 = tex.getUV()[2];
        float uy0 = tex.getUV()[3];

        float radRotation = (float) (rotation * (Math.PI / 180));

        int texID = BatchController.addTexture(tex.getTexture());

        BatchController.addVertices(new float[][] {
                {ax, ay2, 0.0f, radRotation, r, g, b, a, ux0, uy0, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax, ay, 0.0f, radRotation, r, g, b, a, ux0, uy1, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax2, ay, 0.0f, radRotation, r, g, b, a, ux1, uy1, (float) texID, currentCamMode, (float) originX, (float) originY},
                {ax2, ay2, 0.0f, radRotation, r, g, b, a, ux1, uy0, (float) texID, currentCamMode, (float) originX, (float) originY}
        });
    }

    public void text(int x, int y, int height, String s, BitmapFont font) {

        int charX = 0;

        layout.set(font, "", height);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            float ax = x + charX + layout.getXOffset(c);
            float ay = y - (layout.getHeight(c) + layout.getYOffset(c)) + layout.getHeight();
            float ax2 = x + charX + layout.getWidth(c) + layout.getXOffset(c);
            float ay2 = y + layout.getHeight(c) - (layout.getHeight(c) + layout.getYOffset(c)) + layout.getHeight();

            charX += layout.getWidth(c);

            Vector2f[] uvs = font.getUV(c);
            float ux0 = uvs[0].x;
            float ux1 = uvs[1].x;
            float uy1 = uvs[0].y;
            float uy0 = uvs[1].y;

            int texID = BatchController.addTexture(font.getBitmap());

            BatchController.addVertices(new float[][] {
                    {ax, ay2, 0.0f, 0.0f, r, g, b, a, ux0, uy0, (float) texID, currentCamMode, 0.0f, 0.0f},
                    {ax, ay, 0.0f, 0.0f, r, g, b, a, ux0, uy1, (float) texID, currentCamMode, 0.0f, 0.0f},
                    {ax2, ay, 0.0f, 0.0f, r, g, b, a, ux1, uy1, (float) texID, currentCamMode, 0.0f, 0.0f},
                    {ax2, ay2, 0.0f, 0.0f, r, g, b, a, ux1, uy0, (float) texID, currentCamMode, 0.0f, 0.0f}
            });
        }
    }

    public void draw() {
        BatchController.finishAndRender();
        currentCamMode = (float) CAMERA_DYNAMIC;
    }

    private float convertCoords(int val, int fullVal) {
        return (float) val / (float) fullVal * 2.0f - 1.0f;
    }
}