package com.example.opengl;

public class Vertex {
    public float[] positions;
    public float[] normals;
    public float[] textureCoords;
    public float[] colors;

    // Constructor
    Vertex(float[] positions, float[] normals, float[] textureCoords, float[] colors){
        this.positions = positions;
        this.normals = normals;
        this.textureCoords = textureCoords;
        this.colors = colors;
    }

}
