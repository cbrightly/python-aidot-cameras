package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjg implements Runnable {
    final /* synthetic */ zziq zza;
    final /* synthetic */ zzjy zzb;

    zzjg(zzjy zzjy, zziq zziq) {
        this.zzb = zzjy;
        this.zza = zziq;
    }

    public final void run() {
        zzjy zzjy = this.zzb;
        zzek zzh = zzjy.zzb;
        if (zzh == null) {
            zzjy.zzt.zzaA().zzd().zza("Failed to send current screen to service");
            return;
        }
        try {
            zziq zziq = this.zza;
            if (zziq == null) {
                zzh.zzq(0, (String) null, (String) null, zzjy.zzt.zzaw().getPackageName());
            } else {
                zzh.zzq(zziq.zzc, zziq.zza, zziq.zzb, zzjy.zzt.zzaw().getPackageName());
            }
            this.zzb.zzQ();
        } catch (RemoteException e) {
            this.zzb.zzt.zzaA().zzd().zzb("Failed to send current screen to the service", e);
        }
    }
}
