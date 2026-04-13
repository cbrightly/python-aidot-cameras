package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjc implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzjy zzb;

    zzjc(zzjy zzjy, zzq zzq) {
        this.zzb = zzjy;
        this.zza = zzq;
    }

    public final void run() {
        zzjy zzjy = this.zzb;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Failed to reset data on the service: not connected to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzh.zzm(this.zza);
        } catch (RemoteException e) {
            this.zzb.zzt.zzaA().zzd().zzb("Failed to reset data on the service: remote exception", e);
        }
        this.zzb.zzQ();
    }
}
