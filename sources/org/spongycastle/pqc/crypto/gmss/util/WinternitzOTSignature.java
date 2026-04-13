package org.spongycastle.pqc.crypto.gmss.util;

import java.lang.reflect.Array;
import org.spongycastle.crypto.Digest;

public class WinternitzOTSignature {
    private Digest a;
    private int b;
    private int c;
    private byte[][] d;
    private int e;
    private GMSSRandom f;
    private int g;
    private int h;

    public WinternitzOTSignature(byte[] seed0, Digest digest, int w) {
        this.e = w;
        this.a = digest;
        this.f = new GMSSRandom(digest);
        int e2 = this.a.e();
        this.b = e2;
        int ceil = (int) Math.ceil(((double) (e2 << 3)) / ((double) w));
        this.g = ceil;
        int a2 = a((ceil << w) + 1);
        this.h = a2;
        int ceil2 = this.g + ((int) Math.ceil(((double) a2) / ((double) w)));
        this.c = ceil2;
        int[] iArr = new int[2];
        iArr[1] = this.b;
        iArr[0] = ceil2;
        this.d = (byte[][]) Array.newInstance(byte.class, iArr);
        byte[] dummy = new byte[this.b];
        System.arraycopy(seed0, 0, dummy, 0, dummy.length);
        for (int i = 0; i < this.c; i++) {
            this.d[i] = this.f.c(dummy);
        }
    }

    public byte[] b() {
        int i = this.c;
        int i2 = this.b;
        byte[] helppubKey = new byte[(i * i2)];
        byte[] bArr = new byte[i2];
        int two_power_t = 1 << this.e;
        for (int i3 = 0; i3 < this.c; i3++) {
            Digest digest = this.a;
            byte[][] bArr2 = this.d;
            digest.update(bArr2[i3], 0, bArr2[i3].length);
            byte[] help = new byte[this.a.e()];
            this.a.c(help, 0);
            for (int j = 2; j < two_power_t; j++) {
                this.a.update(help, 0, help.length);
                help = new byte[this.a.e()];
                this.a.c(help, 0);
            }
            int j2 = this.b;
            System.arraycopy(help, 0, helppubKey, j2 * i3, j2);
        }
        this.a.update(helppubKey, 0, helppubKey.length);
        byte[] tmp = new byte[this.a.e()];
        this.a.c(tmp, 0);
        return tmp;
    }

