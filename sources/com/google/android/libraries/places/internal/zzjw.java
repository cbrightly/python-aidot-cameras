package com.google.android.libraries.places.internal;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjw extends AbstractList implements RandomAccess, Serializable {
    final List zza;
    final zzcu zzb;

    zzjw(List list, zzcu zzcu) {
        if (list != null) {
            this.zza = list;
            this.zzb = zzcu;
            return;
        }
        throw null;
    }

    public final void clear() {
        this.zza.clear();
    }

    public final Object get(int i) {
        return ((zzcv) this.zza.get(i)).toString();
    }

    public final boolean isEmpty() {
        return this.zza.isEmpty();
    }

    public final Iterator iterator() {
        return listIterator();
    }

    public final ListIterator listIterator(int i) {
        return new zzjv(this, this.zza.listIterator(i));
    }

    public final Object remove(int i) {
        return ((zzcv) this.zza.remove(i)).toString();
    }

    public final int size() {
        return this.zza.size();
    }
}
