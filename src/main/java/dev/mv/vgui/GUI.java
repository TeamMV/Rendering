package dev.mv.vgui;

import dev.mv.vgui.callbacks.*;
import dev.mv.vgui.elements.GUITabList;
import dev.mv.vgui.elements.Scrollable;
import dev.mv.vgui.elements.page.Page;
import dev.mv.vgui.elements.window.GUIWindow;
import dev.mv.vgui.elements.window.layout.HorizontalGUILayout;
import dev.mv.vgui.elements.window.layout.VerticalGUILayout;
import dev.mv.vrender.window.Window;
import dev.mv.vrender.window.screen.LayoutInflater;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GUI {
    @Getter
    private final String name;
    private final List<GUIElement> elements;
    private final List<GUIElement> toAdd = new ArrayList<GUIElement>();
    private final List<GUIElement> toRemove = new ArrayList<GUIElement>();
    private boolean open;
    private boolean used = false;

    private GUIClickCallback clickCallback = null;
    private GUICloseCallback closeCallback = null;
    private GUIMouseMoveCallback mouseMoveCallback = null;
    private GUIOpenCallback openCallback = null;
    private GUISwitchPageCallback switchPageCallback = null;
    private GUIResizeCallback resizeCallback = null;
    private int lastMx = 0, lastMy = 0;

    @Getter
    private GUIWindow guiWindow = null;

    @Getter
    @Setter
    private LayoutInflater inflater;

    public GUI(String name) {
        this(name, new GUIElement[] {});
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
        if (guiWindow != null) {
            guiWindow.render(w);
        }
        for (GUIElement element : elements) {
            if (element.isVisible()) {
                element.render(w);
            }
        }
        used = false;

        if (mouseMoveCallback != null) {
            int mx = w.input.mousePosition().x;
            int my = w.input.mousePosition().y;

            if (mx != lastMx || my != lastMy) {
                mouseMoveCallback.invoke(this, mx, my);
                lastMx = mx;
                lastMy = my;
            }
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void switchForCallback(Page from, Page to) {
        if (switchPageCallback != null) {
            switchPageCallback.invoke(this, from, to);
        }
    }

    public void open() {
        open = true;
        if (openCallback != null) {
            openCallback.invoke(this);
        }
        //resize(MainGameData.SCREEN_SIZE.getWidth(), MainGameData.SCREEN_SIZE.getHeight());
    }

    public void close() {
        elements.forEach(GUIElement::reset);
        open = false;
        if (closeCallback != null) {
            closeCallback.invoke(this);
        }
    }

    public boolean click(int x, int y, int button, int mods) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        if (guiWindow != null) guiWindow.click(x, y, button, mods);
        for (GUIElement element : elements) {
            if (element instanceof Clickable) {
                ((Clickable) element).click(x, y, button, mods);
                ret = true;
            }
        }
        used = false;
        update();
        if (clickCallback != null) {
            clickCallback.invoke(this, x, y, button, mods);
        }
        return ret;
    }

    public boolean release(int x, int y, int mods) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Clickable) {
                ((Clickable) element).release(x, y, mods);
                ret = true;
            }
        }
        used = false;
        update();
        return ret;
    }

    public boolean drag(int x, int y, int button, int mods) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Draggable) {
                ((Draggable) element).drag(x, y, button, mods);
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

    public boolean scroll(int x, int y) {
        if (!open) return false;
        boolean ret = false;
        used = true;
        for (GUIElement element : elements) {
            if (element instanceof Scrollable) {
                ((Scrollable) element).scroll(x, y);
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

    public void setWindow(GUIWindow window) {
        guiWindow = window;
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
        if (guiWindow != null) {
            guiWindow.resize(width, height);
        }
        for (GUIElement element : elements) {
            if (!(element instanceof GUITabList)) {
                element.resize(width, height);
            }
        }
        for (GUIElement element : elements) {
            if (element instanceof GUITabList) {
                element.resize(width, height);
            }
        }

        if (resizeCallback != null) {
            resizeCallback.invoke(this, width, height);
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

    public <T> T findElementById(String id, Class<T> destType) {
        if (id == null) return null;
        for (GUIElement e : elements) {
            if (e instanceof VerticalGUILayout) {
                T element = ((VerticalGUILayout) e).findElementById(id, destType);
                if (element != null) {
                    return element;
                }
            }
            if (e instanceof HorizontalGUILayout) {
                T element = ((HorizontalGUILayout) e).findElementById(id, destType);
                if (element != null) {
                    return element;
                }
            }
            if (e instanceof GUITabList) {
                T element = ((GUITabList) e).findElementById(id, destType);
                if (element != null) {
                    return element;
                }
            }
            if (e.getClass().equals(destType)) {
                if (e.getId() == null) continue;
                if (e.getId().equals(id)) {
                    return (T) e;
                }
            }
        }
        return null;
    }

    public <T> T findElementById(String id) {
        if (id == null) return null;
        for (GUIElement e : elements) {
            if (e instanceof VerticalGUILayout) {
                T element = ((VerticalGUILayout) e).<T>findElementById(id);
                if (element != null) {
                    return element;
                }
            }
            if (e instanceof HorizontalGUILayout) {
                T element = ((HorizontalGUILayout) e).<T>findElementById(id);
                if (element != null) {
                    return element;
                }
            }
            if (e instanceof GUITabList) {
                T element = ((GUITabList) e).<T>findElementById(id);
                if (element != null) {
                    return element;
                }
            }
            if (e.getId() == null) continue;
            if (e.getId().equals(id)) {
                return (T) e;
            }
        }
        return null;
    }

    public void created() {
        for (GUIElement e : elements) {
            e.created();
        }
    }

    public void setCallback(GUICallback callback) {
        if (callback instanceof GUIClickCallback c) {
            clickCallback = c;
        }
        if (callback instanceof GUICloseCallback c) {
            closeCallback = c;
        }
        if (callback instanceof  GUIMouseMoveCallback c) {
            mouseMoveCallback = c;
        }
        if (callback instanceof GUIOpenCallback c) {
            openCallback = c;
        }
        if (closeCallback instanceof GUIResizeCallback c) {
            resizeCallback = c;
        }
        if (callback instanceof GUISwitchPageCallback c) {
            switchPageCallback = c;
        }
    }

    @Override
    public boolean equals(Object gui) {
        if (gui instanceof GUI) {
            if (this.getName().equals(((GUI) gui).getName())) {
                return true;
            }
        }

        return false;
    }
}
