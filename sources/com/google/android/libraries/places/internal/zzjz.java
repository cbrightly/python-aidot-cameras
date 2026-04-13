package com.google.android.libraries.places.internal;

import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjz {
    public static List zza(List list, zzcu zzcu) {
        if (list instanceof RandomAccess) {
            return new zzjw(list, zzcu);
        }
        return new zzjy(list, zzcu);
    }
}
