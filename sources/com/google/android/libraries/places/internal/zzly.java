package com.google.android.libraries.places.internal;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzly extends zzmd {
    private static final zzly zza = new zzly(zzmd.zze());
    private final AtomicReference zzb;

    zzly(zzmd zzmd) {
        this.zzb = new AtomicReference(zzmd);
    }

    public static final zzly zzb() {
        return zza;
    }

    public final zzla zza() {
        return ((zzmd) this.zzb.get()).zza();
    }

    public final zzmq zzc() {
        return ((zzmd) this.zzb.get()).zzc();
    }

    public final boolean zzd(String str, Level level, boolean z) {
        ((zzmd) this.zzb.get()).zzd(str, level, z);
        return false;
    }
}
