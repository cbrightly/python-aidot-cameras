package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.model.LatLng;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzy extends zzal {
    final /* synthetic */ GoogleMap.OnMapClickListener zza;

    zzy(GoogleMap googleMap, GoogleMap.OnMapClickListener onMapClickListener) {
        this.zza = onMapClickListener;
    }

    public final void zzb(LatLng latLng) {
        this.zza.onMapClick(latLng);
    }
}
