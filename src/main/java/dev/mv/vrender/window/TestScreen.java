package dev.mv.vrender.window;

import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.GUIInputBox;

public class TestScreen extends Screen{

    GUI gui = new GUI("login");

    public TestScreen(){
        gui.attachElement(new GUIInputBox(100, 100, 400, 100, "name"));
        guis.add(gui);
        gui.open();
    }

    @Override
    public void render(Window w) {
        renderGUI(w);
    }

    @Override
    public void keyHeld(char c, int mods) {
        //System.out.println(c + " held");
    }

    @Override
    public void keyUp(char c, int mods) {
        //System.out.println(c + " released");
    }

    @Override
    public void keyTyped(char c, int mods) {
        //System.out.println(c + " typed");
    }

    @Override
    public void onMouseDown(int button, int mods) {

    }

    @Override
    public void onMouseUp(int button, int mods) {

    }

    @Override
    public void onMouseDrag(int x, int y, int button, int mods) {

    }

    @Override
    public void onMouseClick(int x, int y, int button, int mods) {
    }

    @Override
    public void onMouseRelease(int x, int y, int button, int mods) {

    }

    @Override
    public void onMouseScroll(int x, int y) {

    }

    @Override
    public void onActivation(Window w) {

    }
}
