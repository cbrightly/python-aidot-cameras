package com.google.android.gms.wearable;

import com.google.android.gms.wearable.WearableListenerService;
import java.util.List;

public final class zzp implements Runnable {
    private final /* synthetic */ WearableListenerService.zzd zzao;
    private final /* synthetic */ List zzar;

    zzp(WearableListenerService.zzd zzd, List list) {
        this.zzao = zzd;
        this.zzar = list;
    }

    public final void run() {
        WearableListenerService.this.onConnectedNodes(this.zzar);
    }
}
