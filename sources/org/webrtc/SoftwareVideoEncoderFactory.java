package org.webrtc;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.webrtc.VideoEncoderFactory;

public class SoftwareVideoEncoderFactory implements VideoEncoderFactory {
    public /* synthetic */ VideoEncoderFactory.VideoEncoderSelector getEncoderSelector() {
        return w0.a(this);
    }

    public /* synthetic */ VideoCodecInfo[] getImplementations() {
        return w0.b(this);
    }

    @Nullable
    public VideoEncoder createEncoder(VideoCodecInfo codecInfo) {
        String codecName = codecInfo.getName();
        if (codecName.equalsIgnoreCase(VideoCodecMimeType.VP8.name())) {
            return new LibvpxVp8Encoder();
        }
        if (codecName.equalsIgnoreCase(VideoCodecMimeType.VP9.name()) && LibvpxVp9Encoder.nativeIsSupported()) {
            return new LibvpxVp9Encoder();
        }
        if (!codecName.equalsIgnoreCase(VideoCodecMimeType.AV1.name()) || !LibaomAv1Encoder.nativeIsSupported()) {
            return null;
        }
        return new LibaomAv1Encoder();
    }

    public VideoCodecInfo[] getSupportedCodecs() {
        return supportedCodecs();
    }

    static VideoCodecInfo[] supportedCodecs() {
        List<VideoCodecInfo> codecs = new ArrayList<>();
        codecs.add(new VideoCodecInfo(VideoCodecMimeType.VP8.name(), new HashMap()));
        if (LibvpxVp9Encoder.nativeIsSupported()) {
            codecs.add(new VideoCodecInfo(VideoCodecMimeType.VP9.name(), new HashMap()));
        }
        if (LibaomAv1Encoder.nativeIsSupported()) {
            codecs.add(new VideoCodecInfo(VideoCodecMimeType.AV1.name(), new HashMap()));
        }
        return (VideoCodecInfo[]) codecs.toArray(new VideoCodecInfo[codecs.size()]);
    }
}
