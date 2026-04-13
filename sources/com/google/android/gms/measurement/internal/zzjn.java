package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjn implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzaw zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzjy zze;

    zzjn(zzjy zzjy, boolean z, zzq zzq, boolean z2, zzaw zzaw, String str) {
        this.zze = zzjy;
        this.zza = zzq;
        this.zzb = z2;
        this.zzc = zzaw;
        this.zzd = str;
    }

    public final void run() {
        zzaw zzaw;
        zzjy zzjy = this.zze;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Discarding data. Failed to send event to service");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        zzjy zzjy2 = this.zze;
        if (this.zzb) {
            zzaw = null;
        } else {
            zzaw = this.zzc;
        }
        zzjy2.zzD(zzh, zzaw, this.zza);
        this.zze.zzQ();
    }
}
