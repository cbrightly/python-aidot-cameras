package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzhl implements Runnable {
    public final /* synthetic */ zzij zza;

    public /* synthetic */ zzhl(zzij zzij) {
        this.zza = zzij;
    }

    public final void run() {
        zzij zzij = this.zza;
        zzij.zzg();
        if (!zzij.zzt.zzm().zzn.zzb()) {
            long zza2 = zzij.zzt.zzm().zzo.zza();
            zzij.zzt.zzm().zzo.zzb(1 + zza2);
            zzij.zzt.zzf();
            if (zza2 >= 5) {
                zzij.zzt.zzaA().zzk().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
                zzij.zzt.zzm().zzn.zza(true);
                return;
            }
            zzij.zzt.zzE();
            return;
        }
        zzij.zzt.zzaA().zzc().zza("Deferred Deep Link already retrieved. Not fetching again.");
    }
}
