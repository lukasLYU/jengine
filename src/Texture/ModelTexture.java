package Texture;

public class ModelTexture {
    private int textureID;
    private float shineDampler = 1;
    private float reflectivity = 0;
    public ModelTexture(int id){
        this.textureID = id;
    }
    public int getTextureID(){
        return textureID;
    }

    public float getShineDampler() {
        return shineDampler;
    }

    public void setShineDampler(float shineDampler) {
        this.shineDampler = shineDampler;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
