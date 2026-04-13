package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgs implements Runnable {
    final /* synthetic */ zzlj zza;
    final /* synthetic */ zzq zzb;
    final /* synthetic */ zzgw zzc;

    zzgs(zzgw zzgw, zzlj zzlj, zzq zzq) {
        this.zzc = zzgw;
        this.zza = zzlj;
        this.zzb = zzq;
    }

    public final void run() {
        this.zzc.zza.zzA();
        if (this.zza.zza() == null) {
            this.zzc.zza.zzP(this.zza.zzb, this.zzb);
        } else {
            this.zzc.zza.zzW(this.zza, this.zzb);
        }
    }
}
