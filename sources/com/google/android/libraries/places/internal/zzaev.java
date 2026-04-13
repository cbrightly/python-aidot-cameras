package com.google.android.libraries.places.internal;

import java.util.NoSuchElementException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaev extends zzaex {
    final /* synthetic */ zzafe zza;
    private int zzb = 0;
    private final int zzc;

    zzaev(zzafe zzafe) {
        this.zza = zzafe;
        this.zzc = zzafe.zzd();
    }

    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    public final byte zza() {
        int i = this.zzb;
        if (i < this.zzc) {
            this.zzb = i + 1;
            return this.zza.zzb(i);
        }
        throw new NoSuchElementException();
    }
}
