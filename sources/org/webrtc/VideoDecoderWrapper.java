package org.webrtc;

import org.webrtc.VideoDecoder;

public class VideoDecoderWrapper {
    /* access modifiers changed from: private */
    public static native void nativeOnDecodedFrame(long j, VideoFrame videoFrame, Integer num, Integer num2);

    VideoDecoderWrapper() {
    }

    @CalledByNative
    static VideoDecoder.Callback createDecoderCallback(long nativeDecoder) {
        return new f0(nativeDecoder);
    }
}
