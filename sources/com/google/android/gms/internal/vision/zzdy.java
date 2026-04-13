package com.google.android.gms.internal.vision;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzdy extends zzdl<K, V> {
    @NullableDecl
    private final K zza;
    private int zzb;
    private final /* synthetic */ zzdp zzc;

    zzdy(zzdp zzdp, int i) {
        this.zzc = zzdp;
        this.zza = zzdp.zzb[i];
        this.zzb = i;
    }

    @NullableDecl
    public final K getKey() {
        return this.zza;
    }

    private final void zza() {
        int i = this.zzb;
        if (i == -1 || i >= this.zzc.size() || !zzcz.zza(this.zza, this.zzc.zzb[this.zzb])) {
            this.zzb = this.zzc.zza((Object) this.zza);
        }
    }

    @NullableDecl
    public final V getValue() {
        Map zzb2 = this.zzc.zzb();
        if (zzb2 != null) {
            return zzb2.get(this.zza);
        }
        zza();
        int i = this.zzb;
        if (i == -1) {
            return null;
        }
        return this.zzc.zzc[i];
    }

    public final V setValue(V v) {
        Map zzb2 = this.zzc.zzb();
        if (zzb2 != null) {
            return zzb2.put(this.zza, v);
        }
        zza();
        int i = this.zzb;
        if (i == -1) {
            this.zzc.put(this.zza, v);
            return null;
        }
        V[] vArr = this.zzc.zzc;
        V v2 = vArr[i];
        vArr[i] = v;
        return v2;
    }
}
