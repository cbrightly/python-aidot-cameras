package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.util.Pack;

public class Permute {
    Permute() {
    }

    protected static int c(int x, int y) {
        return (x << y) | (x >>> (-y));
    }

    public static void b(int rounds, int[] x) {
        int[] iArr = x;
        int i = 16;
        if (iArr.length != 16) {
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
            int x08 = iArr[8];
            int x09 = iArr[9];
            int x10 = iArr[10];
            int x11 = iArr[11];
            int i3 = 12;
            int x12 = iArr[12];
            int x13 = iArr[13];
            int x14 = iArr[14];
            int x15 = iArr[15];
            int i4 = rounds;
            while (i4 > 0) {
                int x002 = x00 + x04;
                int x122 = c(x12 ^ x002, i);
                int x082 = x08 + x122;
                int x042 = c(x04 ^ x082, i3);
                int x003 = x002 + x042;
                int x123 = c(x122 ^ x003, 8);
                int x083 = x082 + x123;
                int x043 = c(x042 ^ x083, i2);
                int x012 = x01 + x05;
                int x132 = c(x13 ^ x012, i);
                int x092 = x09 + x132;
                int x052 = c(x05 ^ x092, i3);
                int x013 = x012 + x052;
                int x133 = c(x132 ^ x013, 8);
                int x093 = x092 + x133;
                int x053 = c(x052 ^ x093, i2);
                int x022 = x02 + x06;
                int x142 = c(x14 ^ x022, i);
                int x102 = x10 + x142;
                int x062 = c(x06 ^ x102, i3);
                int x023 = x022 + x062;
                int x143 = c(x142 ^ x023, 8);
                int x103 = x102 + x143;
                int x063 = c(x062 ^ x103, i2);
                int x032 = x03 + x07;
                int x152 = c(x15 ^ x032, i);
                int x112 = x11 + x152;
                int x072 = c(x07 ^ x112, i3);
                int x033 = x032 + x072;
                int x153 = c(x152 ^ x033, 8);
                int x113 = x112 + x153;
                int x073 = c(x072 ^ x113, i2);
                int x004 = x003 + x053;
                int x154 = c(x153 ^ x004, 16);
                int x104 = x103 + x154;
                int x054 = c(x053 ^ x104, 12);
                x00 = x004 + x054;
                x15 = c(x154 ^ x00, 8);
                x10 = x104 + x15;
                int x055 = c(x054 ^ x10, 7);
                int x014 = x013 + x063;
                int x124 = c(x123 ^ x014, 16);
                int x114 = x113 + x124;
                int x064 = c(x063 ^ x114, 12);
                x01 = x014 + x064;
                x12 = c(x124 ^ x01, 8);
                x11 = x114 + x12;
                x06 = c(x064 ^ x11, 7);
                int x024 = x023 + x073;
                int x134 = c(x133 ^ x024, 16);
                int x084 = x083 + x134;
                int x074 = c(x073 ^ x084, 12);
                x02 = x024 + x074;
                x13 = c(x134 ^ x02, 8);
                x08 = x084 + x13;
                x07 = c(x074 ^ x08, 7);
                int x034 = x033 + x043;
                i = 16;
                int x144 = c(x143 ^ x034, 16);
                int x094 = x093 + x144;
                int x044 = c(x043 ^ x094, 12);
                x03 = x034 + x044;
                x14 = c(x144 ^ x03, 8);
                x09 = x094 + x14;
                x04 = c(x044 ^ x09, 7);
                i4 -= 2;
                x05 = x055;
                i3 = 12;
                i2 = 7;
            }
            iArr[0] = x00;
            iArr[1] = x01;
            iArr[2] = x02;
            iArr[3] = x03;
            iArr[4] = x04;
            iArr[5] = x05;
            iArr[6] = x06;
            iArr[7] = x07;
            iArr[8] = x08;
            iArr[9] = x09;
            iArr[10] = x10;
            iArr[11] = x11;
            iArr[12] = x12;
            iArr[13] = x13;
            iArr[14] = x14;
            iArr[15] = x15;
        } else {
            throw new IllegalArgumentException("Number of rounds must be even");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(byte[] out, byte[] in) {
        int[] x = new int[16];
        for (int i = 0; i < 16; i++) {
            x[i] = Pack.j(in, i * 4);
        }
        b(12, x);
        for (int i2 = 0; i2 < 16; i2++) {
            Pack.h(x[i2], out, i2 * 4);
        }
    }
}
