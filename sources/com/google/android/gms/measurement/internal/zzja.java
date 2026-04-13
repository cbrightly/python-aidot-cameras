package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzja implements Runnable {
    final /* synthetic */ AtomicReference zza;
    final /* synthetic */ zzq zzb;
    final /* synthetic */ boolean zzc;
    final /* synthetic */ zzjy zzd;

    zzja(zzjy zzjy, AtomicReference atomicReference, zzq zzq, boolean z) {
        this.zzd = zzjy;
        this.zza = atomicReference;
        this.zzb = zzq;
        this.zzc = z;
    }

    public final void run() {
        AtomicReference atomicReference;
        synchronized (this.zza) {
            try {
                zzjy zzjy = this.zzd;
                zzek zzh = zzjy.zzb;
                if (zzh == null) {
                    zzjy.zzt.zzaA().zzd().zza("Failed to get all user properties; not connected to service");
                    this.zza.notify();
                    return;
                }
                Preconditions.checkNotNull(this.zzb);
                this.zza.set(zzh.zze(this.zzb, this.zzc));
                this.zzd.zzQ();
                atomicReference = this.zza;
                atomicReference.notify();
            } catch (RemoteException e) {
                try {
                    this.zzd.zzt.zzaA().zzd().zzb("Failed to get all user properties; remote exception", e);
                    atomicReference = this.zza;
                } catch (Throwable th) {
                    this.zza.notify();
                    throw th;
                }
            }
        }
    }
}
