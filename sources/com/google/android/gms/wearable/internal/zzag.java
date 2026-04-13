package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityClient;

public final class zzag extends UnregisterListenerMethod<zzhg, CapabilityClient.OnCapabilityChangedListener> {
    private final CapabilityClient.OnCapabilityChangedListener zzby;

    private zzag(CapabilityClient.OnCapabilityChangedListener onCapabilityChangedListener, ListenerHolder.ListenerKey<CapabilityClient.OnCapabilityChangedListener> listenerKey) {
        super(listenerKey);
        this.zzby = onCapabilityChangedListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgg(taskCompletionSource), (CapabilityApi.CapabilityListener) this.zzby);
    }
}
