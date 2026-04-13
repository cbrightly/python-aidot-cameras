package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzfo;

public final class zzn implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzfo zzaq;

    zzn(WearableListenerService.zzd zzd, zzfo zzfo) {
        this.zzao = zzd;
        this.zzaq = zzfo;
    }

    public final void run() {
        WearableListenerService.this.onPeerConnected(this.zzaq);
    }
}
