package dev.mv.vgui.elements.window.layout;

import dev.mv.vgui.*;
import dev.mv.vgui.elements.Scrollable;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VerticalGUILayout extends GUIElement implements Clickable, Typeable, Scrollable, Draggable {

    private List<GUIElement> items = new ArrayList<>();

    private int spacing = 0;

    private int maxWidth = 0;
    private Alignment currentAlign = Alignment.LEFT;

    private Window centreScreen = null;
    private GUI centreWindow = null;

    public VerticalGUILayout(VariablePosition position) {
        this(position.getX(), position.getY());
        positionCalculator = position;
    }

    public VerticalGUILayout(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public VerticalGUILayout setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    public VerticalGUILayout centerInWindow(GUI gui) {
        int winWidth = gui.getGuiWindow().getWidth();
        int winX = gui.getGuiWindow().getX();
        xPos = winX + (winWidth / 2) - (maxWidth / 2);

        centreWindow = gui;
        return this;
    }

    public VerticalGUILayout centerInScreen(Window w) {
        int winWidth = w.getWidth();
        xPos = (winWidth / 2) - (maxWidth / 2);

        centreScreen = w;
        return this;
    }

    public VerticalGUILayout alignContent(Alignment align) {
        currentAlign = align;

        return this;
    }

    public VerticalGUILayout appendItem(GUIElement element) {
        items.add(element);
        maxWidth = Math.max(maxWidth, element.getWidth());

        return this;
    }

    public VerticalGUILayout removeItem(GUIElement element) {
        items.remove(element);

        return this;
    }

    public void setXPos(int x) {
        xPos = x;
    }

    public void setYPos(int y) {
        yPos = y;
    }

    @Override
    public void render(Window w) {
        int yStart = yPos;
        int xStart = xPos;

        if (currentAlign == Alignment.LEFT) {
            for (int i = 0; i < items.size(); i++) {
                GUIElement e = items.get(i);
                e.setXPos(xStart);
                e.setYPos(yStart - e.getHeight());
                e.render(w);
                yStart -= e.getHeight();
                yStart -= spacing;
            }
        } else if (currentAlign == Alignment.CENTER) {
            for (int i = 0; i < items.size(); i++) {
                GUIElement e = items.get(i);
                e.setXPos(xStart + ((maxWidth / 2) - (e.getWidth() / 2)));
                e.setYPos(yStart - e.getHeight());
                e.render(w);
                yStart -= e.getHeight();
                yStart -= spacing;
            }
        } else if (currentAlign == Alignment.RIGHT) {
            for (int i = 0; i < items.size(); i++) {
                GUIElement e = items.get(i);
                e.setXPos(xStart + (maxWidth - e.getWidth()));
                e.setYPos(yStart - e.getHeight());
                e.render(w);
                yStart -= e.getHeight();
                yStart -= spacing;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator != null) {
            positionCalculator.resize(width, height);

            xPos = positionCalculator.getX();
            yPos = positionCalculator.getY();
        }
        if (centreScreen != null) {
            centerInScreen(centreScreen);
        }
        if (centreWindow != null) {
            centerInWindow(centreWindow);
        }
    }

    @Override
    public int getHeight() {
        int res = 0;
        for (GUIElement e : items) {
            res += e.getHeight();
            res += spacing;
        }
        return res;
    }

    @Override
    public int getWidth() {
        int res = 0;
        for (GUIElement e : items) {
            res += e.getWidth();
            res += spacing;
        }
        return res;
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

    public enum Alignment {
        LEFT,
        CENTER,
        RIGHT
    }

    private Consumer<VerticalGUILayout> createdTask = null;

    public void onCreate(Consumer<VerticalGUILayout> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }

}
