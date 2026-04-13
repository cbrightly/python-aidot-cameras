package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzan;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzj extends zzan {
    final /* synthetic */ GoogleMap.OnMapLoadedCallback zza;

    zzj(GoogleMap googleMap, GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
        this.zza = onMapLoadedCallback;
    }

    public final void zzb() {
        this.zza.onMapLoaded();
    }
}
