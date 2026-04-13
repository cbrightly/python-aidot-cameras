package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.Digest;

public class IndexGenerator {
    private byte[] a;
    private int b;
    private int c;
    private int d;
    private int e = 0;
    private int f = 0;
    private BitString g;
    private int h = 0;
    private boolean i;
    private Digest j;
    private int k;

    IndexGenerator(byte[] seed, NTRUEncryptionParameters params) {
        this.a = seed;
        this.b = params.c;
        this.c = params.D4;
        this.d = params.E4;
        Digest digest = params.L4;
        this.j = digest;
        this.k = digest.e();
        this.i = false;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        int i2;
        int i3;
        int i4;
        int i5;
        if (!this.i) {
            this.g = new BitString();
            byte[] hash = new byte[this.j.e()];
            while (true) {
                int i6 = this.h;
                i5 = this.d;
                if (i6 >= i5) {
                    break;
                }
                b(this.g, hash);
                this.h++;
            }
            int i7 = i5 * 8 * this.k;
            this.e = i7;
            this.f = i7;
            this.i = true;
        }
        do {
            this.e += this.c;
            BitString M = this.g.d(this.f);
            int i8 = this.f;
            int i9 = this.c;
            if (i8 < i9) {
                int tmpLen = i9 - i8;
                int i10 = this.h;
                int i11 = this.k;
                int cThreshold = i10 + (((tmpLen + i11) - 1) / i11);
                byte[] hash2 = new byte[this.j.e()];
                while (this.h < cThreshold) {
                    b(M, hash2);
                    this.h++;
                    int i12 = this.k;
                    if (tmpLen > i12 * 8) {
                        tmpLen -= i12 * 8;
                    }
                }
                this.f = (this.k * 8) - tmpLen;
                BitString bitString = new BitString();
                this.g = bitString;
                bitString.b(hash2);
            } else {
                this.f = i8 - i9;
            }
            i2 = M.c(this.c);
            i3 = this.c;
            i4 = this.b;
        } while (i2 >= (1 << i3) - ((1 << i3) % i4));
        return i2 % i4;
    }

    private void b(BitString m, byte[] hash) {
        Digest digest = this.j;
        byte[] bArr = this.a;
        digest.update(bArr, 0, bArr.length);
        e(this.j, this.h);
        this.j.c(hash, 0);
        m.b(hash);
    }

    private void e(Digest hashAlg, int counter) {
        hashAlg.d((byte) (counter >> 24));
        hashAlg.d((byte) (counter >> 16));
        hashAlg.d((byte) (counter >> 8));
        hashAlg.d((byte) counter);
    }

    public static class BitString {
        byte[] a = new byte[4];
        int b;
        int c;

        /* access modifiers changed from: package-private */
        public void b(byte[] bytes) {
            for (int i = 0; i != bytes.length; i++) {
                a(bytes[i]);
            }
        }

        public void a(byte b2) {
            int i = this.b;
            byte[] bArr = this.a;
            if (i == bArr.length) {
                this.a = IndexGenerator.c(bArr, bArr.length * 2);
            }
            int i2 = this.b;
            if (i2 == 0) {
                this.b = 1;
                this.a[0] = b2;
                this.c = 8;
                return;
            }
            int i3 = this.c;
            if (i3 == 8) {
                byte[] bArr2 = this.a;
                this.b = i2 + 1;
                bArr2[i2] = b2;
                return;
            }
            byte[] bArr3 = this.a;
            int i4 = i2 - 1;
            bArr3[i4] = (byte) (((b2 & 255) << i3) | bArr3[i4]);
            this.b = i2 + 1;
            bArr3[i2] = (byte) ((b2 & 255) >> (8 - i3));
        }

        public BitString d(int numBits) {
            int i;
            BitString newStr = new BitString();
            int i2 = (numBits + 7) / 8;
            newStr.b = i2;
            newStr.a = new byte[i2];
            int i3 = 0;
            while (true) {
                i = newStr.b;
                if (i3 >= i) {
                    break;
                }
                newStr.a[i3] = this.a[i3];
                i3++;
            }
            int i4 = numBits % 8;
            newStr.c = i4;
            if (i4 == 0) {
                newStr.c = 8;
            } else {
                int s = 32 - i4;
                byte[] bArr = newStr.a;
                bArr[i - 1] = (byte) ((bArr[i - 1] << s) >>> s);
            }
            return newStr;
        }

        public int c(int numBits) {
            int startBit = (((this.b - 1) * 8) + this.c) - numBits;
            int startByte = startBit / 8;
            int startBitInStartByte = startBit % 8;
            int sum = (this.a[startByte] & 255) >>> startBitInStartByte;
            int shift = 8 - startBitInStartByte;
            for (int i = startByte + 1; i < this.b; i++) {
                sum |= (this.a[i] & 255) << shift;
                shift += 8;
            }
            return sum;
        }
    }

    /* access modifiers changed from: private */
    public static byte[] c(byte[] src, int len) {
        byte[] tmp = new byte[len];
        System.arraycopy(src, 0, tmp, 0, len < src.length ? len : src.length);
        return tmp;
    }
}
