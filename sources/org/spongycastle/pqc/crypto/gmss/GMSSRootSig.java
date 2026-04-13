package org.spongycastle.pqc.crypto.gmss;

import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.lang.reflect.Array;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.encoders.Hex;

public class GMSSRootSig {
    private Digest a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    private GMSSRandom h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private long o;
    private long p;
    private int q;
    private int r;
    private int s;
    private byte[] t;

    public GMSSRootSig(Digest digest, int w, int height) {
        this.a = digest;
        this.h = new GMSSRandom(digest);
        int e2 = this.a.e();
        this.b = e2;
        this.g = w;
        this.s = height;
        this.j = (1 << w) - 1;
        this.i = (int) Math.ceil(((double) (e2 << 3)) / ((double) w));
    }

    public void f(byte[] seed0, byte[] message) {
        int i2;
        byte[] bArr = message;
        this.e = new byte[this.b];
        this.a.update(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[this.a.e()];
        this.e = bArr2;
        this.a.c(bArr2, 0);
        int i3 = this.b;
        byte[] messPart = new byte[i3];
        System.arraycopy(this.e, 0, messPart, 0, i3);
        int sumH = 0;
        int checksumsize = a((this.i << this.g) + 1);
        int i4 = this.g;
        if (8 % i4 == 0) {
            int dt = 8 / i4;
            for (int a2 = 0; a2 < this.b; a2++) {
                for (int b2 = 0; b2 < dt; b2++) {
                    sumH += messPart[a2] & this.j;
                    messPart[a2] = (byte) (messPart[a2] >>> this.g);
                }
            }
            this.r = (this.i << this.g) - sumH;
            int checkPart = this.r;
            int b3 = 0;
            while (b3 < checksumsize) {
                sumH += this.j & checkPart;
                int i5 = this.g;
                checkPart >>>= i5;
                b3 += i5;
            }
        } else if (i4 < 8) {
            int ii = 0;
            int dt2 = this.b / i4;
            for (int i6 = 0; i6 < dt2; i6++) {
                long big8 = 0;
                int j2 = 0;
                while (j2 < this.g) {
                    big8 ^= (long) ((messPart[ii] & 255) << (j2 << 3));
                    ii++;
                    j2++;
                    sumH = sumH;
                }
                for (int j3 = 0; j3 < 8; j3++) {
                    sumH += (int) (((long) this.j) & big8);
                    big8 >>>= this.g;
                }
            }
            int sumH2 = sumH;
            int dt3 = this.b % this.g;
            long big82 = 0;
            for (int j4 = 0; j4 < dt3; j4++) {
                big82 ^= (long) ((messPart[ii] & 255) << (j4 << 3));
                ii++;
            }
            int dt4 = dt3 << 3;
            int j5 = 0;
            while (j5 < dt4) {
                sumH2 += (int) (((long) this.j) & big82);
                int i7 = this.g;
                big82 >>>= i7;
                j5 += i7;
            }
            this.r = (this.i << this.g) - sumH2;
            int checkPart2 = this.r;
            int i8 = 0;
            while (i8 < checksumsize) {
                sumH2 += this.j & checkPart2;
                int i9 = this.g;
                checkPart2 >>>= i9;
                i8 += i9;
            }
            sumH = sumH2;
        } else if (i4 < 57) {
            int r2 = 0;
            while (true) {
                i2 = this.b;
                int i10 = this.g;
                if (r2 > (i2 << 3) - i10) {
                    break;
                }
                int s2 = r2 >>> 3;
                int rest = r2 % 8;
                r2 += i10;
                int f2 = (r2 + 7) >>> 3;
                long big83 = 0;
                int ii2 = 0;
                int j6 = s2;
                while (j6 < f2) {
                    big83 ^= (long) ((messPart[j6] & 255) << (ii2 << 3));
                    ii2++;
                    j6++;
                    s2 = s2;
                }
                sumH = (int) (((long) sumH) + (((long) this.j) & (big83 >>> rest)));
            }
            int s3 = r2 >>> 3;
            if (s3 < i2) {
                int rest2 = r2 % 8;
                long big84 = 0;
                int ii3 = 0;
                for (int j7 = s3; j7 < this.b; j7++) {
                    big84 ^= (long) ((messPart[j7] & 255) << (ii3 << 3));
                    ii3++;
                }
                sumH = (int) (((long) sumH) + (((long) this.j) & (big84 >>> rest2)));
            }
            this.r = (this.i << this.g) - sumH;
            int checkPart3 = this.r;
            int i11 = 0;
            while (i11 < checksumsize) {
                sumH += this.j & checkPart3;
                int i12 = this.g;
                checkPart3 >>>= i12;
                i11 += i12;
            }
        }
        int ceil = this.i + ((int) Math.ceil(((double) checksumsize) / ((double) this.g)));
        this.c = ceil;
        this.q = (int) Math.ceil(((double) (ceil + sumH)) / ((double) (1 << this.s)));
        int i13 = this.c;
        int i14 = this.b;
        this.f = new byte[(i13 * i14)];
        this.m = 0;
        this.l = 0;
        this.n = 0;
        this.o = 0;
        this.k = 0;
        this.d = new byte[i14];
        byte[] bArr3 = new byte[i14];
        this.t = bArr3;
        System.arraycopy(seed0, 0, bArr3, 0, i14);
    }

    public boolean h() {
        for (int s2 = 0; s2 < this.q; s2++) {
            if (this.m < this.c) {
                g();
            }
            if (this.m == this.c) {
                return true;
            }
        }
        return false;
    }

    public byte[] b() {
        return this.f;
    }

    private void g() {
        int f2;
        int i2;
        int i3 = this.g;
        if (8 % i3 == 0) {
            int i4 = this.l;
            if (i4 == 0) {
                this.d = this.h.c(this.t);
                int i5 = this.n;
                if (i5 < this.b) {
                    byte[] bArr = this.e;
                    this.l = bArr[i5] & this.j;
                    bArr[i5] = (byte) (bArr[i5] >>> this.g);
                } else {
                    int i6 = this.r;
                    this.l = this.j & i6;
                    this.r = i6 >>> this.g;
                }
            } else if (i4 > 0) {
                Digest digest = this.a;
                byte[] bArr2 = this.d;
                digest.update(bArr2, 0, bArr2.length);
                byte[] bArr3 = new byte[this.a.e()];
                this.d = bArr3;
                this.a.c(bArr3, 0);
                this.l--;
            }
            if (this.l == 0) {
                byte[] bArr4 = this.d;
                byte[] bArr5 = this.f;
                int i7 = this.m;
                int i8 = this.b;
                System.arraycopy(bArr4, 0, bArr5, i7 * i8, i8);
                int i9 = this.m + 1;
                this.m = i9;
                if (i9 % (8 / this.g) == 0) {
                    this.n++;
                }
            }
        } else if (i3 < 8) {
            int i10 = this.l;
            if (i10 == 0) {
                int i11 = this.m;
                if (i11 % 8 == 0 && this.n < (i2 = this.b)) {
                    this.p = 0;
                    if (i11 < ((i2 / i3) << 3)) {
                        for (int j2 = 0; j2 < this.g; j2++) {
                            long j3 = this.p;
                            byte[] bArr6 = this.e;
                            int i12 = this.n;
                            this.p = j3 ^ ((long) ((bArr6[i12] & 255) << (j2 << 3)));
                            this.n = i12 + 1;
                        }
                    } else {
                        for (int j4 = 0; j4 < this.b % this.g; j4++) {
                            long j5 = this.p;
                            byte[] bArr7 = this.e;
                            int i13 = this.n;
                            this.p = j5 ^ ((long) ((bArr7[i13] & 255) << (j4 << 3)));
                            this.n = i13 + 1;
                        }
                    }
                }
                if (this.m == this.i) {
                    this.p = (long) this.r;
                }
                this.l = (int) (this.p & ((long) this.j));
                this.d = this.h.c(this.t);
            } else if (i10 > 0) {
                Digest digest2 = this.a;
                byte[] bArr8 = this.d;
                digest2.update(bArr8, 0, bArr8.length);
                byte[] bArr9 = new byte[this.a.e()];
                this.d = bArr9;
                this.a.c(bArr9, 0);
                this.l--;
            }
            if (this.l == 0) {
                byte[] bArr10 = this.d;
                byte[] bArr11 = this.f;
                int i14 = this.m;
                int i15 = this.b;
                System.arraycopy(bArr10, 0, bArr11, i14 * i15, i15);
                this.p >>>= this.g;
                this.m++;
            }
        } else if (i3 < 57) {
            long j6 = this.o;
            if (j6 == 0) {
                this.p = 0;
                this.n = 0;
                int i16 = this.k;
                int rest = i16 % 8;
                int s2 = i16 >>> 3;
                int i17 = this.b;
                if (s2 < i17) {
                    if (i16 <= (i17 << 3) - i3) {
                        int i18 = i16 + i3;
                        this.k = i18;
                        f2 = (i18 + 7) >>> 3;
                    } else {
                        int f3 = this.b;
                        this.k = i16 + i3;
                        f2 = f3;
                    }
                    for (int i19 = s2; i19 < f2; i19++) {
                        long j7 = this.p;
                        int i20 = this.n;
                        this.p = j7 ^ ((long) ((this.e[i19] & 255) << (i20 << 3)));
                        this.n = i20 + 1;
                    }
                    long j8 = this.p >>> rest;
                    this.p = j8;
                    this.o = j8 & ((long) this.j);
                } else {
                    int i21 = this.r;
                    this.o = (long) (this.j & i21);
                    this.r = i21 >>> i3;
                }
                this.d = this.h.c(this.t);
            } else if (j6 > 0) {
                Digest digest3 = this.a;
                byte[] bArr12 = this.d;
                digest3.update(bArr12, 0, bArr12.length);
                byte[] bArr13 = new byte[this.a.e()];
                this.d = bArr13;
                this.a.c(bArr13, 0);
                this.o--;
            }
            if (this.o == 0) {
                byte[] bArr14 = this.d;
                byte[] bArr15 = this.f;
                int i22 = this.m;
                int i23 = this.b;
                System.arraycopy(bArr14, 0, bArr15, i22 * i23, i23);
                this.m++;
            }
        }
    }

    public int a(int intValue) {
        int log = 1;
        int i2 = 2;
        while (i2 < intValue) {
            i2 <<= 1;
            log++;
        }
        return log;
    }

    public byte[][] c() {
        int[] iArr = new int[2];
        iArr[1] = this.b;
        iArr[0] = 5;
        byte[][] statByte = (byte[][]) Array.newInstance(byte.class, iArr);
        statByte[0] = this.d;
        statByte[1] = this.t;
        statByte[2] = this.e;
        statByte[3] = this.f;
        statByte[4] = e();
        return statByte;
    }

    public int[] d() {
        return new int[]{this.m, this.l, this.n, this.k, this.q, this.c, this.s, this.g, this.r};
    }

    public byte[] e() {
        long j2 = this.o;
        long j3 = this.p;
        return new byte[]{(byte) ((int) (j2 & 255)), (byte) ((int) ((j2 >> 8) & 255)), (byte) ((int) ((j2 >> 16) & 255)), (byte) ((int) ((j2 >> 24) & 255)), (byte) ((int) ((j2 >> 32) & 255)), (byte) ((int) ((j2 >> 40) & 255)), (byte) ((int) ((j2 >> 48) & 255)), (byte) ((int) ((j2 >> 56) & 255)), (byte) ((int) (j3 & 255)), (byte) ((int) ((j3 >> 8) & 255)), (byte) ((int) ((j3 >> 16) & 255)), (byte) ((int) ((j3 >> 24) & 255)), (byte) ((int) ((j3 >> 32) & 255)), (byte) ((int) ((j3 >> 40) & 255)), (byte) ((int) ((j3 >> 48) & 255)), (byte) ((int) ((j3 >> 56) & 255))};
    }

    public String toString() {
        String out = "" + this.p + JustifyTextView.TWO_CHINESE_BLANK;
        int[] iArr = new int[9];
        int[] statInt = d();
        int[] iArr2 = new int[2];
        iArr2[1] = this.b;
        iArr2[0] = 5;
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, iArr2);
        byte[][] statByte = c();
        for (int i2 = 0; i2 < 9; i2++) {
            out = out + statInt[i2] + " ";
        }
        for (int i3 = 0; i3 < 5; i3++) {
            out = out + new String(Hex.b(statByte[i3])) + " ";
        }
        return out;
    }
}
