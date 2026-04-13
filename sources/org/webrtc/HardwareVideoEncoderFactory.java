package org.webrtc;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.webrtc.EglBase;
import org.webrtc.EglBase14;
import org.webrtc.VideoEncoderFactory;

public class HardwareVideoEncoderFactory implements VideoEncoderFactory {
    private static final List<String> H264_HW_EXCEPTION_MODELS = Arrays.asList(new String[]{"SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4"});
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_L_MS = 15000;
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_M_MS = 20000;
    private static final int QCOM_VP8_KEY_FRAME_INTERVAL_ANDROID_N_MS = 15000;
    private static final String TAG = "HardwareVideoEncoderFactory";
    @Nullable
    private final Predicate<MediaCodecInfo> codecAllowedPredicate;
    private final boolean enableH264HighProfile;
    private final boolean enableIntelVp8Encoder;
    @Nullable
    private final EglBase14.Context sharedContext;

    public /* synthetic */ VideoEncoderFactory.VideoEncoderSelector getEncoderSelector() {
        return w0.a(this);
    }

    public /* synthetic */ VideoCodecInfo[] getImplementations() {
        return w0.b(this);
    }

    public HardwareVideoEncoderFactory(EglBase.Context sharedContext2, boolean enableIntelVp8Encoder2, boolean enableH264HighProfile2) {
        this(sharedContext2, enableIntelVp8Encoder2, enableH264HighProfile2, (Predicate<MediaCodecInfo>) null);
    }

    public HardwareVideoEncoderFactory(EglBase.Context sharedContext2, boolean enableIntelVp8Encoder2, boolean enableH264HighProfile2, @Nullable Predicate<MediaCodecInfo> codecAllowedPredicate2) {
        if (sharedContext2 instanceof EglBase14.Context) {
            this.sharedContext = (EglBase14.Context) sharedContext2;
        } else {
            Logging.w(TAG, "No shared EglBase.Context.  Encoders will not use texture mode.");
            this.sharedContext = null;
        }
        this.enableIntelVp8Encoder = enableIntelVp8Encoder2;
        this.enableH264HighProfile = enableH264HighProfile2;
        this.codecAllowedPredicate = codecAllowedPredicate2;
    }

