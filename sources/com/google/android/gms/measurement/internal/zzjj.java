package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzcf;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjj implements Runnable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzcf zzc;
    final /* synthetic */ zzjy zzd;

    zzjj(zzjy zzjy, zzaw zzaw, String str, zzcf zzcf) {
        this.zzd = zzjy;
        this.zza = zzaw;
        this.zzb = str;
        this.zzc = zzcf;
    }

    public final void run() {
        zzge zzge;
        byte[] bArr = null;
        try {
            zzjy zzjy = this.zzd;
            zzek zzh = zzjy.zzb;
            if (zzh == null) {
                zzjy.zzt.zzaA().zzd().zza("Discarding data. Failed to send event to service to bundle");
                zzge = this.zzd.zzt;
            } else {
                bArr = zzh.zzu(this.zza, this.zzb);
                try {
                    this.zzd.zzQ();
                    zzge = this.zzd.zzt;
                } catch (RemoteException e) {
                    e = e;
                    try {
                        this.zzd.zzt.zzaA().zzd().zzb("Failed to send event to the service to bundle", e);
                        zzge = this.zzd.zzt;
                        zzge.zzv().zzT(this.zzc, bArr);
                    } catch (Throwable th) {
                        th = th;
                        this.zzd.zzt.zzv().zzT(this.zzc, bArr);
                        throw th;
                    }
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            this.zzd.zzt.zzaA().zzd().zzb("Failed to send event to the service to bundle", e);
            zzge = this.zzd.zzt;
            zzge.zzv().zzT(this.zzc, bArr);
        } catch (Throwable th2) {
            th = th2;
            this.zzd.zzt.zzv().zzT(this.zzc, bArr);
            throw th;
        }
        zzge.zzv().zzT(this.zzc, bArr);
    }
}
