package com.google.android.libraries.places.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmc extends zzlr {
    /* access modifiers changed from: private */
    public static final Set zza;
    /* access modifiers changed from: private */
    public static final zzlj zzb;
    private static final zzlz zzc = new zzlz();
    private final String zzd;
    private final Level zze;
    private final Set zzf;
    private final zzlj zzg;

    static {
        Set unmodifiableSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(new zzkv[]{zzks.zza, zzkx.zza})));
        zza = unmodifiableSet;
        zzb = zzlm.zza(unmodifiableSet).zzd();
    }

    /* synthetic */ zzmc(String str, String str2, boolean z, boolean z2, Level level, Set set, zzlj zzlj, zzmb zzmb) {
        super(str2);
        if (str2.length() > 23) {
            int i = -1;
            int length = str2.length() - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                char charAt = str2.charAt(length);
                if (charAt == '.' || charAt == '$') {
                    i = length;
                } else {
                    length--;
                }
            }
            i = length;
            str2 = str2.substring(i + 1);
        }
        String concat = "".concat(String.valueOf(str2));
        this.zzd = concat.substring(0, Math.min(concat.length(), 23));
        this.zze = level;
        this.zzf = set;
        this.zzg = zzlj;
    }
}
