package com.leedarson.newui.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.leedarson.newui.view.u;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class MyGLSurfaceView extends GLSurfaceView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private u c;

    public MyGLSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        u uVar = new u(context);
        this.c = uVar;
        setRenderer(uVar);
        setRenderMode(0);
        this.c.setOnRenderListener(new a());
    }

    public class a implements u.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5195, new Class[0], Void.TYPE).isSupported) {
                MyGLSurfaceView.this.requestRender();
            }
        }
    }

    public u getRender() {
        return this.c;
    }
}
