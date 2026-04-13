package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhw implements Runnable {
    final /* synthetic */ AtomicReference zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zzij zze;

    zzhw(zzij zzij, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zze = zzij;
        this.zza = atomicReference;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = z;
    }

    public final void run() {
        this.zze.zzt.zzt().zzz(this.zza, (String) null, this.zzb, this.zzc, this.zzd);
    }
}
