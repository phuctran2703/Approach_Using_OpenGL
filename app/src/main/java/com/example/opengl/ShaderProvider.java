package com.example.opengl;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderProvider {
    public static String getVertexShader() {
        return "uniform mat4 u_mMatrix;      \n" +
                "uniform mat4 u_vMatrix;      \n" +
                "uniform mat4 u_pMatrix;      \n" +
                "uniform vec3 u_LightPos;      \n" +

                "attribute vec4 a_Position;     \n" +
                "attribute vec4 a_Color;     \n" +
                "attribute vec3 a_Normal;     \n" +
                "attribute vec2 a_TexCoordinate; \n" +

                "varying vec4 v_Color;          \n" +
                "varying vec3 v_modelViewVertex; \n" +
                "varying vec3 v_modelViewNormal; \n" +
                "varying vec3 v_LightPos; \n" +
                "varying vec2 v_TexCoordinate; \n" +

                // Function to calculate the transpose of a 4x4 matrix
                "mat4 transpose(mat4 m) {\n" +
                "    return mat4(\n" +
                "        vec4(m[0][0], m[1][0], m[2][0], m[3][0]),\n" +
                "        vec4(m[0][1], m[1][1], m[2][1], m[3][1]),\n" +
                "        vec4(m[0][2], m[1][2], m[2][2], m[3][2]),\n" +
                "        vec4(m[0][3], m[1][3], m[2][3], m[3][3])\n" +
                "    );\n" +
                "}" +

                // Function to calculate the inverse of a 4x4 matrix
                "mat4 inverse(mat4 m) {\n" +
                "    float det = \n" +
                "        m[0][0] * (m[1][1] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                   m[2][1] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) + \n" +
                "                   m[3][1] * (m[1][2] * m[2][3] - m[2][2] * m[1][3])) -\n" +
                "        m[0][1] * (m[1][0] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                   m[2][0] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) + \n" +
                "                   m[3][0] * (m[1][2] * m[2][3] - m[2][2] * m[1][3])) +\n" +
                "        m[0][2] * (m[1][0] * (m[2][1] * m[3][3] - m[3][1] * m[2][3]) - \n" +
                "                   m[2][0] * (m[1][1] * m[3][3] - m[3][1] * m[1][3]) + \n" +
                "                   m[3][0] * (m[1][1] * m[2][3] - m[2][1] * m[1][3])) -\n" +
                "        m[0][3] * (m[1][0] * (m[2][1] * m[3][2] - m[3][1] * m[2][2]) - \n" +
                "                   m[2][0] * (m[1][1] * m[3][2] - m[3][1] * m[1][2]) + \n" +
                "                   m[3][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2]));\n" +
                "                   \n" +
                "    mat4 inv;\n" +
                "    inv[0][0] =  (m[1][1] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                  m[2][1] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) + \n" +
                "                  m[3][1] * (m[1][2] * m[2][3] - m[2][2] * m[1][3])) / det;\n" +
                "    inv[0][1] = -(m[0][1] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                  m[2][1] * (m[0][2] * m[3][3] - m[3][2] * m[0][3]) + \n" +
                "                  m[3][1] * (m[0][2] * m[2][3] - m[2][2] * m[0][3])) / det;\n" +
                "    inv[0][2] =  (m[0][1] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) - \n" +
                "                  m[1][1] * (m[0][2] * m[3][3] - m[3][2] * m[0][3]) + \n" +
                "                  m[3][1] * (m[0][2] * m[1][3] - m[1][2] * m[0][3])) / det;\n" +
                "    inv[0][3] = -(m[0][1] * (m[1][2] * m[2][3] - m[2][2] * m[1][3]) - \n" +
                "                  m[1][1] * (m[0][2] * m[2][3] - m[2][2] * m[0][3]) + \n" +
                "                  m[2][1] * (m[0][2] * m[1][3] - m[1][2] * m[0][3])) / det;\n" +
                "    inv[1][0] = -(m[1][0] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                  m[2][0] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) + \n" +
                "                  m[3][0] * (m[1][2] * m[2][3] - m[2][2] * m[1][3])) / det;\n" +
                "    inv[1][1] =  (m[0][0] * (m[2][2] * m[3][3] - m[3][2] * m[2][3]) - \n" +
                "                  m[2][0] * (m[0][2] * m[3][3] - m[3][2] * m[0][3]) + \n" +
                "                  m[3][0] * (m[0][2] * m[2][3] - m[2][2] * m[0][3])) / det;\n" +
                "    inv[1][2] = -(m[0][0] * (m[1][2] * m[3][3] - m[3][2] * m[1][3]) - \n" +
                "                  m[1][0] * (m[0][2] * m[3][3] - m[3][2] * m[0][3]) + \n" +
                "                  m[3][0] * (m[0][2] * m[1][3] - m[1][2] * m[0][3])) / det;\n" +
                "    inv[1][3] =  (m[0][0] * (m[1][2] * m[2][3] - m[2][2] * m[1][3]) - \n" +
                "                  m[1][0] * (m[0][2] * m[2][3] - m[2][2] * m[0][3]) + \n" +
                "                  m[2][0] * (m[0][2] * m[1][3] - m[1][2] * m[0][3])) / det;\n" +
                "    inv[2][0] =  (m[1][0] * (m[2][1] * m[3][3] - m[3][1] * m[2][3]) - \n" +
                "                  m[2][0] * (m[1][1] * m[3][3] - m[3][1] * m[1][3]) + \n" +
                "                  m[3][0] * (m[1][1] * m[2][3] - m[2][1] * m[1][3])) / det;\n" +
                "    inv[2][1] = -(m[0][0] * (m[2][1] * m[3][3] - m[3][1] * m[2][3]) - \n" +
                "                  m[2][0] * (m[0][1] * m[3][3] - m[3][1] * m[0][3]) + \n" +
                "                  m[3][0] * (m[0][1] * m[2][3] - m[2][1] * m[0][3])) / det;\n" +
                "    inv[2][2] =  (m[0][0] * (m[1][1] * m[3][3] - m[3][1] * m[1][3]) - \n" +
                "                  m[1][0] * (m[0][1] * m[3][3] - m[3][1] * m[0][3]) + \n" +
                "                  m[3][0] * (m[0][1] * m[1][3] - m[1][1] * m[0][3])) / det;\n" +
                "    inv[2][3] = -(m[0][0] * (m[1][1] * m[2][3] - m[2][1] * m[1][3]) + \n" +
                "                  m[1][0] * (m[0][1] * m[2][3] - m[2][1] * m[0][3]) - \n" +
                "                  m[2][0] * (m[0][1] * m[1][3] - m[1][1] * m[0][3])) / det;\n" +
                "    inv[3][0] = -(m[1][0] * (m[2][1] * m[3][2] - m[3][1] * m[2][2]) + \n" +
                "                  m[2][0] * (m[1][1] * m[3][2] - m[3][1] * m[1][2]) - \n" +
                "                  m[3][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2])) / det;\n" +
                "    inv[3][1] =  (m[0][0] * (m[2][1] * m[3][2] - m[3][1] * m[2][2]) + \n" +
                "                  m[2][0] * (m[0][1] * m[3][2] - m[3][1] * m[0][2]) - \n" +
                "                  m[3][0] * (m[0][1] * m[2][2] - m[2][1] * m[0][2])) / det;\n" +
                "    inv[3][2] = -(m[0][0] * (m[1][1] * m[3][2] - m[3][1] * m[1][2]) + \n" +
                "                  m[1][0] * (m[0][1] * m[3][2] - m[3][1] * m[0][2]) - \n" +
                "                  m[3][0] * (m[0][1] * m[1][2] - m[1][1] * m[0][2])) / det;\n" +
                "    inv[3][3] =  (m[0][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2]) - \n" +
                "                  m[1][0] * (m[0][1] * m[2][2] - m[2][1] * m[0][2]) + \n" +
                "                  m[2][0] * (m[0][1] * m[1][2] - m[1][1] * m[0][2])) / det;\n" +
                "    return inv;\n" +
                "}\n" +

                "void main()                    \n" +
                "{                              \n" +
                "   mat4 MVMatrix = u_vMatrix * u_mMatrix;      \n" +
                "   mat4 MVPMatrix = u_pMatrix * u_vMatrix * u_mMatrix;      \n" +
                "   mat4 MVMatrixInvTrans = transpose(inverse(MVMatrix));      \n" +
                "   v_modelViewVertex = vec3(MVMatrix * a_Position); \n" +
                "   v_modelViewNormal = normalize(vec3(MVMatrixInvTrans * vec4(a_Normal, 0.0))); \n" +
                "   v_LightPos = u_LightPos; \n" +
                "   v_Color = a_Color*vec4(1.0,1.0,1.0,1.0); \n" +
                "   v_TexCoordinate = a_TexCoordinate;\n" +
                "   gl_Position = MVPMatrix * a_Position;   \n" +
                "}                              \n";
    }

    public static String getFragmentShader() {
        return "precision mediump float;       \n" +
                "varying vec4 v_Color;          \n" +
                "varying vec3 v_modelViewVertex; \n" +
                "varying vec3 v_modelViewNormal; \n" +
                "varying vec3 v_LightPos; \n" +
                "varying vec2 v_TexCoordinate; \n" +

                "uniform sampler2D u_Texture; \n" +
                "uniform vec3 u_Ka; \n" +
                "uniform vec3 u_Kd; \n" +
                "uniform vec3 u_Ks; \n" +
                "uniform float u_Ns; \n" +
                "uniform float u_Tr; \n" +

                "void main()                    \n" +
                "{                              \n" +
//          ambient light
                "   vec3 ambientLight = u_Ka; \n" +

//          diffuse light
                "   float distance = length(v_LightPos - v_modelViewVertex); \n" +
                "   vec3 lightVector = normalize(v_LightPos - v_modelViewVertex); \n" +
                "   float diffuse = max(dot(lightVector, normalize(v_modelViewNormal)), 0.0); \n" +
                "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance))); \n" +
                "   vec3 diffuseLight = u_Kd * diffuse;\n" +

//          specular light
                "   lightVector = -lightVector; \n" +
                "   vec3 reflectVector = normalize(reflect(lightVector,v_modelViewNormal)); \n" +
                "   vec3 posToViewVector = -normalize(v_modelViewVertex); \n" +
                "   float specular = pow(max(dot(posToViewVector, reflectVector), 0.0), u_Ns); \n" +
                "   vec3 specularLight = u_Ks * specular; \n" +

//                final light
                "   vec4 lightColor = vec4(ambientLight,1.0) + vec4(diffuseLight,1.0) + vec4(specularLight,1.0); \n" +
                "   lightColor.a = 1.0 - u_Tr; \n" +
                "   gl_FragColor = v_Color * lightColor * texture2D(u_Texture, v_TexCoordinate); \n" +
                "}                              \n";
    }
    public static int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0) {
            GLES20.glShaderSource(shaderHandle, shaderSource);
            GLES20.glCompileShader(shaderHandle);

            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            if (compileStatus[0] == 0) {
                Log.e("GLRenderer2", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0) {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    public static int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0) {
            GLES20.glAttachShader(programHandle, vertexShaderHandle);
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            if (attributes != null) {
                for (int i = 0; i < attributes.length; i++) {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            GLES20.glLinkProgram(programHandle);

            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == 0) {
                Log.e("GLRenderer2", "Error linking program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }
}