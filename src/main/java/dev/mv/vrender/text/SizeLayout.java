package dev.mv.vrender.text;

import lombok.Getter;
import lombok.Setter;

public class SizeLayout {
    @Setter
    private BitmapFont font = null;
    @Setter
    private String text = "";
    private int height = 0;
    @Getter
    private float multiplier = 0f;

    public SizeLayout(BitmapFont font, String text, int height) {
        this.font = font;
        this.height = height;
        this.text = text;
        this.multiplier = (float) height / (float) font.getDefaultHeight();
    }

    public SizeLayout() {

    }

    public int getWidth() {
        return getWidth(text);
    }

    public int getWidth(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += font.getWidth(s.charAt(i)) * multiplier;
            res += font.getSpacing();
            if (s.charAt(i) == ' ') res += font.getWidth('i') * multiplier;
        }
        return res;
    }

    public int getWidth(char c) {
        if (c == ' ') return (int) (font.getWidth('i') * multiplier) + font.getSpacing();
        return (int) (font.getWidth(c) * multiplier) + font.getSpacing();
    }

    public int getHeight() {
        return (int) (font.getDefaultHeight() * multiplier);
    }

    public SizeLayout setHeight(int height) {
        this.height = height;
        calculate();
        return this;
    }

    public int getHeight(char c) {
        return (int) (font.getHeight(c) * multiplier);
    }

    public float getXOffset(char c) {
        return font.getGlyph(c).getxOff() * multiplier;
    }

    public float getYOffset(char c) {
        return font.getGlyph(c).getyOff() * multiplier;
    }

    public SizeLayout set(BitmapFont font, String text, int height) {
        this.font = font;
        this.height = height;
        this.text = text;
        this.multiplier = (float) height / (float) font.getDefaultHeight();
        return this;
    }

    public SizeLayout set(BitmapFont font, String text) {
        this.font = font;
        this.text = text;
        this.height = font.getDefaultHeight();
        this.multiplier = (float) height / (float) font.getDefaultHeight();
        return this;
    }

    public SizeLayout calculate() {
        this.multiplier = (float) height / (float) font.getDefaultHeight();
        return this;
    }
}
