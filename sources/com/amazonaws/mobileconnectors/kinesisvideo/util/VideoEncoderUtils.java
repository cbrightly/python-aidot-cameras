package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import com.amazonaws.mobileconnectors.kinesisvideo.data.MimeType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class VideoEncoderUtils {
    private static final String SOFTWARE_ENCODER_PREFIX = "OMX.google.";

    public static List<MimeType> getSupportedMimeTypes() {
        Set<MimeType> suportedMimeTypes = EnumSet.noneOf(MimeType.class);
        List<MediaCodecInfo> encoders = getSystemSupportedEncoders();
        for (MimeType mimeType : MimeType.values()) {
            for (MediaCodecInfo encoder : encoders) {
                if (supportsMimeAndYUV420(encoder, mimeType)) {
                    suportedMimeTypes.add(mimeType);
                }
            }
        }
        if (suportedMimeTypes.size() != 0) {
            return new ArrayList(suportedMimeTypes);
        }
        throw new RuntimeException("Unable to find encoders for supported types and image format");
    }

    private static boolean supportsMimeType(MediaCodecInfo encoder, MimeType mimeType) {
        for (String encoderSupportedMime : encoder.getSupportedTypes()) {
            if (encoderSupportedMime.equalsIgnoreCase(mimeType.getMimeType())) {
                return true;
            }
        }
        return false;
    }

    private static boolean supportsYUV420(MediaCodecInfo encoder, MimeType mimeType) {
        for (int colorFormat : encoder.getCapabilitiesForType(mimeType.getMimeType()).colorFormats) {
            if (colorFormat == 2135033992) {
                return true;
            }
        }
        return false;
    }

    public static List<MediaCodecInfo> getSystemSupportedEncoders() {
        MediaCodecList codecList = new MediaCodecList(0);
        List<MediaCodecInfo> codecInfoList = new ArrayList<>();
        for (MediaCodecInfo codecInfo : codecList.getCodecInfos()) {
            if (codecInfo.isEncoder()) {
                codecInfoList.add(codecInfo);
            }
        }
        return codecInfoList;
    }

    public static MediaCodecInfo getSupportedEncoder() {
        List<MediaCodecInfo> allSystemEncoders = getSystemSupportedEncoders();
        List<MediaCodecInfo> supportedEncoders = new ArrayList<>();
        List<MediaCodecInfo> supportedHardwareEncoders = new ArrayList<>();
        for (MimeType mimeType : MimeType.values()) {
            for (MediaCodecInfo encoder : allSystemEncoders) {
                if (supportsMimeAndYUV420(encoder, mimeType)) {
                    supportedEncoders.add(encoder);
                    if (isHardwareAccelerated(encoder)) {
                        supportedHardwareEncoders.add(encoder);
                    }
                }
            }
        }
        if (supportedEncoders.size() != 0) {
            return tryChooseBestEncoder(supportedEncoders, supportedHardwareEncoders);
        }
        throw new RuntimeException("Could not found a supported encoder");
    }

    public static boolean isHardwareAccelerated(MediaCodecInfo encoder) {
        return encoder.getName().startsWith(SOFTWARE_ENCODER_PREFIX);
    }

    private static MediaCodecInfo tryChooseBestEncoder(List<MediaCodecInfo> allSupportedEncoders, List<MediaCodecInfo> supportedHardwareEncoders) {
        MediaCodecInfo chosenEncoder;
        if (supportedHardwareEncoders.size() == 0) {
            chosenEncoder = tryFindH264Encoder(allSupportedEncoders);
        } else {
            chosenEncoder = tryFindH264Encoder(supportedHardwareEncoders);
        }
        return chosenEncoder == null ? allSupportedEncoders.get(0) : chosenEncoder;
    }

    private static MediaCodecInfo tryFindH264Encoder(List<MediaCodecInfo> supportedEncoders) {
        for (MediaCodecInfo encoder : supportedEncoders) {
            String[] supportedTypes = encoder.getSupportedTypes();
            int length = supportedTypes.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (MimeType.H264.getMimeType().equals(supportedTypes[i])) {
                        return encoder;
                    }
                    i++;
                }
            }
        }
        return null;
    }

    private static boolean supportsMimeAndYUV420(MediaCodecInfo encoder, MimeType mimeType) {
        return supportsMimeType(encoder, mimeType) && supportsYUV420(encoder, mimeType);
    }
}
