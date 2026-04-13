package com.google.android.libraries.places.internal;

import java.util.Comparator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaew implements Comparator {
    zzaew() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzafe zzafe = (zzafe) obj;
        zzafe zzafe2 = (zzafe) obj2;
        zzaev zzaev = new zzaev(zzafe);
        zzaev zzaev2 = new zzaev(zzafe2);
        while (zzaev.hasNext() && zzaev2.hasNext()) {
            int compareTo = Integer.valueOf(zzaev.zza() & 255).compareTo(Integer.valueOf(zzaev2.zza() & 255));
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return Integer.valueOf(zzafe.zzd()).compareTo(Integer.valueOf(zzafe2.zzd()));
    }
}
