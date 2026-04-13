package com.google.android.gms.maps.model;

import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class Gap extends PatternItem {
    public final float length;

    public Gap(float length2) {
        super(2, Float.valueOf(Math.max(length2, 0.0f)));
        this.length = Math.max(length2, 0.0f);
    }

    @NonNull
    public String toString() {
        float f = this.length;
        return "[Gap: length=" + f + "]";
    }
}
