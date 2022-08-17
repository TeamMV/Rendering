package dev.mv.vrender.text;

import dev.mv.vrender.texture.Texture;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Font {

    private Texture[] letters = new Texture[95];
    @Getter
    private java.awt.Font font;
    private int width, height, lineHeight;
    private int size;
    private FontMetrics fm;
    private java.awt.Font rfont;

    private Color color = new Color(0, 0, 0, 255);

    public Font(String path, int size) {
        this.size = size;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            rfont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(path));
            ge.registerFont(rfont);

            font = new java.awt.Font(rfont.getName(), java.awt.Font.PLAIN, size);

        } catch (FontFormatException e) {

        } catch (IOException e) {

        }

        updateLetters();
    }

    private void updateLetters(){
        for (int i = 32; i <= 126; i++) {
            letters[i - 32] = getLetter((char) i);
        }
    }

    private Texture getLetter(char c) {
        BufferedImage tempfontImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(font);

        fm = g.getFontMetrics();
        int charwidth = fm.charWidth(c);

        if (charwidth <= 0) {
            charwidth = 1;
        }
        int charheight = fm.getHeight();
        if (charheight <= 0) {
            charheight = size;
        }

        //Create another image for texture creation
        BufferedImage fontImage;
        fontImage = new BufferedImage(charwidth,charheight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D gt = (Graphics2D)fontImage.getGraphics();

        gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, true));
        g.fillRect(0, 0, charwidth, charheight);

        gt.setFont(font);

        gt.setColor(color);
        int charx = 0;
        int chary = 0;
        gt.drawString(String.valueOf(c), (charx), (chary) + fm.getAscent());

        return new Texture(fontImage);
    }

    public Texture getImg(char c) {
        return letters[c - 32];
    }

    public int getWidth(char c){
        return fm.charWidth(c);
    }

    public int getWidth(String s){
        return fm.charsWidth(s.toCharArray(), 0, s.length());
    }

    public int getHeight(){
        return fm.getHeight();
    }

    public int getSpacing(){
        return 2;
    }

    public void setLineHeight(int height){
        this.lineHeight = height;
        font = font.deriveFont(java.awt.Font.PLAIN, ((float)height * 72.0f / 96.0f));
        updateLetters();
    }

    public int getLineHeight(){
        return lineHeight;
    }
}