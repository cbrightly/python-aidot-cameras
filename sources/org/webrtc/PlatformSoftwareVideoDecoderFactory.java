package org.webrtc;

import android.media.MediaCodecInfo;
import androidx.annotation.Nullable;
import org.webrtc.AndroidVideoDecoder;
import org.webrtc.EglBase;
import org.webrtc.Predicate;

public class PlatformSoftwareVideoDecoderFactory extends MediaCodecVideoDecoderFactory {
    private static final Predicate<MediaCodecInfo> defaultAllowedPredicate = new Predicate<MediaCodecInfo>() {
        public /* synthetic */ Predicate and(Predicate predicate) {
            return Predicate.CC.a(this, predicate);
        }

        public /* synthetic */ Predicate negate() {
            return Predicate.CC.b(this);
        }

        public /* synthetic */ Predicate or(Predicate predicate) {
            return Predicate.CC.c(this, predicate);
        }

        public boolean test(MediaCodecInfo arg) {
            return MediaCodecUtils.isSoftwareOnly(arg);
        }
    };

    @Nullable
    public /* bridge */ /* synthetic */ VideoDecoder createDecoder(VideoCodecInfo videoCodecInfo) {
        return super.createDecoder(videoCodecInfo);
    }

    public /* bridge */ /* synthetic */ VideoCodecInfo[] getSupportedCodecs() {
        return super.getSupportedCodecs();
    }

    public /* bridge */ /* synthetic */ void setOnErrorCodecErrorHandler(AndroidVideoDecoder.IOnAndroidVideoCodecError iOnAndroidVideoCodecError) {
        super.setOnErrorCodecErrorHandler(iOnAndroidVideoCodecError);
    }

    public PlatformSoftwareVideoDecoderFactory(@Nullable EglBase.Context sharedContext) {
        super(sharedContext, defaultAllowedPredicate);
    }
}
