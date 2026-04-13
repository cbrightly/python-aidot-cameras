package com.leedarson.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoFrame;

public class WebrtcSurfaceView extends SurfaceViewRenderer {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Paint c;
    private a d;
    private String f;
    private float q = 1.7777778f;
    private boolean x = false;

    public interface a {
        void onFirstFrameRendered();
    }

    public WebrtcSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        b();
    }

    public WebrtcSurfaceView(Context context) {
        super(context);
        b();
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11797, new Class[0], Void.TYPE).isSupported) {
            this.c = new Paint();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11798, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            setLayerType(2, this.c);
        }
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Object[] objArr = {new Byte(changed ? (byte) 1 : 0), new Integer(left), new Integer(top), new Integer(right), new Integer(bottom)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Boolean.TYPE, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11799, clsArr, Void.TYPE).isSupported) {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    public void onFirstFrameRendered() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11800, new Class[0], Void.TYPE).isSupported) {
            super.onFirstFrameRendered();
            a aVar = this.d;
            if (aVar != null) {
                aVar.onFirstFrameRendered();
            }
        }
    }

    public void onFrame(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 11801, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            super.onFrame(frame);
        }
    }

    public void setWide(boolean isWide) {
        this.x = isWide;
    }

    public void setOnFrameListener(a onFrameListener) {
        this.d = onFrameListener;
    }

    public void setModelId(String modelId) {
        this.f = modelId;
    }
}
