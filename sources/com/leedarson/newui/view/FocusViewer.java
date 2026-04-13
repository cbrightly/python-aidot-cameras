package com.leedarson.newui.view;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class FocusViewer extends CardView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private MyGLSurfaceView c;

    public FocusViewer(@NonNull Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public FocusViewer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusViewer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R$layout.view_focus, this, true);
        a();
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4954, new Class[0], Void.TYPE).isSupported) {
            this.c = (MyGLSurfaceView) findViewById(R$id.yuv_glsurface);
            setSurfaceviewCorner(8.0f);
        }
    }

    public class a extends ViewOutlineProvider {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ float a;

        a(float f) {
            this.a = f;
        }

        public void getOutline(View view, Outline outline) {
            Class[] clsArr = {View.class, Outline.class};
            if (!PatchProxy.proxy(new Object[]{view, outline}, this, changeQuickRedirect, false, 4959, clsArr, Void.TYPE).isSupported) {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                outline.setRoundRect(new Rect(0, 0, (rect.right - rect.left) - 0, (rect.bottom - rect.top) - 0), this.a);
            }
        }
    }

    private void setSurfaceviewCorner(float radius) {
        Object[] objArr = {new Float(radius)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4957, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.setOutlineProvider(new a(radius));
            this.c.setClipToOutline(true);
        }
    }

    public void setVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4958, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setVisibility(visibility);
            this.c.setVisibility(visibility);
        }
    }
}
