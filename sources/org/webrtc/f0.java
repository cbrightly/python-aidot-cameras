package org.webrtc;

import org.webrtc.VideoDecoder;

/* compiled from: lambda */
public final /* synthetic */ class f0 implements VideoDecoder.Callback {
    public final /* synthetic */ long a;

    public /* synthetic */ f0(long j) {
        this.a = j;
    }

    public final void onDecodedFrame(VideoFrame videoFrame, Integer num, Integer num2) {
        VideoDecoderWrapper.nativeOnDecodedFrame(this.a, videoFrame, num, num2);
    }
}
