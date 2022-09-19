package dev.mv.vgui.elements;

import dev.mv.vgui.GUIElement;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;

import java.util.function.Consumer;

public class GUIStatusBar extends GUIElement {

    private int width, height;
    @Getter
    private int maxValue, currentValue;
    private int[] color;
    private Consumer<GUIStatusBar> createdTask = null;

    public GUIStatusBar(VariablePosition position, int maxValue, int[] color) {
        this(position.getX(), position.getY(), position.getWidth(), position.getHeight(), maxValue, color);
        positionCalculator = position;
    }

    public GUIStatusBar(int x, int y, int width, int height, int maxValue, int[] color) {
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.color = color;
    }

    public void set(int value) {
        currentValue = value;
        currentValue -= Math.max((currentValue - maxValue), 0);
    }

    public void setLimit(int value) {
        maxValue = value;
    }

    public void increment(int amount) {
        currentValue += amount;
        currentValue -= Math.max((currentValue - maxValue), 0);
    }

    public void decrement(int amount) {
        currentValue -= amount;
        currentValue += Math.max(currentValue * -1, 0);
    }

    @Override
    public void render(Window w) {

        int barProgressWidth = (int) ((width - 6) * ((float) currentValue / (float) maxValue));

        w.draw.color(255, 255, 255, 255);
        w.draw.rectangle(xPos, yPos, width, height);

        w.draw.color(color[0], color[1], color[2], color[3]);
        w.draw.rectangle(xPos + 3, yPos + 3, barProgressWidth, height - 6);

        w.draw.color(0, 0, 0, 50);
        w.draw.rectangle(xPos + 3, yPos + 3, barProgressWidth, 2);
        w.draw.rectangle(xPos + 3, yPos + height - 5, barProgressWidth, 2);
        w.draw.rectangle(xPos + 3, yPos + 5, 2, height - 10);
        w.draw.rectangle(xPos + barProgressWidth + 1, yPos + 5, 2, height - 10);

        w.draw.color(40, 40, 40, 255);
        w.draw.rectangle(xPos + barProgressWidth + 3, yPos + 3, width - barProgressWidth - 6, height - 6);

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

    public void onCreate(Consumer<GUIStatusBar> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }

}
