package org.webrtc;

import androidx.annotation.Nullable;

public interface VideoDecoderFactory {
    @CalledByNative
    @Nullable
    VideoDecoder createDecoder(VideoCodecInfo videoCodecInfo);

    @CalledByNative
    VideoCodecInfo[] getSupportedCodecs();
}
