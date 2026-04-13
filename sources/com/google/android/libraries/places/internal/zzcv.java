package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import java.util.Arrays;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzcv {
    private final String zza;

    private zzcv(String str) {
        this.zza = str;
    }

    public static zzcv zza(zzcv zzcv, zzcv... zzcvArr) {
        return new zzcv(zzcv.zza.concat(zzit.zzb("").zze(zzjz.zza(Arrays.asList(zzcvArr), zzcu.zza))));
    }

    public static zzcv zzb(String str) {
        return new zzcv(str);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj instanceof zzcv) {
            return this.zza.equals(((zzcv) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final String toString() {
        return this.zza;
    }
}
