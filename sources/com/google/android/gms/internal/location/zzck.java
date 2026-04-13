package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzck implements zzcs {
    final /* synthetic */ ListenerHolder zza;
    final /* synthetic */ TaskCompletionSource zzb;

    zzck(zzda zzda, ListenerHolder listenerHolder, TaskCompletionSource taskCompletionSource) {
        this.zza = listenerHolder;
        this.zzb = taskCompletionSource;
    }

    public final ListenerHolder zza() {
        return this.zza;
    }

    public final void zzb() {
        this.zzb.trySetResult(null);
    }

    public final void zzc(ListenerHolder listenerHolder) {
        throw new IllegalStateException();
    }
}
