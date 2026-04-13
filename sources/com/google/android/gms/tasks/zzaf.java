package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.ExecutionException;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-tasks@@18.0.2 */
public final class zzaf<T> implements zzae<T> {
    private final Object zza = new Object();
    private final int zzb;
    private final zzw zzc;
    @GuardedBy("mLock")
    private int zzd;
    @GuardedBy("mLock")
    private int zze;
    @GuardedBy("mLock")
    private int zzf;
    @GuardedBy("mLock")
    private Exception zzg;
    @GuardedBy("mLock")
    private boolean zzh;

    public zzaf(int i, zzw zzw) {
        this.zzb = i;
        this.zzc = zzw;
    }

    @GuardedBy("mLock")
    private final void zza() {
        if (this.zzd + this.zze + this.zzf != this.zzb) {
            return;
        }
        if (this.zzg != null) {
            zzw zzw = this.zzc;
            int i = this.zze;
            int i2 = this.zzb;
            zzw.zza(new ExecutionException(i + " out of " + i2 + " underlying tasks failed", this.zzg));
        } else if (this.zzh) {
            this.zzc.zzc();
        } else {
            this.zzc.zzb((Object) null);
        }
    }

    public final void onCanceled() {
        synchronized (this.zza) {
            this.zzf++;
            this.zzh = true;
            zza();
        }
    }

    public final void onFailure(@NonNull Exception exc) {
        synchronized (this.zza) {
            this.zze++;
            this.zzg = exc;
            zza();
        }
    }

    public final void onSuccess(T t) {
        synchronized (this.zza) {
            this.zzd++;
            zza();
        }
    }
}
