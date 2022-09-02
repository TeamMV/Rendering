package dev.mv.vgui.elements;

import dev.mv.vgui.GUI;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.utils.VariablePosition;

public class GUICloseButton extends GUIIconButton {
    public GUICloseButton(VariablePosition position, GUI gui) {
        this(position.getX(), position.getY(), position.getWidth(), position.getHeight(), gui);
    }

    public GUICloseButton(int x, int y, int width, int height, GUI gui) {
        super(x, y, width, height, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
        });
    }
}
