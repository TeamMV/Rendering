package dev.mv.vrender.window.screen;

import dev.mv.vgui.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LayoutInflater {
    protected Map<String, GUI> guis;

    private GUI defaultGui;
    protected List<GUI> openGuis;
    private Screen screen;

    private Consumer<GUI> open = (t) -> {t.open(); openGuis.add(t);};
    private Consumer<GUI> close = (t) -> {t.close(); openGuis.remove(t);};

    public LayoutInflater(Screen screen){
        this.guis = new HashMap<>();
        this.openGuis = new ArrayList<>();
        this.screen = screen;
    }

    public LayoutInflater inflate(LayoutBundle layout){
        for (GUI gui : layout.getGuis()) {
            this.guis.put(gui.getName(), gui);
            gui.close();
        }
        this.guis.put(layout.getDefaultGUI().getName(), layout.getDefaultGUI());
        open.accept(layout.getDefaultGUI());
        defaultGui = layout.getDefaultGUI();

        return this;
    }

    public LayoutInflater swap(String name){
        if(openGuis.size() > 0) {
            for (GUI gui : openGuis) {
                close.accept(gui);
            }
        }
        open.accept(guis.get(name));

        return this;
    }

    public LayoutInflater open(String name){
        open.accept(guis.get(name));

        return this;
    }

    public LayoutInflater close(String name){
        for (GUI gui : openGuis){
            if(gui.getName().equals(name)){
                close.accept(gui);
                return this;
            }
        }

        return this;
    }

    public LayoutInflater closeAll(){
        for (GUI gui : openGuis) {
            close.accept(gui);
        }

        return this;
    }

    public GUI getDefaultGui(){
        return defaultGui;
    }

    public LayoutInflater resize(int width, int height){
        guis.forEach((name, gui) -> gui.resize(width, height));

        return this;
    }
}
