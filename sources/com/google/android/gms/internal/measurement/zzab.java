package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzab {
    private zzaa zza;
    private zzaa zzb;
    private final List zzc;

    public zzab() {
        this.zza = new zzaa("", 0, (Map) null);
        this.zzb = new zzaa("", 0, (Map) null);
        this.zzc = new ArrayList();
    }

    public final /* bridge */ /* synthetic */ Object clone() {
        zzab zzab = new zzab(this.zza.clone());
        for (zzaa zzb2 : this.zzc) {
            zzab.zzc.add(zzb2.clone());
        }
        return zzab;
    }

    public final zzaa zza() {
        return this.zza;
    }

    public final zzaa zzb() {
        return this.zzb;
    }

    public final List zzc() {
        return this.zzc;
    }

    public final void zzd(zzaa zzaa) {
        this.zza = zzaa;
        this.zzb = zzaa.clone();
        this.zzc.clear();
    }

    public final void zze(String str, long j, Map map) {
        this.zzc.add(new zzaa(str, j, map));
    }

    public final void zzf(zzaa zzaa) {
        this.zzb = zzaa;
    }

    public zzab(zzaa zzaa) {
        this.zza = zzaa;
        this.zzb = zzaa.clone();
        this.zzc = new ArrayList();
    }
}
