package dev.mv.vrender.camera;

import dev.mv.vrender.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;

public class Camera {
    public Vector2f position;
    public boolean isStatic;
    public float zoom = 1.0f;
    public float moveSpeed = 1.0f;
    private Matrix4f projectionMatrix, viewMatrix, zoomMatrix;
    private Window window;

    public Camera(Vector2f position, Window window) {
        this.position = position;
        this.window = window;
        isStatic = false;

        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        zoomMatrix = new Matrix4f();

        declareProjection();
    }

    public Camera(Vector2f position, Window window, boolean isStatic) {
        this.position = position;
        this.window = window;
        this.isStatic = isStatic;

        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        zoomMatrix = new Matrix4f();

        declareProjection();
    }

    public void declareProjection() {
        float[] mat = new float[16];
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, (float) window.getWidth(), 0.0f, (float) window.getHeight(), 0.0f, 100.0f);
        Vector4f vec = new Vector4f(750, 0, 0, 1);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.m00(1).m01(0).m02(0).m03(position.x);
        viewMatrix.m10(0).m11(1).m12(0).m13(position.y);
        viewMatrix.m20(0).m21(0).m22(1).m23(0);
        viewMatrix.m20(0).m21(0).m22(0).m23(1);
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getZoomMatrix() {
        zoomMatrix.set(zoom, 0, 0, 0, 0, zoom, 0, 0, 0, 0, zoom, 0, 0, 0, 0, 1);

        return zoomMatrix;
    }
}
