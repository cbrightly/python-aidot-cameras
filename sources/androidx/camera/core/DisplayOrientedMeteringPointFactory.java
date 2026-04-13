package androidx.camera.core;

import android.graphics.PointF;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraInfoInternal;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

public final class DisplayOrientedMeteringPointFactory extends MeteringPointFactory {
    @NonNull
    private final CameraInfo mCameraInfo;
    @NonNull
    private final Display mDisplay;
    private final float mHeight;
    private final float mWidth;

    public DisplayOrientedMeteringPointFactory(@NonNull Display display, @NonNull CameraInfo cameraInfo, float width, float height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mDisplay = display;
        this.mCameraInfo = cameraInfo;
    }

    @Nullable
    private Integer getLensFacing() {
        CameraInfo cameraInfo = this.mCameraInfo;
        if (cameraInfo instanceof CameraInfoInternal) {
            return ((CameraInfoInternal) cameraInfo).getLensFacing();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PointF convertPoint(float x, float y) {
        float width = this.mWidth;
        float height = this.mHeight;
        Integer lensFacing = getLensFacing();
        boolean compensateForMirroring = lensFacing != null && lensFacing.intValue() == 0;
        int relativeCameraOrientation = getRelativeCameraOrientation(compensateForMirroring);
        float outputX = x;
        float outputY = y;
        float outputWidth = width;
        float outputHeight = height;
        if (relativeCameraOrientation == 90 || relativeCameraOrientation == 270) {
            outputX = y;
            outputY = x;
            outputWidth = height;
            outputHeight = width;
        }
        switch (relativeCameraOrientation) {
            case 90:
                outputY = outputHeight - outputY;
                break;
            case 180:
                outputX = outputWidth - outputX;
                outputY = outputHeight - outputY;
                break;
            case SubsamplingScaleImageView.ORIENTATION_270 /*270*/:
                outputX = outputWidth - outputX;
                break;
        }
        if (compensateForMirroring) {
            outputX = outputWidth - outputX;
        }
        return new PointF(outputX / outputWidth, outputY / outputHeight);
    }

    private int getRelativeCameraOrientation(boolean compensateForMirroring) {
        try {
            int rotationDegrees = this.mCameraInfo.getSensorRotationDegrees(this.mDisplay.getRotation());
            if (compensateForMirroring) {
                return (360 - rotationDegrees) % 360;
            }
            return rotationDegrees;
        } catch (Exception e) {
            return 0;
        }
    }
}
