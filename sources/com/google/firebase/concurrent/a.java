package com.google.firebase.concurrent;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ CustomThreadFactory c;
    public final /* synthetic */ Runnable d;

    public /* synthetic */ a(CustomThreadFactory customThreadFactory, Runnable runnable) {
        this.c = customThreadFactory;
        this.d = runnable;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
