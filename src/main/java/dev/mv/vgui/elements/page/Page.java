package dev.mv.vgui.elements.page;

import dev.mv.vgui.GUI;
import dev.mv.vrender.utils.Consumable;
import dev.mv.vrender.utils.Loopable;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class Page implements Consumable, Loopable {

    @Getter
    private GUI gui;
    private String name;

    public Page(String name) {
        this.name = name;
    }

    public Page(String name, GUI gui) {
        this.name = name;
        this.gui = gui;
    }

    public Page setGui(GUI gui) {
        this.gui = gui;

        return this;
    }

    @Override
    public void OnConsume() {
        getGui().open();
    }

    @Override
    public void OnExcrete() {
        getGui().close();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void loop(Window w) {
        gui.render(w);
    }

    @Override
    public void tick(Window w) {
        //not important here...
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Page page) {
            return this.getName().equals(page.name);
        }

        return false;
    }
}
