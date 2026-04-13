package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzqr;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzif implements Runnable {
    final /* synthetic */ zzai zza;
    final /* synthetic */ int zzb;
    final /* synthetic */ long zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zzai zze;
    final /* synthetic */ zzij zzf;

    zzif(zzij zzij, zzai zzai, int i, long j, boolean z, zzai zzai2) {
        this.zzf = zzij;
        this.zza = zzai;
        this.zzb = i;
        this.zzc = j;
        this.zzd = z;
        this.zze = zzai2;
    }

    public final void run() {
        this.zzf.zzV(this.zza);
        zzij.zzw(this.zzf, this.zza, this.zzb, this.zzc, false, this.zzd);
        zzqr.zzc();
        if (this.zzf.zzt.zzf().zzs((String) null, zzeh.zzan)) {
            zzij.zzv(this.zzf, this.zza, this.zze);
        }
    }
}
