package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzql;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzki implements Runnable {
    public final /* synthetic */ zzkj zza;

    public /* synthetic */ zzki(zzkj zzkj) {
        this.zza = zzkj;
    }

    public final void run() {
        zzkj zzkj = this.zza;
        zzkk zzkk = zzkj.zzc;
        long j = zzkj.zza;
        long j2 = zzkj.zzb;
        zzkk.zza.zzg();
        zzkk.zza.zzt.zzaA().zzc().zza("Application going to the background");
        zzkk.zza.zzt.zzm().zzm.zza(true);
        if (!zzkk.zza.zzt.zzf().zzu()) {
            zzkk.zza.zzb.zzb(j2);
            zzkk.zza.zzb.zzd(false, false, j2);
        }
        zzql.zzc();
        if (zzkk.zza.zzt.zzf().zzs((String) null, zzeh.zzaB)) {
            zzkk.zza.zzt.zzaA().zzi().zzb("Application backgrounded at: timestamp_millis", Long.valueOf(j));
        } else {
            zzkk.zza.zzt.zzq().zzH("auto", "_ab", j, new Bundle());
        }
    }
}
