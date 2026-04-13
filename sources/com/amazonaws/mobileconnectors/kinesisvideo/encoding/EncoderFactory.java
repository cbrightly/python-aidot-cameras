package com.amazonaws.mobileconnectors.kinesisvideo.encoding;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;

public class EncoderFactory {
    private static final int IFRAME_EVERY_2_SEC = 2;
    private static final MediaCrypto NULL_CRYPTO = null;
    private static final Surface NULL_SURFACE = null;
    private static final String TAG = EncoderFactory.class.getSimpleName();

    public static MediaCodec createConfiguredEncoder(CameraMediaSourceConfiguration mediaSourceConfiguration) {
        return createMediaCodec(mediaSourceConfiguration);
    }

    private static MediaCodec createMediaCodec(CameraMediaSourceConfiguration mediaSourceConfiguration) {
        try {
            MediaCodec encoder = MediaCodec.createEncoderByType(mediaSourceConfiguration.getEncoderMimeType());
            try {
                encoder.configure(configureMediaFormat(mediaSourceConfiguration, 21), NULL_SURFACE, NULL_CRYPTO, 1);
                logSupportedColorFormats(encoder, mediaSourceConfiguration);
            } catch (MediaCodec.CodecException e) {
                Log.d(TAG, "Failed configuring MediaCodec with Semi-planar pixel format, falling back to planar");
                encoder.configure(configureMediaFormat(mediaSourceConfiguration, 19), NULL_SURFACE, NULL_CRYPTO, 1);
                logSupportedColorFormats(encoder, mediaSourceConfiguration);
            }
            return encoder;
        } catch (IOException e2) {
            throw new RuntimeException("unable to create encoder", e2);
        }
    }

    private static MediaFormat configureMediaFormat(CameraMediaSourceConfiguration mediaSourceConfiguration, int colorFormat) {
        String str = TAG;
        Log.d(str, mediaSourceConfiguration.getEncoderMimeType() + " output " + mediaSourceConfiguration.getHorizontalResolution() + "x" + mediaSourceConfiguration.getVerticalResolution() + " @" + mediaSourceConfiguration.getBitRate());
        MediaFormat format = MediaFormat.createVideoFormat(mediaSourceConfiguration.getEncoderMimeType(), mediaSourceConfiguration.getHorizontalResolution(), mediaSourceConfiguration.getVerticalResolution());
        format.setInteger("color-format", colorFormat);
        format.setString(IMediaFormat.KEY_MIME, mediaSourceConfiguration.getEncoderMimeType());
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, mediaSourceConfiguration.getBitRate());
        format.setInteger("frame-rate", mediaSourceConfiguration.getFrameRate());
        format.setInteger("i-frame-interval", 2);
        Log.d(str, "format: " + format);
        return format;
    }

    private static void logSupportedColorFormats(MediaCodec encoder, CameraMediaSourceConfiguration mediaSourceConfiguration) {
        int[] colorFormats = encoder.getCodecInfo().getCapabilitiesForType(mediaSourceConfiguration.getEncoderMimeType()).colorFormats;
        List<Integer> formatsList = new ArrayList<>(colorFormats.length);
        for (int fmt : colorFormats) {
            formatsList.add(Integer.valueOf(fmt));
        }
        Log.d(TAG, "Supported color formats: " + formatsList.toString());
    }
}
