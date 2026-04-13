package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzq;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzw extends zzq {
    final /* synthetic */ GoogleMap.OnCameraMoveCanceledListener zza;

    zzw(GoogleMap googleMap, GoogleMap.OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        this.zza = onCameraMoveCanceledListener;
    }

    public final void zzb() {
        this.zza.onCameraMoveCanceled();
    }
}
