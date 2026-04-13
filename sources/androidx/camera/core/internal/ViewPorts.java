package androidx.camera.core.internal;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.camera.core.UseCase;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

public class ViewPorts {
    private ViewPorts() {
    }

    @NonNull
    public static Map<UseCase, Rect> calculateViewPortRects(@NonNull Rect fullSensorRect, boolean isFrontCamera, @NonNull Rational viewPortAspectRatio, @IntRange(from = 0, to = 359) int outputRotationDegrees, int scaleType, int layoutDirection, @NonNull Map<UseCase, Size> useCaseSizes) {
        Rect rect = fullSensorRect;
        Preconditions.checkArgument(fullSensorRect.width() > 0 && fullSensorRect.height() > 0, "Cannot compute viewport crop rects zero sized sensor rect.");
        RectF fullSensorRectF = new RectF(fullSensorRect);
        Map<UseCase, Matrix> useCaseToSensorTransformations = new HashMap<>();
        RectF sensorIntersectionRect = new RectF(fullSensorRect);
        for (Map.Entry<UseCase, Size> entry : useCaseSizes.entrySet()) {
            Matrix useCaseToSensorTransformation = new Matrix();
            RectF srcRect = new RectF(0.0f, 0.0f, (float) entry.getValue().getWidth(), (float) entry.getValue().getHeight());
            useCaseToSensorTransformation.setRectToRect(srcRect, fullSensorRectF, Matrix.ScaleToFit.CENTER);
            useCaseToSensorTransformations.put(entry.getKey(), useCaseToSensorTransformation);
            RectF useCaseSensorRect = new RectF();
            useCaseToSensorTransformation.mapRect(useCaseSensorRect, srcRect);
            sensorIntersectionRect.intersect(useCaseSensorRect);
        }
        RectF viewPortRect = getScaledRect(sensorIntersectionRect, ImageUtil.getRotatedAspectRatio(outputRotationDegrees, viewPortAspectRatio), scaleType, isFrontCamera, layoutDirection, outputRotationDegrees);
        Map<UseCase, Rect> useCaseOutputRects = new HashMap<>();
        RectF useCaseOutputRect = new RectF();
        Matrix sensorToUseCaseTransformation = new Matrix();
        for (Map.Entry<UseCase, Matrix> entry2 : useCaseToSensorTransformations.entrySet()) {
            entry2.getValue().invert(sensorToUseCaseTransformation);
            sensorToUseCaseTransformation.mapRect(useCaseOutputRect, viewPortRect);
            Rect outputCropRect = new Rect();
            useCaseOutputRect.round(outputCropRect);
            useCaseOutputRects.put(entry2.getKey(), outputCropRect);
        }
        return useCaseOutputRects;
    }

    @SuppressLint({"SwitchIntDef"})
    @NonNull
    public static RectF getScaledRect(@NonNull RectF fittingRect, @NonNull Rational containerAspectRatio, int scaleType, boolean isFrontCamera, int layoutDirection, @IntRange(from = 0, to = 359) int rotationDegrees) {
        if (scaleType == 3) {
            return fittingRect;
        }
        Matrix viewPortToSurfaceTransformation = new Matrix();
        RectF viewPortRect = new RectF(0.0f, 0.0f, (float) containerAspectRatio.getNumerator(), (float) containerAspectRatio.getDenominator());
        switch (scaleType) {
            case 0:
                viewPortToSurfaceTransformation.setRectToRect(viewPortRect, fittingRect, Matrix.ScaleToFit.START);
                break;
            case 1:
                viewPortToSurfaceTransformation.setRectToRect(viewPortRect, fittingRect, Matrix.ScaleToFit.CENTER);
                break;
            case 2:
                viewPortToSurfaceTransformation.setRectToRect(viewPortRect, fittingRect, Matrix.ScaleToFit.END);
                break;
            default:
                throw new IllegalStateException("Unexpected scale type: " + scaleType);
        }
        RectF viewPortRectInSurfaceCoordinates = new RectF();
        viewPortToSurfaceTransformation.mapRect(viewPortRectInSurfaceCoordinates, viewPortRect);
        return correctStartOrEnd(shouldMirrorStartAndEnd(isFrontCamera, layoutDirection), rotationDegrees, fittingRect, viewPortRectInSurfaceCoordinates);
    }

    private static RectF correctStartOrEnd(boolean isMirrored, @IntRange(from = 0, to = 359) int rotationDegrees, RectF containerRect, RectF cropRect) {
        boolean rtlRotation270 = true;
        boolean ltrRotation0 = rotationDegrees == 0 && !isMirrored;
        boolean rtlRotation90 = rotationDegrees == 90 && isMirrored;
        if (ltrRotation0 || rtlRotation90) {
            return cropRect;
        }
        boolean rtlRotation0 = rotationDegrees == 0 && isMirrored;
        boolean ltrRotation270 = rotationDegrees == 270 && !isMirrored;
        if (rtlRotation0 || ltrRotation270) {
            return flipHorizontally(cropRect, containerRect.centerX());
        }
        boolean ltrRotation90 = rotationDegrees == 90 && !isMirrored;
        boolean rtlRotation180 = rotationDegrees == 180 && isMirrored;
        if (ltrRotation90 || rtlRotation180) {
            return flipVertically(cropRect, containerRect.centerY());
        }
        boolean ltrRotation180 = rotationDegrees == 180 && !isMirrored;
        if (rotationDegrees != 270 || !isMirrored) {
            rtlRotation270 = false;
        }
        if (ltrRotation180 || rtlRotation270) {
            return flipHorizontally(flipVertically(cropRect, containerRect.centerY()), containerRect.centerX());
        }
        throw new IllegalArgumentException("Invalid argument: mirrored " + isMirrored + " rotation " + rotationDegrees);
    }

    private static boolean shouldMirrorStartAndEnd(boolean isFrontCamera, int layoutDirection) {
        boolean z = true;
        if (layoutDirection != 1) {
            z = false;
        }
        return z ^ isFrontCamera;
    }

    private static RectF flipHorizontally(RectF original, float flipLineX) {
        return new RectF(flipX(original.right, flipLineX), original.top, flipX(original.left, flipLineX), original.bottom);
    }

    private static RectF flipVertically(RectF original, float flipLineY) {
        return new RectF(original.left, flipY(original.bottom, flipLineY), original.right, flipY(original.top, flipLineY));
    }

    private static float flipX(float x, float flipLineX) {
        return (flipLineX + flipLineX) - x;
    }

    private static float flipY(float y, float flipLineY) {
        return (flipLineY + flipLineY) - y;
    }
}
