package dev.mv.vrender.text;

import dev.mv.vrender.texture.Texture;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BitmapFont {

    private int width, height, lineHeight;
    private int size;
    private String path;
    private Color color = new Color(0, 0, 0, 255);
    private Map<Integer, CharInfo> characterMap = new HashMap<>();
    private Texture bitmap;

    public BitmapFont(String path, int size) throws IOException, FontFormatException {
        this.size = size;
        this.path = path;

        create();
    }

    private void create() throws IOException, FontFormatException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont((float)size);

        // Create fake image to get font information
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();

        int estimatedWidth = (int)Math.sqrt(font.getNumGlyphs()) * font.getSize() + 1;
        width = 0;
        height = fontMetrics.getHeight();
        lineHeight = fontMetrics.getHeight();
        int x = 0;
        int y = (int)(fontMetrics.getHeight() * 1.4f);

        for (int i=0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)) {
                // Get the sizes for each codepoint glyph, and update the actual image width and height
                CharInfo charInfo = new CharInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getHeight());
                characterMap.put(i, charInfo);
                width = Math.max(x + fontMetrics.charWidth(i), width);

                x += charInfo.width;
                if (x > estimatedWidth) {
                    x = 0;
                    y += fontMetrics.getHeight() * 1.4f;
                    height += fontMetrics.getHeight() * 1.4f;
                }
            }
        }
        height += fontMetrics.getHeight() * 1.4f;
        g2d.dispose();

        // Create the real texture
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        for (int i=0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)) {
                CharInfo info = characterMap.get(i);
                info.calculateTextureCoordinates(width, height);
                g2d.drawString("" + (char)i, info.sourceX, info.sourceY);
            }
        }
        g2d.dispose();

        bitmap = new Texture(img);
    }

    public Vector2f[] getUV(char c) {
        return characterMap.get((int) c).getTextureCoordinates();
    }

    public int getWidth(char c){
        return characterMap.get((int) c).width;
    }

    public int getDefaultHeight(){
        return lineHeight;
    }

    public Texture getBitmap(){
        return bitmap;
    }
    public int getSpacing(){
        return 2;
    }
}