package dev.mv.vgui.elements;

import dev.mv.vgui.GUIElement;
import dev.mv.vrender.window.Window;

public class GUILabel extends GUIElement {

    private int lineHeight;
    private String text;

    public GUILabel(int x, int y, int lineHeight, String text){
        xPos = x;
        yPos = y;
        this.lineHeight = lineHeight;
        this.text = text;
    }

    public void setText(String s){
        text = s;
    }

    public String getText(){
        return text;
    }

    @Override
    public void render(Window w) {
        w.draw.text(xPos, yPos, lineHeight, text);
    }

    @Override
    public void resize(int width, int height) {

    }
}
