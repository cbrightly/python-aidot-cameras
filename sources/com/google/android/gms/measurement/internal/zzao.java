package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzao implements Runnable {
    final /* synthetic */ zzgz zza;
    final /* synthetic */ zzap zzb;

    zzao(zzap zzap, zzgz zzgz) {
        this.zzb = zzap;
        this.zza = zzgz;
    }

    public final void run() {
        this.zza.zzay();
        if (zzab.zza()) {
            this.zza.zzaB().zzp(this);
            return;
        }
        boolean zze = this.zzb.zze();
        this.zzb.zzd = 0;
        if (zze) {
            this.zzb.zzc();
        }
    }
}
