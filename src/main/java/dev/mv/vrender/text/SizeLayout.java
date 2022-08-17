package dev.mv.vrender.text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SizeLayout {

    private static Font f;
    private static String s;
    private static FontMetrics fm;

    public static void set(Font font, String text) {
        f = font;
        s = text;

        updateFontMetrics();
    }

    public static void setText(String text){
        s = text;

        updateFontMetrics();
    }

    public static void setText(String text, int lineHeight){
        s = text;
        f.setLineHeight(lineHeight);

        updateFontMetrics();
    }

    public static void setFont(Font font){
        f = font;

        updateFontMetrics();
    }

    private static void updateFontMetrics(){
        BufferedImage tempfontImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
        g.setFont(f.getFont());
        fm = g.getFontMetrics();
    }

    public static int getWidth(){
        return fm.charsWidth(s.toCharArray(), 0, s.length());
    }

    public static int getHeight(){
        return fm.getHeight();
    }

    public static int getWidth(String s){
        setText(s);
        return getWidth();
    }

    public static int getHeight(String s){
        setText(s);
        return getHeight();
    }

    public static int getWidth(String s, int lineHeight){
        setText(s, lineHeight);
        return getWidth();
    }

    public static int getHeight(String s, int lineHeight){
        setText(s, lineHeight);
        return getHeight();
    }
}
