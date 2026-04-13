package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbp;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzal extends zzbp {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaLongClickListener zza;

    zzal(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaLongClickListener onStreetViewPanoramaLongClickListener) {
        this.zza = onStreetViewPanoramaLongClickListener;
    }

    public final void zzb(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        this.zza.onStreetViewPanoramaLongClick(streetViewPanoramaOrientation);
    }
}
