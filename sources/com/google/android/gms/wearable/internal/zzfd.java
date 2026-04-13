package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;

public final class zzfd extends UnregisterListenerMethod<zzhg, MessageClient.OnMessageReceivedListener> {
    private final MessageClient.OnMessageReceivedListener zzej;

    private zzfd(MessageClient.OnMessageReceivedListener onMessageReceivedListener, ListenerHolder.ListenerKey<MessageClient.OnMessageReceivedListener> listenerKey) {
        super(listenerKey);
        this.zzej = onMessageReceivedListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza((BaseImplementation.ResultHolder<Status>) new zzgg(taskCompletionSource), (MessageApi.MessageListener) this.zzej);
    }
}
