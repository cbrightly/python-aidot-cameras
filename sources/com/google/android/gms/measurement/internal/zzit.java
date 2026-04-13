package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzit implements Runnable {
    final /* synthetic */ zziq zza;
    final /* synthetic */ zziq zzb;
    final /* synthetic */ long zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zziy zze;

    zzit(zziy zziy, zziq zziq, zziq zziq2, long j, boolean z) {
        this.zze = zziy;
        this.zza = zziq;
        this.zzb = zziq2;
        this.zzc = j;
        this.zzd = z;
    }

    public final void run() {
        this.zze.zzA(this.zza, this.zzb, this.zzc, this.zzd, (Bundle) null);
    }
}
