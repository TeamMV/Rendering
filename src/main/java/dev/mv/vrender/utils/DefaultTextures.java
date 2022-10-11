package dev.mv.vrender.utils;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.texture.TextureRegion;

import java.io.File;
import java.io.IOException;

public class DefaultTextures {
    public static Texture BUTTON_SHEET;
    public static TextureRegion BUTTON_CROSS;
    public static TextureRegion BUTTON_TICK;
    public static TextureRegion BUTTON_DOT;
    public static TextureRegion BUTTON_ARROW;

    public static void onStart() {
        try {
            BUTTON_SHEET = new Texture(DefaultTextures.class.getResourceAsStream(File.separator + "defaultTextures" + File.separator + "buttonSheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
