package androidx.camera.view.transform;

import android.graphics.Matrix;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Logger;
import androidx.camera.view.TransformExperimental;
import androidx.camera.view.TransformUtils;

@TransformExperimental
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CoordinateTransform {
    private static final String MISMATCH_MSG = "The source viewport (%s) does not match the target viewport (%s). Please make sure they are from the same UseCaseGroup.";
    private static final String TAG = "CoordinateTransform";
    private final Matrix mMatrix;

    public CoordinateTransform(@NonNull OutputTransform source, @NonNull OutputTransform target) {
        if (!TransformUtils.isAspectRatioMatchingWithRoundingError(source.getViewPortSize(), false, target.getViewPortSize(), false)) {
            Logger.w(TAG, String.format(MISMATCH_MSG, new Object[]{source.getViewPortSize(), target.getViewPortSize()}));
        }
        Matrix matrix = new Matrix();
        this.mMatrix = matrix;
        source.getMatrix().invert(matrix);
        matrix.postConcat(target.getMatrix());
    }

    public void getTransform(@NonNull Matrix matrix) {
        matrix.set(this.mMatrix);
    }

    public void mapPoints(@NonNull float[] points) {
        this.mMatrix.mapPoints(points);
    }
}
