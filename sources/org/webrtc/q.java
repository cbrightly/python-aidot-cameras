package org.webrtc;

import java.nio.ByteBuffer;

/* compiled from: lambda */
public final /* synthetic */ class q implements Runnable {
    public final /* synthetic */ ByteBuffer c;

    public /* synthetic */ q(ByteBuffer byteBuffer) {
        this.c = byteBuffer;
    }

    public final void run() {
        JniCommon.nativeFreeByteBuffer(this.c);
    }
}
