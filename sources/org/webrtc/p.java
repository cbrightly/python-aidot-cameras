package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class p implements Runnable {
    public final /* synthetic */ HardwareVideoEncoder c;
    public final /* synthetic */ int d;

    public /* synthetic */ p(HardwareVideoEncoder hardwareVideoEncoder, int i) {
        this.c = hardwareVideoEncoder;
        this.d = i;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
