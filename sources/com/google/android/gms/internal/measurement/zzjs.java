package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzjs implements Comparator {
    zzjs() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzka zzka = (zzka) obj;
        zzka zzka2 = (zzka) obj2;
        zzjr zzjr = new zzjr(zzka);
        zzjr zzjr2 = new zzjr(zzka2);
        while (zzjr.hasNext() && zzjr2.hasNext()) {
            int compareTo = Integer.valueOf(zzjr.zza() & 255).compareTo(Integer.valueOf(zzjr2.zza() & 255));
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return Integer.valueOf(zzka.zzd()).compareTo(Integer.valueOf(zzka2.zzd()));
    }
}
