package org.webrtc;

import org.webrtc.VideoEncoderFactory;

/* compiled from: VideoEncoderFactory */
public final /* synthetic */ class w0 {
    @CalledByNative
    public static VideoCodecInfo[] b(VideoEncoderFactory _this) {
        return _this.getSupportedCodecs();
    }

    @CalledByNative
    public static VideoEncoderFactory.VideoEncoderSelector a(VideoEncoderFactory _this) {
        return null;
    }
}
