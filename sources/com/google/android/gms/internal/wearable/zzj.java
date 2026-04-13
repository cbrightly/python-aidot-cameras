package com.google.android.gms.internal.wearable;

import java.util.Arrays;

public final class zzj extends zzn<zzj> {
    public byte[] zzgd = zzw.zzhy;
    public String zzge = "";
    public double zzgf = 0.0d;
    public float zzgg = 0.0f;
    public long zzgh = 0;
    public int zzgi = 0;
    public int zzgj = 0;
    public boolean zzgk = false;
    public zzh[] zzgl = zzh.zzh();
    public zzi[] zzgm = zzi.zzi();
    public String[] zzgn = zzw.zzhw;
    public long[] zzgo = zzw.zzhs;
    public float[] zzgp = zzw.zzht;
    public long zzgq = 0;

    public zzj() {
        this.zzhc = null;
        this.zzhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzj)) {
            return false;
        }
        zzj zzj = (zzj) obj;
        if (!Arrays.equals(this.zzgd, zzj.zzgd)) {
            return false;
        }
        String str = this.zzge;
        if (str == null) {
            if (zzj.zzge != null) {
                return false;
            }
        } else if (!str.equals(zzj.zzge)) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzgf) != Double.doubleToLongBits(zzj.zzgf) || Float.floatToIntBits(this.zzgg) != Float.floatToIntBits(zzj.zzgg) || this.zzgh != zzj.zzgh || this.zzgi != zzj.zzgi || this.zzgj != zzj.zzgj || this.zzgk != zzj.zzgk || !zzr.equals((Object[]) this.zzgl, (Object[]) zzj.zzgl) || !zzr.equals((Object[]) this.zzgm, (Object[]) zzj.zzgm) || !zzr.equals((Object[]) this.zzgn, (Object[]) zzj.zzgn) || !zzr.equals(this.zzgo, zzj.zzgo) || !zzr.equals(this.zzgp, zzj.zzgp) || this.zzgq != zzj.zzgq) {
            return false;
        }
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            return this.zzhc.equals(zzj.zzhc);
        }
        zzp zzp2 = zzj.zzhc;
        if (zzp2 == null || zzp2.isEmpty()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (((zzj.class.getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzgd)) * 31;
        String str = this.zzge;
        int i = 0;
        int hashCode2 = str == null ? 0 : str.hashCode();
        long doubleToLongBits = Double.doubleToLongBits(this.zzgf);
        long j = this.zzgh;
        int floatToIntBits = (((((((((((hashCode + hashCode2) * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + Float.floatToIntBits(this.zzgg)) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + this.zzgi) * 31) + this.zzgj) * 31;
        int i2 = this.zzgk ? 1231 : 1237;
        long j2 = this.zzgq;
        int hashCode3 = (((((((((((((floatToIntBits + i2) * 31) + zzr.hashCode((Object[]) this.zzgl)) * 31) + zzr.hashCode((Object[]) this.zzgm)) * 31) + zzr.hashCode((Object[]) this.zzgn)) * 31) + zzr.hashCode(this.zzgo)) * 31) + zzr.hashCode(this.zzgp)) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        zzp zzp = this.zzhc;
        if (zzp != null && !zzp.isEmpty()) {
            i = this.zzhc.hashCode();
        }
        return hashCode3 + i;
    }

    public final void zza(zzl zzl) {
        if (!Arrays.equals(this.zzgd, zzw.zzhy)) {
            byte[] bArr = this.zzgd;
            zzl.zzf(1, 2);
            zzl.zzl(bArr.length);
            zzl.zzc(bArr);
        }
        String str = this.zzge;
        if (str != null && !str.equals("")) {
            zzl.zza(2, this.zzge);
        }
        if (Double.doubleToLongBits(this.zzgf) != Double.doubleToLongBits(0.0d)) {
            double d = this.zzgf;
            zzl.zzf(3, 1);
            zzl.zzb(Double.doubleToLongBits(d));
        }
        if (Float.floatToIntBits(this.zzgg) != Float.floatToIntBits(0.0f)) {
            zzl.zza(4, this.zzgg);
        }
        long j = this.zzgh;
        if (j != 0) {
            zzl.zza(5, j);
        }
        int i = this.zzgi;
        if (i != 0) {
            zzl.zzd(6, i);
        }
        int i2 = this.zzgj;
        int i3 = 0;
        if (i2 != 0) {
            zzl.zzf(7, 0);
            zzl.zzl(zzl.zzn(i2));
        }
        boolean z = this.zzgk;
        if (z) {
            zzl.zzf(8, 0);
            zzl.zza(z ? (byte) 1 : 0);
        }
        zzh[] zzhArr = this.zzgl;
        if (zzhArr != null && zzhArr.length > 0) {
            int i4 = 0;
            while (true) {
                zzh[] zzhArr2 = this.zzgl;
                if (i4 >= zzhArr2.length) {
                    break;
                }
                zzh zzh = zzhArr2[i4];
                if (zzh != null) {
                    zzl.zza(9, (zzt) zzh);
                }
                i4++;
            }
        }
        zzi[] zziArr = this.zzgm;
        if (zziArr != null && zziArr.length > 0) {
            int i5 = 0;
            while (true) {
                zzi[] zziArr2 = this.zzgm;
                if (i5 >= zziArr2.length) {
                    break;
                }
                zzi zzi = zziArr2[i5];
                if (zzi != null) {
                    zzl.zza(10, (zzt) zzi);
                }
                i5++;
            }
        }
        String[] strArr = this.zzgn;
        if (strArr != null && strArr.length > 0) {
            int i6 = 0;
            while (true) {
                String[] strArr2 = this.zzgn;
                if (i6 >= strArr2.length) {
                    break;
                }
                String str2 = strArr2[i6];
                if (str2 != null) {
                    zzl.zza(11, str2);
                }
                i6++;
            }
        }
        long[] jArr = this.zzgo;
        if (jArr != null && jArr.length > 0) {
            int i7 = 0;
            while (true) {
                long[] jArr2 = this.zzgo;
                if (i7 >= jArr2.length) {
                    break;
                }
                zzl.zza(12, jArr2[i7]);
                i7++;
            }
        }
        long j2 = this.zzgq;
        if (j2 != 0) {
            zzl.zza(13, j2);
        }
        float[] fArr = this.zzgp;
        if (fArr != null && fArr.length > 0) {
            while (true) {
                float[] fArr2 = this.zzgp;
                if (i3 >= fArr2.length) {
                    break;
                }
                zzl.zza(14, fArr2[i3]);
                i3++;
            }
        }
        super.zza(zzl);
    }

    /* access modifiers changed from: protected */
    public final int zzg() {
        long[] jArr;
        int i;
        int zzg = super.zzg();
        if (!Arrays.equals(this.zzgd, zzw.zzhy)) {
            byte[] bArr = this.zzgd;
            zzg += zzl.zzk(1) + zzl.zzm(bArr.length) + bArr.length;
        }
        String str = this.zzge;
        if (str != null && !str.equals("")) {
            zzg += zzl.zzb(2, this.zzge);
        }
        if (Double.doubleToLongBits(this.zzgf) != Double.doubleToLongBits(0.0d)) {
            zzg += zzl.zzk(3) + 8;
        }
        if (Float.floatToIntBits(this.zzgg) != Float.floatToIntBits(0.0f)) {
            zzg += zzl.zzk(4) + 4;
        }
        long j = this.zzgh;
        if (j != 0) {
            zzg += zzl.zzb(5, j);
        }
        int i2 = this.zzgi;
        if (i2 != 0) {
            zzg += zzl.zze(6, i2);
        }
        int i3 = this.zzgj;
        if (i3 != 0) {
            zzg += zzl.zzk(7) + zzl.zzm(zzl.zzn(i3));
        }
        if (this.zzgk) {
            zzg += zzl.zzk(8) + 1;
        }
        zzh[] zzhArr = this.zzgl;
        int i4 = 0;
        if (zzhArr != null && zzhArr.length > 0) {
            int i5 = 0;
            while (true) {
                zzh[] zzhArr2 = this.zzgl;
                if (i5 >= zzhArr2.length) {
                    break;
                }
                zzh zzh = zzhArr2[i5];
                if (zzh != null) {
                    zzg += zzl.zzb(9, (zzt) zzh);
                }
                i5++;
            }
        }
        zzi[] zziArr = this.zzgm;
        if (zziArr != null && zziArr.length > 0) {
            int i6 = 0;
            while (true) {
                zzi[] zziArr2 = this.zzgm;
                if (i6 >= zziArr2.length) {
                    break;
                }
                zzi zzi = zziArr2[i6];
                if (zzi != null) {
                    zzg += zzl.zzb(10, (zzt) zzi);
                }
                i6++;
            }
        }
        String[] strArr = this.zzgn;
        if (strArr != null && strArr.length > 0) {
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            while (true) {
                String[] strArr2 = this.zzgn;
                if (i7 >= strArr2.length) {
                    break;
                }
                String str2 = strArr2[i7];
                if (str2 != null) {
                    i9++;
                    i8 += zzl.zzg(str2);
                }
                i7++;
            }
            zzg = zzg + i8 + (i9 * 1);
        }
        long[] jArr2 = this.zzgo;
        if (jArr2 != null && jArr2.length > 0) {
            int i10 = 0;
            while (true) {
                jArr = this.zzgo;
                if (i4 >= jArr.length) {
                    break;
                }
                long j2 = jArr[i4];
                if ((j2 & -128) == 0) {
                    i = 1;
                } else if ((j2 & -16384) == 0) {
                    i = 2;
                } else if ((j2 & -2097152) == 0) {
                    i = 3;
                } else if ((j2 & -268435456) == 0) {
                    i = 4;
                } else if ((j2 & -34359738368L) == 0) {
                    i = 5;
                } else if ((j2 & -4398046511104L) == 0) {
                    i = 6;
                } else if ((j2 & -562949953421312L) == 0) {
                    i = 7;
                } else if ((j2 & -72057594037927936L) == 0) {
                    i = 8;
                } else if ((j2 & Long.MIN_VALUE) == 0) {
                    i = 9;
                } else {
                    i = 10;
                }
                i10 += i;
                i4++;
            }
            zzg = zzg + i10 + (jArr.length * 1);
        }
        long j3 = this.zzgq;
        if (j3 != 0) {
            zzg += zzl.zzb(13, j3);
        }
        float[] fArr = this.zzgp;
        if (fArr == null || fArr.length <= 0) {
            return zzg;
        }
        return zzg + (fArr.length * 4) + (fArr.length * 1);
    }

    public final /* synthetic */ zzt zza(zzk zzk) {
        while (true) {
            int zzj = zzk.zzj();
            boolean z = true;
            switch (zzj) {
                case 0:
                    return this;
                case 10:
                    this.zzgd = zzk.readBytes();
                    break;
                case 18:
                    this.zzge = zzk.readString();
                    break;
                case 25:
                    this.zzgf = Double.longBitsToDouble(zzk.zzn());
                    break;
                case 37:
                    this.zzgg = Float.intBitsToFloat(zzk.zzm());
                    break;
                case 40:
                    this.zzgh = zzk.zzl();
                    break;
                case 48:
                    this.zzgi = zzk.zzk();
                    break;
                case 56:
                    int zzk2 = zzk.zzk();
                    this.zzgj = (-(zzk2 & 1)) ^ (zzk2 >>> 1);
                    break;
                case 64:
                    if (zzk.zzk() == 0) {
                        z = false;
                    }
                    this.zzgk = z;
                    break;
                case 74:
                    int zzb = zzw.zzb(zzk, 74);
                    zzh[] zzhArr = this.zzgl;
                    int length = zzhArr == null ? 0 : zzhArr.length;
                    int i = zzb + length;
                    zzh[] zzhArr2 = new zzh[i];
                    if (length != 0) {
                        System.arraycopy(zzhArr, 0, zzhArr2, 0, length);
                    }
                    while (length < i - 1) {
                        zzhArr2[length] = new zzh();
                        zzk.zza(zzhArr2[length]);
                        zzk.zzj();
                        length++;
                    }
                    zzhArr2[length] = new zzh();
                    zzk.zza(zzhArr2[length]);
                    this.zzgl = zzhArr2;
                    break;
                case 82:
                    int zzb2 = zzw.zzb(zzk, 82);
                    zzi[] zziArr = this.zzgm;
                    int length2 = zziArr == null ? 0 : zziArr.length;
                    int i2 = zzb2 + length2;
                    zzi[] zziArr2 = new zzi[i2];
                    if (length2 != 0) {
                        System.arraycopy(zziArr, 0, zziArr2, 0, length2);
                    }
                    while (length2 < i2 - 1) {
                        zziArr2[length2] = new zzi();
                        zzk.zza(zziArr2[length2]);
                        zzk.zzj();
                        length2++;
                    }
                    zziArr2[length2] = new zzi();
                    zzk.zza(zziArr2[length2]);
                    this.zzgm = zziArr2;
                    break;
                case 90:
                    int zzb3 = zzw.zzb(zzk, 90);
                    String[] strArr = this.zzgn;
                    int length3 = strArr == null ? 0 : strArr.length;
                    int i3 = zzb3 + length3;
                    String[] strArr2 = new String[i3];
                    if (length3 != 0) {
                        System.arraycopy(strArr, 0, strArr2, 0, length3);
                    }
                    while (length3 < i3 - 1) {
                        strArr2[length3] = zzk.readString();
                        zzk.zzj();
                        length3++;
                    }
                    strArr2[length3] = zzk.readString();
                    this.zzgn = strArr2;
                    break;
                case 96:
                    int zzb4 = zzw.zzb(zzk, 96);
                    long[] jArr = this.zzgo;
                    int length4 = jArr == null ? 0 : jArr.length;
                    int i4 = zzb4 + length4;
                    long[] jArr2 = new long[i4];
                    if (length4 != 0) {
                        System.arraycopy(jArr, 0, jArr2, 0, length4);
                    }
                    while (length4 < i4 - 1) {
                        jArr2[length4] = zzk.zzl();
                        zzk.zzj();
                        length4++;
                    }
                    jArr2[length4] = zzk.zzl();
                    this.zzgo = jArr2;
                    break;
                case 98:
                    int zze = zzk.zze(zzk.zzk());
                    int position = zzk.getPosition();
                    int i5 = 0;
                    while (zzk.zzp() > 0) {
                        zzk.zzl();
                        i5++;
                    }
                    zzk.zzg(position);
                    long[] jArr3 = this.zzgo;
                    int length5 = jArr3 == null ? 0 : jArr3.length;
                    int i6 = i5 + length5;
                    long[] jArr4 = new long[i6];
                    if (length5 != 0) {
                        System.arraycopy(jArr3, 0, jArr4, 0, length5);
                    }
                    while (length5 < i6) {
                        jArr4[length5] = zzk.zzl();
                        length5++;
                    }
                    this.zzgo = jArr4;
                    zzk.zzf(zze);
                    break;
                case 104:
                    this.zzgq = zzk.zzl();
                    break;
                case 114:
                    int zzk3 = zzk.zzk();
                    int zze2 = zzk.zze(zzk3);
                    int i7 = zzk3 / 4;
                    float[] fArr = this.zzgp;
                    int length6 = fArr == null ? 0 : fArr.length;
                    int i8 = i7 + length6;
                    float[] fArr2 = new float[i8];
                    if (length6 != 0) {
                        System.arraycopy(fArr, 0, fArr2, 0, length6);
                    }
                    while (length6 < i8) {
                        fArr2[length6] = Float.intBitsToFloat(zzk.zzm());
                        length6++;
                    }
                    this.zzgp = fArr2;
                    zzk.zzf(zze2);
                    break;
                case 117:
                    int zzb5 = zzw.zzb(zzk, 117);
                    float[] fArr3 = this.zzgp;
                    int length7 = fArr3 == null ? 0 : fArr3.length;
                    int i9 = zzb5 + length7;
                    float[] fArr4 = new float[i9];
                    if (length7 != 0) {
                        System.arraycopy(fArr3, 0, fArr4, 0, length7);
                    }
                    while (length7 < i9 - 1) {
                        fArr4[length7] = Float.intBitsToFloat(zzk.zzm());
                        zzk.zzj();
                        length7++;
                    }
                    fArr4[length7] = Float.intBitsToFloat(zzk.zzm());
                    this.zzgp = fArr4;
                    break;
                default:
                    if (super.zza(zzk, zzj)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }
}
