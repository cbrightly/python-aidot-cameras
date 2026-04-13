package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ DelegatingScheduledExecutorService c;
    public final /* synthetic */ Runnable d;
    public final /* synthetic */ DelegatingScheduledFuture.Completer f;

    public /* synthetic */ g(DelegatingScheduledExecutorService delegatingScheduledExecutorService, Runnable runnable, DelegatingScheduledFuture.Completer completer) {
        this.c = delegatingScheduledExecutorService;
        this.d = runnable;
        this.f = completer;
    }

    public final void run() {
        this.c.e(this.d, this.f);
    }
}
