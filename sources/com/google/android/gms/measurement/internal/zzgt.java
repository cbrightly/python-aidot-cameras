package com.google.android.gms.measurement.internal;

import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzgt implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ zzgw zzb;

    zzgt(zzgw zzgw, String str) {
        this.zzb = zzgw;
        this.zza = str;
    }

    public final /* bridge */ /* synthetic */ Object call() {
        this.zzb.zza.zzA();
        return this.zzb.zza.zzi().zzu(this.zza);
    }
}
