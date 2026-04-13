package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.util.Size;
import com.amazonaws.kinesisvideo.client.KinesisVideoClient;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import java.util.ArrayList;
import java.util.List;

public class CameraUtils {
    private static final String TAG = CameraUtils.class.getSimpleName();

    public static List<Size> getSupportedResolutions(Context context, String cameraId) {
        return CameraHardwareCapabilitiesHelper.getSupportedResolutionsForYUV420_888(context, cameraId);
    }

    public static List<CameraMediaSourceConfiguration> getCameras(KinesisVideoClient kinesisVideoClient) {
        List<CameraMediaSourceConfiguration> cameras = new ArrayList<>();
        for (MediaSourceConfiguration.Builder mediaSourceConfiguration : kinesisVideoClient.listSupportedConfigurations()) {
            if (mediaSourceConfiguration instanceof CameraMediaSourceConfiguration.Builder) {
                cameras.add(((CameraMediaSourceConfiguration.Builder) mediaSourceConfiguration).build());
            }
        }
        return cameras;
    }

    public static List<MediaSourceConfiguration.Builder<? extends MediaSourceConfiguration>> getSupportedCameraConfigrations(Context context) {
        List<MediaSourceConfiguration.Builder<? extends MediaSourceConfiguration>> cameraConfigurations = new ArrayList<>();
        for (String cameraId : getCameraIds(context)) {
            cameraConfigurations.add(DefaultCameraConfigurationHelper.getSupportedCameraConfiguration(context, cameraId));
        }
        return cameraConfigurations;
    }

    public static String[] getCameraIds(Context context) {
        try {
            return ((CameraManager) context.getSystemService("camera")).getCameraIdList();
        } catch (CameraAccessException e) {
            Log.e(TAG, "unable to list cameras", e);
            throw new RuntimeException("unable to list cameras", e);
        }
    }

    private CameraUtils() {
    }
}
