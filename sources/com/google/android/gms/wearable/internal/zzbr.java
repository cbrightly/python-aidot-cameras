package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public final class zzbr extends zzej {
    private final Object lock = new Object();
    @GuardedBy("lock")
    @Nullable
    private zzav zzcw;
    @GuardedBy("lock")
    @Nullable
    private zzbs zzda;

    public final void zza(zzbs zzbs) {
        zzav zzav;
        synchronized (this.lock) {
            this.zzda = (zzbs) Preconditions.checkNotNull(zzbs);
            zzav = this.zzcw;
        }
        if (zzav != null) {
            zzbs.zzb(zzav);
        }
    }

    public final void zza(int i, int i2) {
        zzbs zzbs;
        zzav zzav;
        synchronized (this.lock) {
            zzbs = this.zzda;
            zzav = new zzav(i, i2);
            this.zzcw = zzav;
        }
        if (zzbs != null) {
            zzbs.zzb(zzav);
        }
    }
}
