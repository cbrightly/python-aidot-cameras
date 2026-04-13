package androidx.camera.view;

import android.view.Surface;
import androidx.camera.core.SurfaceRequest;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class q implements Runnable {
    public final /* synthetic */ TextureViewImplementation c;
    public final /* synthetic */ Surface d;
    public final /* synthetic */ ListenableFuture f;
    public final /* synthetic */ SurfaceRequest q;

    public /* synthetic */ q(TextureViewImplementation textureViewImplementation, Surface surface, ListenableFuture listenableFuture, SurfaceRequest surfaceRequest) {
        this.c = textureViewImplementation;
        this.d = surface;
        this.f = listenableFuture;
        this.q = surfaceRequest;
    }

    public final void run() {
        this.c.c(this.d, this.f, this.q);
    }
}
