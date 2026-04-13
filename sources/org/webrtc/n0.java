package org.webrtc;

import java.nio.ByteBuffer;

/* compiled from: lambda */
public final /* synthetic */ class n0 implements Runnable {
    public final /* synthetic */ ByteBuffer c;

    public /* synthetic */ n0(ByteBuffer byteBuffer) {
        this.c = byteBuffer;
    }

    public final void run() {
        JniCommon.nativeFreeByteBuffer(this.c);
    }
}
