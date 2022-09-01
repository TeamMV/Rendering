package dev.mv;

import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.GUIButton;
import dev.mv.vgui.elements.GUIInputBox;
import dev.mv.vgui.elements.GUILabel;
import dev.mv.vgui.elements.GUISlider;
import dev.mv.vgui.elements.window.GUIWindow;
import dev.mv.vgui.elements.window.layout.HorizontalGUILayout;
import dev.mv.vgui.elements.window.layout.VerticalGUILayout;
import dev.mv.vrender.text.FontHolder;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Screen;
import dev.mv.vrender.window.Window;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main extends Screen implements Renderer {

    private GUI gui = new GUI("guitest");

    public static void main(String[] args) {
        Window win = new Window(1000, 800, "game", true, new Main());
        win.run();
    }

    @Override
    public void start(Window w) {

        FontHolder.onStart();
        w.setActiveScreen(this);

        GUIButton button1 = new GUIButton(200, 100, true, 200, 70, "game", FontHolder.font, e -> {

        });

        GUIButton button2 = new GUIButton(200, 100, true, 250, 70, "help", FontHolder.font, e -> {

        });

        GUIButton button3 = new GUIButton(200, 100, true, 200, 70, "about", FontHolder.font, e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new URL("https://factoryisland.net").toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        GUIInputBox box1 = new GUIInputBox(200, 100, 400, 70, "name", FontHolder.font);
        GUIInputBox box2 = new GUIInputBox(200, 100, 400, 70, "pass", FontHolder.font, true);

        GUIButton button4 = new GUIButton(200, 100, true, 300, 70, "view pass", FontHolder.font, e -> {
            if(box2.isHidden()){
                box2.revealText();
            }else{
                box2.hideText();
            }
        });

        GUILabel label1 = new GUILabel(200, 100, 30, "register here:", FontHolder.font);

        gui.setWindow(new GUIWindow(100, 50, 800, 700));

        VerticalGUILayout vgl = new VerticalGUILayout(200, 700).setSpacing(20);
        vgl.appendItem(button1);
        vgl.appendItem(button2);
        vgl.appendItem(button3);
        vgl.appendItem(label1);
        vgl.appendItem(box1);
        vgl.appendItem(box2);
        vgl.appendItem(button4);

        vgl.centerInWindow(gui);
        vgl.alignContent(VerticalGUILayout.Alignment.CENTER);

        gui.attachElement(vgl);

        //gui.attachElement(new GUISlider(300, 500, 200, 5, 1));

        gui.open();
        guis.add(gui);
    }

    @Override
    public void render(Window w) {
        renderGUI(w);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void keyDown(int c) {

    }

    @Override
    public void keyUp(int c) {

    }

    @Override
    public void keyTyped(char c) {

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

    @Override
    public void update(Window w) {

    }

    @Override
    public void resize(Window w, int width, int height) {

    }
}
