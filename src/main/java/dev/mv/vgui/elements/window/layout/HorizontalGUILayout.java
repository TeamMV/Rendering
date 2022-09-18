package dev.mv.vgui.elements.window.layout;

import dev.mv.vgui.*;
import dev.mv.vgui.elements.Scrollable;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HorizontalGUILayout extends GUIElement implements Clickable, Typeable, Scrollable, Draggable {

    private List<GUIElement> items = new ArrayList<>();

    private int spacing = 0;
    private float percent = 0f;
    private boolean spacingPercentage = false;

    private Alignment currentAlign = Alignment.TOP;
    private int maxWidth = 0;

    private Window centreScreen = null;
    private GUI centreWindow = null;

    public HorizontalGUILayout(VariablePosition position) {
        this(position.getX(), position.getY());
        positionCalculator = position;
    }

    public HorizontalGUILayout(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public HorizontalGUILayout setSpacing(int spacing, float percent) {
        spacingPercentage = true;
        this.spacing = spacing;
        this.percent = percent;
        return this;
    }

    public HorizontalGUILayout setSpacing(int spacing) {
        spacingPercentage = false;
        this.spacing = spacing;
        return this;
    }

    public HorizontalGUILayout appendItem(GUIElement element) {
        items.add(element);
        maxWidth = Math.max(maxWidth, element.getWidth());

        return this;
    }

    public HorizontalGUILayout removeItem(GUIElement element) {
        items.remove(element);

        return this;
    }

    public HorizontalGUILayout centerInWindow(GUI gui) {
        int winWidth = gui.getGuiWindow().getWidth();
        int winX = gui.getGuiWindow().getX();
        xPos = winX + (winWidth / 2) - (maxWidth / 2);

        centreWindow = gui;
        return this;
    }

    public HorizontalGUILayout centerInScreen(Window w) {
        int winWidth = w.getWidth();
        xPos = (winWidth / 2) - (maxWidth / 2);

        centreScreen = w;
        return this;
    }

    public HorizontalGUILayout alignContent(HorizontalGUILayout.Alignment align) {
        currentAlign = align;

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

        if (currentAlign == HorizontalGUILayout.Alignment.TOP) {
            for (int i = 0; i < items.size(); i++) {
                GUIElement e = items.get(i);
                e.setXPos(xStart);
                e.setYPos(yStart - e.getHeight());
                e.render(w);
                xStart += e.getWidth();
                xStart += spacing;
            }
        } else if (currentAlign == HorizontalGUILayout.Alignment.CENTER) {
            for (int i = 0; i < items.size(); i++) {
                GUIElement e = items.get(i);
                e.setXPos(xStart + ((maxWidth / 2) - (e.getWidth() / 2)));
                e.setYPos(yStart - e.getHeight());
                e.render(w);
                yStart -= e.getHeight();
                yStart -= spacing;
            }
        } else if (currentAlign == HorizontalGUILayout.Alignment.BOTTOM) {
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
        if (spacingPercentage) {
            spacing = Math.round(width * percent);
        }
        for (GUIElement element : items) {
            element.resize(width, height);
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
        TOP,
        CENTER,
        BOTTOM
    }

    private Consumer<HorizontalGUILayout> createdTask = null;

    public void onCreate(Consumer<HorizontalGUILayout> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }
}
