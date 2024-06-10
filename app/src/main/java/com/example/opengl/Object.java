package com.example.opengl;

import android.content.Context;

public abstract class Object {
    protected Vertex vertex;
    protected Material material;

    Object(Vertex vertex, Material material) {
        this.vertex = vertex;
        this.material = material;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Material getMaterial() {
        return material;
    }
}
