package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LiveData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface CameraInfo {
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String IMPLEMENTATION_TYPE_CAMERA2 = "androidx.camera.camera2";
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String IMPLEMENTATION_TYPE_CAMERA2_LEGACY = "androidx.camera.camera2.legacy";
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String IMPLEMENTATION_TYPE_FAKE = "androidx.camera.fake";
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String IMPLEMENTATION_TYPE_UNKNOWN = "<unknown>";

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImplementationType {
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    LiveData<CameraState> getCameraState();

    @ExperimentalExposureCompensation
    @NonNull
    ExposureState getExposureState();

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    String getImplementationType();

    int getSensorRotationDegrees();

    int getSensorRotationDegrees(int i);

    @NonNull
    LiveData<Integer> getTorchState();

    @NonNull
    LiveData<ZoomState> getZoomState();

    boolean hasFlashUnit();
}
