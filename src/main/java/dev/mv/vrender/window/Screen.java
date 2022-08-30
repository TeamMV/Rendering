package dev.mv.vrender.window;

import dev.mv.vgui.GUI;
import dev.mv.vrender.render.Draw;

import java.util.ArrayList;

public abstract class Screen {

    protected ArrayList<GUI> guis = new ArrayList<GUI>();

    private int lastMods = -1;
    private int lastButton = -1;
    private int lastAction = 0;
    private int lastKey = 256;
    private boolean mouseDown = false, keyHeld = false;
    private int mouseX = 0, mouseY = 0;

    public abstract void render(Window w);

    public void loop(Window w) {

    }

    public void renderGUI(Window w) {
        w.draw.mode(Draw.CAMERA_STATIC);
        for (GUI gui : guis) {
            if (gui.isOpen()) {
                gui.render(w);
            }
        }
        w.draw.mode(Draw.CAMERA_DYNAMIC);
    }

    public void addGUI(GUI gui) {
        this.guis.add(gui);
    }

    public void removeGUI(GUI gui) {
        int i = guis.indexOf(gui);
        if (i == -1) return;
        guis.remove(i);
    }

    public void onMouseAction(int button, int action, int mods) {
        if (action == 1) {
            mouseDown = true;
            onMouseDown(button, mods);
        } else if (action == 0) {
            mouseDown = false;
            onMouseUp(button, mods);
            lastButton = -1;
        }

        if (lastButton != button || lastMods != mods) {
            if (action == 1) {
                for (GUI gui : guis) {
                    if (gui.isOpen()) {
                        gui.click(mouseX, mouseY, button, mods);
                    }
                }
                onMouseClick(mouseX, mouseY, button, mods);
            } else if (action == 0) {
                onMouseRelease(mouseX, mouseY, button, mods);
            }
        }

        lastAction = action;
        lastMods = mods;

    }

    public void onScroll(int x, int y) {
        for (GUI gui : guis) {
            if (gui.isOpen()) {
                gui.scroll(x, y);
            }
        }
        onMouseScroll(x, y);
    }

    public void onMouseMove(int x, int y) {
        mouseX = x;
        mouseY = y;

        if (mouseDown) {
            for (GUI gui : guis) {
                if (gui.isOpen()) {
                    gui.drag(x, y, lastButton, lastMods);
                }
            }
            onMouseDrag(x, y, lastButton, lastMods);
        }
    }

    protected void resizeGUI(int width, int height) {
        for (GUI gui : guis) {
            gui.resize(width, height);
        }
    }

    protected void guiKeyTyped(char c) {
        for (GUI gui : guis) {
            gui.keyTyped(c);
        }
    }

    public abstract void resize(int width, int height);

    public abstract void keyDown(int c);

    public abstract void keyUp(int c);

    public abstract void keyTyped(char c);

    public abstract void onMouseDown(int button, int mods);

    public abstract void onMouseUp(int button, int mods);

    public abstract void onMouseDrag(int x, int y, int button, int mods);

    public abstract void onMouseClick(int x, int y, int button, int mods);

    public abstract void onMouseRelease(int x, int y, int button, int mods);

    public abstract void onMouseScroll(int x, int y);

    public abstract void onActivation(Window w);
}
