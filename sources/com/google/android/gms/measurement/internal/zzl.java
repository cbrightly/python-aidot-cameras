package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-sdk@@21.2.2 */
public final class zzl implements Runnable {
    final /* synthetic */ zzo zza;
    final /* synthetic */ AppMeasurementDynamiteService zzb;

    zzl(AppMeasurementDynamiteService appMeasurementDynamiteService, zzo zzo) {
        this.zzb = appMeasurementDynamiteService;
        this.zza = zzo;
    }

    public final void run() {
        this.zzb.zza.zzq().zzT(this.zza);
    }
}
