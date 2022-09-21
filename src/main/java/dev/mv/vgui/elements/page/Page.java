package dev.mv.vgui.elements.page;

import dev.mv.vgui.GUI;
import dev.mv.vrender.utils.Consumeable;
import dev.mv.vrender.window.screen.Screen;

import java.util.HashMap;
import java.util.Map;

public class Page implements Consumeable {

    private Map<String, GUI> guis = new HashMap<>();
    private Screen screen;
    private String name;

    public Page(Screen screen, String name){
        this.screen = screen;
        this.name = name;
    }

    public Page addGui(GUI gui){
        guis.put(gui.getName(), gui);

        return this;
    }

    @Override
    public void OnConsume() {

    }

    @Override
    public String getName() {
        return name;
    }
}
