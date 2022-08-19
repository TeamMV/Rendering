package dev.mv.vgui.elements;

import dev.mv.vgui.GUIElement;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.window.Window;

public class GUILabel extends GUIElement {

    private int lineHeight;
    private String text;
    private BitmapFont font;

    public GUILabel(int x, int y, int lineHeight, String text, BitmapFont font){
        xPos = x;
        yPos = y;
        this.lineHeight = lineHeight;
        this.text = text;
        this.font = font;
    }

    public void setText(String s){
        text = s;
    }

    public String getText(){
        return text;
    }

    @Override
    public void render(Window w) {
        w.draw.text(xPos, yPos, lineHeight, text, font);
    }

    @Override
    public void resize(int width, int height) {

    }
}
