package dev.mv.vrender.render;

public class Vertices {
    private Vertex[] data = new Vertex[4];

    private int length;

    public Vertices set(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        this.data[0] = v1;
        this.data[1] = v2;
        this.data[2] = v3;
        this.data[3] = v4;

        length = 4;

        return this;
    }

    public Vertices set(Vertex v1, Vertex v2, Vertex v3) {
        this.data[0] = v1;
        this.data[1] = v2;
        this.data[2] = v3;

        length = 3;

        return this;
    }

    public Vertex get(int index) {
        return data[index];
    }

    public int length() {
        return length;
    }
}
