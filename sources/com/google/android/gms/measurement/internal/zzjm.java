package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjm implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzjy zzb;

    zzjm(zzjy zzjy, zzq zzq) {
        this.zzb = zzjy;
        this.zza = zzq;
    }

    public final void run() {
        zzjy zzjy = this.zzb;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Failed to send consent settings to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzh.zzp(this.zza);
            this.zzb.zzQ();
        } catch (RemoteException e) {
            this.zzb.zzt.zzaA().zzd().zzb("Failed to send consent settings to the service", e);
        }
    }
}
