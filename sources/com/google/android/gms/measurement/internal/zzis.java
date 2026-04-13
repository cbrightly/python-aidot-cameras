package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzis implements Runnable {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zziq zzb;
    final /* synthetic */ zziq zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zziy zze;

    zzis(zziy zziy, Bundle bundle, zziq zziq, zziq zziq2, long j) {
        this.zze = zziy;
        this.zza = bundle;
        this.zzb = zziq;
        this.zzc = zziq2;
        this.zzd = j;
    }

    public final void run() {
        zziy.zzp(this.zze, this.zza, this.zzb, this.zzc, this.zzd);
    }
}
