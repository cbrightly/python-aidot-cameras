package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcf;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zziz implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzq zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zzcf zze;
    final /* synthetic */ zzjy zzf;

    zziz(zzjy zzjy, String str, String str2, zzq zzq, boolean z, zzcf zzcf) {
        this.zzf = zzjy;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzq;
        this.zzd = z;
        this.zze = zzcf;
    }

    public final void run() {
        Throwable th;
        Bundle bundle;
        RemoteException e;
        Bundle bundle2 = new Bundle();
        try {
            zzjy zzjy = this.zzf;
            zzek zzh = zzjy.zzb;
            if (zzh == null) {
                zzjy.zzt.zzaA().zzd().zzc("Failed to get user properties; not connected to service", this.zza, this.zzb);
                this.zzf.zzt.zzv().zzS(this.zze, bundle2);
                return;
            }
            Preconditions.checkNotNull(this.zzc);
            List<zzlj> zzh2 = zzh.zzh(this.zza, this.zzb, this.zzd, this.zzc);
            bundle = new Bundle();
            if (zzh2 != null) {
                for (zzlj zzlj : zzh2) {
                    String str = zzlj.zze;
                    if (str != null) {
                        bundle.putString(zzlj.zzb, str);
                    } else {
                        Long l = zzlj.zzd;
                        if (l != null) {
                            bundle.putLong(zzlj.zzb, l.longValue());
                        } else {
                            Double d = zzlj.zzg;
                            if (d != null) {
                                bundle.putDouble(zzlj.zzb, d.doubleValue());
                            }
                        }
                    }
                }
            }
            try {
                this.zzf.zzQ();
                this.zzf.zzt.zzv().zzS(this.zze, bundle);
            } catch (RemoteException e2) {
                e = e2;
                try {
                    this.zzf.zzt.zzaA().zzd().zzc("Failed to get user properties; remote exception", this.zza, e);
                    this.zzf.zzt.zzv().zzS(this.zze, bundle);
                } catch (Throwable th2) {
                    th = th2;
                    bundle2 = bundle;
                    this.zzf.zzt.zzv().zzS(this.zze, bundle2);
                    throw th;
                }
            }
        } catch (RemoteException e3) {
            bundle = bundle2;
            e = e3;
            this.zzf.zzt.zzaA().zzd().zzc("Failed to get user properties; remote exception", this.zza, e);
            this.zzf.zzt.zzv().zzS(this.zze, bundle);
        } catch (Throwable th3) {
            th = th3;
            this.zzf.zzt.zzv().zzS(this.zze, bundle2);
            throw th;
        }
    }
}
