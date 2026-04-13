package com.google.android.libraries.places.internal;

import java.util.Set;
import java.util.logging.Level;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzlz implements zzlt {
    private final String zza;
    private final Level zzb;
    private final Set zzc;
    private final zzlj zzd;

    public zzlz() {
        this("", true, false, Level.ALL, false, zzmc.zza, zzmc.zzb);
    }

    private zzlz(String str, boolean z, boolean z2, Level level, boolean z3, Set set, zzlj zzlj) {
        this.zza = "";
        this.zzb = level;
        this.zzc = set;
        this.zzd = zzlj;
    }

    public final zzky zza(String str) {
        return new zzmc(this.zza, str, true, false, this.zzb, this.zzc, this.zzd, (zzmb) null);
    }

    public final zzlz zzb(boolean z) {
        return new zzlz(this.zza, true, false, Level.OFF, false, this.zzc, this.zzd);
    }
}
