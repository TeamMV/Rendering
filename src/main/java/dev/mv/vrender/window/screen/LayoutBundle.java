package dev.mv.vrender.window.screen;

import dev.mv.vgui.MultiGui;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LayoutBundle {
    @Getter
    private List<MultiGui> guis;
    @Getter
    private MultiGui defaultGUI;

    public LayoutBundle(MultiGui defaultGUI, MultiGui... guis) {
        this.guis = new ArrayList<>();
        this.defaultGUI = defaultGUI;
        for (MultiGui gui : guis) {
            this.guis.add(gui);
        }
    }
}
