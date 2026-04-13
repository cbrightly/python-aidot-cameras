package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zziw implements Runnable {
    final /* synthetic */ zziq zza;
    final /* synthetic */ long zzb;
    final /* synthetic */ zziy zzc;

    zziw(zziy zziy, zziq zziq, long j) {
        this.zzc = zziy;
        this.zza = zziq;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zzB(this.zza, false, this.zzb);
        zziy zziy = this.zzc;
        zziy.zza = null;
        zziy.zzt.zzt().zzG((zziq) null);
    }
}
