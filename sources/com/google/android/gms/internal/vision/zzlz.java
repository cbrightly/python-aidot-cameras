package com.google.android.gms.internal.vision;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzlz extends AbstractList<String> implements zzjv, RandomAccess {
    /* access modifiers changed from: private */
    public final zzjv zza;

    public zzlz(zzjv zzjv) {
        this.zza = zzjv;
    }

    public final Object zzb(int i) {
        return this.zza.zzb(i);
    }

    public final int size() {
        return this.zza.size();
    }

    public final void zza(zzht zzht) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzly(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzmb(this);
    }

    public final List<?> zzd() {
        return this.zza.zzd();
    }

    public final zzjv zze() {
        return this;
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zza.get(i);
    }
}
