package com.leedarson.smartcamera.kvswebrtc;

import android.graphics.Bitmap;
import org.webrtc.EglRenderer;
import org.webrtc.SurfaceViewRenderer;

/* compiled from: lambda */
public final /* synthetic */ class e implements EglRenderer.FrameListener {
    public final /* synthetic */ f0 a;
    public final /* synthetic */ SurfaceViewRenderer b;

    public /* synthetic */ e(f0 f0Var, SurfaceViewRenderer surfaceViewRenderer) {
        this.a = f0Var;
        this.b = surfaceViewRenderer;
    }

    public final void onFrame(Bitmap bitmap) {
        this.a.k2(this.b, bitmap);
    }
}
