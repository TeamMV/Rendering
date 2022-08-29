package dev.mv.vrender.text;

import dev.mv.vrender.texture.Texture;
import lombok.Getter;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitmapFont {
    private Map<Integer, Glyph> chars;
    @Getter
    private Texture bitmap;

    private int maxWidth = 0, maxHeight = 0;

    public BitmapFont(String pngFile, String fntFile) {

        bitmap = loadTexture(pngFile);

        try {
            chars = createCharacters(fntFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Texture loadTexture(String pngFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pngFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (img == null) {
            return null;
        }
        return new Texture(img);
    }

    private Map<Integer, Glyph> createCharacters(String fntFile) throws IOException {
        BufferedReader reader = null;
        Map<Integer, Glyph> map = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(fntFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (reader == null) {
            return null;
        }

        int totalChars = -1;
        int lineHeight = -1;
        int atlasWidth = 1;
        int atlasHeight = 1;

        while (totalChars == -1) {
            String line = reader.readLine();
            if (line.contains("common ")) {
                lineHeight = Integer.parseInt(getCharAttrib(line, "lineHeight"));
                atlasWidth = Integer.parseInt(getCharAttrib(line, "scaleW"));
                atlasHeight = Integer.parseInt(getCharAttrib(line, "scaleH"));
            }
            if (line.contains("chars ")) {
                totalChars = Integer.parseInt(getCharAttrib(line, "count"));
            }
        }

        for (int i = 0; i < totalChars; i++) {
            String line = reader.readLine();
            maxWidth = Math.max(maxWidth, Integer.parseInt(getCharAttrib(line, "width")));
            maxHeight = Math.max(maxHeight, Integer.parseInt(getCharAttrib(line, "height")));
            map.put(Integer.parseInt(getCharAttrib(line, "id")), new Glyph(
                    Integer.parseInt(getCharAttrib(line, "x")),
                    Integer.parseInt(getCharAttrib(line, "y")),
                    Integer.parseInt(getCharAttrib(line, "width")),
                    Integer.parseInt(getCharAttrib(line, "height")),
                    Integer.parseInt(getCharAttrib(line, "xoffset")),
                    Integer.parseInt(getCharAttrib(line, "yoffset")),
                    Integer.parseInt(getCharAttrib(line, "xadvance"))
            ).makeTexCoords(atlasWidth, atlasHeight));
        }

        return map;
    }

    private String getCharAttrib(String line, String name) {
        Pattern pattern = Pattern.compile("\s+");
        Matcher matcher = pattern.matcher(line);
        line = matcher.replaceAll(" ");
        String[] attribs = line.split(" ");

        for (String s : attribs) {
            if (s.contains(name)) {
                return s.split("=")[1];
            }
        }

        return "";
    }

    public int getSpacing() {
        return (int) (maxWidth / 10f);
    }

    public Vector2f[] getUV(char c) {
        return chars.get(c + 0).getTexCoords();
    }

    public int getDefaultHeight() {
        return maxHeight;
    }

    public int getHeight(char c) {
        return chars.get(c + 0).getHeight();
    }

    public int getWidth(char c) {
        return chars.get(c + 0).getWidth();
    }

    public Glyph getGlyph(char c) {
        return chars.get(c + 0);
    }
}