package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjb implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzlj zzc;
    final /* synthetic */ zzjy zzd;

    zzjb(zzjy zzjy, zzq zzq, boolean z, zzlj zzlj) {
        this.zzd = zzjy;
        this.zza = zzq;
        this.zzb = z;
        this.zzc = zzlj;
    }

    public final void run() {
        zzlj zzlj;
        zzjy zzjy = this.zzd;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Discarding data. Failed to set user property");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        zzjy zzjy2 = this.zzd;
        if (this.zzb) {
            zzlj = null;
        } else {
            zzlj = this.zzc;
        }
        zzjy2.zzD(zzh, zzlj, this.zza);
        this.zzd.zzQ();
    }
}
