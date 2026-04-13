package com.google.android.gms.maps;

import android.graphics.Bitmap;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzbu;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzq extends zzbu {
    final /* synthetic */ GoogleMap.SnapshotReadyCallback zza;

    zzq(GoogleMap googleMap, GoogleMap.SnapshotReadyCallback snapshotReadyCallback) {
        this.zza = snapshotReadyCallback;
    }

    public final void zzb(Bitmap bitmap) {
        this.zza.onSnapshotReady(bitmap);
    }

    public final void zzc(IObjectWrapper iObjectWrapper) {
        this.zza.onSnapshotReady((Bitmap) ObjectWrapper.unwrap(iObjectWrapper));
    }
}
