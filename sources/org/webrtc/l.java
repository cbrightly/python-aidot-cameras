package org.webrtc;

import org.webrtc.EglBase;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ EglBase.Context d;
    public final /* synthetic */ int[] f;

    public /* synthetic */ l(EglRenderer eglRenderer, EglBase.Context context, int[] iArr) {
        this.c = eglRenderer;
        this.d = context;
        this.f = iArr;
    }

    public final void run() {
        this.c.d(this.d, this.f);
    }
}
