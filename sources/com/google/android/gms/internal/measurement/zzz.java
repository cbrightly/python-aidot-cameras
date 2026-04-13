package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.TreeMap;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzz {
    final TreeMap zza = new TreeMap();
    final TreeMap zzb = new TreeMap();

    private static final int zzc(zzg zzg, zzao zzao, zzap zzap) {
        zzap zza2 = zzao.zza(zzg, Collections.singletonList(zzap));
        if (zza2 instanceof zzah) {
            return zzh.zzb(zza2.zzh().doubleValue());
        }
        return -1;
    }

    public final void zza(String str, int i, zzao zzao, String str2) {
        TreeMap treeMap;
        if ("create".equals(str2)) {
            treeMap = this.zzb;
        } else if ("edit".equals(str2)) {
            treeMap = this.zza;
        } else {
            throw new IllegalStateException("Unknown callback type: ".concat(String.valueOf(str2)));
        }
        if (treeMap.containsKey(Integer.valueOf(i))) {
            i = ((Integer) treeMap.lastKey()).intValue() + 1;
        }
        treeMap.put(Integer.valueOf(i), zzao);
    }

    public final void zzb(zzg zzg, zzab zzab) {
        zzl zzl = new zzl(zzab);
        for (Integer num : this.zza.keySet()) {
            zzaa zzb2 = zzab.zzb().clone();
            int zzc = zzc(zzg, (zzao) this.zza.get(num), zzl);
            if (zzc == 2 || zzc == -1) {
                zzab.zzf(zzb2);
            }
        }
        for (Integer num2 : this.zzb.keySet()) {
            zzc(zzg, (zzao) this.zzb.get(num2), zzl);
        }
    }
}
