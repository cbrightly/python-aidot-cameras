package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zziv implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zziy zzb;

    zziv(zziy zziy, long j) {
        this.zzb = zziy;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzt.zzd().zzf(this.zza);
        this.zzb.zza = null;
    }
}
