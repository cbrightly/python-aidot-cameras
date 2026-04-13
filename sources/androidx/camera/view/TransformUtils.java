package androidx.camera.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TransformUtils {
    public static final RectF NORMALIZED_RECT = new RectF(-1.0f, -1.0f, 1.0f, 1.0f);

    private TransformUtils() {
    }

    @NonNull
    public static Size rectToSize(@NonNull Rect rect) {
        return new Size(rect.width(), rect.height());
    }

    @NonNull
    public static RectF verticesToRect(@NonNull float[] vertices) {
        return new RectF(min(vertices[0], vertices[2], vertices[4], vertices[6]), min(vertices[1], vertices[3], vertices[5], vertices[7]), max(vertices[0], vertices[2], vertices[4], vertices[6]), max(vertices[1], vertices[3], vertices[5], vertices[7]));
    }

    public static float max(float value1, float value2, float value3, float value4) {
        return Math.max(Math.max(value1, value2), Math.max(value3, value4));
    }

    public static float min(float value1, float value2, float value3, float value4) {
        return Math.min(Math.min(value1, value2), Math.min(value3, value4));
    }

    public static int surfaceRotationToRotationDegrees(int rotationValue) {
        switch (rotationValue) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                throw new IllegalStateException("Unexpected rotation value " + rotationValue);
        }
    }

    public static boolean is90or270(int rotationDegrees) {
        if (rotationDegrees == 90 || rotationDegrees == 270) {
            return true;
        }
        if (rotationDegrees == 0 || rotationDegrees == 180) {
            return false;
        }
        throw new IllegalArgumentException("Invalid rotation degrees: " + rotationDegrees);
    }

    @NonNull
    public static float[] sizeToVertices(@NonNull Size size) {
        return new float[]{0.0f, 0.0f, (float) size.getWidth(), 0.0f, (float) size.getWidth(), (float) size.getHeight(), 0.0f, (float) size.getHeight()};
    }

    @NonNull
    public static float[] rectToVertices(@NonNull RectF rectF) {
        float f = rectF.left;
        float f2 = rectF.top;
        float f3 = rectF.right;
        float f4 = rectF.bottom;
        return new float[]{f, f2, f3, f2, f3, f4, f, f4};
    }

    public static boolean isAspectRatioMatchingWithRoundingError(@NonNull Size size1, boolean isAccurate1, @NonNull Size size2, boolean isAccurate2) {
        float ratio1LowerBound;
        float ratio1UpperBound;
        float ratio2UpperBound;
        float ratio2UpperBound2;
        if (isAccurate1) {
            ratio1UpperBound = ((float) size1.getWidth()) / ((float) size1.getHeight());
            ratio1LowerBound = ratio1UpperBound;
        } else {
            ratio1UpperBound = (((float) size1.getWidth()) + 1.0f) / (((float) size1.getHeight()) - 1.0f);
            ratio1LowerBound = (((float) size1.getWidth()) - 1.0f) / (((float) size1.getHeight()) + 1.0f);
        }
        if (isAccurate2) {
            ratio2UpperBound2 = ((float) size2.getWidth()) / ((float) size2.getHeight());
            ratio2UpperBound = ratio2UpperBound2;
        } else {
            float width = (((float) size2.getWidth()) + 1.0f) / (((float) size2.getHeight()) - 1.0f);
            ratio2UpperBound = (((float) size2.getWidth()) - 1.0f) / (((float) size2.getHeight()) + 1.0f);
            ratio2UpperBound2 = width;
        }
        return ratio1UpperBound >= ratio2UpperBound && ratio2UpperBound2 >= ratio1LowerBound;
    }

    @NonNull
    public static Matrix getRectToRect(@NonNull RectF source, @NonNull RectF target, int rotationDegrees) {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(source, NORMALIZED_RECT, Matrix.ScaleToFit.FILL);
        matrix.postRotate((float) rotationDegrees);
        matrix.postConcat(getNormalizedToBuffer(target));
        return matrix;
    }

    @NonNull
    public static Matrix getNormalizedToBuffer(@NonNull Rect viewPortRect) {
        return getNormalizedToBuffer(new RectF(viewPortRect));
    }

    @NonNull
    private static Matrix getNormalizedToBuffer(@NonNull RectF viewPortRect) {
        Matrix normalizedToBuffer = new Matrix();
        normalizedToBuffer.setRectToRect(NORMALIZED_RECT, viewPortRect, Matrix.ScaleToFit.FILL);
        return normalizedToBuffer;
    }

    @NonNull
    public static Matrix getExifTransform(int exifOrientation, int width, int height) {
        Matrix matrix = new Matrix();
        RectF rect = new RectF(0.0f, 0.0f, (float) width, (float) height);
        RectF rectF = NORMALIZED_RECT;
        matrix.setRectToRect(rect, rectF, Matrix.ScaleToFit.FILL);
        boolean isWidthHeightSwapped = false;
        switch (exifOrientation) {
            case 2:
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.postRotate(180.0f);
                break;
            case 4:
                matrix.postScale(1.0f, -1.0f);
                break;
            case 5:
                matrix.postScale(-1.0f, 1.0f);
                matrix.postRotate(270.0f);
                isWidthHeightSwapped = true;
                break;
            case 6:
                matrix.postRotate(90.0f);
                isWidthHeightSwapped = true;
                break;
            case 7:
                matrix.postScale(-1.0f, 1.0f);
                matrix.postRotate(90.0f);
                isWidthHeightSwapped = true;
                break;
            case 8:
                matrix.postRotate(270.0f);
                isWidthHeightSwapped = true;
                break;
        }
        RectF restoredRect = isWidthHeightSwapped ? new RectF(0.0f, 0.0f, (float) height, (float) width) : rect;
        Matrix restore = new Matrix();
        restore.setRectToRect(rectF, restoredRect, Matrix.ScaleToFit.FILL);
        matrix.postConcat(restore);
        return matrix;
    }
}
