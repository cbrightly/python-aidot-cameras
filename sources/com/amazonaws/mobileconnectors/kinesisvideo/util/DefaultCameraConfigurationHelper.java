package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.media.MediaCodecInfo;
import android.util.Log;
import android.util.Size;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.producer.Time;
import com.amazonaws.mobileconnectors.kinesisvideo.data.MimeType;
import java.util.List;

public class DefaultCameraConfigurationHelper {
    private static final int DEFAULT_BITRATE = 1000000;
    private static final int DEFAULT_FRAMERATE = 25;
    private static final int DEFAULT_GOP_DURATION = 2000;
    private static final int DEFAULT_TIMESCALE = 1000000;
    private static final Size RESOLUTION_640x480 = new Size(640, 480);
    private static final String TAG = DefaultCameraConfigurationHelper.class.getSimpleName();
    private static final Size ZERO_RESOLUTION = new Size(0, 0);

    public static CameraMediaSourceConfiguration.Builder getSupportedCameraConfiguration(Context context, String cameraId) {
        CameraCharacteristics cameraInfo = CameraHardwareCapabilitiesHelper.getCameraInfo(context, cameraId);
        Size supportedResolution = getSupportedResolution(context, cameraId);
        MediaCodecInfo supportedEncoder = VideoEncoderUtils.getSupportedEncoder();
        String encoderMimeType = getMimeType(supportedEncoder);
        return CameraMediaSourceConfiguration.builder().withCameraId(cameraId).withCameraFacing(getFacing(cameraInfo)).withCameraOrientation(getOrientation(cameraInfo)).withHorizontalResolution(supportedResolution.getWidth()).withVerticalResolution(supportedResolution.getHeight()).withGopDurationMillis(2000).withFrameTimeScale(Time.NANOS_IN_A_MILLISECOND).withEncodingBitRate(1000000).withFrameRate(25).withEncodingMimeType(encoderMimeType).withIsEncoderHardwareAccelerated(VideoEncoderUtils.isHardwareAccelerated(supportedEncoder));
    }

    private static Size getSupportedResolution(Context context, String cameraId) {
        List<Size> supportedResolutions = CameraHardwareCapabilitiesHelper.getSupportedResolutionsForYUV420_888(context, cameraId);
        Size under640 = select640orBelow(supportedResolutions);
        return ZERO_RESOLUTION.equals(under640) ? supportedResolutions.get(0) : under640;
    }

    private static Size select640orBelow(List<Size> resolutions) {
        Size tmpSize = ZERO_RESOLUTION;
        for (Size resolution : resolutions) {
            int width = resolution.getWidth();
            Size size = RESOLUTION_640x480;
            if (width <= size.getWidth() && tmpSize.getWidth() <= resolution.getWidth() && resolution.getHeight() <= size.getHeight() && tmpSize.getHeight() <= resolution.getHeight()) {
                tmpSize = resolution;
            }
        }
        return tmpSize;
    }

    private static String getMimeType(MediaCodecInfo encoder) {
        if (isH264(encoder)) {
            return MimeType.H264.getMimeType();
        }
        return pickAnySupportedMime(encoder);
    }

    private static boolean isH264(MediaCodecInfo encoder) {
        for (String encoderMime : encoder.getSupportedTypes()) {
            if (MimeType.H264.getMimeType().equalsIgnoreCase(encoderMime)) {
                return true;
            }
        }
        return false;
    }

    private static String pickAnySupportedMime(MediaCodecInfo encoder) {
        for (MimeType supportedMime : MimeType.values()) {
            for (String encoderMime : encoder.getSupportedTypes()) {
                if (supportedMime.getMimeType().equals(encoderMime)) {
                    return supportedMime.getMimeType();
                }
            }
        }
        Log.e(TAG, "Could not find supported mime type, choosing any type supported by encoder");
        return encoder.getSupportedTypes()[0];
    }

    private static int getFacing(CameraCharacteristics cameraInfo) {
        return ((Integer) cameraInfo.get(CameraCharacteristics.LENS_FACING)).intValue();
    }

    private static int getOrientation(CameraCharacteristics cameraInfo) {
        return ((Integer) cameraInfo.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
    }
}
