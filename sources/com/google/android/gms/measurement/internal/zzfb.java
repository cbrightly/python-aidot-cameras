package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfb implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzfc zzb;

    zzfb(zzfc zzfc, boolean z) {
        this.zzb = zzfc;
        this.zza = z;
    }

    public final void run() {
        this.zzb.zzb.zzJ(this.zza);
    }
}
