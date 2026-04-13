package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ Runnable c;
    public final /* synthetic */ DelegatingScheduledFuture.Completer d;

    public /* synthetic */ c(Runnable runnable, DelegatingScheduledFuture.Completer completer) {
        this.c = runnable;
        this.d = completer;
    }

    public final void run() {
        DelegatingScheduledExecutorService.lambda$schedule$0(this.c, this.d);
    }
}
