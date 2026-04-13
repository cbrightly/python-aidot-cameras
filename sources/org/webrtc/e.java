package org.webrtc;

import org.webrtc.Camera2Session;

/* compiled from: lambda */
public final /* synthetic */ class e implements VideoSink {
    public final /* synthetic */ Camera2Session.CaptureSessionCallback c;

    public /* synthetic */ e(Camera2Session.CaptureSessionCallback captureSessionCallback) {
        this.c = captureSessionCallback;
    }

    public final void onFrame(VideoFrame videoFrame) {
        this.c.a(videoFrame);
    }
}
