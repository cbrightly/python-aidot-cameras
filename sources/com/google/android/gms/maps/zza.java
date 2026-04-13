package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzat;
import com.google.android.gms.maps.model.Marker;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zza extends zzat {
    final /* synthetic */ GoogleMap.OnMarkerClickListener zza;

    zza(GoogleMap googleMap, GoogleMap.OnMarkerClickListener onMarkerClickListener) {
        this.zza = onMarkerClickListener;
    }

    public final boolean zzb(zzaa zzaa) {
        return this.zza.onMarkerClick(new Marker(zzaa));
    }
}
