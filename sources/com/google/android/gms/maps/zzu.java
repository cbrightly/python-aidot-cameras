package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzu extends com.google.android.gms.maps.internal.zzu {
    final /* synthetic */ GoogleMap.OnCameraMoveStartedListener zza;

    zzu(GoogleMap googleMap, GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener) {
        this.zza = onCameraMoveStartedListener;
    }

    public final void zzb(int i) {
        this.zza.onCameraMoveStarted(i);
    }
}
