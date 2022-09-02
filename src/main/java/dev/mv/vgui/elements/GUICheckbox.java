package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class GUICheckbox extends GUIElement implements Clickable {

    private int size;
    @Getter
    private boolean enabled = false;
    private ClickListener listener;
    private GUILabel label;

    public GUICheckbox(VariablePosition position, String text, BitmapFont font, ClickListener listener){
        this(position.getX(), position.getY(), position.getWidth(), text, font, listener);
        positionCalculator = position;
    }

    public GUICheckbox(int x, int y, int size, String text, BitmapFont font, ClickListener listener){
        xPos = x;
        yPos = y;
        this.size = size;
        if(font != null) {
            label = new GUILabel(x + size + 10, y + 10, size - 10, text, font);
        }
        this.listener = listener;
    }

    @Override
    public void render(Window w) {
        if (w.input.mouseInside(xPos, yPos, xPos + size, yPos + size)) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        w.draw.rectangle(xPos + 10, yPos, size - 20, size);
        w.draw.triangle(xPos, yPos + size / 2, xPos + 10, yPos + size, xPos + 10, yPos);
        w.draw.triangle(xPos + size, yPos + size / 2, xPos + size - 10, yPos + size, xPos + size - 10, yPos);

        w.draw.color(40, 40, 40, 255);

        w.draw.rectangle(xPos + 13, yPos + 5, size - 26, size - 10);
        w.draw.triangle(xPos + 5, yPos + size / 2, xPos + 13, yPos + size - 5, xPos + 13, yPos + 5);
        w.draw.triangle(xPos + size - 5, yPos + size / 2, xPos + size - 13, yPos + size - 5, xPos + size - 13, yPos + 5);

        if (w.input.mouseInside(xPos, yPos, xPos + size, yPos + size)) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        if(enabled) w.draw.image(xPos + 15, yPos + 15, size - 30, size -30, DefaultTextures.BUTTON_TICK);

        w.draw.color(255, 255, 255, 255);

        label.render(w);

        w.draw.color(0, 0, 0, 255);
    }

    @Override
    public void resize(int width, int height) {
        if(positionCalculator != null){
            positionCalculator.resize(width, height);
        }
    }

    @Override
    public void setXPos(int x){
        xPos = x;
        label.setXPos(x + size + 10);
        if(positionCalculator == null) return;
        positionCalculator.setX(x);
    }

    @Override
    public void setYPos(int y){
        yPos = y;
        label.setYPos(y + 10);
        if(positionCalculator == null) return;
        positionCalculator.setY(y);
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public int getWidth() {
        return size + 10 + label.getWidth();
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if (x >= xPos && x <= xPos + size && y >= yPos && y <= yPos + size) {
            enabled = !enabled;
            listener.clicked(this);
        }
    }

    @Override
    public void release(int x, int y, int mods) {

    }
}
