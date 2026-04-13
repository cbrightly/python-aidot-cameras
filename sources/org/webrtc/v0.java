package org.webrtc;

import org.webrtc.VideoEncoder;

/* compiled from: VideoEncoder */
public final /* synthetic */ class v0 {
    @CalledByNative
    public static long a(VideoEncoder _this) {
        return 0;
    }

    @CalledByNative
    public static boolean d(VideoEncoder _this) {
        return true;
    }

    @CalledByNative
    public static VideoCodecStatus e(VideoEncoder _this, VideoEncoder.RateControlParameters rcParameters) {
        return _this.setRateAllocation(rcParameters.bitrate, (int) Math.ceil(rcParameters.framerateFps));
    }

    @CalledByNative
    public static VideoEncoder.ResolutionBitrateLimits[] c(VideoEncoder _this) {
        return new VideoEncoder.ResolutionBitrateLimits[0];
    }

    @CalledByNative
    public static VideoEncoder.EncoderInfo b(VideoEncoder _this) {
        return new VideoEncoder.EncoderInfo(1, false);
    }
}
