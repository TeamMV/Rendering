package dev.mv.vgui.elements.window;

import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.GUICloseButton;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class GUIWindow {

    @Getter
    private int x, y, width, height;

    @Getter
    private GUICloseButton button;

    private GUI gui;

    private VariablePosition positionCalculator = null;

    public GUIWindow(VariablePosition position, GUI gui, Runnable onClose) {
        positionCalculator = position;
        x = position.getX();
        y = position.getY();
        width = position.getWidth();
        height = position.getHeight();
        this.gui = gui;

        button = new GUICloseButton(x + width - 64 - 42, y + height - 64 - 10, 64, 64, gui, onClose);
    }

    public GUIWindow(int x, int y, int width, int height, GUI gui, Runnable onClose) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gui = gui;

        button = new GUICloseButton(x + width - 64 - 42, y + height - 64 - 10, 64, 64, gui, onClose);
    }

    public void render(Window w) {
        w.draw.color(255, 255, 255, 255);
        w.draw.rectangle(x, y + 50, width, height - 100);
        w.draw.rectangle(x + 50, y, width - 100, height);

        w.draw.triangle(x, y + height - 50, x + 50, y + height - 50, x + 50, y + height);
        w.draw.triangle(x + width - 50, y + height, x + width - 50, y + height - 50, x + width, y + height - 50);
        w.draw.triangle(x + width - 50, y, x + width - 50, y + 50, x + width, y + 50);
        w.draw.triangle(x, y + 50, x + 50, y + 50, x + 50, y);

        w.draw.color(40, 40, 40, 255);
        w.draw.rectangle(x + 5, y + 50, width - 10, height - 100);
        w.draw.rectangle(x + 50, y + 5, width - 100, height - 10);

        w.draw.triangle(x + 5, y + height - 50, x + 50, y + height - 50, x + 50, y + height - 5);
        w.draw.triangle(x + width - 50, y + height - 5, x + width - 50, y + height - 50, x + width - 5, y + height - 50);
        w.draw.triangle(x + width - 5, y + 50, x + width - 50, y + 50, x + width - 50, y + 5);
        w.draw.triangle(x + 5, y + 50, x + 50, y + 50, x + 50, y + 5);

        button.render(w);
    }

    public void click(int x, int y, int b, int mods) {
        button.click(x, y, b, mods);
    }

    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        x = positionCalculator.getX();
        y = positionCalculator.getY();
        this.width = positionCalculator.getWidth();
        this.height = positionCalculator.getHeight();
        button.setXPos(x + this.width - 64 - 42);
        button.setYPos(y + this.height - 64 - 10);
    }
}
