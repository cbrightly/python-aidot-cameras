package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class z implements Runnable {
    public final /* synthetic */ SurfaceTextureHelper c;
    public final /* synthetic */ int d;

    public /* synthetic */ z(SurfaceTextureHelper surfaceTextureHelper, int i) {
        this.c = surfaceTextureHelper;
        this.d = i;
    }

    public final void run() {
        this.c.e(this.d);
    }
}
