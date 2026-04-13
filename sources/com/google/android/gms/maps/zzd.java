package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzaa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzag;
import com.google.android.gms.maps.model.Marker;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzd extends zzag {
    final /* synthetic */ GoogleMap.OnInfoWindowLongClickListener zza;

    zzd(GoogleMap googleMap, GoogleMap.OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
        this.zza = onInfoWindowLongClickListener;
    }

    public final void zzb(zzaa zzaa) {
        this.zza.onInfoWindowLongClick(new Marker(zzaa));
    }
}
