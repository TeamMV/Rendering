package dev.mv.vgui.callbacks;

import dev.mv.vgui.GUI;

public interface GUIClickCallback extends GUICallback {
    void invoke(GUI gui, int x, int y, int button, int mods);
}
