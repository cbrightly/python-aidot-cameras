package org.webrtc;

import java.util.concurrent.CountDownLatch;
import org.webrtc.EglRenderer;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ CountDownLatch d;
    public final /* synthetic */ EglRenderer.FrameListener f;

    public /* synthetic */ i(EglRenderer eglRenderer, CountDownLatch countDownLatch, EglRenderer.FrameListener frameListener) {
        this.c = eglRenderer;
        this.d = countDownLatch;
        this.f = frameListener;
    }

    public final void run() {
        this.c.h(this.d, this.f);
    }
}
