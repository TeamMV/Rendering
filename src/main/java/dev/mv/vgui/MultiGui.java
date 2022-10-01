package dev.mv.vgui;

import dev.mv.vgui.elements.page.Page;
import lombok.Getter;

import java.util.Map;

public class MultiGui {
    @Getter
    private Map<String, Page> pages;
    private Page open;
    @Getter
    private String name;

    public MultiGui(String name, Page... pages) {
        for(Page page : pages){
            this.pages.put(page.getName(), page);
        }
        this.name = name;
    }

    public GUI getGuiFromOpenPage() {
        return open.getGui();
    }

    public GUI getGui(String name) {
        return getPages().get(name).getGui();
    }

    public void gotoPage(String name) {
        open = getPages().get(name);
    }
}
