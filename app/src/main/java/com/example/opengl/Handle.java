package com.example.opengl;

import android.opengl.GLES20;

public class Handle {
    public int mMatrixHandle;
    public int vMatrixHandle;
    public int pMatrixHandle;
    public int positionHandle;
    public int lightPosHandle;
    public int colorHandle;
    public int normalHandle;
    public int textureCoordinateHandle;
    public int kaHandle;
    public int kdHandle;
    public int ksHandle;
    public int nsHandle;
    public int trHandle;
    public int textureUniformHandle;
    
    Handle(int ProgramHandle){
        GLES20.glUseProgram(ProgramHandle);

        mMatrixHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_mMatrix");
        vMatrixHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_vMatrix");
        pMatrixHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_pMatrix");
        lightPosHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_LightPos");
        positionHandle = GLES20.glGetAttribLocation(ProgramHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(ProgramHandle, "a_Color");
        normalHandle = GLES20.glGetAttribLocation(ProgramHandle, "a_Normal");
        textureCoordinateHandle = GLES20.glGetAttribLocation(ProgramHandle, "a_TexCoordinate");
        kaHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Ka");
        kdHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Kd");
        ksHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Ks");
        nsHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Ns");
        trHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Tr");
        textureUniformHandle = GLES20.glGetUniformLocation(ProgramHandle, "u_Texture");
    }
}
