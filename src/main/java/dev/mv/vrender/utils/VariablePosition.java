package dev.mv.vrender.utils;

import lombok.Getter;
import lombok.Setter;

public class VariablePosition {

    @Getter
    @Setter
    private int x, y, width, height;
    private PositionCalculator pos;

    public VariablePosition(int width, int height, PositionCalculator pos) {
        this.pos = pos;
        resize(width, height);
    }

    public void resize(int width, int height) {
        int[] size = pos.resize(width, height);
        x = size[0];
        y = size[1];
        this.width = size[2];
        this.height = size[3];
    }

}