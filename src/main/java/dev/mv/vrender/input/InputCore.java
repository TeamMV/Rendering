package dev.mv.vrender.input;

import dev.mv.vrender.window.Window;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class InputCore {

    private Window window;

    private DoubleBuffer mxPos = BufferUtils.createDoubleBuffer(1), myPos = BufferUtils.createDoubleBuffer(1);
    private int xScroll = 0, yScroll = 0;

    public InputCore(Window window){
        this.window = window;

        glfwSetScrollCallback(window.getWindow(), new GLFWScrollCallbackI() {
            @Override
            public void invoke(long win, double xOffset, double yOffset) {
                xScroll = (int)xOffset;
                yScroll = (int)yOffset;
                window.onScroll(xScroll, yScroll);
            }
        });

        glfwSetMouseButtonCallback(window.getWindow(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long win, int button, int action, int mods) {
                window.onMouseAction(button, action, mods);
            }
        });

        glfwSetCursorPosCallback(window.getWindow(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long wind, double x, double y) {
                window.onMouseMove((int)x, window.getHeight() - (int)y);
            }
        });

        glfwSetKeyCallback(window.getWindow(), new GLFWKeyCallbackI() {
            @Override
            public void invoke(long win, int key, int scanCode, int action, int mods) {
                window.onKeyAction(key, scanCode, action, mods);
            }
        });
    }

    public boolean keyDown(int key){
        return glfwGetKey(window.getWindow(), key) == GL_TRUE;
    }

    public boolean mouseClick(int button){
        return glfwGetMouseButton(window.getWindow(), button - 1) == GL_TRUE;
    }

    public Vector2i mousePosition(){
        glfwGetCursorPos(window.getWindow(), mxPos, myPos);
        return new Vector2i((int)mxPos.get(0), window.getHeight() - (int)myPos.get(0));
    }

    public boolean mouseInside(int x, int y, int x2, int y2){
        glfwGetCursorPos(window.getWindow(), mxPos, myPos);
        int mx = (int)mxPos.get(0), my = window.getHeight() - (int)myPos.get(0);
        return (mx >= x && mx <= x2 &&
                my >= y && my <= y2);
    }

    public boolean scrollUp(){
        if (yScroll == 1){
            yScroll = 0;
            return true;
        }
        return false;
    }

    public boolean scrollDown(){
        if (yScroll == -1){
            yScroll = 0;
            return true;
        }
        return false;
    }
}
