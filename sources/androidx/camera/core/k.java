package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ CameraX c;
    public final /* synthetic */ CallbackToFutureAdapter.Completer d;

    public /* synthetic */ k(CameraX cameraX, CallbackToFutureAdapter.Completer completer) {
        this.c = cameraX;
        this.d = completer;
    }

    public final void run() {
        this.c.d(this.d);
    }
}
