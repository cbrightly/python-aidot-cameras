package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbl;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzai extends zzbl {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaChangeListener zza;

    zzai(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaChangeListener onStreetViewPanoramaChangeListener) {
        this.zza = onStreetViewPanoramaChangeListener;
    }

    public final void zzb(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        this.zza.onStreetViewPanoramaChange(streetViewPanoramaLocation);
    }
}
