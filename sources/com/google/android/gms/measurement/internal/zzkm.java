package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzpe;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkm {
    @VisibleForTesting
    protected long zza;
    @VisibleForTesting
    protected long zzb;
    final /* synthetic */ zzko zzc;
    private final zzap zzd;

    public zzkm(zzko zzko) {
        this.zzc = zzko;
        this.zzd = new zzkl(this, zzko.zzt);
        long elapsedRealtime = zzko.zzt.zzax().elapsedRealtime();
        this.zza = elapsedRealtime;
        this.zzb = elapsedRealtime;
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        this.zzd.zzb();
        this.zza = 0;
        this.zzb = 0;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzb(long j) {
        this.zzd.zzb();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzc(long j) {
        this.zzc.zzg();
        this.zzd.zzb();
        this.zza = j;
        this.zzb = j;
    }

    @WorkerThread
    public final boolean zzd(boolean z, boolean z2, long j) {
        this.zzc.zzg();
        this.zzc.zza();
        zzpe.zzc();
        if (!this.zzc.zzt.zzf().zzs((String) null, zzeh.zzaf)) {
            this.zzc.zzt.zzm().zzj.zzb(this.zzc.zzt.zzax().currentTimeMillis());
        } else if (this.zzc.zzt.zzJ()) {
            this.zzc.zzt.zzm().zzj.zzb(this.zzc.zzt.zzax().currentTimeMillis());
        }
        long j2 = j - this.zza;
        if (z || j2 >= 1000) {
            if (!z2) {
                j2 = j - this.zzb;
                this.zzb = j;
            }
            this.zzc.zzt.zzaA().zzj().zzb("Recording user engagement, ms", Long.valueOf(j2));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j2);
            zzlo.zzK(this.zzc.zzt.zzs().zzj(!this.zzc.zzt.zzf().zzu()), bundle, true);
            if (!z2) {
                this.zzc.zzt.zzq().zzG("auto", "_e", bundle);
            }
            this.zza = j;
            this.zzd.zzb();
            this.zzd.zzd(CostTimeUtil.HOUR);
            return true;
        }
        this.zzc.zzt.zzaA().zzj().zzb("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j2));
        return false;
    }
}
