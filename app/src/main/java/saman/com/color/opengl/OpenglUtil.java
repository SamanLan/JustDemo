package saman.com.color.opengl;

import android.opengl.GLES20;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/7 11:43<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class OpenglUtil {
    public static int loadShader(int type, String shaderCode){

        // 创建一个顶点着色器类型（gles20.gl_vertex_shader）
        // 或片段着色器类型（gles20.gl_fragment_shader）
        int shader = GLES20.glCreateShader(type);

        // 将源代码添加到着色器并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
