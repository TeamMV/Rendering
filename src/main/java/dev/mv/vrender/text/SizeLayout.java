package dev.mv.vrender.text;

import lombok.Getter;
import lombok.Setter;

public class SizeLayout {
    @Setter
    private BitmapFont font;
    @Setter
    private String text;

    public int getWidth(){
        return getWidth(text);
    }
    public int getWidth(String s){
        int res = 0;

        for(int i = 0; i < s.length(); i++){
            res += font.getWidth(s.charAt(i));
            res += font.getSpacing();
        }

        return res;
    }
}
