package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzad;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbf;
import com.google.android.gms.maps.model.Polygon;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzo extends zzbf {
    final /* synthetic */ GoogleMap.OnPolygonClickListener zza;

    zzo(GoogleMap googleMap, GoogleMap.OnPolygonClickListener onPolygonClickListener) {
        this.zza = onPolygonClickListener;
    }

    public final void zzb(zzad zzad) {
        this.zza.onPolygonClick(new Polygon(zzad));
    }
}
