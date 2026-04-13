package com.google.android.gms.internal.vision;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzii extends zzhq {
    private static final Logger zzb = Logger.getLogger(zzii.class.getName());
    /* access modifiers changed from: private */
    public static final boolean zzc = zzma.zza();
    zzil zza;

    public static zzii zza(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public abstract int zza();

    public abstract void zza(byte b);

    public abstract void zza(int i);

    public abstract void zza(int i, int i2);

    public abstract void zza(int i, long j);

    public abstract void zza(int i, zzht zzht);

    public abstract void zza(int i, zzkk zzkk);

    /* access modifiers changed from: package-private */
    public abstract void zza(int i, zzkk zzkk, zzlc zzlc);

    public abstract void zza(int i, String str);

    public abstract void zza(int i, boolean z);

    public abstract void zza(long j);

    public abstract void zza(zzht zzht);

    public abstract void zza(zzkk zzkk);

    public abstract void zza(String str);

    public abstract void zzb(int i);

    public abstract void zzb(int i, int i2);

    public abstract void zzb(int i, zzht zzht);

    /* access modifiers changed from: package-private */
    public abstract void zzb(byte[] bArr, int i, int i2);

    public abstract void zzc(int i, int i2);

    public abstract void zzc(int i, long j);

    public abstract void zzc(long j);

    public abstract void zzd(int i);

    public abstract void zze(int i, int i2);

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zzb extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzb(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        zzb(java.lang.String r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                java.lang.String r0 = "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                java.lang.String r0 = java.lang.String.valueOf(r0)
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r1 = r3.length()
                if (r1 == 0) goto L_0x0015
                java.lang.String r3 = r0.concat(r3)
                goto L_0x001a
            L_0x0015:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r0)
            L_0x001a:
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzii.zzb.<init>(java.lang.String, java.lang.Throwable):void");
        }
    }

    private zzii() {
    }

    public final void zzd(int i, int i2) {
        zzc(i, zzm(i2));
    }

    public final void zzb(int i, long j) {
        zza(i, zzi(j));
    }

    public final void zza(int i, float f) {
        zze(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zza extends zzii {
        private final byte[] zzb;
        private final int zzc;
        private final int zzd;
        private int zze;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            } else if ((i2 | 0 | (bArr.length - i2)) >= 0) {
                this.zzb = bArr;
                this.zzc = 0;
                this.zze = 0;
                this.zzd = i2;
            } else {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), 0, Integer.valueOf(i2)}));
            }
        }

        public final void zza(int i, int i2) {
            zzb((i << 3) | i2);
        }

        public final void zzb(int i, int i2) {
            zza(i, 0);
            zza(i2);
        }

        public final void zzc(int i, int i2) {
            zza(i, 0);
            zzb(i2);
        }

        public final void zze(int i, int i2) {
            zza(i, 5);
            zzd(i2);
        }

        public final void zza(int i, long j) {
            zza(i, 0);
            zza(j);
        }

        public final void zzc(int i, long j) {
            zza(i, 1);
            zzc(j);
        }

        public final void zza(int i, boolean z) {
            zza(i, 0);
            zza(z ? (byte) 1 : 0);
        }

        public final void zza(int i, String str) {
            zza(i, 2);
            zza(str);
        }

        public final void zza(int i, zzht zzht) {
            zza(i, 2);
            zza(zzht);
        }

        public final void zza(zzht zzht) {
            zzb(zzht.zza());
            zzht.zza((zzhq) this);
        }

        public final void zzb(byte[] bArr, int i, int i2) {
            zzb(i2);
            zzc(bArr, 0, i2);
        }

        /* access modifiers changed from: package-private */
        public final void zza(int i, zzkk zzkk, zzlc zzlc) {
            zza(i, 2);
            zzhf zzhf = (zzhf) zzkk;
            int zzi = zzhf.zzi();
            if (zzi == -1) {
                zzi = zzlc.zzb(zzhf);
                zzhf.zzb(zzi);
            }
            zzb(zzi);
            zzlc.zza(zzkk, (zzmr) this.zza);
        }

        public final void zza(int i, zzkk zzkk) {
            zza(1, 3);
            zzc(2, i);
            zza(3, 2);
            zza(zzkk);
            zza(1, 4);
        }

        public final void zzb(int i, zzht zzht) {
            zza(1, 3);
            zzc(2, i);
            zza(3, zzht);
            zza(1, 4);
        }

        public final void zza(zzkk zzkk) {
            zzb(zzkk.zzm());
            zzkk.zza(this);
        }

        public final void zza(byte b) {
            try {
                byte[] bArr = this.zzb;
                int i = this.zze;
                this.zze = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
            }
        }

        public final void zza(int i) {
            if (i >= 0) {
                zzb(i);
            } else {
                zza((long) i);
            }
        }

        public final void zzb(int i) {
            if (!zzii.zzc || zzhi.zza() || zza() < 5) {
                while ((i & -128) != 0) {
                    byte[] bArr = this.zzb;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    bArr[i2] = (byte) ((i & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
                    i >>>= 7;
                }
                try {
                    byte[] bArr2 = this.zzb;
                    int i3 = this.zze;
                    this.zze = i3 + 1;
                    bArr2[i3] = (byte) i;
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
                }
            } else if ((i & -128) == 0) {
                byte[] bArr3 = this.zzb;
                int i4 = this.zze;
                this.zze = i4 + 1;
                zzma.zza(bArr3, (long) i4, (byte) i);
            } else {
                byte[] bArr4 = this.zzb;
                int i5 = this.zze;
                this.zze = i5 + 1;
                zzma.zza(bArr4, (long) i5, (byte) (i | 128));
                int i6 = i >>> 7;
                if ((i6 & -128) == 0) {
                    byte[] bArr5 = this.zzb;
                    int i7 = this.zze;
                    this.zze = i7 + 1;
                    zzma.zza(bArr5, (long) i7, (byte) i6);
                    return;
                }
                byte[] bArr6 = this.zzb;
                int i8 = this.zze;
                this.zze = i8 + 1;
                zzma.zza(bArr6, (long) i8, (byte) (i6 | 128));
                int i9 = i6 >>> 7;
                if ((i9 & -128) == 0) {
                    byte[] bArr7 = this.zzb;
                    int i10 = this.zze;
                    this.zze = i10 + 1;
                    zzma.zza(bArr7, (long) i10, (byte) i9);
                    return;
                }
                byte[] bArr8 = this.zzb;
                int i11 = this.zze;
                this.zze = i11 + 1;
                zzma.zza(bArr8, (long) i11, (byte) (i9 | 128));
                int i12 = i9 >>> 7;
                if ((i12 & -128) == 0) {
                    byte[] bArr9 = this.zzb;
                    int i13 = this.zze;
                    this.zze = i13 + 1;
                    zzma.zza(bArr9, (long) i13, (byte) i12);
                    return;
                }
                byte[] bArr10 = this.zzb;
                int i14 = this.zze;
                this.zze = i14 + 1;
                zzma.zza(bArr10, (long) i14, (byte) (i12 | 128));
                byte[] bArr11 = this.zzb;
                int i15 = this.zze;
                this.zze = i15 + 1;
                zzma.zza(bArr11, (long) i15, (byte) (i12 >>> 7));
            }
        }

        public final void zzd(int i) {
            try {
                byte[] bArr = this.zzb;
                int i2 = this.zze;
                int i3 = i2 + 1;
                this.zze = i3;
                bArr[i2] = (byte) i;
                int i4 = i3 + 1;
                this.zze = i4;
                bArr[i3] = (byte) (i >> 8);
                int i5 = i4 + 1;
                this.zze = i5;
                bArr[i4] = (byte) (i >> 16);
                this.zze = i5 + 1;
                bArr[i5] = (byte) (i >>> 24);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
            }
        }

        public final void zza(long j) {
            if (!zzii.zzc || zza() < 10) {
                while ((j & -128) != 0) {
                    byte[] bArr = this.zzb;
                    int i = this.zze;
                    this.zze = i + 1;
                    bArr[i] = (byte) ((((int) j) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
                    j >>>= 7;
                }
                try {
                    byte[] bArr2 = this.zzb;
                    int i2 = this.zze;
                    this.zze = i2 + 1;
                    bArr2[i2] = (byte) ((int) j);
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
                }
            } else {
                while ((j & -128) != 0) {
                    byte[] bArr3 = this.zzb;
                    int i3 = this.zze;
                    this.zze = i3 + 1;
                    zzma.zza(bArr3, (long) i3, (byte) ((((int) j) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128));
                    j >>>= 7;
                }
                byte[] bArr4 = this.zzb;
                int i4 = this.zze;
                this.zze = i4 + 1;
                zzma.zza(bArr4, (long) i4, (byte) ((int) j));
            }
        }

        public final void zzc(long j) {
            try {
                byte[] bArr = this.zzb;
                int i = this.zze;
                int i2 = i + 1;
                this.zze = i2;
                bArr[i] = (byte) ((int) j);
                int i3 = i2 + 1;
                this.zze = i3;
                bArr[i2] = (byte) ((int) (j >> 8));
                int i4 = i3 + 1;
                this.zze = i4;
                bArr[i3] = (byte) ((int) (j >> 16));
                int i5 = i4 + 1;
                this.zze = i5;
                bArr[i4] = (byte) ((int) (j >> 24));
                int i6 = i5 + 1;
                this.zze = i6;
                bArr[i5] = (byte) ((int) (j >> 32));
                int i7 = i6 + 1;
                this.zze = i7;
                bArr[i6] = (byte) ((int) (j >> 40));
                int i8 = i7 + 1;
                this.zze = i8;
                bArr[i7] = (byte) ((int) (j >> 48));
                this.zze = i8 + 1;
                bArr[i8] = (byte) ((int) (j >> 56));
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), 1}), e);
            }
        }

        private final void zzc(byte[] bArr, int i, int i2) {
            try {
                System.arraycopy(bArr, i, this.zzb, this.zze, i2);
                this.zze += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(i2)}), e);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) {
            zzc(bArr, i, i2);
        }

        public final void zza(String str) {
            int i = this.zze;
            try {
                int zzg = zzii.zzg(str.length() * 3);
                int zzg2 = zzii.zzg(str.length());
                if (zzg2 == zzg) {
                    int i2 = i + zzg2;
                    this.zze = i2;
                    int zza = zzmd.zza(str, this.zzb, i2, zza());
                    this.zze = i;
                    zzb((zza - i) - zzg2);
                    this.zze = zza;
                    return;
                }
                zzb(zzmd.zza((CharSequence) str));
                this.zze = zzmd.zza(str, this.zzb, this.zze, zza());
            } catch (zzmg e) {
                this.zze = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzb(e2);
            }
        }

        public final int zza() {
            return this.zzd - this.zze;
        }
    }

    public final void zzc(int i) {
        zzb(zzm(i));
    }

    public final void zzb(long j) {
        zza(zzi(j));
    }

    public final void zza(float f) {
        zzd(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) {
        zzc(Double.doubleToRawLongBits(d));
    }

    public final void zza(boolean z) {
        zza(z ? (byte) 1 : 0);
    }

    public static int zzf(int i, int i2) {
        return zzg(i << 3) + zzf(i2);
    }

    public static int zzg(int i, int i2) {
        return zzg(i << 3) + zzg(i2);
    }

    public static int zzh(int i, int i2) {
        return zzg(i << 3) + zzg(zzm(i2));
    }

    public static int zzi(int i, int i2) {
        return zzg(i << 3) + 4;
    }

    public static int zzj(int i, int i2) {
        return zzg(i << 3) + 4;
    }

    public static int zzd(int i, long j) {
        return zzg(i << 3) + zze(j);
    }

    public static int zze(int i, long j) {
        return zzg(i << 3) + zze(j);
    }

    public static int zzf(int i, long j) {
        return zzg(i << 3) + zze(zzi(j));
    }

    public static int zzg(int i, long j) {
        return zzg(i << 3) + 8;
    }

    public static int zzh(int i, long j) {
        return zzg(i << 3) + 8;
    }

    public static int zzb(int i, float f) {
        return zzg(i << 3) + 4;
    }

    public static int zzb(int i, double d) {
        return zzg(i << 3) + 8;
    }

    public static int zzb(int i, boolean z) {
        return zzg(i << 3) + 1;
    }

    public static int zzk(int i, int i2) {
        return zzg(i << 3) + zzf(i2);
    }

    public static int zzb(int i, String str) {
        return zzg(i << 3) + zzb(str);
    }

    public static int zzc(int i, zzht zzht) {
        int zzg = zzg(i << 3);
        int zza2 = zzht.zza();
        return zzg + zzg(zza2) + zza2;
    }

    public static int zza(int i, zzjt zzjt) {
        int zzg = zzg(i << 3);
        int zzb2 = zzjt.zzb();
        return zzg + zzg(zzb2) + zzb2;
    }

    static int zzb(int i, zzkk zzkk, zzlc zzlc) {
        return zzg(i << 3) + zza(zzkk, zzlc);
    }

    public static int zzb(int i, zzkk zzkk) {
        return (zzg(8) << 1) + zzg(2, i) + zzg(24) + zzb(zzkk);
    }

    public static int zzd(int i, zzht zzht) {
        return (zzg(8) << 1) + zzg(2, i) + zzc(3, zzht);
    }

    public static int zzb(int i, zzjt zzjt) {
        return (zzg(8) << 1) + zzg(2, i) + zza(3, zzjt);
    }

    public static int zze(int i) {
        return zzg(i << 3);
    }

    public static int zzf(int i) {
        if (i >= 0) {
            return zzg(i);
        }
        return 10;
    }

    public static int zzg(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        if ((i & -268435456) == 0) {
            return 4;
        }
        return 5;
    }

    public static int zzh(int i) {
        return zzg(zzm(i));
    }

    public static int zzi(int i) {
        return 4;
    }

    public static int zzj(int i) {
        return 4;
    }

    public static int zzd(long j) {
        return zze(j);
    }

    public static int zze(long j) {
        int i;
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            return i + 1;
        }
        return i;
    }

    public static int zzf(long j) {
        return zze(zzi(j));
    }

    public static int zzg(long j) {
        return 8;
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(boolean z) {
        return 1;
    }

    public static int zzk(int i) {
        return zzf(i);
    }

    public static int zzb(String str) {
        int i;
        try {
            i = zzmd.zza((CharSequence) str);
        } catch (zzmg e) {
            i = str.getBytes(zzjf.zza).length;
        }
        return zzg(i) + i;
    }

    public static int zza(zzjt zzjt) {
        int zzb2 = zzjt.zzb();
        return zzg(zzb2) + zzb2;
    }

    public static int zzb(zzht zzht) {
        int zza2 = zzht.zza();
        return zzg(zza2) + zza2;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        return zzg(length) + length;
    }

    public static int zzb(zzkk zzkk) {
        int zzm = zzkk.zzm();
        return zzg(zzm) + zzm;
    }

    static int zza(zzkk zzkk, zzlc zzlc) {
        zzhf zzhf = (zzhf) zzkk;
        int zzi = zzhf.zzi();
        if (zzi == -1) {
            zzi = zzlc.zzb(zzhf);
            zzhf.zzb(zzi);
        }
        return zzg(zzi) + zzi;
    }

    private static int zzm(int i) {
        return (i >> 31) ^ (i << 1);
    }

    private static long zzi(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public final void zzb() {
        if (zza() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(String str, zzmg zzmg) {
        zzb.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzmg);
        byte[] bytes = str.getBytes(zzjf.zza);
        try {
            zzb(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzb(e);
        } catch (zzb e2) {
            throw e2;
        }
    }

    @Deprecated
    static int zzc(int i, zzkk zzkk, zzlc zzlc) {
        int zzg = zzg(i << 3) << 1;
        zzhf zzhf = (zzhf) zzkk;
        int zzi = zzhf.zzi();
        if (zzi == -1) {
            zzi = zzlc.zzb(zzhf);
            zzhf.zzb(zzi);
        }
        return zzg + zzi;
    }

    @Deprecated
    public static int zzc(zzkk zzkk) {
        return zzkk.zzm();
    }

    @Deprecated
    public static int zzl(int i) {
        return zzg(i);
    }
}
