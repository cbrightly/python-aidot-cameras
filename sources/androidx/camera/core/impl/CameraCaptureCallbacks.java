package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CameraCaptureCallbacks {
    private CameraCaptureCallbacks() {
    }

    @NonNull
    public static CameraCaptureCallback createNoOpCallback() {
        return new NoOpCameraCaptureCallback();
    }

    @NonNull
    static CameraCaptureCallback createComboCallback(@NonNull List<CameraCaptureCallback> callbacks) {
        if (callbacks.isEmpty()) {
            return createNoOpCallback();
        }
        if (callbacks.size() == 1) {
            return callbacks.get(0);
        }
        return new ComboCameraCaptureCallback(callbacks);
    }

    @NonNull
    public static CameraCaptureCallback createComboCallback(@NonNull CameraCaptureCallback... callbacks) {
        return createComboCallback((List<CameraCaptureCallback>) Arrays.asList(callbacks));
    }

    public static final class NoOpCameraCaptureCallback extends CameraCaptureCallback {
        NoOpCameraCaptureCallback() {
        }

        public void onCaptureCompleted(@NonNull CameraCaptureResult cameraCaptureResult) {
        }

        public void onCaptureFailed(@NonNull CameraCaptureFailure failure) {
        }
    }

    public static final class ComboCameraCaptureCallback extends CameraCaptureCallback {
        private final List<CameraCaptureCallback> mCallbacks = new ArrayList();

        ComboCameraCaptureCallback(@NonNull List<CameraCaptureCallback> callbacks) {
            for (CameraCaptureCallback callback : callbacks) {
                if (!(callback instanceof NoOpCameraCaptureCallback)) {
                    this.mCallbacks.add(callback);
                }
            }
        }

        public void onCaptureCompleted(@NonNull CameraCaptureResult result) {
            for (CameraCaptureCallback callback : this.mCallbacks) {
                callback.onCaptureCompleted(result);
            }
        }

        public void onCaptureFailed(@NonNull CameraCaptureFailure failure) {
            for (CameraCaptureCallback callback : this.mCallbacks) {
                callback.onCaptureFailed(failure);
            }
        }

        public void onCaptureCancelled() {
            for (CameraCaptureCallback callback : this.mCallbacks) {
                callback.onCaptureCancelled();
            }
        }

        @NonNull
        public List<CameraCaptureCallback> getCallbacks() {
            return this.mCallbacks;
        }
    }
}
