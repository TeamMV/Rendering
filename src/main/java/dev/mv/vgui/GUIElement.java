package dev.mv.vgui;

import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

public abstract class GUIElement {

    protected int xPos = -1, yPos = -1;
    protected VariablePosition positionCalculator;
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setPositionCalculator(VariablePosition positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public abstract void render(Window w);

    public abstract void resize(int width, int height);

}
