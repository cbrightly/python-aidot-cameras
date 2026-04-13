package com.google.android.libraries.places.api.model;

import androidx.annotation.FloatRange;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzu extends PlaceLikelihood {
    private final Place zza;
    private final double zzb;

    zzu(Place place, double d) {
        if (place != null) {
            this.zza = place;
            this.zzb = d;
            return;
        }
        throw new NullPointerException("Null place");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PlaceLikelihood) {
            PlaceLikelihood placeLikelihood = (PlaceLikelihood) obj;
            return this.zza.equals(placeLikelihood.getPlace()) && Double.doubleToLongBits(this.zzb) == Double.doubleToLongBits(placeLikelihood.getLikelihood());
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public final double getLikelihood() {
        return this.zzb;
    }

    public final Place getPlace() {
        return this.zza;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ ((int) ((Double.doubleToLongBits(this.zzb) >>> 32) ^ Double.doubleToLongBits(this.zzb)));
    }

    public final String toString() {
        String obj = this.zza.toString();
        double d = this.zzb;
        return "PlaceLikelihood{place=" + obj + ", likelihood=" + d + "}";
    }
}
