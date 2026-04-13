package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ float d;
    public final /* synthetic */ float f;
    public final /* synthetic */ float q;
    public final /* synthetic */ float x;

    public /* synthetic */ h(EglRenderer eglRenderer, float f2, float f3, float f4, float f5) {
        this.c = eglRenderer;
        this.d = f2;
        this.f = f3;
        this.q = f4;
        this.x = f5;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q, this.x);
    }
}
