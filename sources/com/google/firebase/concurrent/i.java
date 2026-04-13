package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ Runnable c;
    public final /* synthetic */ DelegatingScheduledFuture.Completer d;

    public /* synthetic */ i(Runnable runnable, DelegatingScheduledFuture.Completer completer) {
        this.c = runnable;
        this.d = completer;
    }

    public final void run() {
        DelegatingScheduledExecutorService.lambda$scheduleAtFixedRate$6(this.c, this.d);
    }
}
