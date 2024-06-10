package com.example.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Buffer {
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int NORMAL_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATE_COMPONENT_COUNT = 2;


    public static FloatBuffer createVertexBuffer(final float[] positions, final float[] colors, final float[] normals, final float[] textureCoords) {
        FloatBuffer buffer;

        final int totalSize = positions.length + colors.length + normals.length + textureCoords.length;

        int positionsOffset = 0;
        int colorsOffset = 0;
        int normalsOffset = 0;
        int textureCoordsOffset = 0;

        buffer = ByteBuffer.allocateDirect(totalSize * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        final int numVertices = positions.length / POSITION_COMPONENT_COUNT;

        for (int i = 0; i < numVertices; i++) {
            buffer.put(positions, positionsOffset, POSITION_COMPONENT_COUNT);
            positionsOffset += POSITION_COMPONENT_COUNT;
            buffer.put(colors, colorsOffset, COLOR_COMPONENT_COUNT);
            colorsOffset += COLOR_COMPONENT_COUNT;
            buffer.put(normals, normalsOffset, NORMAL_COMPONENT_COUNT);
            normalsOffset += NORMAL_COMPONENT_COUNT;
            buffer.put(textureCoords, textureCoordsOffset, TEXTURE_COORDINATE_COMPONENT_COUNT);
            textureCoordsOffset += TEXTURE_COORDINATE_COMPONENT_COUNT;
        }

        buffer.position(0);

        return buffer;
    }
    public static int loadBuffer(FloatBuffer buffer){
        int vboHandle;

        final int[] bufferHandle = new int[1];
        GLES20.glGenBuffers(1, bufferHandle, 0);

        if (bufferHandle[0] == 0) {
            throw new RuntimeException("Error generating buffer handle.");
        }

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_FLOAT, buffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        vboHandle = bufferHandle[0];

        buffer.limit(0);
        buffer = null;

        return vboHandle;
    }
}
