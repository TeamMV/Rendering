package dev.mv.vrender.render;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Window;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BatchController{

    private static Window win;
    private static int maxBatchSize;
    private static int currentBatch;
    private static List<Batch> batches = new ArrayList<>();

    public static void init(Window window, int batchLimit){

        if(batchLimit < 4){
            throw new IllegalArgumentException("Batch limit of " + batchLimit + " is too small, at least 4 is required!");
        }

        win = window;
        maxBatchSize = batchLimit;
        currentBatch = 0;
        batches.add(new Batch(batchLimit, window));
    }

    private static void nextBatch() {
        currentBatch++;
        try {
            batches.get(currentBatch);
        } catch (IndexOutOfBoundsException e) {
            batches.add(new Batch(maxBatchSize, win));
        }
    }

    public static void addVertices(float[][] vertexData){

        if(batches.get(currentBatch).isFull(vertexData.length * batches.get(0).VERTEX_SIZE_FLOATS)){
            nextBatch();
        }

        batches.get(currentBatch).addVertices(vertexData);
    }

    public static int addTexture(Texture tex){
        if(batches.get(currentBatch).isFullOfTextures() || batches.get(currentBatch).isFull(44)){
            nextBatch();
        }

        int texID = batches.get(currentBatch).addTexture(tex);

        if (texID == -1) {
            nextBatch();
            texID = batches.get(currentBatch).addTexture(tex);
        }

        return texID;
    }

    public static int getNumberOfBatches(){
        return batches.size();
    }

    public static void finish(){
        for (int i = 0; i <= currentBatch; i++) {
            batches.get(i).finish();
        }
        currentBatch = 0;
    }

    public static void render(){
        for (int i = 0; i <= currentBatch; i++) {
            batches.get(i).render();
        }
        currentBatch = 0;
    }

    public static void finishAndRender(){
        for (int i = 0; i <= currentBatch; i++) {
            batches.get(i).finish();
            batches.get(i).render();
        }
        currentBatch = 0;
    }
}