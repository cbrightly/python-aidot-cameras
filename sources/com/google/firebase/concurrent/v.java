package com.google.firebase.concurrent;

/* compiled from: lambda */
public final /* synthetic */ class v implements Runnable {
    public final /* synthetic */ LimitedConcurrencyExecutor c;
    public final /* synthetic */ Runnable d;

    public /* synthetic */ v(LimitedConcurrencyExecutor limitedConcurrencyExecutor, Runnable runnable) {
        this.c = limitedConcurrencyExecutor;
        this.d = runnable;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
