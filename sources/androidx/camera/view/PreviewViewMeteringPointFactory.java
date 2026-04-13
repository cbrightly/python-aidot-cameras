package androidx.camera.view;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Size;
import androidx.annotation.AnyThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.impl.utils.Threads;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class PreviewViewMeteringPointFactory extends MeteringPointFactory {
    static final PointF INVALID_POINT = new PointF(2.0f, 2.0f);
    @GuardedBy("this")
    @Nullable
    private Matrix mMatrix;
    @NonNull
    private final PreviewTransformation mPreviewTransformation;

    PreviewViewMeteringPointFactory(@NonNull PreviewTransformation previewTransformation) {
        this.mPreviewTransformation = previewTransformation;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @AnyThread
    public PointF convertPoint(float x, float y) {
        float[] point = {x, y};
        synchronized (this) {
            Matrix matrix = this.mMatrix;
            if (matrix == null) {
                PointF pointF = INVALID_POINT;
                return pointF;
            }
            matrix.mapPoints(point);
            return new PointF(point[0], point[1]);
        }
    }

    /* access modifiers changed from: package-private */
    @UiThread
    public void recalculate(@NonNull Size previewViewSize, int layoutDirection) {
        Threads.checkMainThread();
        synchronized (this) {
            if (previewViewSize.getWidth() != 0) {
                if (previewViewSize.getHeight() != 0) {
                    this.mMatrix = this.mPreviewTransformation.getPreviewViewToNormalizedSurfaceMatrix(previewViewSize, layoutDirection);
                    return;
                }
            }
            this.mMatrix = null;
        }
    }
}
