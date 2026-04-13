package com.google.android.gms.internal.measurement;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzjo {
    static int zza(byte[] bArr, int i, zzjn zzjn) {
        int zzj = zzj(bArr, i, zzjn);
        int i2 = zzjn.zza;
        if (i2 < 0) {
            throw zzll.zzd();
        } else if (i2 > bArr.length - zzj) {
            throw zzll.zzf();
        } else if (i2 == 0) {
            zzjn.zzc = zzka.zzb;
            return zzj;
        } else {
            zzjn.zzc = zzka.zzl(bArr, zzj, i2);
            return zzj + i2;
        }
    }

    static int zzb(byte[] bArr, int i) {
        int i2 = (bArr[i + 1] & 255) << 8;
        return ((bArr[i + 3] & 255) << 24) | i2 | (bArr[i] & 255) | ((bArr[i + 2] & 255) << MappingData.PATH);
    }

    static int zzc(zzmt zzmt, byte[] bArr, int i, int i2, int i3, zzjn zzjn) {
        Object zze = zzmt.zze();
        int zzn = zzn(zze, zzmt, bArr, i, i2, i3, zzjn);
        zzmt.zzf(zze);
        zzjn.zzc = zze;
        return zzn;
    }

    static int zzd(zzmt zzmt, byte[] bArr, int i, int i2, zzjn zzjn) {
        Object zze = zzmt.zze();
        int zzo = zzo(zze, zzmt, bArr, i, i2, zzjn);
        zzmt.zzf(zze);
        zzjn.zzc = zze;
        return zzo;
    }

    static int zze(zzmt zzmt, int i, byte[] bArr, int i2, int i3, zzli zzli, zzjn zzjn) {
        int zzd = zzd(zzmt, bArr, i2, i3, zzjn);
        zzli.add(zzjn.zzc);
        while (zzd < i3) {
            int zzj = zzj(bArr, zzd, zzjn);
            if (i != zzjn.zza) {
                break;
            }
            zzd = zzd(zzmt, bArr, zzj, i3, zzjn);
            zzli.add(zzjn.zzc);
        }
        return zzd;
    }

    static int zzf(byte[] bArr, int i, zzli zzli, zzjn zzjn) {
        zzlc zzlc = (zzlc) zzli;
        int zzj = zzj(bArr, i, zzjn);
        int i2 = zzjn.zza + zzj;
        while (zzj < i2) {
            zzj = zzj(bArr, zzj, zzjn);
            zzlc.zzh(zzjn.zza);
        }
        if (zzj == i2) {
            return zzj;
        }
        throw zzll.zzf();
    }

    static int zzg(byte[] bArr, int i, zzjn zzjn) {
        int zzj = zzj(bArr, i, zzjn);
        int i2 = zzjn.zza;
        if (i2 < 0) {
            throw zzll.zzd();
        } else if (i2 == 0) {
            zzjn.zzc = "";
            return zzj;
        } else {
            zzjn.zzc = new String(bArr, zzj, i2, zzlj.zzb);
            return zzj + i2;
        }
    }

    static int zzh(byte[] bArr, int i, zzjn zzjn) {
        int i2;
        int i3;
        int zzj = zzj(bArr, i, zzjn);
        int i4 = zzjn.zza;
        if (i4 < 0) {
            throw zzll.zzd();
        } else if (i4 == 0) {
            zzjn.zzc = "";
            return zzj;
        } else {
            int i5 = zznz.zza;
            int length = bArr.length;
            if ((((length - zzj) - i4) | zzj | i4) >= 0) {
                int i6 = zzj + i4;
                char[] cArr = new char[i4];
                int i7 = 0;
                while (i2 < i6) {
                    byte b = bArr[i2];
                    if (!zznv.zzd(b)) {
                        break;
                    }
                    zzj = i2 + 1;
                    cArr[i3] = (char) b;
                    i7 = i3 + 1;
                }
                while (i2 < i6) {
                    int i8 = i2 + 1;
                    byte b2 = bArr[i2];
                    if (zznv.zzd(b2)) {
                        cArr[i3] = (char) b2;
                        i2 = i8;
                        i3++;
                        while (i2 < i6) {
                            byte b3 = bArr[i2];
                            if (!zznv.zzd(b3)) {
                                break;
                            }
                            i2++;
                            cArr[i3] = (char) b3;
                            i3++;
                        }
                    } else if (b2 < -32) {
                        if (i8 < i6) {
                            zznv.zzc(b2, bArr[i8], cArr, i3);
                            i2 = i8 + 1;
                            i3++;
                        } else {
                            throw zzll.zzc();
                        }
                    } else if (b2 < -16) {
                        if (i8 < i6 - 1) {
                            int i9 = i8 + 1;
                            zznv.zzb(b2, bArr[i8], bArr[i9], cArr, i3);
                            i2 = i9 + 1;
                            i3++;
                        } else {
                            throw zzll.zzc();
                        }
                    } else if (i8 < i6 - 2) {
                        int i10 = i8 + 1;
                        byte b4 = bArr[i8];
                        int i11 = i10 + 1;
                        zznv.zza(b2, b4, bArr[i10], bArr[i11], cArr, i3);
                        i3 += 2;
                        i2 = i11 + 1;
                    } else {
                        throw zzll.zzc();
                    }
                }
                zzjn.zzc = new String(cArr, 0, i3);
                return i6;
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[]{Integer.valueOf(length), Integer.valueOf(zzj), Integer.valueOf(i4)}));
        }
    }

    static int zzj(byte[] bArr, int i, zzjn zzjn) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzk(b, bArr, i2, zzjn);
        }
        zzjn.zza = b;
        return i2;
    }

    static int zzk(int i, byte[] bArr, int i2, zzjn zzjn) {
        byte b = bArr[i2];
        int i3 = i2 + 1;
        int i4 = i & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        if (b >= 0) {
            zzjn.zza = i4 | (b << 7);
            return i3;
        }
        int i5 = i4 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzjn.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzjn.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzjn.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] < 0) {
                i10 = i12;
            } else {
                zzjn.zza = i11;
                return i12;
            }
        }
    }

    static int zzl(int i, byte[] bArr, int i2, int i3, zzli zzli, zzjn zzjn) {
        zzlc zzlc = (zzlc) zzli;
        int zzj = zzj(bArr, i2, zzjn);
        zzlc.zzh(zzjn.zza);
        while (zzj < i3) {
            int zzj2 = zzj(bArr, zzj, zzjn);
            if (i != zzjn.zza) {
                break;
            }
            zzj = zzj(bArr, zzj2, zzjn);
            zzlc.zzh(zzjn.zza);
        }
        return zzj;
    }

    static int zzm(byte[] bArr, int i, zzjn zzjn) {
        long j = (long) bArr[i];
        int i2 = i + 1;
        if (j >= 0) {
            zzjn.zzb = j;
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
            byte b3 = b2;
            i3 = i5;
            b = b3;
        }
        zzjn.zzb = j2;
        return i3;
    }

    static int zzn(Object obj, zzmt zzmt, byte[] bArr, int i, int i2, int i3, zzjn zzjn) {
        int zzc = ((zzml) zzmt).zzc(obj, bArr, i, i2, i3, zzjn);
        zzjn.zzc = obj;
        return zzc;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int zzo(java.lang.Object r6, com.google.android.gms.internal.measurement.zzmt r7, byte[] r8, int r9, int r10, com.google.android.gms.internal.measurement.zzjn r11) {
        /*
            int r0 = r9 + 1
            byte r9 = r8[r9]
            if (r9 >= 0) goto L_0x000e
            int r0 = zzk(r9, r8, r0, r11)
            int r9 = r11.zza
            r3 = r0
            goto L_0x000f
        L_0x000e:
            r3 = r0
        L_0x000f:
            if (r9 < 0) goto L_0x0020
            int r10 = r10 - r3
            if (r9 > r10) goto L_0x0020
            int r9 = r9 + r3
            r0 = r7
            r1 = r6
            r2 = r8
            r4 = r9
            r5 = r11
            r0.zzh(r1, r2, r3, r4, r5)
            r11.zzc = r6
            return r9
        L_0x0020:
            com.google.android.gms.internal.measurement.zzll r6 = com.google.android.gms.internal.measurement.zzll.zzf()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjo.zzo(java.lang.Object, com.google.android.gms.internal.measurement.zzmt, byte[], int, int, com.google.android.gms.internal.measurement.zzjn):int");
    }

    static long zzp(byte[] bArr, int i) {
        return (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    static int zzi(int i, byte[] bArr, int i2, int i3, zznl zznl, zzjn zzjn) {
        if ((i >>> 3) != 0) {
            switch (i & 7) {
                case 0:
                    int zzm = zzm(bArr, i2, zzjn);
                    zznl.zzj(i, Long.valueOf(zzjn.zzb));
                    return zzm;
                case 1:
                    zznl.zzj(i, Long.valueOf(zzp(bArr, i2)));
                    return i2 + 8;
                case 2:
                    int zzj = zzj(bArr, i2, zzjn);
                    int i4 = zzjn.zza;
                    if (i4 < 0) {
                        throw zzll.zzd();
                    } else if (i4 <= bArr.length - zzj) {
                        if (i4 == 0) {
                            zznl.zzj(i, zzka.zzb);
                        } else {
                            zznl.zzj(i, zzka.zzl(bArr, zzj, i4));
                        }
                        return zzj + i4;
                    } else {
                        throw zzll.zzf();
                    }
                case 3:
                    int i5 = (i & -8) | 4;
                    zznl zzf = zznl.zzf();
                    int i6 = 0;
                    while (true) {
                        if (i2 < i3) {
                            int zzj2 = zzj(bArr, i2, zzjn);
                            int i7 = zzjn.zza;
                            if (i7 != i5) {
                                i6 = i7;
                                i2 = zzi(i7, bArr, zzj2, i3, zzf, zzjn);
                            } else {
                                i6 = i7;
                                i2 = zzj2;
                            }
                        }
                    }
                    if (i2 > i3 || i6 != i5) {
                        throw zzll.zze();
                    }
                    zznl.zzj(i, zzf);
                    return i2;
                case 5:
                    zznl.zzj(i, Integer.valueOf(zzb(bArr, i2)));
                    return i2 + 4;
                default:
                    throw zzll.zzb();
            }
        } else {
            throw zzll.zzb();
        }
    }
}
