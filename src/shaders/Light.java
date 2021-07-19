package shaders;

import org.lwjgl.util.vector.Vector3f;

public class Light {
    private Vector3f position;
    private Vector3f colour;
    public Light(Vector3f pos, Vector3f colour){
        this.position = pos;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void updatePos(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
