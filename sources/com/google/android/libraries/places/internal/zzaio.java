package com.google.android.libraries.places.internal;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

@Deprecated
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaio extends AbstractList implements RandomAccess, zzagp {
    /* access modifiers changed from: private */
    public final zzagp zza;

    public zzaio(zzagp zzagp) {
        this.zza = zzagp;
    }

    public final /* bridge */ /* synthetic */ Object get(int i) {
        return ((zzago) this.zza).get(i);
    }

    public final Iterator iterator() {
        return new zzain(this);
    }

    public final ListIterator listIterator(int i) {
        return new zzaim(this, i);
    }

    public final int size() {
        return this.zza.size();
    }

    public final zzagp zzd() {
        return this;
    }

    public final Object zze(int i) {
        return this.zza.zze(i);
    }

    public final List zzh() {
        return this.zza.zzh();
    }
}
