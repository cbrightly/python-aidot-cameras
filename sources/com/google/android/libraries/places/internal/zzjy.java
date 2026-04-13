package com.google.android.libraries.places.internal;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjy extends AbstractSequentialList implements Serializable {
    final List zza;
    final zzcu zzb;

    zzjy(List list, zzcu zzcu) {
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

    public final ListIterator listIterator(int i) {
        return new zzjx(this, this.zza.listIterator(i));
    }

    public final int size() {
        return this.zza.size();
    }
}
