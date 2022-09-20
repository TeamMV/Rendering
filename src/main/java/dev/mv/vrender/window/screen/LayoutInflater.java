package dev.mv.vrender.window.screen;

import dev.mv.vgui.GUI;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LayoutInflater {
    protected Map<String, GUI> guis;
    protected List<GUI> openGuis;
    private GUI defaultGui;
    private Screen screen;

    private Window window;

    private Consumer<GUI> open = (t) -> {
        t.open();
        openGuis.add(t);
        t.setInflater(this);
        t.resize(window.getWidth(), window.getHeight());
    };
    private Consumer<GUI> close = (t) -> {
        t.close();
        openGuis.remove(t);
    };

    public LayoutInflater(Screen screen, Window window) {
        this.guis = new HashMap<>();
        this.openGuis = new ArrayList<>();
        this.screen = screen;
        this.window = window;
    }

    public LayoutInflater inflate(LayoutBundle layout) {
        for (GUI gui : layout.getGuis()) {
            this.guis.put(gui.getName(), gui);
            gui.close();
        }
        this.guis.put(layout.getDefaultGUI().getName(), layout.getDefaultGUI());
        open.accept(layout.getDefaultGUI());
        defaultGui = layout.getDefaultGUI();

        return this;
    }

    public LayoutInflater swap(String name) {
        List<GUI> toClose = new ArrayList<>();
        if (openGuis.size() > 0) {
            for (GUI gui : openGuis) {
                toClose.add(gui);
            }
        }
        for (GUI gui : toClose) {
            close.accept(gui);
        }
        toClose.clear();
        open.accept(guis.get(name));

        return this;
    }

    public LayoutInflater open(String name) {
        open.accept(guis.get(name));

        return this;
    }

    public LayoutInflater close(String name) {
        GUI toClose = null;
        for (GUI gui : openGuis) {
            if (gui.getName().equals(name)) {
                toClose = gui;
            }
        }

        if (toClose != null) {
            close.accept(toClose);
        }

        return this;
    }

    public LayoutInflater closeAll() {
        List<GUI> toClose = new ArrayList<>();
        for (GUI gui : openGuis) {
            toClose.add(gui);
        }

        for (GUI gui : toClose) {
            close.accept(gui);
        }

        return this;
    }

    public GUI getDefaultGui() {
        return defaultGui;
    }

    public LayoutInflater resize(int width, int height) {
        guis.forEach((name, gui) -> gui.resize(width, height));

        return this;
    }

    public GUI[] getGuis() {
        return guis.values().toArray(new GUI[] {});
    }

    public GUI get(String name) {
        return guis.get(name);
    }
}
