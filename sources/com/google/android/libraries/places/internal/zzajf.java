package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzajf implements zzajg {
    private static final Object zza = new Object();
    private volatile zzajg zzb;
    private volatile Object zzc = zza;

    private zzajf(zzajg zzajg) {
        this.zzb = zzajg;
    }

    public static zzajg zza(zzajg zzajg) {
        return new zzajf(zzajg);
    }

    public final Object zzb() {
        Object obj = this.zzc;
        if (obj != zza) {
            return obj;
        }
        if (this.zzb == null) {
            return this.zzc;
        }
        zzcq zzcq = new zzcq();
        this.zzc = zzcq;
        this.zzb = null;
        return zzcq;
    }
}
