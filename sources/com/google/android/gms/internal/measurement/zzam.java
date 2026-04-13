package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public class zzam implements zzap, zzal {
    final Map zza = new HashMap();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzam)) {
            return false;
        }
        return this.zza.equals(((zzam) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (!this.zza.isEmpty()) {
            for (String str : this.zza.keySet()) {
                sb.append(String.format("%s: %s,", new Object[]{str, this.zza.get(str)}));
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("}");
        return sb.toString();
    }

    public final List zzb() {
        return new ArrayList(this.zza.keySet());
    }

    public zzap zzbU(String str, zzg zzg, List list) {
        if ("toString".equals(str)) {
            return new zzat(toString());
        }
        return zzaj.zza(this, new zzat(str), zzg, list);
    }

    public final zzap zzd() {
        zzam zzam = new zzam();
        for (Map.Entry entry : this.zza.entrySet()) {
            if (entry.getValue() instanceof zzal) {
                zzam.zza.put((String) entry.getKey(), (zzap) entry.getValue());
            } else {
                zzam.zza.put((String) entry.getKey(), ((zzap) entry.getValue()).zzd());
            }
        }
        return zzam;
    }

    public final zzap zzf(String str) {
        return this.zza.containsKey(str) ? (zzap) this.zza.get(str) : zzap.zzf;
    }

    public final Boolean zzg() {
        return true;
    }

    public final Double zzh() {
        return Double.valueOf(Double.NaN);
    }

    public final String zzi() {
        return "[object Object]";
    }

    public final Iterator zzl() {
        return zzaj.zzb(this.zza);
    }

    public final void zzr(String str, zzap zzap) {
        if (zzap == null) {
            this.zza.remove(str);
        } else {
            this.zza.put(str, zzap);
        }
    }

    public final boolean zzt(String str) {
        return this.zza.containsKey(str);
    }
}
