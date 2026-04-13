package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzag;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbh;
import com.google.android.gms.maps.model.Polyline;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzp extends zzbh {
    final /* synthetic */ GoogleMap.OnPolylineClickListener zza;

    zzp(GoogleMap googleMap, GoogleMap.OnPolylineClickListener onPolylineClickListener) {
        this.zza = onPolylineClickListener;
    }

    public final void zzb(zzag zzag) {
        this.zza.onPolylineClick(new Polyline(zzag));
    }
}
