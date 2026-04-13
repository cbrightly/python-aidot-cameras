package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjd implements Runnable {
    final /* synthetic */ AtomicReference zza;
    final /* synthetic */ zzq zzb;
    final /* synthetic */ zzjy zzc;

    zzjd(zzjy zzjy, AtomicReference atomicReference, zzq zzq) {
        this.zzc = zzjy;
        this.zza = atomicReference;
        this.zzb = zzq;
    }

    public final void run() {
        AtomicReference atomicReference;
        synchronized (this.zza) {
            try {
                if (!this.zzc.zzt.zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
                    this.zzc.zzt.zzaA().zzl().zza("Analytics storage consent denied; will not get app instance id");
                    this.zzc.zzt.zzq().zzO((String) null);
                    this.zzc.zzt.zzm().zze.zzb((String) null);
                    this.zza.set((Object) null);
                    this.zza.notify();
                    return;
                }
                zzjy zzjy = this.zzc;
                zzek zzh = zzjy.zzb;
                if (zzh == null) {
                    zzjy.zzt.zzaA().zzd().zza("Failed to get app instance id");
                    this.zza.notify();
                    return;
                }
                Preconditions.checkNotNull(this.zzb);
                this.zza.set(zzh.zzd(this.zzb));
                String str = (String) this.zza.get();
                if (str != null) {
                    this.zzc.zzt.zzq().zzO(str);
                    this.zzc.zzt.zzm().zze.zzb(str);
                }
                this.zzc.zzQ();
                atomicReference = this.zza;
                atomicReference.notify();
            } catch (RemoteException e) {
                try {
                    this.zzc.zzt.zzaA().zzd().zzb("Failed to get app instance id", e);
                    atomicReference = this.zza;
                } catch (Throwable th) {
                    this.zza.notify();
                    throw th;
                }
            }
        }
    }
}
