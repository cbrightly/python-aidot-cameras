package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.UseCase;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collection;
import java.util.LinkedHashSet;

public interface CameraInternal extends Camera, UseCase.StateChangeCallback {
    void attachUseCases(@NonNull Collection<UseCase> collection);

    void close();

    void detachUseCases(@NonNull Collection<UseCase> collection);

    @NonNull
    CameraControl getCameraControl();

    @NonNull
    CameraControlInternal getCameraControlInternal();

    @NonNull
    CameraInfo getCameraInfo();

    @NonNull
    CameraInfoInternal getCameraInfoInternal();

    @NonNull
    LinkedHashSet<CameraInternal> getCameraInternals();

    @NonNull
    Observable<State> getCameraState();

    @NonNull
    CameraConfig getExtendedConfig();

    void open();

    @NonNull
    ListenableFuture<Void> release();

    void setExtendedConfig(@Nullable CameraConfig cameraConfig);

    public enum State {
        PENDING_OPEN(false),
        OPENING(true),
        OPEN(true),
        CLOSING(true),
        CLOSED(false),
        RELEASING(true),
        RELEASED(false);
        
        private final boolean mHoldsCameraSlot;

        private State(boolean holdsCameraSlot) {
            this.mHoldsCameraSlot = holdsCameraSlot;
        }

        /* access modifiers changed from: package-private */
        public boolean holdsCameraSlot() {
            return this.mHoldsCameraSlot;
        }
    }
}
