package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.Typeable;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class GUIInputBox extends GUIElement implements Clickable, Typeable {

    private int width, height, textWidth, charOverflow = 0;
    @Getter
    @Setter
    private int maxChars = -1;
    private String placeholder, text = "";
    private boolean isSelected, isPlaceholder = true;
    @Getter
    private boolean hidden;
    @Getter
    private GUILabel label;

    private SizeLayout layout;
    private BitmapFont font;

    public GUIInputBox(int x, int y, int width, int height, String placeholder, BitmapFont font) {
        this(x, y, width, height, placeholder, font, -1);
    }

    public GUIInputBox(int x, int y, int width, int height, String placeholder, BitmapFont font, boolean hidden) {
        this(x, y, width, height, placeholder, font, -1, hidden);
    }

    public GUIInputBox(int x, int y, int width, int height, String placeholder, BitmapFont font, int maxChars) {
        this(x, y, width, height, placeholder, font, maxChars, false);
    }

    public GUIInputBox(int x, int y, int width, int height, String placeholder, BitmapFont font, int maxChars, boolean hidden) {
        layout = new SizeLayout(font, text, height - height / 5);
        xPos = x;
        yPos = y;
        this.maxChars = maxChars;
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.hidden = hidden;
        this.font = font;

        textWidth = layout.getWidth();

        label = new GUILabel(x + 20, y + (height / 2) - (layout.getHeight('e') / 2), height - height / 5, placeholder, font);
    }

    public GUIInputBox(VariablePosition position, String placeholder, BitmapFont font) {
        this(position, placeholder, font, -1);
    }

    public GUIInputBox(VariablePosition position, String placeholder, BitmapFont font, boolean hidden) {
        this(position, placeholder, font, -1, hidden);
    }

    public GUIInputBox(VariablePosition position, String placeholder, BitmapFont font, int maxChars) {
        this(position, placeholder, font, maxChars, false);
    }

    public GUIInputBox(VariablePosition position, String placeholder, BitmapFont font, int maxChars, boolean hidden) {
        positionCalculator = position;
        layout = new SizeLayout(font, text, height - height / 5);
        xPos = position.getX();
        yPos = position.getY();
        this.maxChars = maxChars;
        this.width = position.getWidth();
        this.height = position.getHeight();
        this.placeholder = placeholder;
        this.hidden = hidden;
        this.font = font;

        textWidth = layout.getWidth();

        label = new GUILabel(xPos + 20, yPos + (height / 2) - (layout.getHeight('e') / 2), height - height / 5, placeholder, font);
    }

    @Override
    public void render(Window w) {
        if (w.input.mouseInside(xPos, yPos, xPos + width, yPos + height) || isSelected) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        w.draw.rectangle(xPos + 10, yPos, width - 20, height);
        w.draw.triangle(xPos, yPos + height / 2, xPos + 10, yPos + height, xPos + 10, yPos);
        w.draw.triangle(xPos + width, yPos + height / 2, xPos + width - 10, yPos + height, xPos + width - 10, yPos);

        w.draw.color(40, 40, 40, 255);

        w.draw.rectangle(xPos + 13, yPos + 5, width - 26, height - 10);
        w.draw.triangle(xPos + 5, yPos + height / 2, xPos + 13, yPos + height - 5, xPos + 13, yPos + 5);
        w.draw.triangle(xPos + width - 5, yPos + height / 2, xPos + width - 13, yPos + height - 5, xPos + width - 13, yPos + 5);

        if (w.input.mouseInside(xPos, yPos, xPos + width, yPos + height) || isSelected) {
            w.draw.color(186, 247, 32, 255);
        } else {
            w.draw.color(255, 255, 255, 255);
        }

        label.render(w);

        if (isSelected) {
            w.draw.rectangle(xPos + textWidth + 20, yPos + 10, 3, height - 20);
        }

        w.draw.color(0, 0, 0, 255);
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        this.width = positionCalculator.getWidth();
        this.height = positionCalculator.getHeight();
        layout.setHeight(this.height - this.height / 5);
        label.setX(xPos + 20);
        label.setY(yPos + (height / 2)  - (layout.getHeight('e') / 2));
        label.setHeight(this.height - this.height / 5);
    }

    @Override
    public void setXPos(int x) {
        xPos = x;
        label.setXPos(x + 20);
        if (positionCalculator == null) return;
        positionCalculator.setX(x);
    }

    @Override
    public void setYPos(int y) {
        yPos = y;
        label.setYPos(y + (height / 2) - (layout.getHeight('e') / 2));
        if (positionCalculator == null) return;
        positionCalculator.setY(y);
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
    public void keyTyped(char c) {
        if (isSelected) {
            if (c == 259 || c == '\b') {
                if (isPlaceholder) return;
                if (text.length() == 0) return;
                text = text.substring(0, text.length() - 1);
                label.setText(text);

                if (charOverflow > 0) {
                    label.setText(text.substring(--charOverflow));
                }

                if (hidden) {
                    label.setText("*".repeat(label.getText().length()));
                }
            } else if (c > 31 && c < 128) {
                if (isPlaceholder) {
                    label.setText("");
                    isPlaceholder = false;
                }

                if (text.length() >= maxChars && maxChars > 0) return;

                text += c;

                label.setText(text);

                if (layout.getWidth(label.getText()) >= width - 20) {
                    if (charOverflow <= maxChars || maxChars == -1) {
                        label.setText(label.getText().substring(++charOverflow));
                    }
                }

                if (hidden) {
                    label.setText("*".repeat(label.getText().length()));
                }
            }
        }

        layout.setHeight(height);
        textWidth = layout.getWidth(label.getText());
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height) {
            if (isSelected) return;
            isSelected = true;
            if (!isPlaceholder) return;
            label.setText("");
            isPlaceholder = false;
        } else {
            isSelected = false;
            if (!text.equals("")) return;
            label.setText(placeholder);
            isPlaceholder = true;
        }
    }

    @Override
    public void release(int x, int y, int mods) {

    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void hideText() {
        hidden = true;
        if (text.length() == 0) {
            if (isPlaceholder) label.setText(placeholder);
            return;
        }
        label.setText("*".repeat(text.substring(charOverflow).length()));
        textWidth = layout.getWidth(label.getText());
    }

    public void revealText() {
        hidden = false;
        label.setText(text.substring(charOverflow));
        textWidth = layout.getWidth(label.getText());
        if (isPlaceholder) label.setText(placeholder);
    }

    private Consumer<GUIInputBox> createdTask = null;

    public void onCreate(Consumer<GUIInputBox> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }

}