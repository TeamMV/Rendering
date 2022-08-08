package dev.mv.vrender.window;

public interface Renderer {
    /**
     * gets called once when the method Window.run() is executed and if the interface Renderer is implemented in the main class
     */
    void start(Window w);

    /**
     * gets called every frame since the method Window.run() is executed and if the interface Renderer is implemented in the mian class
     */
    void render(Window w);
}
