package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class c0 implements Runnable {
    public final /* synthetic */ SurfaceViewRenderer c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int f;

    public /* synthetic */ c0(SurfaceViewRenderer surfaceViewRenderer, int i, int i2) {
        this.c = surfaceViewRenderer;
        this.d = i;
        this.f = i2;
    }

    public final void run() {
        this.c.a(this.d, this.f);
    }
}
