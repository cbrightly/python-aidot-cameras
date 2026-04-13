package org.webrtc;

import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class e0 implements Callable {
    public final /* synthetic */ TextureBufferImpl c;

    public /* synthetic */ e0(TextureBufferImpl textureBufferImpl) {
        this.c = textureBufferImpl;
    }

    public final Object call() {
        return this.c.b();
    }
}
