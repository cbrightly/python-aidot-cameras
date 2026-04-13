package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgq implements Runnable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzgw zzc;

    zzgq(zzgw zzgw, zzaw zzaw, String str) {
        this.zzc = zzgw;
        this.zza = zzaw;
        this.zzb = str;
    }

    public final void run() {
        this.zzc.zza.zzA();
        this.zzc.zza.zzF(this.zza, this.zzb);
    }
}
