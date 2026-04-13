package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjo implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzac zzc;
    final /* synthetic */ zzac zzd;
    final /* synthetic */ zzjy zze;

    zzjo(zzjy zzjy, boolean z, zzq zzq, boolean z2, zzac zzac, zzac zzac2) {
        this.zze = zzjy;
        this.zza = zzq;
        this.zzb = z2;
        this.zzc = zzac;
        this.zzd = zzac2;
    }

    public final void run() {
        zzac zzac;
        zzjy zzjy = this.zze;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Discarding data. Failed to send conditional user property to service");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        zzjy zzjy2 = this.zze;
        if (this.zzb) {
            zzac = null;
        } else {
            zzac = this.zzc;
        }
        zzjy2.zzD(zzh, zzac, this.zza);
        this.zze.zzQ();
    }
}
