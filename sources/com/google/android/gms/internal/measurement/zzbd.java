package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzbd implements zzbf {
    private final zzg zza;
    private final String zzb;

    public zzbd(zzg zzg, String str) {
        this.zza = zzg;
        this.zzb = str;
    }

    public final zzg zza(zzap zzap) {
        zzg zza2 = this.zza.zza();
        zza2.zzf(this.zzb, zzap);
        return zza2;
    }
}
