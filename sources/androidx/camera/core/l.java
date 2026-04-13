package androidx.camera.core;

import android.content.Context;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ CameraX c;
    public final /* synthetic */ Context d;
    public final /* synthetic */ Executor f;
    public final /* synthetic */ CallbackToFutureAdapter.Completer q;
    public final /* synthetic */ long x;

    public /* synthetic */ l(CameraX cameraX, Context context, Executor executor, CallbackToFutureAdapter.Completer completer, long j) {
        this.c = cameraX;
        this.d = context;
        this.f = executor;
        this.q = completer;
        this.x = j;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q, this.x);
    }
}
