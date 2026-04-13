package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzno implements Iterator {
    final Iterator zza;
    final /* synthetic */ zznp zzb;

    zzno(zznp zznp) {
        this.zzb = zznp;
        this.zza = zznp.zza.iterator();
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
