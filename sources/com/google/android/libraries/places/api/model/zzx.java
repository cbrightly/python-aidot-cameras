package com.google.android.libraries.places.api.model;

import com.google.android.gms.maps.model.LatLng;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzx extends zzbv {
    private LatLng zza;
    private LatLng zzb;

    zzx() {
    }

    /* access modifiers changed from: package-private */
    public final zzbv zza(LatLng latLng) {
        if (latLng != null) {
            this.zzb = latLng;
            return this;
        }
        throw new NullPointerException("Null northeast");
    }

    /* access modifiers changed from: package-private */
    public final zzbv zzb(LatLng latLng) {
        if (latLng != null) {
            this.zza = latLng;
            return this;
        }
        throw new NullPointerException("Null southwest");
    }

    /* access modifiers changed from: package-private */
    public final RectangularBounds zzc() {
        LatLng latLng;
        LatLng latLng2 = this.zza;
        if (latLng2 != null && (latLng = this.zzb) != null) {
            return new zzbe(latLng2, latLng);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" southwest");
        }
        if (this.zzb == null) {
            sb.append(" northeast");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
