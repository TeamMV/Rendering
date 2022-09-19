package dev.mv.vgui;

import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;
import lombok.Setter;

public abstract class GUIElement {

    protected int xPos = -1, yPos = -1;
    @Setter @Getter
    protected String id;
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

    public void setXPos(int x) {
        xPos = x;
        if (positionCalculator == null) return;
        positionCalculator.setX(x);
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int y) {
        yPos = y;
        if (positionCalculator == null) return;
        positionCalculator.setY(y);
    }

    public void setPositionCalculator(VariablePosition positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public abstract void render(Window w);

    public abstract void resize(int width, int height);

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract void created();
}
