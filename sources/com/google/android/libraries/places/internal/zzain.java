package com.google.android.libraries.places.internal;

import java.util.Iterator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzain implements Iterator {
    final Iterator zza;
    final /* synthetic */ zzaio zzb;

    zzain(zzaio zzaio) {
        this.zzb = zzaio;
        this.zza = zzaio.zza.iterator();
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final /* bridge */ /* synthetic */ Object next() {
        return (String) this.zza.next();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
