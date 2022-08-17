package dev.mv.vrender.text;

import dev.mv.vrender.render.Draw;
import lombok.Getter;

public class TextRenderer {

    private Font font;
    private Draw draw;

    public TextRenderer(Font font, Draw draw){
        this.font = font;
        this.draw = draw;

        SizeLayout.setFont(font);
    }

    public void draw(int x, int y, int height, String s){
        if(font.getLineHeight() != height){
            font.setLineHeight(height);
        }
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ' ') continue;
            draw.image(x + ((i * font.getSpacing() + font.getWidth(s.substring(0, i)))), y, font.getWidth(s.charAt(i)), font.getHeight(), font.getImg(s.charAt(i)));
        }
    }
}
