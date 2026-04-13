package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.wearable.DataApi;

public final class zzce implements zzc<DataApi.DataListener> {
    private final /* synthetic */ IntentFilter[] zzbr;

    zzce(IntentFilter[] intentFilterArr) {
        this.zzbr = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzhg zzhg, BaseImplementation.ResultHolder resultHolder, Object obj, ListenerHolder listenerHolder) {
        zzhg.zza((BaseImplementation.ResultHolder<Status>) resultHolder, (DataApi.DataListener) obj, (ListenerHolder<DataApi.DataListener>) listenerHolder, this.zzbr);
    }
}
