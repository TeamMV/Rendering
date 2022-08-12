package dev.mv.vrender.main;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Renderer {

    Texture max, md;

    @Override
    public void start(Window w) {
        max = new Texture("src/textures/max.png");
        md = new Texture("src/textures/maxDesc.png");

        w.camera.moveSpeed = 3.0f;
    }

    @Override
    public void render(Window w) {
        w.draw.color(255, 255, 0, 100);
        w.draw.triangle(20, 40, 300, 500, 700, 220);

        w.draw.image(100, 100, 100, 100, max);

        if(w.input.mouseInside(100, 100, 200, 200)){
            w.draw.image(100, 100, 100, 100, md);
        }
    }

    @Override
    public void update(Window w) {
        if(w.input.keyDown(GLFW_KEY_W)) w.camera.position.y -= w.camera.moveSpeed;
        if(w.input.keyDown(GLFW_KEY_A)) w.camera.position.x += w.camera.moveSpeed;
        if(w.input.keyDown(GLFW_KEY_S)) w.camera.position.y += w.camera.moveSpeed;
        if(w.input.keyDown(GLFW_KEY_D)) w.camera.position.x -= w.camera.moveSpeed;

        if(w.input.scrollUp()) w.camera.zoom += 0.05f;
        if(w.input.scrollDown()) w.camera.zoom -= 0.05f;
    }

    public static void main(String[] args) {
        Window win = new Window(800, 600, "myGame", true, new Main());
        win.run();
    }
}
