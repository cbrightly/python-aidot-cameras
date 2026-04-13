package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class e implements Callable {
    public final /* synthetic */ DelegatingScheduledExecutorService c;
    public final /* synthetic */ Callable d;
    public final /* synthetic */ DelegatingScheduledFuture.Completer f;

    public /* synthetic */ e(DelegatingScheduledExecutorService delegatingScheduledExecutorService, Callable callable, DelegatingScheduledFuture.Completer completer) {
        this.c = delegatingScheduledExecutorService;
        this.d = callable;
        this.f = completer;
    }

    public final Object call() {
        return this.c.c(this.d, this.f);
    }
}
