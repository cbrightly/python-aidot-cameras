package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ Executor c;
    public final /* synthetic */ ListenableFuture d;
    public final /* synthetic */ CallbackToFutureAdapter.Completer f;
    public final /* synthetic */ long q;

    public /* synthetic */ k(Executor executor, ListenableFuture listenableFuture, CallbackToFutureAdapter.Completer completer, long j) {
        this.c = executor;
        this.d = listenableFuture;
        this.f = completer;
        this.q = j;
    }

    public final void run() {
        this.c.execute(new i(this.d, this.f, this.q));
    }
}
