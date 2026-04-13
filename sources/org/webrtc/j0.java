package org.webrtc;

import java.util.concurrent.CountDownLatch;

/* compiled from: lambda */
public final /* synthetic */ class j0 implements Runnable {
    public final /* synthetic */ VideoFileRenderer c;
    public final /* synthetic */ CountDownLatch d;

    public /* synthetic */ j0(VideoFileRenderer videoFileRenderer, CountDownLatch countDownLatch) {
        this.c = videoFileRenderer;
        this.d = countDownLatch;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
