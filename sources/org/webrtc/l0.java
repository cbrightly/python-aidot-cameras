package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class l0 implements Runnable {
    public final /* synthetic */ VideoSource c;
    public final /* synthetic */ VideoFrame d;

    public /* synthetic */ l0(VideoSource videoSource, VideoFrame videoFrame) {
        this.c = videoSource;
        this.d = videoFrame;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
