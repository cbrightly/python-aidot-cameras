package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzar implements Iterator {
    final /* synthetic */ zzat zza;
    private int zzb = 0;

    zzar(zzat zzat) {
        this.zza = zzat;
    }

    public final boolean hasNext() {
        return this.zzb < this.zza.zza.length();
    }

    public final /* bridge */ /* synthetic */ Object next() {
        int i = this.zzb;
        if (i < this.zza.zza.length()) {
            this.zzb = i + 1;
            return new zzat(String.valueOf(i));
        }
        throw new NoSuchElementException();
    }
}
