package dev.mv.vrender.text;
public class FontHolder {

    public static BitmapFont font;

    public static void onStart() {
        font = new BitmapFont(FontHolder.class.getResource("/fonts/Viga/viga.png").getPath(), FontHolder.class.getResource("/fonts/Viga/viga.fnt").getPath());
    }
}
