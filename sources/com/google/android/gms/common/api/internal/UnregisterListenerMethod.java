package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class UnregisterListenerMethod<A extends Api.AnyClient, L> {
    private final ListenerHolder.ListenerKey zaa;

    @KeepForSdk
    protected UnregisterListenerMethod(@NonNull ListenerHolder.ListenerKey<L> listenerKey) {
        this.zaa = listenerKey;
    }

    @NonNull
    @KeepForSdk
    public ListenerHolder.ListenerKey<L> getListenerKey() {
        return this.zaa;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public abstract void unregisterListener(@NonNull A a, @NonNull TaskCompletionSource<Boolean> taskCompletionSource);
}
