package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationCallback;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzcv implements ListenerHolder.Notifier {
    final /* synthetic */ zzcw zza;

    zzcv(zzcw zzcw) {
        this.zza = zzcw;
    }

    public final /* bridge */ /* synthetic */ void notifyListener(Object obj) {
        LocationCallback locationCallback = (LocationCallback) obj;
        this.zza.zza.zzb();
    }

    public final void onNotifyListenerFailed() {
    }
}
