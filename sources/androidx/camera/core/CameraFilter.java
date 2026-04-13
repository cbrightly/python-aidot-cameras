package androidx.camera.core;

import androidx.annotation.NonNull;
import java.util.List;

@ExperimentalCameraFilter
public interface CameraFilter {
    @NonNull
    List<CameraInfo> filter(@NonNull List<CameraInfo> list);
}
