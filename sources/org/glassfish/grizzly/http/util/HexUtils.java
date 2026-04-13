package org.glassfish.grizzly.http.util;

import java.io.ByteArrayOutputStream;

public final class HexUtils {
    static final int[] DEC = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final byte[] HEX = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    static final boolean[] IS_HEX_DIGIT = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    public static byte[] convert(String digits) {
        byte b;
        int i;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i2 = 0;
        while (i2 < digits.length()) {
            char c1 = digits.charAt(i2);
            if (i2 + 1 < digits.length()) {
                char c2 = digits.charAt(i2 + 1);
                if (c1 >= '0' && c1 <= '9') {
                    b = (byte) (((c1 - '0') * 16) + 0);
                } else if (c1 >= 'a' && c1 <= 'f') {
                    b = (byte) ((((c1 - 'a') + 10) * 16) + 0);
                } else if (c1 < 'A' || c1 > 'F') {
                    throw new IllegalArgumentException("hexUtil.bad");
                } else {
                    b = (byte) ((((c1 - 'A') + 10) * 16) + 0);
                }
                if (c2 >= '0' && c2 <= '9') {
                    i = c2 - '0';
                } else if (c2 >= 'a' && c2 <= 'f') {
                    i = (c2 - 'a') + 10;
                } else if (c2 < 'A' || c2 > 'F') {
                    throw new IllegalArgumentException("hexUtil.bad");
                } else {
                    i = (c2 - 'A') + 10;
                }
                baos.write((byte) (i + b));
                i2 += 2;
            } else {
                throw new IllegalArgumentException("hexUtil.odd");
            }
        }
        return baos.toByteArray();
    }

    public static String convert(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(convertDigit(bytes[i] >> 4));
            sb.append(convertDigit(bytes[i] & 15));
        }
        return sb.toString();
    }

    public static int convert2Int(byte[] hex) {
        if (hex.length < 4) {
            return 0;
        }
        int[] iArr = DEC;
        if (iArr[hex[0]] >= 0) {
            int len = iArr[hex[0]] << 4;
            if (iArr[hex[1]] >= 0) {
                int len2 = (len + iArr[hex[1]]) << 4;
                if (iArr[hex[2]] >= 0) {
                    int len3 = (len2 + iArr[hex[2]]) << 4;
                    if (iArr[hex[3]] >= 0) {
                        return len3 + iArr[hex[3]];
                    }
                    throw new IllegalArgumentException("hexUtil.bad");
                }
                throw new IllegalArgumentException("hexUtil.bad");
            }
            throw new IllegalArgumentException("hexUtil.bad");
        }
        throw new IllegalArgumentException("hexUtil.bad");
    }

    public static int[] getDecBytes() {
        return (int[]) DEC.clone();
    }

    private static char convertDigit(int value) {
        int value2 = value & 15;
        if (value2 >= 10) {
            return (char) ((value2 - 10) + 97);
        }
        return (char) (value2 + 48);
    }

    public static boolean isHexDigit(byte c) {
        return IS_HEX_DIGIT[c];
    }

    public static boolean isHexDigit(int c) {
        return IS_HEX_DIGIT[c];
    }

    public static int hexDigit2Dec(byte hexDigit) {
        return DEC[hexDigit];
    }

    public static int hexDigit2Dec(int hexDigit) {
        return DEC[hexDigit];
    }
}
