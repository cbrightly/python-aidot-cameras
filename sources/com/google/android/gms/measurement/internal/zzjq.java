package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcf;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjq implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzq zzc;
    final /* synthetic */ zzcf zzd;
    final /* synthetic */ zzjy zze;

    zzjq(zzjy zzjy, String str, String str2, zzq zzq, zzcf zzcf) {
        this.zze = zzjy;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzq;
        this.zzd = zzcf;
    }

    public final void run() {
        zzge zzge;
        ArrayList arrayList = new ArrayList();
        try {
            zzjy zzjy = this.zze;
            zzek zzh = zzjy.zzb;
            if (zzh == null) {
                zzjy.zzt.zzaA().zzd().zzc("Failed to get conditional properties; not connected to service", this.zza, this.zzb);
                zzge = this.zze.zzt;
            } else {
                Preconditions.checkNotNull(this.zzc);
                arrayList = zzlo.zzH(zzh.zzf(this.zza, this.zzb, this.zzc));
                try {
                    this.zze.zzQ();
                    zzge = this.zze.zzt;
                } catch (RemoteException e) {
                    e = e;
                    try {
                        this.zze.zzt.zzaA().zzd().zzd("Failed to get conditional properties; remote exception", this.zza, this.zzb, e);
                        zzge = this.zze.zzt;
                        zzge.zzv().zzR(this.zzd, arrayList);
                    } catch (Throwable th) {
                        th = th;
                        this.zze.zzt.zzv().zzR(this.zzd, arrayList);
                        throw th;
                    }
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            this.zze.zzt.zzaA().zzd().zzd("Failed to get conditional properties; remote exception", this.zza, this.zzb, e);
            zzge = this.zze.zzt;
            zzge.zzv().zzR(this.zzd, arrayList);
        } catch (Throwable th2) {
            th = th2;
            this.zze.zzt.zzv().zzR(this.zzd, arrayList);
            throw th;
        }
        zzge.zzv().zzR(this.zzd, arrayList);
    }
}
