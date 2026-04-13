package com.google.android.gms.internal.measurement;

import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzjr extends zzjt {
    final /* synthetic */ zzka zza;
    private int zzb = 0;
    private final int zzc;

    zzjr(zzka zzka) {
        this.zza = zzka;
        this.zzc = zzka.zzd();
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
