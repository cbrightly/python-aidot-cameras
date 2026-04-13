package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzho implements ListenerHolder.Notifier<CapabilityApi.CapabilityListener> {
    private final /* synthetic */ zzah zzfr;

    zzho(zzah zzah) {
        this.zzfr = zzah;
    }

    public final void onNotifyListenerFailed() {
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        ((CapabilityApi.CapabilityListener) obj).onCapabilityChanged(this.zzfr);
    }
}
