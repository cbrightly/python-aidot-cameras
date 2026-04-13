package com.google.android.libraries.places.internal;

import java.util.Set;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzma extends zzlr {
    private final Level zza;
    private final Set zzb;
    private final zzlj zzc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzma(String str, @NullableDecl String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        super(str2);
        Level level = Level.ALL;
        Set zzc2 = zzmc.zza;
        zzlj zzb2 = zzmc.zzb;
        this.zza = level;
        this.zzb = zzc2;
        this.zzc = zzb2;
    }
}
