package androidx.camera.core.impl;

import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import java.util.concurrent.Executor;

@AutoValue
public abstract class CameraThreadConfig {
    @NonNull
    public abstract Executor getCameraExecutor();

    @NonNull
    public abstract Handler getSchedulerHandler();

    @NonNull
    public static CameraThreadConfig create(@NonNull Executor cameraExecutor, @NonNull Handler schedulerHandler) {
        return new AutoValue_CameraThreadConfig(cameraExecutor, schedulerHandler);
    }
}
