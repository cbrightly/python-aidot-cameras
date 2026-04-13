package com.alibaba.fastjson.util;

import java.util.Arrays;

public class Base64 {
    public static final char[] CA;
    public static final int[] IA;

    static {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int iS = charArray.length;
        for (int i = 0; i < iS; i++) {
            IA[CA[i]] = i;
        }
        IA[61] = 0;
    }

    public static byte[] decodeFast(char[] chars, int offset, int charsLen) {
        int sepCnt = 0;
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx < eIx && IA[chars[sIx]] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[chars[eIx]] < 0) {
            eIx--;
        }
        int pad = chars[eIx] == '=' ? chars[eIx + -1] == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (charsLen > 76) {
            if (chars[76] == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[chars[sIx]] << 18) | (iArr[chars[sIx2]] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[chars[sIx3]] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[chars[sIx4]];
            int d2 = d + 1;
            bytes[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[chars[sIx]] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeFast(String chars, int offset, int charsLen) {
        int sepCnt = 0;
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx < eIx && IA[chars.charAt(sIx)] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[chars.charAt(eIx)] < 0) {
            eIx--;
        }
        int pad = chars.charAt(eIx) == '=' ? chars.charAt(eIx + -1) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (charsLen > 76) {
            if (chars.charAt(76) == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[chars.charAt(sIx)] << 18) | (iArr[chars.charAt(sIx2)] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[chars.charAt(sIx3)] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[chars.charAt(sIx4)];
            int d2 = d + 1;
            bytes[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[chars.charAt(sIx)] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeFast(String s) {
        int sLen = s.length();
        int sepCnt = 0;
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx = 0;
        int eIx = sLen - 1;
        while (sIx < eIx && IA[s.charAt(sIx) & 255] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[s.charAt(eIx) & 255] < 0) {
            eIx--;
        }
        int pad = s.charAt(eIx) == '=' ? s.charAt(eIx + -1) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (sLen > 76) {
            if (s.charAt(76) == 13) {
                sepCnt = cCnt / 78;
            }
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int[] iArr = IA;
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (iArr[s.charAt(sIx)] << 18) | (iArr[s.charAt(sIx2)] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (iArr[s.charAt(sIx3)] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | iArr[s.charAt(sIx4)];
            int d2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[s.charAt(sIx)] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }
}
