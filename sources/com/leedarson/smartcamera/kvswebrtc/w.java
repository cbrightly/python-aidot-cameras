package com.leedarson.smartcamera.kvswebrtc;

import org.webrtc.SurfaceViewRenderer;

/* compiled from: lambda */
public final /* synthetic */ class w implements Runnable {
    public final /* synthetic */ f0 c;
    public final /* synthetic */ SurfaceViewRenderer d;

    public /* synthetic */ w(f0 f0Var, SurfaceViewRenderer surfaceViewRenderer) {
        this.c = f0Var;
        this.d = surfaceViewRenderer;
    }

    public final void run() {
        this.c.o2(this.d);
    }
}
