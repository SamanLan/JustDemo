package saman.com.color.opengl.bean;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import saman.com.color.opengl.OpenglUtil;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/6 18:21<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class Square {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    static final int COORDS_PER_VERTEX = 3;
    // 用红色，绿色，蓝色和alpha（不透明度）值设置颜色
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    // 顶点
    private float vertices[] = {
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f
    };

    // 点的连接顺序，至于点的连接方式（哪些点连），自己参数决定。
    private short indices[] = {0,1,2,0,2,3};

    private final int vertexCount = vertices.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // 为了提高性能，通常将这些数组存放到 java.io 中定义的 Buffer 类中
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private final int program;

    public Square() {

        int vertexShader = OpenglUtil.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = OpenglUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 创建空的opengl程序
        program = GLES20.glCreateProgram();
        // 添加顶点着色器
        GLES20.glAttachShader(program, vertexShader);
        // 添加片段着色器
        GLES20.glAttachShader(program, fragmentShader);
        // 创建opengl es程序可执行文件
        GLES20.glLinkProgram(program);

        // float是4位
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // short是2位
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        vbb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        // 添加程序到opengl环境
        GLES20.glUseProgram(program);
        /**
         * OpenGL ES 提供一个成为”管道 Pipeline”的机制，这个管道定义了一些“开关”来控制 OpenGL ES 支持的某些功能，
         * 缺省情况这些功能是关闭的，如果需要使用 OpenGL ES 的这些功能，需要明确告知 OpenGL “管道”打开所需功能。
         * 要注意的使用完某个功能之后，要关闭这个功能以免影响后续操作：
         */
//        // 设置逆时针方法为面的“前面”
//        GLES20.glFrontFace(GL10.GL_CCW);
//        // 打开 忽略“后面”设置
//        GLES20.glEnable(GL10.GL_CULL_FACE);
//        // 明确指明“忽略“哪个面
//        GLES20.glCullFace(GL10.GL_BACK);

        //获取顶点着色器的vPosition成员
        int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        //启用三角形顶点
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        //获取片段着色器的vColor成员
        int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");

        //设置绘制三角形
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);

//        // 打开“顶点数组”设置
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_FLOAT, indexBuffer);
//        // 关闭“顶点数组”设置
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        // 关闭 忽略“后面”设置
//        GLES20.glDisable(GL10.GL_CULL_FACE);
    }
}
