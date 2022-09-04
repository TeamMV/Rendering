package dev.mv;

import dev.mv.vgui.GUI;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.*;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vgui.elements.window.GUIWindow;
import dev.mv.vgui.elements.window.layout.VerticalGUILayout;
import dev.mv.vrender.camera.Camera;
import dev.mv.vrender.render.Draw;
import dev.mv.vrender.text.FontHolder;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Screen;
import dev.mv.vrender.window.Window;
import org.joml.Vector2f;

import java.util.Objects;

public class Main extends Screen implements Renderer {

    private GUI gui = new GUI("testGui");
    private GUIStatusBar bar;

    public static void main(String[] args) {
        Window win = new Window(1500, 800, "test", true, new Main());
        win.run();

    }

    @Override
    public void start(Window w) {

        w.setActiveScreen(this);

        gui.setWindow(new GUIWindow(50, 50, 900, 700, gui));

        GUITab tab = new GUITab("button");
        GUITab tab2 = new GUITab("slider");
        GUITab tab3 = new GUITab("bar");

        tab.addItem(new GUIButton(100, 100, false, 50, "hello", FontHolder.font, new ClickListener() {
            @Override
            public void clicked(GUIElement e) {
                System.out.println("lol");
            }
        }));

        tab2.addItem(new GUISlider(100, 100, 500, 10, 1));

        bar = new GUIStatusBar(100, 200, 500, 75, 1000, new int[] {255, 255, 0, 255});

        tab3.addItem(bar);
        tab3.addItem(new GUIButton(100, 100, false, 500, 75, "reset bar", FontHolder.font, new ClickListener() {
            @Override
            public void clicked(GUIElement e) {
                bar.set(0);
                System.out.println("yay");
            }
        }));

        GUITabList tl = new GUITabList(200, 500, 1, FontHolder.font);
        tl.addTab(tab);
        tl.addTab(tab2);
        tl.addTab(tab3);

        VerticalGUILayout vgl = new VerticalGUILayout(0, 700).setSpacing(10);
        vgl.appendItem(tl);
        vgl.centerInWindow(gui);
        vgl.alignContent(VerticalGUILayout.Alignment.CENTER);

        gui.attachElement(vgl);
        gui.open();
        guis.add(gui);
    }

    int i = 0;
    @Override
    public void render(Window w) {
        renderGUI(w);
        w.draw.mode(Draw.CAMERA_STATIC);
        w.draw.color(255, 255, 0, 255);
        w.draw.rectangle(740, 390, 20, 20, i++);
    }

    @Override
    public void update(Window w) {
        bar.increment(1);
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
    public void resize(Window w, int width, int height) {
        //camera.updateProjection();
    }
}
