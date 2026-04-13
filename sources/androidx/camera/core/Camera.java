package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraInternal;
import java.util.LinkedHashSet;

public interface Camera {
    @NonNull
    CameraControl getCameraControl();

    @NonNull
    CameraInfo getCameraInfo();

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    LinkedHashSet<CameraInternal> getCameraInternals();

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    CameraConfig getExtendedConfig();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void setExtendedConfig(@Nullable CameraConfig cameraConfig);
}
