package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzbi extends zzaw {
    protected zzbi() {
        this.zza.add(zzbl.ADD);
        this.zza.add(zzbl.DIVIDE);
        this.zza.add(zzbl.MODULUS);
        this.zza.add(zzbl.MULTIPLY);
        this.zza.add(zzbl.NEGATE);
        this.zza.add(zzbl.POST_DECREMENT);
        this.zza.add(zzbl.POST_INCREMENT);
        this.zza.add(zzbl.PRE_DECREMENT);
        this.zza.add(zzbl.PRE_INCREMENT);
        this.zza.add(zzbl.SUBTRACT);
    }

    public final zzap zza(String str, zzg zzg, List list) {
        zzbl zzbl = zzbl.ADD;
        switch (zzh.zze(str).ordinal()) {
            case 0:
                zzh.zzh(zzbl.name(), 2, list);
                zzap zzb = zzg.zzb((zzap) list.get(0));
                zzap zzb2 = zzg.zzb((zzap) list.get(1));
                if (!(zzb instanceof zzal) && !(zzb instanceof zzat) && !(zzb2 instanceof zzal) && !(zzb2 instanceof zzat)) {
                    return new zzah(Double.valueOf(zzb.zzh().doubleValue() + zzb2.zzh().doubleValue()));
                }
                return new zzat(String.valueOf(zzb.zzi()).concat(String.valueOf(zzb2.zzi())));
            case 21:
                zzh.zzh(zzbl.DIVIDE.name(), 2, list);
                return new zzah(Double.valueOf(zzg.zzb((zzap) list.get(0)).zzh().doubleValue() / zzg.zzb((zzap) list.get(1)).zzh().doubleValue()));
            case 44:
                zzh.zzh(zzbl.MODULUS.name(), 2, list);
                return new zzah(Double.valueOf(zzg.zzb((zzap) list.get(0)).zzh().doubleValue() % zzg.zzb((zzap) list.get(1)).zzh().doubleValue()));
            case 45:
                zzh.zzh(zzbl.MULTIPLY.name(), 2, list);
                return new zzah(Double.valueOf(zzg.zzb((zzap) list.get(0)).zzh().doubleValue() * zzg.zzb((zzap) list.get(1)).zzh().doubleValue()));
            case 46:
                zzh.zzh(zzbl.NEGATE.name(), 1, list);
                return new zzah(Double.valueOf(-zzg.zzb((zzap) list.get(0)).zzh().doubleValue()));
            case 52:
            case 53:
                zzh.zzh(str, 2, list);
                zzap zzb3 = zzg.zzb((zzap) list.get(0));
                zzg.zzb((zzap) list.get(1));
                return zzb3;
            case 55:
            case 56:
                zzh.zzh(str, 1, list);
                return zzg.zzb((zzap) list.get(0));
            case 59:
                zzh.zzh(zzbl.SUBTRACT.name(), 2, list);
                return new zzah(Double.valueOf(zzg.zzb((zzap) list.get(0)).zzh().doubleValue() + new zzah(Double.valueOf(-zzg.zzb((zzap) list.get(1)).zzh().doubleValue())).zzh().doubleValue()));
            default:
                return super.zzb(str);
        }
    }
}
