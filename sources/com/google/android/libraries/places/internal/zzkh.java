package com.google.android.libraries.places.internal;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzkh extends zzju {
    private final transient zzjt zza;
    private final transient zzjq zzb;

    zzkh(zzjt zzjt, zzjq zzjq) {
        this.zza = zzjt;
        this.zzb = zzjq;
    }

    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.get(obj) != null;
    }

    public final /* synthetic */ Iterator iterator() {
        return this.zzb.listIterator(0);
    }

    public final int size() {
        return this.zza.size();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        return this.zzb.zza(objArr, 0);
    }

    public final zzjq zzd() {
        return this.zzb;
    }

    public final zzkn zze() {
        return this.zzb.listIterator(0);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        throw null;
    }
}
