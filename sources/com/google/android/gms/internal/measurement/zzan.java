package com.google.android.gms.internal.measurement;

import com.google.maps.android.BuildConfig;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzan implements zzap {
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof zzan;
    }

    public final int hashCode() {
        return 1;
    }

    public final zzap zzbU(String str, zzg zzg, List list) {
        throw new IllegalStateException(String.format("null has no function %s", new Object[]{str}));
    }

    public final zzap zzd() {
        return zzap.zzg;
    }

    public final Boolean zzg() {
        return false;
    }

    public final Double zzh() {
        return Double.valueOf(0.0d);
    }

    public final String zzi() {
        return BuildConfig.TRAVIS;
    }

    public final Iterator zzl() {
        return null;
    }
}
