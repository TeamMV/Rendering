package dev.mv.vrender.window.screen;

import dev.mv.vgui.MultiGui;
import dev.mv.vgui.elements.page.Page;
import dev.mv.vrender.render.Draw;
import dev.mv.vrender.window.Window;
import lombok.Getter;
import lombok.Setter;

public abstract class Screen {
    private int lastMods = -1;
    private int lastButton = -1;
    private int lastAction = 0;
    private int lastKey = 256;
    private boolean mouseDown = false, keyHeld = false;
    private int mouseX = 0, mouseY = 0;

    @Getter
    @Setter
    private LayoutInflater inflater;

    public abstract void onCreate(LayoutInflater inflater);

    public abstract void renderBefore(Window w);

    public abstract void renderAfter(Window w);

    private void renderGUI(Window w) {
        w.draw.mode(Draw.CAMERA_STATIC);
        for (MultiGui gui : inflater.openGuis) {
            gui.getPages().values().forEach(t -> {
                if (t.getGui().isOpen()) {
                    t.getGui().render(w);
                }
            });
        }
        w.draw.mode(Draw.CAMERA_DYNAMIC);
    }

    public void render(Window w) {
        renderBefore(w);
        renderGUI(w);
        renderAfter(w);
    }

    public void onMouseAction(int button, int action, int mods) {
        lastButton = button;
        if (action == 1) {
            mouseDown = true;
            onMouseDown(button, mods);
        } else if (action == 0) {
            mouseDown = false;
            onMouseUp(button, mods);
            lastButton = -1;
        }

        if (action == 1) {
            for (MultiGui pageSystem : inflater.openGuis) {
                pageSystem.getPages().values().forEach(t -> {
                    if (t.getGui().isOpen()) {
                        t.getGui().click(mouseX, mouseY, button, mods);
                    }
                });
            }
            onMouseClick(mouseX, mouseY, button, mods);
        } else if (action == 0) {
            for (MultiGui pageSystem : inflater.openGuis) {
                pageSystem.getPages().values().forEach(t -> {
                    if (t.getGui().isOpen()) {
                        t.getGui().release(mouseX, mouseY, mods);
                    }
                });
            }
            onMouseRelease(mouseX, mouseY, button, mods);
        }

        lastAction = action;
        lastMods = mods;
    }

    public void onScroll(int x, int y) {
        for (MultiGui pageSystem : inflater.openGuis) {
            pageSystem.getPages().values().forEach(t -> {
                if (t.getGui().isOpen()) {
                    t.getGui().scroll(x, y);
                }
            });
        }
        onMouseScroll(x, y);
    }

    public void onMouseMove(int x, int y) {
        mouseX = x;
        mouseY = y;

        if (mouseDown) {
            for (MultiGui pageSystem : inflater.openGuis) {
                pageSystem.getPages().values().forEach(t -> {
                    if (t.getGui().isOpen()) {
                        t.getGui().drag(x, y, lastButton, lastMods);
                    }
                });
            }
            onMouseDrag(x, y, lastButton, lastMods);
        }
    }

    private void resizeGUI(int width, int height) {
        for (MultiGui pageSystem : inflater.openGuis) {
            pageSystem.getPages().values().forEach(t -> {
                if (t.getGui().isOpen()) {
                    t.getGui().resize(width, height);
                }
            });
        }
    }

    protected void guiKeyTyped(char c) {
        for (MultiGui pageSystem : inflater.openGuis) {
            pageSystem.getPages().values().forEach(t -> {
                if (t.getGui().isOpen()) {
                    t.getGui().keyTyped(c);
                }
            });
        }
    }

    public void typed(char c) {
        keyTyped(c);
        guiKeyTyped(c);
    }

    public void closeAllGUIs() {
        for (MultiGui pageSystem : inflater.openGuis) {
            pageSystem.getPages().values().forEach(t -> {
                t.getGui().close();
            });
        }
    }

    public abstract void resize(int width, int height);

    public abstract void update(Window w);

    public abstract void keyDown(int c);

    public abstract void keyUp(int c);

    public abstract void keyTyped(char c);

    public abstract void onMouseDown(int button, int mods);

    public abstract void onMouseUp(int button, int mods);

    public abstract void onMouseDrag(int x, int y, int button, int mods);

    public abstract void onMouseClick(int x, int y, int button, int mods);

    public abstract void onMouseRelease(int x, int y, int button, int mods);

    public abstract void onMouseScroll(int x, int y);
}
