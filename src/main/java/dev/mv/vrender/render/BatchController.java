package dev.mv.vrender.render;

import dev.mv.vrender.texture.Texture;
import dev.mv.vrender.window.Window;

import java.util.ArrayList;
import java.util.List;

public class BatchController{

    private static Window win;
    private static int maxBatchSize;
    private static List<Batch> batches = new ArrayList<>();

    public static void init(Window window, int batchLimit){

        if(batchLimit < 4){
            throw new IllegalArgumentException("Batch limit of " + batchLimit + " is too small, at least 4 is required!");
        }

        win = window;
        maxBatchSize = batchLimit;
        batches.add(new Batch(batchLimit, window));
    }

    public static void addVertices(float[][] vertexData){
        if(batches.get(batches.size() - 1).isFull()){
            batches.add(new Batch(maxBatchSize, win));
        }

        batches.get(batches.size() - 1).addVertices(vertexData);
    }

    public static void addTexture(Texture tex){
        if(batches.get(batches.size() - 1).isFullOfTextures()){
            batches.add(new Batch(maxBatchSize, win));
        }

        batches.get(batches.size() - 1).addTexture(tex);
    }

    public static int getNumberOfBatches(){
        return batches.size();
    }

    public static void finish(){
        for(Batch batch : batches){
            batch.finish();
        }
        batches.clear();
        batches.add(new Batch(maxBatchSize, win));
    }

    public static void render(){
        for(Batch batch : batches){
            batch.render();
        }
        batches.clear();
        batches.add(new Batch(maxBatchSize, win));
    }

    public static void finishAndRender(){
        for(Batch batch : batches){
            batch.finish();
            batch.render();
        }
        batches.clear();
        batches.add(new Batch(maxBatchSize, win));
    }
}
