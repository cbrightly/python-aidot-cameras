package com.google.android.gms.internal.vision;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzhl {
    static int zza(byte[] bArr, int i, zzhn zzhn) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza((int) b, bArr, i2, zzhn);
        }
        zzhn.zza = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzhn zzhn) {
        int i3 = i & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzhn.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzhn.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzhn.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzhn.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzhn.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzhn zzhn) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzhn.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & Byte.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & Byte.MAX_VALUE)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        zzhn.zzb = j2;
        return i3;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << MappingData.PATH);
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzhn zzhn) {
        int zza = zza(bArr, i, zzhn);
        int i2 = zzhn.zza;
        if (i2 < 0) {
            throw zzjk.zzb();
        } else if (i2 == 0) {
            zzhn.zzc = "";
            return zza;
        } else {
            zzhn.zzc = new String(bArr, zza, i2, zzjf.zza);
            return zza + i2;
        }
    }

    static int zzd(byte[] bArr, int i, zzhn zzhn) {
        int zza = zza(bArr, i, zzhn);
        int i2 = zzhn.zza;
        if (i2 < 0) {
            throw zzjk.zzb();
        } else if (i2 == 0) {
            zzhn.zzc = "";
            return zza;
        } else {
            zzhn.zzc = zzmd.zzb(bArr, zza, i2);
            return zza + i2;
        }
    }

    static int zze(byte[] bArr, int i, zzhn zzhn) {
        int zza = zza(bArr, i, zzhn);
        int i2 = zzhn.zza;
        if (i2 < 0) {
            throw zzjk.zzb();
        } else if (i2 > bArr.length - zza) {
            throw zzjk.zza();
        } else if (i2 == 0) {
            zzhn.zzc = zzht.zza;
            return zza;
        } else {
            zzhn.zzc = zzht.zza(bArr, zza, i2);
            return zza + i2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int zza(com.google.android.gms.internal.vision.zzlc r6, byte[] r7, int r8, int r9, com.google.android.gms.internal.vision.zzhn r10) {
        /*
            int r0 = r8 + 1
            byte r8 = r7[r8]
            if (r8 >= 0) goto L_0x000e
            int r0 = zza((int) r8, (byte[]) r7, (int) r0, (com.google.android.gms.internal.vision.zzhn) r10)
            int r8 = r10.zza
            r3 = r0
            goto L_0x000f
        L_0x000e:
            r3 = r0
        L_0x000f:
            if (r8 < 0) goto L_0x0027
            int r9 = r9 - r3
            if (r8 > r9) goto L_0x0027
            java.lang.Object r9 = r6.zza()
            int r8 = r8 + r3
            r0 = r6
            r1 = r9
            r2 = r7
            r4 = r8
            r5 = r10
            r0.zza(r1, r2, r3, r4, r5)
            r6.zzc(r9)
            r10.zzc = r9
            return r8
        L_0x0027:
            com.google.android.gms.internal.vision.zzjk r6 = com.google.android.gms.internal.vision.zzjk.zza()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzhl.zza(com.google.android.gms.internal.vision.zzlc, byte[], int, int, com.google.android.gms.internal.vision.zzhn):int");
    }

    static int zza(zzlc zzlc, byte[] bArr, int i, int i2, int i3, zzhn zzhn) {
        zzko zzko = (zzko) zzlc;
        Object zza = zzko.zza();
        int zza2 = zzko.zza(zza, bArr, i, i2, i3, zzhn);
        zzko.zzc(zza);
        zzhn.zzc = zza;
        return zza2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzjl<?> zzjl, zzhn zzhn) {
        zzjd zzjd = (zzjd) zzjl;
        int zza = zza(bArr, i2, zzhn);
        zzjd.zzc(zzhn.zza);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzhn);
            if (i != zzhn.zza) {
                break;
            }
            zza = zza(bArr, zza2, zzhn);
            zzjd.zzc(zzhn.zza);
        }
        return zza;
    }

    static int zza(byte[] bArr, int i, zzjl<?> zzjl, zzhn zzhn) {
        zzjd zzjd = (zzjd) zzjl;
        int zza = zza(bArr, i, zzhn);
        int i2 = zzhn.zza + zza;
        while (zza < i2) {
            zza = zza(bArr, zza, zzhn);
            zzjd.zzc(zzhn.zza);
        }
        if (zza == i2) {
            return zza;
        }
        throw zzjk.zza();
    }

    static int zza(zzlc<?> zzlc, int i, byte[] bArr, int i2, int i3, zzjl<?> zzjl, zzhn zzhn) {
        int zza = zza((zzlc) zzlc, bArr, i2, i3, zzhn);
        zzjl.add(zzhn.zzc);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzhn);
            if (i != zzhn.zza) {
                break;
            }
            zza = zza((zzlc) zzlc, bArr, zza2, i3, zzhn);
            zzjl.add(zzhn.zzc);
        }
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzlx zzlx, zzhn zzhn) {
        if ((i >>> 3) != 0) {
            switch (i & 7) {
                case 0:
                    int zzb = zzb(bArr, i2, zzhn);
                    zzlx.zza(i, (Object) Long.valueOf(zzhn.zzb));
                    return zzb;
                case 1:
                    zzlx.zza(i, (Object) Long.valueOf(zzb(bArr, i2)));
                    return i2 + 8;
                case 2:
                    int zza = zza(bArr, i2, zzhn);
                    int i4 = zzhn.zza;
                    if (i4 < 0) {
                        throw zzjk.zzb();
                    } else if (i4 <= bArr.length - zza) {
                        if (i4 == 0) {
                            zzlx.zza(i, (Object) zzht.zza);
                        } else {
                            zzlx.zza(i, (Object) zzht.zza(bArr, zza, i4));
                        }
                        return zza + i4;
                    } else {
                        throw zzjk.zza();
                    }
                case 3:
                    zzlx zzb2 = zzlx.zzb();
                    int i5 = (i & -8) | 4;
                    int i6 = 0;
                    while (true) {
                        if (i2 < i3) {
                            int zza2 = zza(bArr, i2, zzhn);
                            int i7 = zzhn.zza;
                            if (i7 != i5) {
                                i6 = i7;
                                i2 = zza(i7, bArr, zza2, i3, zzb2, zzhn);
                            } else {
                                i6 = i7;
                                i2 = zza2;
                            }
                        }
                    }
                    if (i2 > i3 || i6 != i5) {
                        throw zzjk.zzg();
                    }
                    zzlx.zza(i, (Object) zzb2);
                    return i2;
                case 5:
                    zzlx.zza(i, (Object) Integer.valueOf(zza(bArr, i2)));
                    return i2 + 4;
                default:
                    throw zzjk.zzd();
            }
        } else {
            throw zzjk.zzd();
        }
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzhn zzhn) {
        if ((i >>> 3) != 0) {
            switch (i & 7) {
                case 0:
                    return zzb(bArr, i2, zzhn);
                case 1:
                    return i2 + 8;
                case 2:
                    return zza(bArr, i2, zzhn) + zzhn.zza;
                case 3:
                    int i4 = (i & -8) | 4;
                    int i5 = 0;
                    while (i2 < i3) {
                        i2 = zza(bArr, i2, zzhn);
                        i5 = zzhn.zza;
                        if (i5 != i4) {
                            i2 = zza(i5, bArr, i2, i3, zzhn);
                        } else if (i2 > i3 && i5 == i4) {
                            return i2;
                        } else {
                            throw zzjk.zzg();
                        }
                    }
                    if (i2 > i3) {
                    }
                    throw zzjk.zzg();
                case 5:
                    return i2 + 4;
                default:
                    throw zzjk.zzd();
            }
        } else {
            throw zzjk.zzd();
        }
    }
}
