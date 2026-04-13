package org.webrtc;

import org.webrtc.EglRenderer;
import org.webrtc.RendererCommon;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ EglRenderer c;
    public final /* synthetic */ RendererCommon.GlDrawer d;
    public final /* synthetic */ EglRenderer.FrameListener f;
    public final /* synthetic */ float q;
    public final /* synthetic */ boolean x;

    public /* synthetic */ n(EglRenderer eglRenderer, RendererCommon.GlDrawer glDrawer, EglRenderer.FrameListener frameListener, float f2, boolean z) {
        this.c = eglRenderer;
        this.d = glDrawer;
        this.f = frameListener;
        this.q = f2;
        this.x = z;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q, this.x);
    }
}
