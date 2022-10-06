package dev.mv.vrender.text;

public class CharNotSupportetException extends Exception{
    public CharNotSupportetException(char c) {
        super("character: " + c + ", keyCode: " + (c + 0));
    }
}
