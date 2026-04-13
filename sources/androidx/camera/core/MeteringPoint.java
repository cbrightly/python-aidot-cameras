package androidx.camera.core;

import android.util.Rational;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

public class MeteringPoint {
    private float mNormalizedX;
    private float mNormalizedY;
    private float mSize;
    @Nullable
    private Rational mSurfaceAspectRatio;

    MeteringPoint(float normalizedX, float normalizedY, float size, @Nullable Rational surfaceAspectRatio) {
        this.mNormalizedX = normalizedX;
        this.mNormalizedY = normalizedY;
        this.mSize = size;
        this.mSurfaceAspectRatio = surfaceAspectRatio;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getX() {
        return this.mNormalizedX;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getY() {
        return this.mNormalizedY;
    }

    public float getSize() {
        return this.mSize;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Rational getSurfaceAspectRatio() {
        return this.mSurfaceAspectRatio;
    }
}
