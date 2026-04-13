package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzah;

public final class zzq implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzah zzas;

    zzq(WearableListenerService.zzd zzd, zzah zzah) {
        this.zzao = zzd;
        this.zzas = zzah;
    }

    public final void run() {
        WearableListenerService.this.onCapabilityChanged(this.zzas);
    }
}
