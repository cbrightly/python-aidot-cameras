package com.google.android.libraries.places.internal;

import java.util.Comparator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmi implements Comparator {
    zzmi() {
    }

    public final int compare(Object obj, Object obj2) {
        zzmp zza = zzmp.zza(obj);
        zzmp zza2 = zzmp.zza(obj2);
        if (zza != zza2) {
            return zza.compareTo(zza2);
        }
        switch (zza.ordinal()) {
            case 0:
                return ((Boolean) obj).compareTo((Boolean) obj2);
            case 1:
                return ((String) obj).compareTo((String) obj2);
            case 2:
                return ((Long) obj).compareTo((Long) obj2);
            case 3:
                return ((Double) obj).compareTo((Double) obj2);
            default:
                throw null;
        }
    }
}
