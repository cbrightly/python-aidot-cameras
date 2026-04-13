package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmc extends LinkedHashMap {
    private static final zzmc zza;
    private boolean zzb = true;

    static {
        zzmc zzmc = new zzmc();
        zza = zzmc;
        zzmc.zzb = false;
    }

    private zzmc() {
    }

    public static zzmc zza() {
        return zza;
    }

    private static int zzf(Object obj) {
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = zzlj.zzd;
            int length = bArr.length;
            int zzb2 = zzlj.zzb(length, bArr, 0, length);
            if (zzb2 == 0) {
                return 1;
            }
            return zzb2;
        } else if (!(obj instanceof zzld)) {
            return obj.hashCode();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private final void zzg() {
        if (!this.zzb) {
            throw new UnsupportedOperationException();
        }
    }

    public final void clear() {
        zzg();
        super.clear();
    }

    public final Set entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (this == map) {
            return true;
        }
        if (size() != map.size()) {
            return false;
        }
        for (Map.Entry entry : entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                return false;
            }
            Object value = entry.getValue();
            Object obj2 = map.get(entry.getKey());
            if (!(value instanceof byte[]) || !(obj2 instanceof byte[])) {
                z = value.equals(obj2);
                continue;
            } else {
                z = Arrays.equals((byte[]) value, (byte[]) obj2);
                continue;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        for (Map.Entry entry : entrySet()) {
            i += zzf(entry.getValue()) ^ zzf(entry.getKey());
        }
        return i;
    }

    public final Object put(Object obj, Object obj2) {
        zzg();
        byte[] bArr = zzlj.zzd;
        if (obj == null) {
            throw null;
        } else if (obj2 != null) {
            return super.put(obj, obj2);
        } else {
            throw null;
        }
    }

    public final void putAll(Map map) {
        zzg();
        for (Object next : map.keySet()) {
            byte[] bArr = zzlj.zzd;
            if (next == null) {
                throw null;
            } else if (map.get(next) == null) {
                throw null;
            }
        }
        super.putAll(map);
    }

    public final Object remove(Object obj) {
        zzg();
        return super.remove(obj);
    }

    public final zzmc zzb() {
        return isEmpty() ? new zzmc() : new zzmc(this);
    }

    public final void zzc() {
        this.zzb = false;
    }

    public final void zzd(zzmc zzmc) {
        zzg();
        if (!zzmc.isEmpty()) {
            putAll(zzmc);
        }
    }

    public final boolean zze() {
        return this.zzb;
    }

    private zzmc(Map map) {
        super(map);
    }
}
