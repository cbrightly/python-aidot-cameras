package com.google.android.gms.internal.vision;

import java.util.AbstractMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzeu extends zzee<Map.Entry<K, V>> {
    private final /* synthetic */ zzer zza;

    zzeu(zzer zzer) {
        this.zza = zzer;
    }

    public final int size() {
        return this.zza.zzd;
    }

    public final boolean zzf() {
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzde.zza(i, this.zza.zzd);
        int i2 = i * 2;
        return new AbstractMap.SimpleImmutableEntry(this.zza.zzb[i2], this.zza.zzb[i2 + 1]);
    }
}
