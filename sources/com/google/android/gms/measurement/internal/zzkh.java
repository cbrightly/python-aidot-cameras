package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkh implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzko zzb;

    zzkh(zzko zzko, long j) {
        this.zzb = zzko;
        this.zza = j;
    }

    public final void run() {
        zzko.zzj(this.zzb, this.zza);
    }
}