    public byte[] c(byte[] message) {
        int i;
        byte[] bArr = message;
        int i2 = this.c;
        int i3 = this.b;
        byte[] sign = new byte[(i2 * i3)];
        byte[] bArr2 = new byte[i3];
        int counter = 0;
        int c2 = 0;
        this.a.update(bArr, 0, bArr.length);
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        int i4 = this.e;
        if (8 % i4 == 0) {
            int d2 = 8 / i4;
            int k = (1 << i4) - 1;
            byte[] hlp = new byte[this.b];
            for (int i5 = 0; i5 < hash.length; i5++) {
                for (int j = 0; j < d2; j++) {
                    int test = hash[i5] & k;
                    c2 += test;
                    System.arraycopy(this.d[counter], 0, hlp, 0, this.b);
                    while (test > 0) {
                        this.a.update(hlp, 0, hlp.length);
                        hlp = new byte[this.a.e()];
                        this.a.c(hlp, 0);
                        test--;
                    }
                    int i6 = this.b;
                    System.arraycopy(hlp, 0, sign, counter * i6, i6);
                    hash[i5] = (byte) (hash[i5] >>> this.e);
                    counter++;
                }
            }
            int c3 = (this.g << this.e) - c2;
            int i7 = 0;
            while (i7 < this.h) {
                System.arraycopy(this.d[counter], 0, hlp, 0, this.b);
                for (int test2 = c3 & k; test2 > 0; test2--) {
                    this.a.update(hlp, 0, hlp.length);
                    hlp = new byte[this.a.e()];
                    this.a.c(hlp, 0);
                }
                int i8 = this.b;
                System.arraycopy(hlp, 0, sign, counter * i8, i8);
                int i9 = this.e;
                c3 >>>= i9;
                counter++;
                i7 += i9;
            }
            int i10 = c3;
        } else if (i4 < 8) {
            int i11 = this.b;
            int d3 = i11 / i4;
            int k2 = (1 << i4) - 1;
            byte[] hlp2 = new byte[i11];
            int ii = 0;
            int i12 = 0;
            while (i12 < d3) {
                long big8 = 0;
                int j2 = 0;
                while (j2 < this.e) {
                    big8 ^= (long) ((hash[ii] & 255) << (j2 << 3));
                    ii++;
                    j2++;
                    counter = counter;
                    c2 = c2;
                }
                int counter2 = counter;
                int i13 = c2;
                int j3 = 0;
                while (j3 < 8) {
                    int test3 = (int) (((long) k2) & big8);
                    c2 += test3;
                    System.arraycopy(this.d[counter2], 0, hlp2, 0, this.b);
                    while (test3 > 0) {
                        this.a.update(hlp2, 0, hlp2.length);
                        hlp2 = new byte[this.a.e()];
                        this.a.c(hlp2, 0);
                        test3--;
                    }
                    int i14 = this.b;
                    System.arraycopy(hlp2, 0, sign, counter2 * i14, i14);
                    big8 >>>= this.e;
                    counter2++;
                    j3++;
                    byte[] bArr3 = message;
                }
                i12++;
                byte[] bArr4 = message;
                counter = counter2;
            }
            int counter3 = counter;
            int c4 = c2;
            int d4 = this.b % this.e;
            long big82 = 0;
            for (int j4 = 0; j4 < d4; j4++) {
                big82 ^= (long) ((hash[ii] & 255) << (j4 << 3));
                ii++;
            }
            int d5 = d4 << 3;
            int j5 = 0;
            while (j5 < d5) {
                int test4 = (int) (((long) k2) & big82);
                c4 += test4;
                System.arraycopy(this.d[counter3], 0, hlp2, 0, this.b);
                while (test4 > 0) {
                    this.a.update(hlp2, 0, hlp2.length);
                    hlp2 = new byte[this.a.e()];
                    this.a.c(hlp2, 0);
                    test4--;
                }
                int i15 = this.b;
                System.arraycopy(hlp2, 0, sign, counter3 * i15, i15);
                int i16 = this.e;
                big82 >>>= i16;
                counter3++;
                j5 += i16;
            }
            int c5 = (this.g << this.e) - c4;
            int i17 = 0;
            while (i17 < this.h) {
                System.arraycopy(this.d[counter3], 0, hlp2, 0, this.b);
                for (int test5 = c5 & k2; test5 > 0; test5--) {
                    this.a.update(hlp2, 0, hlp2.length);
                    hlp2 = new byte[this.a.e()];
                    this.a.c(hlp2, 0);
                }
                int i18 = this.b;
                System.arraycopy(hlp2, 0, sign, counter3 * i18, i18);
                int i19 = this.e;
                c5 >>>= i19;
                counter3++;
                i17 += i19;
            }
            int i20 = c5;
            int i21 = counter3;
        } else if (i4 < 57) {
            int i22 = this.b;
            int d6 = (i22 << 3) - i4;
            int k3 = (1 << i4) - 1;
            byte[] hlp3 = new byte[i22];
            int r = 0;
            while (r <= d6) {
                int s = r >>> 3;
                int rest = r % 8;
                int r2 = r + this.e;
                int f2 = (r2 + 7) >>> 3;
                long big83 = 0;
                int ii2 = 0;
                int j6 = s;
                while (j6 < f2) {
                    big83 ^= (long) ((hash[j6] & 255) << (ii2 << 3));
                    ii2++;
                    j6++;
                    d6 = d6;
                    r2 = r2;
                }
                int d7 = d6;
                int r3 = r2;
                long big84 = big83 >>> rest;
                long test8 = ((long) k3) & big84;
                long j7 = big84;
                int c6 = (int) (((long) c2) + test8);
                System.arraycopy(this.d[counter], 0, hlp3, 0, this.b);
                while (test8 > 0) {
                    this.a.update(hlp3, 0, hlp3.length);
                    hlp3 = new byte[this.a.e()];
                    this.a.c(hlp3, 0);
                    test8--;
                }
                int i23 = this.b;
                System.arraycopy(hlp3, 0, sign, counter * i23, i23);
                counter++;
                d6 = d7;
                r = r3;
                c2 = c6;
            }
            int s2 = r >>> 3;
            if (s2 < this.b) {
                int rest2 = r % 8;
                long big85 = 0;
                int ii3 = 0;
                int j8 = s2;
                while (true) {
                    i = this.b;
                    if (j8 >= i) {
                        break;
                    }
                    big85 ^= (long) ((hash[j8] & 255) << (ii3 << 3));
                    ii3++;
                    j8++;
                    r = r;
                    s2 = s2;
                }
                int i24 = r;
                long big86 = big85 >>> rest2;
                long test82 = ((long) k3) & big86;
                long j9 = big86;
                c2 = (int) (((long) c2) + test82);
                System.arraycopy(this.d[counter], 0, hlp3, 0, i);
                while (test82 > 0) {
                    this.a.update(hlp3, 0, hlp3.length);
                    hlp3 = new byte[this.a.e()];
                    this.a.c(hlp3, 0);
                    test82--;
                }
                int i25 = this.b;
                System.arraycopy(hlp3, 0, sign, counter * i25, i25);
                counter++;
            } else {
                int i26 = r;
            }
            int c7 = (this.g << this.e) - c2;
            int i27 = 0;
            while (i27 < this.h) {
                System.arraycopy(this.d[counter], 0, hlp3, 0, this.b);
                for (long test83 = (long) (c7 & k3); test83 > 0; test83--) {
                    this.a.update(hlp3, 0, hlp3.length);
                    hlp3 = new byte[this.a.e()];
                    this.a.c(hlp3, 0);
                }
                int i28 = this.b;
                System.arraycopy(hlp3, 0, sign, counter * i28, i28);
                int i29 = this.e;
                c7 >>>= i29;
                counter++;
                i27 += i29;
            }
            int i30 = c7;
        }
        return sign;
    }

    public int a(int intValue) {
        int log = 1;
        int i = 2;
        while (i < intValue) {
            i <<= 1;
            log++;
        }
        return log;
    }
}
