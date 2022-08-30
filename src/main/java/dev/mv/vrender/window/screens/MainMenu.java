package dev.mv.vrender.window.screens;

import dev.mv.vgui.GUI;
import dev.mv.vgui.elements.GUIButton;
import dev.mv.vrender.render.Draw;
import dev.mv.vrender.text.FontHolder;
import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.utils.VariablePosition;
import dev.mv.vrender.window.Screen;
import dev.mv.vrender.window.Window;

public class MainMenu extends Screen {

    private Texture main_bg, main_logo;
    private int logoX, logoWidth, logoHeight;

    private GUI main = new GUI("main_menu");

    private int r = 0;

    public MainMenu(Window w) {
        main_bg = new Texture(this.getClass().getResource("/textures/main_bg.png").getPath());
        main_logo = new Texture(this.getClass().getResource("/textures/main_logo.png").getPath());

        logoWidth = (int) (w.getWidth() - w.getWidth() / 5f);
        logoHeight = 64 * (logoWidth / 128);
        logoX = w.getWidth() / 2 - logoWidth / 2;

        int button_width = 400;

        main.attachElement(new GUIButton((int) ((w.getWidth() / 2) - (button_width / 2) - button_width / 1.7f), w.getHeight() - (w.getHeight() / 20) - logoHeight - 200, button_width, 50, "load game", FontHolder.font, e -> {

        }));

        main.attachElement(new GUIButton((int) ((w.getWidth() / 2) - (button_width / 2) + button_width / 1.7f), w.getHeight() - (w.getHeight() / 20) - logoHeight - 200, button_width, 50, "new game", FontHolder.font, e -> {

        }));

        main.attachElement(new GUIButton(new VariablePosition(w.getWidth(), w.getHeight(), (width, height) ->
            new int[] {w.getWidth() / 2 - 50, w.getHeight() / 2 - 20, 100, 40}
        ), "hello", FontHolder.font, e -> {

        }));

        main.open();
        guis.add(main);
    }

    @Override
    public void render(Window w) {

        w.draw.mode(Draw.CAMERA_STATIC);
        w.draw.color(0, 0, 0, 255);
        w.draw.image(0, 0, w.getWidth(), w.getHeight(), main_bg);
        w.draw.image(logoX, w.getHeight() - (w.getHeight() / 20) - logoHeight, logoWidth, logoHeight, main_logo);

        renderGUI(w);
    }

    @Override
    public void resize(int width, int height) {
        resizeGUI(width, height);
        logoWidth = (int) (width - width / 5f);
        logoHeight = 64 * (logoWidth / 128);
        logoX = width / 2 - logoWidth / 2;
    }

    @Override
    public void keyHeld(char c, int mods) {

    }

    @Override
    public void keyUp(char c, int mods) {

    }

    @Override
    public void keyTyped(char c, int mods) {

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
