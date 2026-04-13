package org.webrtc;

import org.webrtc.VideoFrame;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    public final /* synthetic */ VideoFrame.I420Buffer c;

    public /* synthetic */ o(VideoFrame.I420Buffer i420Buffer) {
        this.c = i420Buffer;
    }

    public final void run() {
        this.c.release();
    }
}
