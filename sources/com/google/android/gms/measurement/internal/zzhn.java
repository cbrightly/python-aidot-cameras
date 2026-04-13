package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhn implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzij zzb;

    zzhn(zzij zzij, long j) {
        this.zzb = zzij;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzt.zzm().zzf.zzb(this.zza);
        this.zzb.zzt.zzaA().zzc().zzb("Session timeout duration set", Long.valueOf(this.zza));
    }
}
