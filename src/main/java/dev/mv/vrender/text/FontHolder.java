package dev.mv.vrender.text;

public class FontHolder {

    public static BitmapFont font;

    public static void onStart() {
        font = new BitmapFont("/fonts/Viga/viga.png", "/fonts/Viga/viga.fnt");
    }
}
