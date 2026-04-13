package com.google.android.gms.internal.location;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzbo implements RemoteCall, zzcs {
    final /* synthetic */ zzbp zza;
    private final zzbn zzb;
    @GuardedBy("this")
    private ListenerHolder zzc;
    @GuardedBy("this")
    private boolean zzd = true;

    zzbo(zzbp zzbp, ListenerHolder listenerHolder, zzbn zzbn) {
        this.zza = zzbp;
        this.zzc = listenerHolder;
        this.zzb = zzbn;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) {
        ListenerHolder.ListenerKey listenerKey;
        boolean z;
        zzda zzda = (zzda) obj;
        TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
        synchronized (this) {
            listenerKey = this.zzc.getListenerKey();
            z = this.zzd;
            this.zzc.clear();
        }
        if (listenerKey == null) {
            taskCompletionSource.setResult(false);
        } else {
            this.zzb.zza(zzda, listenerKey, z, taskCompletionSource);
        }
    }

    public final synchronized ListenerHolder zza() {
        return this.zzc;
    }

    public final void zzb() {
        ListenerHolder.ListenerKey listenerKey;
        synchronized (this) {
            this.zzd = false;
            listenerKey = this.zzc.getListenerKey();
        }
        if (listenerKey != null) {
            this.zza.doUnregisterEventListener(listenerKey, 2441);
        }
    }

    public final synchronized void zzc(ListenerHolder listenerHolder) {
        ListenerHolder listenerHolder2 = this.zzc;
        if (listenerHolder2 != listenerHolder) {
            listenerHolder2.clear();
            this.zzc = listenerHolder;
        }
    }
}
