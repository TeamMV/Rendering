package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vgui.elements.window.layout.HorizontalGUILayout;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.texture.TextureRegion;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

import java.util.function.Consumer;

public class GUIIconButton extends GUIElement implements Clickable {

    private int width, height;
    private Texture tex = null;
    private TextureRegion texReg = null;
    private ClickListener listener;

    private boolean highlightImage;

    public GUIIconButton(VariablePosition position, Texture tex, boolean highlightImage, ClickListener listner) {
        this(position.getX(), position.getY(), position.getWidth(), position.getHeight(), tex, highlightImage, listner);
        positionCalculator = position;
    }

    public GUIIconButton(int x, int y, int width, int height, Texture tex, boolean highlightImage, ClickListener listner) {
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.tex = tex;
        this.listener = listner;
        this.highlightImage = highlightImage;
    }

    public GUIIconButton(VariablePosition position, TextureRegion tex, boolean highlightImage, ClickListener listner) {
        this(position.getX(), position.getY(), position.getWidth(), position.getHeight(), tex, highlightImage, listner);
        positionCalculator = position;
    }

    public GUIIconButton(int x, int y, int width, int height, TextureRegion tex, boolean highlightImage, ClickListener listner) {
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.texReg = tex;
        this.listener = listner;
        this.highlightImage = highlightImage;
    }

    @Override
    public void render(Window w) {
        if (w.input.mouseInside(xPos, yPos, xPos + width, yPos + height)) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        w.draw.rectangle(xPos + 10, yPos, width - 20, height);
        w.draw.triangle(xPos, yPos + height / 2, xPos + 10, yPos + height, xPos + 10, yPos);
        w.draw.triangle(xPos + width, yPos + height / 2, xPos + width - 10, yPos + height, xPos + width - 10, yPos);

        w.draw.color(40, 40, 40, 255);

        w.draw.rectangle(xPos + 13, yPos + 5, width - 26, height - 10);
        w.draw.triangle(xPos + 5, yPos + height / 2, xPos + 13, yPos + height - 5, xPos + 13, yPos + 5);
        w.draw.triangle(xPos + width - 5, yPos + height / 2, xPos + width - 13, yPos + height - 5, xPos + width - 13, yPos + 5);

        if (w.input.mouseInside(xPos, yPos, xPos + width, yPos + height)) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        if (!highlightImage) w.draw.color(0, 0, 0, 255);

        if (tex != null) {
            w.draw.image(xPos + 15, yPos + 15, width - 30, height - 30, tex);
        } else if (texReg != null) {
            w.draw.image(xPos + 15, yPos + 15, width - 30, height - 30, texReg);
        }

        w.draw.color(0, 0, 0, 255);
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        this.width = positionCalculator.getWidth();
        this.height = positionCalculator.getHeight();
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
        if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height) {
            listener.clicked(this);
        }
    }

    @Override
    public void release(int x, int y, int mods) {

    }

    private Consumer<GUIIconButton> createdTask = null;

    public void onCreate(Consumer<GUIIconButton> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }
}
