package dev.mv.vrender.main;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Renderer;
import dev.mv.vrender.window.Window;
import dev.mv.vrender.window.screens.MainMenu;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Renderer {
    float rot = 0.0f;

    Texture tex;

    @Override
    public void start(Window w) {

        w.camera.moveSpeed = 3.0f;

        w.setActiveScreen(new MainMenu(w));

        tex = new Texture(this.getClass().getResource("/textures/main_bg.png").getPath());
    }

    @Override
    public void render(Window w) {
        //w.draw.image(0, 0, 100, 100, tex);
    }

    @Override
    public void update(Window w) {
        if (w.input.keyDown(GLFW_KEY_W)) w.camera.position.y -= w.camera.moveSpeed;
        if (w.input.keyDown(GLFW_KEY_A)) w.camera.position.x += w.camera.moveSpeed;
        if (w.input.keyDown(GLFW_KEY_S)) w.camera.position.y += w.camera.moveSpeed;
        if (w.input.keyDown(GLFW_KEY_D)) w.camera.position.x -= w.camera.moveSpeed;

        if (w.input.scrollUp()) w.camera.zoom += 0.05f;
        if (w.input.scrollDown()) w.camera.zoom -= 0.05f;
    }

    public static void main(String[] args) {
        Window win = new Window(1200, 900, "myGame", true, new Main());
        win.run();
    }
}
