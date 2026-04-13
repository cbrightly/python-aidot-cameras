package org.webrtc;

/* compiled from: lambda */
public final /* synthetic */ class d implements VideoSink {
    public final /* synthetic */ Camera1Session c;

    public /* synthetic */ d(Camera1Session camera1Session) {
        this.c = camera1Session;
    }

    public final void onFrame(VideoFrame videoFrame) {
        this.c.a(videoFrame);
    }
}
