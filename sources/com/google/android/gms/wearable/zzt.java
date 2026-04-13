package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzaw;

public final class zzt implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzaw zzav;

    zzt(WearableListenerService.zzd zzd, zzaw zzaw) {
        this.zzao = zzd;
        this.zzav = zzaw;
    }

    public final void run() {
        this.zzav.zza(WearableListenerService.this);
        this.zzav.zza(WearableListenerService.this.zzaj);
    }
}
