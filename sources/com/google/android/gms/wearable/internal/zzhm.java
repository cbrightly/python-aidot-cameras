package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.wearable.MessageApi;

public final class zzhm implements ListenerHolder.Notifier<MessageApi.MessageListener> {
    private final /* synthetic */ zzfe zzap;

    zzhm(zzfe zzfe) {
        this.zzap = zzfe;
    }

    public final void onNotifyListenerFailed() {
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        ((MessageApi.MessageListener) obj).onMessageReceived(this.zzap);
    }
}
