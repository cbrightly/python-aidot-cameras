package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzaj {
    public static zzap zza(zzal zzal, zzap zzap, zzg zzg, List list) {
        if (zzal.zzt(zzap.zzi())) {
            zzap zzf = zzal.zzf(zzap.zzi());
            if (zzf instanceof zzai) {
                return ((zzai) zzf).zza(zzg, list);
            }
            throw new IllegalArgumentException(String.format("%s is not a function", new Object[]{zzap.zzi()}));
        } else if ("hasOwnProperty".equals(zzap.zzi())) {
            zzh.zzh("hasOwnProperty", 1, list);
            if (zzal.zzt(zzg.zzb((zzap) list.get(0)).zzi())) {
                return zzap.zzk;
            }
            return zzap.zzl;
        } else {
            throw new IllegalArgumentException(String.format("Object has no function %s", new Object[]{zzap.zzi()}));
        }
    }

    public static Iterator zzb(Map map) {
        return new zzak(map.keySet().iterator());
    }
}
