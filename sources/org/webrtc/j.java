package org.webrtc;

import java.util.concurrent.CountDownLatch;

/* compiled from: lambda */
public final /* synthetic */ class j implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ CountDownLatch d;

    public /* synthetic */ j(EglRenderer eglRenderer, CountDownLatch countDownLatch) {
        this.c = eglRenderer;
        this.d = countDownLatch;
    }

    public final void run() {
        this.c.e(this.d);
    }
}
