package androidx.camera.core;

import android.graphics.PointF;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public class SurfaceOrientedMeteringPointFactory extends MeteringPointFactory {
    private final float mHeight;
    private final float mWidth;

    public SurfaceOrientedMeteringPointFactory(float width, float height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public SurfaceOrientedMeteringPointFactory(float width, float height, @NonNull UseCase useCaseForAspectRatio) {
        super(getUseCaseAspectRatio(useCaseForAspectRatio));
        this.mWidth = width;
        this.mHeight = height;
    }

    @Nullable
    private static Rational getUseCaseAspectRatio(@Nullable UseCase useCase) {
        if (useCase == null) {
            return null;
        }
        Size resolution = useCase.getAttachedSurfaceResolution();
        if (resolution != null) {
            return new Rational(resolution.getWidth(), resolution.getHeight());
        }
        throw new IllegalStateException("UseCase " + useCase + " is not bound.");
    }

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PointF convertPoint(float x, float y) {
        return new PointF(x / this.mWidth, y / this.mHeight);
    }
}
