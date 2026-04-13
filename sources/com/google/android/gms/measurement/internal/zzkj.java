package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkj implements Runnable {
    final long zza;
    final long zzb;
    final /* synthetic */ zzkk zzc;

    zzkj(zzkk zzkk, long j, long j2) {
        this.zzc = zzkk;
        this.zza = j;
        this.zzb = j2;
    }

    public final void run() {
        this.zzc.zza.zzt.zzaB().zzp(new zzki(this));
    }
}
