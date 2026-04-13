package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: lambda */
public final /* synthetic */ class h implements DelegatingScheduledFuture.Resolver {
    public final /* synthetic */ DelegatingScheduledExecutorService a;
    public final /* synthetic */ Callable b;
    public final /* synthetic */ long c;
    public final /* synthetic */ TimeUnit d;

    public /* synthetic */ h(DelegatingScheduledExecutorService delegatingScheduledExecutorService, Callable callable, long j, TimeUnit timeUnit) {
        this.a = delegatingScheduledExecutorService;
        this.b = callable;
        this.c = j;
        this.d = timeUnit;
    }

    public final ScheduledFuture addCompleter(DelegatingScheduledFuture.Completer completer) {
        return this.a.d(this.b, this.c, this.d, completer);
    }
}
