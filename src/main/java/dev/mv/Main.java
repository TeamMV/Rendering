package dev.mv;

import dev.mv.animation.Animation;
import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.GUISlider;
import dev.mv.vrender.text.FontHolder;
import dev.mv.vrender.utils.DefaultTextures;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Screen;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Main extends Screen implements Renderer {
    private Animation anim;
    private GUISlider slider;

    public static void main(String[] args) {
        Window win = new Window(1500, 800, "test", true, new Main());
        win.run();

    }

    @Override
    public void start(Window w) {

        GUI gui = new GUI("hAS");

        gui.open();
        guis.add(gui);

        anim = new Animation(DefaultTextures.GENERATOR, 1, 3).speed(1000).play();

        slider = new GUISlider(100, 400, 400, 10, 1);
        gui.attachElement(slider);

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
        w.draw.color(0, 0, 0, 255);
        anim.draw(w, 100, 100, 200, 200, 0f);

        w.draw.text(10, w.getHeight() - 60, 50, w.getCurrentFPS() + " fps", FontHolder.font);

        renderGUI(w);
    }

    @Override
    public void update(Window w) {
        //if(w.input.mouseClick(1)) anim.stop(); else anim.play();

        if(w.input.scrollDown()) w.camera.zoom -= 0.2f;
        if(w.input.scrollUp()) w.camera.zoom += 0.2f;

        anim.speed(slider.getValue() * 10);
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
