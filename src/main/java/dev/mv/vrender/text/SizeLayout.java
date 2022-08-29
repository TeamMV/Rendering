package dev.mv.vrender.text;

import lombok.Getter;
import lombok.Setter;

public class SizeLayout {
    @Setter
    private BitmapFont font = null;
    @Setter
    private String text = "";
    @Setter
    private int height = 0;

    public SizeLayout(BitmapFont font, String text, int height){
        this.font = font;
        this.height = height;
        this.text = text;
    }

    public SizeLayout(){

    }

    public int getWidth(){
        return getWidth(text);
    }
    public int getWidth(String s){
        int res = 0;

        float multiplier = (float)height / (float)font.getDefaultHeight();

        for(int i = 0; i < s.length(); i++){
            res += font.getWidth(s.charAt(i)) * multiplier;
            res += font.getSpacing();
            if(s.charAt(i) == ' ') res += font.getWidth('i') * multiplier;
        }

        return res;
    }

    public int getHeight(){
        return font.getDefaultHeight();
    }

    public void set(BitmapFont font, String text, int height){
        this.font = font;
        this.height = height;
        this.text = text;
    }

    public void set(BitmapFont font, String text){
        this.font = font;
        this.text = text;
    }
}
