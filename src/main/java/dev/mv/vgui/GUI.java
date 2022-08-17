package dev.mv.vgui;

import dev.mv.vrender.window.Window;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GUI {
    private boolean open;
    @Getter
    private final String name;

    private final List<GUIElement> elements;

    private boolean used = false;
    private final List<GUIElement> toAdd = new ArrayList<GUIElement>();
    private final List<GUIElement> toRemove = new ArrayList<GUIElement>();


    public GUI(String name) {
        this(name, new GUIElement[]{});
    }

    public GUI(String name, GUIElement... elements) {
        this.name = name;
        open = false;
        this.elements = new ArrayList<GUIElement>();
        for (GUIElement element : elements) {
            this.elements.add(element);
        }
    }

    public void render(Window w) {
        if (!open) return;
        used = true;
        for (GUIElement element : elements) {
            if (element.isVisible()) {
                element.render(w);
            }
        }
        used = false;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
        //resize(MainGameData.SCREEN_SIZE.getWidth(), MainGameData.SCREEN_SIZE.getHeight());
    }

    public void close() {
        open = false;
    }

    public boolean click(int x, int y, int button) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Clickable) {
                ((Clickable) element).click(x, y, button);
                ret = true;
            }
        }
        used = false;
        update();
        return ret;
    }

    public boolean drag(int x, int y) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Draggable) {
                ((Draggable) element).drag(x, y);
                ret = true;
            }
        }
        used = false;
        update();
        return ret;
    }

    public boolean keyTyped(char c) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Typeable) {
                ((Typeable) element).keyTyped(c);
                ret = true;
            }
        }
        used = false;
        update();
        return ret;
    }

    public void attachElement(GUIElement e) {
        if (used) {
            toAdd.add(e);
            return;
        }
        elements.add(e);
    }

    public void removeElement(GUIElement e) {
        if (used) {
            toRemove.add(e);
            return;
        }
        elements.remove(e);
    }

    public List<GUIElement> getElements() {
        return elements;
    }

    public boolean isAttached(GUIElement element) {
        return elements.contains(element);
    }

    public void update() {
        for (GUIElement element : toAdd) {
            elements.add(element);
        }
        toAdd.clear();

        for (GUIElement element : toRemove) {
            elements.remove(element);
        }
        toRemove.clear();
    }

    public void resize(int width, int height) {
        if (!open) return;
        for (GUIElement element : elements) {
            element.resize(width, height);
        }
    }

    public boolean isSelected() {
        for (GUIElement element : elements) {
            if (element instanceof Typeable) {
                if (((Typeable) element).isSelected()) return true;
            }
        }
        return false;
    }
}