package com.leedarson.newui.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import androidx.work.Data;
import com.leedarson.R$raw;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import meshsdk.cache.CacheHandler;

/* compiled from: MyRender */
public class u implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Context a;
    private final float[] b;
    private final float[] c;
    private FloatBuffer d;
    private FloatBuffer e;
    private int f = 1;
    private b g;
    private a h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int[] o;
    private int p;
    private int q;
    private ByteBuffer r;
    private ByteBuffer s;
    private ByteBuffer t;

    /* compiled from: MyRender */
    public interface a {
        void a();
    }

    /* compiled from: MyRender */
    public interface b {
    }

    public u(Context context) {
        float[] fArr = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        this.b = fArr;
        float[] fArr2 = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        this.c = fArr2;
        this.a = context;
        FloatBuffer put = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr);
        this.d = put;
        put.position(0);
        FloatBuffer put2 = ByteBuffer.allocateDirect(fArr2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr2);
        this.e = put2;
        put2.position(0);
    }

    public void setOnSurfaceCreateListener(b onSurfaceCreateListener) {
        this.g = onSurfaceCreateListener;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        Class[] clsArr = {GL10.class, EGLConfig.class};
        if (!PatchProxy.proxy(new Object[]{gl10, eGLConfig}, this, changeQuickRedirect, false, 5196, clsArr, Void.TYPE).isSupported) {
            a();
        }
    }

    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Object[] objArr = {gl10, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5197, new Class[]{GL10.class, cls, cls}, Void.TYPE).isSupported) {
            GLES20.glViewport(0, 0, width, height);
        }
    }

    public void onDrawFrame(GL10 gl10) {
        if (!PatchProxy.proxy(new Object[]{gl10}, this, changeQuickRedirect, false, 5198, new Class[]{GL10.class}, Void.TYPE).isSupported) {
            GLES20.glClear(16384);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            b();
            GLES20.glDrawArrays(5, 0, 4);
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        a aVar;
        if (!PatchProxy.proxy(new Object[]{surfaceTexture}, this, changeQuickRedirect, false, 5199, new Class[]{SurfaceTexture.class}, Void.TYPE).isSupported && (aVar = this.h) != null) {
            aVar.a();
        }
    }

    public void setOnRenderListener(a onRenderListener) {
        this.h = onRenderListener;
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5200, new Class[0], Void.TYPE).isSupported) {
            int a2 = v.a(v.c(this.a, R$raw.vertex_shader), v.c(this.a, R$raw.fragment_yuv));
            this.i = a2;
            this.j = GLES20.glGetAttribLocation(a2, "av_Position");
            this.k = GLES20.glGetAttribLocation(this.i, "af_Position");
            this.l = GLES20.glGetUniformLocation(this.i, "sampler_y");
            this.m = GLES20.glGetUniformLocation(this.i, "sampler_u");
            this.n = GLES20.glGetUniformLocation(this.i, "sampler_v");
            int[] iArr = new int[3];
            this.o = iArr;
            GLES20.glGenTextures(3, iArr, 0);
            for (int i2 = 0; i2 < 3; i2++) {
                GLES20.glBindTexture(3553, this.o[i2]);
                GLES20.glTexParameteri(3553, 10242, 10497);
                GLES20.glTexParameteri(3553, 10243, 10497);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, Data.MAX_DATA_BYTES, 9729);
            }
        }
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, CacheHandler.WHAT_GET_DETECTION_MDOE_DETAIL, new Class[0], Void.TYPE).isSupported) {
            if (this.p > 0 && this.q > 0 && this.r != null) {
                if ((this.s != null) && (this.t != null)) {
                    GLES20.glUseProgram(this.i);
                    GLES20.glEnableVertexAttribArray(this.j);
                    GLES20.glVertexAttribPointer(this.j, 2, 5126, false, 8, this.d);
                    GLES20.glEnableVertexAttribArray(this.k);
                    GLES20.glVertexAttribPointer(this.k, 2, 5126, false, 8, this.e);
                    GLES20.glActiveTexture(33984);
                    GLES20.glBindTexture(3553, this.o[0]);
                    GLES20.glTexImage2D(3553, 0, 6409, this.p, this.q, 0, 6409, 5121, this.r);
                    GLES20.glActiveTexture(33985);
                    GLES20.glBindTexture(3553, this.o[1]);
                    GLES20.glTexImage2D(3553, 0, 6409, this.p / 2, this.q / 2, 0, 6409, 5121, this.s);
                    GLES20.glActiveTexture(33986);
                    GLES20.glBindTexture(3553, this.o[2]);
                    GLES20.glTexImage2D(3553, 0, 6409, this.p / 2, this.q / 2, 0, 6409, 5121, this.t);
                    GLES20.glUniform1i(this.l, 0);
                    GLES20.glUniform1i(this.m, 1);
                    GLES20.glUniform1i(this.n, 2);
                    this.r.clear();
                    this.s.clear();
                    this.t.clear();
                    this.r = null;
                    this.s = null;
                    this.t = null;
                }
            }
        }
    }
}
