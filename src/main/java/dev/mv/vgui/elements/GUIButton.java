package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class GUIButton extends GUIElement implements Clickable {

    private int width, height;
    private GUILabel label;

    @Getter
    private boolean enabled = true;

    @Setter
    private ClickListener listener;
    private SizeLayout layout;

    private boolean textWidth = false;
    private Consumer<GUIButton> createdTask = null;

    public GUIButton(int x, int y, boolean centerX, int height, boolean disabled, String text, BitmapFont font, ClickListener listner) {
        layout = new SizeLayout(font, text, height - height / 5);
        xPos = x;
        yPos = y;
        this.enabled = !disabled;
        this.listener = listner;
        this.width = layout.getWidth() + 50;
        if (centerX) {
            xPos = x - width / 2;
        }
        this.height = height;
        label = new GUILabel(xPos + (width / 2) - (layout.getWidth() / 2), yPos + (height / 2 - layout.getHeight('e') / 2), height - height / 5, text, font);
    }

    public GUIButton(int x, int y, boolean centerX, int width, int height, boolean disabled, String text, BitmapFont font, ClickListener listner) {
        layout = new SizeLayout(font, text, height - height / 5);
        xPos = x;
        if (centerX) {
            xPos = x + width / 2;
        }
        yPos = y;
        this.enabled = !disabled;
        this.listener = listner;
        this.width = width;
        this.height = height;
        label = new GUILabel(x + (width / 2) - (layout.getWidth() / 2), y + (height / 2 - layout.getHeight('e') / 2), height - height / 5, text, font);
    }

    public GUIButton(VariablePosition position, boolean centerX, boolean disabled, String text, BitmapFont font, ClickListener listner) {
        this(position, centerX, disabled, text, font, listner, false);
    }

    public GUIButton(VariablePosition position, boolean centerX, boolean disabled, String text, BitmapFont font, ClickListener listner, boolean textWidth) {
        this.textWidth = textWidth;
        xPos = position.getX();
        yPos = position.getY();
        this.enabled = !disabled;
        height = position.getHeight();
        layout = new SizeLayout(font, text, height - height / 5);
        if (textWidth) {
            this.width = layout.getWidth() + 50;
        } else {
            width = position.getWidth();
        }
        if (centerX) {
            xPos = position.getX() + width / 2;
        }
        this.positionCalculator = position;
        this.listener = listner;
        label = new GUILabel(xPos + (width / 2) - (layout.getWidth() / 2), yPos + (height / 2 - layout.getHeight('e') / 2), height - height / 5, text, font);
    }

    @Override
    public void render(Window w) {

        w.draw.color(255, 255, 255, 255);

        if(!enabled) {
            w.draw.color(255, 255, 255, 50);
        }

        w.draw.rectangle(xPos + 10, yPos, width - 20, height);
        w.draw.triangle(xPos, yPos + height / 2, xPos + 10, yPos + height, xPos + 10, yPos);
        w.draw.triangle(xPos + width, yPos + height / 2, xPos + width - 10, yPos + height, xPos + width - 10, yPos);

        w.draw.color(40, 40, 40, 255);

        if (w.input.mouseInside(xPos, yPos, xPos + width, yPos + height)) {
            w.draw.color(13, 132, 148, 255);
        }

        if(!enabled) {
            w.draw.color(40, 40, 40, 50);
        }

        w.draw.rectangle(xPos + 13, yPos + 5, width - 26, height - 10);
        w.draw.triangle(xPos + 5, yPos + height / 2, xPos + 13, yPos + height - 5, xPos + 13, yPos + 5);
        w.draw.triangle(xPos + width - 5, yPos + height / 2, xPos + width - 13, yPos + height - 5, xPos + width - 13, yPos + 5);

        label.setColor(255, 255, 255, 255);

        if(!enabled) {
            label.setColor(255, 255, 255, 50);
        }
        label.render(w);

        w.draw.color(0, 0, 0, 255);
    }

    @Override
    public void setXPos(int x) {
        xPos = x;
        label.setXPos(xPos + (width / 2) - (layout.getWidth() / 2));
        if (positionCalculator == null) return;
        positionCalculator.setX(x);
    }

    @Override
    public void setYPos(int y) {
        yPos = y;
        label.setYPos(y + (height / 2) - (layout.getHeight('e') / 2));
        if (positionCalculator == null) return;
        positionCalculator.setY(y);
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        this.height = positionCalculator.getHeight();
        layout.setHeight(this.height - this.height / 5);
        if (textWidth) {
            this.width = layout.getWidth() + 50;
        } else {
            this.width = positionCalculator.getWidth();
        }
        label.setX(xPos + (this.width / 2) - (layout.getWidth() / 2));
        label.setY(yPos + (this.height / 2 - layout.getHeight('e') / 2));
        label.setHeight(this.height - this.height / 5);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if (listener == null) return;
        if(!enabled) return;
        if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height) {
            listener.clicked(this);
        }
    }

    @Override
    public void release(int x, int y, int mods) {

    }

    public void onCreate(Consumer<GUIButton> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }

    @Override
    public void reset() {

    }

    public void disable(){
        enabled = false;
    }

    public void enable(){
        enabled = true;
    }
}