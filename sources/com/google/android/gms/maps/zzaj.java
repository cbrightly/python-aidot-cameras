package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbj;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzaj extends zzbj {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener zza;

    zzaj(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener onStreetViewPanoramaCameraChangeListener) {
        this.zza = onStreetViewPanoramaCameraChangeListener;
    }

    public final void zzb(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.zza.onStreetViewPanoramaCameraChange(streetViewPanoramaCamera);
    }
}
