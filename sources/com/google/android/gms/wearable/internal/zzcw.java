package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;

public final class zzcw extends UnregisterListenerMethod<zzhg, DataClient.OnDataChangedListener> {
    private final DataClient.OnDataChangedListener zzdk;

    private zzcw(DataClient.OnDataChangedListener onDataChangedListener, ListenerHolder.ListenerKey<DataClient.OnDataChangedListener> listenerKey) {
        super(listenerKey);
        this.zzdk = onDataChangedListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgg(taskCompletionSource), (DataApi.DataListener) this.zzdk);
    }
}
