package com.google.firebase.concurrent;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class z implements Callable {
    public final /* synthetic */ Runnable c;

    public /* synthetic */ z(Runnable runnable) {
        this.c = runnable;
    }

    public final Object call() {
        Object unused = this.c.run();
        return null;
    }
}
