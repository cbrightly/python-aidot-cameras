package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzav;
import com.google.android.gms.maps.model.Marker;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzb extends zzav {
    final /* synthetic */ GoogleMap.OnMarkerDragListener zza;

    zzb(GoogleMap googleMap, GoogleMap.OnMarkerDragListener onMarkerDragListener) {
        this.zza = onMarkerDragListener;
    }

    public final void zzb(zzaa zzaa) {
        this.zza.onMarkerDrag(new Marker(zzaa));
    }

    public final void zzc(zzaa zzaa) {
        this.zza.onMarkerDragEnd(new Marker(zzaa));
    }

    public final void zzd(zzaa zzaa) {
        this.zza.onMarkerDragStart(new Marker(zzaa));
    }
}
