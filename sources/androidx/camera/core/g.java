package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ CameraX c;
    public final /* synthetic */ Executor d;
    public final /* synthetic */ long f;
    public final /* synthetic */ CallbackToFutureAdapter.Completer q;

    public /* synthetic */ g(CameraX cameraX, Executor executor, long j, CallbackToFutureAdapter.Completer completer) {
        this.c = cameraX;
        this.d = executor;
        this.f = j;
        this.q = completer;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q);
    }
}
