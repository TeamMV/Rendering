package dev.mv.vrender.window;

import dev.mv.vgui.GUI;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.GUIButton;
import dev.mv.vgui.elements.GUIInputBox;
import dev.mv.vgui.elements.GUILabel;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vrender.text.BitmapFont;

import java.awt.*;
import java.io.IOException;

public class TestScreen extends Screen{

    GUI gui = new GUI("login");

    public static BitmapFont font;

    {
        font = new BitmapFont("src/fonts/Viga/viga.png", "src/fonts/Viga/viga.fnt");
    }

    public TestScreen(){
        GUIInputBox nameBox = new GUIInputBox(100, 100, 400, 100, "pass", font);
        gui.attachElement(nameBox);

        gui.attachElement(new GUIInputBox(100, 220, 400, 100, "name", font));
        gui.attachElement(new GUIButton(550, 100, 64, "view", font, new ClickListener() {
            @Override
            public void clicked(GUIElement e) {
                if(nameBox.isHidden()){
                    nameBox.revealText();
                }else{
                    nameBox.hideText();
                }
            }
        }));

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
