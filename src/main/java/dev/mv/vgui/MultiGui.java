package dev.mv.vgui;

import dev.mv.vgui.elements.page.Page;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

public class MultiGui {
    @Getter
    private Map<String, Page> pages;
    private Page open;
    @Getter
    private String name;

    public MultiGui(String name, Page... pages) {
        this.pages = new HashMap<>();
        for(Page page : pages){
            this.pages.put(page.getName(), page);
        }
        this.name = name;
        this.open = pages[0];
    }

    public MultiGui(String name) {
        this.pages = new HashMap<>();
        this.name = name;
    }

    public void addPage(Page page) {
        if(this.open == null){
            this.open = page;
        }
        this.pages.put(page.getName(), page);
    }

    public GUI getGuiFromOpenPage() {
        return open.getGui();
    }

    public GUI getPage(String name) {
        return getPages().get(name).getGui();
    }

    public void gotoPage(String name) {
        open.getGui().close();
        open = getPages().get(name);
        open.getGui().open();
    }
}
