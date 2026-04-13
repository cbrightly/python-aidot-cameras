package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class m0 implements VideoSink {
    public final /* synthetic */ VideoSource c;

    public /* synthetic */ m0(VideoSource videoSource) {
        this.c = videoSource;
    }

    public final void onFrame(VideoFrame videoFrame) {
        this.c.b(videoFrame);
    }
}
