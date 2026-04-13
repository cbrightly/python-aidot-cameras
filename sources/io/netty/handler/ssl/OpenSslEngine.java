package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;

public final class OpenSslEngine extends ReferenceCountedOpenSslEngine {
    OpenSslEngine(OpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode) {
        super(context, alloc, peerHost, peerPort, jdkCompatibilityMode, false);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}
