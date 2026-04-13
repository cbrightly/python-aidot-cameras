package org.webrtc;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.os.Build;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MediaCodecUtils {
    static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka = 2141391874;
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka = 2141391873;
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka = 2141391875;
    static final int[] DECODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    static final int[] ENCODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    static final String EXYNOS_PREFIX = "OMX.Exynos.";
    static final String INTEL_PREFIX = "OMX.Intel.";
    static final String NVIDIA_PREFIX = "OMX.Nvidia.";
    static final String QCOM_PREFIX = "OMX.qcom.";
    static final String[] SOFTWARE_IMPLEMENTATION_PREFIXES = {"OMX.google.", "OMX.SEC.", "c2.android"};
    private static final String TAG = "MediaCodecUtils";
    static final int[] TEXTURE_COLOR_FORMATS = getTextureColorFormats();

    private static int[] getTextureColorFormats() {
        if (Build.VERSION.SDK_INT < 18) {
            return new int[0];
        }
        return new int[]{2130708361};
    }

    @Nullable
    static Integer selectColorFormat(int[] supportedColorFormats, MediaCodecInfo.CodecCapabilities capabilities) {
        for (int supportedColorFormat : supportedColorFormats) {
            for (int codecColorFormat : capabilities.colorFormats) {
                if (codecColorFormat == supportedColorFormat) {
                    return Integer.valueOf(codecColorFormat);
                }
            }
        }
        return null;
    }

    static boolean codecSupportsType(MediaCodecInfo info, VideoCodecMimeType type) {
        for (String mimeType : info.getSupportedTypes()) {
            if (type.mimeType().equals(mimeType)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: org.webrtc.MediaCodecUtils$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$webrtc$VideoCodecMimeType;

        static {
            int[] iArr = new int[VideoCodecMimeType.values().length];
            $SwitchMap$org$webrtc$VideoCodecMimeType = iArr;
            try {
                iArr[VideoCodecMimeType.VP8.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.VP9.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.AV1.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.H264.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.H265.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static Map<String, String> getCodecProperties(VideoCodecMimeType type, boolean highProfile) {
        switch (AnonymousClass1.$SwitchMap$org$webrtc$VideoCodecMimeType[type.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return new HashMap();
            case 4:
                return H264Utils.getDefaultH264Params(highProfile);
            case 5:
                return new HashMap();
            default:
                throw new IllegalArgumentException("Unsupported codec: " + type);
        }
    }

    static boolean isHardwareAccelerated(MediaCodecInfo info) {
        if (Build.VERSION.SDK_INT >= 29) {
            return isHardwareAcceleratedQOrHigher(info);
        }
        return !isSoftwareOnly(info);
    }

    @TargetApi(29)
    private static boolean isHardwareAcceleratedQOrHigher(MediaCodecInfo codecInfo) {
        return codecInfo.isHardwareAccelerated();
    }

    static boolean isSoftwareOnly(MediaCodecInfo codecInfo) {
        if (Build.VERSION.SDK_INT >= 29) {
            return isSoftwareOnlyQOrHigher(codecInfo);
        }
        String name = codecInfo.getName();
        for (String prefix : SOFTWARE_IMPLEMENTATION_PREFIXES) {
            if (name.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(29)
    private static boolean isSoftwareOnlyQOrHigher(MediaCodecInfo codecInfo) {
        return codecInfo.isSoftwareOnly();
    }

    private MediaCodecUtils() {
    }
}
