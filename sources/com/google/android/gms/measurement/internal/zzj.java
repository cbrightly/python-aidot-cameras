package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzcf;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.2.2 */
public final class zzj implements Runnable {
    final /* synthetic */ zzcf zza;
    final /* synthetic */ zzaw zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ AppMeasurementDynamiteService zzd;

    zzj(AppMeasurementDynamiteService appMeasurementDynamiteService, zzcf zzcf, zzaw zzaw, String str) {
        this.zzd = appMeasurementDynamiteService;
        this.zza = zzcf;
        this.zzb = zzaw;
        this.zzc = str;
    }

    public final void run() {
        this.zzd.zza.zzt().zzB(this.zza, this.zzb, this.zzc);
    }
}
