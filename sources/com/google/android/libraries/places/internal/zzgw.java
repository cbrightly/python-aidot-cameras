package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgw {
    public static zznf zza(zzaam zzaam) {
        zznd zza = zznf.zza();
        zza.zzb(1);
        zza.zza(zzaam);
        return (zznf) zza.zzq();
    }

    public static zzaah zzb(zzgr zzgr) {
        int i;
        switch (zzgr.zzc() - 1) {
            case 1:
                i = 4;
                break;
            default:
                i = 2;
                break;
        }
        zzaah zza = zzaam.zza();
        zznh zza2 = zznm.zza();
        zza2.zza(zzgr.zzb());
        zza2.zzb(zzgr.zza());
        zza.zzb((zznm) zza2.zzq());
        zza.zzf(true);
        zza.zzk(i);
        zza.zzi("3.1.0");
        return zza;
    }
}
