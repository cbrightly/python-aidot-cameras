package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;

public class IOUtils {
    public static final char[] ASCII_CHARS = {'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
    public static final char[] CA;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    static final char[] DigitOnes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static final char[] DigitTens = {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
    public static final String FASTJSON_COMPATIBLEWITHFIELDNAME = "fastjson.compatibleWithFieldName";
    public static final String FASTJSON_COMPATIBLEWITHJAVABEAN = "fastjson.compatibleWithJavaBean";
    public static final String FASTJSON_PROPERTIES = "fastjson.properties";
    public static final int[] IA;
    public static final Charset UTF8 = Charset.forName("UTF-8");
    static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];
    public static final char[] replaceChars = new char[93];
    static final int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];

    static {
        char c = 0;
        while (true) {
            boolean[] zArr = firstIdentifierFlags;
            if (c >= zArr.length) {
                break;
            }
            if (c >= 'A' && c <= 'Z') {
                zArr[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                zArr[c] = true;
            } else if (c == '_' || c == '$') {
                zArr[c] = true;
            }
            c = (char) (c + 1);
        }
        char c2 = 0;
        while (true) {
            boolean[] zArr2 = identifierFlags;
            if (c2 < zArr2.length) {
                if (c2 >= 'A' && c2 <= 'Z') {
                    zArr2[c2] = true;
                } else if (c2 >= 'a' && c2 <= 'z') {
                    zArr2[c2] = true;
                } else if (c2 == '_') {
                    zArr2[c2] = true;
                } else if (c2 >= '0' && c2 <= '9') {
                    zArr2[c2] = true;
                }
                c2 = (char) (c2 + 1);
            } else {
                try {
                    break;
                } catch (Throwable th) {
                }
            }
        }
        loadPropertiesFromFile();
        byte[] bArr = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_doubleQuotes = bArr;
        byte[] bArr2 = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_singleQuotes = bArr2;
        bArr[0] = 4;
        bArr[1] = 4;
        bArr[2] = 4;
        bArr[3] = 4;
        bArr[4] = 4;
        bArr[5] = 4;
        bArr[6] = 4;
        bArr[7] = 4;
        bArr[8] = 1;
        bArr[9] = 1;
        bArr[10] = 1;
        bArr[11] = 4;
        bArr[12] = 1;
        bArr[13] = 1;
        bArr[34] = 1;
        bArr[92] = 1;
        bArr2[0] = 4;
        bArr2[1] = 4;
        bArr2[2] = 4;
        bArr2[3] = 4;
        bArr2[4] = 4;
        bArr2[5] = 4;
        bArr2[6] = 4;
        bArr2[7] = 4;
        bArr2[8] = 1;
        bArr2[9] = 1;
        bArr2[10] = 1;
        bArr2[11] = 4;
        bArr2[12] = 1;
        bArr2[13] = 1;
        bArr2[92] = 1;
        bArr2[39] = 1;
        for (int i = 14; i <= 31; i++) {
            specicalFlags_doubleQuotes[i] = 4;
            specicalFlags_singleQuotes[i] = 4;
        }
        for (int i2 = NeedPermissionEvent.PER_IPC_SPEAK_PERM; i2 < 160; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 0; i3 < 161; i3++) {
            specicalFlags_doubleQuotesFlags[i3] = specicalFlags_doubleQuotes[i3] != 0;
            specicalFlags_singleQuotesFlags[i3] = specicalFlags_singleQuotes[i3] != 0;
        }
        char[] cArr = replaceChars;
        cArr[0] = '0';
        cArr[1] = '1';
        cArr[2] = '2';
        cArr[3] = '3';
        cArr[4] = '4';
        cArr[5] = '5';
        cArr[6] = '6';
        cArr[7] = '7';
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[11] = 'v';
        cArr[12] = 'f';
        cArr[13] = 'r';
        cArr[34] = StringUtil.DOUBLE_QUOTE;
        cArr[39] = '\'';
        cArr[47] = '/';
        cArr[92] = '\\';
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int iS = charArray.length;
        for (int i4 = 0; i4 < iS; i4++) {
            IA[CA[i4]] = i4;
        }
        IA[61] = 0;
    }

    public static String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
        }
        return prop == null ? DEFAULT_PROPERTIES.getProperty(name) : prop;
    }

    public static void loadPropertiesFromFile() {
        InputStream imputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl != null) {
                    return cl.getResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
                }
                return ClassLoader.getSystemResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
            }
        });
        if (imputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(imputStream);
                imputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
            }
        }
    }

    public static int stringSize(long x) {
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (x < p) {
                return i;
            }
            p *= 10;
        }
        return 19;
    }

    public static void getChars(long i, int index, char[] buf) {
        int charPos = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i > 2147483647L) {
            long q = i / 100;
            int r = (int) (i - (((q << 6) + (q << 5)) + (q << 2)));
            i = q;
            int charPos2 = charPos - 1;
            buf[charPos2] = DigitOnes[r];
            charPos = charPos2 - 1;
            buf[charPos] = DigitTens[r];
        }
        int i2 = (int) i;
        while (i2 >= 65536) {
            int q2 = i2 / 100;
            int r2 = i2 - (((q2 << 6) + (q2 << 5)) + (q2 << 2));
            i2 = q2;
            int charPos3 = charPos - 1;
            buf[charPos3] = DigitOnes[r2];
            charPos = charPos3 - 1;
            buf[charPos] = DigitTens[r2];
        }
        do {
            int q22 = (52429 * i2) >>> 19;
            charPos--;
            buf[charPos] = digits[i2 - ((q22 << 3) + (q22 << 1))];
            i2 = q22;
        } while (i2 != 0);
        if (sign != 0) {
            buf[charPos - 1] = sign;
        }
    }

    public static void getChars(int i, int index, char[] buf) {
        int p = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i >= 65536) {
            int q = i / 100;
            int r = i - (((q << 6) + (q << 5)) + (q << 2));
            i = q;
            int p2 = p - 1;
            buf[p2] = DigitOnes[r];
            p = p2 - 1;
            buf[p] = DigitTens[r];
        }
        do {
            int q2 = (52429 * i) >>> 19;
            p--;
            buf[p] = digits[i - ((q2 << 3) + (q2 << 1))];
            i = q2;
        } while (i != 0);
        if (sign != 0) {
            buf[p - 1] = sign;
        }
    }

    public static void getChars(byte b, int index, char[] buf) {
        int i = b;
        int charPos = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        do {
            int q = (52429 * i) >>> 19;
            charPos--;
            buf[charPos] = digits[i - ((q << 3) + (q << 1))];
            i = q;
        } while (i != 0);
        if (sign != 0) {
            buf[charPos - 1] = sign;
        }
    }

    public static int stringSize(int x) {
        int i = 0;
        while (x > sizeTable[i]) {
            i++;
        }
        return i + 1;
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuf, CharBuffer charByte) {
        try {
            CoderResult cr = charsetDecoder.decode(byteBuf, charByte, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = charsetDecoder.flush(charByte);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
        } catch (CharacterCodingException x) {
            throw new JSONException("utf8 decode error, " + x.getMessage(), x);
        }
    }

    public static boolean firstIdentifier(char ch) {
        boolean[] zArr = firstIdentifierFlags;
        return ch < zArr.length && zArr[ch];
    }

    public static boolean isIdent(char ch) {
        boolean[] zArr = identifierFlags;
        return ch < zArr.length && zArr[ch];
    }

    public static byte[] decodeBase64(char[] chars, int offset, int charsLen) {
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

    public static byte[] decodeBase64(String chars, int offset, int charsLen) {
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

    public static byte[] decodeBase64(String s) {
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

    public static int encodeUTF8(char[] chars, int offset, int len, byte[] bytes) {
        int dp;
        int uc;
        int dp2;
        int sl = offset + len;
        int dp3 = 0;
        int dlASCII = Math.min(len, bytes.length) + 0;
        while (dp < dlASCII && chars[offset] < 128) {
            bytes[dp] = (byte) chars[offset];
            dp3 = dp + 1;
            offset = offset + 1;
        }
        while (offset < sl) {
            int offset2 = offset + 1;
            char c = chars[offset];
            if (c < 128) {
                bytes[dp] = (byte) c;
                offset = offset2;
                dp++;
            } else if (c < 2048) {
                int dp4 = dp + 1;
                bytes[dp] = (byte) ((c >> 6) | Opcodes.CHECKCAST);
                dp = dp4 + 1;
                bytes[dp4] = (byte) ((c & '?') | 128);
                offset = offset2;
            } else if (c < 55296 || c >= 57344) {
                int dp5 = dp + 1;
                bytes[dp] = (byte) ((c >> 12) | 224);
                int dp6 = dp5 + 1;
                bytes[dp5] = (byte) ((63 & (c >> 6)) | 128);
                bytes[dp6] = (byte) ((c & '?') | 128);
                offset = offset2;
                dp = dp6 + 1;
            } else {
                int ip = offset2 - 1;
                if (c < 55296 || c >= 56320) {
                    if (c < 56320 || c >= 57344) {
                        uc = c;
                    } else {
                        bytes[dp] = 63;
                        offset = offset2;
                        dp++;
                    }
                } else if (sl - ip < 2) {
                    uc = -1;
                } else {
                    char d = chars[ip + 1];
                    if (d < 56320 || d >= 57344) {
                        bytes[dp] = 63;
                        offset = offset2;
                        dp++;
                    } else {
                        uc = ((c << 10) + d) - 56613888;
                    }
                }
                if (uc < 0) {
                    dp2 = dp + 1;
                    bytes[dp] = 63;
                } else {
                    int dp7 = dp + 1;
                    bytes[dp] = (byte) ((uc >> 18) | 240);
                    int dp8 = dp7 + 1;
                    bytes[dp7] = (byte) (((uc >> 12) & 63) | 128);
                    int dp9 = dp8 + 1;
                    bytes[dp8] = (byte) ((63 & (uc >> 6)) | 128);
                    bytes[dp9] = (byte) ((uc & 63) | 128);
                    offset2++;
                    dp2 = dp9 + 1;
                }
                offset = offset2;
                dp = dp2;
            }
        }
        return dp;
    }

    public static int decodeUTF8(byte[] sa, int sp, int len, char[] da) {
        int dp;
        int sl = sp + len;
        int dp2 = 0;
        int dlASCII = Math.min(len, da.length);
        while (dp < dlASCII && sa[sp] >= 0) {
            da[dp] = (char) sa[sp];
            dp2 = dp + 1;
            sp = sp + 1;
        }
        while (sp < sl) {
            int sp2 = sp + 1;
            byte sp3 = sa[sp];
            if (sp3 >= 0) {
                da[dp] = (char) sp3;
                sp = sp2;
                dp++;
            } else if ((sp3 >> 5) != -2 || (sp3 & 30) == 0) {
                if ((sp3 >> 4) == -2) {
                    if (sp2 + 1 >= sl) {
                        return -1;
                    }
                    int sp4 = sp2 + 1;
                    byte sp5 = sa[sp2];
                    int sp6 = sp4 + 1;
                    byte b3 = sa[sp4];
                    if ((sp3 == -32 && (sp5 & 224) == 128) || (sp5 & 192) != 128 || (b3 & 192) != 128) {
                        return -1;
                    }
                    char c = (char) (((sp3 << 12) ^ (sp5 << 6)) ^ (-123008 ^ b3));
                    if (c >= 55296 && c < 57344) {
                        return -1;
                    }
                    da[dp] = c;
                    sp = sp6;
                    dp++;
                } else if ((sp3 >> 3) != -2 || sp2 + 2 >= sl) {
                    return -1;
                } else {
                    int sp7 = sp2 + 1;
                    byte sp8 = sa[sp2];
                    int sp9 = sp7 + 1;
                    byte b32 = sa[sp7];
                    int sp10 = sp9 + 1;
                    byte b4 = sa[sp9];
                    int uc = (((sp3 << 18) ^ (sp8 << 12)) ^ (b32 << 6)) ^ (3678080 ^ b4);
                    if ((sp8 & 192) != 128 || (b32 & 192) != 128 || (b4 & 192) != 128 || uc < 65536 || uc >= 1114112) {
                        return -1;
                    }
                    int dp3 = dp + 1;
                    da[dp] = (char) ((uc >>> 10) + 55232);
                    dp = dp3 + 1;
                    da[dp3] = (char) ((uc & 1023) + 56320);
                    sp = sp10;
                }
            } else if (sp2 >= sl) {
                return -1;
            } else {
                int sp11 = sp2 + 1;
                byte sp12 = sa[sp2];
                if ((sp12 & 192) != 128) {
                    return -1;
                }
                da[dp] = (char) (((sp3 << 6) ^ sp12) ^ OTACommand.RESP_ID_VERSION);
                sp = sp11;
                dp++;
            }
        }
        return dp;
    }

    public static String readAll(Reader reader) {
        StringBuilder buf = new StringBuilder();
        try {
            char[] chars = new char[2048];
            while (true) {
                int len = reader.read(chars, 0, chars.length);
                if (len < 0) {
                    return buf.toString();
                }
                buf.append(chars, 0, len);
            }
        } catch (Exception ex) {
            throw new JSONException("read string from reader error", ex);
        }
    }

    public static boolean isValidJsonpQueryParam(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch != '.' && !isIdent(ch)) {
                return false;
            }
        }
        return true;
    }
}
