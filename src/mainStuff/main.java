package mainStuff;

import Entities.Camera;
import Entities.Entity;
import Terrai.Terrain;
import Texture.ModelTexture;
import models.OBJLoader;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderStuff.DisplayMan;
import org.lwjgl.opengl.Display;
import renderStuff.Loader;
import models.RawModel;
import renderStuff.masterRenderer;
import shaders.Light;
import toolbox.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {
    public static void main(String[] args){
        DisplayMan.createDisplay();
        Loader loader = new Loader();
        RawModel model = OBJLoader.loadModel("tree", loader);
        RawModel grassModel = OBJLoader.loadModel("grassModel", loader);
        RawModel dragonModel = OBJLoader.loadModel("dragon", loader);
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grassTexModel = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel dragonTex = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("white")));
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        Light light = new Light(new Vector3f(10000,100000,10000),new Vector3f(1,1,1f));
       for (int i = 0; i < 500; i++){
           entities.add(new Entity(dragonTex, new Vector3f((int)(Math.random()*(1000+1000+1)+-1000), (int)(Math.random()*(1000+1000+1)+-1000), 0), 0, 0, 0, 2));
           entities.get(i).getModel().getTexture().setReflectivity(500);
           entities.get(i).getModel().getTexture().setShineDampler(500);
        }

        Camera camera = new Camera();
        masterRenderer renderer = new masterRenderer();

        while(!Display.isCloseRequested()) {
            camera.move();
            for (Entity entity : entities) {
                renderer.processEntity(entity);
                entity.increaseRotation(1f, 1f, 2f);
            }
            renderer.render(light, camera);
            DisplayMan.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayMan.closeDisplay();

    }
}
