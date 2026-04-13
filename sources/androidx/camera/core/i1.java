package androidx.camera.core;

import android.view.Surface;
import androidx.camera.core.SurfaceRequest;
import androidx.core.util.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class i1 implements Runnable {
    public final /* synthetic */ Consumer c;
    public final /* synthetic */ Surface d;

    public /* synthetic */ i1(Consumer consumer, Surface surface) {
        this.c = consumer;
        this.d = surface;
    }

    public final void run() {
        this.c.accept(SurfaceRequest.Result.of(4, this.d));
    }
}
