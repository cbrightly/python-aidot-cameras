package com.google.android.gms.internal.measurement;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzbk extends zzaw {
    protected zzbk() {
        this.zza.add(zzbl.ASSIGN);
        this.zza.add(zzbl.CONST);
        this.zza.add(zzbl.CREATE_ARRAY);
        this.zza.add(zzbl.CREATE_OBJECT);
        this.zza.add(zzbl.EXPRESSION_LIST);
        this.zza.add(zzbl.GET);
        this.zza.add(zzbl.GET_INDEX);
        this.zza.add(zzbl.GET_PROPERTY);
        this.zza.add(zzbl.NULL);
        this.zza.add(zzbl.SET_PROPERTY);
        this.zza.add(zzbl.TYPEOF);
        this.zza.add(zzbl.UNDEFINED);
        this.zza.add(zzbl.VAR);
    }

    public final zzap zza(String str, zzg zzg, List list) {
        String str2;
        zzbl zzbl = zzbl.ADD;
        int i = 0;
        switch (zzh.zze(str).ordinal()) {
            case 3:
                zzh.zzh(zzbl.ASSIGN.name(), 2, list);
                zzap zzb = zzg.zzb((zzap) list.get(0));
                if (!(zzb instanceof zzat)) {
                    throw new IllegalArgumentException(String.format("Expected string for assign var. got %s", new Object[]{zzb.getClass().getCanonicalName()}));
                } else if (zzg.zzh(zzb.zzi())) {
                    zzap zzb2 = zzg.zzb((zzap) list.get(1));
                    zzg.zzg(zzb.zzi(), zzb2);
                    return zzb2;
                } else {
                    throw new IllegalArgumentException(String.format("Attempting to assign undefined value %s", new Object[]{zzb.zzi()}));
                }
            case 14:
                zzh.zzi(zzbl.CONST.name(), 2, list);
                if (list.size() % 2 == 0) {
                    int i2 = 0;
                    while (i2 < list.size() - 1) {
                        zzap zzb3 = zzg.zzb((zzap) list.get(i2));
                        if (zzb3 instanceof zzat) {
                            zzg.zzf(zzb3.zzi(), zzg.zzb((zzap) list.get(i2 + 1)));
                            i2 += 2;
                        } else {
                            throw new IllegalArgumentException(String.format("Expected string for const name. got %s", new Object[]{zzb3.getClass().getCanonicalName()}));
                        }
                    }
                    return zzap.zzf;
                }
                throw new IllegalArgumentException(String.format("CONST requires an even number of arguments, found %s", new Object[]{Integer.valueOf(list.size())}));
            case 17:
                if (list.isEmpty()) {
                    return new zzae();
                }
                zzae zzae = new zzae();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    zzap zzb4 = zzg.zzb((zzap) it.next());
                    if (!(zzb4 instanceof zzag)) {
                        zzae.zzq(i, zzb4);
                        i++;
                    } else {
                        throw new IllegalStateException("Failed to evaluate array element");
                    }
                }
                return zzae;
            case 18:
                if (list.isEmpty()) {
                    return new zzam();
                }
                if (list.size() % 2 == 0) {
                    zzam zzam = new zzam();
                    while (i < list.size() - 1) {
                        zzap zzb5 = zzg.zzb((zzap) list.get(i));
                        zzap zzb6 = zzg.zzb((zzap) list.get(i + 1));
                        if ((zzb5 instanceof zzag) || (zzb6 instanceof zzag)) {
                            throw new IllegalStateException("Failed to evaluate map entry");
                        }
                        zzam.zzr(zzb5.zzi(), zzb6);
                        i += 2;
                    }
                    return zzam;
                }
                throw new IllegalArgumentException(String.format("CREATE_OBJECT requires an even number of arguments, found %s", new Object[]{Integer.valueOf(list.size())}));
            case 24:
                zzh.zzi(zzbl.EXPRESSION_LIST.name(), 1, list);
                zzap zzap = zzap.zzf;
                while (i < list.size()) {
                    zzap = zzg.zzb((zzap) list.get(i));
                    if (!(zzap instanceof zzag)) {
                        i++;
                    } else {
                        throw new IllegalStateException("ControlValue cannot be in an expression list");
                    }
                }
                return zzap;
            case 33:
                zzh.zzh(zzbl.GET.name(), 1, list);
                zzap zzb7 = zzg.zzb((zzap) list.get(0));
                if (zzb7 instanceof zzat) {
                    return zzg.zzd(zzb7.zzi());
                }
                throw new IllegalArgumentException(String.format("Expected string for get var. got %s", new Object[]{zzb7.getClass().getCanonicalName()}));
            case 35:
            case 36:
                zzh.zzh(zzbl.GET_PROPERTY.name(), 2, list);
                zzap zzb8 = zzg.zzb((zzap) list.get(0));
                zzap zzb9 = zzg.zzb((zzap) list.get(1));
                if ((zzb8 instanceof zzae) && zzh.zzk(zzb9)) {
                    return ((zzae) zzb8).zze(zzb9.zzh().intValue());
                }
                if (zzb8 instanceof zzal) {
                    return ((zzal) zzb8).zzf(zzb9.zzi());
                }
                if (zzb8 instanceof zzat) {
                    if ("length".equals(zzb9.zzi())) {
                        return new zzah(Double.valueOf((double) zzb8.zzi().length()));
                    }
                    if (zzh.zzk(zzb9) && zzb9.zzh().doubleValue() < ((double) zzb8.zzi().length())) {
                        return new zzat(String.valueOf(zzb8.zzi().charAt(zzb9.zzh().intValue())));
                    }
                }
                return zzap.zzf;
            case 49:
                zzh.zzh(zzbl.NULL.name(), 0, list);
                return zzap.zzg;
            case 58:
                zzh.zzh(zzbl.SET_PROPERTY.name(), 3, list);
                zzap zzb10 = zzg.zzb((zzap) list.get(0));
                zzap zzb11 = zzg.zzb((zzap) list.get(1));
                zzap zzb12 = zzg.zzb((zzap) list.get(2));
                if (zzb10 == zzap.zzf || zzb10 == zzap.zzg) {
                    throw new IllegalStateException(String.format("Can't set property %s of %s", new Object[]{zzb11.zzi(), zzb10.zzi()}));
                }
                if ((zzb10 instanceof zzae) && (zzb11 instanceof zzah)) {
                    ((zzae) zzb10).zzq(zzb11.zzh().intValue(), zzb12);
                } else if (zzb10 instanceof zzal) {
                    ((zzal) zzb10).zzr(zzb11.zzi(), zzb12);
                }
                return zzb12;
            case 62:
                zzh.zzh(zzbl.TYPEOF.name(), 1, list);
                zzap zzb13 = zzg.zzb((zzap) list.get(0));
                if (zzb13 instanceof zzau) {
                    str2 = "undefined";
                } else if (zzb13 instanceof zzaf) {
                    str2 = "boolean";
                } else if (zzb13 instanceof zzah) {
                    str2 = "number";
                } else if (zzb13 instanceof zzat) {
                    str2 = TypedValues.Custom.S_STRING;
                } else if (zzb13 instanceof zzao) {
                    str2 = "function";
                } else if ((zzb13 instanceof zzaq) || (zzb13 instanceof zzag)) {
                    throw new IllegalArgumentException(String.format("Unsupported value type %s in typeof", new Object[]{zzb13}));
                } else {
                    str2 = "object";
                }
                return new zzat(str2);
            case 63:
                zzh.zzh(zzbl.UNDEFINED.name(), 0, list);
                return zzap.zzf;
            case 64:
                zzh.zzi(zzbl.VAR.name(), 1, list);
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    zzap zzb14 = zzg.zzb((zzap) it2.next());
                    if (zzb14 instanceof zzat) {
                        zzg.zze(zzb14.zzi(), zzap.zzf);
                    } else {
                        throw new IllegalArgumentException(String.format("Expected string for var name. got %s", new Object[]{zzb14.getClass().getCanonicalName()}));
                    }
                }
                return zzap.zzf;
            default:
                return super.zzb(str);
        }
    }
}
