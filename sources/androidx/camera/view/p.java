package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;

/* compiled from: lambda */
public final /* synthetic */ class p implements Runnable {
    public final /* synthetic */ TextureViewImplementation c;
    public final /* synthetic */ SurfaceRequest d;

    public /* synthetic */ p(TextureViewImplementation textureViewImplementation, SurfaceRequest surfaceRequest) {
        this.c = textureViewImplementation;
        this.d = surfaceRequest;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
