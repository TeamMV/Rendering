package dev.mv.vgui;

import dev.mv.vrender.window.Window;
import lombok.Getter;

public abstract class GUIElement {

    private boolean visible = true;
    protected int xPos = -1, yPos = -1;
    protected GUIPosition positionCalculator;

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

    public void setPositionCalculator(GUIPosition positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public abstract void render(Window w);

    public abstract void resize(int width, int height);

}
