package shaders;

import Entities.Camera;
import org.lwjgl.util.vector.Matrix4f;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;


public class StaticShader extends ShaderHandler{


    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyCol;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getAllUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getAllUniformLocation("projectionMatrix");
        location_viewMatrix = super.getAllUniformLocation("viewMatrix");
        location_lightPosition = super.getAllUniformLocation("lightPosition");
        location_lightColour = super.getAllUniformLocation("lightColour");
        location_shineDamper = super.getAllUniformLocation("shineDamper");
        location_reflectivity = super.getAllUniformLocation("reflectivity");
        location_skyCol = super.getAllUniformLocation("skyCol");
    }
    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyCol, new Vector3f(r,g,b));

    }
    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }


}
