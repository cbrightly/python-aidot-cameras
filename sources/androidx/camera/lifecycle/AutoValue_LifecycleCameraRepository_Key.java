package androidx.camera.lifecycle;

import androidx.annotation.NonNull;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.camera.lifecycle.LifecycleCameraRepository;
import androidx.lifecycle.LifecycleOwner;

public final class AutoValue_LifecycleCameraRepository_Key extends LifecycleCameraRepository.Key {
    private final CameraUseCaseAdapter.CameraId cameraId;
    private final LifecycleOwner lifecycleOwner;

    AutoValue_LifecycleCameraRepository_Key(LifecycleOwner lifecycleOwner2, CameraUseCaseAdapter.CameraId cameraId2) {
        if (lifecycleOwner2 != null) {
            this.lifecycleOwner = lifecycleOwner2;
            if (cameraId2 != null) {
                this.cameraId = cameraId2;
                return;
            }
            throw new NullPointerException("Null cameraId");
        }
        throw new NullPointerException("Null lifecycleOwner");
    }

    @NonNull
    public LifecycleOwner getLifecycleOwner() {
        return this.lifecycleOwner;
    }

    @NonNull
    public CameraUseCaseAdapter.CameraId getCameraId() {
        return this.cameraId;
    }

    public String toString() {
        return "Key{lifecycleOwner=" + this.lifecycleOwner + ", cameraId=" + this.cameraId + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LifecycleCameraRepository.Key)) {
            return false;
        }
        LifecycleCameraRepository.Key that = (LifecycleCameraRepository.Key) o;
        if (!this.lifecycleOwner.equals(that.getLifecycleOwner()) || !this.cameraId.equals(that.getCameraId())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.lifecycleOwner.hashCode()) * 1000003) ^ this.cameraId.hashCode();
    }
}
