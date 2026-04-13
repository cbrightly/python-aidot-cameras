package com.google.android.material.shape;

import androidx.annotation.NonNull;

public class TriangleEdgeTreatment extends EdgeTreatment {
    private final boolean inside;
    private final float size;

    public TriangleEdgeTreatment(float size2, boolean inside2) {
        this.size = size2;
        this.inside = inside2;
    }

    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {
        shapePath.lineTo(center - (this.size * interpolation), 0.0f);
        shapePath.lineTo(center, (this.inside ? this.size : -this.size) * interpolation);
        shapePath.lineTo((this.size * interpolation) + center, 0.0f);
        shapePath.lineTo(length, 0.0f);
    }
}
