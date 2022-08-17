package dev.mv.vgui;

public interface GUIPosition {

    void resize(int width, int height);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    void setX(int x);

    void setY(int y);

    void setWidth(int width);

    void sezHeight(int height);

}