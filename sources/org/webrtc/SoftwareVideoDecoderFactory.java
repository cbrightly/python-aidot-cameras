package org.webrtc;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoftwareVideoDecoderFactory implements VideoDecoderFactory {
    private LibH265Decoder h265Decoder;

    @Nullable
    public VideoDecoder createDecoder(VideoCodecInfo codecInfo) {
        String codecName = codecInfo.getName();
        if (codecName.equalsIgnoreCase(VideoCodecMimeType.VP8.name())) {
            return new LibvpxVp8Decoder();
        }
        if (codecName.equalsIgnoreCase(VideoCodecMimeType.VP9.name()) && LibvpxVp9Decoder.nativeIsSupported()) {
            return new LibvpxVp9Decoder();
        }
        if (codecName.equalsIgnoreCase(VideoCodecMimeType.AV1.name()) && LibaomAv1Decoder.nativeIsSupported()) {
            return new LibaomAv1Decoder();
        }
        if (!codecName.equalsIgnoreCase(VideoCodecMimeType.H265.name())) {
            return null;
        }
        LibH265Decoder libH265Decoder = new LibH265Decoder();
        this.h265Decoder = libH265Decoder;
        return libH265Decoder;
    }

    public VideoCodecInfo[] getSupportedCodecs() {
        return supportedCodecs();
    }

    static VideoCodecInfo[] supportedCodecs() {
        List<VideoCodecInfo> codecs = new ArrayList<>();
        codecs.add(new VideoCodecInfo(VideoCodecMimeType.VP8.name(), new HashMap()));
        if (LibvpxVp9Decoder.nativeIsSupported()) {
            codecs.add(new VideoCodecInfo(VideoCodecMimeType.VP9.name(), new HashMap()));
        }
        if (LibaomAv1Decoder.nativeIsSupported()) {
            codecs.add(new VideoCodecInfo(VideoCodecMimeType.AV1.name(), new HashMap()));
        }
        return (VideoCodecInfo[]) codecs.toArray(new VideoCodecInfo[codecs.size()]);
    }

    public void closeDecoder() {
        LibH265Decoder libH265Decoder = this.h265Decoder;
        if (libH265Decoder != null) {
            libH265Decoder.closeDecoder();
        }
    }

    public void openDecoder() {
        LibH265Decoder libH265Decoder = this.h265Decoder;
        if (libH265Decoder != null) {
            libH265Decoder.openDecoder();
        }
    }
}
