package dev.mv.vrender.window.screen;

import dev.mv.vgui.GUI;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LayoutBundle {
    @Getter
    private List<GUI> guis;
    @Getter
    private GUI defaultGUI;

    public LayoutBundle(GUI defaultGUI, GUI... guis){
        this.guis = new ArrayList<>();
        this.defaultGUI = defaultGUI;
        for(GUI gui : guis){
            this.guis.add(gui);
        }
    }
}
