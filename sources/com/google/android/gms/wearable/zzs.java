package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzi;

public final class zzs implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzi zzau;

    zzs(WearableListenerService.zzd zzd, zzi zzi) {
        this.zzao = zzd;
        this.zzau = zzi;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.google.android.gms.wearable.internal.zzi, com.google.android.gms.wearable.zzb] */
    public final void run() {
        WearableListenerService.this.onEntityUpdate(this.zzau);
    }
}
