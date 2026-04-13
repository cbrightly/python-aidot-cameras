package androidx.camera.core.impl;

import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    public final /* synthetic */ ListenableFuture c;

    public /* synthetic */ h(ListenableFuture listenableFuture) {
        this.c = listenableFuture;
    }

    public final void run() {
        this.c.cancel(true);
    }
}
