package dev.mv.vrender.shader;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private String vertexCode;
    private String fragmentCode;
    @Getter
    @Setter
    private int vertexShader;
    @Getter
    @Setter
    private int fragmentShader;
    @Getter
    @Setter
    private int programID;

    public Shader(String vertexShader, String fragmentShader) {
        this.vertexCode = loadShaderFile(vertexShader);
        this.fragmentCode = loadShaderFile(fragmentShader);
    }

    private static String loadShaderFile(String fileStream) {
        try {
            return new String(Shader.class.getResourceAsStream(fileStream).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        if (!(new File(file).exists())) {
            System.out.println("Could not load file: \"" + file + "\"!");
            return null;
        }
        StringBuilder string = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                string.append(line);
                string.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(string.toString());
        return string.toString();
        */
    }

    public void make() {
        this.programID = glCreateProgram();

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, this.vertexCode);
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
            System.out.println("vertex shader error: " + glGetShaderInfoLog(vertexShader));
            return;
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, this.fragmentCode);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
            System.out.println("fragment shader error: " + glGetShaderInfoLog(fragmentShader));
            return;
        }
    }

    public void use() {
        glAttachShader(this.programID, vertexShader);
        glAttachShader(this.programID, fragmentShader);

        glBindAttribLocation(this.programID, 0, "vertices");
        glLinkProgram(this.programID);
        if ((glGetProgrami(this.programID, GL_LINK_STATUS)) != 1) {
            System.out.println("link program error: " + glGetProgramInfoLog(this.programID));
            return;
        }
        glValidateProgram(this.programID);
        if ((glGetProgrami(this.programID, GL_VALIDATE_STATUS)) != 1) {
            System.out.println("link program error: " + glGetProgramInfoLog(this.programID));
            return;
        }

        glUseProgram(this.programID);
    }

    public int getProgramID() {
        return this.programID;
    }

    public void setUniform1f(String name, float value) {
        int location = glGetUniformLocation(this.programID, name);
        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    public void setUniform1i(String name, int value) {
        int location = glGetUniformLocation(this.programID, name);
        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    public void setUniform1iv(String name, int[] value) {
        int location = glGetUniformLocation(this.programID, name);
        if (location != -1) {
            glUniform1iv(location, value);
        }
    }

    public void setMatrix4f(String name, Matrix4f value) {
        int location = glGetUniformLocation(this.programID, name);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        value.get(matBuffer);
        glUniformMatrix4fv(location, false, matBuffer);
    }
}
