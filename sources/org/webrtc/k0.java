package org.webrtc;

import org.webrtc.VideoFrame;

/* compiled from: lambda */
public final /* synthetic */ class k0 implements Runnable {
    public final /* synthetic */ VideoFileRenderer c;
    public final /* synthetic */ VideoFrame.I420Buffer d;
    public final /* synthetic */ VideoFrame f;

    public /* synthetic */ k0(VideoFileRenderer videoFileRenderer, VideoFrame.I420Buffer i420Buffer, VideoFrame videoFrame) {
        this.c = videoFileRenderer;
        this.d = i420Buffer;
        this.f = videoFrame;
    }

    public final void run() {
        this.c.d(this.d, this.f);
    }
}
