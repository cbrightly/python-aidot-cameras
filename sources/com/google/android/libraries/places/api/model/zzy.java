package com.google.android.libraries.places.api.model;

import com.google.android.gms.maps.model.LatLng;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzy extends RectangularBounds {
    private final LatLng zza;
    private final LatLng zzb;

    zzy(LatLng latLng, LatLng latLng2) {
        if (latLng != null) {
            this.zza = latLng;
            if (latLng2 != null) {
                this.zzb = latLng2;
                return;
            }
            throw new NullPointerException("Null northeast");
        }
        throw new NullPointerException("Null southwest");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RectangularBounds) {
            RectangularBounds rectangularBounds = (RectangularBounds) obj;
            return this.zza.equals(rectangularBounds.getSouthwest()) && this.zzb.equals(rectangularBounds.getNortheast());
        }
    }

    public final LatLng getNortheast() {
        return this.zzb;
    }

    public final LatLng getSouthwest() {
        return this.zza;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        String obj = this.zza.toString();
        String obj2 = this.zzb.toString();
        return "RectangularBounds{southwest=" + obj + ", northeast=" + obj2 + "}";
    }
}
