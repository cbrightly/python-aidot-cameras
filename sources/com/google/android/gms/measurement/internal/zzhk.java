package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzhk implements Runnable {
    public final /* synthetic */ zzij zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzhk(zzij zzij, String str) {
        this.zza = zzij;
        this.zzb = str;
    }

    public final void run() {
        zzij zzij = this.zza;
        if (zzij.zzt.zzh().zzp(this.zzb)) {
            zzij.zzt.zzh().zzo();
        }
    }
}
