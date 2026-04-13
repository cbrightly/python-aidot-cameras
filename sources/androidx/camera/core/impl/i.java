package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ ListenableFuture c;
    public final /* synthetic */ CallbackToFutureAdapter.Completer d;
    public final /* synthetic */ long f;

    public /* synthetic */ i(ListenableFuture listenableFuture, CallbackToFutureAdapter.Completer completer, long j) {
        this.c = listenableFuture;
        this.d = completer;
        this.f = j;
    }

    public final void run() {
        DeferrableSurfaces.lambda$surfaceListWithTimeout$0(this.c, this.d, this.f);
    }
}
