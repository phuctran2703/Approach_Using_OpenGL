package com.example.opengl;

public class Material {
    public float[] Ka = new float[3];
    public float[] Kd = new float[3];
    public float[] Ks = new float[3];
    public float Tr;
    public float Ns;
    public float illum;

    Material(){}

    Material(float[] Ka, float[] Kd, float[] Ks, float Tr, float Ns, int illum){
        this.Ka = Ka;
        this.Kd = Kd;
        this.Ks = Ks;
        this.Tr = Tr;
        this.Ns = Ns;
        this.illum = illum;
    }
}
