package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationListener;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzcy implements ListenerHolder.Notifier {
    final /* synthetic */ zzcz zza;

    zzcy(zzcz zzcz) {
        this.zza = zzcz;
    }

    public final /* bridge */ /* synthetic */ void notifyListener(Object obj) {
        LocationListener locationListener = (LocationListener) obj;
        this.zza.zza.zzb();
    }

    public final void onNotifyListenerFailed() {
    }
}
