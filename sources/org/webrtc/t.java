package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class t implements Runnable {
    public final /* synthetic */ long c;

    public /* synthetic */ t(long j) {
        this.c = j;
    }

    public final void run() {
        JniCommon.nativeReleaseRef(this.c);
    }
}
