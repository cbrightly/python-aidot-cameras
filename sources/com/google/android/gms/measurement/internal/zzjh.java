package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjh implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ Bundle zzb;
    final /* synthetic */ zzjy zzc;

    zzjh(zzjy zzjy, zzq zzq, Bundle bundle) {
        this.zzc = zzjy;
        this.zza = zzq;
        this.zzb = bundle;
    }

    public final void run() {
        zzjy zzjy = this.zzc;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Failed to send default event parameters to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzh.zzr(this.zzb, this.zza);
        } catch (RemoteException e) {
            this.zzc.zzt.zzaA().zzd().zzb("Failed to send default event parameters to service", e);
        }
    }
}
