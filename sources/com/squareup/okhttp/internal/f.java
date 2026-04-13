package com.squareup.okhttp.internal;

/* compiled from: NamedRunnable */
public abstract class f implements Runnable {
    protected final String c;

    /* access modifiers changed from: protected */
    public abstract void a();

    public f(String format, Object... args) {
        this.c = String.format(format, args);
    }

    public final void run() {
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(this.c);
        try {
            a();
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }
}
