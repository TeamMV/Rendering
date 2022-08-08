package dev.mv.vrender.render;

import dev.mv.vrender.shader.Shader;
import dev.mv.vrender.texture.Texture;
import lombok.Getter;
import org.lwjgl.BufferUtils;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Batch {
    private int maxSize;
    private float[] data;
    private int[] indices;
    private Texture[] textures;

    @Getter
    private Shader shader;

    private FloatBuffer vbo;
    private int vbo_id;
    private IntBuffer ibo;
    private int ibo_id;
    private int[] tex_ids;

    /**
     * The var vertCount is the offset pointer for the incoming data,
     * therefor no data gets overridden.
     * For clearing this var, use clearBatch().
     */

    private int vertCount = 0;
    private int objCount = 0;
    private int nextFreeTexSlot = 0;

    //f, f, f (pos), f, f, f, f (col), f, f (uv), f (texID)

    private final int POSITION_SIZE = 3;
    private final int POSITION_OFFSET = 0;
    private final int POSITION_OFFSET_BYTES = POSITION_OFFSET * Float.BYTES;

    private final int COLOR_SIZE = 4;
    private final int COLOR_OFFSET = POSITION_SIZE;
    private final int COLOR_OFFSET_BYTES = COLOR_OFFSET * Float.BYTES;

    private final int UV_SIZE = 2;
    private final int UV_OFFSET = POSITION_SIZE + COLOR_SIZE;
    private final int UV_OFFSET_BYTES = UV_OFFSET * Float.BYTES;

    private final int TEX_ID_SIZE = 1;
    private final int TEX_ID_OFFSET = POSITION_SIZE + COLOR_SIZE + UV_SIZE;
    private final int TEX_ID_OFFSET_BYTES = TEX_ID_OFFSET * Float.BYTES;

    private final int VERTEX_SIZE_FLOATS = POSITION_SIZE + COLOR_SIZE + UV_SIZE + TEX_ID_SIZE;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE_FLOATS * Float.BYTES;

    public Batch(int maxSize){
        this.maxSize = maxSize;
        initBatch();
    }

    private void initBatch(){
        data = new float[VERTEX_SIZE_FLOATS * maxSize];
        indices = new int[maxSize * 6];
        textures = new Texture[GL_MAX_TEXTURE_UNITS - 1];

        shader = new Shader("src/shaders/default.vert", "src/shaders/default.frag");

        shader.make();
        shader.use();

        vbo = BufferUtils.createFloatBuffer(VERTEX_SIZE_BYTES * maxSize);
        vbo_id = glGenBuffers();

        ibo = BufferUtils.createIntBuffer(maxSize * 6);
        ibo_id = glGenBuffers();

        tex_ids = new int[GL_MAX_TEXTURE_UNITS - 1];
    }

    /**
     * Importtant Note:
     * This method does not really clear the data of this batch, it just sets the data offset back to 0.
     * With this change, the batch gets overridden if new data comes in.
     * This is better for performance than actually clearing the array.
     * for really clearing the data, use forceClearBatch().
     */

    public void clearBatch(){
        vertCount = 0;
        objCount = 0;
        nextFreeTexSlot = 0;
    }

    /**
     * This method clears the actual data of the data array.
     * But keep in mind, that this uses some performance and for only resetting the data
     * offset, use clearBatch().
     */

    public  void forceClearBatch(){
        data = new float[VERTEX_SIZE_FLOATS * maxSize];
        vertCount = 0;
        objCount = 0;
        nextFreeTexSlot = 0;
    }

    private void addVertex(float[] vertData){
        for(int i = 0; i < VERTEX_SIZE_FLOATS; i++){
            data[i + (vertCount * VERTEX_SIZE_FLOATS)] = vertData[i];
        }
        vertCount++;
    }

    public void addVertices(float[][] vertData){
        if(vertData.length == 4){
            indices[objCount * 6 + 0] = 0 + objCount * 4;
            indices[objCount * 6 + 1] = 1 + objCount * 4;
            indices[objCount * 6 + 2] = 2 + objCount * 4;
            indices[objCount * 6 + 3] = 0 + objCount * 4;
            indices[objCount * 6 + 4] = 2 + objCount * 4;
            indices[objCount * 6 + 5] = 3 + objCount * 4;
        }else{
            indices[objCount * 6 + 0] = 0 + objCount * 4;
            indices[objCount * 6 + 1] = 1 + objCount * 4;
            indices[objCount * 6 + 2] = 2 + objCount * 4;
        }

        for(float[] vertex : vertData){
            addVertex(vertex);
        }
        if(vertData.length < 4){
            addVertex(vertData[0]);
        }

        objCount++;
    }

    public void addTexture(Texture tex){
        for(Texture texture : textures){
            if(texture == tex){
                return;
            }
        }
        textures[nextFreeTexSlot] = tex;
        tex_ids[nextFreeTexSlot] = tex.getID();
        nextFreeTexSlot++;
        tex.bind();
    }

    public void finish(){
        vbo.put(data);
        ibo.put(indices);
        vbo.flip();
        ibo.flip();
        clearBatch();
    }

    public void render(){
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, POSITION_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POSITION_OFFSET_BYTES);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET_BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, UV_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, UV_OFFSET_BYTES);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET_BYTES);
        glEnableVertexAttribArray(3);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}