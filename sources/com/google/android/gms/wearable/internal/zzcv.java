package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;

public final class zzcv extends RegisterListenerMethod<zzhg, DataClient.OnDataChangedListener> {
    private final IntentFilter[] zzba;
    private final ListenerHolder<DataApi.DataListener> zzbz;
    private final DataClient.OnDataChangedListener zzdk;

    private zzcv(DataClient.OnDataChangedListener onDataChangedListener, IntentFilter[] intentFilterArr, ListenerHolder<DataClient.OnDataChangedListener> listenerHolder) {
        super(listenerHolder);
        this.zzdk = onDataChangedListener;
        this.zzba = intentFilterArr;
        this.zzbz = listenerHolder;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgh(taskCompletionSource), (DataApi.DataListener) this.zzdk, this.zzbz, this.zzba);
    }
}
