package androidx.camera.core;

import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ CameraX c;
    public final /* synthetic */ CallbackToFutureAdapter.Completer d;

    public /* synthetic */ m(CameraX cameraX, CallbackToFutureAdapter.Completer completer) {
        this.c = cameraX;
        this.d = completer;
    }

    public final void run() {
        Futures.propagate(this.c.shutdownInternal(), this.d);
    }
}
