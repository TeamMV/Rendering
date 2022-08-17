package dev.mv.vgui.elements.listeners;

import dev.mv.vgui.GUIElement;

@FunctionalInterface
public interface ClickListener {
    void clicked(GUIElement e);
}
