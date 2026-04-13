package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmd {
    zzmd() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzmc zzmc = (zzmc) obj;
        zzmb zzmb = (zzmb) obj2;
        if (zzmc.isEmpty()) {
            return 0;
        }
        Iterator it = zzmc.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzmc zzmc = (zzmc) obj;
        zzmc zzmc2 = (zzmc) obj2;
        if (!zzmc2.isEmpty()) {
            if (!zzmc.zze()) {
                zzmc = zzmc.zzb();
            }
            zzmc.zzd(zzmc2);
        }
        return zzmc;
    }
}
