package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhr implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzij zzb;

    zzhr(zzij zzij, long j) {
        this.zzb = zzij;
        this.zza = j;
    }

    public final void run() {
        this.zzb.zzL(this.zza, true);
        this.zzb.zzt.zzt().zzu(new AtomicReference());
    }
}
