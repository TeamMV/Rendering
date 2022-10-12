package dev.mv.vgui.callbacks;

import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.page.Page;

public interface GUISwitchPageCallback extends GUICallback {
    void invoke(GUI gui, Page from, Page to);
}
