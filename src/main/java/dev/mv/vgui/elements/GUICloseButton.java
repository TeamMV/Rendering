package dev.mv.vgui.elements;

import dev.mv.vgui.GUI;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.utils.VariablePosition;

public class GUICloseButton extends GUIIconButton {
    public GUICloseButton(VariablePosition position, GUI gui, Runnable onClose) {
        super(position, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (onClose == null) return;
            onClose.run();
        });
    }

    public GUICloseButton(int x, int y, int width, int height, GUI gui, Runnable onClose) {
        super(x, y, width, height, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (onClose == null) return;
            onClose.run();
        });
    }
}
