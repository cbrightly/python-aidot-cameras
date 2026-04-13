package com.google.android.gms.internal.vision;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzdt extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzdp zza;

    zzdt(zzdp zzdp) {
        this.zza = zzdp;
    }

    public final int size() {
        return this.zza.size();
    }

    public final void clear() {
        this.zza.clear();
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return this.zza.zzf();
    }

    public final boolean contains(@NullableDecl Object obj) {
        Map zzb = this.zza.zzb();
        if (zzb != null) {
            return zzb.entrySet().contains(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        int zzb2 = this.zza.zza(entry.getKey());
        if (zzb2 == -1 || !zzcz.zza(this.zza.zzc[zzb2], entry.getValue())) {
            return false;
        }
        return true;
    }

    public final boolean remove(@NullableDecl Object obj) {
        Map zzb = this.zza.zzb();
        if (zzb != null) {
            return zzb.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zza.zza()) {
            return false;
        }
        int zzb2 = this.zza.zzi();
        Object key = entry.getKey();
        Object value = entry.getValue();
        Object zzc = this.zza.zze;
        zzdp zzdp = this.zza;
        int zza2 = zzea.zza(key, value, zzb2, zzc, zzdp.zza, zzdp.zzb, zzdp.zzc);
        if (zza2 == -1) {
            return false;
        }
        this.zza.zza(zza2, zzb2);
        zzdp.zzd(this.zza);
        this.zza.zzc();
        return true;
    }
}
