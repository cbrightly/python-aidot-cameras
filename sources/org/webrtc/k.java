package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ EglRenderer c;

    public /* synthetic */ k(EglRenderer eglRenderer) {
        this.c = eglRenderer;
    }

    public final void run() {
        this.c.renderFrameOnRenderThread();
    }
}
