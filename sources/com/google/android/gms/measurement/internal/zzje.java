package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcf;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzje implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzcf zzb;
    final /* synthetic */ zzjy zzc;

    zzje(zzjy zzjy, zzq zzq, zzcf zzcf) {
        this.zzc = zzjy;
        this.zza = zzq;
        this.zzb = zzcf;
    }

    public final void run() {
        zzge zzge;
        String str = null;
        try {
            if (!this.zzc.zzt.zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
                this.zzc.zzt.zzaA().zzl().zza("Analytics storage consent denied; will not get app instance id");
                this.zzc.zzt.zzq().zzO((String) null);
                this.zzc.zzt.zzm().zze.zzb((String) null);
                zzge = this.zzc.zzt;
            } else {
                zzjy zzjy = this.zzc;
                zzek zzh = zzjy.zzb;
                if (zzh == null) {
                    zzjy.zzt.zzaA().zzd().zza("Failed to get app instance id");
                    zzge = this.zzc.zzt;
                } else {
                    Preconditions.checkNotNull(this.zza);
                    str = zzh.zzd(this.zza);
                    if (str != null) {
                        try {
                            this.zzc.zzt.zzq().zzO(str);
                            this.zzc.zzt.zzm().zze.zzb(str);
                        } catch (RemoteException e) {
                            e = e;
                            try {
                                this.zzc.zzt.zzaA().zzd().zzb("Failed to get app instance id", e);
                                zzge = this.zzc.zzt;
                                zzge.zzv().zzW(this.zzb, str);
                            } catch (Throwable th) {
                                th = th;
                                this.zzc.zzt.zzv().zzW(this.zzb, str);
                                throw th;
                            }
                        }
                    }
                    this.zzc.zzQ();
                    zzge = this.zzc.zzt;
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            this.zzc.zzt.zzaA().zzd().zzb("Failed to get app instance id", e);
            zzge = this.zzc.zzt;
            zzge.zzv().zzW(this.zzb, str);
        } catch (Throwable th2) {
            th = th2;
            this.zzc.zzt.zzv().zzW(this.zzb, str);
            throw th;
        }
        zzge.zzv().zzW(this.zzb, str);
    }
}
