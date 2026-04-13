package com.google.android.gms.measurement.internal;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.WorkerThread;
import com.google.android.gms.internal.measurement.zzby;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzko extends zzf {
    protected final zzkn zza = new zzkn(this);
    protected final zzkm zzb = new zzkm(this);
    protected final zzkk zzc = new zzkk(this);
    /* access modifiers changed from: private */
    public Handler zzd;

    zzko(zzge zzge) {
        super(zzge);
    }

    static /* bridge */ /* synthetic */ void zzj(zzko zzko, long j) {
        zzko.zzg();
        zzko.zzm();
        zzko.zzt.zzaA().zzj().zzb("Activity paused, time", Long.valueOf(j));
        zzko.zzc.zza(j);
        if (zzko.zzt.zzf().zzu()) {
            zzko.zzb.zzb(j);
        }
    }

    static /* bridge */ /* synthetic */ void zzl(zzko zzko, long j) {
        zzko.zzg();
        zzko.zzm();
        zzko.zzt.zzaA().zzj().zzb("Activity resumed, time", Long.valueOf(j));
        if (zzko.zzt.zzf().zzu() || zzko.zzt.zzm().zzm.zzb()) {
            zzko.zzb.zzc(j);
        }
        zzko.zzc.zzb();
        zzkn zzkn = zzko.zza;
        zzkn.zza.zzg();
        if (zzkn.zza.zzt.zzJ()) {
            zzkn.zzb(zzkn.zza.zzt.zzax().currentTimeMillis(), false);
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzm() {
        zzg();
        if (this.zzd == null) {
            this.zzd = new zzby(Looper.getMainLooper());
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }
}
