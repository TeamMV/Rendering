package dev.mv;

import dev.mv.animation.Animation;
import dev.mv.vgui.GUI;
import dev.mv.vgui.GUIElement;
import dev.mv.vgui.elements.*;
import dev.mv.vgui.elements.listeners.ClickListener;
import dev.mv.vgui.elements.window.GUIWindow;
import dev.mv.vgui.elements.window.layout.VerticalGUILayout;
import dev.mv.vrender.text.FontHolder;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Screen;
import dev.mv.vrender.window.Window;

public class Main extends Screen implements Renderer {
    private Animation anim;

    public static void main(String[] args) {
        Window win = new Window(1500, 800, "test", true, new Main());
        win.run();

    }

    @Override
    public void start(Window w) {

        anim = new Animation(DefaultTextures.GENERATOR, 1, 3).speed(100).play();

        w.setActiveScreen(this);
    }

    int i = 0;
    @Override
    public void render(Window w) {
        //renderGUI(w);
        //w.draw.mode(Draw.CAMERA_STATIC);
        //w.draw.color(0, 0, 0, 255);
        //w.draw.imageFromTo(500, 400, w.input.mousePosition().x, w.input.mousePosition().y, 100, DefaultTextures.CABLE);
        //w.draw.image(300, 300, 50, 200, DefaultTextures.CABLE);
        anim.draw(w, 100, 100, w.input.mousePosition().x - 100, w.input.mousePosition().y - 100, 0f);

        w.draw.text(10, w.getHeight() - 60, 50, w.getCurrentFPS() + " fps", FontHolder.font);
    }

    @Override
    public void update(Window w) {

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
