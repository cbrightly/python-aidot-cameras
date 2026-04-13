package io.netty.util.internal;

import java.io.IOException;

public final class StringUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String[] BYTE2HEX_NOPAD = new String[256];
    private static final String[] BYTE2HEX_PAD = new String[256];
    public static final char CARRIAGE_RETURN = '\r';
    public static final char COMMA = ',';
    private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
    public static final char DOUBLE_QUOTE = '\"';
    public static final String EMPTY_STRING = "";
    public static final char LINE_FEED = '\n';
    public static final String NEWLINE = SystemPropertyUtil.get("line.separator", "\n");
    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    static {
        int i = 0;
        while (i < 10) {
            String[] strArr = BYTE2HEX_PAD;
            strArr[i] = "0" + i;
            BYTE2HEX_NOPAD[i] = String.valueOf(i);
            i++;
        }
        while (i < 16) {
            char c = (char) ((i + 97) - 10);
            String[] strArr2 = BYTE2HEX_PAD;
            strArr2[i] = "0" + c;
            BYTE2HEX_NOPAD[i] = String.valueOf(c);
            i++;
        }
        while (true) {
            String[] strArr3 = BYTE2HEX_PAD;
            if (i < strArr3.length) {
                String str = Integer.toHexString(i);
                strArr3[i] = str;
                BYTE2HEX_NOPAD[i] = str;
                i++;
            } else {
                return;
            }
        }
    }

    private StringUtil() {
    }

    public static String substringAfter(String value, char delim) {
        int pos = value.indexOf(delim);
        if (pos >= 0) {
            return value.substring(pos + 1);
        }
        return null;
    }

    public static boolean commonSuffixOfLength(String s, String p, int len) {
        return s != null && p != null && len >= 0 && s.regionMatches(s.length() - len, p, p.length() - len, len);
    }

    public static String byteToHexStringPadded(int value) {
        return BYTE2HEX_PAD[value & 255];
    }

    public static <T extends Appendable> T byteToHexStringPadded(T buf, int value) {
        try {
            buf.append(byteToHexStringPadded(value));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return buf;
    }

    public static String toHexStringPadded(byte[] src) {
        return toHexStringPadded(src, 0, src.length);
    }

    public static String toHexStringPadded(byte[] src, int offset, int length) {
        return ((StringBuilder) toHexStringPadded(new StringBuilder(length << 1), src, offset, length)).toString();
    }

    public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src) {
        return toHexStringPadded(dst, src, 0, src.length);
    }

    public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src, int offset, int length) {
        int end = offset + length;
        for (int i = offset; i < end; i++) {
            byteToHexStringPadded(dst, src[i]);
        }
        return dst;
    }

    public static String byteToHexString(int value) {
        return BYTE2HEX_NOPAD[value & 255];
    }

    public static <T extends Appendable> T byteToHexString(T buf, int value) {
        try {
            buf.append(byteToHexString(value));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return buf;
    }

    public static String toHexString(byte[] src) {
        return toHexString(src, 0, src.length);
    }

    public static String toHexString(byte[] src, int offset, int length) {
        return ((StringBuilder) toHexString(new StringBuilder(length << 1), src, offset, length)).toString();
    }

    public static <T extends Appendable> T toHexString(T dst, byte[] src) {
        return toHexString(dst, src, 0, src.length);
    }

    public static <T extends Appendable> T toHexString(T dst, byte[] src, int offset, int length) {
        if (length < 0) {
            throw new AssertionError();
        } else if (length == 0) {
            return dst;
        } else {
            int end = offset + length;
            int endMinusOne = end - 1;
            int i = offset;
            while (i < endMinusOne && src[i] == 0) {
                i++;
            }
            int i2 = i + 1;
            byteToHexString(dst, src[i]);
            toHexStringPadded(dst, src, i2, end - i2);
            return dst;
        }
    }

    public static int decodeHexNibble(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - 'A') + 10;
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return (c - 'a') + 10;
    }

    public static byte decodeHexByte(CharSequence s, int pos) {
        int hi = decodeHexNibble(s.charAt(pos));
        int lo = decodeHexNibble(s.charAt(pos + 1));
        if (hi != -1 && lo != -1) {
            return (byte) ((hi << 4) + lo);
        }
        throw new IllegalArgumentException(String.format("invalid hex byte '%s' at index %d of '%s'", new Object[]{s.subSequence(pos, pos + 2), Integer.valueOf(pos), s}));
    }

    public static byte[] decodeHexDump(CharSequence hexDump, int fromIndex, int length) {
        if (length < 0 || (length & 1) != 0) {
            throw new IllegalArgumentException("length: " + length);
        } else if (length == 0) {
            return EmptyArrays.EMPTY_BYTES;
        } else {
            byte[] bytes = new byte[(length >>> 1)];
            for (int i = 0; i < length; i += 2) {
                bytes[i >>> 1] = decodeHexByte(hexDump, fromIndex + i);
            }
            return bytes;
        }
    }

    public static byte[] decodeHexDump(CharSequence hexDump) {
        return decodeHexDump(hexDump, 0, hexDump.length());
    }

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        }
        return simpleClassName(o.getClass());
    }

    public static String simpleClassName(Class<?> clazz) {
        String className = ((Class) ObjectUtil.checkNotNull(clazz, "clazz")).getName();
        int lastDotIdx = className.lastIndexOf(46);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    public static CharSequence escapeCsv(CharSequence value) {
        int length = ((CharSequence) ObjectUtil.checkNotNull(value, "value")).length();
        if (length == 0) {
            return value;
        }
        int last = length - 1;
        boolean quoted = false;
        if (isDoubleQuote(value.charAt(0)) && isDoubleQuote(value.charAt(last)) && length != 1) {
            quoted = true;
        }
        boolean foundSpecialCharacter = false;
        boolean escapedDoubleQuote = false;
        StringBuilder escaped = new StringBuilder(length + 7).append(DOUBLE_QUOTE);
        for (int i = 0; i < length; i++) {
            char current = value.charAt(i);
            switch (current) {
                case 10:
                case 13:
                case ',':
                    break;
                case '\"':
                    if (i != 0 && i != last) {
                        boolean isNextCharDoubleQuote = isDoubleQuote(value.charAt(i + 1));
                        if (!isDoubleQuote(value.charAt(i - 1)) && (!isNextCharDoubleQuote || i + 1 == last)) {
                            escaped.append(DOUBLE_QUOTE);
                            escapedDoubleQuote = true;
                            break;
                        }
                    } else if (!quoted) {
                        escaped.append(DOUBLE_QUOTE);
                        break;
                    } else {
                        continue;
                    }
            }
            foundSpecialCharacter = true;
            escaped.append(current);
            continue;
        }
        if (!escapedDoubleQuote && (!foundSpecialCharacter || quoted)) {
            return value;
        }
        escaped.append(DOUBLE_QUOTE);
        return escaped;
    }

    public static CharSequence unescapeCsv(CharSequence value) {
        int length = ((CharSequence) ObjectUtil.checkNotNull(value, "value")).length();
        if (length == 0) {
            return value;
        }
        int last = length - 1;
        boolean quoted = false;
        if (isDoubleQuote(value.charAt(0)) && isDoubleQuote(value.charAt(last)) && length != 1) {
            quoted = true;
        }
        if (!quoted) {
            validateCsvFormat(value);
            return value;
        }
        StringBuilder unescaped = InternalThreadLocalMap.get().stringBuilder();
        int i = 1;
        while (i < last) {
            char current = value.charAt(i);
            if (current == '\"') {
                if (!isDoubleQuote(value.charAt(i + 1)) || i + 1 == last) {
                    throw newInvalidEscapedCsvFieldException(value, i);
                }
                i++;
            }
            unescaped.append(current);
            i++;
        }
        return unescaped.toString();
    }

    private static void validateCsvFormat(CharSequence value) {
        int length = value.length();
        int i = 0;
        while (i < length) {
            switch (value.charAt(i)) {
                case 10:
                case 13:
                case '\"':
                case ',':
                    throw newInvalidEscapedCsvFieldException(value, i);
                default:
                    i++;
            }
        }
    }

    private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence value, int index) {
        return new IllegalArgumentException("invalid escaped CSV field: " + value + " index: " + index);
    }

    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        return s.length();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isSurrogate(char c) {
        return c >= 55296 && c <= 57343;
    }

    private static boolean isDoubleQuote(char c) {
        return c == '\"';
    }
}
