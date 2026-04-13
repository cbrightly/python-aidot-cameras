package androidx.camera.core.impl;

import android.os.Handler;
import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

public final class AutoValue_CameraThreadConfig extends CameraThreadConfig {
    private final Executor cameraExecutor;
    private final Handler schedulerHandler;

    AutoValue_CameraThreadConfig(Executor cameraExecutor2, Handler schedulerHandler2) {
        if (cameraExecutor2 != null) {
            this.cameraExecutor = cameraExecutor2;
            if (schedulerHandler2 != null) {
                this.schedulerHandler = schedulerHandler2;
                return;
            }
            throw new NullPointerException("Null schedulerHandler");
        }
        throw new NullPointerException("Null cameraExecutor");
    }

    @NonNull
    public Executor getCameraExecutor() {
        return this.cameraExecutor;
    }

    @NonNull
    public Handler getSchedulerHandler() {
        return this.schedulerHandler;
    }

    public String toString() {
        return "CameraThreadConfig{cameraExecutor=" + this.cameraExecutor + ", schedulerHandler=" + this.schedulerHandler + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CameraThreadConfig)) {
            return false;
        }
        CameraThreadConfig that = (CameraThreadConfig) o;
        if (!this.cameraExecutor.equals(that.getCameraExecutor()) || !this.schedulerHandler.equals(that.getSchedulerHandler())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.cameraExecutor.hashCode()) * 1000003) ^ this.schedulerHandler.hashCode();
    }
}
