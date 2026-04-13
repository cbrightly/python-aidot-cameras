package com.google.android.gms.internal.vision;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.util.Arrays;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzih extends zzif {
    private final byte[] zzd;
    private final boolean zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;

    private zzih(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzd = bArr;
        this.zzf = i2 + i;
        this.zzh = i;
        this.zzi = i;
        this.zze = z;
    }

    public final int zza() {
        if (zzt()) {
            this.zzj = 0;
            return 0;
        }
        int zzv = zzv();
        this.zzj = zzv;
        if ((zzv >>> 3) != 0) {
            return zzv;
        }
        throw zzjk.zzd();
    }

    public final void zza(int i) {
        if (this.zzj != i) {
            throw zzjk.zze();
        }
    }

    public final boolean zzb(int i) {
        int zza;
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.zzf - this.zzh >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.zzd;
                        int i3 = this.zzh;
                        this.zzh = i3 + 1;
                        if (bArr[i3] < 0) {
                            i2++;
                        }
                    }
                    throw zzjk.zzc();
                }
                while (i2 < 10) {
                    if (zzaa() < 0) {
                        i2++;
                    }
                }
                throw zzjk.zzc();
                return true;
            case 1:
                zzf(8);
                return true;
            case 2:
                zzf(zzv());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzf(4);
                return true;
            default:
                throw zzjk.zzf();
        }
        do {
            zza = zza();
            if (zza == 0 || !zzb(zza)) {
                zza(((i >>> 3) << 3) | 4);
                return true;
            }
            zza = zza();
            zza(((i >>> 3) << 3) | 4);
            return true;
        } while (!zzb(zza));
        zza(((i >>> 3) << 3) | 4);
        return true;
    }

    public final double zzb() {
        return Double.longBitsToDouble(zzy());
    }

    public final float zzc() {
        return Float.intBitsToFloat(zzx());
    }

    public final long zzd() {
        return zzw();
    }

    public final long zze() {
        return zzw();
    }

    public final int zzf() {
        return zzv();
    }

    public final long zzg() {
        return zzy();
    }

    public final int zzh() {
        return zzx();
    }

    public final boolean zzi() {
        return zzw() != 0;
    }

    public final String zzj() {
        int zzv = zzv();
        if (zzv > 0 && zzv <= this.zzf - this.zzh) {
            String str = new String(this.zzd, this.zzh, zzv, zzjf.zza);
            this.zzh += zzv;
            return str;
        } else if (zzv == 0) {
            return "";
        } else {
            if (zzv < 0) {
                throw zzjk.zzb();
            }
            throw zzjk.zza();
        }
    }

    public final String zzk() {
        int zzv = zzv();
        if (zzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (zzv <= i - i2) {
                String zzb = zzmd.zzb(this.zzd, i2, zzv);
                this.zzh += zzv;
                return zzb;
            }
        }
        if (zzv == 0) {
            return "";
        }
        if (zzv <= 0) {
            throw zzjk.zzb();
        }
        throw zzjk.zza();
    }

    public final zzht zzl() {
        byte[] bArr;
        int zzv = zzv();
        if (zzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (zzv <= i - i2) {
                zzht zza = zzht.zza(this.zzd, i2, zzv);
                this.zzh += zzv;
                return zza;
            }
        }
        if (zzv == 0) {
            return zzht.zza;
        }
        if (zzv > 0) {
            int i3 = this.zzf;
            int i4 = this.zzh;
            if (zzv <= i3 - i4) {
                int i5 = zzv + i4;
                this.zzh = i5;
                bArr = Arrays.copyOfRange(this.zzd, i4, i5);
                return zzht.zza(bArr);
            }
        }
        if (zzv > 0) {
            throw zzjk.zza();
        } else if (zzv == 0) {
            bArr = zzjf.zzb;
            return zzht.zza(bArr);
        } else {
            throw zzjk.zzb();
        }
    }

    public final int zzm() {
        return zzv();
    }

    public final int zzn() {
        return zzv();
    }

    public final int zzo() {
        return zzx();
    }

    public final long zzp() {
        return zzy();
    }

    public final int zzq() {
        return zzif.zze(zzv());
    }

    public final long zzr() {
        return zzif.zza(zzw());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzv() {
        /*
            r5 = this;
            int r0 = r5.zzh
            int r1 = r5.zzf
            if (r1 == r0) goto L_0x006d
            byte[] r2 = r5.zzd
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0011
            r5.zzh = r3
            return r0
        L_0x0011:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x006d
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0022
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            goto L_0x006a
        L_0x0022:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x002f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            r1 = r3
            goto L_0x006a
        L_0x002f:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x003d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x006a
        L_0x003d:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r2 = r2[r3]
            if (r2 < 0) goto L_0x006d
            goto L_0x006a
        L_0x0069:
            r1 = r3
        L_0x006a:
            r5.zzh = r1
            return r0
        L_0x006d:
            long r0 = r5.zzs()
            int r0 = (int) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzih.zzv():int");
    }

    private final long zzw() {
        long j;
        int i = this.zzh;
        int i2 = this.zzf;
        if (i2 != i) {
            byte[] bArr = this.zzd;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzh = i3;
                return (long) b;
            } else if (i2 - i3 >= 9) {
                int i4 = i3 + 1;
                byte b2 = b ^ (bArr[i3] << 7);
                if (b2 < 0) {
                    j = (long) (b2 ^ OTACommand.RESP_ID_VERSION);
                } else {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << 14);
                    if (b3 >= 0) {
                        i4 = i5;
                        j = (long) (b3 ^ OTACommand.RESP_ID_VERSION);
                    } else {
                        i4 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << 21);
                        if (b4 < 0) {
                            j = (long) (b4 ^ OTACommand.RESP_ID_VERSION);
                        } else {
                            long j2 = (long) b4;
                            int i6 = i4 + 1;
                            long j3 = j2 ^ (((long) bArr[i4]) << 28);
                            if (j3 >= 0) {
                                j = j3 ^ 266354560;
                                i4 = i6;
                            } else {
                                i4 = i6 + 1;
                                long j4 = j3 ^ (((long) bArr[i6]) << 35);
                                if (j4 < 0) {
                                    j = j4 ^ -34093383808L;
                                } else {
                                    int i7 = i4 + 1;
                                    long j5 = j4 ^ (((long) bArr[i4]) << 42);
                                    if (j5 >= 0) {
                                        j = j5 ^ 4363953127296L;
                                        i4 = i7;
                                    } else {
                                        i4 = i7 + 1;
                                        long j6 = j5 ^ (((long) bArr[i7]) << 49);
                                        if (j6 < 0) {
                                            j = j6 ^ -558586000294016L;
                                        } else {
                                            int i8 = i4 + 1;
                                            long j7 = (j6 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                i4 = i8 + 1;
                                                if (((long) bArr[i8]) >= 0) {
                                                    j = j7;
                                                }
                                            } else {
                                                i4 = i8;
                                                j = j7;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.zzh = i4;
                return j;
            }
        }
        return zzs();
    }

    /* access modifiers changed from: package-private */
    public final long zzs() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzaa = zzaa();
            j |= ((long) (zzaa & Byte.MAX_VALUE)) << i;
            if ((zzaa & OTACommand.RESP_ID_VERSION) == 0) {
                return j;
            }
        }
        throw zzjk.zzc();
    }

    private final int zzx() {
        int i = this.zzh;
        if (this.zzf - i >= 4) {
            byte[] bArr = this.zzd;
            this.zzh = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << MappingData.PATH);
        }
        throw zzjk.zza();
    }

    private final long zzy() {
        int i = this.zzh;
        if (this.zzf - i >= 8) {
            byte[] bArr = this.zzd;
            this.zzh = i + 8;
            return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
        }
        throw zzjk.zza();
    }

    public final int zzc(int i) {
        if (i >= 0) {
            int zzu = i + zzu();
            int i2 = this.zzk;
            if (zzu <= i2) {
                this.zzk = zzu;
                zzz();
                return i2;
            }
            throw zzjk.zza();
        }
        throw zzjk.zzb();
    }

    private final void zzz() {
        int i = this.zzf + this.zzg;
        this.zzf = i;
        int i2 = i - this.zzi;
        int i3 = this.zzk;
        if (i2 > i3) {
            int i4 = i2 - i3;
            this.zzg = i4;
            this.zzf = i - i4;
            return;
        }
        this.zzg = 0;
    }

    public final void zzd(int i) {
        this.zzk = i;
        zzz();
    }

    public final boolean zzt() {
        return this.zzh == this.zzf;
    }

    public final int zzu() {
        return this.zzh - this.zzi;
    }

    private final byte zzaa() {
        int i = this.zzh;
        if (i != this.zzf) {
            byte[] bArr = this.zzd;
            this.zzh = i + 1;
            return bArr[i];
        }
        throw zzjk.zza();
    }

    private final void zzf(int i) {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (i <= i2 - i3) {
                this.zzh = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzjk.zzb();
        }
        throw zzjk.zza();
    }
}
