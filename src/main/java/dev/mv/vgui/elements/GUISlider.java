package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.Draggable;
import dev.mv.vgui.GUIElement;
import dev.mv.vrender.window.Window;

public class GUISlider extends GUIElement implements Clickable, Draggable {

    private int width;
    private final int height;
    private final int steps;
    private int selection;
    private final int selectorWidth = 30;
    private float selectorX;
    private boolean startDrag = false, removeStripes = false;
    private boolean renderExtension = true;

    public GUISlider(int x, int y, int width, int steps, int defaultSelection) {
        xPos = x;
        yPos = y;
        height = 10;
        this.width = width;
        if (steps > 0) {
            this.steps = steps;
        } else {
            this.steps = 1;
        }

        if (defaultSelection <= this.steps && defaultSelection > 0) {
            selection = defaultSelection;
        } else {
            selection = 1;
        }
        selectorX = (xPos + (((selection) * (width + 6)) / (this.steps + 1))) - 6 - (selectorWidth / 2);
    }

    public void setSelection(int selection) {
        if (selection > steps) {
            this.selection = steps;
            selectorX = (xPos + (((this.selection) * (width + 6)) / (steps + 1))) - 6 - (selectorWidth / 2);
            return;
        }
        if (selection < 1) {
            this.selection = 1;
            selectorX = (xPos + (((this.selection) * (width + 6)) / (steps + 1))) - 6 - (selectorWidth / 2);
            return;
        }
        this.selection = selection;
        selectorX = (xPos + (((this.selection) * (width + 6)) / (steps + 1))) - 6 - (selectorWidth / 2);
    }

    public boolean removingStripes() {
        return removeStripes;
    }

    public boolean renderingExtension() {
        return renderExtension;
    }

    public void removeStripes(boolean removeStripes) {
        this.removeStripes = removeStripes;
    }

    public void renderExtension(boolean renderExtension) {
        this.renderExtension = renderExtension;
    }

    public int getValue(){
        return selection;
    }

    @Override
    public void render(Window w) {

        w.draw.color(255, 255, 255, 255);

        if (renderExtension) {

            w.draw.color(255, 255, 255, 255);
            w.draw.rectangle(xPos, yPos + 3, width - 5, height - 3);
            w.draw.color(40, 40, 40, 255);
            w.draw.rectangle(xPos + 2, yPos + 5, width - 5 - 4, height - 7);
        }

        if (!removeStripes) {
            w.draw.color(255, 255, 255, 255);
            for (int i = 0; i < steps; i++) {
                if (i + 1 == selection) continue;
                w.draw.rectangle((xPos + (((i + 1) * (width + 6)) / (steps + 1))) - 6, yPos, 2, height + 3);
            }
        }

        w.draw.color(255, 255, 255, 255);
        w.draw.rectangle((int)selectorX - 2, yPos - 5 - (selectorWidth / 4), selectorWidth + 4, selectorWidth + 4);
        w.draw.color(40, 40, 40, 255);
        w.draw.rectangle((int)selectorX, yPos - 3 - (selectorWidth / 4), selectorWidth, selectorWidth);
    }

    @Override
    public void resize(int width, int height) {
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        this.width = positionCalculator.getWidth();
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if (button != 0) return;
        if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos+ selectorWidth) {
            startDrag = true;
            int selection = (int) Math.ceil((((float) x - (float) xPos) / ((float) width / ((steps * 2.0f) + 2.0f)) - 1.0f) / 2.0f);
            if (this.selection != selection) {
                setSelection(selection);
            }
        }
    }

    @Override
    public void release(int x, int y, int mods) {
        startDrag = false;
    }

    @Override
    public void drag(int x, int y, int button, int mods) {
        if (startDrag) {
            if (!(button == 0)) {
                startDrag = false;
                System.out.println(button);
                return;
            }
            int selection = (int) Math.ceil((((float) x - (float) xPos) / ((float) width / ((steps * 2.0f) + 2.0f)) - 1.0f) / 2.0f);
            if (this.selection != selection) {
                setSelection(selection);
            }
        }
    }
}