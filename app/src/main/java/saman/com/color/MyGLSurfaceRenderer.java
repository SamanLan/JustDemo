package saman.com.color;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import saman.com.color.opengl.bean.Square;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/6 17:32<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class MyGLSurfaceRenderer implements GLSurfaceView.Renderer {
    /**
     * 在这个方法中主要用来设置一些绘制时不常变化的 参数，比如：背景色，是否打开 z-buffer 等。
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
//        // Enable Smooth Shading光滑的底纹, default not really needed.
//        gl.glShadeModel(GL10.GL_SMOOTH);
//        // Depth buffer setup.  深度缓冲设置
//        gl.glClearDepthf(1.0f);
//        // Enables depth testing.
//        gl.glEnable(GL10.GL_DEPTH_TEST);
//        // The type of depth testing to do.
//        gl.glDepthFunc(GL10.GL_LEQUAL);
//        // Really nice perspective calculations.
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        square = new Square();
    }

    /**
     * 如果设备支持屏幕横向和纵向切换，这个方法将发 生在横向<->纵向互换时。此时可以重新设置绘制的纵横比率
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //调整屏幕比例
        gl.glViewport(0, 0, width, height);
        //将矩阵设置为投影模式
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //将矩阵重置为默认状态
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        //应用投影矩阵
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
    }

    private Square square;

    /**
     * 定义实际的绘图操作
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        //设置GL_MODELVIEW转换模式
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //将矩阵重置为默认状态
        gl.glLoadIdentity();
        //使用GL_MODELVIEW时，必须设置相机视图
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Clears the screen and depth buffer.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//        GLES20.glLoadIdentity();
//        GLES20.glTranslatef(0, 0, -4);
        square.draw(gl);
    }
}
