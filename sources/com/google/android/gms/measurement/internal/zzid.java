package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzid implements Runnable {
    final /* synthetic */ Boolean zza;
    final /* synthetic */ zzij zzb;

    zzid(zzij zzij, Boolean bool) {
        this.zzb = zzij;
        this.zza = bool;
    }

    public final void run() {
        this.zzb.zzaa(this.zza, true);
    }
}
