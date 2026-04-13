package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ Runnable c;
    public final /* synthetic */ DelegatingScheduledFuture.Completer d;

    public /* synthetic */ k(Runnable runnable, DelegatingScheduledFuture.Completer completer) {
        this.c = runnable;
        this.d = completer;
    }

    public final void run() {
        DelegatingScheduledExecutorService.lambda$scheduleWithFixedDelay$9(this.c, this.d);
    }
}
