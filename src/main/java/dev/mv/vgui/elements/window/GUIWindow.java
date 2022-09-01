package dev.mv.vgui.elements.window;

import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class GUIWindow {

    @Getter
    private int x, y, width, height;

    public GUIWindow(VariablePosition position){
        this(position.getX(), position.getY(), position.getWidth(), position.getHeight());
    }

    public GUIWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Window w){
        w.draw.color(255, 255, 255, 255);
        w.draw.rectangle(x, y + 50, width, height - 100);
        w.draw.rectangle(x + 50, y, width - 100, height);

        w.draw.triangle(x, y + height - 50, x + 50, y + height - 50, x + 50, y + height);
        w.draw.triangle(x + width - 50, y + height, x + width - 50, y + height - 50, x + width, y + height - 50);
        w.draw.triangle(x + width - 50, y, x + width - 50, y + 50, x + width, y + 50);
        w.draw.triangle(x, y + 50, x + 50, y + 50, x + 50, y);

        w.draw.color(40, 40, 40, 255);
        w.draw.rectangle(x + 5, y + 50, width - 10, height - 100);
        w.draw.rectangle(x + 50, y + 5, width - 100, height - 10);

        w.draw.triangle(x + 5, y + height - 50, x + 50, y + height - 50, x + 50, y + height - 5);
        w.draw.triangle(x + width - 50, y + height - 5, x + width - 50, y + height - 50, x + width - 5, y + height - 50);
        w.draw.triangle(x + width - 5, y + 50, x + width - 50, y + 50, x + width - 50, y + 5);
        w.draw.triangle(x + 5, y + 50, x + 50, y + 50, x + 50, y + 5);
    }
}
