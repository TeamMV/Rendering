package dev.mv.vgui.callbacks;

import dev.mv.vgui.GUI;

public interface GUICloseCallback extends GUICallback {
    //This is the even funnier interface GUICloseCallback

    void invoke(GUI gui);
}
