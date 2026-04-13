package com.google.firebase.concurrent;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class x implements Callable {
    public final /* synthetic */ Runnable c;
    public final /* synthetic */ Object d;

    public /* synthetic */ x(Runnable runnable, Object obj) {
        this.c = runnable;
        this.d = obj;
    }

    public final Object call() {
        Runnable runnable = this.c;
        Object obj = this.d;
        Object unused = runnable.run();
        return obj;
    }
}
