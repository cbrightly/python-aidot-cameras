package androidx.camera.core.internal.utils;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.CameraOrientationUtil;

public final class UseCaseConfigUtil {
    private UseCaseConfigUtil() {
    }

    public static void updateTargetRotationAndRelatedConfigs(@NonNull UseCaseConfig.Builder<?, ?, ?> builder, int newRotation) {
        Size targetResolution;
        ImageOutputConfig config = (ImageOutputConfig) builder.getUseCaseConfig();
        int oldRotation = config.getTargetRotation(-1);
        if (oldRotation == -1 || oldRotation != newRotation) {
            ((ImageOutputConfig.Builder) builder).setTargetRotation(newRotation);
        }
        if (oldRotation != -1 && newRotation != -1 && oldRotation != newRotation) {
            if (Math.abs(CameraOrientationUtil.surfaceRotationToDegrees(newRotation) - CameraOrientationUtil.surfaceRotationToDegrees(oldRotation)) % 180 == 90 && (targetResolution = config.getTargetResolution((Size) null)) != null) {
                ((ImageOutputConfig.Builder) builder).setTargetResolution(new Size(targetResolution.getHeight(), targetResolution.getWidth()));
            }
        }
    }
}
