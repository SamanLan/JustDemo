package saman.com.color.opengl.bean;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/6 18:21<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class Square {

    // 顶点
    private float vertices[] = {
            -1.0f,  1.0f, 0.0f,  // 0, Top Left
            -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
            1.0f, -1.0f, 0.0f,  // 2, Bottom Right
            1.0f,  1.0f, 0.0f,  // 3, Top Right
    };

    // 点的连接顺序，至于点的连接方式（哪些点连），自己参数决定。
    private short indices[] = {0,1,2,0,2,3};

    // 为了提高性能，通常将这些数组存放到 java.io 中定义的 Buffer 类中
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    public Square() {
        // float是4位
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // short是4位
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        vbb.order(ByteOrder.nativeOrder());
        indexBuffer = vbb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        /**
         * OpenGL ES 提供一个成为”管道 Pipeline”的机制，这个管道定义了一些“开关”来控制 OpenGL ES 支持的某些功能，
         * 缺省情况这些功能是关闭的，如果需要使用 OpenGL ES 的这些功能，需要明确告知 OpenGL “管道”打开所需功能。
         * 要注意的使用完某个功能之后，要关闭这个功能以免影响后续操作：
         */
        // 设置逆时针方法为面的“前面”
        gl.glFrontFace(GL10.GL_CCW);
        // 打开 忽略“后面”设置
        gl.glEnable(GL10.GL_CULL_FACE);
        // 明确指明“忽略“哪个面
        gl.glCullFace(GL10.GL_BACK);

        // 打开“顶点数组”设置
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_FLOAT, indexBuffer);
        // 关闭“顶点数组”设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        // 关闭 忽略“后面”设置
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
