package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfy implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ zzgb zza;
    private final String zzb;

    public zzfy(zzgb zzgb, String str) {
        this.zza = zzgb;
        Preconditions.checkNotNull(str);
        this.zzb = str;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zza.zzt.zzaA().zzd().zzb(this.zzb, th);
    }
}
