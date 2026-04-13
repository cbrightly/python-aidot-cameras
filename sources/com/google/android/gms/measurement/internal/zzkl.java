package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkl extends zzap {
    final /* synthetic */ zzkm zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzkl(zzkm zzkm, zzgz zzgz) {
        super(zzgz);
        this.zza = zzkm;
    }

    @WorkerThread
    public final void zzc() {
        zzkm zzkm = this.zza;
        zzkm.zzc.zzg();
        zzkm.zzd(false, false, zzkm.zzc.zzt.zzax().elapsedRealtime());
        zzkm.zzc.zzt.zzd().zzf(zzkm.zzc.zzt.zzax().elapsedRealtime());
    }
}
