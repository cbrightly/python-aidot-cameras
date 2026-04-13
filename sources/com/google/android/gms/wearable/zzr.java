package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzl;

public final class zzr implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzl zzat;

    zzr(WearableListenerService.zzd zzd, zzl zzl) {
        this.zzao = zzd;
        this.zzat = zzl;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.google.android.gms.wearable.internal.zzl, com.google.android.gms.wearable.zzd] */
    public final void run() {
        WearableListenerService.this.onNotificationReceived(this.zzat);
    }
}
