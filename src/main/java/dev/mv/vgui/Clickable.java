package dev.mv.vgui;

public interface Clickable {

    void click(int x, int y, int button, int mods);
    void release(int x, int y, int mods);

}
