package dev.mv.vrender.main;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.TestScreen;
import dev.mv.vrender.window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Renderer{

    Texture max, md, demon, al;

    float rot = 0.0f;

    @Override
    public void start(Window w) {
        max = new Texture("src/textures/max.png");
        md = new Texture("src/textures/maxDesc.png");
        demon = new Texture("src/textures/demon.png");
        al = new Texture("src/textures/alpha.png");

        w.camera.moveSpeed = 3.0f;

        w.setActiveScreen(new TestScreen());
    }

    @Override
    public void render(Window w) {

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
