package com.example.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Car extends Object {
    Car(Vertex vertex, Material material) {
        super(vertex, material);
    }

    public void draw(int vertexProgramHandle, int textureDataHandle, int vertexBufferHandle, int vertexbufferSize, float[] modelMatrix, float[] viewMatrix, float[] projectionMatrix, float[] lightPosInEyeSpace) {
        final int POSITION_DATA_SIZE = 3;
        final int COLOR_DATA_SIZE = 4;
        final int NORMAL_DATA_SIZE = 3;
        final int TEXTURE_COORDINATE_DATA_SIZE = 2;
        final int BYTES_PER_FLOAT = 4;
        final int STRIDE = (POSITION_DATA_SIZE + COLOR_DATA_SIZE + NORMAL_DATA_SIZE + TEXTURE_COORDINATE_DATA_SIZE) * 4;
        final int TEXTURE_UNIT = GLES20.GL_TEXTURE0;
        
        GLES20.glUseProgram(vertexProgramHandle);

        Handle handles = new Handle(vertexProgramHandle);

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(TEXTURE_UNIT);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(handles.textureUniformHandle, 0);

        // Bind the buffer
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferHandle);

        // Position data
        GLES20.glEnableVertexAttribArray(handles.positionHandle);
        GLES20.glVertexAttribPointer(handles.positionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, STRIDE, 0);

        // Color data
        GLES20.glEnableVertexAttribArray(handles.colorHandle);
        GLES20.glVertexAttribPointer(handles.colorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, STRIDE, POSITION_DATA_SIZE * BYTES_PER_FLOAT);

        // Normal data
        GLES20.glEnableVertexAttribArray(handles.normalHandle);
        GLES20.glVertexAttribPointer(handles.normalHandle, NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, STRIDE, (POSITION_DATA_SIZE + COLOR_DATA_SIZE) * BYTES_PER_FLOAT);

        // Texture coordinate data
        GLES20.glEnableVertexAttribArray(handles.textureCoordinateHandle);
        GLES20.glVertexAttribPointer(handles.textureCoordinateHandle, TEXTURE_COORDINATE_DATA_SIZE, GLES20.GL_FLOAT, false, STRIDE, (POSITION_DATA_SIZE + COLOR_DATA_SIZE + NORMAL_DATA_SIZE) * BYTES_PER_FLOAT);

        // Unbind the buffer
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glUniformMatrix4fv(handles.mMatrixHandle, 1, false, modelMatrix, 0);
        GLES20.glUniformMatrix4fv(handles.vMatrixHandle, 1, false, viewMatrix, 0);
        GLES20.glUniformMatrix4fv(handles.pMatrixHandle, 1, false, projectionMatrix, 0);

        GLES20.glUniform3f(handles.lightPosHandle, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);
        GLES20.glUniform3fv(handles.kaHandle, 1, this.material.Ka, 0);
        GLES20.glUniform3fv(handles.kdHandle, 1, material.Kd, 0);
        GLES20.glUniform3fv(handles.ksHandle, 1, material.Ks, 0);
        GLES20.glUniform1f(handles.nsHandle, material.Ns);
        GLES20.glUniform1f(handles.trHandle, material.Tr);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexbufferSize / (POSITION_DATA_SIZE + COLOR_DATA_SIZE + NORMAL_DATA_SIZE + TEXTURE_COORDINATE_DATA_SIZE));
    }
}
