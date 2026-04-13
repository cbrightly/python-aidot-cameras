package androidx.camera.lifecycle;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.UseCase;

public interface LifecycleCameraProvider extends CameraProvider {
    boolean isBound(@NonNull UseCase useCase);

    void unbind(@NonNull UseCase... useCaseArr);

    void unbindAll();
}
