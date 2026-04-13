package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import java.util.Arrays;
import java.util.List;

public class CameraHardwareCapabilitiesHelper {
    public static List<Size> getSupportedResolutionsForYUV420_888(Context context, String cameraId) {
        Size[] sizes = ((StreamConfigurationMap) getCameraInfo(context, cameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(35);
        if (sizes != null && sizes.length != 0) {
            return Arrays.asList(sizes);
        }
        throw new RuntimeException("Camera " + cameraId + " does not support YUV420_888");
    }

    public static CameraCharacteristics getCameraInfo(Context context, String cameraId) {
        try {
            return ((CameraManager) context.getSystemService("camera")).getCameraCharacteristics(cameraId);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private CameraHardwareCapabilitiesHelper() {
    }
}
