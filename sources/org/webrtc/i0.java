package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class i0 implements Runnable {
    public final /* synthetic */ VideoFileRenderer c;
    public final /* synthetic */ VideoFrame d;

    public /* synthetic */ i0(VideoFileRenderer videoFileRenderer, VideoFrame videoFrame) {
        this.c = videoFileRenderer;
        this.d = videoFrame;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
