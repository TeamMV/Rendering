package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.window.Window;

public class GUIButton extends GUIElement implements Clickable {

    private int width, height;
    private GUILabel label;

    private ClickListener listener;

    public GUIButton(int x, int y, int height, String text, BitmapFont font, ClickListener listner){
        xPos = x;
        yPos = y;
        this.listener = listner;
        this.width = SizeLayout.getWidth(text, height) + 50;
        this.height = height;
        label = new GUILabel(x + (width / 2) - (SizeLayout.getWidth() / 2), y, height, text, font);
    }

    public GUIButton(int x, int y, int width, int height, String text, BitmapFont font, ClickListener listner){
        xPos = x;
        yPos = y;
        this.listener = listner;
        this.width = width;
        this.height = height;
        SizeLayout.setText(text, height);
        label = new GUILabel(x + (width / 2) - (SizeLayout.getWidth() / 2), y, height, text, font);
    }

    @Override
    public void render(Window w) {
        w.draw.color(0, 0, 0, 255);
        w.draw.rectangle(xPos, yPos, width, height);
        w.draw.color(117, 117, 117, 255);

        if(w.input.mouseInside(xPos, yPos, xPos + width, yPos + height)){
            w.draw.color(140, 140, 140, 255);
        }

        w.draw.rectangle(xPos + 3, yPos + 3, width - 6, height- 6);
        w.draw.color(255, 255, 255, 255);
        label.render(w);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if(x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height){
            listener.clicked(this);
        }
    }
}
