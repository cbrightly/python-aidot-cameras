package androidx.camera.core;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;

public interface CameraControl {
    @NonNull
    ListenableFuture<Void> cancelFocusAndMetering();

    @NonNull
    ListenableFuture<Void> enableTorch(boolean z);

    @ExperimentalExposureCompensation
    @NonNull
    ListenableFuture<Integer> setExposureCompensationIndex(int i);

    @NonNull
    ListenableFuture<Void> setLinearZoom(@FloatRange(from = 0.0d, to = 1.0d) float f);

    @NonNull
    ListenableFuture<Void> setZoomRatio(float f);

    @NonNull
    ListenableFuture<FocusMeteringResult> startFocusAndMetering(@NonNull FocusMeteringAction focusMeteringAction);

    public static final class OperationCanceledException extends Exception {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public OperationCanceledException(@NonNull String message) {
            super(message);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public OperationCanceledException(@NonNull String message, @NonNull Throwable cause) {
            super(message, cause);
        }
    }
}
