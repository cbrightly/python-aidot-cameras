package com.google.android.gms.maps;

import android.location.Location;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbb;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzi extends zzbb {
    final /* synthetic */ GoogleMap.OnMyLocationClickListener zza;

    zzi(GoogleMap googleMap, GoogleMap.OnMyLocationClickListener onMyLocationClickListener) {
        this.zza = onMyLocationClickListener;
    }

    public final void zzb(Location location) {
        this.zza.onMyLocationClick(location);
    }
}
