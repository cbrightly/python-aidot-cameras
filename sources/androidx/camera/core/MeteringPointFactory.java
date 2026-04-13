package androidx.camera.core;

import android.graphics.PointF;
import android.util.Rational;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public abstract class MeteringPointFactory {
    @Nullable
    private Rational mSurfaceAspectRatio;

    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract PointF convertPoint(float f, float f2);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MeteringPointFactory() {
        this((Rational) null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MeteringPointFactory(@Nullable Rational surfaceAspectRatio) {
        this.mSurfaceAspectRatio = surfaceAspectRatio;
    }

    public static float getDefaultPointSize() {
        return 0.15f;
    }

    @NonNull
    public final MeteringPoint createPoint(float x, float y) {
        return createPoint(x, y, getDefaultPointSize());
    }

    @NonNull
    public final MeteringPoint createPoint(float x, float y, float size) {
        PointF convertedPoint = convertPoint(x, y);
        return new MeteringPoint(convertedPoint.x, convertedPoint.y, size, this.mSurfaceAspectRatio);
    }
}
