package com.google.android.gms.maps;

import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbr;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzaq extends zzbr {
    final /* synthetic */ OnStreetViewPanoramaReadyCallback zza;

    zzaq(zzar zzar, OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        this.zza = onStreetViewPanoramaReadyCallback;
    }

    public final void zzb(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) {
        this.zza.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
    }
}
