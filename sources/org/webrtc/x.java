package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class x implements Runnable {
    public final /* synthetic */ SurfaceTextureHelper c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int f;

    public /* synthetic */ x(SurfaceTextureHelper surfaceTextureHelper, int i, int i2) {
        this.c = surfaceTextureHelper;
        this.d = i;
        this.f = i2;
    }

    public final void run() {
        this.c.f(this.d, this.f);
    }
}
