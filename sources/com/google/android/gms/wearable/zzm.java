package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.internal.zzfe;

public final class zzm implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ zzfe zzap;

    zzm(WearableListenerService.zzd zzd, zzfe zzfe) {
        this.zzao = zzd;
        this.zzap = zzfe;
    }

    public final void run() {
        WearableListenerService.this.onMessageReceived(this.zzap);
    }
}
