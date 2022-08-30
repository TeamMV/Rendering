package dev.mv.vgui.elements;

import dev.mv.vgui.GUIElement;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.window.Window;

public class GUILabel extends GUIElement {

    private int lineHeight;
    private String text;
    private BitmapFont font;

    public GUILabel(int x, int y, int lineHeight, String text, BitmapFont font) {
        xPos = x;
        yPos = y;
        this.lineHeight = lineHeight;
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }

    @Override
    public void render(Window w) {
        w.draw.text(xPos, yPos, lineHeight, text, font);
    }

    @Override
    public void resize(int width, int height) {

    }

    public void setHeight(int height) {
        lineHeight = height;
    }

    public void setY(int y) {
        yPos = y;
    }

    public void setX(int x) {
        xPos = x;
    }
}
