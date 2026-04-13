package org.webrtc;

import org.webrtc.TextureBufferImpl;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements Runnable {
    public final /* synthetic */ TextureBufferImpl c;
    public final /* synthetic */ TextureBufferImpl.RefCountMonitor d;

    public /* synthetic */ d0(TextureBufferImpl textureBufferImpl, TextureBufferImpl.RefCountMonitor refCountMonitor) {
        this.c = textureBufferImpl;
        this.d = refCountMonitor;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
