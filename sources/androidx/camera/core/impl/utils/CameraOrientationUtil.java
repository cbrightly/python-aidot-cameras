package androidx.camera.core.impl.utils;

import androidx.camera.core.Logger;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

public final class CameraOrientationUtil {
    private static final String TAG = "CameraOrientationUtil";

    private CameraOrientationUtil() {
    }

    public static int getRelativeImageRotation(int destRotationDegrees, int sourceRotationDegrees, boolean isOppositeFacing) {
        int result;
        if (isOppositeFacing) {
            result = ((sourceRotationDegrees - destRotationDegrees) + 360) % 360;
        } else {
            result = (sourceRotationDegrees + destRotationDegrees) % 360;
        }
        if (Logger.isDebugEnabled(TAG)) {
            Logger.d(TAG, String.format("getRelativeImageRotation: destRotationDegrees=%s, sourceRotationDegrees=%s, isOppositeFacing=%s, result=%s", new Object[]{Integer.valueOf(destRotationDegrees), Integer.valueOf(sourceRotationDegrees), Boolean.valueOf(isOppositeFacing), Integer.valueOf(result)}));
        }
        return result;
    }

    public static int surfaceRotationToDegrees(int rotation) {
        switch (rotation) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                throw new IllegalArgumentException("Unsupported surface rotation: " + rotation);
        }
    }

    public static int degreesToSurfaceRotation(int degrees) {
        switch (degrees) {
            case 0:
                return 0;
            case 90:
                return 1;
            case 180:
                return 2;
            case SubsamplingScaleImageView.ORIENTATION_270 /*270*/:
                return 3;
            default:
                throw new IllegalStateException("Invalid sensor rotation: " + degrees);
        }
    }
}
