package org.spongycastle.crypto.engines;

import org.spongycastle.util.Pack;

public class ChaChaEngine extends Salsa20Engine {
    public ChaChaEngine() {
    }

    public ChaChaEngine(int rounds) {
        super(rounds);
    }

    public String b() {
        return "ChaCha" + this.d;
    }

    /* access modifiers changed from: protected */
    public void f(long diff) {
        int hi = (int) (diff >>> 32);
        int lo = (int) diff;
        if (hi > 0) {
            int[] iArr = this.f;
            iArr[13] = iArr[13] + hi;
        }
        int[] iArr2 = this.f;
        int oldState = iArr2[12];
        iArr2[12] = iArr2[12] + lo;
        if (oldState != 0 && iArr2[12] < oldState) {
            iArr2[13] = iArr2[13] + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        int[] iArr = this.f;
        int i = iArr[12] + 1;
        iArr[12] = i;
        if (i == 0) {
            iArr[13] = iArr[13] + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void p(long diff) {
        int hi = (int) (diff >>> 32);
        int lo = (int) diff;
        if (hi != 0) {
            int[] iArr = this.f;
            if ((((long) iArr[13]) & 4294967295L) >= (((long) hi) & 4294967295L)) {
                iArr[13] = iArr[13] - hi;
            } else {
                throw new IllegalStateException("attempt to reduce counter past zero.");
            }
        }
        int[] iArr2 = this.f;
        if ((((long) iArr2[12]) & 4294967295L) >= (4294967295L & ((long) lo))) {
            iArr2[12] = iArr2[12] - lo;
        } else if (iArr2[13] != 0) {
            iArr2[13] = iArr2[13] - 1;
            iArr2[12] = iArr2[12] - lo;
        } else {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        int[] iArr = this.f;
        if (iArr[12] == 0 && iArr[13] == 0) {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
        int i = iArr[12] - 1;
        iArr[12] = i;
        if (i == -1) {
            iArr[13] = iArr[13] - 1;
        }
    }

    /* access modifiers changed from: protected */
    public long h() {
        int[] iArr = this.f;
        return (((long) iArr[13]) << 32) | (((long) iArr[12]) & 4294967295L);
    }

    /* access modifiers changed from: protected */
    public void m() {
        int[] iArr = this.f;
        iArr[13] = 0;
        iArr[12] = 0;
    }

    /* access modifiers changed from: protected */
    public void s(byte[] keyBytes, byte[] ivBytes) {
        if (keyBytes != null) {
            if (keyBytes.length == 16 || keyBytes.length == 32) {
                l(keyBytes.length, this.f, 0);
                Pack.k(keyBytes, 0, this.f, 4, 4);
                Pack.k(keyBytes, keyBytes.length - 16, this.f, 8, 4);
            } else {
                throw new IllegalArgumentException(b() + " requires 128 bit or 256 bit key");
            }
        }
        Pack.k(ivBytes, 0, this.f, 14, 2);
    }

    /* access modifiers changed from: protected */
    public void g(byte[] output) {
        t(this.d, this.f, this.g);
        Pack.i(this.g, output, 0);
    }

    public static void t(int rounds, int[] input, int[] x) {
        int[] iArr = input;
        int[] iArr2 = x;
        int i = 16;
        if (iArr.length != 16) {
            throw new IllegalArgumentException();
        } else if (iArr2.length != 16) {
            throw new IllegalArgumentException();
        } else if (rounds % 2 == 0) {
            int x00 = iArr[0];
            int x01 = iArr[1];
            int x02 = iArr[2];
            int x03 = iArr[3];
            int x04 = iArr[4];
            int x05 = iArr[5];
            int x06 = iArr[6];
            int i2 = 7;
            int x07 = iArr[7];
            int i3 = 8;
            int x08 = iArr[8];
            int x09 = iArr[9];
            int x10 = iArr[10];
            int x11 = iArr[11];
            int i4 = 12;
            int x12 = iArr[12];
            int x13 = iArr[13];
            int x14 = iArr[14];
            int x15 = iArr[15];
            int i5 = rounds;
            while (i5 > 0) {
                int x002 = x00 + x04;
                int x122 = Salsa20Engine.q(x12 ^ x002, i);
                int x082 = x08 + x122;
                int x042 = Salsa20Engine.q(x04 ^ x082, i4);
                int x003 = x002 + x042;
                int x123 = Salsa20Engine.q(x122 ^ x003, i3);
                int x083 = x082 + x123;
                int x043 = Salsa20Engine.q(x042 ^ x083, i2);
                int x012 = x01 + x05;
                int x132 = Salsa20Engine.q(x13 ^ x012, i);
                int x092 = x09 + x132;
                int x052 = Salsa20Engine.q(x05 ^ x092, i4);
                int x013 = x012 + x052;
                int x133 = Salsa20Engine.q(x132 ^ x013, i3);
                int x093 = x092 + x133;
                int x053 = Salsa20Engine.q(x052 ^ x093, i2);
                int x022 = x02 + x06;
                int x142 = Salsa20Engine.q(x14 ^ x022, i);
                int x102 = x10 + x142;
                int x062 = Salsa20Engine.q(x06 ^ x102, i4);
                int x023 = x022 + x062;
                int x143 = Salsa20Engine.q(x142 ^ x023, i3);
                int x103 = x102 + x143;
                int x063 = Salsa20Engine.q(x062 ^ x103, i2);
                int x032 = x03 + x07;
                int x152 = Salsa20Engine.q(x15 ^ x032, i);
                int x112 = x11 + x152;
                int x072 = Salsa20Engine.q(x07 ^ x112, 12);
                int x033 = x032 + x072;
                int x153 = Salsa20Engine.q(x152 ^ x033, i3);
                int x113 = x112 + x153;
                int x073 = Salsa20Engine.q(x072 ^ x113, 7);
                int x004 = x003 + x053;
                int x154 = Salsa20Engine.q(x153 ^ x004, 16);
                int x104 = x103 + x154;
                int x054 = Salsa20Engine.q(x053 ^ x104, 12);
                x00 = x004 + x054;
                x15 = Salsa20Engine.q(x154 ^ x00, 8);
                x10 = x104 + x15;
                int x055 = Salsa20Engine.q(x054 ^ x10, 7);
                int x014 = x013 + x063;
                int x124 = Salsa20Engine.q(x123 ^ x014, 16);
                int x114 = x113 + x124;
                int x064 = Salsa20Engine.q(x063 ^ x114, 12);
                x01 = x014 + x064;
                x12 = Salsa20Engine.q(x124 ^ x01, 8);
                x11 = x114 + x12;
                x06 = Salsa20Engine.q(x064 ^ x11, 7);
                int x024 = x023 + x073;
                int x134 = Salsa20Engine.q(x133 ^ x024, 16);
                int x084 = x083 + x134;
                int x074 = Salsa20Engine.q(x073 ^ x084, 12);
                x02 = x024 + x074;
                x13 = Salsa20Engine.q(x134 ^ x02, 8);
                x08 = x084 + x13;
                x07 = Salsa20Engine.q(x074 ^ x08, 7);
                int x034 = x033 + x043;
                i = 16;
                int x144 = Salsa20Engine.q(x143 ^ x034, 16);
                int x094 = x093 + x144;
                int x044 = Salsa20Engine.q(x043 ^ x094, 12);
                x03 = x034 + x044;
                x14 = Salsa20Engine.q(x144 ^ x03, 8);
                x09 = x094 + x14;
                x04 = Salsa20Engine.q(x044 ^ x09, 7);
                i5 -= 2;
                x05 = x055;
                i4 = 12;
                i3 = 8;
                i2 = 7;
            }
            iArr2[0] = iArr[0] + x00;
            iArr2[1] = iArr[1] + x01;
            iArr2[2] = iArr[2] + x02;
            iArr2[3] = iArr[3] + x03;
            iArr2[4] = iArr[4] + x04;
            iArr2[5] = iArr[5] + x05;
            iArr2[6] = x06 + iArr[6];
            iArr2[7] = x07 + iArr[7];
            iArr2[8] = x08 + iArr[8];
            iArr2[9] = x09 + iArr[9];
            iArr2[10] = x10 + iArr[10];
            iArr2[11] = x11 + iArr[11];
            iArr2[12] = x12 + iArr[12];
            iArr2[13] = x13 + iArr[13];
            iArr2[14] = x14 + iArr[14];
            iArr2[15] = x15 + iArr[15];
        } else {
            throw new IllegalArgumentException("Number of rounds must be even");
        }
    }
}
