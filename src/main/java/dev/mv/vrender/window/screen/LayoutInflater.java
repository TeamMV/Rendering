package dev.mv.vrender.window.screen;

import dev.mv.vgui.GUI;
import dev.mv.vgui.MultiGui;
import dev.mv.vgui.elements.page.Page;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LayoutInflater {

    protected List<MultiGui> pageSystems;
    protected List<MultiGui> openGuis;

    private List<GUI> toClose;
    private MultiGui defaultSystem;
    private Screen screen;

    private Window window;

    private Consumer<MultiGui> open = (t) -> {
        GUI gui = t.getGuiFromOpenPage();
        gui.open();
        openGuis.add(t);
        gui.setInflater(this);
        t.getPages().values().forEach(page -> page.getGui().resize(window.getWidth(), window.getHeight()));
    };
    private Consumer<MultiGui> close = (t) -> {
        GUI gui = t.getGuiFromOpenPage();
        gui.close();
        toClose.add(gui);
    };

    public LayoutInflater(Screen screen, Window window) {
        this.openGuis = new ArrayList<>();
        this.pageSystems = new ArrayList<>();
        this.toClose = new ArrayList<>();
        this.screen = screen;
        this.window = window;
    }

    public LayoutInflater inflate(LayoutBundle layout) {
        for (MultiGui pageSystem : layout.getGuis()) {
            for (Page page : pageSystem.getPages().values()) {
                page.getGui().close();
                page.getGui().resize(window.getWidth(), window.getHeight());
            }
            pageSystems.add(pageSystem);
        }
        if(layout.getDefaultGUI() != null){
            open.accept(layout.getDefaultGUI());
        }
        defaultSystem = layout.getDefaultGUI();

        return this;
    }

    public LayoutInflater swap(String name) {
        List<MultiGui> toClose = new ArrayList<>();
        if (openGuis.size() > 0) {
            for (MultiGui pageSystem : openGuis) {
                toClose.add(pageSystem);
            }
        }
        for (MultiGui pageSystem : toClose) {
            close.accept(pageSystem);
        }
        toClose.clear();
        for (MultiGui pageSystem : pageSystems) {
            if (pageSystem.getName().equals(name)) {
                open.accept(pageSystem);
                break;
            }
        }

        return this;
    }

    public LayoutInflater open(String name) {
        for (MultiGui pageSystem : pageSystems) {
            if (pageSystem.getName().equals(name)) {
                open.accept(pageSystem);
                break;
            }
        }

        return this;
    }

    public LayoutInflater close(String name) {
        MultiGui toClose = null;
        for (MultiGui pageSystem : openGuis) {
            if (pageSystem.getName().equals(name)) {
                toClose = pageSystem;
            }
        }

        if (toClose != null) {
            close.accept(toClose);
        }

        return this;
    }

    public LayoutInflater closeAll() {
        List<MultiGui> toClose = new ArrayList<>();
        for (MultiGui pageSystem : openGuis) {
            toClose.add(pageSystem);
        }

        for (MultiGui pageSystem : toClose) {
            close.accept(pageSystem);
        }

        return this;
    }

    public LayoutInflater gotoPage(String guiName, String name) {
        openGuis.forEach(t -> {
            if (t.getName().equals(guiName)) {
                t.gotoPage(name);
                t.getPage(name).resize(window.getWidth(), window.getHeight());
                t.getPage(name).switchForCallback(t.getOpenPage(), t.getPages().get(name));
            }
        });

        return this;
    }

    public MultiGui getDefaultGui() {
        return defaultSystem;
    }

    public List<MultiGui> getOpenGuis() {
        return openGuis;
    }

    public LayoutInflater resize(int width, int height) {
        pageSystems.forEach((system) -> system.getGuiFromOpenPage().resize(width, height));

        return this;
    }

    public MultiGui getGui(String name) {
        for (MultiGui pageSystem : pageSystems) {
            if (pageSystem.getName().equals(name)) {
                return pageSystem;
            }
        }

        return null;
    }

    protected void canClose() {
        for(GUI gui : toClose) {
            gui.close();
        }

        toClose.clear();
    }
}
