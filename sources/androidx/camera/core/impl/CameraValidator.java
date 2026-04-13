package androidx.camera.core.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Logger;

public final class CameraValidator {
    private static final String TAG = "CameraValidator";

    private CameraValidator() {
    }

    public static void validateCameras(@NonNull Context context, @NonNull CameraRepository cameraRepository, @Nullable CameraSelector availableCamerasSelector) {
        Integer lensFacing = null;
        if (availableCamerasSelector != null) {
            try {
                Integer lensFacing2 = availableCamerasSelector.getLensFacing();
                lensFacing = lensFacing2;
                if (lensFacing2 == null) {
                    Logger.w(TAG, "No lens facing info in the availableCamerasSelector, don't verify the camera lens facing.");
                    return;
                }
            } catch (IllegalStateException e) {
                Logger.e(TAG, "Cannot get lens facing from the availableCamerasSelector don't verify the camera lens facing.", e);
                return;
            }
        }
        Logger.d(TAG, "Verifying camera lens facing on " + Build.DEVICE + ", lensFacingInteger: " + lensFacing);
        PackageManager pm = context.getPackageManager();
        try {
            if (pm.hasSystemFeature("android.hardware.camera") && (availableCamerasSelector == null || lensFacing.intValue() == 1)) {
                CameraSelector.DEFAULT_BACK_CAMERA.select(cameraRepository.getCameras());
            }
            if (!pm.hasSystemFeature("android.hardware.camera.front")) {
                return;
            }
            if (availableCamerasSelector == null || lensFacing.intValue() == 0) {
                CameraSelector.DEFAULT_FRONT_CAMERA.select(cameraRepository.getCameras());
            }
        } catch (IllegalArgumentException e2) {
            Logger.e(TAG, "Camera LensFacing verification failed, existing cameras: " + cameraRepository.getCameras());
            throw new CameraIdListIncorrectException("Expected camera missing from device.", e2);
        }
    }

    public static class CameraIdListIncorrectException extends Exception {
        public CameraIdListIncorrectException(@Nullable String message, @Nullable Throwable cause) {
            super(message, cause);
        }
    }
}
