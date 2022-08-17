package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.Typeable;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class GUIInputBox extends GUIElement implements Clickable, Typeable {

    private int width, height;
    private String placeholder, text;
    private boolean isSelected, isEmpty, isPlaceholder;
    @Getter
    private boolean hidden;

    private GUILabel label;

    public GUIInputBox(int x, int y, int width, int height, String placeholder) {
        this(x, y, width, height, placeholder, false);
    }

    public GUIInputBox(int x, int y, int width, int height, String placeholder, boolean hidden){
        xPos = x;
        yPos = y;
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.hidden = hidden;

        label = new GUILabel(x + 10, y + 10, height - 20, placeholder);
    }

    @Override
    public void click(int x, int y, int button) {

    }

    @Override
    public void render(Window w) {
        w.draw.color(0, 0, 0, 255);
        w.draw.rectangle(xPos, yPos, width, height);
        w.draw.color(117, 117, 117, 255);

        if(w.input.mouseInside(xPos, yPos, xPos + width, yPos + height)){
            w.draw.color(140, 140, 140, 255);
        }

        w.draw.rectangle(xPos + 3, yPos + 3, width - 6, height- 6);
        w.draw.color(255, 255, 255, 255);
        label.render(w);

        if(isSelected){
            w.draw.rectangle(xPos + 10 + SizeLayout.getWidth(text, height - 20) + 5, yPos + 10, 3, height - 20);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void keyTyped(char c) {
        if (isSelected) {
            if (c == '\b') {
                if (isPlaceholder) return;
                label.setText(label.getText().substring(0, label.getText().length() - 2));
            }
            else if (c == '\n' || c == '\r') {
                return;
            }
            else {
                if (isPlaceholder) {
                    label.setText("");
                    isPlaceholder = false;
                }
                label.setText(label.getText() + c);
            }
        }

        if(label.getText().equals("")) {
            isPlaceholder = true;
            label.setText(placeholder);
        }
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void hideText() {
        hidden = true;
    }

    public void revealText() {
        hidden = false;
    }

}