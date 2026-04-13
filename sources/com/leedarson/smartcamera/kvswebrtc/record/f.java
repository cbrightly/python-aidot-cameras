package com.leedarson.smartcamera.kvswebrtc.record;

import org.webrtc.VideoFrame;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ m c;
    public final /* synthetic */ VideoFrame d;

    public /* synthetic */ f(m mVar, VideoFrame videoFrame) {
        this.c = mVar;
        this.d = videoFrame;
    }

    public final void run() {
        this.c.i(this.d);
    }
}
