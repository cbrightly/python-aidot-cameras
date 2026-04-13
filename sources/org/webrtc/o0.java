package org.webrtc;

import java.util.concurrent.CountDownLatch;

/* compiled from: lambda */
public final /* synthetic */ class o0 implements Runnable {
    public final /* synthetic */ CountDownLatch c;

    public /* synthetic */ o0(CountDownLatch countDownLatch) {
        this.c = countDownLatch;
    }

    public final void run() {
        this.c.countDown();
    }
}
