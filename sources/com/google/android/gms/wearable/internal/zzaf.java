package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityClient;

public final class zzaf extends RegisterListenerMethod<zzhg, CapabilityClient.OnCapabilityChangedListener> {
    private final IntentFilter[] zzba;
    private final CapabilityClient.OnCapabilityChangedListener zzby;
    private final ListenerHolder<CapabilityApi.CapabilityListener> zzbz;

    private zzaf(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, IntentFilter[] intentFilterArr, ListenerHolder<CapabilityClient.OnCapabilityChangedListener> listenerHolder) {
        super(listenerHolder);
        this.zzby = onCapabilityChangedListener;
        this.zzba = intentFilterArr;
        this.zzbz = listenerHolder;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgh(taskCompletionSource), (CapabilityApi.CapabilityListener) this.zzby, this.zzbz, this.zzba);
    }
}
