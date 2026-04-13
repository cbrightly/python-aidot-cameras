package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzu extends zzai {
    private final Callable zza;

    public zzu(String str, Callable callable) {
        super("internal.appMetadata");
        this.zza = callable;
    }

    public final zzap zza(zzg zzg, List list) {
        try {
            return zzi.zzb(this.zza.call());
        } catch (Exception e) {
            return zzap.zzf;
        }
    }
}
