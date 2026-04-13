package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzax;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzh extends zzax {
    final /* synthetic */ GoogleMap.OnMyLocationButtonClickListener zza;

    zzh(GoogleMap googleMap, GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
        this.zza = onMyLocationButtonClickListener;
    }

    public final boolean zzb() {
        return this.zza.onMyLocationButtonClick();
    }
}
