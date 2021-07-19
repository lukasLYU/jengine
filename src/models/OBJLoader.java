package models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderStuff.Loader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class OBJLoader {
    public static RawModel loadModel(String fileName, Loader loader){
        FileReader fr = null;
        try {
            fr = new FileReader(new File("res/"+fileName+".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> verticies = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indicies = new ArrayList<Integer>();
        float[] verticiesArray = null;
        float[] normalsArray = null;
        float[] texturesArray = null;
        int[] indiecesArray = null;
        try {
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    verticies.add(vertex);
                }else if (line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(vertex);
                } else if (line.startsWith("f ")){
                    texturesArray = new float[verticies.size() * 2];
                    normalsArray = new float[verticies.size() * 3];
                    break;
                }
            }
            while(line!=null){
                if (!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vertex1, indicies, textures, normals, texturesArray, normalsArray);
                processVertex(vertex2, indicies, textures, normals, texturesArray, normalsArray);
                processVertex(vertex3, indicies, textures, normals, texturesArray, normalsArray);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        verticiesArray = new float[verticies.size()*3];
        indiecesArray = new int[indicies.size()];
        int vertexPointer = 0;
        for (Vector3f vertex:verticies){
            verticiesArray[vertexPointer++] = vertex.x;
            verticiesArray[vertexPointer++] = vertex.y;
            verticiesArray[vertexPointer++] = vertex.z;
        }
        for (int i=0; i<indicies.size(); i++){
            indiecesArray[i] = indicies.get(i);
        }
        return loader.loadToVAO(verticiesArray, texturesArray, normalsArray, indiecesArray);
    }
    private static void processVertex(String[] vertexData, List<Integer> indecies, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indecies.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
        textureArray[currentVertexPointer*2] = currentTex.x;
        textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalArray[currentVertexPointer*3] = currentNorm.x;
        normalArray[currentVertexPointer*3+1] = currentNorm.y;
        normalArray[currentVertexPointer*3+2] = currentNorm.z;
    }
}
