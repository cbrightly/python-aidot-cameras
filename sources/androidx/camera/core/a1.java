package androidx.camera.core;

import androidx.camera.core.Preview;

/* compiled from: lambda */
public final /* synthetic */ class a1 implements Runnable {
    public final /* synthetic */ Preview.SurfaceProvider c;
    public final /* synthetic */ SurfaceRequest d;

    public /* synthetic */ a1(Preview.SurfaceProvider surfaceProvider, SurfaceRequest surfaceRequest) {
        this.c = surfaceProvider;
        this.d = surfaceRequest;
    }

    public final void run() {
        this.c.onSurfaceRequested(this.d);
    }
}
