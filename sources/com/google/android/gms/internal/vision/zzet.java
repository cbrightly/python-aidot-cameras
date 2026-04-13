package com.google.android.gms.internal.vision;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzet<K> extends zzej<K> {
    private final transient zzef<K, ?> zza;
    private final transient zzee<K> zzb;

    zzet(zzef<K, ?> zzef, zzee<K> zzee) {
        this.zza = zzef;
        this.zzb = zzee;
    }

    public final zzfa<K> zza() {
        return (zzfa) zze().iterator();
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        return zze().zza(objArr, i);
    }

    public final zzee<K> zze() {
        return this.zzb;
    }

    public final boolean contains(@NullableDecl Object obj) {
        return this.zza.get(obj) != null;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        return true;
    }

    public final int size() {
        return this.zza.size();
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }
}
