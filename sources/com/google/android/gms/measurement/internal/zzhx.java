package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzcf;
import com.google.android.gms.internal.measurement.zzqo;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhx implements Runnable {
    final /* synthetic */ zzcf zza;
    final /* synthetic */ zzij zzb;

    zzhx(zzij zzij, zzcf zzcf) {
        this.zzb = zzij;
        this.zza = zzcf;
    }

    public final void run() {
        Long l;
        zzko zzu = this.zzb.zzt.zzu();
        zzqo.zzc();
        if (!zzu.zzt.zzf().zzs((String) null, zzeh.zzau)) {
            zzu.zzt.zzaA().zzl().zza("getSessionId has been disabled.");
            l = null;
        } else if (!zzu.zzt.zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
            zzu.zzt.zzaA().zzl().zza("Analytics storage consent denied; will not get session id");
            l = null;
        } else {
            l = !zzu.zzt.zzm().zzk(zzu.zzt.zzax().currentTimeMillis()) ? zzu.zzt.zzm().zzk.zza() == 0 ? null : Long.valueOf(zzu.zzt.zzm().zzk.zza()) : null;
        }
        if (l != null) {
            this.zzb.zzt.zzv().zzV(this.zza, l.longValue());
            return;
        }
        try {
            this.zza.zze((Bundle) null);
        } catch (RemoteException e) {
            this.zzb.zzt.zzaA().zzd().zzb("getSessionId failed with exception", e);
        }
    }
}
