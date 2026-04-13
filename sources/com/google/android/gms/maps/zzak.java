package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbn;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzak extends zzbn {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaClickListener zza;

    zzak(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaClickListener onStreetViewPanoramaClickListener) {
        this.zza = onStreetViewPanoramaClickListener;
    }

    public final void zzb(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        this.zza.onStreetViewPanoramaClick(streetViewPanoramaOrientation);
    }
}
