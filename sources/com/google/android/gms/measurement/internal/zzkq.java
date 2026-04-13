package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkq extends zzap {
    final /* synthetic */ zzkr zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzkq(zzkr zzkr, zzgz zzgz) {
        super(zzgz);
        this.zza = zzkr;
    }

    public final void zzc() {
        this.zza.zza();
        this.zza.zzt.zzaA().zzj().zza("Starting upload from DelayedRunnable");
        this.zza.zzf.zzX();
    }
}
