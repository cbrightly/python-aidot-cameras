package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.wearable.ChannelApi;

public final class zzhn implements ListenerHolder.Notifier<ChannelApi.ChannelListener> {
    private final /* synthetic */ zzaw zzav;

    zzhn(zzaw zzaw) {
        this.zzav = zzaw;
    }

    public final void onNotifyListenerFailed() {
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        this.zzav.zza((ChannelApi.ChannelListener) obj);
    }
}
