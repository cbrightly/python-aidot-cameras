package com.google.android.libraries.places.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmm implements Iterator {
    final /* synthetic */ zzmn zza;
    private int zzb = 0;

    zzmm(zzmn zzmn) {
        this.zza = zzmn;
    }

    public final boolean hasNext() {
        int i = this.zzb;
        zzmn zzmn = this.zza;
        return i < zzmn.zza() - zzmn.zzb();
    }

    public final Object next() {
        int i = this.zzb;
        zzmn zzmn = this.zza;
        if (i < zzmn.zza() - zzmn.zzb()) {
            zzmn zzmn2 = this.zza;
            Object obj = zzmn2.zzb.zzb[zzmn2.zzb() + i];
            this.zzb = i + 1;
            return obj;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
