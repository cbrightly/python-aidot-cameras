package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import javax.annotation.Nullable;

public final class zzau extends UnregisterListenerMethod<zzhg, ChannelClient.ChannelCallback> {
    @Nullable
    private final String zzce;
    private final ChannelApi.ChannelListener zzcf;

    zzau(ChannelApi.ChannelListener channelListener, @Nullable String str, ListenerHolder.ListenerKey<ChannelClient.ChannelCallback> listenerKey) {
        super(listenerKey);
        this.zzcf = channelListener;
        this.zzce = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza(new zzgg(taskCompletionSource), this.zzcf, this.zzce);
    }
}
