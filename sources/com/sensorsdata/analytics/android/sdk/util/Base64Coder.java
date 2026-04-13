package com.sensorsdata.analytics.android.sdk.util;

import com.sensorsdata.analytics.android.sdk.SALog;
import java.io.UnsupportedEncodingException;

public class Base64Coder {
    public static final String CHARSET_UTF8 = "UTF-8";
    private static char[] map1 = new char[64];
    private static byte[] map2 = new byte[128];

    static {
        int i = 0;
        char c = 'A';
        while (c <= 'Z') {
            map1[i] = c;
            c = (char) (c + 1);
            i++;
        }
        char c2 = 'a';
        while (c2 <= 'z') {
            map1[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = '0';
        while (c3 <= '9') {
            map1[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        char[] cArr = map1;
        cArr[i] = '+';
        cArr[i + 1] = '/';
        int i2 = 0;
        while (true) {
            byte[] bArr = map2;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = -1;
            i2++;
        }
        for (int i3 = 0; i3 < 64; i3++) {
            map2[map1[i3]] = (byte) i3;
        }
    }

    public static String encodeString(String s) {
        try {
            return new String(encode(s.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static char[] encode(byte[] in) {
        return encode(in, in.length);
    }

    public static char[] encode(byte[] in, int iLen) {
        int ip;
        int ip2;
        int ip3;
        int ip4;
        int i = iLen;
        int oDataLen = ((i * 4) + 2) / 3;
        char[] out = new char[(((i + 2) / 3) * 4)];
        int ip5 = 0;
        int op = 0;
        while (ip5 < i) {
            int ip6 = ip5 + 1;
            int i0 = in[ip5] & 255;
            if (ip6 < i) {
                ip = ip6 + 1;
                ip2 = in[ip6] & 255;
            } else {
                ip = ip6;
                ip2 = 0;
            }
            if (ip < i) {
                ip4 = ip + 1;
                ip3 = in[ip] & 255;
            } else {
                int i2 = ip;
                ip3 = 0;
                ip4 = i2;
            }
            int o2 = ((ip2 & 15) << 2) | (ip3 >>> 6);
            int o3 = ip3 & 63;
            int op2 = op + 1;
            char[] cArr = map1;
            out[op] = cArr[i0 >>> 2];
            int op3 = op2 + 1;
            out[op2] = cArr[((i0 & 3) << 4) | (ip2 >>> 4)];
            char c = '=';
            out[op3] = op3 < oDataLen ? cArr[o2] : '=';
            int op4 = op3 + 1;
            if (op4 < oDataLen) {
                c = cArr[o3];
            }
            out[op4] = c;
            op = op4 + 1;
            ip5 = ip4;
        }
        return out;
    }

    public static String decodeString(String s) {
        return new String(decode(s));
    }

    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r7v3, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r9v3, types: [char] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(char[] r19) {
        /*
            r0 = r19
            int r1 = r0.length
            int r2 = r1 % 4
            if (r2 != 0) goto L_0x00a4
        L_0x0007:
            if (r1 <= 0) goto L_0x0014
            int r2 = r1 + -1
            char r2 = r0[r2]
            r3 = 61
            if (r2 != r3) goto L_0x0014
            int r1 = r1 + -1
            goto L_0x0007
        L_0x0014:
            int r2 = r1 * 3
            int r2 = r2 / 4
            byte[] r3 = new byte[r2]
            r4 = 0
            r5 = 0
        L_0x001c:
            if (r4 >= r1) goto L_0x00a3
            int r6 = r4 + 1
            char r4 = r0[r4]
            int r7 = r6 + 1
            char r6 = r0[r6]
            r8 = 65
            if (r7 >= r1) goto L_0x002f
            int r9 = r7 + 1
            char r7 = r0[r7]
            goto L_0x0031
        L_0x002f:
            r9 = r7
            r7 = r8
        L_0x0031:
            if (r9 >= r1) goto L_0x0038
            int r8 = r9 + 1
            char r9 = r0[r9]
            goto L_0x003d
        L_0x0038:
            r18 = r9
            r9 = r8
            r8 = r18
        L_0x003d:
            java.lang.String r10 = "Illegal character in Base64 encoded data."
            r11 = 127(0x7f, float:1.78E-43)
            if (r4 > r11) goto L_0x0099
            if (r6 > r11) goto L_0x0099
            if (r7 > r11) goto L_0x0099
            if (r9 > r11) goto L_0x0099
            byte[] r11 = map2
            byte r12 = r11[r4]
            byte r13 = r11[r6]
            byte r14 = r11[r7]
            byte r11 = r11[r9]
            if (r12 < 0) goto L_0x008f
            if (r13 < 0) goto L_0x008f
            if (r14 < 0) goto L_0x008f
            if (r11 < 0) goto L_0x008f
            int r10 = r12 << 2
            int r15 = r13 >>> 4
            r10 = r10 | r15
            r15 = r13 & 15
            int r15 = r15 << 4
            int r16 = r14 >>> 2
            r15 = r15 | r16
            r16 = r14 & 3
            int r16 = r16 << 6
            r0 = r16 | r11
            r16 = r1
            int r1 = r5 + 1
            r17 = r4
            byte r4 = (byte) r10
            r3[r5] = r4
            if (r1 >= r2) goto L_0x007f
            int r4 = r1 + 1
            byte r5 = (byte) r15
            r3[r1] = r5
            r1 = r4
        L_0x007f:
            if (r1 >= r2) goto L_0x0088
            int r4 = r1 + 1
            byte r5 = (byte) r0
            r3[r1] = r5
            r5 = r4
            goto L_0x0089
        L_0x0088:
            r5 = r1
        L_0x0089:
            r0 = r19
            r4 = r8
            r1 = r16
            goto L_0x001c
        L_0x008f:
            r16 = r1
            r17 = r4
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r10)
            throw r0
        L_0x0099:
            r16 = r1
            r17 = r4
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r10)
            throw r0
        L_0x00a3:
            return r3
        L_0x00a4:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Length of Base64 encoded input string is not a multiple of 4."
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.Base64Coder.decode(char[]):byte[]");
    }
}
