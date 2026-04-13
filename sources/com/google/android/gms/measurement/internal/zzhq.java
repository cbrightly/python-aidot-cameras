package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhq implements Runnable {
    final /* synthetic */ AtomicReference zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzij zzc;

    zzhq(zzij zzij, AtomicReference atomicReference, boolean z) {
        this.zzc = zzij;
        this.zza = atomicReference;
        this.zzb = z;
    }

    public final void run() {
        this.zzc.zzt.zzt().zzx(this.zza, this.zzb);
    }
}
