package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzi {
    public static zzap zza(zzgy zzgy) {
        if (zzgy == null) {
            return zzap.zzf;
        }
        switch (zzgy.zzj() - 1) {
            case 1:
                if (zzgy.zzi()) {
                    return new zzat(zzgy.zzd());
                }
                return zzap.zzm;
            case 2:
                if (zzgy.zzh()) {
                    return new zzah(Double.valueOf(zzgy.zza()));
                }
                return new zzah((Double) null);
            case 3:
                if (zzgy.zzg()) {
                    return new zzaf(Boolean.valueOf(zzgy.zzf()));
                }
                return new zzaf((Boolean) null);
            case 4:
                List<zzgy> zze = zzgy.zze();
                ArrayList arrayList = new ArrayList();
                for (zzgy zza : zze) {
                    arrayList.add(zza(zza));
                }
                return new zzaq(zzgy.zzc(), arrayList);
            default:
                throw new IllegalArgumentException("Unknown type found. Cannot convert entity");
        }
    }

    public static zzap zzb(Object obj) {
        if (obj == null) {
            return zzap.zzg;
        }
        if (obj instanceof String) {
            return new zzat((String) obj);
        }
        if (obj instanceof Double) {
            return new zzah((Double) obj);
        }
        if (obj instanceof Long) {
            return new zzah(Double.valueOf(((Long) obj).doubleValue()));
        }
        if (obj instanceof Integer) {
            return new zzah(Double.valueOf(((Integer) obj).doubleValue()));
        }
        if (obj instanceof Boolean) {
            return new zzaf((Boolean) obj);
        }
        if (obj instanceof Map) {
            zzam zzam = new zzam();
            Map map = (Map) obj;
            for (Object next : map.keySet()) {
                zzap zzb = zzb(map.get(next));
                if (next != null) {
                    if (!(next instanceof String)) {
                        next = next.toString();
                    }
                    zzam.zzr((String) next, zzb);
                }
            }
            return zzam;
        } else if (obj instanceof List) {
            zzae zzae = new zzae();
            for (Object zzb2 : (List) obj) {
                zzae.zzq(zzae.zzc(), zzb(zzb2));
            }
            return zzae;
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }
}
