package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzkg implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzko zzb;

    zzkg(zzko zzko, long j) {
        this.zzb = zzko;
        this.zza = j;
    }

    public final void run() {
        zzko.zzl(this.zzb, this.zza);
    }
}
