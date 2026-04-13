package androidx.camera.core.impl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import java.util.Set;

public interface CameraFactory {

    public interface Provider {
        @NonNull
        CameraFactory newInstance(@NonNull Context context, @NonNull CameraThreadConfig cameraThreadConfig, @Nullable CameraSelector cameraSelector);
    }

    @NonNull
    Set<String> getAvailableCameraIds();

    @NonNull
    CameraInternal getCamera(@NonNull String str);

    @Nullable
    Object getCameraManager();
}
