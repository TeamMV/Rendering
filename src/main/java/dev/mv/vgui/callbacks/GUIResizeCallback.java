package dev.mv.vgui.callbacks;

import dev.mv.vgui.GUI;

public interface GUIResizeCallback extends GUICallback{
    void invoke(GUI gui, int width, int height);
}
