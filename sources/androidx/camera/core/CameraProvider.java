package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface CameraProvider {
    @NonNull
    List<CameraInfo> getAvailableCameraInfos();

    boolean hasCamera(@NonNull CameraSelector cameraSelector);
}
