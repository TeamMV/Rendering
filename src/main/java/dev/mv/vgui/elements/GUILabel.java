package dev.mv.vgui.elements;

import dev.mv.vgui.GUIElement;
import dev.mv.vrender.text.BitmapFont;
import dev.mv.vrender.text.SizeLayout;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Window;

import java.util.function.Consumer;

public class GUILabel extends GUIElement {

    SizeLayout layout = new SizeLayout();

    private int lineHeight;
    private String text, initialText;
    private BitmapFont font;

    private int[] colour, initialColor;
    private Consumer<GUILabel> createdTask = null;

    public GUILabel(int x, int y, int lineHeight, String text, BitmapFont font) {
        xPos = x;
        yPos = y;
        this.lineHeight = lineHeight;
        this.text = text;
        this.initialText = text;
        this.font = font;
        colour = new int[] {255, 255, 255, 255};
        initialColor = colour;

        layout.set(font, text, lineHeight);
    }

    public GUILabel(VariablePosition position, String text, BitmapFont font) {
        positionCalculator = position;
        xPos = position.getX();
        yPos = position.getY();
        this.lineHeight = position.getHeight();
        this.text = text;
        this.initialText = text;
        this.font = font;
        colour = new int[] {255, 255, 255, 255};
        initialColor = colour;

        layout.set(font, text, lineHeight);
    }

    public GUILabel(int x, int y, int lineHeight, String text, BitmapFont font, int[] colour) {
        xPos = x;
        yPos = y;
        this.lineHeight = lineHeight;
        this.text = text;
        this.initialText = text;
        this.font = font;
        this.colour = colour;
        initialColor = colour;

        layout.set(font, text, lineHeight);
    }

    public GUILabel(VariablePosition position, String text, BitmapFont font, int[] colour) {
        positionCalculator = position;
        xPos = position.getX();
        yPos = position.getY();
        this.lineHeight = position.getHeight();
        this.text = text;
        this.initialText = text;
        this.font = font;
        this.colour = colour;
        initialColor = colour;

        layout.set(font, text, lineHeight);
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
        layout.setText(s);
    }

    @Override
    public void render(Window w) {
        w.draw.color(colour[0], colour[1], colour[2], colour[3]);
        w.draw.text(xPos, yPos, lineHeight, text, font);
    }

    @Override
    public void resize(int width, int height) {
        if (positionCalculator == null) return;
        positionCalculator.resize(width, height);
        xPos = positionCalculator.getX();
        yPos = positionCalculator.getY();
        lineHeight = positionCalculator.getHeight();
    }

    @Override
    public int getHeight() {
        return lineHeight;
    }

    public void setHeight(int height) {
        lineHeight = height;
    }

    @Override
    public int getWidth() {
        return layout.getWidth();
    }

    public void setY(int y) {
        yPos = y;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void onCreate(Consumer<GUILabel> task) {
        this.createdTask = task;
    }

    @Override
    public void created() {
        if (createdTask == null) return;
        createdTask.accept(this);
    }

    @Override
    protected void reset() {
        setText(initialText);
        colour = initialColor;
    }

    public void setColor(int r, int g, int b, int a){
        this.colour[0] = r;
        this.colour[1] = g;
        this.colour[2] = b;
        this.colour[3] = a;
    }
}
