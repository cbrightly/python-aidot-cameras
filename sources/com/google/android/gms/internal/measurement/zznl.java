package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zznl {
    private static final zznl zza = new zznl(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zznl() {
        this(0, new int[8], new Object[8], true);
    }

    private zznl(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zznl zzc() {
        return zza;
    }

    static zznl zze(zznl zznl, zznl zznl2) {
        int i = zznl.zzb + zznl2.zzb;
        int[] copyOf = Arrays.copyOf(zznl.zzc, i);
        System.arraycopy(zznl2.zzc, 0, copyOf, zznl.zzb, zznl2.zzb);
        Object[] copyOf2 = Arrays.copyOf(zznl.zzd, i);
        System.arraycopy(zznl2.zzd, 0, copyOf2, zznl.zzb, zznl2.zzb);
        return new zznl(i, copyOf, copyOf2, true);
    }

    static zznl zzf() {
        return new zznl(0, new int[8], new Object[8], true);
    }

    private final void zzl(int i) {
        int[] iArr = this.zzc;
        if (i > iArr.length) {
            int i2 = this.zzb;
            int i3 = i2 + (i2 / 2);
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.zzc = Arrays.copyOf(iArr, i);
            this.zzd = Arrays.copyOf(this.zzd, i);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zznl)) {
            return false;
        }
        zznl zznl = (zznl) obj;
        int i = this.zzb;
        if (i == zznl.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zznl.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zznl.zzd;
                    int i3 = this.zzb;
                    int i4 = 0;
                    while (i4 < i3) {
                        if (objArr[i4].equals(objArr2[i4])) {
                            i4++;
                        }
                    }
                    return true;
                } else if (iArr[i2] != iArr2[i2]) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = i + 527;
        int[] iArr = this.zzc;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 * 31) + i4;
        Object[] objArr = this.zzd;
        int i7 = this.zzb;
        for (int i8 = 0; i8 < i7; i8++) {
            i3 = (i3 * 31) + objArr[i8].hashCode();
        }
        return (i6 * 31) + i3;
    }

    public final int zza() {
        int i;
        int i2 = this.zze;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzb; i4++) {
            int i5 = this.zzc[i4];
            int i6 = i5 >>> 3;
            switch (i5 & 7) {
                case 0:
                    i = zzki.zzx(i6 << 3) + zzki.zzy(((Long) this.zzd[i4]).longValue());
                    break;
                case 1:
                    ((Long) this.zzd[i4]).longValue();
                    i = zzki.zzx(i6 << 3) + 8;
                    break;
                case 2:
                    int i7 = zzki.zzb;
                    int zzd2 = ((zzka) this.zzd[i4]).zzd();
                    i = zzki.zzx(i6 << 3) + zzki.zzx(zzd2) + zzd2;
                    break;
                case 3:
                    int i8 = i6 << 3;
                    int i9 = zzki.zzb;
                    int zza2 = ((zznl) this.zzd[i4]).zza();
                    int zzx = zzki.zzx(i8);
                    i = zzx + zzx + zza2;
                    break;
                case 5:
                    ((Integer) this.zzd[i4]).intValue();
                    i = zzki.zzx(i6 << 3) + 4;
                    break;
                default:
                    throw new IllegalStateException(zzll.zza());
            }
            i3 += i;
        }
        this.zze = i3;
        return i3;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = zzki.zzb;
            int zzd2 = ((zzka) this.zzd[i3]).zzd();
            int zzx = zzki.zzx(zzd2) + zzd2;
            int zzx2 = zzki.zzx(16);
            int zzx3 = zzki.zzx(this.zzc[i3] >>> 3);
            int zzx4 = zzki.zzx(8);
            i2 += zzx4 + zzx4 + zzx2 + zzx3 + zzki.zzx(24) + zzx;
        }
        this.zze = i2;
        return i2;
    }

    /* access modifiers changed from: package-private */
    public final zznl zzd(zznl zznl) {
        if (zznl.equals(zza)) {
            return this;
        }
        zzg();
        int i = this.zzb + zznl.zzb;
        zzl(i);
        System.arraycopy(zznl.zzc, 0, this.zzc, this.zzb, zznl.zzb);
        System.arraycopy(zznl.zzd, 0, this.zzd, this.zzb, zznl.zzb);
        this.zzb = i;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final void zzg() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    public final void zzh() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzi(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzmk.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzj(int i, Object obj) {
        zzg();
        zzl(this.zzb + 1);
        int[] iArr = this.zzc;
        int i2 = this.zzb;
        iArr[i2] = i;
        this.zzd[i2] = obj;
        this.zzb = i2 + 1;
    }

    public final void zzk(zzoc zzoc) {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 & 7;
                int i4 = i2 >>> 3;
                switch (i3) {
                    case 0:
                        zzoc.zzt(i4, ((Long) obj).longValue());
                        break;
                    case 1:
                        zzoc.zzm(i4, ((Long) obj).longValue());
                        break;
                    case 2:
                        zzoc.zzd(i4, (zzka) obj);
                        break;
                    case 3:
                        zzoc.zzE(i4);
                        ((zznl) obj).zzk(zzoc);
                        zzoc.zzh(i4);
                        break;
                    case 5:
                        zzoc.zzk(i4, ((Integer) obj).intValue());
                        break;
                    default:
                        throw new RuntimeException(zzll.zza());
                }
            }
        }
    }
}
