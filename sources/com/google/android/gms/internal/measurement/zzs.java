package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzs extends zzai {
    final boolean zza;
    final boolean zzb;
    final /* synthetic */ zzt zzc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzs(zzt zzt, boolean z, boolean z2) {
        super("log");
        this.zzc = zzt;
        this.zza = z;
        this.zzb = z2;
    }

    public final zzap zza(zzg zzg, List list) {
        int i;
        zzh.zzi("log", 1, list);
        if (list.size() == 1) {
            this.zzc.zza.zza(3, zzg.zzb((zzap) list.get(0)).zzi(), Collections.emptyList(), this.zza, this.zzb);
            return zzap.zzf;
        }
        switch (zzh.zzb(zzg.zzb((zzap) list.get(0)).zzh().doubleValue())) {
            case 2:
                i = 4;
                break;
            case 3:
                i = 1;
                break;
            case 5:
                i = 5;
                break;
            case 6:
                i = 2;
                break;
            default:
                i = 3;
                break;
        }
        String zzi = zzg.zzb((zzap) list.get(1)).zzi();
        if (list.size() == 2) {
            this.zzc.zza.zza(i, zzi, Collections.emptyList(), this.zza, this.zzb);
            return zzap.zzf;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 2; i2 < Math.min(list.size(), 5); i2++) {
            arrayList.add(zzg.zzb((zzap) list.get(i2)).zzi());
        }
        this.zzc.zza.zza(i, zzi, arrayList, this.zza, this.zzb);
        return zzap.zzf;
    }
}
