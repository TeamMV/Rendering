package dev.mv.vrender.render;

public class Vertex {
    private float[] data = new float[Batch.VERTEX_SIZE_FLOATS];

    public Vertex put(float... data) {
        this.data = data;
        return this;
    }

    public float get(int index) {
        return data[index];
    }
}
