package com.google.android.gms.maps;

import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzar;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzat extends zzar {
    final /* synthetic */ OnMapReadyCallback zza;

    zzat(zzau zzau, OnMapReadyCallback onMapReadyCallback) {
        this.zza = onMapReadyCallback;
    }

    public final void zzb(IGoogleMapDelegate iGoogleMapDelegate) {
        this.zza.onMapReady(new GoogleMap(iGoogleMapDelegate));
    }
}
