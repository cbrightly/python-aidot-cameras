package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;

public final class zzfc extends RegisterListenerMethod<zzhg, MessageClient.OnMessageReceivedListener> {
    private final IntentFilter[] zzba;
    private final ListenerHolder<MessageApi.MessageListener> zzbz;
    private final MessageClient.OnMessageReceivedListener zzej;

    private zzfc(MessageClient.OnMessageReceivedListener onMessageReceivedListener, IntentFilter[] intentFilterArr, ListenerHolder<MessageClient.OnMessageReceivedListener> listenerHolder) {
        super(listenerHolder);
        this.zzej = onMessageReceivedListener;
        this.zzba = intentFilterArr;
        this.zzbz = listenerHolder;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgh(taskCompletionSource), (MessageApi.MessageListener) this.zzej, this.zzbz, this.zzba);
    }
}
