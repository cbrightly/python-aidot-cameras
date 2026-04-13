package androidx.camera.view.transform;

import android.graphics.Matrix;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.view.TransformExperimental;

@TransformExperimental
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class OutputTransform {
    @NonNull
    final Matrix mMatrix;
    @NonNull
    final Size mViewPortSize;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public OutputTransform(@NonNull Matrix matrix, @NonNull Size viewPortSize) {
        this.mMatrix = matrix;
        this.mViewPortSize = viewPortSize;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Matrix getMatrix() {
        return this.mMatrix;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Size getViewPortSize() {
        return this.mViewPortSize;
    }
}
