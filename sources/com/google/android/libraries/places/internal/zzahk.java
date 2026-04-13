package com.google.android.libraries.places.internal;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;
import sun.misc.Unsafe;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahk<T> implements zzahs<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzait.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzahh zze;
    private final boolean zzf;
    private final boolean zzg;
    private final int[] zzh;
    private final int zzi;
    private final zzagv zzj;
    private final zzaij zzk;
    private final zzafp zzl;
    private final zzahm zzm;
    private final zzahc zzn;

    private zzahk(int[] iArr, Object[] objArr, int i, int i2, zzahh zzahh, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzahm zzahm, zzagv zzagv, zzaij zzaij, zzafp zzafp, zzahc zzahc) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzg = z;
        boolean z3 = false;
        if (zzafp != null && zzafp.zzc(zzahh)) {
            z3 = true;
        }
        this.zzf = z3;
        this.zzh = iArr2;
        this.zzi = i3;
        this.zzm = zzahm;
        this.zzj = zzagv;
        this.zzk = zzaij;
        this.zzl = zzafp;
        this.zze = zzahh;
        this.zzn = zzahc;
    }

    private final boolean zzA(Object obj, int i) {
        int zzn2 = zzn(i);
        long j = (long) (zzn2 & 1048575);
        if (j == 1048575) {
            int zzp = zzp(i);
            long j2 = (long) (zzp & 1048575);
            switch (zzo(zzp)) {
                case 0:
                    return Double.doubleToRawLongBits(zzait.zza(obj, j2)) != 0;
                case 1:
                    return Float.floatToRawIntBits(zzait.zzb(obj, j2)) != 0;
                case 2:
                    return zzait.zzd(obj, j2) != 0;
                case 3:
                    return zzait.zzd(obj, j2) != 0;
                case 4:
                    return zzait.zzc(obj, j2) != 0;
                case 5:
                    return zzait.zzd(obj, j2) != 0;
                case 6:
                    return zzait.zzc(obj, j2) != 0;
                case 7:
                    return zzait.zzw(obj, j2);
                case 8:
                    Object zzf2 = zzait.zzf(obj, j2);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzafe) {
                        return !zzafe.zzb.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzait.zzf(obj, j2) != null;
                case 10:
                    return !zzafe.zzb.equals(zzait.zzf(obj, j2));
                case 11:
                    return zzait.zzc(obj, j2) != 0;
                case 12:
                    return zzait.zzc(obj, j2) != 0;
                case 13:
                    return zzait.zzc(obj, j2) != 0;
                case 14:
                    return zzait.zzd(obj, j2) != 0;
                case 15:
                    return zzait.zzc(obj, j2) != 0;
                case 16:
                    return zzait.zzd(obj, j2) != 0;
                case 17:
                    return zzait.zzf(obj, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return (zzait.zzc(obj, j) & (1 << (zzn2 >>> 20))) != 0;
        }
    }

    private final boolean zzB(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzA(obj, i);
        }
        return (i3 & i4) != 0;
    }

    private static boolean zzC(Object obj, int i, zzahs zzahs) {
        return zzahs.zzh(zzait.zzf(obj, (long) (i & 1048575)));
    }

    private static boolean zzD(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzafz) {
            return ((zzafz) obj).zzL();
        }
        return true;
    }

    private final boolean zzE(Object obj, int i, int i2) {
        return zzait.zzc(obj, (long) (zzn(i2) & 1048575)) == i;
    }

    private static boolean zzF(Object obj, long j) {
        return ((Boolean) zzait.zzf(obj, j)).booleanValue();
    }

    private static final void zzG(int i, Object obj, zzaja zzaja) {
        if (obj instanceof String) {
            zzaja.zzD(i, (String) obj);
        } else {
            zzaja.zzd(i, (zzafe) obj);
        }
    }

    static zzahk zzi(Class cls, zzahe zzahe, zzahm zzahm, zzagv zzagv, zzaij zzaij, zzafp zzafp, zzahc zzahc) {
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
        zzahe zzahe2 = zzahe;
        if (zzahe2 instanceof zzahr) {
            zzahr zzahr = (zzahr) zzahe2;
            int zzc2 = zzahr.zzc();
            String zzd2 = zzahr.zzd();
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
            Object[] zze2 = zzahr.zze();
            Class<?> cls2 = zzahr.zza().getClass();
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
                            field2 = zzt(cls2, (String) obj);
                            zze2[i67] = field2;
                        }
                        int objectFieldOffset = (int) unsafe.objectFieldOffset(field2);
                        int i68 = i67 + 1;
                        Object obj2 = zze2[i68];
                        if (obj2 instanceof Field) {
                            field3 = (Field) obj2;
                        } else {
                            field3 = zzt(cls2, (String) obj2);
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
                        Field zzt = zzt(cls2, (String) zze2[i2]);
                        if (c20 == 9 || c20 == 17) {
                            int i70 = i51 / 3;
                            objArr2[i70 + i70 + 1] = zzt.getType();
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
                        i7 = (int) unsafe.objectFieldOffset(zzt);
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
                                field = zzt(cls2, (String) obj3);
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
                    return new zzahk(iArr3, objArr2, c5, c2, zzahr.zza(), z, false, iArr, c, i47, zzahm, zzagv, zzaij, zzafp, zzahc);
                }
            }
        } else {
            zzaig zzaig = (zzaig) zzahe2;
            throw null;
        }
    }

    private static double zzj(Object obj, long j) {
        return ((Double) zzait.zzf(obj, j)).doubleValue();
    }

    private static float zzk(Object obj, long j) {
        return ((Float) zzait.zzf(obj, j)).floatValue();
    }

    private final int zzl(Object obj) {
        int i;
        Object obj2 = obj;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < this.zzc.length) {
            int zzp = zzp(i4);
            int[] iArr = this.zzc;
            int i7 = iArr[i4];
            int zzo = zzo(zzp);
            if (zzo <= 17) {
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
            long j = (long) (zzp & i2);
            switch (zzo) {
                case 0:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 1:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 2:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy(unsafe.getLong(obj2, j));
                        break;
                    }
                case 3:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy(unsafe.getLong(obj2, j));
                        break;
                    }
                case 4:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzu(unsafe.getInt(obj2, j));
                        break;
                    }
                case 5:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 6:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 7:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 1;
                        break;
                    }
                case 8:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(obj2, j);
                        if (!(object instanceof zzafe)) {
                            i5 += zzafm.zzx(i7 << 3) + zzafm.zzw((String) object);
                            break;
                        } else {
                            int i11 = zzafm.zzb;
                            int zzd2 = ((zzafe) object).zzd();
                            i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzd2) + zzd2;
                            break;
                        }
                    }
                case 9:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzahu.zzn(i7, unsafe.getObject(obj2, j), zzr(i4));
                        break;
                    }
                case 10:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        int i12 = zzafm.zzb;
                        int zzd3 = ((zzafe) unsafe.getObject(obj2, j)).zzd();
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzd3) + zzd3;
                        break;
                    }
                case 11:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(unsafe.getInt(obj2, j));
                        break;
                    }
                case 12:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzu(unsafe.getInt(obj2, j));
                        break;
                    }
                case 13:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 14:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 15:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        int i13 = unsafe.getInt(obj2, j);
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx((i13 >> 31) ^ (i13 + i13));
                        break;
                    }
                case 16:
                    if ((i & i6) == 0) {
                        break;
                    } else {
                        long j2 = unsafe.getLong(obj2, j);
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy((j2 >> 63) ^ (j2 + j2));
                        break;
                    }
                case 17:
                    if ((i6 & i) == 0) {
                        break;
                    } else {
                        i5 += zzafm.zzt(i7, (zzahh) unsafe.getObject(obj2, j), zzr(i4));
                        break;
                    }
                case 18:
                    i5 += zzahu.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 19:
                    i5 += zzahu.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 20:
                    i5 += zzahu.zzl(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 21:
                    i5 += zzahu.zzw(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 22:
                    i5 += zzahu.zzj(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 23:
                    i5 += zzahu.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 24:
                    i5 += zzahu.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 25:
                    i5 += zzahu.zza(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 26:
                    i5 += zzahu.zzt(i7, (List) unsafe.getObject(obj2, j));
                    break;
                case 27:
                    i5 += zzahu.zzo(i7, (List) unsafe.getObject(obj2, j), zzr(i4));
                    break;
                case 28:
                    i5 += zzahu.zzb(i7, (List) unsafe.getObject(obj2, j));
                    break;
                case 29:
                    i5 += zzahu.zzu(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 30:
                    i5 += zzahu.zzc(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 31:
                    i5 += zzahu.zze(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 32:
                    i5 += zzahu.zzg(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 33:
                    i5 += zzahu.zzp(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 34:
                    i5 += zzahu.zzr(i7, (List) unsafe.getObject(obj2, j), false);
                    break;
                case 35:
                    int zzh2 = zzahu.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh2 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzh2) + zzh2;
                    }
                    break;
                case 36:
                    int zzf2 = zzahu.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf2 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzf2) + zzf2;
                    }
                    break;
                case 37:
                    int zzm2 = zzahu.zzm((List) unsafe.getObject(obj2, j));
                    if (zzm2 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzm2) + zzm2;
                    }
                    break;
                case 38:
                    int zzx = zzahu.zzx((List) unsafe.getObject(obj2, j));
                    if (zzx > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzx) + zzx;
                    }
                    break;
                case 39:
                    int zzk2 = zzahu.zzk((List) unsafe.getObject(obj2, j));
                    if (zzk2 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzk2) + zzk2;
                    }
                    break;
                case 40:
                    int zzh3 = zzahu.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh3 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzh3) + zzh3;
                    }
                    break;
                case 41:
                    int zzf3 = zzahu.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf3 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzf3) + zzf3;
                    }
                    break;
                case 42:
                    int i14 = zzahu.zza;
                    int size = ((List) unsafe.getObject(obj2, j)).size();
                    if (size > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(size) + size;
                    }
                    break;
                case 43:
                    int zzv = zzahu.zzv((List) unsafe.getObject(obj2, j));
                    if (zzv > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzv) + zzv;
                    }
                    break;
                case 44:
                    int zzd4 = zzahu.zzd((List) unsafe.getObject(obj2, j));
                    if (zzd4 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzd4) + zzd4;
                    }
                    break;
                case 45:
                    int zzf4 = zzahu.zzf((List) unsafe.getObject(obj2, j));
                    if (zzf4 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzf4) + zzf4;
                    }
                    break;
                case 46:
                    int zzh4 = zzahu.zzh((List) unsafe.getObject(obj2, j));
                    if (zzh4 > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzh4) + zzh4;
                    }
                    break;
                case 47:
                    int zzq = zzahu.zzq((List) unsafe.getObject(obj2, j));
                    if (zzq > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzq) + zzq;
                    }
                    break;
                case 48:
                    int zzs = zzahu.zzs((List) unsafe.getObject(obj2, j));
                    if (zzs > 0) {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzs) + zzs;
                    }
                    break;
                case 49:
                    i5 += zzahu.zzi(i7, (List) unsafe.getObject(obj2, j), zzr(i4));
                    break;
                case 50:
                    zzahc.zza(i7, unsafe.getObject(obj2, j), zzs(i4));
                    break;
                case 51:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 52:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 53:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy(zzq(obj2, j));
                        break;
                    }
                case 54:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy(zzq(obj2, j));
                        break;
                    }
                case 55:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzu(zzm(obj2, j));
                        break;
                    }
                case 56:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 57:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 58:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 1;
                        break;
                    }
                case 59:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        Object object2 = unsafe.getObject(obj2, j);
                        if (!(object2 instanceof zzafe)) {
                            i5 += zzafm.zzx(i7 << 3) + zzafm.zzw((String) object2);
                            break;
                        } else {
                            int i15 = zzafm.zzb;
                            int zzd5 = ((zzafe) object2).zzd();
                            i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzd5) + zzd5;
                            break;
                        }
                    }
                case 60:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzahu.zzn(i7, unsafe.getObject(obj2, j), zzr(i4));
                        break;
                    }
                case 61:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        int i16 = zzafm.zzb;
                        int zzd6 = ((zzafe) unsafe.getObject(obj2, j)).zzd();
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzd6) + zzd6;
                        break;
                    }
                case 62:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx(zzm(obj2, j));
                        break;
                    }
                case 63:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzu(zzm(obj2, j));
                        break;
                    }
                case 64:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 4;
                        break;
                    }
                case 65:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzx(i7 << 3) + 8;
                        break;
                    }
                case 66:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        int zzm3 = zzm(obj2, j);
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzx((zzm3 >> 31) ^ (zzm3 + zzm3));
                        break;
                    }
                case 67:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        long zzq2 = zzq(obj2, j);
                        i5 += zzafm.zzx(i7 << 3) + zzafm.zzy((zzq2 >> 63) ^ (zzq2 + zzq2));
                        break;
                    }
                case 68:
                    if (!zzE(obj2, i7, i4)) {
                        break;
                    } else {
                        i5 += zzafm.zzt(i7, (zzahh) unsafe.getObject(obj2, j), zzr(i4));
                        break;
                    }
            }
            i4 += 3;
            i2 = 1048575;
        }
        zzaij zzaij = this.zzk;
        int zza2 = i5 + zzaij.zza(zzaij.zzc(obj2));
        if (!this.zzf) {
            return zza2;
        }
        this.zzl.zza(obj2);
        throw null;
    }

    private static int zzm(Object obj, long j) {
        return ((Integer) zzait.zzf(obj, j)).intValue();
    }

    private final int zzn(int i) {
        return this.zzc[i + 2];
    }

    private static int zzo(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzp(int i) {
        return this.zzc[i + 1];
    }

    private static long zzq(Object obj, long j) {
        return ((Long) zzait.zzf(obj, j)).longValue();
    }

    private final zzahs zzr(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzahs zzahs = (zzahs) this.zzd[i3];
        if (zzahs != null) {
            return zzahs;
        }
        zzahs zzb2 = zzahp.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzb2;
        return zzb2;
    }

    private final Object zzs(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzt(Class cls, String str) {
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

    private final void zzu(Object obj, Object obj2, int i) {
        if (zzA(obj2, i)) {
            Unsafe unsafe = zzb;
            long zzp = (long) (zzp(i) & 1048575);
            Object object = unsafe.getObject(obj2, zzp);
            if (object != null) {
                zzahs zzr = zzr(i);
                if (!zzA(obj, i)) {
                    if (!zzD(object)) {
                        unsafe.putObject(obj, zzp, object);
                    } else {
                        Object zzc2 = zzr.zzc();
                        zzr.zze(zzc2, object);
                        unsafe.putObject(obj, zzp, zzc2);
                    }
                    zzw(obj, i);
                    return;
                }
                Object object2 = unsafe.getObject(obj, zzp);
                if (!zzD(object2)) {
                    Object zzc3 = zzr.zzc();
                    zzr.zze(zzc3, object2);
                    unsafe.putObject(obj, zzp, zzc3);
                    object2 = zzc3;
                }
                zzr.zze(object2, object);
                return;
            }
            int i2 = this.zzc[i];
            String obj3 = obj2.toString();
            throw new IllegalStateException("Source subfield " + i2 + " is present but null: " + obj3);
        }
    }

    private final void zzv(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzE(obj2, i2, i)) {
            Unsafe unsafe = zzb;
            long zzp = (long) (zzp(i) & 1048575);
            Object object = unsafe.getObject(obj2, zzp);
            if (object != null) {
                zzahs zzr = zzr(i);
                if (!zzE(obj, i2, i)) {
                    if (!zzD(object)) {
                        unsafe.putObject(obj, zzp, object);
                    } else {
                        Object zzc2 = zzr.zzc();
                        zzr.zze(zzc2, object);
                        unsafe.putObject(obj, zzp, zzc2);
                    }
                    zzx(obj, i2, i);
                    return;
                }
                Object object2 = unsafe.getObject(obj, zzp);
                if (!zzD(object2)) {
                    Object zzc3 = zzr.zzc();
                    zzr.zze(zzc3, object2);
                    unsafe.putObject(obj, zzp, zzc3);
                    object2 = zzc3;
                }
                zzr.zze(object2, object);
                return;
            }
            int i3 = this.zzc[i];
            String obj3 = obj2.toString();
            throw new IllegalStateException("Source subfield " + i3 + " is present but null: " + obj3);
        }
    }

    private final void zzw(Object obj, int i) {
        int zzn2 = zzn(i);
        long j = (long) (1048575 & zzn2);
        if (j != 1048575) {
            zzait.zzq(obj, j, (1 << (zzn2 >>> 20)) | zzait.zzc(obj, j));
        }
    }

    private final void zzx(Object obj, int i, int i2) {
        zzait.zzq(obj, (long) (zzn(i2) & 1048575), i);
    }

    private final void zzy(zzaja zzaja, int i, Object obj, int i2) {
        if (obj != null) {
            zzaha zzaha = (zzaha) zzs(i2);
            throw null;
        }
    }

    private final boolean zzz(Object obj, Object obj2, int i) {
        return zzA(obj, i) == zzA(obj2, i);
    }

    public final int zza(Object obj) {
        if (!this.zzg) {
            return zzl(obj);
        }
        Unsafe unsafe = zzb;
        int i = 0;
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int zzp = zzp(i2);
            int zzo = zzo(zzp);
            int i3 = this.zzc[i2];
            int i4 = zzp & 1048575;
            if (zzo >= zzafu.DOUBLE_LIST_PACKED.zza() && zzo <= zzafu.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i2 + 2];
            }
            long j = (long) i4;
            switch (zzo) {
                case 0:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 1:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 2:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy(zzait.zzd(obj, j));
                        break;
                    }
                case 3:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy(zzait.zzd(obj, j));
                        break;
                    }
                case 4:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzu(zzait.zzc(obj, j));
                        break;
                    }
                case 5:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 6:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 7:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 1;
                        break;
                    }
                case 8:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        Object zzf2 = zzait.zzf(obj, j);
                        if (!(zzf2 instanceof zzafe)) {
                            i += zzafm.zzx(i3 << 3) + zzafm.zzw((String) zzf2);
                            break;
                        } else {
                            int i6 = i3 << 3;
                            int i7 = zzafm.zzb;
                            int zzd2 = ((zzafe) zzf2).zzd();
                            i += zzafm.zzx(i6) + zzafm.zzx(zzd2) + zzd2;
                            break;
                        }
                    }
                case 9:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzahu.zzn(i3, zzait.zzf(obj, j), zzr(i2));
                        break;
                    }
                case 10:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        int i8 = i3 << 3;
                        int i9 = zzafm.zzb;
                        int zzd3 = ((zzafe) zzait.zzf(obj, j)).zzd();
                        i += zzafm.zzx(i8) + zzafm.zzx(zzd3) + zzd3;
                        break;
                    }
                case 11:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzait.zzc(obj, j));
                        break;
                    }
                case 12:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzu(zzait.zzc(obj, j));
                        break;
                    }
                case 13:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 14:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 15:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        int zzc2 = zzait.zzc(obj, j);
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx((zzc2 >> 31) ^ (zzc2 + zzc2));
                        break;
                    }
                case 16:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        long zzd4 = zzait.zzd(obj, j);
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy((zzd4 + zzd4) ^ (zzd4 >> 63));
                        break;
                    }
                case 17:
                    if (!zzA(obj, i2)) {
                        break;
                    } else {
                        i += zzafm.zzt(i3, (zzahh) zzait.zzf(obj, j), zzr(i2));
                        break;
                    }
                case 18:
                    i += zzahu.zzg(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 19:
                    i += zzahu.zze(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 20:
                    i += zzahu.zzl(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 21:
                    i += zzahu.zzw(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 22:
                    i += zzahu.zzj(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 23:
                    i += zzahu.zzg(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 24:
                    i += zzahu.zze(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 25:
                    i += zzahu.zza(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 26:
                    i += zzahu.zzt(i3, (List) zzait.zzf(obj, j));
                    break;
                case 27:
                    i += zzahu.zzo(i3, (List) zzait.zzf(obj, j), zzr(i2));
                    break;
                case 28:
                    i += zzahu.zzb(i3, (List) zzait.zzf(obj, j));
                    break;
                case 29:
                    i += zzahu.zzu(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 30:
                    i += zzahu.zzc(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 31:
                    i += zzahu.zze(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 32:
                    i += zzahu.zzg(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 33:
                    i += zzahu.zzp(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 34:
                    i += zzahu.zzr(i3, (List) zzait.zzf(obj, j), false);
                    break;
                case 35:
                    int zzh2 = zzahu.zzh((List) unsafe.getObject(obj, j));
                    if (zzh2 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzh2) + zzh2;
                        break;
                    }
                case 36:
                    int zzf3 = zzahu.zzf((List) unsafe.getObject(obj, j));
                    if (zzf3 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzf3) + zzf3;
                        break;
                    }
                case 37:
                    int zzm2 = zzahu.zzm((List) unsafe.getObject(obj, j));
                    if (zzm2 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzm2) + zzm2;
                        break;
                    }
                case 38:
                    int zzx = zzahu.zzx((List) unsafe.getObject(obj, j));
                    if (zzx <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzx) + zzx;
                        break;
                    }
                case 39:
                    int zzk2 = zzahu.zzk((List) unsafe.getObject(obj, j));
                    if (zzk2 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzk2) + zzk2;
                        break;
                    }
                case 40:
                    int zzh3 = zzahu.zzh((List) unsafe.getObject(obj, j));
                    if (zzh3 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzh3) + zzh3;
                        break;
                    }
                case 41:
                    int zzf4 = zzahu.zzf((List) unsafe.getObject(obj, j));
                    if (zzf4 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzf4) + zzf4;
                        break;
                    }
                case 42:
                    int i10 = zzahu.zza;
                    int size = ((List) unsafe.getObject(obj, j)).size();
                    if (size <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(size) + size;
                        break;
                    }
                case 43:
                    int zzv = zzahu.zzv((List) unsafe.getObject(obj, j));
                    if (zzv <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzv) + zzv;
                        break;
                    }
                case 44:
                    int zzd5 = zzahu.zzd((List) unsafe.getObject(obj, j));
                    if (zzd5 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzd5) + zzd5;
                        break;
                    }
                case 45:
                    int zzf5 = zzahu.zzf((List) unsafe.getObject(obj, j));
                    if (zzf5 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzf5) + zzf5;
                        break;
                    }
                case 46:
                    int zzh4 = zzahu.zzh((List) unsafe.getObject(obj, j));
                    if (zzh4 <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzh4) + zzh4;
                        break;
                    }
                case 47:
                    int zzq = zzahu.zzq((List) unsafe.getObject(obj, j));
                    if (zzq <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzq) + zzq;
                        break;
                    }
                case 48:
                    int zzs = zzahu.zzs((List) unsafe.getObject(obj, j));
                    if (zzs <= 0) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzs) + zzs;
                        break;
                    }
                case 49:
                    i += zzahu.zzi(i3, (List) zzait.zzf(obj, j), zzr(i2));
                    break;
                case 50:
                    zzahc.zza(i3, zzait.zzf(obj, j), zzs(i2));
                    break;
                case 51:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 52:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 53:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy(zzq(obj, j));
                        break;
                    }
                case 54:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy(zzq(obj, j));
                        break;
                    }
                case 55:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzu(zzm(obj, j));
                        break;
                    }
                case 56:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 57:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 58:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 1;
                        break;
                    }
                case 59:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        Object zzf6 = zzait.zzf(obj, j);
                        if (!(zzf6 instanceof zzafe)) {
                            i += zzafm.zzx(i3 << 3) + zzafm.zzw((String) zzf6);
                            break;
                        } else {
                            int i11 = i3 << 3;
                            int i12 = zzafm.zzb;
                            int zzd6 = ((zzafe) zzf6).zzd();
                            i += zzafm.zzx(i11) + zzafm.zzx(zzd6) + zzd6;
                            break;
                        }
                    }
                case 60:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzahu.zzn(i3, zzait.zzf(obj, j), zzr(i2));
                        break;
                    }
                case 61:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        int i13 = i3 << 3;
                        int i14 = zzafm.zzb;
                        int zzd7 = ((zzafe) zzait.zzf(obj, j)).zzd();
                        i += zzafm.zzx(i13) + zzafm.zzx(zzd7) + zzd7;
                        break;
                    }
                case 62:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx(zzm(obj, j));
                        break;
                    }
                case 63:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + zzafm.zzu(zzm(obj, j));
                        break;
                    }
                case 64:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 4;
                        break;
                    }
                case 65:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzx(i3 << 3) + 8;
                        break;
                    }
                case 66:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        int zzm3 = zzm(obj, j);
                        i += zzafm.zzx(i3 << 3) + zzafm.zzx((zzm3 >> 31) ^ (zzm3 + zzm3));
                        break;
                    }
                case 67:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq2 = zzq(obj, j);
                        i += zzafm.zzx(i3 << 3) + zzafm.zzy((zzq2 + zzq2) ^ (zzq2 >> 63));
                        break;
                    }
                case 68:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i += zzafm.zzt(i3, (zzahh) zzait.zzf(obj, j), zzr(i2));
                        break;
                    }
            }
        }
        zzaij zzaij = this.zzk;
        return i + zzaij.zza(zzaij.zzc(obj));
    }

    public final int zzb(Object obj) {
        int length = this.zzc.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int zzp = zzp(i2);
            int i3 = this.zzc[i2];
            long j = (long) (1048575 & zzp);
            int i4 = 37;
            switch (zzo(zzp)) {
                case 0:
                    long doubleToLongBits = Double.doubleToLongBits(zzait.zza(obj, j));
                    byte[] bArr = zzagi.zzd;
                    i = (i * 53) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zzait.zzb(obj, j));
                    break;
                case 2:
                    long zzd2 = zzait.zzd(obj, j);
                    byte[] bArr2 = zzagi.zzd;
                    i = (i * 53) + ((int) (zzd2 ^ (zzd2 >>> 32)));
                    break;
                case 3:
                    long zzd3 = zzait.zzd(obj, j);
                    byte[] bArr3 = zzagi.zzd;
                    i = (i * 53) + ((int) (zzd3 ^ (zzd3 >>> 32)));
                    break;
                case 4:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 5:
                    long zzd4 = zzait.zzd(obj, j);
                    byte[] bArr4 = zzagi.zzd;
                    i = (i * 53) + ((int) (zzd4 ^ (zzd4 >>> 32)));
                    break;
                case 6:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 7:
                    i = (i * 53) + zzagi.zza(zzait.zzw(obj, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zzait.zzf(obj, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zzait.zzf(obj, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zzait.zzf(obj, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 12:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 13:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 14:
                    long zzd5 = zzait.zzd(obj, j);
                    byte[] bArr5 = zzagi.zzd;
                    i = (i * 53) + ((int) (zzd5 ^ (zzd5 >>> 32)));
                    break;
                case 15:
                    i = (i * 53) + zzait.zzc(obj, j);
                    break;
                case 16:
                    long zzd6 = zzait.zzd(obj, j);
                    byte[] bArr6 = zzagi.zzd;
                    i = (i * 53) + ((int) (zzd6 ^ (zzd6 >>> 32)));
                    break;
                case 17:
                    Object zzf3 = zzait.zzf(obj, j);
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
                    i = (i * 53) + zzait.zzf(obj, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zzait.zzf(obj, j).hashCode();
                    break;
                case 51:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long doubleToLongBits2 = Double.doubleToLongBits(zzj(obj, j));
                        byte[] bArr7 = zzagi.zzd;
                        i = (i * 53) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
                        break;
                    }
                case 52:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzk(obj, j));
                        break;
                    }
                case 53:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq = zzq(obj, j);
                        byte[] bArr8 = zzagi.zzd;
                        i = (i * 53) + ((int) (zzq ^ (zzq >>> 32)));
                        break;
                    }
                case 54:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq2 = zzq(obj, j);
                        byte[] bArr9 = zzagi.zzd;
                        i = (i * 53) + ((int) (zzq2 ^ (zzq2 >>> 32)));
                        break;
                    }
                case 55:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 56:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq3 = zzq(obj, j);
                        byte[] bArr10 = zzagi.zzd;
                        i = (i * 53) + ((int) (zzq3 ^ (zzq3 >>> 32)));
                        break;
                    }
                case 57:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 58:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzagi.zza(zzF(obj, j));
                        break;
                    }
                case 59:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zzait.zzf(obj, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzait.zzf(obj, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzait.zzf(obj, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 63:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 64:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 65:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq4 = zzq(obj, j);
                        byte[] bArr11 = zzagi.zzd;
                        i = (i * 53) + ((int) (zzq4 ^ (zzq4 >>> 32)));
                        break;
                    }
                case 66:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzm(obj, j);
                        break;
                    }
                case 67:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        long zzq5 = zzq(obj, j);
                        byte[] bArr12 = zzagi.zzd;
                        i = (i * 53) + ((int) (zzq5 ^ (zzq5 >>> 32)));
                        break;
                    }
                case 68:
                    if (!zzE(obj, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzait.zzf(obj, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzk.zzc(obj).hashCode();
        if (!this.zzf) {
            return hashCode;
        }
        this.zzl.zza(obj);
        throw null;
    }

    public final Object zzc() {
        return ((zzafz) this.zze).zzy();
    }

    public final void zzd(Object obj) {
        if (zzD(obj)) {
            if (obj instanceof zzafz) {
                zzafz zzafz = (zzafz) obj;
                zzafz.zzJ(Integer.MAX_VALUE);
                zzafz.zza = 0;
                zzafz.zzH();
            }
            int length = this.zzc.length;
            for (int i = 0; i < length; i += 3) {
                int zzp = zzp(i);
                long j = (long) (1048575 & zzp);
                switch (zzo(zzp)) {
                    case 9:
                    case 17:
                        if (!zzA(obj, i)) {
                            break;
                        } else {
                            zzr(i).zzd(zzb.getObject(obj, j));
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
                        this.zzj.zza(obj, j);
                        break;
                    case 50:
                        Unsafe unsafe = zzb;
                        Object object = unsafe.getObject(obj, j);
                        if (object == null) {
                            break;
                        } else {
                            ((zzahb) object).zzb();
                            unsafe.putObject(obj, j, object);
                            break;
                        }
                    case 60:
                    case 68:
                        if (!zzE(obj, this.zzc[i], i)) {
                            break;
                        } else {
                            zzr(i).zzd(zzb.getObject(obj, j));
                            break;
                        }
                }
            }
            this.zzk.zze(obj);
            if (this.zzf) {
                this.zzl.zzb(obj);
            }
        }
    }

    public final void zze(Object obj, Object obj2) {
        if (!zzD(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        } else if (obj2 != null) {
            for (int i = 0; i < this.zzc.length; i += 3) {
                int zzp = zzp(i);
                int i2 = this.zzc[i];
                long j = (long) (1048575 & zzp);
                switch (zzo(zzp)) {
                    case 0:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzo(obj, j, zzait.zza(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 1:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzp(obj, j, zzait.zzb(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 2:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzr(obj, j, zzait.zzd(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 3:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzr(obj, j, zzait.zzd(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 4:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 5:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzr(obj, j, zzait.zzd(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 6:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 7:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzm(obj, j, zzait.zzw(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 8:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzs(obj, j, zzait.zzf(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 9:
                        zzu(obj, obj2, i);
                        break;
                    case 10:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzs(obj, j, zzait.zzf(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 11:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 12:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 13:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 14:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzr(obj, j, zzait.zzd(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 15:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzq(obj, j, zzait.zzc(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 16:
                        if (!zzA(obj2, i)) {
                            break;
                        } else {
                            zzait.zzr(obj, j, zzait.zzd(obj2, j));
                            zzw(obj, i);
                            break;
                        }
                    case 17:
                        zzu(obj, obj2, i);
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
                        this.zzj.zzb(obj, obj2, j);
                        break;
                    case 50:
                        int i3 = zzahu.zza;
                        zzahb zzahb = (zzahb) zzait.zzf(obj, j);
                        zzahb zzahb2 = (zzahb) zzait.zzf(obj2, j);
                        if (!zzahb2.isEmpty()) {
                            if (!zzahb.zzd()) {
                                zzahb = zzahb.zza();
                            }
                            zzahb.zzc(zzahb2);
                        }
                        zzait.zzs(obj, j, zzahb);
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
                        if (!zzE(obj2, i2, i)) {
                            break;
                        } else {
                            zzait.zzs(obj, j, zzait.zzf(obj2, j));
                            zzx(obj, i2, i);
                            break;
                        }
                    case 60:
                        zzv(obj, obj2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zzE(obj2, i2, i)) {
                            break;
                        } else {
                            zzait.zzs(obj, j, zzait.zzf(obj2, j));
                            zzx(obj, i2, i);
                            break;
                        }
                    case 68:
                        zzv(obj, obj2, i);
                        break;
                }
            }
            zzahu.zzB(this.zzk, obj, obj2);
            if (this.zzf) {
                this.zzl.zza(obj2);
                throw null;
            }
        } else {
            throw null;
        }
    }

    public final boolean zzg(Object obj, Object obj2) {
        boolean z;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int zzp = zzp(i);
            long j = (long) (zzp & 1048575);
            switch (zzo(zzp)) {
                case 0:
                    if (zzz(obj, obj2, i) && Double.doubleToLongBits(zzait.zza(obj, j)) == Double.doubleToLongBits(zzait.zza(obj2, j))) {
                        continue;
                    }
                case 1:
                    if (zzz(obj, obj2, i) && Float.floatToIntBits(zzait.zzb(obj, j)) == Float.floatToIntBits(zzait.zzb(obj2, j))) {
                        continue;
                    }
                case 2:
                    if (zzz(obj, obj2, i) && zzait.zzd(obj, j) == zzait.zzd(obj2, j)) {
                        continue;
                    }
                case 3:
                    if (zzz(obj, obj2, i) && zzait.zzd(obj, j) == zzait.zzd(obj2, j)) {
                        continue;
                    }
                case 4:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 5:
                    if (zzz(obj, obj2, i) && zzait.zzd(obj, j) == zzait.zzd(obj2, j)) {
                        continue;
                    }
                case 6:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 7:
                    if (zzz(obj, obj2, i) && zzait.zzw(obj, j) == zzait.zzw(obj2, j)) {
                        continue;
                    }
                case 8:
                    if (zzz(obj, obj2, i) && zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j))) {
                        continue;
                    }
                case 9:
                    if (zzz(obj, obj2, i) && zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j))) {
                        continue;
                    }
                case 10:
                    if (zzz(obj, obj2, i) && zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j))) {
                        continue;
                    }
                case 11:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 12:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 13:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 14:
                    if (zzz(obj, obj2, i) && zzait.zzd(obj, j) == zzait.zzd(obj2, j)) {
                        continue;
                    }
                case 15:
                    if (zzz(obj, obj2, i) && zzait.zzc(obj, j) == zzait.zzc(obj2, j)) {
                        continue;
                    }
                case 16:
                    if (zzz(obj, obj2, i) && zzait.zzd(obj, j) == zzait.zzd(obj2, j)) {
                        continue;
                    }
                case 17:
                    if (zzz(obj, obj2, i) && zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j))) {
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
                    z = zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j));
                    break;
                case 50:
                    z = zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j));
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
                    long zzn2 = (long) (zzn(i) & 1048575);
                    if (zzait.zzc(obj, zzn2) == zzait.zzc(obj2, zzn2) && zzahu.zzV(zzait.zzf(obj, j), zzait.zzf(obj2, j))) {
                        continue;
                    }
            }
            if (!z) {
                return false;
            }
        }
        if (!this.zzk.zzc(obj).equals(this.zzk.zzc(obj2))) {
            return false;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzl.zza(obj);
        this.zzl.zza(obj2);
        throw null;
    }

    public final boolean zzh(Object obj) {
        int i;
        int i2;
        Object obj2 = obj;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i5 < this.zzi) {
            int i6 = this.zzh[i5];
            int i7 = this.zzc[i6];
            int zzp = zzp(i6);
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
            if ((268435456 & zzp) != 0 && !zzB(obj, i6, i2, i, i10)) {
                return false;
            }
            switch (zzo(zzp)) {
                case 9:
                case 17:
                    if (zzB(obj, i6, i2, i, i10) && !zzC(obj2, zzp, zzr(i6))) {
                        return false;
                    }
                case 27:
                case 49:
                    List list = (List) zzait.zzf(obj2, (long) (zzp & 1048575));
                    if (!list.isEmpty()) {
                        zzahs zzr = zzr(i6);
                        for (int i11 = 0; i11 < list.size(); i11++) {
                            if (!zzr.zzh(list.get(i11))) {
                                return false;
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                case 50:
                    if (((zzahb) zzait.zzf(obj2, (long) (zzp & 1048575))).isEmpty()) {
                        break;
                    } else {
                        zzaha zzaha = (zzaha) zzs(i6);
                        throw null;
                    }
                case 60:
                case 68:
                    if (zzE(obj2, i7, i6) && !zzC(obj2, zzp, zzr(i6))) {
                        return false;
                    }
            }
            i5++;
            i3 = i2;
            i4 = i;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzl.zza(obj2);
        throw null;
    }

    public final void zzf(Object obj, zzaja zzaja) {
        int i;
        int i2;
        Object obj2 = obj;
        zzaja zzaja2 = zzaja;
        int i3 = 0;
        int i4 = 1048575;
        if (this.zzg) {
            if (!this.zzf) {
                int length = this.zzc.length;
                for (int i5 = 0; i5 < length; i5 += 3) {
                    int zzp = zzp(i5);
                    int i6 = this.zzc[i5];
                    switch (zzo(zzp)) {
                        case 0:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzf(i6, zzait.zza(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 1:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzn(i6, zzait.zzb(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 2:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzs(i6, zzait.zzd(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 3:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzH(i6, zzait.zzd(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 4:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzq(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 5:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzl(i6, zzait.zzd(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 6:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzj(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 7:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzb(i6, zzait.zzw(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 8:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzG(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2);
                                break;
                            }
                        case 9:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzu(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzr(i5));
                                break;
                            }
                        case 10:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzd(i6, (zzafe) zzait.zzf(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 11:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzF(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 12:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzh(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 13:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzv(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 14:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzx(i6, zzait.zzd(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 15:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzz(i6, zzait.zzc(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 16:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzB(i6, zzait.zzd(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 17:
                            if (!zzA(obj2, i5)) {
                                break;
                            } else {
                                zzaja2.zzp(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzr(i5));
                                break;
                            }
                        case 18:
                            zzahu.zzF(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 19:
                            zzahu.zzJ(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 20:
                            zzahu.zzM(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 21:
                            zzahu.zzU(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 22:
                            zzahu.zzL(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 23:
                            zzahu.zzI(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 24:
                            zzahu.zzH(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 25:
                            zzahu.zzD(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 26:
                            zzahu.zzS(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2);
                            break;
                        case 27:
                            zzahu.zzN(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, zzr(i5));
                            break;
                        case 28:
                            zzahu.zzE(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2);
                            break;
                        case 29:
                            zzahu.zzT(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 30:
                            zzahu.zzG(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 31:
                            zzahu.zzO(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 32:
                            zzahu.zzP(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 33:
                            zzahu.zzQ(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 34:
                            zzahu.zzR(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, false);
                            break;
                        case 35:
                            zzahu.zzF(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 36:
                            zzahu.zzJ(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 37:
                            zzahu.zzM(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 38:
                            zzahu.zzU(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 39:
                            zzahu.zzL(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 40:
                            zzahu.zzI(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 41:
                            zzahu.zzH(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 42:
                            zzahu.zzD(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 43:
                            zzahu.zzT(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 44:
                            zzahu.zzG(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 45:
                            zzahu.zzO(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 46:
                            zzahu.zzP(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 47:
                            zzahu.zzQ(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 48:
                            zzahu.zzR(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, true);
                            break;
                        case 49:
                            zzahu.zzK(i6, (List) zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2, zzr(i5));
                            break;
                        case 50:
                            zzy(zzaja2, i6, zzait.zzf(obj2, (long) (zzp & 1048575)), i5);
                            break;
                        case 51:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzf(i6, zzj(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 52:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzn(i6, zzk(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 53:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzs(i6, zzq(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 54:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzH(i6, zzq(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 55:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzq(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 56:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzl(i6, zzq(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 57:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzj(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 58:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzb(i6, zzF(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 59:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzG(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzaja2);
                                break;
                            }
                        case 60:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzu(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzr(i5));
                                break;
                            }
                        case 61:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzd(i6, (zzafe) zzait.zzf(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 62:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzF(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 63:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzh(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 64:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzv(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 65:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzx(i6, zzq(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 66:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzz(i6, zzm(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 67:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzB(i6, zzq(obj2, (long) (zzp & 1048575)));
                                break;
                            }
                        case 68:
                            if (!zzE(obj2, i6, i5)) {
                                break;
                            } else {
                                zzaja2.zzp(i6, zzait.zzf(obj2, (long) (zzp & 1048575)), zzr(i5));
                                break;
                            }
                    }
                }
                zzaij zzaij = this.zzk;
                zzaij.zzg(zzaij.zzc(obj2), zzaja2);
                return;
            }
            this.zzl.zza(obj2);
            throw null;
        } else if (!this.zzf) {
            int length2 = this.zzc.length;
            Unsafe unsafe = zzb;
            int i7 = 0;
            int i8 = 0;
            int i9 = 1048575;
            while (i7 < length2) {
                int zzp2 = zzp(i7);
                int[] iArr = this.zzc;
                int i10 = iArr[i7];
                int zzo = zzo(zzp2);
                if (zzo <= 17) {
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
                long j = (long) (zzp2 & i4);
                switch (zzo) {
                    case 0:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzf(i10, zzait.zza(obj2, j));
                            break;
                        }
                    case 1:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzn(i10, zzait.zzb(obj2, j));
                            break;
                        }
                    case 2:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzs(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 3:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzH(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 4:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzq(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 5:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzl(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 6:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzj(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 7:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzb(i10, zzait.zzw(obj2, j));
                            break;
                        }
                    case 8:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzG(i10, unsafe.getObject(obj2, j), zzaja2);
                            break;
                        }
                    case 9:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzu(i10, unsafe.getObject(obj2, j), zzr(i7));
                            break;
                        }
                    case 10:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzd(i10, (zzafe) unsafe.getObject(obj2, j));
                            break;
                        }
                    case 11:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzF(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 12:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzh(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 13:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzv(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 14:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzx(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 15:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzz(i10, unsafe.getInt(obj2, j));
                            break;
                        }
                    case 16:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzB(i10, unsafe.getLong(obj2, j));
                            break;
                        }
                    case 17:
                        i2 = 0;
                        if ((i8 & i) == 0) {
                            break;
                        } else {
                            zzaja2.zzp(i10, unsafe.getObject(obj2, j), zzr(i7));
                            break;
                        }
                    case 18:
                        i2 = 0;
                        zzahu.zzF(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 19:
                        i2 = 0;
                        zzahu.zzJ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 20:
                        i2 = 0;
                        zzahu.zzM(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 21:
                        i2 = 0;
                        zzahu.zzU(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 22:
                        i2 = 0;
                        zzahu.zzL(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 23:
                        i2 = 0;
                        zzahu.zzI(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 24:
                        i2 = 0;
                        zzahu.zzH(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 25:
                        i2 = 0;
                        zzahu.zzD(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        break;
                    case 26:
                        zzahu.zzS(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2);
                        i2 = 0;
                        break;
                    case 27:
                        zzahu.zzN(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, zzr(i7));
                        i2 = 0;
                        break;
                    case 28:
                        zzahu.zzE(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2);
                        i2 = 0;
                        break;
                    case 29:
                        zzahu.zzT(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 30:
                        zzahu.zzG(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 31:
                        zzahu.zzO(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 32:
                        zzahu.zzP(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 33:
                        zzahu.zzQ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 34:
                        zzahu.zzR(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, false);
                        i2 = 0;
                        break;
                    case 35:
                        zzahu.zzF(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 36:
                        zzahu.zzJ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 37:
                        zzahu.zzM(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 38:
                        zzahu.zzU(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 39:
                        zzahu.zzL(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 40:
                        zzahu.zzI(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 41:
                        zzahu.zzH(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 42:
                        zzahu.zzD(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 43:
                        zzahu.zzT(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 44:
                        zzahu.zzG(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 45:
                        zzahu.zzO(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 46:
                        zzahu.zzP(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 47:
                        zzahu.zzQ(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 48:
                        zzahu.zzR(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, true);
                        i2 = 0;
                        break;
                    case 49:
                        zzahu.zzK(this.zzc[i7], (List) unsafe.getObject(obj2, j), zzaja2, zzr(i7));
                        i2 = 0;
                        break;
                    case 50:
                        zzy(zzaja2, i10, unsafe.getObject(obj2, j), i7);
                        i2 = 0;
                        break;
                    case 51:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzf(i10, zzj(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 52:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzn(i10, zzk(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 53:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzs(i10, zzq(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 54:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzH(i10, zzq(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 55:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzq(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 56:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzl(i10, zzq(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 57:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzj(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 58:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzb(i10, zzF(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 59:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzG(i10, unsafe.getObject(obj2, j), zzaja2);
                            i2 = 0;
                            break;
                        }
                    case 60:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzu(i10, unsafe.getObject(obj2, j), zzr(i7));
                            i2 = 0;
                            break;
                        }
                    case 61:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzd(i10, (zzafe) unsafe.getObject(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 62:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzF(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 63:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzh(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 64:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzv(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 65:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzx(i10, zzq(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 66:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzz(i10, zzm(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 67:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzB(i10, zzq(obj2, j));
                            i2 = 0;
                            break;
                        }
                    case 68:
                        if (!zzE(obj2, i10, i7)) {
                            i2 = 0;
                            break;
                        } else {
                            zzaja2.zzp(i10, unsafe.getObject(obj2, j), zzr(i7));
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
            zzaij zzaij2 = this.zzk;
            zzaij2.zzg(zzaij2.zzc(obj2), zzaja2);
        } else {
            this.zzl.zza(obj2);
            throw null;
        }
    }
}
