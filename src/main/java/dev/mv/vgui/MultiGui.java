package dev.mv.vgui;

import dev.mv.vgui.elements.page.Page;
import dev.mv.vrender.utils.ConsumableSystem;
import lombok.Getter;

public class MultiGui {
    @Getter
    private ConsumableSystem<Page> pages;
    @Getter
    private String name;

    public MultiGui(ConsumableSystem<Page> pages, String name) {
        this.pages = pages;
        this.name = name;
    }

    public GUI getGuiFromOpenPage() {
        return getPages().getConsumer().getConsumedConsumeable().getGui();
    }

    public GUI getGui(String name) {
        return getPages().get(name).getGui();
    }

    public void gotoPage(int index) {
        getPages().getConsumer().consume(index);
    }

    public void gotoPage(String name) {
        getPages().getConsumer().consume(name);
    }
}
