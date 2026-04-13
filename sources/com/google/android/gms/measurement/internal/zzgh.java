package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgh implements Runnable {
    final /* synthetic */ zzac zza;
    final /* synthetic */ zzgw zzb;

    zzgh(zzgw zzgw, zzac zzac) {
        this.zzb = zzgw;
        this.zza = zzac;
    }

    public final void run() {
        this.zzb.zza.zzA();
        if (this.zza.zzc.zza() == null) {
            this.zzb.zza.zzN(this.zza);
        } else {
            this.zzb.zza.zzT(this.zza);
        }
    }
}
