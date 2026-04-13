package org.spongycastle.pqc.crypto.gmss.util;

import org.spongycastle.crypto.Digest;

public class WinternitzOTSVerify {
    private Digest a;
    private int b;

    public WinternitzOTSVerify(Digest digest, int w) {
        this.b = w;
        this.a = digest;
    }

    public int c() {
        int mdsize = this.a.e();
        int i = this.b;
        int size = ((mdsize << 3) + (i - 1)) / i;
        int logs = b((size << i) + 1);
        int i2 = this.b;
        return mdsize * (size + (((logs + i2) - 1) / i2));
    }

    public byte[] a(byte[] message, byte[] signature) {
        WinternitzOTSVerify winternitzOTSVerify;
        byte[] testKey;
        byte[] bArr;
        int logs;
        int logs2;
        int logs3;
        byte[] bArr2 = message;
        byte[] bArr3 = signature;
        int mdsize = this.a.e();
        byte[] bArr4 = new byte[mdsize];
        this.a.update(bArr2, 0, bArr2.length);
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        int i = this.b;
        int test = ((mdsize << 3) + (i - 1)) / i;
        int logs4 = b((test << i) + 1);
        int i2 = this.b;
        int test2 = (((logs4 + i2) - 1) / i2) + test;
        int testKeySize = mdsize * test2;
        if (testKeySize != bArr3.length) {
            return null;
        }
        byte[] testKey2 = new byte[testKeySize];
        int c = 0;
        int counter = 0;
        if (8 % i2 == 0) {
            int d = 8 / i2;
            int k = (1 << i2) - 1;
            byte[] hlp = new byte[mdsize];
            int i3 = 0;
            while (i3 < hash.length) {
                int j = 0;
                while (j < d) {
                    int test3 = hash[i3] & k;
                    int c2 = c + test3;
                    int keysize = test2;
                    int testKeySize2 = testKeySize;
                    System.arraycopy(bArr3, counter * mdsize, hlp, 0, mdsize);
                    int test4 = test3;
                    while (test4 < k) {
                        this.a.update(hlp, 0, hlp.length);
                        hlp = new byte[this.a.e()];
                        this.a.c(hlp, 0);
                        test4++;
                        c2 = c2;
                        d = d;
                    }
                    int i4 = d;
                    System.arraycopy(hlp, 0, testKey2, counter * mdsize, mdsize);
                    hash[i3] = (byte) (hash[i3] >>> this.b);
                    counter++;
                    j++;
                    c = c2;
                    test2 = keysize;
                    testKeySize = testKeySize2;
                }
                int keysize2 = test2;
                int i5 = testKeySize;
                int i6 = d;
                i3++;
                byte[] bArr5 = message;
            }
            int keysize3 = test2;
            int i7 = testKeySize;
            int i8 = d;
            int i9 = 0;
            int c3 = (test << this.b) - c;
            while (i9 < logs4) {
                System.arraycopy(bArr3, counter * mdsize, hlp, 0, mdsize);
                for (int test5 = c3 & k; test5 < k; test5++) {
                    this.a.update(hlp, 0, hlp.length);
                    hlp = new byte[this.a.e()];
                    this.a.c(hlp, 0);
                }
                System.arraycopy(hlp, 0, testKey2, counter * mdsize, mdsize);
                int i10 = this.b;
                c3 >>>= i10;
                counter++;
                i9 += i10;
            }
            winternitzOTSVerify = this;
            int i11 = test;
            int i12 = logs4;
            testKey = testKey2;
        } else {
            int keysize4 = test2;
            int i13 = testKeySize;
            if (i2 < 8) {
                int d2 = mdsize / i2;
                int k2 = (1 << i2) - 1;
                byte[] hlp2 = new byte[mdsize];
                int ii = 0;
                for (int i14 = 0; i14 < d2; i14++) {
                    long big8 = 0;
                    int j2 = 0;
                    while (j2 < this.b) {
                        big8 ^= (long) ((hash[ii] & 255) << (j2 << 3));
                        ii++;
                        j2++;
                        c = c;
                        counter = counter;
                    }
                    int i15 = counter;
                    int j3 = 0;
                    while (j3 < 8) {
                        int size = test;
                        int logs5 = logs4;
                        int test6 = (int) (big8 & ((long) k2));
                        c += test6;
                        System.arraycopy(bArr3, counter * mdsize, hlp2, 0, mdsize);
                        while (test6 < k2) {
                            this.a.update(hlp2, 0, hlp2.length);
                            hlp2 = new byte[this.a.e()];
                            this.a.c(hlp2, 0);
                            test6++;
                            d2 = d2;
                        }
                        System.arraycopy(hlp2, 0, testKey2, counter * mdsize, mdsize);
                        big8 >>>= this.b;
                        counter++;
                        j3++;
                        test = size;
                        logs4 = logs5;
                        d2 = d2;
                    }
                    int i16 = test;
                    int i17 = logs4;
                }
                int size2 = test;
                int logs6 = logs4;
                int c4 = c;
                int counter2 = counter;
                int d3 = mdsize % this.b;
                long big82 = 0;
                for (int j4 = 0; j4 < d3; j4++) {
                    big82 ^= (long) ((hash[ii] & 255) << (j4 << 3));
                    ii++;
                }
                int d4 = d3 << 3;
                int j5 = 0;
                int c5 = c4;
                int counter3 = counter2;
                while (j5 < d4) {
                    int ii2 = ii;
                    int j6 = j5;
                    int test7 = (int) (((long) k2) & big82);
                    c5 += test7;
                    int d5 = d4;
                    System.arraycopy(bArr3, counter3 * mdsize, hlp2, 0, mdsize);
                    while (test7 < k2) {
                        this.a.update(hlp2, 0, hlp2.length);
                        hlp2 = new byte[this.a.e()];
                        this.a.c(hlp2, 0);
                        test7++;
                        ii2 = ii2;
                    }
                    System.arraycopy(hlp2, 0, testKey2, counter3 * mdsize, mdsize);
                    int i18 = this.b;
                    big82 >>>= i18;
                    counter3++;
                    j5 = j6 + i18;
                    d4 = d5;
                    ii = ii2;
                }
                int i19 = ii;
                int i20 = j5;
                int i21 = 0;
                int c6 = (size2 << this.b) - c5;
                while (true) {
                    logs3 = logs6;
                    if (i21 >= logs3) {
                        break;
                    }
                    int test8 = c6 & k2;
                    System.arraycopy(bArr3, counter3 * mdsize, hlp2, 0, mdsize);
                    while (test8 < k2) {
                        this.a.update(hlp2, 0, hlp2.length);
                        hlp2 = new byte[this.a.e()];
                        this.a.c(hlp2, 0);
                        test8++;
                        big82 = big82;
                    }
                    long big83 = big82;
                    System.arraycopy(hlp2, 0, testKey2, counter3 * mdsize, mdsize);
                    int i22 = this.b;
                    c6 >>>= i22;
                    counter3++;
                    i21 += i22;
                    logs6 = logs3;
                    big82 = big83;
                }
                winternitzOTSVerify = this;
                int i23 = logs3;
                testKey = testKey2;
            } else {
                int size3 = test;
                int logs7 = logs4;
                if (i2 < 57) {
                    int s = (mdsize << 3) - i2;
                    int k3 = (1 << i2) - 1;
                    byte[] hlp3 = new byte[mdsize];
                    int r = 0;
                    while (r <= s) {
                        int s2 = r >>> 3;
                        int rest = r % 8;
                        int r2 = r + this.b;
                        int f = (r2 + 7) >>> 3;
                        long big84 = 0;
                        int ii3 = 0;
                        int d6 = s;
                        int j7 = s2;
                        while (j7 < f) {
                            big84 ^= (long) ((hash[j7] & 255) << (ii3 << 3));
                            ii3++;
                            j7++;
                            r2 = r2;
                            f = f;
                        }
                        int r3 = r2;
                        int i24 = f;
                        long big85 = big84 >>> rest;
                        int i25 = ii3;
                        int i26 = s2;
                        long test82 = ((long) k3) & big85;
                        long j8 = big85;
                        c = (int) (((long) c) + test82);
                        System.arraycopy(bArr3, counter * mdsize, hlp3, 0, mdsize);
                        while (true) {
                            logs2 = logs7;
                            if (test82 >= ((long) k3)) {
                                break;
                            }
                            this.a.update(hlp3, 0, hlp3.length);
                            hlp3 = new byte[this.a.e()];
                            this.a.c(hlp3, 0);
                            test82++;
                            byte[] bArr6 = signature;
                            logs7 = logs2;
                        }
                        System.arraycopy(hlp3, 0, testKey2, counter * mdsize, mdsize);
                        counter++;
                        bArr3 = signature;
                        logs7 = logs2;
                        s = d6;
                        r = r3;
                    }
                    int logs8 = logs7;
                    int i27 = s;
                    int s3 = r >>> 3;
                    if (s3 < mdsize) {
                        int rest2 = r % 8;
                        long big86 = 0;
                        int ii4 = 0;
                        int j9 = s3;
                        while (j9 < mdsize) {
                            big86 ^= (long) ((hash[j9] & 255) << (ii4 << 3));
                            ii4++;
                            j9++;
                            s3 = s3;
                        }
                        long big87 = big86 >>> rest2;
                        long test83 = ((long) k3) & big87;
                        long j10 = big87;
                        c = (int) (((long) c) + test83);
                        bArr = signature;
                        System.arraycopy(bArr, counter * mdsize, hlp3, 0, mdsize);
                        while (true) {
                            testKey = testKey2;
                            if (test83 >= ((long) k3)) {
                                break;
                            }
                            this.a.update(hlp3, 0, hlp3.length);
                            hlp3 = new byte[this.a.e()];
                            this.a.c(hlp3, 0);
                            test83++;
                            testKey2 = testKey;
                            rest2 = rest2;
                        }
                        winternitzOTSVerify = this;
                        int i28 = rest2;
                        System.arraycopy(hlp3, 0, testKey, counter * mdsize, mdsize);
                        counter++;
                    } else {
                        winternitzOTSVerify = this;
                        int i29 = s3;
                        testKey = testKey2;
                        bArr = signature;
                    }
                    int i30 = 0;
                    int c7 = (size3 << winternitzOTSVerify.b) - c;
                    while (true) {
                        logs = logs8;
                        if (i30 >= logs) {
                            break;
                        }
                        long test84 = (long) (c7 & k3);
                        System.arraycopy(bArr, counter * mdsize, hlp3, 0, mdsize);
                        while (true) {
                            logs8 = logs;
                            if (test84 >= ((long) k3)) {
                                break;
                            }
                            winternitzOTSVerify.a.update(hlp3, 0, hlp3.length);
                            hlp3 = new byte[winternitzOTSVerify.a.e()];
                            winternitzOTSVerify.a.c(hlp3, 0);
                            test84++;
                            byte[] bArr7 = signature;
                            logs = logs8;
                        }
                        System.arraycopy(hlp3, 0, testKey, counter * mdsize, mdsize);
                        int i31 = winternitzOTSVerify.b;
                        c7 >>>= i31;
                        counter++;
                        i30 += i31;
                        bArr = signature;
                    }
                } else {
                    winternitzOTSVerify = this;
                    int i32 = logs7;
                    testKey = testKey2;
                }
            }
        }
        byte[] bArr8 = new byte[mdsize];
        winternitzOTSVerify.a.update(testKey, 0, testKey.length);
        byte[] TKey = new byte[winternitzOTSVerify.a.e()];
        winternitzOTSVerify.a.c(TKey, 0);
        return TKey;
    }

    public int b(int intValue) {
        int log = 1;
        int i = 2;
        while (i < intValue) {
            i <<= 1;
            log++;
        }
        return log;
    }
}
