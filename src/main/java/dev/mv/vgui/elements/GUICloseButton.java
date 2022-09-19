package dev.mv.vgui.elements;

import dev.mv.vgui.GUI;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.utils.VariablePosition;

public class GUICloseButton extends GUIIconButton {
    public GUICloseButton(VariablePosition position, GUI gui, GUI shouldOpenOnClose) {
        super(position, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (shouldOpenOnClose == null) return;
            shouldOpenOnClose.open();
        });
    }

    public GUICloseButton(int x, int y, int width, int height, GUI gui, GUI shouldOpenOnClose) {
        super(x, y, width, height, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (shouldOpenOnClose == null) return;
            shouldOpenOnClose.open();
        });
    }

    public GUICloseButton(VariablePosition position, GUI gui, Runnable runOnClose) {
        super(position, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (runOnClose == null) return;
            runOnClose.run();
        });
    }

    public GUICloseButton(int x, int y, int width, int height, GUI gui, Runnable runOnClose) {
        super(x, y, width, height, DefaultTextures.BUTTON_CROSS, true, e -> {
            gui.close();
            if (runOnClose == null) return;
            runOnClose.run();
        });
    }

}