    @Deprecated
    public HardwareVideoEncoderFactory(boolean enableIntelVp8Encoder2, boolean enableH264HighProfile2) {
        this((EglBase.Context) null, enableIntelVp8Encoder2, enableH264HighProfile2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r2 = org.webrtc.VideoCodecMimeType.valueOf(r22.getName());
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.webrtc.VideoEncoder createEncoder(org.webrtc.VideoCodecInfo r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 0
            r4 = 19
            if (r2 >= r4) goto L_0x000c
            return r3
        L_0x000c:
            java.lang.String r2 = r22.getName()
            org.webrtc.VideoCodecMimeType r2 = org.webrtc.VideoCodecMimeType.valueOf(r2)
            android.media.MediaCodecInfo r15 = r0.findCodecForType(r2)
            if (r15 != 0) goto L_0x001b
            return r3
        L_0x001b:
            java.lang.String r14 = r15.getName()
            java.lang.String r13 = r2.mimeType()
            int[] r4 = org.webrtc.MediaCodecUtils.TEXTURE_COLOR_FORMATS
            android.media.MediaCodecInfo$CodecCapabilities r5 = r15.getCapabilitiesForType(r13)
            java.lang.Integer r16 = org.webrtc.MediaCodecUtils.selectColorFormat(r4, r5)
            int[] r4 = org.webrtc.MediaCodecUtils.ENCODER_COLOR_FORMATS
            android.media.MediaCodecInfo$CodecCapabilities r5 = r15.getCapabilitiesForType(r13)
            java.lang.Integer r17 = org.webrtc.MediaCodecUtils.selectColorFormat(r4, r5)
            org.webrtc.VideoCodecMimeType r4 = org.webrtc.VideoCodecMimeType.H264
            if (r2 != r4) goto L_0x005f
            java.util.Map<java.lang.String, java.lang.String> r4 = r1.params
            r5 = 1
            java.util.Map r5 = org.webrtc.MediaCodecUtils.getCodecProperties(r2, r5)
            boolean r4 = org.webrtc.H264Utils.isSameH264Profile(r4, r5)
            java.util.Map<java.lang.String, java.lang.String> r5 = r1.params
            r6 = 0
            java.util.Map r6 = org.webrtc.MediaCodecUtils.getCodecProperties(r2, r6)
            boolean r5 = org.webrtc.H264Utils.isSameH264Profile(r5, r6)
            if (r4 != 0) goto L_0x0056
            if (r5 != 0) goto L_0x0056
            return r3
        L_0x0056:
            if (r4 == 0) goto L_0x005f
            boolean r6 = r0.isH264HighProfileSupported(r15)
            if (r6 != 0) goto L_0x005f
            return r3
        L_0x005f:
            org.webrtc.HardwareVideoEncoder r3 = new org.webrtc.HardwareVideoEncoder
            org.webrtc.MediaCodecWrapperFactoryImpl r5 = new org.webrtc.MediaCodecWrapperFactoryImpl
            r5.<init>()
            java.util.Map<java.lang.String, java.lang.String> r10 = r1.params
            int r11 = r0.getKeyFrameIntervalSec(r2)
            int r12 = r0.getForcedKeyFrameIntervalMs(r2, r14)
            org.webrtc.BitrateAdjuster r18 = r0.createBitrateAdjuster(r2, r14)
            org.webrtc.EglBase14$Context r9 = r0.sharedContext
            r4 = r3
            r6 = r14
            r7 = r2
            r8 = r16
            r19 = r9
            r9 = r17
            r20 = r13
            r13 = r18
            r18 = r14
            r14 = r19
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.HardwareVideoEncoderFactory.createEncoder(org.webrtc.VideoCodecInfo):org.webrtc.VideoEncoder");
    }

    public VideoCodecInfo[] getSupportedCodecs() {
        if (Build.VERSION.SDK_INT < 19) {
            return new VideoCodecInfo[0];
        }
        List<VideoCodecInfo> supportedCodecInfos = new ArrayList<>();
        VideoCodecMimeType[] videoCodecMimeTypeArr = {VideoCodecMimeType.VP8, VideoCodecMimeType.VP9, VideoCodecMimeType.H264, VideoCodecMimeType.AV1, VideoCodecMimeType.H265};
        for (int i = 0; i < 5; i++) {
            VideoCodecMimeType type = videoCodecMimeTypeArr[i];
            MediaCodecInfo codec = findCodecForType(type);
            if (codec != null) {
                String name = type.name();
                if (type == VideoCodecMimeType.H264 && isH264HighProfileSupported(codec)) {
                    supportedCodecInfos.add(new VideoCodecInfo(name, MediaCodecUtils.getCodecProperties(type, true)));
                }
                supportedCodecInfos.add(new VideoCodecInfo(name, MediaCodecUtils.getCodecProperties(type, false)));
            }
        }
        return (VideoCodecInfo[]) supportedCodecInfos.toArray(new VideoCodecInfo[supportedCodecInfos.size()]);
    }

    @Nullable
    private MediaCodecInfo findCodecForType(VideoCodecMimeType type) {
        for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
            MediaCodecInfo info = null;
            try {
                info = MediaCodecList.getCodecInfoAt(i);
            } catch (IllegalArgumentException e) {
                Logging.e(TAG, "Cannot retrieve encoder codec info", e);
            }
            if (info != null && info.isEncoder() && isSupportedCodec(info, type)) {
                return info;
            }
        }
        return null;
    }

    private boolean isSupportedCodec(MediaCodecInfo info, VideoCodecMimeType type) {
        if (MediaCodecUtils.codecSupportsType(info, type) && MediaCodecUtils.selectColorFormat(MediaCodecUtils.ENCODER_COLOR_FORMATS, info.getCapabilitiesForType(type.mimeType())) != null && isHardwareSupportedInCurrentSdk(info, type) && isMediaCodecAllowed(info)) {
            return true;
        }
        return false;
    }

    /* renamed from: org.webrtc.HardwareVideoEncoderFactory$1  reason: invalid class name */
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
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.H264.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.AV1.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$webrtc$VideoCodecMimeType[VideoCodecMimeType.H265.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private boolean isHardwareSupportedInCurrentSdk(MediaCodecInfo info, VideoCodecMimeType type) {
        switch (AnonymousClass1.$SwitchMap$org$webrtc$VideoCodecMimeType[type.ordinal()]) {
            case 1:
                return isHardwareSupportedInCurrentSdkVp8(info);
            case 2:
                return isHardwareSupportedInCurrentSdkVp9(info);
            case 3:
                boolean h264Support = isHardwareSupportedInCurrentSdkH264(info);
                Logging.d("", "isHardwareSupportedH264InCurrentSdk=>" + h264Support);
                return true;
            case 4:
                return false;
            case 5:
                boolean h265Support = isHardwareSupportedInCurrentSdkH265(info);
                Logging.d("", "isHardwareSupportedH264InCurrentSdk=>" + h265Support);
                return true;
            default:
                return false;
        }
    }

    private boolean isHardwareSupportedInCurrentSdkVp8(MediaCodecInfo info) {
        String name = info.getName();
        return (name.startsWith("OMX.qcom.") && Build.VERSION.SDK_INT >= 19) || (name.startsWith("OMX.Exynos.") && Build.VERSION.SDK_INT >= 23) || (name.startsWith("OMX.Intel.") && Build.VERSION.SDK_INT >= 21 && this.enableIntelVp8Encoder);
    }

    private boolean isHardwareSupportedInCurrentSdkVp9(MediaCodecInfo info) {
        String name = info.getName();
        return (name.startsWith("OMX.qcom.") || name.startsWith("OMX.Exynos.")) && Build.VERSION.SDK_INT >= 24;
    }

    private boolean isHardwareSupportedInCurrentSdkH264(MediaCodecInfo info) {
        if (H264_HW_EXCEPTION_MODELS.contains(Build.MODEL)) {
            Logging.d("", "H264_HW_EXCEPTION_MODELS Error");
            return false;
        }
        String name = info.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("name.startsWith(QCOM_PREFIX)=>");
        sb.append(name.startsWith("OMX.qcom."));
        sb.append(" Build.VERSION.SDK_INT=>");
        int i = Build.VERSION.SDK_INT;
        sb.append(i);
        sb.append(" name.startsWith(EXYNOS_PREFIX)=>");
        sb.append(name.startsWith("OMX.Exynos."));
        sb.append(" Build.VERSION_CODES.KITKAT=>");
        sb.append(19);
        sb.append(" Build.VERSION_CODES.LOLLIPOP");
        sb.append(21);
        sb.append(" name=>");
        sb.append(name);
        sb.append(" QCOM_PREFIX=>");
        sb.append("OMX.qcom.");
        sb.append(" EXYNOS_PREFIX=>");
        sb.append("OMX.Exynos.");
        Logging.d("", sb.toString());
        if ((!name.startsWith("OMX.qcom.") || i < 19) && (!name.startsWith("OMX.Exynos.") || i < 21)) {
            return false;
        }
        return true;
    }

    private boolean isHardwareSupportedInCurrentSdkH265(MediaCodecInfo info) {
        String name = info.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("name.startsWith(QCOM_PREFIX)=>");
        sb.append(name.startsWith("OMX.qcom."));
        sb.append(" Build.VERSION.SDK_INT=>");
        int i = Build.VERSION.SDK_INT;
        sb.append(i);
        sb.append(" name.startsWith(EXYNOS_PREFIX)=>");
        sb.append(name.startsWith("OMX.Exynos."));
        sb.append(" Build.VERSION_CODES.KITKAT=>");
        sb.append(19);
        sb.append(" Build.VERSION_CODES.LOLLIPOP");
        sb.append(21);
        sb.append(" name=>");
        sb.append(name);
        Logging.d("", sb.toString());
        return (name.startsWith("OMX.qcom.") && i >= 19) || (name.startsWith("OMX.Exynos.") && i >= 21);
    }

    private String[] supportedHwCodecPrefixes() {
        ArrayList<String> supportedPrefixes = new ArrayList<>();
        supportedPrefixes.add("OMX.qcom.");
        supportedPrefixes.add("OMX.Intel.");
        supportedPrefixes.add("OMX.Exynos.");
        supportedPrefixes.add("OMX.google.");
        supportedPrefixes.add("OMX.hisi.");
        supportedPrefixes.add("OMX.MTK.");
        return (String[]) supportedPrefixes.toArray(new String[supportedPrefixes.size()]);
    }

    private boolean startPrefixesWith(String name) {
        for (String codec : supportedHwCodecPrefixes()) {
            if (name.startsWith(codec)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMediaCodecAllowed(MediaCodecInfo info) {
        Predicate<MediaCodecInfo> predicate = this.codecAllowedPredicate;
        if (predicate == null) {
            return true;
        }
        return predicate.test(info);
    }

    private int getKeyFrameIntervalSec(VideoCodecMimeType type) {
        switch (AnonymousClass1.$SwitchMap$org$webrtc$VideoCodecMimeType[type.ordinal()]) {
            case 1:
            case 2:
            case 4:
                return 100;
            case 3:
            case 5:
                return 20;
            default:
                throw new IllegalArgumentException("Unsupported VideoCodecMimeType " + type);
        }
    }

    private int getForcedKeyFrameIntervalMs(VideoCodecMimeType type, String codecName) {
        if (type != VideoCodecMimeType.VP8 || !codecName.startsWith("OMX.qcom.")) {
            return 0;
        }
        int i = Build.VERSION.SDK_INT;
        if (i == 21 || i == 22) {
            return 15000;
        }
        if (i == 23) {
            return 20000;
        }
        if (i > 23) {
            return 15000;
        }
        return 0;
    }

    private BitrateAdjuster createBitrateAdjuster(VideoCodecMimeType type, String codecName) {
        if (!codecName.startsWith("OMX.Exynos.")) {
            return new BaseBitrateAdjuster();
        }
        if (type == VideoCodecMimeType.VP8) {
            return new DynamicBitrateAdjuster();
        }
        return new FramerateBitrateAdjuster();
    }

    private boolean isH264HighProfileSupported(MediaCodecInfo info) {
        return this.enableH264HighProfile && Build.VERSION.SDK_INT > 23 && info.getName().startsWith("OMX.Exynos.");
    }
}
