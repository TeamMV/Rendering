package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.Draggable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.Typeable;
import dev.mv.vrender.window.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GUITab extends AbstractGUITab {

    private int maxWidth, maxHeight;
    @Getter
    @Setter
    private String title;
    private List<GUIElement> items = new ArrayList<>();
    private List<int[]> positions = new ArrayList<>();

    public GUITab(String title) {
        this.title = title;
    }

    public void addItem(GUIElement e) {
        maxWidth = Math.max(maxWidth, e.getWidth());
        maxHeight = Math.max(maxHeight, e.getHeight());
        items.add(e);
        positions.add(new int[] {e.getXPos(), e.getYPos()});
    }

    @Override
    public void render(Window w, int x, int y) {
        int count = 0;
        for (GUIElement e : items) {
            e.setXPos(positions.get(count)[0] + x);
            e.setYPos(positions.get(count)[1] + y);
            e.render(w);

            count++;
        }
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
    }

    @Override
    public int getHeight() {
        int height = 0;

        for (GUIElement e : items) {
            height = Math.max(height, xPos + e.getYPos() + e.getHeight());
        }

        return height;
    }

    @Override
    public int getWidth() {
        int width = 0;

        for (GUIElement e : items) {
            width = Math.max(width, xPos + e.getXPos() + e.getWidth());
        }

        return width;
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        for (GUIElement e : items) {
            if (e instanceof Clickable instance) {
                instance.click(x, y, button, mods);
            }
        }
    }

    @Override
    public void release(int x, int y, int mods) {
        for (GUIElement e : items) {
            if (e instanceof Clickable instance) {
                instance.release(x, y, mods);
            }
        }
    }

    @Override
    public void drag(int x, int y, int button, int mods) {
        for (GUIElement e : items) {
            if (e instanceof Draggable instance) {
                instance.drag(x, y, button, mods);
            }
        }
    }

    @Override
    public void keyTyped(char c) {
        for (GUIElement e : items) {
            if (e instanceof Typeable instance) {
                instance.keyTyped(c);
            }
        }
    }

    @Override
    public boolean isSelected() {
        for (GUIElement e : items) {
            if (e instanceof Typeable instance) {
                return instance.isSelected();
            }
        }

        return false;
    }

    @Override
    public void scroll(int x, int y) {
        for (GUIElement e : items) {
            if (e instanceof Scrollable instance) {
                instance.scroll(x, y);
            }
        }
    }
}
