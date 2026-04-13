package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzqr;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzie implements Runnable {
    final /* synthetic */ zzai zza;
    final /* synthetic */ long zzb;
    final /* synthetic */ int zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ boolean zze;
    final /* synthetic */ zzai zzf;
    final /* synthetic */ zzij zzg;

    zzie(zzij zzij, zzai zzai, long j, int i, long j2, boolean z, zzai zzai2) {
        this.zzg = zzij;
        this.zza = zzai;
        this.zzb = j;
        this.zzc = i;
        this.zzd = j2;
        this.zze = z;
        this.zzf = zzai2;
    }

    public final void run() {
        this.zzg.zzV(this.zza);
        this.zzg.zzL(this.zzb, false);
        zzij.zzw(this.zzg, this.zza, this.zzc, this.zzd, true, this.zze);
        zzqr.zzc();
        if (this.zzg.zzt.zzf().zzs((String) null, zzeh.zzan)) {
            zzij.zzv(this.zzg, this.zza, this.zzf);
        }
    }
}
