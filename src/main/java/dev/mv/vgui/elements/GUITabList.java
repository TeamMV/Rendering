package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.Draggable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.Typeable;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GUITabList extends GUIElement implements Clickable, Typeable, Scrollable, Draggable {
    private int width = 200, height = 200;
    private List<GUITab> tabs = new ArrayList<>();

    private SizeLayout layout = new SizeLayout();
    private BitmapFont font;

    private int selected;

    public GUITabList(VariablePosition position, int defaultSelection, BitmapFont font) {
        this(position.getX(), position.getY(), defaultSelection, font);
        positionCalculator = position;
    }

    public GUITabList(int x, int y, int defaultSelection, BitmapFont font) {
        xPos = x;
        yPos = y;
        this.font = font;
        selected = defaultSelection - 1;

        layout.set(font, "", 31);
    }

    public void addTab(GUITab tab) {
        tabs.add(tab);
        tab.setXPos(xPos);
        tab.setYPos(yPos);
        width = tabs.size() * 200;
        height = Math.max(height, tab.getHeight());
    }

    @Override
    public void render(Window w) {

        int wi = width + 30;

        w.draw.color(255, 255, 255, 255);
        w.draw.rectangle(xPos, yPos + 15, wi, height - 30);
        w.draw.rectangle(xPos + 15, yPos, wi - 30, height);

        w.draw.triangle(xPos, yPos + height - 15, xPos + 15, yPos + height - 15, xPos + 15, yPos + height);
        w.draw.triangle(xPos + wi - 15, yPos + height, xPos + wi - 15, yPos + height - 15, xPos + wi, yPos + height - 15);
        w.draw.triangle(xPos + wi - 15, yPos, xPos + wi - 15, yPos + 15, xPos + wi, yPos + 15);
        w.draw.triangle(xPos, yPos + 15, xPos + 15, yPos + 15, xPos + 15, yPos);

        w.draw.color(40, 40, 40, 255);
        w.draw.rectangle(xPos + 5, yPos + 15, wi - 10, height - 30);
        w.draw.rectangle(xPos + 15, yPos + 5, wi - 30, height - 10);

        w.draw.triangle(xPos + 5, yPos + height - 15, xPos + 15, yPos + height - 15, xPos + 15, yPos + height - 5);
        w.draw.triangle(xPos + wi - 15, yPos + height - 5, xPos + wi - 50, yPos + height - 15, xPos + wi - 5, yPos + height - 15);
        w.draw.triangle(xPos + wi - 5, yPos + 15, xPos + wi - 15, yPos + 15, xPos + wi - 15, yPos + 5);
        w.draw.triangle(xPos + 5, yPos + 15, xPos + 15, yPos + 15, xPos + 15, yPos + 5);

        for (int i = 0; i < tabs.size(); i++) {
            int xi = xPos + 200 * i + 15;

            w.draw.color(255, 255, 255, 255);
            if (selected == i) w.draw.color(186, 247, 32, 255);
            if (w.input.mouseInside(xi, yPos + height - 35 - 5, xi + 200, yPos + height - 5))
                w.draw.color(186, 247, 32, 255);
            w.draw.triangle(xi, yPos + height - 5, xi + 20, yPos + height - 5, xi + 20, yPos + height - 35 - 5);
            w.draw.rectangle(xi + 20, yPos + height - 35 - 5, 200 - 40, 35);
            w.draw.triangle(xi + 20 + 200 - 40, yPos + height - 35 - 5, xi + 200 - 20, yPos + height - 5, xi + 200, yPos + height - 5);

            w.draw.color(40, 40, 40, 255);
            w.draw.triangle(xi + 4, yPos + height - 2 - 5, xi + 20 + 2, yPos + height - 2 - 5, xi + 20 + 2, yPos + height - 35 + 2 - 5);
            w.draw.rectangle(xi + 2 + 20, yPos + 2 + height - 35 - 5, 200 - 40 - 4, 35 - 4);
            w.draw.triangle(xi - 2 + 200 - 20, yPos + height - 35 + 2 - 5, xi - 2 + 200 - 20, yPos + height - 2 - 5, xi + 200 - 4, yPos + height - 2 - 5);

            w.draw.color(255, 255, 255, 255);
            if (selected == i) w.draw.color(186, 247, 32, 255);
            if (w.input.mouseInside(xi, yPos + height - 35 - 5, xi + 200, yPos + height - 5))
                w.draw.color(186, 247, 32, 255);
            String title = tabs.get(i).getTitle();
            w.draw.text(xi + 100 - layout.getWidth(title) / 2, yPos + height - 31 - 4, 31, title, font);

            if (selected == i) tabs.get(i).render(w, xPos, yPos);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        this.width = positionCalculator.getWidth();
        this.height = positionCalculator.getHeight();
        tabs.forEach(tab -> tab.resize(width, height));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if (button != 10) {
            if (y <= yPos + height - 5 && y >= yPos + height - 35 - 5) {
                selected = Math.ceilDiv(x - xPos + 15, 200) - 1;
            }
        }

        int count = 0;

        for (GUITab tab : tabs) {
            if (selected != count++) continue;
            tab.click(x, y, button, mods);
        }
    }

    @Override
    public void release(int x, int y, int mods) {
        int count = 0;
        for (GUITab tab : tabs) {
            if (selected != count++) continue;
            tab.release(x, y, mods);
        }
    }

    @Override
    public void keyTyped(char c) {
        int count = 0;
        for (GUITab tab : tabs) {
            if (selected != count++) continue;
            tab.keyTyped(c);
        }
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void scroll(int x, int y) {
        int count = 0;
        for (GUITab tab : tabs) {
            if (selected != count++) continue;
            tab.scroll(x, y);
        }
    }

    @Override
    public void drag(int x, int y, int button, int mods) {
        int count = 0;
        for (GUITab tab : tabs) {
            if (selected != count++) continue;
            tab.drag(x, y, button, mods);
        }
    }

    private Consumer<GUITabList> createdTask = null;

    public void onCreate(Consumer<GUITabList> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }
}
