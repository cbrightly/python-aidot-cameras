package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;

public final class zzhl implements ListenerHolder.Notifier<DataApi.DataListener> {
    private final /* synthetic */ DataHolder zzan;

    zzhl(DataHolder dataHolder) {
        this.zzan = dataHolder;
    }

    public final void onNotifyListenerFailed() {
        this.zzan.close();
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        try {
            ((DataApi.DataListener) obj).onDataChanged(new DataEventBuffer(this.zzan));
        } finally {
            this.zzan.close();
        }
    }
}
