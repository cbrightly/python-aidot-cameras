package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ Callable c;
    public final /* synthetic */ DelegatingScheduledFuture.Completer d;

    public /* synthetic */ m(Callable callable, DelegatingScheduledFuture.Completer completer) {
        this.c = callable;
        this.d = completer;
    }

    public final void run() {
        DelegatingScheduledExecutorService.lambda$schedule$3(this.c, this.d);
    }
}
