package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ SurfaceViewImplementation c;
    public final /* synthetic */ SurfaceRequest d;

    public /* synthetic */ l(SurfaceViewImplementation surfaceViewImplementation, SurfaceRequest surfaceRequest) {
        this.c = surfaceViewImplementation;
        this.d = surfaceRequest;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
