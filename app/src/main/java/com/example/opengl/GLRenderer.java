package com.example.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.FloatBuffer;

public class GLRenderer implements GLSurfaceView.Renderer {
    // Matrix for model, view, projection and light calculations
    private final float[] mModelMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mLightModelMatrix = new float[16];

    // Light position arrays
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];
    private final float[] mLightPosInModelSpace = new float[]{0.0f, 0.0f, 0.0f, 1.0f};

    // Buffers for vertex data
    FloatBuffer mVertexbuffer;

    // Shader program handles
    private int mPerVertexProgramHandle;
    private int mTextureDataHandle;
    private int mVertexbufferHandle;

    // Size of buffer
    private int mVertexbufferSize;

    // Context
    private Context mActivityContext;

    // Material
    private final Material material;

    // Car object
    private Car mcar;
    private int numOfCar;


    public GLRenderer(Context context, int numOfCar){
        mActivityContext = context;
        this.numOfCar = numOfCar;

        // Read file obj
        Vertex vertex = FileReader.readObjFile(context, "laurel.obj");

        // Read file mtl
        material = FileReader.readMtlFile(context, "");

        // Create car object
        mcar = new Car(vertex, material);

        // Create buffer
        mVertexbuffer = Buffer.createVertexBuffer(vertex.positions, vertex.colors, vertex.normals, vertex.textureCoords);
        mVertexbufferSize = mVertexbuffer.capacity();
    }



    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);

        // Use culling to remove back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        // Enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        final String vertexShader = ShaderProvider.getVertexShader();
        final String fragmentShader = ShaderProvider.getFragmentShader();

        int vertexShaderHandle = ShaderProvider.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        int fragmentShaderHandle = ShaderProvider.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mPerVertexProgramHandle = ShaderProvider.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});

        // Load the texture
        mTextureDataHandle = Texture.loadTexture(mActivityContext, R.drawable.image);

        // Load the buffer
        mVertexbufferHandle = Buffer.loadBuffer(mVertexbuffer);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, ratio, bottom, top, near, far);

        // Calculate position of the light.
        Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 1.0f);

        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        draw(numOfCar);
    }

    private void draw(int number){
        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        float scaleFactor = 0.6f;

        int start = -number/2;
        int end = number%2 == 0? number/2 : number/2 +1;

        for(int i=start; i<end; i++){
            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, 0.0f, (float) i, -3.5f);
            Matrix.scaleM(mModelMatrix, 0, scaleFactor, scaleFactor, scaleFactor);
            Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
            mcar.draw(mPerVertexProgramHandle, mTextureDataHandle,mVertexbufferHandle, mVertexbufferSize, mModelMatrix, mViewMatrix, mProjectionMatrix, mLightPosInEyeSpace);
        }
    }
}
