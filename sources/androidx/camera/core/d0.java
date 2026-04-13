package androidx.camera.core;

import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements Runnable {
    public final /* synthetic */ ListenableFuture c;

    public /* synthetic */ d0(ListenableFuture listenableFuture) {
        this.c = listenableFuture;
    }

    public final void run() {
        this.c.cancel(true);
    }
}
