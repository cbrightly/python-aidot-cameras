package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjf implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzjy zzb;

    zzjf(zzjy zzjy, zzq zzq) {
        this.zzb = zzjy;
        this.zza = zzq;
    }

    public final void run() {
        zzjy zzjy = this.zzb;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Discarding data. Failed to send app launch");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzh.zzj(this.zza);
            this.zzb.zzt.zzi().zzm();
            this.zzb.zzD(zzh, (AbstractSafeParcelable) null, this.zza);
            this.zzb.zzQ();
        } catch (RemoteException e) {
            this.zzb.zzt.zzaA().zzd().zzb("Failed to send app launch to the service", e);
        }
    }
}
