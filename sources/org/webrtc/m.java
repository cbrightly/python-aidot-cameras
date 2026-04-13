package org.webrtc;

import android.os.Looper;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ Looper d;

    public /* synthetic */ m(EglRenderer eglRenderer, Looper looper) {
        this.c = eglRenderer;
        this.d = looper;
    }

    public final void run() {
        this.c.f(this.d);
    }
}
