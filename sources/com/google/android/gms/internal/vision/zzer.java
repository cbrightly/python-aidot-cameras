package com.google.android.gms.internal.vision;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzer<K, V> extends zzej<Map.Entry<K, V>> {
    private final transient zzef<K, V> zza;
    /* access modifiers changed from: private */
    public final transient Object[] zzb;
    private final transient int zzc = 0;
    /* access modifiers changed from: private */
    public final transient int zzd;

    zzer(zzef<K, V> zzef, Object[] objArr, int i, int i2) {
        this.zza = zzef;
        this.zzb = objArr;
        this.zzd = i2;
    }

    public final zzfa<Map.Entry<K, V>> zza() {
        return (zzfa) zze().iterator();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        return zze().zza(objArr, i);
    }

    /* access modifiers changed from: package-private */
    public final zzee<Map.Entry<K, V>> zzh() {
        return new zzeu(this);
    }

    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (value == null || !value.equals(this.zza.get(key))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        return true;
    }

    public final int size() {
        return this.zzd;
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
