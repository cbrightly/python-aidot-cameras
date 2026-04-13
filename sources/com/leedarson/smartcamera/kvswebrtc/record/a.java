package com.leedarson.smartcamera.kvswebrtc.record;

import org.webrtc.VideoFrame;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ j c;
    public final /* synthetic */ VideoFrame d;

    public /* synthetic */ a(j jVar, VideoFrame videoFrame) {
        this.c = jVar;
        this.d = videoFrame;
    }

    public final void run() {
        this.c.f(this.d);
    }
}
