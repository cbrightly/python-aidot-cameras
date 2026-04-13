package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.camera.core.CameraControl;
import androidx.camera.core.ExperimentalExposureCompensation;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

public interface CameraControlInternal extends CameraControl {
    public static final CameraControlInternal DEFAULT_EMPTY_INSTANCE = new CameraControlInternal() {
        public int getFlashMode() {
            return 2;
        }

        public void setFlashMode(int flashMode) {
        }

        @NonNull
        public ListenableFuture<Void> enableTorch(boolean torch) {
            return Futures.immediateFuture(null);
        }

        @NonNull
        public ListenableFuture<CameraCaptureResult> triggerAf() {
            return Futures.immediateFuture(CameraCaptureResult.EmptyCameraCaptureResult.create());
        }

        @NonNull
        public ListenableFuture<CameraCaptureResult> triggerAePrecapture() {
            return Futures.immediateFuture(CameraCaptureResult.EmptyCameraCaptureResult.create());
        }

        public void cancelAfAeTrigger(boolean cancelAfTrigger, boolean cancelAePrecaptureTrigger) {
        }

        @ExperimentalExposureCompensation
        @NonNull
        public ListenableFuture<Integer> setExposureCompensationIndex(int exposure) {
            return Futures.immediateFuture(0);
        }

        public void submitCaptureRequests(@NonNull List<CaptureConfig> list) {
        }

        @NonNull
        public Rect getSensorRect() {
            return new Rect();
        }

        @NonNull
        public ListenableFuture<FocusMeteringResult> startFocusAndMetering(@NonNull FocusMeteringAction action) {
            return Futures.immediateFuture(FocusMeteringResult.emptyInstance());
        }

        @NonNull
        public ListenableFuture<Void> cancelFocusAndMetering() {
            return Futures.immediateFuture(null);
        }

        @NonNull
        public ListenableFuture<Void> setZoomRatio(float ratio) {
            return Futures.immediateFuture(null);
        }

        @NonNull
        public ListenableFuture<Void> setLinearZoom(float linearZoom) {
            return Futures.immediateFuture(null);
        }

        public void addInteropConfig(@NonNull Config config) {
        }

        public void clearInteropConfig() {
        }

        @NonNull
        public Config getInteropConfig() {
            return null;
        }
    };

    public interface ControlUpdateCallback {
        void onCameraControlCaptureRequests(@NonNull List<CaptureConfig> list);

        void onCameraControlUpdateSessionConfig(@NonNull SessionConfig sessionConfig);
    }

    void addInteropConfig(@NonNull Config config);

    void cancelAfAeTrigger(boolean z, boolean z2);

    void clearInteropConfig();

    int getFlashMode();

    @NonNull
    Config getInteropConfig();

    @NonNull
    Rect getSensorRect();

    @ExperimentalExposureCompensation
    @NonNull
    ListenableFuture<Integer> setExposureCompensationIndex(int i);

    void setFlashMode(int i);

    void submitCaptureRequests(@NonNull List<CaptureConfig> list);

    @NonNull
    ListenableFuture<CameraCaptureResult> triggerAePrecapture();

    @NonNull
    ListenableFuture<CameraCaptureResult> triggerAf();

    public static final class CameraControlException extends Exception {
        @NonNull
        private CameraCaptureFailure mCameraCaptureFailure;

        public CameraControlException(@NonNull CameraCaptureFailure failure) {
            this.mCameraCaptureFailure = failure;
        }

        public CameraControlException(@NonNull CameraCaptureFailure failure, @NonNull Throwable cause) {
            super(cause);
            this.mCameraCaptureFailure = failure;
        }

        @NonNull
        public CameraCaptureFailure getCameraCaptureFailure() {
            return this.mCameraCaptureFailure;
        }
    }
}
