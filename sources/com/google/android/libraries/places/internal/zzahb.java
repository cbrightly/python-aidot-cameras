package com.google.android.libraries.places.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahb extends LinkedHashMap {
    private static final zzahb zza;
    private boolean zzb = true;

    static {
        zzahb zzahb = new zzahb();
        zza = zzahb;
        zzahb.zzb = false;
    }

    private zzahb() {
    }

    private static int zze(Object obj) {
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = zzagi.zzd;
            int length = bArr.length;
            int zzb2 = zzagi.zzb(length, bArr, 0, length);
            if (zzb2 == 0) {
                return 1;
            }
            return zzb2;
        } else if (!(obj instanceof zzagb)) {
            return obj.hashCode();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private final void zzf() {
        if (!this.zzb) {
            throw new UnsupportedOperationException();
        }
    }

    public final void clear() {
        zzf();
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
            i += zze(entry.getValue()) ^ zze(entry.getKey());
        }
        return i;
    }

    public final Object put(Object obj, Object obj2) {
        zzf();
        byte[] bArr = zzagi.zzd;
        if (obj == null) {
            throw null;
        } else if (obj2 != null) {
            return super.put(obj, obj2);
        } else {
            throw null;
        }
    }

    public final void putAll(Map map) {
        zzf();
        for (Object next : map.keySet()) {
            byte[] bArr = zzagi.zzd;
            if (next == null) {
                throw null;
            } else if (map.get(next) == null) {
                throw null;
            }
        }
        super.putAll(map);
    }

    public final Object remove(Object obj) {
        zzf();
        return super.remove(obj);
    }

    public final zzahb zza() {
        return isEmpty() ? new zzahb() : new zzahb(this);
    }

    public final void zzb() {
        this.zzb = false;
    }

    public final void zzc(zzahb zzahb) {
        zzf();
        if (!zzahb.isEmpty()) {
            putAll(zzahb);
        }
    }

    public final boolean zzd() {
        return this.zzb;
    }

    private zzahb(Map map) {
        super(map);
    }
}
