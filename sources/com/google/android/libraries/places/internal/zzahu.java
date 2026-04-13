package com.google.android.libraries.places.internal;

import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahu {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zzaij zzc = zzW(false);
    private static final zzaij zzd = zzW(true);
    private static final zzaij zze = new zzail();

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            cls = null;
        }
        zzb = cls;
    }

    public static zzaij zzA() {
        return zze;
    }

    static void zzB(zzaij zzaij, Object obj, Object obj2) {
        zzaij.zzf(obj, zzaij.zzd(zzaij.zzc(obj), zzaij.zzc(obj2)));
    }

    public static void zzC(Class cls) {
        Class cls2;
        if (!zzafz.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzD(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzc(i, list, z);
        }
    }

    public static void zzE(int i, List list, zzaja zzaja) {
        if (list != null && !list.isEmpty()) {
            zzaja.zze(i, list);
        }
    }

    public static void zzF(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzg(i, list, z);
        }
    }

    public static void zzG(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzi(i, list, z);
        }
    }

    public static void zzH(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzk(i, list, z);
        }
    }

    public static void zzI(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzm(i, list, z);
        }
    }

    public static void zzJ(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzo(i, list, z);
        }
    }

    public static void zzK(int i, List list, zzaja zzaja, zzahs zzahs) {
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                ((zzafn) zzaja).zzp(i, list.get(i2), zzahs);
            }
        }
    }

    public static void zzL(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzr(i, list, z);
        }
    }

    public static void zzM(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzt(i, list, z);
        }
    }

    public static void zzN(int i, List list, zzaja zzaja, zzahs zzahs) {
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                ((zzafn) zzaja).zzu(i, list.get(i2), zzahs);
            }
        }
    }

    public static void zzO(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzw(i, list, z);
        }
    }

    public static void zzP(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzy(i, list, z);
        }
    }

    public static void zzQ(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzA(i, list, z);
        }
    }

    public static void zzR(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzC(i, list, z);
        }
    }

    public static void zzS(int i, List list, zzaja zzaja) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzE(i, list);
        }
    }

    public static void zzT(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzG(i, list, z);
        }
    }

    public static void zzU(int i, List list, zzaja zzaja, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzaja.zzI(i, list, z);
        }
    }

    static boolean zzV(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private static zzaij zzW(boolean z) {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        try {
            return (zzaij) cls.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th2) {
            return null;
        }
    }

    static int zza(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzafm.zzx(i << 3) + 1);
    }

    static int zzb(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzx = size * zzafm.zzx(i << 3);
        for (int i2 = 0; i2 < list.size(); i2++) {
            int zzd2 = ((zzafe) list.get(i2)).zzd();
            zzx += zzafm.zzx(zzd2) + zzd2;
        }
        return zzx;
    }

    static int zzc(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzd(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaga) {
            zzaga zzaga = (zzaga) list;
            i = 0;
            while (i2 < size) {
                i += zzafm.zzu(zzaga.zzd(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzafm.zzu(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zze(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzafm.zzx(i << 3) + 4);
    }

    static int zzf(List list) {
        return list.size() * 4;
    }

    static int zzg(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzafm.zzx(i << 3) + 8);
    }

    static int zzh(List list) {
        return list.size() * 8;
    }

    static int zzi(int i, List list, zzahs zzahs) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzafm.zzt(i, (zzahh) list.get(i3), zzahs);
        }
        return i2;
    }

    static int zzj(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzk(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzk(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaga) {
            zzaga zzaga = (zzaga) list;
            i = 0;
            while (i2 < size) {
                i += zzafm.zzu(zzaga.zzd(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzafm.zzu(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzl(int i, List list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzm(list) + (list.size() * zzafm.zzx(i << 3));
    }

    static int zzm(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzagw) {
            zzagw zzagw = (zzagw) list;
            i = 0;
            while (i2 < size) {
                i += zzafm.zzy(zzagw.zzd(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzafm.zzy(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzn(int i, Object obj, zzahs zzahs) {
        if (obj instanceof zzagn) {
            int i2 = zzafm.zzb;
            int zza2 = ((zzagn) obj).zza();
            return zzafm.zzx(i << 3) + zzafm.zzx(zza2) + zza2;
        }
        return zzafm.zzx(i << 3) + zzafm.zzv((zzahh) obj, zzahs);
    }

    static int zzo(int i, List list, zzahs zzahs) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzx = zzafm.zzx(i << 3) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzagn) {
                int zza2 = ((zzagn) obj).zza();
                zzx += zzafm.zzx(zza2) + zza2;
            } else {
                zzx += zzafm.zzv((zzahh) obj, zzahs);
            }
        }
        return zzx;
    }

    static int zzp(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzq(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzq(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaga) {
            zzaga zzaga = (zzaga) list;
            i = 0;
            while (i2 < size) {
                int zzd2 = zzaga.zzd(i2);
                i += zzafm.zzx((zzd2 >> 31) ^ (zzd2 + zzd2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                int intValue = ((Integer) list.get(i2)).intValue();
                i3 = i + zzafm.zzx((intValue >> 31) ^ (intValue + intValue));
                i2++;
            }
        }
        return i;
    }

    static int zzr(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzs(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzs(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzagw) {
            zzagw zzagw = (zzagw) list;
            i = 0;
            while (i2 < size) {
                long zzd2 = zzagw.zzd(i2);
                i += zzafm.zzy((zzd2 >> 63) ^ (zzd2 + zzd2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                long longValue = ((Long) list.get(i2)).longValue();
                i3 = i + zzafm.zzy((longValue >> 63) ^ (longValue + longValue));
                i2++;
            }
        }
        return i;
    }

    static int zzt(int i, List list) {
        int zzw;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int i3 = zzafm.zzb;
        boolean z = list instanceof zzagp;
        int zzx = zzafm.zzx(i << 3) * size;
        if (z) {
            zzagp zzagp = (zzagp) list;
            while (i2 < size) {
                Object zze2 = zzagp.zze(i2);
                if (zze2 instanceof zzafe) {
                    int zzd2 = ((zzafe) zze2).zzd();
                    zzx += zzafm.zzx(zzd2) + zzd2;
                } else {
                    zzx += zzafm.zzw((String) zze2);
                }
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzafe) {
                    int zzd3 = ((zzafe) obj).zzd();
                    zzw = zzx + zzafm.zzx(zzd3) + zzd3;
                } else {
                    zzw = zzx + zzafm.zzw((String) obj);
                }
                i2++;
            }
        }
        return zzx;
    }

    static int zzu(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzv(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzv(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzaga) {
            zzaga zzaga = (zzaga) list;
            i = 0;
            while (i2 < size) {
                i += zzafm.zzx(zzaga.zzd(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzafm.zzx(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzw(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzx(list) + (size * zzafm.zzx(i << 3));
    }

    static int zzx(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzagw) {
            zzagw zzagw = (zzagw) list;
            i = 0;
            while (i2 < size) {
                i += zzafm.zzy(zzagw.zzd(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzafm.zzy(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static zzaij zzy() {
        return zzc;
    }

    public static zzaij zzz() {
        return zzd;
    }
}
