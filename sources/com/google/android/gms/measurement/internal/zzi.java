package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzcf;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.2.2 */
public final class zzi implements Runnable {
    final /* synthetic */ zzcf zza;
    final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzi(AppMeasurementDynamiteService appMeasurementDynamiteService, zzcf zzcf) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzcf;
    }

    public final void run() {
        this.zzb.zza.zzt().zzt(this.zza);
    }
}
