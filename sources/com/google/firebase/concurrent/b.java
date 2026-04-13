package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: lambda */
public final /* synthetic */ class b implements DelegatingScheduledFuture.Resolver {
    public final /* synthetic */ DelegatingScheduledExecutorService a;
    public final /* synthetic */ Runnable b;
    public final /* synthetic */ long c;
    public final /* synthetic */ TimeUnit d;

    public /* synthetic */ b(DelegatingScheduledExecutorService delegatingScheduledExecutorService, Runnable runnable, long j, TimeUnit timeUnit) {
        this.a = delegatingScheduledExecutorService;
        this.b = runnable;
        this.c = j;
        this.d = timeUnit;
    }

    public final ScheduledFuture addCompleter(DelegatingScheduledFuture.Completer completer) {
        return this.a.b(this.b, this.c, this.d, completer);
    }
}
