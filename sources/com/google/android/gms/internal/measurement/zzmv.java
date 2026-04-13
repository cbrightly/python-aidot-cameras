package com.google.android.gms.internal.measurement;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmv {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zznk zzc = zzX(false);
    private static final zznk zzd = zzX(true);
    private static final zznk zze = new zznm();

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            cls = null;
        }
        zzb = cls;
    }

    public static zznk zzA() {
        return zze;
    }

    static Object zzB(Object obj, int i, int i2, Object obj2, zznk zznk) {
        if (obj2 == null) {
            obj2 = zznk.zzc(obj);
        }
        zznk.zzf(obj2, i, (long) i2);
        return obj2;
    }

    static void zzC(zznk zznk, Object obj, Object obj2) {
        zznk.zzh(obj, zznk.zze(zznk.zzd(obj), zznk.zzd(obj2)));
    }

    public static void zzD(Class cls) {
        Class cls2;
        if (!zzlb.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzE(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzc(i, list, z);
        }
    }

    public static void zzF(int i, List list, zzoc zzoc) {
        if (list != null && !list.isEmpty()) {
            zzoc.zze(i, list);
        }
    }

    public static void zzG(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzg(i, list, z);
        }
    }

    public static void zzH(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzj(i, list, z);
        }
    }

    public static void zzI(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzl(i, list, z);
        }
    }

    public static void zzJ(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzn(i, list, z);
        }
    }

    public static void zzK(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzp(i, list, z);
        }
    }

    public static void zzL(int i, List list, zzoc zzoc, zzmt zzmt) {
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                ((zzkj) zzoc).zzq(i, list.get(i2), zzmt);
            }
        }
    }

    public static void zzM(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzs(i, list, z);
        }
    }

    public static void zzN(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzu(i, list, z);
        }
    }

    public static void zzO(int i, List list, zzoc zzoc, zzmt zzmt) {
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                ((zzkj) zzoc).zzv(i, list.get(i2), zzmt);
            }
        }
    }

    public static void zzP(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzx(i, list, z);
        }
    }

    public static void zzQ(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzz(i, list, z);
        }
    }

    public static void zzR(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzB(i, list, z);
        }
    }

    public static void zzS(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzD(i, list, z);
        }
    }

    public static void zzT(int i, List list, zzoc zzoc) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzG(i, list);
        }
    }

    public static void zzU(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzI(i, list, z);
        }
    }

    public static void zzV(int i, List list, zzoc zzoc, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzoc.zzK(i, list, z);
        }
    }

    static boolean zzW(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private static zznk zzX(boolean z) {
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
            return (zznk) cls.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th2) {
            return null;
        }
    }

    static int zza(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzki.zzx(i << 3) + 1);
    }

    static int zzb(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzx = size * zzki.zzx(i << 3);
        for (int i2 = 0; i2 < list.size(); i2++) {
            int zzd2 = ((zzka) list.get(i2)).zzd();
            zzx += zzki.zzx(zzd2) + zzd2;
        }
        return zzx;
    }

    static int zzc(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzki.zzx(i << 3));
    }

    static int zzd(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlc) {
            zzlc zzlc = (zzlc) list;
            i = 0;
            while (i2 < size) {
                i += zzki.zzu(zzlc.zze(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzki.zzu(((Integer) list.get(i2)).intValue());
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
        return size * (zzki.zzx(i << 3) + 4);
    }

    static int zzf(List list) {
        return list.size() * 4;
    }

    static int zzg(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzki.zzx(i << 3) + 8);
    }

    static int zzh(List list) {
        return list.size() * 8;
    }

    static int zzi(int i, List list, zzmt zzmt) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzki.zzt(i, (zzmi) list.get(i3), zzmt);
        }
        return i2;
    }

    static int zzj(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzk(list) + (size * zzki.zzx(i << 3));
    }

    static int zzk(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlc) {
            zzlc zzlc = (zzlc) list;
            i = 0;
            while (i2 < size) {
                i += zzki.zzu(zzlc.zze(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzki.zzu(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzl(int i, List list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzm(list) + (list.size() * zzki.zzx(i << 3));
    }

    static int zzm(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlx = (zzlx) list;
            i = 0;
            while (i2 < size) {
                i += zzki.zzy(zzlx.zza(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzki.zzy(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzn(int i, Object obj, zzmt zzmt) {
        if (obj instanceof zzlo) {
            int i2 = zzki.zzb;
            int zza2 = ((zzlo) obj).zza();
            return zzki.zzx(i << 3) + zzki.zzx(zza2) + zza2;
        }
        return zzki.zzx(i << 3) + zzki.zzv((zzmi) obj, zzmt);
    }

    static int zzo(int i, List list, zzmt zzmt) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzx = zzki.zzx(i << 3) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzlo) {
                int zza2 = ((zzlo) obj).zza();
                zzx += zzki.zzx(zza2) + zza2;
            } else {
                zzx += zzki.zzv((zzmi) obj, zzmt);
            }
        }
        return zzx;
    }

    static int zzp(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzq(list) + (size * zzki.zzx(i << 3));
    }

    static int zzq(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlc) {
            zzlc zzlc = (zzlc) list;
            i = 0;
            while (i2 < size) {
                int zze2 = zzlc.zze(i2);
                i += zzki.zzx((zze2 >> 31) ^ (zze2 + zze2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                int intValue = ((Integer) list.get(i2)).intValue();
                i3 = i + zzki.zzx((intValue >> 31) ^ (intValue + intValue));
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
        return zzs(list) + (size * zzki.zzx(i << 3));
    }

    static int zzs(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlx = (zzlx) list;
            i = 0;
            while (i2 < size) {
                long zza2 = zzlx.zza(i2);
                i += zzki.zzy((zza2 >> 63) ^ (zza2 + zza2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                long longValue = ((Long) list.get(i2)).longValue();
                i3 = i + zzki.zzy((longValue >> 63) ^ (longValue + longValue));
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
        int i3 = zzki.zzb;
        boolean z = list instanceof zzlq;
        int zzx = zzki.zzx(i << 3) * size;
        if (z) {
            zzlq zzlq = (zzlq) list;
            while (i2 < size) {
                Object zzf = zzlq.zzf(i2);
                if (zzf instanceof zzka) {
                    int zzd2 = ((zzka) zzf).zzd();
                    zzx += zzki.zzx(zzd2) + zzd2;
                } else {
                    zzx += zzki.zzw((String) zzf);
                }
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzka) {
                    int zzd3 = ((zzka) obj).zzd();
                    zzw = zzx + zzki.zzx(zzd3) + zzd3;
                } else {
                    zzw = zzx + zzki.zzw((String) obj);
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
        return zzv(list) + (size * zzki.zzx(i << 3));
    }

    static int zzv(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlc) {
            zzlc zzlc = (zzlc) list;
            i = 0;
            while (i2 < size) {
                i += zzki.zzx(zzlc.zze(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzki.zzx(((Integer) list.get(i2)).intValue());
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
        return zzx(list) + (size * zzki.zzx(i << 3));
    }

    static int zzx(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlx = (zzlx) list;
            i = 0;
            while (i2 < size) {
                i += zzki.zzy(zzlx.zza(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzki.zzy(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static zznk zzy() {
        return zzc;
    }

    public static zznk zzz() {
        return zzd;
    }
}
