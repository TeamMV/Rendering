package dev.mv.vgui.elements;

import dev.mv.vgui.Clickable;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.Typeable;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.window.Window;
import lombok.Getter;

public class GUIInputBox extends GUIElement implements Clickable, Typeable {

    private int width, height, textWidth;
    private String placeholder, text = "";
    private boolean isSelected, isPlaceholder = true;
    @Getter
    private boolean hidden;
    @Getter
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

        textWidth = SizeLayout.getWidth(text, height - 20);

        label = new GUILabel(x + 10, y + 10, height - 20, placeholder);
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
            w.draw.rectangle(xPos + 10 + textWidth + 5, yPos + 10, 3, height - 20);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void keyTyped(char c) {
        if (isSelected) {
            if (c == 259) {
                if (isPlaceholder) return;
                if(text.length() == 0) return;
                text = label.getText().substring(0, text.length() - 1);
                label.setText(text);
            }
            else if (c == 257) {
                return;
            }
            else if(c > 31 && c < 128){
                if (isPlaceholder) {
                    label.setText("");
                    isPlaceholder = false;
                }

                text += c;
                label.setText(text);

                if(hidden){
                    label.setText("*".repeat(label.getText().length()));
                }
            }

            textWidth = SizeLayout.getWidth(label.getText());
        }
    }

    @Override
    public void click(int x, int y, int button, int mods) {
        if(x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height){
            if(isSelected) return;
            isSelected = true;
            if(!isPlaceholder) return;
            label.setText("");
            isPlaceholder = false;
        }else{
            isSelected = false;
            if(!text.equals("")) return;
            label.setText(placeholder);
            isPlaceholder = true;
        }
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void hideText() {
        hidden = true;
        if(text.length() == 0){
            if(isPlaceholder) label.setText("");
            return;
        }
        label.setText("*".repeat(text.length()));
        textWidth = SizeLayout.getWidth(label.getText());
    }

    public void revealText() {
        hidden = false;
        label.setText(text);
        textWidth = SizeLayout.getWidth(label.getText());
        if(isPlaceholder) label.setText(placeholder);
    }

}