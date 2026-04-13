package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzig implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzij zzb;

    zzig(zzij zzij, boolean z) {
        this.zzb = zzij;
        this.zza = z;
    }

    public final void run() {
        boolean zzJ = this.zzb.zzt.zzJ();
        boolean zzI = this.zzb.zzt.zzI();
        this.zzb.zzt.zzF(this.zza);
        if (zzI == this.zza) {
            this.zzb.zzt.zzaA().zzj().zzb("Default data collection state already set to", Boolean.valueOf(this.zza));
        }
        if (this.zzb.zzt.zzJ() == zzJ || this.zzb.zzt.zzJ() != this.zzb.zzt.zzI()) {
            this.zzb.zzt.zzaA().zzl().zzc("Default data collection is different than actual status", Boolean.valueOf(this.zza), Boolean.valueOf(zzJ));
        }
        this.zzb.zzab();
    }
}
