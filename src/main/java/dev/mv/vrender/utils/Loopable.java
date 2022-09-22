package dev.mv.vrender.utils;

import dev.mv.vrender.window.Window;

public interface Loopable {
    void loop(Window w);

    void tick(Window w);
}