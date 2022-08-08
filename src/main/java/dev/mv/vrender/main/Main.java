package dev.mv.vrender.main;

import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Window;

public class Main implements Renderer {

    @Override
    public void start(Window w) {

    }

    @Override
    public void render(Window w) {
        w.draw.color(255, 255, 0, 255);
        w.draw.rectangle(10, 10, 100, 100);
        w.draw.color(123, 32, 233, 255);
        w.draw.triangle(120, 20, 300, 400, 600, 100);
        w.draw.color(255, 0, 0, 100);
        w.draw.triangle(200, 10, 600, 550, 700, 40);
    }

    public static void main(String[] args) {
        Window win = new Window(800, 600, "myGame", true, new Main());
        win.run();
    }
}
