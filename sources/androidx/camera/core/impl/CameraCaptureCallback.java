package androidx.camera.core.impl;

import androidx.annotation.NonNull;

public abstract class CameraCaptureCallback {
    public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
    }

    public void onCaptureFailed(@NonNull CameraCaptureFailure failure) {
    }

    public void onCaptureCancelled() {
    }
}
