package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ Runnable d;

    public /* synthetic */ g(EglRenderer eglRenderer, Runnable runnable) {
        this.c = eglRenderer;
        this.d = runnable;
    }

    public final void run() {
        this.c.g(this.d);
    }
}
