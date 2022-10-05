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

    public static TextureRegion[] GENERATOR = new TextureRegion[4];

    public static Texture POWER_SHEET;
    public static TextureRegion CABLE;

    public static void onStart() {
        try {
            BUTTON_SHEET = new Texture(DefaultTextures.class.getResourceAsStream(File.separator + "defaultTextures" + File.separator + "buttonSheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BUTTON_CROSS = BUTTON_SHEET.getRegion(0, 0, 32, 32);
        BUTTON_TICK = BUTTON_SHEET.getRegion(32, 0, 32, 32);
        BUTTON_DOT = BUTTON_SHEET.getRegion(0, 32, 32, 32);
        BUTTON_ARROW = BUTTON_SHEET.getRegion(32, 32, 32, 32);

        try {
            POWER_SHEET = new Texture(DefaultTextures.class.getResourceAsStream(File.separator + "defaultTextures" + File.separator + "powerSheet1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CABLE = POWER_SHEET.getRegion(64, 32, 32, 32);

        GENERATOR[0] = POWER_SHEET.getRegion(0, 0, 32, 32);
        GENERATOR[1] = POWER_SHEET.getRegion(32, 0, 32, 32);
        GENERATOR[2] = POWER_SHEET.getRegion(64, 0, 32, 32);
        GENERATOR[3] = POWER_SHEET.getRegion(96, 0, 32, 32);
    }
}
