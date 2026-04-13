package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzii implements zzih {
    private final zzgr zza;
    private final zzgv zzb;

    public zzii(zzgv zzgv, zzgr zzgr) {
        this.zzb = zzgv;
        this.zza = zzgr;
    }

    public final void zza(zzig zzig) {
        zzzy zza2 = zzaab.zza();
        zza2.zzg(zzig.zzz());
        zza2.zzd(zzig.zzx());
        zza2.zze(zzig.zzy());
        zza2.zzj(zzig.zzd());
        zza2.zzc(zzig.zzb());
        zza2.zzb(zzig.zza());
        zza2.zzk(zzig.zze());
        zza2.zzh(zzig.zzk().length());
        zza2.zzl(zzig.zzg());
        zza2.zzf(zzig.zzc());
        zza2.zzi(zzig.zzA());
        zza2.zza(zzig.zzf());
        if (zzig.zzi() == zzhh.FRAGMENT) {
            zza2.zzn(2);
        } else if (zzig.zzi() == zzhh.INTENT) {
            zza2.zzn(3);
        } else {
            zza2.zzn(1);
        }
        if (zzig.zzj() == AutocompleteActivityMode.FULLSCREEN) {
            zza2.zzm(2);
        } else if (zzig.zzj() == AutocompleteActivityMode.OVERLAY) {
            zza2.zzm(1);
        }
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(10);
        zzb2.zzc((zzaab) zza2.zzq());
        this.zzb.zza(zzgw.zza((zzaam) zzb2.zzq()));
    }
}
