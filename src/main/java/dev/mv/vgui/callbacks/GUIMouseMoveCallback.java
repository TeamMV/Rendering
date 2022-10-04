package dev.mv.vgui.callbacks;

import dev.mv.vgui.GUI;

public interface GUIMouseMoveCallback extends GUICallback{
    void invoke(GUI gui, int x, int y);
}
