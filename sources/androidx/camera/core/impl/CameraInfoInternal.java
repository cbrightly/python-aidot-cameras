package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraInfo;
import java.util.concurrent.Executor;

public interface CameraInfoInternal extends CameraInfo {
    void addSessionCaptureCallback(@NonNull Executor executor, @NonNull CameraCaptureCallback cameraCaptureCallback);

    @NonNull
    CamcorderProfileProvider getCamcorderProfileProvider();

    @NonNull
    String getCameraId();

    @NonNull
    Quirks getCameraQuirks();

    @Nullable
    Integer getLensFacing();

    void removeSessionCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback);
}
