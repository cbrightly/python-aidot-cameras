package com.google.android.gms.internal.measurement;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import net.sqlcipher.database.SQLiteDatabase;
import sun.misc.Unsafe;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzml<T> implements zzmt<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zznu.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzmi zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzlw zzm;
    private final zznk zzn;
    private final zzko zzo;
    private final zzmn zzp;
    private final zzmd zzq;

    private zzml(int[] iArr, Object[] objArr, int i, int i2, zzmi zzmi, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzmn zzmn, zzlw zzlw, zznk zznk, zzko zzko, zzmd zzmd) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = z;
        boolean z3 = false;
        if (zzko != null && zzko.zzc(zzmi)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzj = iArr2;
        this.zzk = i3;
        this.zzl = i4;
        this.zzp = zzmn;
        this.zzm = zzlw;
        this.zzn = zznk;
        this.zzo = zzko;
        this.zzg = zzmi;
        this.zzq = zzmd;
    }

    private final zzlf zzA(int i) {
        int i2 = i / 3;
        return (zzlf) this.zzd[i2 + i2 + 1];
    }

    private final zzmt zzB(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzmt zzmt = (zzmt) this.zzd[i3];
        if (zzmt != null) {
            return zzmt;
        }
        zzmt zzb2 = zzmq.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzb2;
        return zzb2;
    }

    private final Object zzC(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final Object zzD(Object obj, int i) {
        zzmt zzB = zzB(i);
        int zzy = zzy(i) & 1048575;
        if (!zzP(obj, i)) {
            return zzB.zze();
        }
        Object object = zzb.getObject(obj, (long) zzy);
        if (zzS(object)) {
            return object;
        }
        Object zze2 = zzB.zze();
        if (object != null) {
            zzB.zzg(zze2, object);
        }
        return zze2;
    }

    private final Object zzE(Object obj, int i, int i2) {
        zzmt zzB = zzB(i2);
        if (!zzT(obj, i, i2)) {
            return zzB.zze();
        }
        Object object = zzb.getObject(obj, (long) (zzy(i2) & 1048575));
        if (zzS(object)) {
            return object;
        }
        Object zze2 = zzB.zze();
        if (object != null) {
            zzB.zzg(zze2, object);
        }
        return zze2;
    }

    private static Field zzF(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private static void zzG(Object obj) {
        if (!zzS(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        }
    }

    private final void zzH(Object obj, Object obj2, int i) {
        if (zzP(obj2, i)) {
            Unsafe unsafe = zzb;
            long zzy = (long) (zzy(i) & 1048575);
            Object object = unsafe.getObject(obj2, zzy);
            if (object != null) {
                zzmt zzB = zzB(i);
                if (!zzP(obj, i)) {
                    if (!zzS(object)) {
                        unsafe.putObject(obj, zzy, object);
                    } else {
                        Object zze2 = zzB.zze();
                        zzB.zzg(zze2, object);
                        unsafe.putObject(obj, zzy, zze2);
                    }
                    zzJ(obj, i);
                    return;
                }
                Object object2 = unsafe.getObject(obj, zzy);
                if (!zzS(object2)) {
                    Object zze3 = zzB.zze();
                    zzB.zzg(zze3, object2);
                    unsafe.putObject(obj, zzy, zze3);
                    object2 = zze3;
                }
                zzB.zzg(object2, object);
                return;
            }
            int i2 = this.zzc[i];
            String obj3 = obj2.toString();
            throw new IllegalStateException("Source subfield " + i2 + " is present but null: " + obj3);
        }
    }

    private final void zzI(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzT(obj2, i2, i)) {
            Unsafe unsafe = zzb;
            long zzy = (long) (zzy(i) & 1048575);
            Object object = unsafe.getObject(obj2, zzy);
            if (object != null) {
                zzmt zzB = zzB(i);
                if (!zzT(obj, i2, i)) {
                    if (!zzS(object)) {
                        unsafe.putObject(obj, zzy, object);
                    } else {
                        Object zze2 = zzB.zze();
                        zzB.zzg(zze2, object);
                        unsafe.putObject(obj, zzy, zze2);
                    }
                    zzK(obj, i2, i);
                    return;
                }
                Object object2 = unsafe.getObject(obj, zzy);
                if (!zzS(object2)) {
                    Object zze3 = zzB.zze();
                    zzB.zzg(zze3, object2);
                    unsafe.putObject(obj, zzy, zze3);
                    object2 = zze3;
                }
                zzB.zzg(object2, object);
                return;
            }
            int i3 = this.zzc[i];
            String obj3 = obj2.toString();
            throw new IllegalStateException("Source subfield " + i3 + " is present but null: " + obj3);
        }
    }

    private final void zzJ(Object obj, int i) {
        int zzv = zzv(i);
        long j = (long) (1048575 & zzv);
        if (j != 1048575) {
            zznu.zzq(obj, j, (1 << (zzv >>> 20)) | zznu.zzc(obj, j));
        }
    }

    private final void zzK(Object obj, int i, int i2) {
        zznu.zzq(obj, (long) (zzv(i2) & 1048575), i);
    }

    private final void zzL(Object obj, int i, Object obj2) {
        zzb.putObject(obj, (long) (zzy(i) & 1048575), obj2);
        zzJ(obj, i);
    }

    private final void zzM(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, (long) (zzy(i2) & 1048575), obj2);
        zzK(obj, i, i2);
    }

    private final void zzN(zzoc zzoc, int i, Object obj, int i2) {
        if (obj != null) {
            zzmb zzmb = (zzmb) zzC(i2);
            throw null;
        }
    }

    private final boolean zzO(Object obj, Object obj2, int i) {
        return zzP(obj, i) == zzP(obj2, i);
    }

    private final boolean zzP(Object obj, int i) {
        int zzv = zzv(i);
        long j = (long) (zzv & 1048575);
        if (j == 1048575) {
            int zzy = zzy(i);
            long j2 = (long) (zzy & 1048575);
            switch (zzx(zzy)) {
                case 0:
                    return Double.doubleToRawLongBits(zznu.zza(obj, j2)) != 0;
                case 1:
                    return Float.floatToRawIntBits(zznu.zzb(obj, j2)) != 0;
                case 2:
                    return zznu.zzd(obj, j2) != 0;
                case 3:
                    return zznu.zzd(obj, j2) != 0;
                case 4:
                    return zznu.zzc(obj, j2) != 0;
                case 5:
                    return zznu.zzd(obj, j2) != 0;
                case 6:
                    return zznu.zzc(obj, j2) != 0;
                case 7:
                    return zznu.zzw(obj, j2);
                case 8:
                    Object zzf2 = zznu.zzf(obj, j2);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzka) {
                        return !zzka.zzb.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zznu.zzf(obj, j2) != null;
                case 10:
                    return !zzka.zzb.equals(zznu.zzf(obj, j2));
                case 11:
                    return zznu.zzc(obj, j2) != 0;
                case 12:
                    return zznu.zzc(obj, j2) != 0;
                case 13:
                    return zznu.zzc(obj, j2) != 0;
                case 14:
                    return zznu.zzd(obj, j2) != 0;
                case 15:
                    return zznu.zzc(obj, j2) != 0;
                case 16:
                    return zznu.zzd(obj, j2) != 0;
                case 17:
                    return zznu.zzf(obj, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return (zznu.zzc(obj, j) & (1 << (zzv >>> 20))) != 0;
        }
    }

    private final boolean zzQ(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzP(obj, i);
        }
        return (i3 & i4) != 0;
    }

    private static boolean zzR(Object obj, int i, zzmt zzmt) {
        return zzmt.zzk(zznu.zzf(obj, (long) (i & 1048575)));
    }

    private static boolean zzS(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzlb) {
            return ((zzlb) obj).zzbR();
        }
        return true;
    }

    private final boolean zzT(Object obj, int i, int i2) {
        return zznu.zzc(obj, (long) (zzv(i2) & 1048575)) == i;
    }

    private static boolean zzU(Object obj, long j) {
        return ((Boolean) zznu.zzf(obj, j)).booleanValue();
    }

    private static final void zzV(int i, Object obj, zzoc zzoc) {
        if (obj instanceof String) {
            zzoc.zzF(i, (String) obj);
        } else {
            zzoc.zzd(i, (zzka) obj);
        }
    }

    static zznl zzd(Object obj) {
        zzlb zzlb = (zzlb) obj;
        zznl zznl = zzlb.zzc;
        if (zznl != zznl.zzc()) {
            return zznl;
        }
        zznl zzf2 = zznl.zzf();
        zzlb.zzc = zzf2;
        return zzf2;
    }

    static zzml zzl(Class cls, zzmf zzmf, zzmn zzmn, zzlw zzlw, zznk zznk, zzko zzko, zzmd zzmd) {
        int i;
        char c;
        int[] iArr;
        char c2;
        char c3;
        char c4;
        int i2;
        char c5;
        int i3;
        int i4;
        char c6;
        int i5;
        Object[] objArr;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        Field field;
        char charAt;
        int i11;
        Field field2;
        Field field3;
        int i12;
        char charAt2;
        int i13;
        char charAt3;
        int i14;
        char charAt4;
        int i15;
        char charAt5;
        int i16;
        char charAt6;
        int i17;
        char charAt7;
        int i18;
        char charAt8;
        int i19;
        char charAt9;
        int i20;
        char charAt10;
        int i21;
        char charAt11;
        int i22;
        char charAt12;
        int i23;
        char charAt13;
        zzmf zzmf2 = zzmf;
        if (zzmf2 instanceof zzms) {
            zzms zzms = (zzms) zzmf2;
            int zzc2 = zzms.zzc();
            String zzd2 = zzms.zzd();
            int length = zzd2.length();
            char c7 = 0;
            int i24 = 55296;
            if (zzd2.charAt(0) >= 55296) {
                int i25 = 1;
                while (true) {
                    i = i25 + 1;
                    if (zzd2.charAt(i25) < 55296) {
                        break;
                    }
                    i25 = i;
                }
            } else {
                i = 1;
            }
            int i26 = i + 1;
            char charAt14 = zzd2.charAt(i);
            if (charAt14 >= 55296) {
                char c8 = charAt14 & 8191;
                int i27 = 13;
                while (true) {
                    i23 = i26 + 1;
                    charAt13 = zzd2.charAt(i26);
                    if (charAt13 < 55296) {
                        break;
                    }
                    c8 |= (charAt13 & 8191) << i27;
                    i27 += 13;
                    i26 = i23;
                }
                charAt14 = c8 | (charAt13 << i27);
                i26 = i23;
            }
            if (charAt14 == 0) {
                i2 = 0;
                c4 = 0;
                c3 = 0;
                c2 = 0;
                c = 0;
                iArr = zza;
                c5 = 0;
            } else {
                int i28 = i26 + 1;
                char charAt15 = zzd2.charAt(i26);
                if (charAt15 >= 55296) {
                    char c9 = charAt15 & 8191;
                    int i29 = 13;
                    while (true) {
                        i22 = i28 + 1;
                        charAt12 = zzd2.charAt(i28);
                        if (charAt12 < 55296) {
                            break;
                        }
                        c9 |= (charAt12 & 8191) << i29;
                        i29 += 13;
                        i28 = i22;
                    }
                    charAt15 = c9 | (charAt12 << i29);
                    i28 = i22;
                }
                int i30 = i28 + 1;
                char charAt16 = zzd2.charAt(i28);
                if (charAt16 >= 55296) {
                    char c10 = charAt16 & 8191;
                    int i31 = 13;
                    while (true) {
                        i21 = i30 + 1;
                        charAt11 = zzd2.charAt(i30);
                        if (charAt11 < 55296) {
                            break;
                        }
                        c10 |= (charAt11 & 8191) << i31;
                        i31 += 13;
                        i30 = i21;
                    }
                    charAt16 = c10 | (charAt11 << i31);
                    i30 = i21;
                }
                int i32 = i30 + 1;
                char charAt17 = zzd2.charAt(i30);
                if (charAt17 >= 55296) {
                    char c11 = charAt17 & 8191;
                    int i33 = 13;
                    while (true) {
                        i20 = i32 + 1;
                        charAt10 = zzd2.charAt(i32);
                        if (charAt10 < 55296) {
                            break;
                        }
                        c11 |= (charAt10 & 8191) << i33;
                        i33 += 13;
                        i32 = i20;
                    }
                    charAt17 = c11 | (charAt10 << i33);
                    i32 = i20;
                }
                int i34 = i32 + 1;
                char charAt18 = zzd2.charAt(i32);
                if (charAt18 >= 55296) {
                    char c12 = charAt18 & 8191;
                    int i35 = 13;
                    while (true) {
                        i19 = i34 + 1;
                        charAt9 = zzd2.charAt(i34);
                        if (charAt9 < 55296) {
                            break;
                        }
                        c12 |= (charAt9 & 8191) << i35;
                        i35 += 13;
                        i34 = i19;
                    }
                    charAt18 = c12 | (charAt9 << i35);
                    i34 = i19;
                }
                int i36 = i34 + 1;
                c4 = zzd2.charAt(i34);
                if (c4 >= 55296) {
                    char c13 = c4 & 8191;
                    int i37 = 13;
                    while (true) {
                        i18 = i36 + 1;
                        charAt8 = zzd2.charAt(i36);
                        if (charAt8 < 55296) {
                            break;
                        }
                        c13 |= (charAt8 & 8191) << i37;
                        i37 += 13;
                        i36 = i18;
                    }
                    c4 = c13 | (charAt8 << i37);
                    i36 = i18;
                }
                int i38 = i36 + 1;
                c3 = zzd2.charAt(i36);
                if (c3 >= 55296) {
                    char c14 = c3 & 8191;
                    int i39 = 13;
                    while (true) {
                        i17 = i38 + 1;
                        charAt7 = zzd2.charAt(i38);
                        if (charAt7 < 55296) {
                            break;
                        }
                        c14 |= (charAt7 & 8191) << i39;
                        i39 += 13;
                        i38 = i17;
                    }
                    c3 = c14 | (charAt7 << i39);
                    i38 = i17;
                }
                int i40 = i38 + 1;
                char charAt19 = zzd2.charAt(i38);
                if (charAt19 >= 55296) {
                    char c15 = charAt19 & 8191;
                    int i41 = 13;
                    while (true) {
                        i16 = i40 + 1;
                        charAt6 = zzd2.charAt(i40);
                        if (charAt6 < 55296) {
                            break;
                        }
                        c15 |= (charAt6 & 8191) << i41;
                        i41 += 13;
                        i40 = i16;
                    }
                    charAt19 = c15 | (charAt6 << i41);
                    i40 = i16;
                }
                int i42 = i40 + 1;
                char charAt20 = zzd2.charAt(i40);
                if (charAt20 >= 55296) {
                    char c16 = charAt20 & 8191;
                    int i43 = i42;
                    int i44 = 13;
                    while (true) {
                        i15 = i43 + 1;
                        charAt5 = zzd2.charAt(i43);
                        if (charAt5 < 55296) {
                            break;
                        }
                        c16 |= (charAt5 & 8191) << i44;
                        i44 += 13;
                        i43 = i15;
                    }
                    charAt20 = c16 | (charAt5 << i44);
                    i42 = i15;
                }
                int i45 = charAt20 + c3 + charAt19;
                int i46 = charAt15 + charAt15 + charAt16;
                int[] iArr2 = new int[i45];
                c7 = charAt15;
                iArr = iArr2;
                c5 = charAt17;
                i2 = i46;
                c = charAt20;
                i26 = i42;
                c2 = charAt18;
            }
            Unsafe unsafe = zzb;
            Object[] zze2 = zzms.zze();
            Class<?> cls2 = zzms.zza().getClass();
            int i47 = c + c3;
            int i48 = c4 + c4;
            int[] iArr3 = new int[(c4 * 3)];
            Object[] objArr2 = new Object[i48];
            char c17 = c;
            int i49 = i47;
            int i50 = 0;
            int i51 = 0;
            while (true) {
                boolean z = zzc2 == 2;
                if (i26 < length) {
                    int i52 = i26 + 1;
                    int charAt21 = zzd2.charAt(i26);
                    if (charAt21 >= i24) {
                        int i53 = charAt21 & AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_REPORT;
                        int i54 = i52;
                        int i55 = 13;
                        while (true) {
                            i14 = i54 + 1;
                            charAt4 = zzd2.charAt(i54);
                            i3 = zzc2;
                            if (charAt4 < 55296) {
                                break;
                            }
                            i53 |= (charAt4 & 8191) << i55;
                            i55 += 13;
                            i54 = i14;
                            zzc2 = i3;
                        }
                        charAt21 = i53 | (charAt4 << i55);
                        i4 = i14;
                    } else {
                        i3 = zzc2;
                        i4 = i52;
                    }
                    int i56 = i4 + 1;
                    char charAt22 = zzd2.charAt(i4);
                    int i57 = length;
                    char c18 = 55296;
                    if (charAt22 >= 55296) {
                        char c19 = charAt22 & 8191;
                        int i58 = 13;
                        while (true) {
                            i13 = i56 + 1;
                            charAt3 = zzd2.charAt(i56);
                            if (charAt3 < c18) {
                                break;
                            }
                            c19 |= (charAt3 & 8191) << i58;
                            i58 += 13;
                            i56 = i13;
                            c18 = 55296;
                        }
                        charAt22 = c19 | (charAt3 << i58);
                        i56 = i13;
                    }
                    if ((charAt22 & 1024) != 0) {
                        iArr[i50] = i51;
                        i50++;
                    }
                    char c20 = charAt22 & 255;
                    char c21 = c2;
                    if (c20 >= '3') {
                        int i59 = i56 + 1;
                        char charAt23 = zzd2.charAt(i56);
                        int i60 = i59;
                        if (charAt23 >= 55296) {
                            char c22 = charAt23 & 8191;
                            int i61 = i60;
                            int i62 = 13;
                            while (true) {
                                i12 = i61 + 1;
                                charAt2 = zzd2.charAt(i61);
                                c6 = c5;
                                if (charAt2 < 55296) {
                                    break;
                                }
                                c22 |= (charAt2 & 8191) << i62;
                                i62 += 13;
                                i61 = i12;
                                c5 = c6;
                            }
                            charAt23 = c22 | (charAt2 << i62);
                            i11 = i12;
                        } else {
                            c6 = c5;
                            i11 = i60;
                        }
                        int i63 = c20 - '3';
                        int i64 = i11;
                        if (i63 == 9 || i63 == 17) {
                            int i65 = i51 / 3;
                            objArr2[i65 + i65 + 1] = zze2[i2];
                            i2++;
                        } else if (i63 == 12 && !z) {
                            int i66 = i51 / 3;
                            objArr2[i66 + i66 + 1] = zze2[i2];
                            i2++;
                        }
                        int i67 = charAt23 + charAt23;
                        Object obj = zze2[i67];
                        if (obj instanceof Field) {
                            field2 = (Field) obj;
                        } else {
                            field2 = zzF(cls2, (String) obj);
                            zze2[i67] = field2;
                        }
                        int objectFieldOffset = (int) unsafe.objectFieldOffset(field2);
                        int i68 = i67 + 1;
                        Object obj2 = zze2[i68];
                        if (obj2 instanceof Field) {
                            field3 = (Field) obj2;
                        } else {
                            field3 = zzF(cls2, (String) obj2);
                            zze2[i68] = field3;
                        }
                        i7 = objectFieldOffset;
                        objArr = zze2;
                        i5 = i64;
                        i6 = (int) unsafe.objectFieldOffset(field3);
                        i8 = 0;
                    } else {
                        c6 = c5;
                        int i69 = i2 + 1;
                        Field zzF = zzF(cls2, (String) zze2[i2]);
                        if (c20 == 9 || c20 == 17) {
                            int i70 = i51 / 3;
                            objArr2[i70 + i70 + 1] = zzF.getType();
                        } else if (c20 == 27 || c20 == '1') {
                            int i71 = i51 / 3;
                            objArr2[i71 + i71 + 1] = zze2[i69];
                            i69++;
                        } else if (c20 == 12 || c20 == 30 || c20 == ',') {
                            if (!z) {
                                int i72 = i51 / 3;
                                objArr2[i72 + i72 + 1] = zze2[i69];
                                i69++;
                            }
                        } else if (c20 == '2') {
                            int i73 = c17 + 1;
                            iArr[c17] = i51;
                            int i74 = i51 / 3;
                            int i75 = i69 + 1;
                            int i76 = i74 + i74;
                            objArr2[i76] = zze2[i69];
                            if ((charAt22 & 2048) != 0) {
                                i69 = i75 + 1;
                                objArr2[i76 + 1] = zze2[i75];
                                c17 = i73;
                            } else {
                                i69 = i75;
                                c17 = i73;
                            }
                        }
                        objArr = zze2;
                        i7 = (int) unsafe.objectFieldOffset(zzF);
                        i6 = 1048575;
                        if ((charAt22 & 4096) != 4096 || c20 > 17) {
                            i5 = i56;
                            i8 = 0;
                        } else {
                            int i77 = i56 + 1;
                            char charAt24 = zzd2.charAt(i56);
                            if (charAt24 >= 55296) {
                                char c23 = charAt24 & 8191;
                                int i78 = 13;
                                while (true) {
                                    i5 = i77 + 1;
                                    charAt = zzd2.charAt(i77);
                                    if (charAt < 55296) {
                                        break;
                                    }
                                    c23 |= (charAt & 8191) << i78;
                                    i78 += 13;
                                    i77 = i5;
                                }
                                charAt24 = c23 | (charAt << i78);
                            } else {
                                i5 = i77;
                            }
                            int i79 = c7 + c7 + (charAt24 / ' ');
                            Object obj3 = objArr[i79];
                            if (obj3 instanceof Field) {
                                field = (Field) obj3;
                            } else {
                                field = zzF(cls2, (String) obj3);
                                objArr[i79] = field;
                            }
                            i8 = charAt24 % ' ';
                            i6 = (int) unsafe.objectFieldOffset(field);
                        }
                        if (c20 < 18 || c20 > '1') {
                            i2 = i69;
                        } else {
                            iArr[i49] = i7;
                            i49++;
                            i2 = i69;
                        }
                    }
                    int i80 = i51 + 1;
                    iArr3[i51] = charAt21;
                    int i81 = i80 + 1;
                    if ((charAt22 & 512) != 0) {
                        i9 = 536870912;
                    } else {
                        i9 = 0;
                    }
                    if ((charAt22 & 256) != 0) {
                        i10 = SQLiteDatabase.CREATE_IF_NECESSARY;
                    } else {
                        i10 = 0;
                    }
                    iArr3[i80] = i10 | i9 | (c20 << 20) | i7;
                    i51 = i81 + 1;
                    iArr3[i81] = (i8 << 20) | i6;
                    zze2 = objArr;
                    length = i57;
                    i26 = i5;
                    c2 = c21;
                    zzc2 = i3;
                    c5 = c6;
                    i24 = 55296;
                } else {
                    char c24 = c2;
                    return new zzml(iArr3, objArr2, c5, c2, zzms.zza(), z, false, iArr, c, i47, zzmn, zzlw, zznk, zzko, zzmd);
                }
            }
        } else {
            zznh zznh = (zznh) zzmf2;
            throw null;
        }
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zznu.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zznu.zzf(obj, j)).floatValue();
    }

    private final int zzo(Object obj) {
        int i;
        Object obj2 = obj;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < this.zzc.length) {
            int zzy = zzy(i4);
            int[] iArr = this.zzc;
            int i7 = iArr[i4];
            int zzx = zzx(zzy);
            if (zzx <= 17) {
                int i8 = iArr[i4 + 2];
                int i9 = i8 & i2;
                int i10 = i8 >>> 20;
                if (i9 != i3) {
                    i6 = unsafe.getInt(obj2, (long) i9);
                    i3 = i9;
                }
                i = 1 << i10;
            } else {
                i = 0;
            }
            long j = (long) (zzy & i2);
            switch (zzx) {
                case 0:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 1:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 2:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy(unsafe.getLong(obj2, j));
                        break;
                    }
                case 3:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy(unsafe.getLong(obj2, j));
                        break;
                    }
                case 4:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzu(unsafe.getInt(obj2, j));
                        break;
                    }
                case 5:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 6:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 7:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 1;
                        break;
                    }
                case 8:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(obj2, j);
                        if (!(object instanceof zzka)) {
                            i5 += zzki.zzx(i7 << 3) + zzki.zzw((String) object);
                            break;
                        } else {
                            int i11 = zzki.zzb;
                            int zzd2 = ((zzka) object).zzd();
                            i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzd2) + zzd2;
                            break;
                        }
                    }
                case 9:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzmv.zzn(i7, unsafe.getObject(obj2, j), zzB(i4));
                        break;
                    }
                case 10:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        int i12 = zzki.zzb;
                        int zzd3 = ((zzka) unsafe.getObject(obj2, j)).zzd();
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzd3) + zzd3;
                        break;
                    }
                case 11:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(unsafe.getInt(obj2, j));
                        break;
                    }
                case 12:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzu(unsafe.getInt(obj2, j));
                        break;
                    }
                case 13:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 14:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 15:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        int i13 = unsafe.getInt(obj2, j);
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx((i13 >> 31) ^ (i13 + i13));
                        break;
                    }
                case 16:
                    if ((i & i6) == 0) {
                        break;
                    } else {
                        long j2 = unsafe.getLong(obj2, j);
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy((j2 >> 63) ^ (j2 + j2));
                        break;
                    }
                case 17:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzki.zzt(i7, (zzmi) unsafe.getObject(obj2, j), zzB(i4));
                        break;
                    }
                case 18:
                    i5 += zzmv.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 19:
                    i5 += zzmv.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 20:
                    i5 += zzmv.zzl(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 21:
                    i5 += zzmv.zzw(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 22:
                    i5 += zzmv.zzj(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 23:
                    i5 += zzmv.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 24:
                    i5 += zzmv.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 25:
                    i5 += zzmv.zza(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 26:
                    i5 += zzmv.zzt(i7, (List) unsafe.getObject(obj2, j));
                    break;
                case 27:
                    i5 += zzmv.zzo(i7, (List) unsafe.getObject(obj2, j), zzB(i4));
                    break;
                case 28:
                    i5 += zzmv.zzb(i7, (List) unsafe.getObject(obj2, j));
                    break;
                case 29:
                    i5 += zzmv.zzu(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 30:
                    i5 += zzmv.zzc(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 31:
                    i5 += zzmv.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 32:
                    i5 += zzmv.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 33:
                    i5 += zzmv.zzp(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 34:
                    i5 += zzmv.zzr(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 35:
                    int zzh2 = zzmv.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzh2) + zzh2;
                    }
                    break;
                case 36:
                    int zzf2 = zzmv.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzf2) + zzf2;
                    }
                    break;
                case 37:
                    int zzm2 = zzmv.zzm((List) unsafe.getObject(obj2, j));
                    if (zzm2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzm2) + zzm2;
                    }
                    break;
                case 38:
                    int zzx2 = zzmv.zzx((List) unsafe.getObject(obj2, j));
                    if (zzx2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzx2) + zzx2;
                    }
                    break;
                case 39:
                    int zzk2 = zzmv.zzk((List) unsafe.getObject(obj2, j));
                    if (zzk2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzk2) + zzk2;
                    }
                    break;
                case 40:
                    int zzh3 = zzmv.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh3 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzh3) + zzh3;
                    }
                    break;
                case 41:
                    int zzf3 = zzmv.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf3 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzf3) + zzf3;
                    }
                    break;
                case 42:
                    int i14 = zzmv.zza;
                    int size = ((List) unsafe.getObject(obj2, j)).size();
                    if (size > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(size) + size;
                    }
                    break;
                case 43:
                    int zzv = zzmv.zzv((List) unsafe.getObject(obj2, j));
                    if (zzv > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzv) + zzv;
                    }
                    break;
                case 44:
                    int zzd4 = zzmv.zzd((List) unsafe.getObject(obj2, j));
                    if (zzd4 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzd4) + zzd4;
                    }
                    break;
                case 45:
                    int zzf4 = zzmv.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf4 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzf4) + zzf4;
                    }
                    break;
                case 46:
                    int zzh4 = zzmv.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh4 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzh4) + zzh4;
                    }
                    break;
                case 47:
                    int zzq2 = zzmv.zzq((List) unsafe.getObject(obj2, j));
                    if (zzq2 > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzq2) + zzq2;
                    }
                    break;
                case 48:
                    int zzs = zzmv.zzs((List) unsafe.getObject(obj2, j));
                    if (zzs > 0) {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzs) + zzs;
                    }
                    break;
                case 49:
                    i5 += zzmv.zzi(i7, (List) unsafe.getObject(obj2, j), zzB(i4));
                    break;
                case 50:
                    zzmd.zza(i7, unsafe.getObject(obj2, j), zzC(i4));
                    break;
                case 51:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 52:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 53:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy(zzz(obj2, j));
                        break;
                    }
                case 54:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy(zzz(obj2, j));
                        break;
                    }
                case 55:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzu(zzp(obj2, j));
                        break;
                    }
                case 56:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 57:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 58:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 1;
                        break;
                    }
                case 59:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        Object object2 = unsafe.getObject(obj2, j);
                        if (!(object2 instanceof zzka)) {
                            i5 += zzki.zzx(i7 << 3) + zzki.zzw((String) object2);
                            break;
                        } else {
                            int i15 = zzki.zzb;
                            int zzd5 = ((zzka) object2).zzd();
                            i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzd5) + zzd5;
                            break;
                        }
                    }
                case 60:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzmv.zzn(i7, unsafe.getObject(obj2, j), zzB(i4));
                        break;
                    }
                case 61:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        int i16 = zzki.zzb;
                        int zzd6 = ((zzka) unsafe.getObject(obj2, j)).zzd();
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzd6) + zzd6;
                        break;
                    }
                case 62:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx(zzp(obj2, j));
                        break;
                    }
                case 63:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + zzki.zzu(zzp(obj2, j));
                        break;
                    }
                case 64:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 4;
                        break;
                    }
                case 65:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzx(i7 << 3) + 8;
                        break;
                    }
                case 66:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        int zzp2 = zzp(obj2, j);
                        i5 += zzki.zzx(i7 << 3) + zzki.zzx((zzp2 >> 31) ^ (zzp2 + zzp2));
                        break;
                    }
                case 67:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        long zzz = zzz(obj2, j);
                        i5 += zzki.zzx(i7 << 3) + zzki.zzy((zzz >> 63) ^ (zzz + zzz));
                        break;
                    }
                case 68:
                    if (!zzT(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzki.zzt(i7, (zzmi) unsafe.getObject(obj2, j), zzB(i4));
                        break;
                    }
            }
            i4 += 3;
            i2 = 1048575;
        }
        zznk zznk = this.zzn;
        int zza2 = i5 + zznk.zza(zznk.zzd(obj2));
        if (!this.zzh) {
            return zza2;
        }
        this.zzo.zza(obj2);
        throw null;
    }

    private static int zzp(Object obj, long j) {
        return ((Integer) zznu.zzf(obj, j)).intValue();
    }

    private final int zzq(Object obj, byte[] bArr, int i, int i2, int i3, long j, zzjn zzjn) {
        Unsafe unsafe = zzb;
        Object zzC = zzC(i3);
        Object object = unsafe.getObject(obj, j);
        if (!((zzmc) object).zze()) {
            zzmc zzb2 = zzmc.zza().zzb();
            zzmd.zzb(zzb2, object);
            unsafe.putObject(obj, j, zzb2);
        }
        zzmb zzmb = (zzmb) zzC;
        throw null;
    }

    private final int zzr(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzjn zzjn) {
        boolean z;
        Object obj2 = obj;
        byte[] bArr2 = bArr;
        int i9 = i;
        int i10 = i3;
        int i11 = i4;
        int i12 = i5;
        long j2 = j;
        int i13 = i8;
        zzjn zzjn2 = zzjn;
        Unsafe unsafe = zzb;
        long j3 = (long) (this.zzc[i13 + 2] & 1048575);
        switch (i7) {
            case 51:
                if (i12 == 1) {
                    unsafe.putObject(obj2, j2, Double.valueOf(Double.longBitsToDouble(zzjo.zzp(bArr, i))));
                    int i14 = i9 + 8;
                    unsafe.putInt(obj2, j3, i11);
                    return i14;
                }
                break;
            case 52:
                if (i12 == 5) {
                    unsafe.putObject(obj2, j2, Float.valueOf(Float.intBitsToFloat(zzjo.zzb(bArr, i))));
                    int i15 = i9 + 4;
                    unsafe.putInt(obj2, j3, i11);
                    return i15;
                }
                break;
            case 53:
            case 54:
                if (i12 == 0) {
                    int zzm2 = zzjo.zzm(bArr2, i9, zzjn2);
                    unsafe.putObject(obj2, j2, Long.valueOf(zzjn2.zzb));
                    unsafe.putInt(obj2, j3, i11);
                    return zzm2;
                }
                break;
            case 55:
            case 62:
                if (i12 == 0) {
                    int zzj2 = zzjo.zzj(bArr2, i9, zzjn2);
                    unsafe.putObject(obj2, j2, Integer.valueOf(zzjn2.zza));
                    unsafe.putInt(obj2, j3, i11);
                    return zzj2;
                }
                break;
            case 56:
            case 65:
                if (i12 == 1) {
                    unsafe.putObject(obj2, j2, Long.valueOf(zzjo.zzp(bArr, i)));
                    int i16 = i9 + 8;
                    unsafe.putInt(obj2, j3, i11);
                    return i16;
                }
                break;
            case 57:
            case 64:
                if (i12 == 5) {
                    unsafe.putObject(obj2, j2, Integer.valueOf(zzjo.zzb(bArr, i)));
                    int i17 = i9 + 4;
                    unsafe.putInt(obj2, j3, i11);
                    return i17;
                }
                break;
            case 58:
                if (i12 == 0) {
                    int zzm3 = zzjo.zzm(bArr2, i9, zzjn2);
                    if (zzjn2.zzb != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    unsafe.putObject(obj2, j2, Boolean.valueOf(z));
                    unsafe.putInt(obj2, j3, i11);
                    return zzm3;
                }
                break;
            case 59:
                if (i12 == 2) {
                    int zzj3 = zzjo.zzj(bArr2, i9, zzjn2);
                    int i18 = zzjn2.zza;
                    if (i18 == 0) {
                        unsafe.putObject(obj2, j2, "");
                    } else if ((i6 & 536870912) == 0 || zznz.zze(bArr2, zzj3, zzj3 + i18)) {
                        unsafe.putObject(obj2, j2, new String(bArr2, zzj3, i18, zzlj.zzb));
                        zzj3 += i18;
                    } else {
                        throw zzll.zzc();
                    }
                    unsafe.putInt(obj2, j3, i11);
                    return zzj3;
                }
                break;
            case 60:
                if (i12 == 2) {
                    Object zzE = zzE(obj2, i11, i13);
                    int zzo2 = zzjo.zzo(zzE, zzB(i13), bArr, i, i2, zzjn);
                    zzM(obj2, i11, i13, zzE);
                    return zzo2;
                }
                break;
            case 61:
                if (i12 == 2) {
                    int zza2 = zzjo.zza(bArr2, i9, zzjn2);
                    unsafe.putObject(obj2, j2, zzjn2.zzc);
                    unsafe.putInt(obj2, j3, i11);
                    return zza2;
                }
                break;
            case 63:
                if (i12 == 0) {
                    int zzj4 = zzjo.zzj(bArr2, i9, zzjn2);
                    int i19 = zzjn2.zza;
                    zzlf zzA = zzA(i13);
                    if (zzA == null || zzA.zza(i19)) {
                        unsafe.putObject(obj2, j2, Integer.valueOf(i19));
                        unsafe.putInt(obj2, j3, i11);
                    } else {
                        zzd(obj).zzj(i10, Long.valueOf((long) i19));
                    }
                    return zzj4;
                }
                break;
            case 66:
                if (i12 == 0) {
                    int zzj5 = zzjo.zzj(bArr2, i9, zzjn2);
                    unsafe.putObject(obj2, j2, Integer.valueOf(zzke.zzb(zzjn2.zza)));
                    unsafe.putInt(obj2, j3, i11);
                    return zzj5;
                }
                break;
            case 67:
                if (i12 == 0) {
                    int zzm4 = zzjo.zzm(bArr2, i9, zzjn2);
                    unsafe.putObject(obj2, j2, Long.valueOf(zzke.zzc(zzjn2.zzb)));
                    unsafe.putInt(obj2, j3, i11);
                    return zzm4;
                }
                break;
            case 68:
                if (i12 == 3) {
                    Object zzE2 = zzE(obj2, i11, i13);
                    int i20 = (i10 & -8) | 4;
                    int zzn2 = zzjo.zzn(zzE2, zzB(i13), bArr, i, i2, i20, zzjn);
                    zzM(obj2, i11, i13, zzE2);
                    return zzn2;
                }
                break;
        }
        return i9;
    }

    private final int zzs(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzjn zzjn) {
        boolean z;
        boolean z2;
        boolean z3;
        int i8;
        int i9;
        int i10;
        int i11;
        Object obj2 = obj;
        byte[] bArr2 = bArr;
        int i12 = i;
        int i13 = i2;
        int i14 = i3;
        int i15 = i4;
        int i16 = i5;
        int i17 = i6;
        long j3 = j2;
        zzjn zzjn2 = zzjn;
        Unsafe unsafe = zzb;
        zzli zzli = (zzli) unsafe.getObject(obj2, j3);
        if (!zzli.zzc()) {
            int size = zzli.size();
            zzli = zzli.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(obj2, j3, zzli);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i16 == 2) {
                    zzkk zzkk = (zzkk) zzli;
                    int zzj2 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i18 = zzjn2.zza + zzj2;
                    while (zzj2 < i18) {
                        zzkk.zze(Double.longBitsToDouble(zzjo.zzp(bArr2, zzj2)));
                        zzj2 += 8;
                    }
                    if (zzj2 == i18) {
                        return zzj2;
                    }
                    throw zzll.zzf();
                } else if (i16 == 1) {
                    zzkk zzkk2 = (zzkk) zzli;
                    zzkk2.zze(Double.longBitsToDouble(zzjo.zzp(bArr, i)));
                    int i19 = i12 + 8;
                    while (i19 < i13) {
                        int zzj3 = zzjo.zzj(bArr2, i19, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return i19;
                        }
                        zzkk2.zze(Double.longBitsToDouble(zzjo.zzp(bArr2, zzj3)));
                        i19 = zzj3 + 8;
                    }
                    return i19;
                }
                break;
            case 19:
            case 36:
                if (i16 == 2) {
                    zzku zzku = (zzku) zzli;
                    int zzj4 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i20 = zzjn2.zza + zzj4;
                    while (zzj4 < i20) {
                        zzku.zze(Float.intBitsToFloat(zzjo.zzb(bArr2, zzj4)));
                        zzj4 += 4;
                    }
                    if (zzj4 == i20) {
                        return zzj4;
                    }
                    throw zzll.zzf();
                } else if (i16 == 5) {
                    zzku zzku2 = (zzku) zzli;
                    zzku2.zze(Float.intBitsToFloat(zzjo.zzb(bArr, i)));
                    int i21 = i12 + 4;
                    while (i21 < i13) {
                        int zzj5 = zzjo.zzj(bArr2, i21, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return i21;
                        }
                        zzku2.zze(Float.intBitsToFloat(zzjo.zzb(bArr2, zzj5)));
                        i21 = zzj5 + 4;
                    }
                    return i21;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i16 == 2) {
                    zzlx zzlx = (zzlx) zzli;
                    int zzj6 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i22 = zzjn2.zza + zzj6;
                    while (zzj6 < i22) {
                        zzj6 = zzjo.zzm(bArr2, zzj6, zzjn2);
                        zzlx.zzg(zzjn2.zzb);
                    }
                    if (zzj6 == i22) {
                        return zzj6;
                    }
                    throw zzll.zzf();
                } else if (i16 == 0) {
                    zzlx zzlx2 = (zzlx) zzli;
                    int zzm2 = zzjo.zzm(bArr2, i12, zzjn2);
                    zzlx2.zzg(zzjn2.zzb);
                    while (zzm2 < i13) {
                        int zzj7 = zzjo.zzj(bArr2, zzm2, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return zzm2;
                        }
                        zzm2 = zzjo.zzm(bArr2, zzj7, zzjn2);
                        zzlx2.zzg(zzjn2.zzb);
                    }
                    return zzm2;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i16 == 2) {
                    return zzjo.zzf(bArr2, i12, zzli, zzjn2);
                }
                if (i16 == 0) {
                    return zzjo.zzl(i3, bArr, i, i2, zzli, zzjn);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i16 == 2) {
                    zzlx zzlx3 = (zzlx) zzli;
                    int zzj8 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i23 = zzjn2.zza + zzj8;
                    while (zzj8 < i23) {
                        zzlx3.zzg(zzjo.zzp(bArr2, zzj8));
                        zzj8 += 8;
                    }
                    if (zzj8 == i23) {
                        return zzj8;
                    }
                    throw zzll.zzf();
                } else if (i16 == 1) {
                    zzlx zzlx4 = (zzlx) zzli;
                    zzlx4.zzg(zzjo.zzp(bArr, i));
                    int i24 = i12 + 8;
                    while (i24 < i13) {
                        int zzj9 = zzjo.zzj(bArr2, i24, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return i24;
                        }
                        zzlx4.zzg(zzjo.zzp(bArr2, zzj9));
                        i24 = zzj9 + 8;
                    }
                    return i24;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i16 == 2) {
                    zzlc zzlc = (zzlc) zzli;
                    int zzj10 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i25 = zzjn2.zza + zzj10;
                    while (zzj10 < i25) {
                        zzlc.zzh(zzjo.zzb(bArr2, zzj10));
                        zzj10 += 4;
                    }
                    if (zzj10 == i25) {
                        return zzj10;
                    }
                    throw zzll.zzf();
                } else if (i16 == 5) {
                    zzlc zzlc2 = (zzlc) zzli;
                    zzlc2.zzh(zzjo.zzb(bArr, i));
                    int i26 = i12 + 4;
                    while (i26 < i13) {
                        int zzj11 = zzjo.zzj(bArr2, i26, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return i26;
                        }
                        zzlc2.zzh(zzjo.zzb(bArr2, zzj11));
                        i26 = zzj11 + 4;
                    }
                    return i26;
                }
                break;
            case 25:
            case 42:
                if (i16 == 2) {
                    zzjp zzjp = (zzjp) zzli;
                    int zzj12 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i27 = zzjn2.zza + zzj12;
                    while (zzj12 < i27) {
                        zzj12 = zzjo.zzm(bArr2, zzj12, zzjn2);
                        if (zzjn2.zzb != 0) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        zzjp.zze(z3);
                    }
                    if (zzj12 == i27) {
                        return zzj12;
                    }
                    throw zzll.zzf();
                } else if (i16 == 0) {
                    zzjp zzjp2 = (zzjp) zzli;
                    int zzm3 = zzjo.zzm(bArr2, i12, zzjn2);
                    if (zzjn2.zzb != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    zzjp2.zze(z);
                    while (zzm3 < i13) {
                        int zzj13 = zzjo.zzj(bArr2, zzm3, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return zzm3;
                        }
                        zzm3 = zzjo.zzm(bArr2, zzj13, zzjn2);
                        if (zzjn2.zzb != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        zzjp2.zze(z2);
                    }
                    return zzm3;
                }
                break;
            case 26:
                if (i16 == 2) {
                    if ((j & IjkMediaMeta.AV_CH_STEREO_LEFT) == 0) {
                        int zzj14 = zzjo.zzj(bArr2, i12, zzjn2);
                        int i28 = zzjn2.zza;
                        if (i28 >= 0) {
                            if (i28 == 0) {
                                zzli.add("");
                            } else {
                                zzli.add(new String(bArr2, zzj14, i28, zzlj.zzb));
                                zzj14 += i28;
                            }
                            while (i9 < i13) {
                                int zzj15 = zzjo.zzj(bArr2, i9, zzjn2);
                                if (i14 != zzjn2.zza) {
                                    return i9;
                                }
                                i9 = zzjo.zzj(bArr2, zzj15, zzjn2);
                                int i29 = zzjn2.zza;
                                if (i29 < 0) {
                                    throw zzll.zzd();
                                } else if (i29 == 0) {
                                    zzli.add("");
                                } else {
                                    zzli.add(new String(bArr2, i9, i29, zzlj.zzb));
                                    i9 += i29;
                                }
                            }
                            return i9;
                        }
                        throw zzll.zzd();
                    }
                    int zzj16 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i30 = zzjn2.zza;
                    if (i30 >= 0) {
                        if (i30 == 0) {
                            zzli.add("");
                        } else {
                            int i31 = zzj16 + i30;
                            if (zznz.zze(bArr2, zzj16, i31)) {
                                zzli.add(new String(bArr2, zzj16, i30, zzlj.zzb));
                                zzj16 = i31;
                            } else {
                                throw zzll.zzc();
                            }
                        }
                        while (i8 < i13) {
                            int zzj17 = zzjo.zzj(bArr2, i8, zzjn2);
                            if (i14 != zzjn2.zza) {
                                return i8;
                            }
                            i8 = zzjo.zzj(bArr2, zzj17, zzjn2);
                            int i32 = zzjn2.zza;
                            if (i32 < 0) {
                                throw zzll.zzd();
                            } else if (i32 == 0) {
                                zzli.add("");
                            } else {
                                int i33 = i8 + i32;
                                if (zznz.zze(bArr2, i8, i33)) {
                                    zzli.add(new String(bArr2, i8, i32, zzlj.zzb));
                                    i8 = i33;
                                } else {
                                    throw zzll.zzc();
                                }
                            }
                        }
                        return i8;
                    }
                    throw zzll.zzd();
                }
                break;
            case 27:
                if (i16 == 2) {
                    return zzjo.zze(zzB(i17), i3, bArr, i, i2, zzli, zzjn);
                }
                break;
            case 28:
                if (i16 == 2) {
                    int zzj18 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i34 = zzjn2.zza;
                    if (i34 < 0) {
                        throw zzll.zzd();
                    } else if (i34 <= bArr2.length - zzj18) {
                        if (i34 == 0) {
                            zzli.add(zzka.zzb);
                        } else {
                            zzli.add(zzka.zzl(bArr2, zzj18, i34));
                            zzj18 += i34;
                        }
                        while (i10 < i13) {
                            int zzj19 = zzjo.zzj(bArr2, i10, zzjn2);
                            if (i14 != zzjn2.zza) {
                                return i10;
                            }
                            i10 = zzjo.zzj(bArr2, zzj19, zzjn2);
                            int i35 = zzjn2.zza;
                            if (i35 < 0) {
                                throw zzll.zzd();
                            } else if (i35 > bArr2.length - i10) {
                                throw zzll.zzf();
                            } else if (i35 == 0) {
                                zzli.add(zzka.zzb);
                            } else {
                                zzli.add(zzka.zzl(bArr2, i10, i35));
                                i10 += i35;
                            }
                        }
                        return i10;
                    } else {
                        throw zzll.zzf();
                    }
                }
                break;
            case 30:
            case 44:
                if (i16 == 2) {
                    i11 = zzjo.zzf(bArr2, i12, zzli, zzjn2);
                } else if (i16 == 0) {
                    i11 = zzjo.zzl(i3, bArr, i, i2, zzli, zzjn);
                }
                zzlf zzA = zzA(i17);
                zznk zznk = this.zzn;
                int i36 = zzmv.zza;
                if (zzA != null) {
                    Object obj3 = null;
                    if (zzli instanceof RandomAccess) {
                        int size2 = zzli.size();
                        int i37 = 0;
                        for (int i38 = 0; i38 < size2; i38++) {
                            int intValue = ((Integer) zzli.get(i38)).intValue();
                            if (zzA.zza(intValue)) {
                                if (i38 != i37) {
                                    zzli.set(i37, Integer.valueOf(intValue));
                                }
                                i37++;
                            } else {
                                obj3 = zzmv.zzB(obj2, i15, intValue, obj3, zznk);
                            }
                        }
                        if (i37 != size2) {
                            zzli.subList(i37, size2).clear();
                            return i11;
                        }
                    } else {
                        Iterator it = zzli.iterator();
                        while (it.hasNext()) {
                            int intValue2 = ((Integer) it.next()).intValue();
                            if (!zzA.zza(intValue2)) {
                                obj3 = zzmv.zzB(obj2, i15, intValue2, obj3, zznk);
                                it.remove();
                            }
                        }
                    }
                }
                return i11;
            case 33:
            case 47:
                if (i16 == 2) {
                    zzlc zzlc3 = (zzlc) zzli;
                    int zzj20 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i39 = zzjn2.zza + zzj20;
                    while (zzj20 < i39) {
                        zzj20 = zzjo.zzj(bArr2, zzj20, zzjn2);
                        zzlc3.zzh(zzke.zzb(zzjn2.zza));
                    }
                    if (zzj20 == i39) {
                        return zzj20;
                    }
                    throw zzll.zzf();
                } else if (i16 == 0) {
                    zzlc zzlc4 = (zzlc) zzli;
                    int zzj21 = zzjo.zzj(bArr2, i12, zzjn2);
                    zzlc4.zzh(zzke.zzb(zzjn2.zza));
                    while (zzj21 < i13) {
                        int zzj22 = zzjo.zzj(bArr2, zzj21, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return zzj21;
                        }
                        zzj21 = zzjo.zzj(bArr2, zzj22, zzjn2);
                        zzlc4.zzh(zzke.zzb(zzjn2.zza));
                    }
                    return zzj21;
                }
                break;
            case 34:
            case 48:
                if (i16 == 2) {
                    zzlx zzlx5 = (zzlx) zzli;
                    int zzj23 = zzjo.zzj(bArr2, i12, zzjn2);
                    int i40 = zzjn2.zza + zzj23;
                    while (zzj23 < i40) {
                        zzj23 = zzjo.zzm(bArr2, zzj23, zzjn2);
                        zzlx5.zzg(zzke.zzc(zzjn2.zzb));
                    }
                    if (zzj23 == i40) {
                        return zzj23;
                    }
                    throw zzll.zzf();
                } else if (i16 == 0) {
                    zzlx zzlx6 = (zzlx) zzli;
                    int zzm4 = zzjo.zzm(bArr2, i12, zzjn2);
                    zzlx6.zzg(zzke.zzc(zzjn2.zzb));
                    while (zzm4 < i13) {
                        int zzj24 = zzjo.zzj(bArr2, zzm4, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return zzm4;
                        }
                        zzm4 = zzjo.zzm(bArr2, zzj24, zzjn2);
                        zzlx6.zzg(zzke.zzc(zzjn2.zzb));
                    }
                    return zzm4;
                }
                break;
            default:
                if (i16 == 3) {
                    zzmt zzB = zzB(i17);
                    int i41 = (i14 & -8) | 4;
                    int zzc2 = zzjo.zzc(zzB, bArr, i, i2, i41, zzjn);
                    zzli.add(zzjn2.zzc);
                    while (zzc2 < i13) {
                        int zzj25 = zzjo.zzj(bArr2, zzc2, zzjn2);
                        if (i14 != zzjn2.zza) {
                            return zzc2;
                        }
                        zzc2 = zzjo.zzc(zzB, bArr, zzj25, i2, i41, zzjn);
                        zzli.add(zzjn2.zzc);
                    }
                    return zzc2;
                }
                break;
        }
        return i12;
    }

    private final int zzt(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, 0);
    }

    private final int zzu(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, i2);
    }

    private final int zzv(int i) {
        return this.zzc[i + 2];
    }

    private final int zzw(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static int zzx(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzy(int i) {
        return this.zzc[i + 1];
    }

    private static long zzz(Object obj, long j) {
        return ((Long) zznu.zzf(obj, j)).longValue();
    }

    public final int zza(Object obj) {
        if (!this.zzi) {
            return zzo(obj);
        }
        Unsafe unsafe = zzb;
        int i = 0;
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int zzy = zzy(i2);
            int zzx = zzx(zzy);
            int i3 = this.zzc[i2];
            int i4 = zzy & 1048575;
            if (zzx >= zzkt.DOUBLE_LIST_PACKED.zza() && zzx <= zzkt.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i2 + 2];
            }
            long j = (long) i4;
            switch (zzx) {
                case 0:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 1:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 2:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzy(zznu.zzd(obj, j));
                        break;
                    }
                case 3:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzy(zznu.zzd(obj, j));
                        break;
                    }
                case 4:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzu(zznu.zzc(obj, j));
                        break;
                    }
                case 5:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 6:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 7:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 1;
                        break;
                    }
                case 8:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        Object zzf2 = zznu.zzf(obj, j);
                        if (!(zzf2 instanceof zzka)) {
                            i += zzki.zzx(i3 << 3) + zzki.zzw((String) zzf2);
                            break;
                        } else {
                            int i6 = i3 << 3;
                            int i7 = zzki.zzb;
                            int zzd2 = ((zzka) zzf2).zzd();
                            i += zzki.zzx(i6) + zzki.zzx(zzd2) + zzd2;
                            break;
                        }
                    }
                case 9:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzmv.zzn(i3, zznu.zzf(obj, j), zzB(i2));
                        break;
                    }
                case 10:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        int i8 = i3 << 3;
                        int i9 = zzki.zzb;
                        int zzd3 = ((zzka) zznu.zzf(obj, j)).zzd();
                        i += zzki.zzx(i8) + zzki.zzx(zzd3) + zzd3;
                        break;
                    }
                case 11:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zznu.zzc(obj, j));
                        break;
                    }
                case 12:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzu(zznu.zzc(obj, j));
                        break;
                    }
                case 13:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 14:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 15:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        int zzc2 = zznu.zzc(obj, j);
                        i += zzki.zzx(i3 << 3) + zzki.zzx((zzc2 >> 31) ^ (zzc2 + zzc2));
                        break;
                    }
                case 16:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        long zzd4 = zznu.zzd(obj, j);
                        i += zzki.zzx(i3 << 3) + zzki.zzy((zzd4 + zzd4) ^ (zzd4 >> 63));
                        break;
                    }
                case 17:
                    if (!zzP(obj, i2)) {
                        break;
                    } else {
                        i += zzki.zzt(i3, (zzmi) zznu.zzf(obj, j), zzB(i2));
                        break;
                    }
                case 18:
                    i += zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 19:
                    i += zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 20:
                    i += zzmv.zzl(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 21:
                    i += zzmv.zzw(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 22:
                    i += zzmv.zzj(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 23:
                    i += zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 24:
                    i += zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 25:
                    i += zzmv.zza(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 26:
                    i += zzmv.zzt(i3, (List) zznu.zzf(obj, j));
                    break;
                case 27:
                    i += zzmv.zzo(i3, (List) zznu.zzf(obj, j), zzB(i2));
                    break;
                case 28:
                    i += zzmv.zzb(i3, (List) zznu.zzf(obj, j));
                    break;
                case 29:
                    i += zzmv.zzu(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 30:
                    i += zzmv.zzc(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 31:
                    i += zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 32:
                    i += zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 33:
                    i += zzmv.zzp(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 34:
                    i += zzmv.zzr(i3, (List) zznu.zzf(obj, j), false);
                    break;
                case 35:
                    int zzh2 = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (zzh2 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzh2) + zzh2;
                        break;
                    }
                case 36:
                    int zzf3 = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (zzf3 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzf3) + zzf3;
                        break;
                    }
                case 37:
                    int zzm2 = zzmv.zzm((List) unsafe.getObject(obj, j));
                    if (zzm2 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzm2) + zzm2;
                        break;
                    }
                case 38:
                    int zzx2 = zzmv.zzx((List) unsafe.getObject(obj, j));
                    if (zzx2 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzx2) + zzx2;
                        break;
                    }
                case 39:
                    int zzk2 = zzmv.zzk((List) unsafe.getObject(obj, j));
                    if (zzk2 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzk2) + zzk2;
                        break;
                    }
                case 40:
                    int zzh3 = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (zzh3 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzh3) + zzh3;
                        break;
                    }
                case 41:
                    int zzf4 = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (zzf4 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzf4) + zzf4;
                        break;
                    }
                case 42:
                    int i10 = zzmv.zza;
                    int size = ((List) unsafe.getObject(obj, j)).size();
                    if (size <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(size) + size;
                        break;
                    }
                case 43:
                    int zzv = zzmv.zzv((List) unsafe.getObject(obj, j));
                    if (zzv <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzv) + zzv;
                        break;
                    }
                case 44:
                    int zzd5 = zzmv.zzd((List) unsafe.getObject(obj, j));
                    if (zzd5 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzd5) + zzd5;
                        break;
                    }
                case 45:
                    int zzf5 = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (zzf5 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzf5) + zzf5;
                        break;
                    }
                case 46:
                    int zzh4 = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (zzh4 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzh4) + zzh4;
                        break;
                    }
                case 47:
                    int zzq2 = zzmv.zzq((List) unsafe.getObject(obj, j));
                    if (zzq2 <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzq2) + zzq2;
                        break;
                    }
                case 48:
                    int zzs = zzmv.zzs((List) unsafe.getObject(obj, j));
                    if (zzs <= 0) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzs) + zzs;
                        break;
                    }
                case 49:
                    i += zzmv.zzi(i3, (List) zznu.zzf(obj, j), zzB(i2));
                    break;
                case 50:
                    zzmd.zza(i3, zznu.zzf(obj, j), zzC(i2));
                    break;
                case 51:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 52:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 53:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzy(zzz(obj, j));
                        break;
                    }
                case 54:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzy(zzz(obj, j));
                        break;
                    }
                case 55:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzu(zzp(obj, j));
                        break;
                    }
                case 56:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 57:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 58:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 1;
                        break;
                    }
                case 59:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        Object zzf6 = zznu.zzf(obj, j);
                        if (!(zzf6 instanceof zzka)) {
                            i += zzki.zzx(i3 << 3) + zzki.zzw((String) zzf6);
                            break;
                        } else {
                            int i11 = i3 << 3;
                            int i12 = zzki.zzb;
                            int zzd6 = ((zzka) zzf6).zzd();
                            i += zzki.zzx(i11) + zzki.zzx(zzd6) + zzd6;
                            break;
                        }
                    }
                case 60:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzmv.zzn(i3, zznu.zzf(obj, j), zzB(i2));
                        break;
                    }
                case 61:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        int i13 = i3 << 3;
                        int i14 = zzki.zzb;
                        int zzd7 = ((zzka) zznu.zzf(obj, j)).zzd();
                        i += zzki.zzx(i13) + zzki.zzx(zzd7) + zzd7;
                        break;
                    }
                case 62:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzx(zzp(obj, j));
                        break;
                    }
                case 63:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + zzki.zzu(zzp(obj, j));
                        break;
                    }
                case 64:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 4;
                        break;
                    }
                case 65:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzx(i3 << 3) + 8;
                        break;
                    }
                case 66:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        int zzp2 = zzp(obj, j);
                        i += zzki.zzx(i3 << 3) + zzki.zzx((zzp2 >> 31) ^ (zzp2 + zzp2));
                        break;
                    }
                case 67:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz = zzz(obj, j);
                        i += zzki.zzx(i3 << 3) + zzki.zzy((zzz + zzz) ^ (zzz >> 63));
                        break;
                    }
                case 68:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzki.zzt(i3, (zzmi) zznu.zzf(obj, j), zzB(i2));
                        break;
                    }
            }
        }
        zznk zznk = this.zzn;
        return i + zznk.zza(zznk.zzd(obj));
    }

    public final int zzb(Object obj) {
        int length = this.zzc.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int zzy = zzy(i2);
            int i3 = this.zzc[i2];
            long j = (long) (1048575 & zzy);
            int i4 = 37;
            switch (zzx(zzy)) {
                case 0:
                    long doubleToLongBits = Double.doubleToLongBits(zznu.zza(obj, j));
                    byte[] bArr = zzlj.zzd;
                    i = (i * 53) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zznu.zzb(obj, j));
                    break;
                case 2:
                    long zzd2 = zznu.zzd(obj, j);
                    byte[] bArr2 = zzlj.zzd;
                    i = (i * 53) + ((int) (zzd2 ^ (zzd2 >>> 32)));
                    break;
                case 3:
                    long zzd3 = zznu.zzd(obj, j);
                    byte[] bArr3 = zzlj.zzd;
                    i = (i * 53) + ((int) (zzd3 ^ (zzd3 >>> 32)));
                    break;
                case 4:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 5:
                    long zzd4 = zznu.zzd(obj, j);
                    byte[] bArr4 = zzlj.zzd;
                    i = (i * 53) + ((int) (zzd4 ^ (zzd4 >>> 32)));
                    break;
                case 6:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 7:
                    i = (i * 53) + zzlj.zza(zznu.zzw(obj, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zznu.zzf(obj, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zznu.zzf(obj, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zznu.zzf(obj, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 12:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 13:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 14:
                    long zzd5 = zznu.zzd(obj, j);
                    byte[] bArr5 = zzlj.zzd;
                    i = (i * 53) + ((int) (zzd5 ^ (zzd5 >>> 32)));
                    break;
                case 15:
                    i = (i * 53) + zznu.zzc(obj, j);
                    break;
                case 16:
                    long zzd6 = zznu.zzd(obj, j);
                    byte[] bArr6 = zzlj.zzd;
                    i = (i * 53) + ((int) (zzd6 ^ (zzd6 >>> 32)));
                    break;
                case 17:
                    Object zzf3 = zznu.zzf(obj, j);
                    if (zzf3 != null) {
                        i4 = zzf3.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + zznu.zzf(obj, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zznu.zzf(obj, j).hashCode();
                    break;
                case 51:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long doubleToLongBits2 = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzlj.zzd;
                        i = (i * 53) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
                        break;
                    }
                case 52:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzn(obj, j));
                        break;
                    }
                case 53:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz = zzz(obj, j);
                        byte[] bArr8 = zzlj.zzd;
                        i = (i * 53) + ((int) (zzz ^ (zzz >>> 32)));
                        break;
                    }
                case 54:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz2 = zzz(obj, j);
                        byte[] bArr9 = zzlj.zzd;
                        i = (i * 53) + ((int) (zzz2 ^ (zzz2 >>> 32)));
                        break;
                    }
                case 55:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 56:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz3 = zzz(obj, j);
                        byte[] bArr10 = zzlj.zzd;
                        i = (i * 53) + ((int) (zzz3 ^ (zzz3 >>> 32)));
                        break;
                    }
                case 57:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 58:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzlj.zza(zzU(obj, j));
                        break;
                    }
                case 59:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zznu.zzf(obj, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zznu.zzf(obj, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zznu.zzf(obj, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 63:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 64:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 65:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz4 = zzz(obj, j);
                        byte[] bArr11 = zzlj.zzd;
                        i = (i * 53) + ((int) (zzz4 ^ (zzz4 >>> 32)));
                        break;
                    }
                case 66:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzp(obj, j);
                        break;
                    }
                case 67:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        long zzz5 = zzz(obj, j);
                        byte[] bArr12 = zzlj.zzd;
                        i = (i * 53) + ((int) (zzz5 ^ (zzz5 >>> 32)));
                        break;
                    }
                case 68:
                    if (!zzT(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zznu.zzf(obj, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzn.zzd(obj).hashCode();
        if (!this.zzh) {
            return hashCode;
        }
        this.zzo.zza(obj);
        throw null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v28, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v34, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v30, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v38, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v32, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v41, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v30, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v34, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v45, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v37, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v48, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v50, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v40, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v36, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v42, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v17, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzc(java.lang.Object r30, byte[] r31, int r32, int r33, int r34, com.google.android.gms.internal.measurement.zzjn r35) {
        /*
            r29 = this;
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r9 = r35
            zzG(r30)
            sun.misc.Unsafe r10 = zzb
            r8 = -1
            r16 = 0
            r0 = r32
            r1 = r8
            r2 = r16
            r3 = r2
            r5 = r3
            r6 = 1048575(0xfffff, float:1.469367E-39)
        L_0x001e:
            r17 = 0
            if (r0 >= r13) goto L_0x04f2
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0031
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzk(r0, r12, r3, r9)
            int r3 = r9.zza
            r4 = r3
            r3 = r0
            goto L_0x0032
        L_0x0031:
            r4 = r0
        L_0x0032:
            int r0 = r4 >>> 3
            r7 = 3
            if (r0 <= r1) goto L_0x003e
            int r2 = r2 / r7
            int r1 = r15.zzu(r0, r2)
            r2 = r1
            goto L_0x0043
        L_0x003e:
            int r1 = r15.zzt(r0)
            r2 = r1
        L_0x0043:
            if (r2 != r8) goto L_0x0054
            r20 = r0
            r2 = r3
            r21 = r4
            r22 = r5
            r18 = r8
            r28 = r10
            r19 = r16
            goto L_0x0482
        L_0x0054:
            r1 = r4 & 7
            int[] r8 = r15.zzc
            int r20 = r2 + 1
            r7 = r8[r20]
            int r11 = zzx(r7)
            r20 = r0
            r18 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r7 & r18
            r22 = r3
            r21 = r4
            long r3 = (long) r0
            r0 = 17
            if (r11 > r0) goto L_0x0336
            int r0 = r2 + 2
            r0 = r8[r0]
            int r8 = r0 >>> 20
            r13 = 1
            int r8 = r13 << r8
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r13
            if (r0 == r6) goto L_0x0093
            if (r6 == r13) goto L_0x0088
            r18 = r7
            long r6 = (long) r6
            r10.putInt(r14, r6, r5)
            goto L_0x008a
        L_0x0088:
            r18 = r7
        L_0x008a:
            long r5 = (long) r0
            int r5 = r10.getInt(r14, r5)
            r25 = r0
            r7 = r5
            goto L_0x0098
        L_0x0093:
            r18 = r7
            r7 = r5
            r25 = r6
        L_0x0098:
            r0 = 5
            switch(r11) {
                case 0: goto L_0x0300;
                case 1: goto L_0x02db;
                case 2: goto L_0x02b1;
                case 3: goto L_0x02b1;
                case 4: goto L_0x028f;
                case 5: goto L_0x0264;
                case 6: goto L_0x0241;
                case 7: goto L_0x0214;
                case 8: goto L_0x01e5;
                case 9: goto L_0x01ab;
                case 10: goto L_0x0184;
                case 11: goto L_0x028f;
                case 12: goto L_0x0138;
                case 13: goto L_0x0241;
                case 14: goto L_0x0264;
                case 15: goto L_0x010e;
                case 16: goto L_0x00d4;
                default: goto L_0x009c;
            }
        L_0x009c:
            r6 = r2
            r11 = r20
            r13 = r22
            r0 = 3
            if (r1 != r0) goto L_0x0326
            java.lang.Object r5 = r15.zzD(r14, r6)
            int r0 = r11 << 3
            r17 = r0 | 4
            com.google.android.gms.internal.measurement.zzmt r1 = r15.zzB(r6)
            r0 = r5
            r2 = r31
            r3 = r13
            r4 = r33
            r13 = r5
            r5 = r17
            r12 = r6
            r6 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzn(r0, r1, r2, r3, r4, r5, r6)
            r15.zzL(r14, r12, r13)
            r5 = r7 | r8
            r13 = r33
            r1 = r11
            r2 = r12
            r3 = r21
            r6 = r25
            r8 = -1
            r12 = r31
            r11 = r34
            goto L_0x001e
        L_0x00d4:
            if (r1 != 0) goto L_0x0103
            r5 = r22
            int r6 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r5, r9)
            long r0 = r9.zzb
            long r17 = com.google.android.gms.internal.measurement.zzke.zzc(r0)
            r11 = r20
            r0 = r10
            r1 = r30
            r5 = r2
            r2 = r3
            r32 = r6
            r13 = r21
            r6 = r5
            r4 = r17
            r0.putLong(r1, r2, r4)
            r5 = r7 | r8
            r0 = r32
            r2 = r6
            r1 = r11
            r3 = r13
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x0103:
            r6 = r2
            r11 = r20
            r13 = r21
            r5 = r22
            r12 = r6
            r13 = r5
            goto L_0x0327
        L_0x010e:
            r6 = r2
            r11 = r20
            r13 = r21
            r5 = r22
            if (r1 != 0) goto L_0x0132
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r5, r9)
            int r1 = r9.zza
            int r1 = com.google.android.gms.internal.measurement.zzke.zzb(r1)
            r10.putInt(r14, r3, r1)
            r5 = r7 | r8
            r2 = r6
            r1 = r11
            r3 = r13
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x0132:
            r12 = r6
            r21 = r13
            r13 = r5
            goto L_0x0327
        L_0x0138:
            r6 = r2
            r11 = r20
            r13 = r21
            r5 = r22
            if (r1 != 0) goto L_0x017e
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r5, r9)
            int r1 = r9.zza
            com.google.android.gms.internal.measurement.zzlf r2 = r15.zzA(r6)
            if (r2 == 0) goto L_0x016d
            boolean r2 = r2.zza(r1)
            if (r2 == 0) goto L_0x0154
            goto L_0x016d
        L_0x0154:
            com.google.android.gms.internal.measurement.zznl r2 = zzd(r30)
            long r3 = (long) r1
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            r2.zzj(r13, r1)
            r2 = r6
            r5 = r7
            r1 = r11
            r3 = r13
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x016d:
            r10.putInt(r14, r3, r1)
            r5 = r7 | r8
            r2 = r6
            r1 = r11
            r3 = r13
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x017e:
            r12 = r6
            r21 = r13
            r13 = r5
            goto L_0x0327
        L_0x0184:
            r6 = r2
            r11 = r20
            r13 = r21
            r5 = r22
            r0 = 2
            if (r1 != r0) goto L_0x01a5
            int r0 = com.google.android.gms.internal.measurement.zzjo.zza(r12, r5, r9)
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r3, r1)
            r5 = r7 | r8
            r2 = r6
            r1 = r11
            r3 = r13
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x01a5:
            r12 = r6
            r21 = r13
            r13 = r5
            goto L_0x0327
        L_0x01ab:
            r6 = r2
            r11 = r20
            r13 = r21
            r5 = r22
            r0 = 2
            if (r1 != r0) goto L_0x01df
            java.lang.Object r4 = r15.zzD(r14, r6)
            com.google.android.gms.internal.measurement.zzmt r1 = r15.zzB(r6)
            r0 = r4
            r2 = r31
            r3 = r5
            r5 = r4
            r4 = r33
            r21 = r13
            r13 = r5
            r5 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzo(r0, r1, r2, r3, r4, r5)
            r15.zzL(r14, r6, r13)
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x01df:
            r21 = r13
            r13 = r5
            r12 = r6
            goto L_0x0327
        L_0x01e5:
            r6 = r2
            r11 = r20
            r5 = r22
            r0 = 2
            if (r1 != r0) goto L_0x0210
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r18 & r0
            if (r0 != 0) goto L_0x01f8
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzg(r12, r5, r9)
            goto L_0x01fc
        L_0x01f8:
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzh(r12, r5, r9)
        L_0x01fc:
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r3, r1)
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x0210:
            r13 = r5
            r12 = r6
            goto L_0x0327
        L_0x0214:
            r6 = r2
            r11 = r20
            r5 = r22
            if (r1 != 0) goto L_0x023d
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r5, r9)
            long r1 = r9.zzb
            r17 = 0
            int r1 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r1 == 0) goto L_0x0229
            r13 = 1
            goto L_0x022b
        L_0x0229:
            r13 = r16
        L_0x022b:
            com.google.android.gms.internal.measurement.zznu.zzm(r14, r3, r13)
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x023d:
            r13 = r5
            r12 = r6
            goto L_0x0327
        L_0x0241:
            r6 = r2
            r11 = r20
            r5 = r22
            if (r1 != r0) goto L_0x0260
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzb(r12, r5)
            r10.putInt(r14, r3, r0)
            int r0 = r5 + 4
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x0260:
            r13 = r5
            r12 = r6
            goto L_0x0327
        L_0x0264:
            r6 = r2
            r11 = r20
            r5 = r22
            r0 = 1
            if (r1 != r0) goto L_0x028b
            long r17 = com.google.android.gms.internal.measurement.zzjo.zzp(r12, r5)
            r0 = r10
            r1 = r30
            r13 = r5
            r2 = r3
            r4 = r17
            r0.putLong(r1, r2, r4)
            int r0 = r13 + 8
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x028b:
            r13 = r5
            r12 = r6
            goto L_0x0327
        L_0x028f:
            r6 = r2
            r11 = r20
            r13 = r22
            if (r1 != 0) goto L_0x02ae
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r13, r9)
            int r1 = r9.zza
            r10.putInt(r14, r3, r1)
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x02ae:
            r12 = r6
            goto L_0x0327
        L_0x02b1:
            r6 = r2
            r11 = r20
            r13 = r22
            if (r1 != 0) goto L_0x02d9
            int r13 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r13, r9)
            long r1 = r9.zzb
            r0 = r10
            r17 = r1
            r1 = r30
            r2 = r3
            r4 = r17
            r0.putLong(r1, r2, r4)
            r5 = r7 | r8
            r2 = r6
            r1 = r11
            r0 = r13
            r3 = r21
            r6 = r25
            r8 = -1
            r13 = r33
            r11 = r34
            goto L_0x001e
        L_0x02d9:
            r12 = r6
            goto L_0x0327
        L_0x02db:
            r6 = r2
            r11 = r20
            r13 = r22
            if (r1 != r0) goto L_0x02fe
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzb(r12, r13)
            float r0 = java.lang.Float.intBitsToFloat(r0)
            com.google.android.gms.internal.measurement.zznu.zzp(r14, r3, r0)
            int r0 = r13 + 4
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x02fe:
            r12 = r6
            goto L_0x0327
        L_0x0300:
            r6 = r2
            r11 = r20
            r13 = r22
            r0 = 1
            if (r1 != r0) goto L_0x0324
            long r0 = com.google.android.gms.internal.measurement.zzjo.zzp(r12, r13)
            double r0 = java.lang.Double.longBitsToDouble(r0)
            com.google.android.gms.internal.measurement.zznu.zzo(r14, r3, r0)
            int r0 = r13 + 8
            r5 = r7 | r8
            r13 = r33
            r2 = r6
            r1 = r11
            r3 = r21
            r6 = r25
            r8 = -1
            r11 = r34
            goto L_0x001e
        L_0x0324:
            r12 = r6
            goto L_0x0327
        L_0x0326:
            r12 = r6
        L_0x0327:
            r22 = r7
            r28 = r10
            r20 = r11
            r19 = r12
            r2 = r13
            r6 = r25
            r18 = -1
            goto L_0x0482
        L_0x0336:
            r12 = r2
            r18 = r7
            r8 = r20
            r13 = r22
            r0 = 27
            if (r11 != r0) goto L_0x039a
            r0 = 2
            if (r1 != r0) goto L_0x038b
            java.lang.Object r0 = r10.getObject(r14, r3)
            com.google.android.gms.internal.measurement.zzli r0 = (com.google.android.gms.internal.measurement.zzli) r0
            boolean r1 = r0.zzc()
            if (r1 != 0) goto L_0x0363
            int r1 = r0.size()
            if (r1 != 0) goto L_0x0359
            r1 = 10
            goto L_0x035a
        L_0x0359:
            int r1 = r1 + r1
        L_0x035a:
            com.google.android.gms.internal.measurement.zzli r0 = r0.zzd(r1)
            r10.putObject(r14, r3, r0)
            r7 = r0
            goto L_0x0364
        L_0x0363:
            r7 = r0
        L_0x0364:
            com.google.android.gms.internal.measurement.zzmt r0 = r15.zzB(r12)
            r1 = r21
            r2 = r31
            r3 = r13
            r4 = r33
            r22 = r5
            r5 = r7
            r25 = r6
            r6 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zze(r0, r1, r2, r3, r4, r5, r6)
            r13 = r33
            r11 = r34
            r1 = r8
            r2 = r12
            r3 = r21
            r5 = r22
            r6 = r25
            r8 = -1
            r12 = r31
            goto L_0x001e
        L_0x038b:
            r22 = r5
            r25 = r6
            r20 = r8
            r28 = r10
            r19 = r12
            r15 = r13
            r18 = -1
            goto L_0x0440
        L_0x039a:
            r22 = r5
            r25 = r6
            r0 = 49
            if (r11 > r0) goto L_0x03f3
            r7 = r18
            long r6 = (long) r7
            r0 = r29
            r5 = r1
            r1 = r30
            r2 = r31
            r26 = r3
            r3 = r13
            r4 = r33
            r32 = r5
            r5 = r21
            r23 = r6
            r6 = r8
            r7 = r32
            r20 = r8
            r18 = -1
            r8 = r12
            r28 = r10
            r9 = r23
            r15 = r34
            r19 = r12
            r15 = r13
            r12 = r26
            r14 = r35
            int r0 = r0.zzs(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            if (r0 == r15) goto L_0x03ee
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r9 = r35
            r8 = r18
            r2 = r19
            r1 = r20
            r3 = r21
            r5 = r22
            r6 = r25
            r10 = r28
            goto L_0x001e
        L_0x03ee:
            r2 = r0
            r6 = r25
            goto L_0x0482
        L_0x03f3:
            r32 = r1
            r26 = r3
            r20 = r8
            r28 = r10
            r19 = r12
            r15 = r13
            r7 = r18
            r18 = -1
            r0 = 50
            if (r11 != r0) goto L_0x0444
            r8 = r32
            r0 = 2
            if (r8 != r0) goto L_0x0440
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r19
            r6 = r26
            r8 = r35
            int r0 = r0.zzq(r1, r2, r3, r4, r5, r6, r8)
            if (r0 == r15) goto L_0x043c
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r9 = r35
            r8 = r18
            r2 = r19
            r1 = r20
            r3 = r21
            r5 = r22
            r6 = r25
            r10 = r28
            goto L_0x001e
        L_0x043c:
            r2 = r0
            r6 = r25
            goto L_0x0482
        L_0x0440:
            r2 = r15
            r6 = r25
            goto L_0x0482
        L_0x0444:
            r8 = r32
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r21
            r6 = r20
            r9 = r7
            r7 = r8
            r8 = r9
            r9 = r11
            r10 = r26
            r12 = r19
            r13 = r35
            int r0 = r0.zzr(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == r15) goto L_0x047f
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r9 = r35
            r8 = r18
            r2 = r19
            r1 = r20
            r3 = r21
            r5 = r22
            r6 = r25
            r10 = r28
            goto L_0x001e
        L_0x047f:
            r2 = r0
            r6 = r25
        L_0x0482:
            r0 = r34
            r7 = r21
            if (r7 != r0) goto L_0x0495
            if (r0 == 0) goto L_0x0495
            r8 = r29
            r12 = r30
            r9 = r0
            r0 = r2
            r3 = r7
            r5 = r22
            goto L_0x04fb
        L_0x0495:
            r8 = r29
            r9 = r0
            boolean r0 = r8.zzh
            if (r0 == 0) goto L_0x04c9
            r10 = r35
            com.google.android.gms.internal.measurement.zzkn r0 = r10.zzd
            com.google.android.gms.internal.measurement.zzkn r1 = com.google.android.gms.internal.measurement.zzkn.zza
            if (r0 == r1) goto L_0x04c6
            com.google.android.gms.internal.measurement.zzmi r1 = r8.zzg
            r11 = r20
            com.google.android.gms.internal.measurement.zzkz r0 = r0.zzb(r1, r11)
            if (r0 != 0) goto L_0x04c0
            com.google.android.gms.internal.measurement.zznl r4 = zzd(r30)
            r0 = r7
            r1 = r31
            r3 = r33
            r5 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzi(r0, r1, r2, r3, r4, r5)
            r12 = r30
            goto L_0x04de
        L_0x04c0:
            r12 = r30
            r0 = r12
            com.google.android.gms.internal.measurement.zzky r0 = (com.google.android.gms.internal.measurement.zzky) r0
            throw r17
        L_0x04c6:
            r12 = r30
            goto L_0x04cd
        L_0x04c9:
            r12 = r30
            r10 = r35
        L_0x04cd:
            r11 = r20
            com.google.android.gms.internal.measurement.zznl r4 = zzd(r30)
            r0 = r7
            r1 = r31
            r3 = r33
            r5 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzi(r0, r1, r2, r3, r4, r5)
        L_0x04de:
            r13 = r33
            r3 = r7
            r15 = r8
            r1 = r11
            r14 = r12
            r8 = r18
            r2 = r19
            r5 = r22
            r12 = r31
            r11 = r9
            r9 = r10
            r10 = r28
            goto L_0x001e
        L_0x04f2:
            r22 = r5
            r25 = r6
            r28 = r10
            r9 = r11
            r12 = r14
            r8 = r15
        L_0x04fb:
            r1 = 1048575(0xfffff, float:1.469367E-39)
            if (r6 == r1) goto L_0x0506
            long r6 = (long) r6
            r2 = r28
            r2.putInt(r12, r6, r5)
        L_0x0506:
            int r2 = r8.zzk
        L_0x0508:
            int r4 = r8.zzl
            if (r2 >= r4) goto L_0x0533
            int[] r4 = r8.zzj
            r4 = r4[r2]
            int[] r5 = r8.zzc
            r5 = r5[r4]
            int r5 = r8.zzy(r4)
            r5 = r5 & r1
            long r5 = (long) r5
            java.lang.Object r5 = com.google.android.gms.internal.measurement.zznu.zzf(r12, r5)
            if (r5 != 0) goto L_0x0521
            goto L_0x0527
        L_0x0521:
            com.google.android.gms.internal.measurement.zzlf r6 = r8.zzA(r4)
            if (r6 != 0) goto L_0x052a
        L_0x0527:
            int r2 = r2 + 1
            goto L_0x0508
        L_0x052a:
            com.google.android.gms.internal.measurement.zzmc r5 = (com.google.android.gms.internal.measurement.zzmc) r5
            java.lang.Object r0 = r8.zzC(r4)
            com.google.android.gms.internal.measurement.zzmb r0 = (com.google.android.gms.internal.measurement.zzmb) r0
            throw r17
        L_0x0533:
            if (r9 != 0) goto L_0x053f
            r1 = r33
            if (r0 != r1) goto L_0x053a
            goto L_0x0545
        L_0x053a:
            com.google.android.gms.internal.measurement.zzll r0 = com.google.android.gms.internal.measurement.zzll.zze()
            throw r0
        L_0x053f:
            r1 = r33
            if (r0 > r1) goto L_0x0546
            if (r3 != r9) goto L_0x0546
        L_0x0545:
            return r0
        L_0x0546:
            com.google.android.gms.internal.measurement.zzll r0 = com.google.android.gms.internal.measurement.zzll.zze()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzml.zzc(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzjn):int");
    }

    public final Object zze() {
        return ((zzlb) this.zzg).zzbD();
    }

    public final void zzf(Object obj) {
        if (zzS(obj)) {
            if (obj instanceof zzlb) {
                zzlb zzlb = (zzlb) obj;
                zzlb.zzbP(Integer.MAX_VALUE);
                zzlb.zzb = 0;
                zzlb.zzbN();
            }
            int length = this.zzc.length;
            for (int i = 0; i < length; i += 3) {
                int zzy = zzy(i);
                long j = (long) (1048575 & zzy);
                switch (zzx(zzy)) {
                    case 9:
                    case 17:
                        if (!zzP(obj, i)) {
                            break;
                        } else {
                            zzB(i).zzf(zzb.getObject(obj, j));
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzm.zza(obj, j);
                        break;
                    case 50:
                        Unsafe unsafe = zzb;
                        Object object = unsafe.getObject(obj, j);
                        if (object == null) {
                            break;
                        } else {
                            ((zzmc) object).zzc();
                            unsafe.putObject(obj, j, object);
                            break;
                        }
                    case 60:
                    case 68:
                        if (!zzT(obj, this.zzc[i], i)) {
                            break;
                        } else {
                            zzB(i).zzf(zzb.getObject(obj, j));
                            break;
                        }
                }
            }
            this.zzn.zzg(obj);
            if (this.zzh) {
                this.zzo.zzb(obj);
            }
        }
    }

    public final void zzg(Object obj, Object obj2) {
        zzG(obj);
        if (obj2 != null) {
            for (int i = 0; i < this.zzc.length; i += 3) {
                int zzy = zzy(i);
                int i2 = this.zzc[i];
                long j = (long) (1048575 & zzy);
                switch (zzx(zzy)) {
                    case 0:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzo(obj, j, zznu.zza(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 1:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzp(obj, j, zznu.zzb(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 2:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzr(obj, j, zznu.zzd(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 3:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzr(obj, j, zznu.zzd(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 4:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 5:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzr(obj, j, zznu.zzd(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 6:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 7:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzm(obj, j, zznu.zzw(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 8:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzs(obj, j, zznu.zzf(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 9:
                        zzH(obj, obj2, i);
                        break;
                    case 10:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzs(obj, j, zznu.zzf(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 11:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 12:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 13:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 14:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzr(obj, j, zznu.zzd(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 15:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzq(obj, j, zznu.zzc(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 16:
                        if (!zzP(obj2, i)) {
                            break;
                        } else {
                            zznu.zzr(obj, j, zznu.zzd(obj2, j));
                            zzJ(obj, i);
                            break;
                        }
                    case 17:
                        zzH(obj, obj2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzm.zzb(obj, obj2, j);
                        break;
                    case 50:
                        int i3 = zzmv.zza;
                        zznu.zzs(obj, j, zzmd.zzb(zznu.zzf(obj, j), zznu.zzf(obj2, j)));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zzT(obj2, i2, i)) {
                            break;
                        } else {
                            zznu.zzs(obj, j, zznu.zzf(obj2, j));
                            zzK(obj, i2, i);
                            break;
                        }
                    case 60:
                        zzI(obj, obj2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zzT(obj2, i2, i)) {
                            break;
                        } else {
                            zznu.zzs(obj, j, zznu.zzf(obj2, j));
                            zzK(obj, i2, i);
                            break;
                        }
                    case 68:
                        zzI(obj, obj2, i);
                        break;
                }
            }
            zzmv.zzC(this.zzn, obj, obj2);
            if (this.zzh) {
                this.zzo.zza(obj2);
                throw null;
            }
            return;
        }
        throw null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzh(java.lang.Object r31, byte[] r32, int r33, int r34, com.google.android.gms.internal.measurement.zzjn r35) {
        /*
            r30 = this;
            r15 = r30
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r35
            boolean r0 = r15.zzi
            if (r0 == 0) goto L_0x0494
            zzG(r31)
            sun.misc.Unsafe r9 = zzb
            r10 = -1
            r16 = 0
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r33
            r7 = r8
            r1 = r10
            r2 = r16
            r6 = r2
        L_0x0020:
            if (r0 >= r13) goto L_0x0475
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0032
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzk(r0, r12, r3, r11)
            int r3 = r11.zza
            r4 = r0
            r17 = r3
            goto L_0x0035
        L_0x0032:
            r17 = r0
            r4 = r3
        L_0x0035:
            int r5 = r17 >>> 3
            if (r5 <= r1) goto L_0x0041
            int r2 = r2 / 3
            int r0 = r15.zzu(r5, r2)
            r2 = r0
            goto L_0x0046
        L_0x0041:
            int r0 = r15.zzt(r5)
            r2 = r0
        L_0x0046:
            if (r2 != r10) goto L_0x0053
            r2 = r4
            r23 = r5
            r29 = r9
            r18 = r10
            r15 = r16
            goto L_0x044f
        L_0x0053:
            r3 = r17 & 7
            int[] r0 = r15.zzc
            int r1 = r2 + 1
            r1 = r0[r1]
            int r13 = zzx(r1)
            r10 = r1 & r8
            r19 = r9
            long r8 = (long) r10
            r10 = 17
            r33 = r5
            if (r13 > r10) goto L_0x0305
            int r10 = r2 + 2
            r0 = r0[r10]
            int r10 = r0 >>> 20
            r5 = 1
            int r10 = r5 << r10
            r15 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r15
            if (r0 == r7) goto L_0x0097
            if (r7 == r15) goto L_0x0086
            r22 = r1
            r20 = r2
            long r1 = (long) r7
            r7 = r19
            r7.putInt(r14, r1, r6)
            goto L_0x008c
        L_0x0086:
            r22 = r1
            r20 = r2
            r7 = r19
        L_0x008c:
            if (r0 == r15) goto L_0x0094
            long r1 = (long) r0
            int r1 = r7.getInt(r14, r1)
            r6 = r1
        L_0x0094:
            r2 = r7
            r7 = r0
            goto L_0x009d
        L_0x0097:
            r22 = r1
            r20 = r2
            r2 = r19
        L_0x009d:
            r0 = 5
            switch(r13) {
                case 0: goto L_0x02d3;
                case 1: goto L_0x02aa;
                case 2: goto L_0x027f;
                case 3: goto L_0x027f;
                case 4: goto L_0x025a;
                case 5: goto L_0x022b;
                case 6: goto L_0x0206;
                case 7: goto L_0x01d8;
                case 8: goto L_0x01a7;
                case 9: goto L_0x016b;
                case 10: goto L_0x0140;
                case 11: goto L_0x025a;
                case 12: goto L_0x0116;
                case 13: goto L_0x0206;
                case 14: goto L_0x022b;
                case 15: goto L_0x00e8;
                case 16: goto L_0x00ae;
                default: goto L_0x00a1;
            }
        L_0x00a1:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            goto L_0x02fc
        L_0x00ae:
            if (r3 != 0) goto L_0x00da
            int r13 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r4, r11)
            long r0 = r11.zzb
            long r4 = com.google.android.gms.internal.measurement.zzke.zzc(r0)
            r0 = r2
            r1 = r31
            r15 = r20
            r20 = r7
            r7 = r2
            r2 = r8
            r23 = r33
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r9 = r7
            r0 = r13
            r2 = r15
            r7 = r20
            r1 = r23
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r10 = -1
            r15 = r30
            r13 = r34
            goto L_0x0020
        L_0x00da:
            r23 = r33
            r15 = r20
            r20 = r7
            r7 = r2
            r13 = r30
            r19 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x02fc
        L_0x00e8:
            r23 = r33
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != 0) goto L_0x010f
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r4, r11)
            int r1 = r11.zza
            int r1 = com.google.android.gms.internal.measurement.zzke.zzb(r1)
            r7.putInt(r14, r8, r1)
            r6 = r6 | r10
            r13 = r34
            r9 = r7
            r2 = r15
            r7 = r20
            r1 = r23
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r10 = -1
            r15 = r30
            goto L_0x0020
        L_0x010f:
            r13 = r30
            r19 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x02fc
        L_0x0116:
            r23 = r33
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != 0) goto L_0x0139
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r4, r11)
            int r1 = r11.zza
            r7.putInt(r14, r8, r1)
            r6 = r6 | r10
            r13 = r34
            r9 = r7
            r2 = r15
            r7 = r20
            r1 = r23
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r10 = -1
            r15 = r30
            goto L_0x0020
        L_0x0139:
            r13 = r30
            r19 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x02fc
        L_0x0140:
            r23 = r33
            r15 = r20
            r20 = r7
            r7 = r2
            r0 = 2
            if (r3 != r0) goto L_0x0164
            int r0 = com.google.android.gms.internal.measurement.zzjo.zza(r12, r4, r11)
            java.lang.Object r1 = r11.zzc
            r7.putObject(r14, r8, r1)
            r6 = r6 | r10
            r13 = r34
            r9 = r7
            r2 = r15
            r7 = r20
            r1 = r23
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r10 = -1
            r15 = r30
            goto L_0x0020
        L_0x0164:
            r13 = r30
            r19 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x02fc
        L_0x016b:
            r23 = r33
            r15 = r20
            r0 = 2
            r20 = r7
            r7 = r2
            if (r3 != r0) goto L_0x01a0
            r19 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r30
            java.lang.Object r8 = r13.zzD(r14, r15)
            com.google.android.gms.internal.measurement.zzmt r1 = r13.zzB(r15)
            r0 = r8
            r2 = r32
            r3 = r4
            r4 = r34
            r5 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzo(r0, r1, r2, r3, r4, r5)
            r13.zzL(r14, r15, r8)
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x01a0:
            r13 = r30
            r19 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x02fc
        L_0x01a7:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            r0 = 2
            if (r3 != r0) goto L_0x02fc
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r22 & r0
            if (r0 != 0) goto L_0x01c0
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzg(r12, r4, r11)
            goto L_0x01c4
        L_0x01c0:
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzh(r12, r4, r11)
        L_0x01c4:
            java.lang.Object r1 = r11.zzc
            r7.putObject(r14, r8, r1)
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x01d8:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != 0) goto L_0x02fc
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r4, r11)
            long r1 = r11.zzb
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x01f2
            goto L_0x01f4
        L_0x01f2:
            r5 = r16
        L_0x01f4:
            com.google.android.gms.internal.measurement.zznu.zzm(r14, r8, r5)
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x0206:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != r0) goto L_0x02fc
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzb(r12, r4)
            r7.putInt(r14, r8, r0)
            int r0 = r4 + 4
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x022b:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != r5) goto L_0x0257
            long r21 = com.google.android.gms.internal.measurement.zzjo.zzp(r12, r4)
            r0 = r7
            r1 = r31
            r2 = r8
            r8 = r4
            r4 = r21
            r0.putLong(r1, r2, r4)
            int r0 = r8 + 8
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x0257:
            r8 = r4
            goto L_0x02fc
        L_0x025a:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != 0) goto L_0x02fc
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzj(r12, r4, r11)
            int r1 = r11.zza
            r7.putInt(r14, r8, r1)
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x027f:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != 0) goto L_0x02fc
            int r17 = com.google.android.gms.internal.measurement.zzjo.zzm(r12, r4, r11)
            long r4 = r11.zzb
            r0 = r7
            r1 = r31
            r2 = r8
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r0 = r17
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x02aa:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != r0) goto L_0x02fc
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzb(r12, r4)
            float r0 = java.lang.Float.intBitsToFloat(r0)
            com.google.android.gms.internal.measurement.zznu.zzp(r14, r8, r0)
            int r0 = r4 + 4
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x02d3:
            r13 = r30
            r23 = r33
            r19 = r15
            r15 = r20
            r20 = r7
            r7 = r2
            if (r3 != r5) goto L_0x02fc
            long r0 = com.google.android.gms.internal.measurement.zzjo.zzp(r12, r4)
            double r0 = java.lang.Double.longBitsToDouble(r0)
            com.google.android.gms.internal.measurement.zznu.zzo(r14, r8, r0)
            int r0 = r4 + 8
            r6 = r6 | r10
            r9 = r7
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r10 = -1
            r15 = r13
            r13 = r34
            goto L_0x0020
        L_0x02fc:
            r2 = r4
            r29 = r7
            r7 = r20
            r18 = -1
            goto L_0x044f
        L_0x0305:
            r23 = r33
            r22 = r1
            r20 = r7
            r10 = r15
            r7 = r19
            r19 = 1048575(0xfffff, float:1.469367E-39)
            r15 = r2
            r0 = 27
            if (r13 != r0) goto L_0x0366
            r0 = 2
            if (r3 != r0) goto L_0x035a
            java.lang.Object r0 = r7.getObject(r14, r8)
            com.google.android.gms.internal.measurement.zzli r0 = (com.google.android.gms.internal.measurement.zzli) r0
            boolean r1 = r0.zzc()
            if (r1 != 0) goto L_0x0338
            int r1 = r0.size()
            if (r1 != 0) goto L_0x032e
            r1 = 10
            goto L_0x032f
        L_0x032e:
            int r1 = r1 + r1
        L_0x032f:
            com.google.android.gms.internal.measurement.zzli r0 = r0.zzd(r1)
            r7.putObject(r14, r8, r0)
            r5 = r0
            goto L_0x0339
        L_0x0338:
            r5 = r0
        L_0x0339:
            com.google.android.gms.internal.measurement.zzmt r0 = r10.zzB(r15)
            r1 = r17
            r2 = r32
            r3 = r4
            r4 = r34
            r8 = r6
            r6 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zze(r0, r1, r2, r3, r4, r5, r6)
            r13 = r34
            r9 = r7
            r6 = r8
            r2 = r15
            r8 = r19
            r7 = r20
            r1 = r23
            r15 = r10
            r10 = -1
            goto L_0x0020
        L_0x035a:
            r8 = r6
            r14 = r4
            r29 = r7
            r25 = r8
            r26 = r20
            r18 = -1
            goto L_0x040e
        L_0x0366:
            r0 = 49
            if (r13 > r0) goto L_0x03c2
            r1 = r22
            long r1 = (long) r1
            r0 = r30
            r21 = r1
            r1 = r31
            r2 = r32
            r5 = r3
            r3 = r4
            r24 = r4
            r4 = r34
            r33 = r5
            r5 = r17
            r25 = r6
            r6 = r23
            r26 = r20
            r20 = r7
            r7 = r33
            r27 = r8
            r9 = r19
            r8 = r15
            r29 = r20
            r18 = -1
            r9 = r21
            r11 = r13
            r12 = r27
            r14 = r35
            int r0 = r0.zzs(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            r14 = r24
            if (r0 == r14) goto L_0x03bb
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r35
            r2 = r15
            r10 = r18
            r1 = r23
            r6 = r25
            r7 = r26
            r9 = r29
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r15 = r30
            goto L_0x0020
        L_0x03bb:
            r2 = r0
            r6 = r25
            r7 = r26
            goto L_0x044f
        L_0x03c2:
            r33 = r3
            r14 = r4
            r25 = r6
            r29 = r7
            r27 = r8
            r26 = r20
            r1 = r22
            r18 = -1
            r0 = 50
            if (r13 != r0) goto L_0x0414
            r7 = r33
            r0 = 2
            if (r7 != r0) goto L_0x040e
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r14
            r4 = r34
            r5 = r15
            r6 = r27
            r8 = r35
            int r0 = r0.zzq(r1, r2, r3, r4, r5, r6, r8)
            if (r0 == r14) goto L_0x0408
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r35
            r2 = r15
            r10 = r18
            r1 = r23
            r6 = r25
            r7 = r26
            r9 = r29
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r15 = r30
            goto L_0x0020
        L_0x0408:
            r2 = r0
            r6 = r25
            r7 = r26
            goto L_0x044f
        L_0x040e:
            r2 = r14
            r6 = r25
            r7 = r26
            goto L_0x044f
        L_0x0414:
            r7 = r33
            r0 = r30
            r8 = r1
            r1 = r31
            r2 = r32
            r3 = r14
            r4 = r34
            r5 = r17
            r6 = r23
            r9 = r13
            r10 = r27
            r12 = r15
            r13 = r35
            int r0 = r0.zzr(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == r14) goto L_0x044a
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r35
            r2 = r15
            r10 = r18
            r1 = r23
            r6 = r25
            r7 = r26
            r9 = r29
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r15 = r30
            goto L_0x0020
        L_0x044a:
            r2 = r0
            r6 = r25
            r7 = r26
        L_0x044f:
            com.google.android.gms.internal.measurement.zznl r4 = zzd(r31)
            r0 = r17
            r1 = r32
            r3 = r34
            r5 = r35
            int r0 = com.google.android.gms.internal.measurement.zzjo.zzi(r0, r1, r2, r3, r4, r5)
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r35
            r2 = r15
            r10 = r18
            r1 = r23
            r9 = r29
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r15 = r30
            goto L_0x0020
        L_0x0475:
            r25 = r6
            r26 = r7
            r29 = r9
            r1 = 1048575(0xfffff, float:1.469367E-39)
            if (r7 == r1) goto L_0x048a
            long r1 = (long) r7
            r3 = r31
            r6 = r25
            r4 = r29
            r4.putInt(r3, r1, r6)
        L_0x048a:
            r4 = r34
            if (r0 != r4) goto L_0x048f
            return
        L_0x048f:
            com.google.android.gms.internal.measurement.zzll r0 = com.google.android.gms.internal.measurement.zzll.zze()
            throw r0
        L_0x0494:
            r4 = r13
            r3 = r14
            r5 = 0
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r33
            r4 = r34
            r6 = r35
            r0.zzc(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzml.zzh(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzjn):void");
    }

    public final boolean zzj(Object obj, Object obj2) {
        boolean z;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int zzy = zzy(i);
            long j = (long) (zzy & 1048575);
            switch (zzx(zzy)) {
                case 0:
                    if (zzO(obj, obj2, i) && Double.doubleToLongBits(zznu.zza(obj, j)) == Double.doubleToLongBits(zznu.zza(obj2, j))) {
                        continue;
                    }
                case 1:
                    if (zzO(obj, obj2, i) && Float.floatToIntBits(zznu.zzb(obj, j)) == Float.floatToIntBits(zznu.zzb(obj2, j))) {
                        continue;
                    }
                case 2:
                    if (zzO(obj, obj2, i) && zznu.zzd(obj, j) == zznu.zzd(obj2, j)) {
                        continue;
                    }
                case 3:
                    if (zzO(obj, obj2, i) && zznu.zzd(obj, j) == zznu.zzd(obj2, j)) {
                        continue;
                    }
                case 4:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 5:
                    if (zzO(obj, obj2, i) && zznu.zzd(obj, j) == zznu.zzd(obj2, j)) {
                        continue;
                    }
                case 6:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 7:
                    if (zzO(obj, obj2, i) && zznu.zzw(obj, j) == zznu.zzw(obj2, j)) {
                        continue;
                    }
                case 8:
                    if (zzO(obj, obj2, i) && zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        continue;
                    }
                case 9:
                    if (zzO(obj, obj2, i) && zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        continue;
                    }
                case 10:
                    if (zzO(obj, obj2, i) && zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        continue;
                    }
                case 11:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 12:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 13:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 14:
                    if (zzO(obj, obj2, i) && zznu.zzd(obj, j) == zznu.zzd(obj2, j)) {
                        continue;
                    }
                case 15:
                    if (zzO(obj, obj2, i) && zznu.zzc(obj, j) == zznu.zzc(obj2, j)) {
                        continue;
                    }
                case 16:
                    if (zzO(obj, obj2, i) && zznu.zzd(obj, j) == zznu.zzd(obj2, j)) {
                        continue;
                    }
                case 17:
                    if (zzO(obj, obj2, i) && zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        continue;
                    }
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    z = zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j));
                    break;
                case 50:
                    z = zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long zzv = (long) (zzv(i) & 1048575);
                    if (zznu.zzc(obj, zzv) == zznu.zzc(obj2, zzv) && zzmv.zzW(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        continue;
                    }
            }
            if (!z) {
                return false;
            }
        }
        if (!this.zzn.zzd(obj).equals(this.zzn.zzd(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zza(obj);
        this.zzo.zza(obj2);
        throw null;
    }

    public final boolean zzk(Object obj) {
        int i;
        int i2;
        Object obj2 = obj;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i5 < this.zzk) {
            int i6 = this.zzj[i5];
            int i7 = this.zzc[i6];
            int zzy = zzy(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 == i3) {
                i2 = i3;
                i = i4;
            } else if (i9 != 1048575) {
                i = zzb.getInt(obj2, (long) i9);
                i2 = i9;
            } else {
                i = i4;
                i2 = i9;
            }
            if ((268435456 & zzy) != 0 && !zzQ(obj, i6, i2, i, i10)) {
                return false;
            }
            switch (zzx(zzy)) {
                case 9:
                case 17:
                    if (zzQ(obj, i6, i2, i, i10) && !zzR(obj2, zzy, zzB(i6))) {
                        return false;
                    }
                case 27:
                case 49:
                    List list = (List) zznu.zzf(obj2, (long) (zzy & 1048575));
                    if (!list.isEmpty()) {
                        zzmt zzB = zzB(i6);
                        for (int i11 = 0; i11 < list.size(); i11++) {
                            if (!zzB.zzk(list.get(i11))) {
                                return false;
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                case 50:
                    if (((zzmc) zznu.zzf(obj2, (long) (zzy & 1048575))).isEmpty()) {
                        break;
                    } else {
                        zzmb zzmb = (zzmb) zzC(i6);
                        throw null;
                    }
                case 60:
                case 68:
                    if (zzT(obj2, i7, i6) && !zzR(obj2, zzy, zzB(i6))) {
                        return false;
                    }
            }
            i5++;
            i3 = i2;
            i4 = i;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zza(obj2);
        throw null;
    }

    public final void zzi(Object obj, zzoc zzoc) {
        int i;
        int i2;
        Object obj2 = obj;
        zzoc zzoc2 = zzoc;
        int i3 = 0;
        int i4 = 1048575;
        if (this.zzi) {
            if (!this.zzh) {
                int length = this.zzc.length;
                for (int i5 = 0; i5 < length; i5 += 3) {
                    int zzy = zzy(i5);
                    int i6 = this.zzc[i5];
                    switch (zzx(zzy)) {
                        case 0:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzf(i6, zznu.zza(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 1:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzo(i6, zznu.zzb(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 2:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzt(i6, zznu.zzd(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 3:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzJ(i6, zznu.zzd(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 4:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzr(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 5:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzm(i6, zznu.zzd(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 6:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzk(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 7:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzb(i6, zznu.zzw(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 8:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzV(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2);
                                break;
                            }
                        case 9:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzv(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzB(i5));
                                break;
                            }
                        case 10:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzd(i6, (zzka) zznu.zzf(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 11:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzH(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 12:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzi(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 13:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzw(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 14:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzy(i6, zznu.zzd(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 15:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzA(i6, zznu.zzc(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 16:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzC(i6, zznu.zzd(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 17:
                            if (!zzP(obj2, i5)) {
                                break;
                            } else {
                                zzoc2.zzq(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzB(i5));
                                break;
                            }
                        case 18:
                            zzmv.zzG(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 19:
                            zzmv.zzK(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 20:
                            zzmv.zzN(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 21:
                            zzmv.zzV(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 22:
                            zzmv.zzM(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 23:
                            zzmv.zzJ(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 24:
                            zzmv.zzI(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 25:
                            zzmv.zzE(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 26:
                            zzmv.zzT(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2);
                            break;
                        case 27:
                            zzmv.zzO(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, zzB(i5));
                            break;
                        case 28:
                            zzmv.zzF(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2);
                            break;
                        case 29:
                            zzmv.zzU(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 30:
                            zzmv.zzH(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 31:
                            zzmv.zzP(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 32:
                            zzmv.zzQ(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 33:
                            zzmv.zzR(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 34:
                            zzmv.zzS(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, false);
                            break;
                        case 35:
                            zzmv.zzG(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 36:
                            zzmv.zzK(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 37:
                            zzmv.zzN(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 38:
                            zzmv.zzV(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 39:
                            zzmv.zzM(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 40:
                            zzmv.zzJ(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 41:
                            zzmv.zzI(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 42:
                            zzmv.zzE(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 43:
                            zzmv.zzU(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 44:
                            zzmv.zzH(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 45:
                            zzmv.zzP(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 46:
                            zzmv.zzQ(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 47:
                            zzmv.zzR(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 48:
                            zzmv.zzS(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, true);
                            break;
                        case 49:
                            zzmv.zzL(i6, (List) zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2, zzB(i5));
                            break;
                        case 50:
                            zzN(zzoc2, i6, zznu.zzf(obj2, (long) (zzy & 1048575)), i5);
                            break;
                        case 51:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzf(i6, zzm(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 52:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzo(i6, zzn(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 53:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzt(i6, zzz(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 54:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzJ(i6, zzz(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 55:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzr(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 56:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzm(i6, zzz(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 57:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzk(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 58:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzb(i6, zzU(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 59:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzV(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzoc2);
                                break;
                            }
                        case 60:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzv(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzB(i5));
                                break;
                            }
                        case 61:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzd(i6, (zzka) zznu.zzf(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 62:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzH(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 63:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzi(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 64:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzw(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 65:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzy(i6, zzz(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 66:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzA(i6, zzp(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 67:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzC(i6, zzz(obj2, (long) (zzy & 1048575)));
                                break;
                            }
                        case 68:
                            if (!zzT(obj2, i6, i5)) {
                                break;
                            } else {
                                zzoc2.zzq(i6, zznu.zzf(obj2, (long) (zzy & 1048575)), zzB(i5));
                                break;
                            }
                    }
                }
                zznk zznk = this.zzn;
                zznk.zzi(zznk.zzd(obj2), zzoc2);
                return;
            }
            this.zzo.zza(obj2);
            throw null;
        } else if (!this.zzh) {
            int length2 = this.zzc.length;
            Unsafe unsafe = zzb;
            int i7 = 0;
            int i8 = 0;
            int i9 = 1048575;
            while (i7 < length2) {
                int zzy2 = zzy(i7);
                int[] iArr = this.zzc;
                int i10 = iArr[i7];
                int zzx = zzx(zzy2);
                if (zzx <= 17) {
                    int i11 = iArr[i7 + 2];
                    int i12 = i11 & i4;
                    if (i12 != i9) {
                        i8 = unsafe.getInt(obj2, (long) i12);
                        i9 = i12;
                    }
                    i = 1 << (i11 >>> 20);
                } else {
                    i = i3;
                }
                long j = (long) (zzy2 & i4);
                switch (zzx) {
                    case 0:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzf(i10, zznu.zza(obj2, j));
                            break;
                        }
                    case 1:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzo(i10, zznu.zzb(obj2, j));
                            break;
                        }
                    case 2:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzt(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 3:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzJ(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 4:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzr(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 5:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzm(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 6:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzk(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 7:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzb(i10, zznu.zzw(obj2, j));
                            break;
                        }
                    case 8:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzV(i10, unsafe.getObject(obj2, j), zzoc2);
                            break;
                        }
                    case 9:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzv(i10, unsafe.getObject(obj2, j), zzB(i7));
                            break;
                        }
                    case 10:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzd(i10, (zzka) unsafe.getObject(obj2, j));
                            break;
                        }
                    case 11:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzH(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 12:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzi(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 13:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzw(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 14:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzy(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 15:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzA(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 16:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzC(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 17:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzoc2.zzq(i10, unsafe.getObject(obj2, j), zzB(i7));
                            break;
                        }
                    case 18:
                        i2 = 0;
                        zzmv.zzG(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 19:
                        i2 = 0;
                        zzmv.zzK(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 20:
                        i2 = 0;
                        zzmv.zzN(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 21:
                        i2 = 0;
                        zzmv.zzV(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 22:
                        i2 = 0;
                        zzmv.zzM(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 23:
                        i2 = 0;
                        zzmv.zzJ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 24:
                        i2 = 0;
                        zzmv.zzI(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 25:
                        i2 = 0;
                        zzmv.zzE(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        break;
                    case 26:
                        zzmv.zzT(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2);
                        i2 = 0;
                        break;
                    case 27:
                        zzmv.zzO(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, zzB(i7));
                        i2 = 0;
                        break;
                    case 28:
                        zzmv.zzF(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2);
                        i2 = 0;
                        break;
                    case 29:
                        zzmv.zzU(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 30:
                        zzmv.zzH(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 31:
                        zzmv.zzP(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 32:
                        zzmv.zzQ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 33:
                        zzmv.zzR(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 34:
                        zzmv.zzS(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, false);
                        i2 = 0;
                        break;
                    case 35:
                        zzmv.zzG(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 36:
                        zzmv.zzK(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 37:
                        zzmv.zzN(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 38:
                        zzmv.zzV(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 39:
                        zzmv.zzM(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 40:
                        zzmv.zzJ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 41:
                        zzmv.zzI(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 42:
                        zzmv.zzE(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 43:
                        zzmv.zzU(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 44:
                        zzmv.zzH(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 45:
                        zzmv.zzP(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 46:
                        zzmv.zzQ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 47:
                        zzmv.zzR(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 48:
                        zzmv.zzS(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, true);
                        i2 = 0;
                        break;
                    case 49:
                        zzmv.zzL(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzoc2, zzB(i7));
                        i2 = 0;
                        break;
                    case 50:
                        zzN(zzoc2, i10, unsafe.getObject(obj2, j), i7);
                        i2 = 0;
                        break;
                    case 51:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzf(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 52:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzo(i10, zzn(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 53:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzt(i10, zzz(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 54:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzJ(i10, zzz(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 55:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzr(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 56:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzm(i10, zzz(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 57:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzk(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 58:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzb(i10, zzU(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 59:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzV(i10, unsafe.getObject(obj2, j), zzoc2);
                            i2 = 0;
                            break;
                        }
                    case 60:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzv(i10, unsafe.getObject(obj2, j), zzB(i7));
                            i2 = 0;
                            break;
                        }
                    case 61:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzd(i10, (zzka) unsafe.getObject(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 62:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzH(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 63:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzi(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 64:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzw(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 65:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzy(i10, zzz(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 66:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzA(i10, zzp(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 67:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzC(i10, zzz(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 68:
                        if (!zzT(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzoc2.zzq(i10, unsafe.getObject(obj2, j), zzB(i7));
                            i2 = 0;
                            break;
                        }
                    default:
                        i2 = 0;
                        break;
                }
                i7 += 3;
                i3 = i2;
                i4 = 1048575;
            }
            zznk zznk2 = this.zzn;
            zznk2.zzi(zznk2.zzd(obj2), zzoc2);
        } else {
            this.zzo.zza(obj2);
            throw null;
        }
    }
}
