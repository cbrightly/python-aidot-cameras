package org.webrtc;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.webrtc.AndroidVideoDecoder;
import org.webrtc.EglBase;

public class MediaCodecVideoDecoderFactory implements VideoDecoderFactory {
    private static final String TAG = "MediaCodecVideoDecoderFactory";
    private AndroidVideoDecoder.IOnAndroidVideoCodecError _onErrorCodecErrorHandler;
    @Nullable
    private final Predicate<MediaCodecInfo> codecAllowedPredicate;
    @Nullable
    private final EglBase.Context sharedContext;

    public MediaCodecVideoDecoderFactory(@Nullable EglBase.Context sharedContext2, @Nullable Predicate<MediaCodecInfo> codecAllowedPredicate2) {
        this.sharedContext = sharedContext2;
        this.codecAllowedPredicate = codecAllowedPredicate2;
    }

    @Nullable
    public VideoDecoder createDecoder(VideoCodecInfo codecType) {
        VideoCodecMimeType type = VideoCodecMimeType.valueOf(codecType.getName());
        MediaCodecInfo info = findCodecForType(type);
        if (info == null) {
            return null;
        }
        VideoCodecMimeType videoCodecMimeType = type;
        AndroidVideoDecoder androidVideoDecoder = new AndroidVideoDecoder(new MediaCodecWrapperFactoryImpl(), info.getName(), videoCodecMimeType, MediaCodecUtils.selectColorFormat(MediaCodecUtils.DECODER_COLOR_FORMATS, info.getCapabilitiesForType(type.mimeType())).intValue(), this.sharedContext);
        androidVideoDecoder.setOnErrorCodecErrorHandler(new s(this));
        return androidVideoDecoder;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createDecoder$0 */
    public /* synthetic */ void a(int code, String message) {
        AndroidVideoDecoder.IOnAndroidVideoCodecError iOnAndroidVideoCodecError = this._onErrorCodecErrorHandler;
        if (iOnAndroidVideoCodecError != null) {
            iOnAndroidVideoCodecError.onFirstFrameKeyDecodedError(code, message);
        }
    }

    public void setOnErrorCodecErrorHandler(AndroidVideoDecoder.IOnAndroidVideoCodecError errorHandler) {
        this._onErrorCodecErrorHandler = errorHandler;
    }

    public VideoCodecInfo[] getSupportedCodecs() {
        List<VideoCodecInfo> supportedCodecInfos = new ArrayList<>();
        VideoCodecMimeType[] videoCodecMimeTypeArr = {VideoCodecMimeType.H264, VideoCodecMimeType.H265};
        for (int i = 0; i < 2; i++) {
            VideoCodecMimeType type = videoCodecMimeTypeArr[i];
            if (findCodecForType(type) != null) {
                supportedCodecInfos.add(new VideoCodecInfo(type.name(), MediaCodecUtils.getCodecProperties(type, false)));
            }
        }
        Logging.d(TAG, "supportedCodecInfos" + supportedCodecInfos);
        return (VideoCodecInfo[]) supportedCodecInfos.toArray(new VideoCodecInfo[supportedCodecInfos.size()]);
    }

    @Nullable
    private MediaCodecInfo findCodecForType(VideoCodecMimeType type) {
        if (Build.VERSION.SDK_INT < 19) {
            return null;
        }
        for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
            MediaCodecInfo info = null;
            try {
                info = MediaCodecList.getCodecInfoAt(i);
            } catch (IllegalArgumentException e) {
                Logging.e(TAG, "Cannot retrieve decoder codec info", e);
            }
            if (info != null && !info.isEncoder() && isSupportedCodec(info, type)) {
                return info;
            }
        }
        return null;
    }

    private boolean isSupportedCodec(MediaCodecInfo info, VideoCodecMimeType type) {
        if (MediaCodecUtils.codecSupportsType(info, type) && MediaCodecUtils.selectColorFormat(MediaCodecUtils.DECODER_COLOR_FORMATS, info.getCapabilitiesForType(type.mimeType())) != null) {
            return isCodecAllowed(info);
        }
        return false;
    }

    private boolean isCodecAllowed(MediaCodecInfo info) {
        Predicate<MediaCodecInfo> predicate = this.codecAllowedPredicate;
        if (predicate == null) {
            return true;
        }
        return predicate.test(info);
    }

    private boolean isH264HighProfileSupported(MediaCodecInfo info) {
        String name = info.getName();
        int i = Build.VERSION.SDK_INT;
        if (i >= 21 && name.startsWith("OMX.qcom.")) {
            return true;
        }
        if (i < 23 || !name.startsWith("OMX.Exynos.")) {
            return false;
        }
        return true;
    }
}
