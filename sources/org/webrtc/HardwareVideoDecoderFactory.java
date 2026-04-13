package org.webrtc;

import android.media.MediaCodecInfo;
import androidx.annotation.Nullable;
import org.webrtc.AndroidVideoDecoder;
import org.webrtc.EglBase;
import org.webrtc.Predicate;

public class HardwareVideoDecoderFactory extends MediaCodecVideoDecoderFactory {
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
            return MediaCodecUtils.isHardwareAccelerated(arg);
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

    @Deprecated
    public HardwareVideoDecoderFactory() {
        this((EglBase.Context) null);
    }

    public HardwareVideoDecoderFactory(@Nullable EglBase.Context sharedContext) {
        this(sharedContext, (Predicate<MediaCodecInfo>) null);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HardwareVideoDecoderFactory(@androidx.annotation.Nullable org.webrtc.EglBase.Context r2, @androidx.annotation.Nullable org.webrtc.Predicate<android.media.MediaCodecInfo> r3) {
        /*
            r1 = this;
            if (r3 != 0) goto L_0x0006
            org.webrtc.Predicate<android.media.MediaCodecInfo> r0 = defaultAllowedPredicate
            goto L_0x000c
        L_0x0006:
            org.webrtc.Predicate<android.media.MediaCodecInfo> r0 = defaultAllowedPredicate
            org.webrtc.Predicate r0 = r3.and(r0)
        L_0x000c:
            r1.<init>(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.HardwareVideoDecoderFactory.<init>(org.webrtc.EglBase$Context, org.webrtc.Predicate):void");
    }
}
