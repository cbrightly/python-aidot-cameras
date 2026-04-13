package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgp implements Runnable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ zzq zzb;
    final /* synthetic */ zzgw zzc;

    zzgp(zzgw zzgw, zzaw zzaw, zzq zzq) {
        this.zzc = zzgw;
        this.zza = zzaw;
        this.zzb = zzq;
    }

    public final void run() {
        this.zzc.zzv(this.zzc.zzb(this.zza, this.zzb), this.zzb);
    }
}
