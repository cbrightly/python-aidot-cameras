package com.google.android.material.shape;

import androidx.annotation.NonNull;

public class EdgeTreatment {
    @Deprecated
    public void getEdgePath(float length, float interpolation, @NonNull ShapePath shapePath) {
        getEdgePath(length, length / 2.0f, interpolation, shapePath);
    }

    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {
        shapePath.lineTo(length, 0.0f);
    }

    /* access modifiers changed from: package-private */
    public boolean forceIntersection() {
        return false;
    }
}
