package org.glassfish.grizzly.http.util;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;

public final class Ascii {
    private static final long INT_OVERFLOW_LIMIT = 214748364;
    private static final long LONG_OVERFLOW_LIMIT = 922337203685477580L;
    static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static int toUpper(int c) {
        return (c < 97 || c > 122) ? c & 255 : (c + 65) - 97;
    }

    public static void toUpper(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            bytes[i] = (byte) ((b < 97 || b > 122) ? b : (b + 65) - 97);
        }
    }

    public static int toLower(int c) {
        return (c < 65 || c > 90) ? c & 255 : (c - 65) + 97;
    }

    public static void toLower(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            bytes[i] = (byte) ((b < 65 || b > 90) ? b : (b - 65) + 97);
        }
    }

    public static boolean isAlpha(int c) {
        return (c >= 97 && c <= 122) || (c >= 65 && c <= 90);
    }

    public static boolean isUpper(int c) {
        return c >= 65 && c <= 90;
    }

    public static boolean isLower(int c) {
        return c >= 97 && c <= 122;
    }

    public static boolean isWhite(int c) {
        return c == 32 || c == 9 || c == 13 || c == 10 || c == 12 || c == 8;
    }

    public static boolean isDigit(int c) {
        return c >= 48 && c <= 57;
    }

    /* renamed from: org.glassfish.grizzly.http.util.Ascii$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[DataChunk.Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[DataChunk.Type.Buffer.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.String.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Chars.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static int parseInt(DataChunk dataChunk) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dataChunk.getType().ordinal()]) {
            case 1:
                BufferChunk bc = dataChunk.getBufferChunk();
                return parseInt(bc.getBuffer(), bc.getStart(), bc.getLength());
            case 2:
                return Integer.parseInt(dataChunk.toString());
            case 3:
                CharChunk cc = dataChunk.getCharChunk();
                return parseInt(cc.getBuffer(), cc.getStart(), cc.getLength());
            default:
                throw new NullPointerException();
        }
    }

    public static int parseInt(DataChunk dataChunk, int offset, int length) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dataChunk.getType().ordinal()]) {
            case 1:
                BufferChunk bc = dataChunk.getBufferChunk();
                return parseInt(bc.getBuffer(), bc.getStart() + offset, length);
            case 2:
                return parseInt(dataChunk.toString(), offset, length);
            case 3:
                CharChunk cc = dataChunk.getCharChunk();
                return parseInt(cc.getBuffer(), cc.getStart() + offset, cc.getLength());
            default:
                throw new NullPointerException();
        }
    }

    public static int parseInt(byte[] b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            byte off3 = b[off];
            int c = off3;
            if (isDigit(off3)) {
                int n = c - 48;
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    byte off5 = b[off2];
                    int c2 = off5;
                    if (isDigit(off5)) {
                        n = ((n * 10) + c2) - 48;
                        off2 = off4;
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } else {
                int n2 = off2;
            }
        }
        throw new NumberFormatException();
    }

    public static int parseInt(char[] b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            char off3 = b[off];
            int c = off3;
            if (isDigit(off3)) {
                int n = c - 48;
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    char off5 = b[off2];
                    int c2 = off5;
                    if (isDigit(off5)) {
                        n = ((n * 10) + c2) - 48;
                        off2 = off4;
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } else {
                int n2 = off2;
            }
        }
        throw new NumberFormatException();
    }

    public static int parseInt(Buffer b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            int off3 = b.get(off);
            int c = off3;
            if (isDigit(off3)) {
                int n = c - 48;
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    int i = b.get(off2);
                    int c2 = i;
                    if (isDigit(i)) {
                        n = ((n * 10) + c2) - 48;
                        off2 = off4;
                    } else {
                        throw new NumberFormatException();
                    }
                }
            } else {
                int n2 = off2;
            }
        }
        throw new NumberFormatException();
    }

    public static int parseInt(String s, int off, int len) {
        if (s != null && len > 0) {
            int off2 = off + 1;
            int off3 = s.charAt(off);
            int c = off3;
            if (isDigit(off3)) {
                int n = c - 48;
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    int off5 = s.charAt(off2);
                    int c2 = off5;
                    if (isDigit(off5) && (((long) n) < INT_OVERFLOW_LIMIT || (((long) n) == INT_OVERFLOW_LIMIT && c2 - 48 < 8))) {
                        n = ((n * 10) + c2) - 48;
                        off2 = off4;
                    }
                }
                throw new NumberFormatException();
            }
            int n2 = off2;
        }
        throw new NumberFormatException();
    }

    public static long parseLong(byte[] b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            byte off3 = b[off];
            int c = off3;
            if (isDigit(off3)) {
                long n = (long) (c - 48);
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    byte off5 = b[off2];
                    int c2 = off5;
                    if (isDigit(off5) && (n < LONG_OVERFLOW_LIMIT || (n == LONG_OVERFLOW_LIMIT && c2 - 48 < 8))) {
                        n = ((10 * n) + ((long) c2)) - 48;
                        off2 = off4;
                    }
                }
                throw new NumberFormatException();
            }
            int i = off2;
        }
        throw new NumberFormatException();
    }

    public static long parseLong(char[] b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            char off3 = b[off];
            int c = off3;
            if (isDigit(off3)) {
                long n = (long) (c - 48);
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    char off5 = b[off2];
                    int c2 = off5;
                    if (isDigit(off5) && (n < LONG_OVERFLOW_LIMIT || (n == LONG_OVERFLOW_LIMIT && c2 - 48 < 8))) {
                        n = ((10 * n) + ((long) c2)) - 48;
                        off2 = off4;
                    }
                }
                throw new NumberFormatException();
            }
            int i = off2;
        }
        throw new NumberFormatException();
    }

    public static long parseLong(String s, int off, int len) {
        if (s != null && len > 0) {
            int off2 = off + 1;
            int off3 = s.charAt(off);
            int c = off3;
            if (isDigit(off3)) {
                long n = (long) (c - 48);
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    int off5 = s.charAt(off2);
                    int c2 = off5;
                    if (isDigit(off5) && (n < LONG_OVERFLOW_LIMIT || (n == LONG_OVERFLOW_LIMIT && c2 - 48 < 8))) {
                        n = ((10 * n) + ((long) c2)) - 48;
                        off2 = off4;
                    }
                }
                throw new NumberFormatException();
            }
            int i = off2;
        }
        throw new NumberFormatException();
    }

    public static long parseLong(Buffer b, int off, int len) {
        if (b != null && len > 0) {
            int off2 = off + 1;
            int off3 = b.get(off);
            int c = off3;
            if (isDigit(off3)) {
                long n = (long) (c - 48);
                while (true) {
                    len--;
                    if (len <= 0) {
                        return n;
                    }
                    int off4 = off2 + 1;
                    int off5 = b.get(off2);
                    int c2 = off5;
                    if (isDigit(off5) && (n < LONG_OVERFLOW_LIMIT || (n == LONG_OVERFLOW_LIMIT && c2 - 48 < 8))) {
                        n = ((10 * n) + ((long) c2)) - 48;
                        off2 = off4;
                    }
                }
                throw new NumberFormatException();
            }
            int i = off2;
        }
        throw new NumberFormatException();
    }

    public static long parseLong(DataChunk dataChunk) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dataChunk.getType().ordinal()]) {
            case 1:
                BufferChunk bc = dataChunk.getBufferChunk();
                return parseLong(bc.getBuffer(), bc.getStart(), bc.getLength());
            case 2:
                return Long.parseLong(dataChunk.toString());
            case 3:
                CharChunk cc = dataChunk.getCharChunk();
                return parseLong(cc.getBuffer(), cc.getStart(), cc.getLength());
            default:
                throw new NullPointerException();
        }
    }

    public static long parseLong(DataChunk dataChunk, int offset, int length) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dataChunk.getType().ordinal()]) {
            case 1:
                BufferChunk bc = dataChunk.getBufferChunk();
                return parseLong(bc.getBuffer(), bc.getStart() + offset, length);
            case 2:
                return parseLong(dataChunk.toString(), offset, length);
            case 3:
                CharChunk cc = dataChunk.getCharChunk();
                return parseLong(cc.getBuffer(), cc.getStart() + offset, cc.getLength());
            default:
                throw new NullPointerException();
        }
    }

    public static void intToHexString(Buffer buffer, int i) {
        intToUnsignedString(buffer, i, 4);
    }

    public static void intToUnsignedString(Buffer buffer, int value, int shift) {
        if (value == 0) {
            buffer.put((byte) 48);
            return;
        }
        int currentShift = 32 - shift;
        int mask = ((1 << shift) - 1) << currentShift;
        boolean initialZeros = true;
        while (mask != 0) {
            int digit = (value & mask) >>> currentShift;
            if (digit != 0 || !initialZeros) {
                buffer.put((byte) digits[digit]);
                initialZeros = false;
            }
            mask >>>= shift;
            currentShift -= shift;
        }
    }
}
