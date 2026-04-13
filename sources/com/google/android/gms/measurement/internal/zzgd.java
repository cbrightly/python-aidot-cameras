package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzgd implements Runnable {
    final /* synthetic */ zzhh zza;
    final /* synthetic */ zzge zzb;

    zzgd(zzge zzge, zzhh zzhh) {
        this.zzb = zzge;
        this.zza = zzhh;
    }

    public final void run() {
        zzge.zzA(this.zzb, this.zza);
        this.zzb.zzH(this.zza.zzg);
    }
}
