package com.google.android.gms.internal.location;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.api.internal.ListenerHolder;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzas implements zzcs {
    @GuardedBy("this")
    private ListenerHolder zza;

    zzas(ListenerHolder listenerHolder) {
        this.zza = listenerHolder;
    }

    public final synchronized ListenerHolder zza() {
        return this.zza;
    }

    public final void zzb() {
    }

    public final synchronized void zzc(ListenerHolder listenerHolder) {
        ListenerHolder listenerHolder2 = this.zza;
        if (listenerHolder2 != listenerHolder) {
            listenerHolder2.clear();
            this.zza = listenerHolder;
        }
    }
}
