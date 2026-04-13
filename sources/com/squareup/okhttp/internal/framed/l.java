package com.squareup.okhttp.internal.framed;

import java.util.concurrent.CountDownLatch;

/* compiled from: Ping */
public final class l {
    private final CountDownLatch a = new CountDownLatch(1);
    private long b = -1;
    private long c = -1;

    l() {
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (this.b == -1) {
            this.b = System.nanoTime();
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        if (this.c != -1 || this.b == -1) {
            throw new IllegalStateException();
        }
        this.c = System.nanoTime();
        this.a.countDown();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.c == -1) {
            long j = this.b;
            if (j != -1) {
                this.c = j - 1;
                this.a.countDown();
                return;
            }
        }
        throw new IllegalStateException();
    }
}
