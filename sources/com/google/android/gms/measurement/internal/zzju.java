package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzju implements Runnable {
    final /* synthetic */ zzek zza;
    final /* synthetic */ zzjx zzb;

    zzju(zzjx zzjx, zzek zzek) {
        this.zzb = zzjx;
        this.zza = zzek;
    }

    public final void run() {
        synchronized (this.zzb) {
            this.zzb.zzb = false;
            if (!this.zzb.zza.zzL()) {
                this.zzb.zza.zzt.zzaA().zzc().zza("Connected to remote service");
                this.zzb.zza.zzJ(this.zza);
            }
        }
    }
}
