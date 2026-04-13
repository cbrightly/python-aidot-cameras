package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;

public class URLDecoder {
    public static void decode(DataChunk dataChunk) {
        decode(dataChunk, true);
    }

    public static void decode(DataChunk dataChunk, boolean allowEncodedSlash) {
        decodeAscii(dataChunk, dataChunk, allowEncodedSlash);
    }

    public static void decode(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash) {
        decodeAscii(srcDataChunk, dstDataChunk, allowEncodedSlash);
    }

    /* renamed from: org.glassfish.grizzly.http.util.URLDecoder$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[DataChunk.Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[DataChunk.Type.Bytes.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Buffer.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.String.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Chars.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static void decode(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash, String enc) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[srcDataChunk.getType().ordinal()]) {
            case 1:
                ByteChunk srcByteChunk = srcDataChunk.getByteChunk();
                ByteChunk dstByteChunk = dstDataChunk.getByteChunk();
                if (dstByteChunk != srcByteChunk) {
                    dstByteChunk.allocate(srcByteChunk.getLength(), -1);
                }
                decode(srcByteChunk, dstByteChunk, allowEncodedSlash);
                return;
            case 2:
                BufferChunk srcBufferChunk = srcDataChunk.getBufferChunk();
                if (dstDataChunk != srcDataChunk) {
                    ByteChunk dstByteChunk2 = dstDataChunk.getByteChunk();
                    dstByteChunk2.allocate(srcBufferChunk.getLength(), -1);
                    decode(srcBufferChunk, dstByteChunk2, allowEncodedSlash);
                    return;
                }
                decode(srcBufferChunk, srcBufferChunk, allowEncodedSlash);
                return;
            case 3:
                dstDataChunk.setString(decode(srcDataChunk.toString(), allowEncodedSlash, enc));
                return;
            case 4:
                CharChunk srcCharChunk = srcDataChunk.getCharChunk();
                CharChunk dstCharChunk = dstDataChunk.getCharChunk();
                if (dstDataChunk != srcDataChunk) {
                    dstCharChunk.ensureCapacity(srcCharChunk.getLength());
                    decode(srcCharChunk, dstCharChunk, allowEncodedSlash, enc);
                } else {
                    decode(srcCharChunk, srcCharChunk, allowEncodedSlash, enc);
                }
                dstDataChunk.setChars(dstCharChunk.getChars(), dstCharChunk.getStart(), dstCharChunk.getEnd());
                return;
            default:
                throw new NullPointerException();
        }
    }

    public static void decodeAscii(DataChunk srcDataChunk, DataChunk dstDataChunk, boolean allowEncodedSlash) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[srcDataChunk.getType().ordinal()]) {
            case 1:
                ByteChunk srcByteChunk = srcDataChunk.getByteChunk();
                ByteChunk dstByteChunk = dstDataChunk.getByteChunk();
                if (dstByteChunk != srcByteChunk) {
                    dstByteChunk.allocate(srcByteChunk.getLength(), -1);
                }
                decode(srcByteChunk, dstByteChunk, allowEncodedSlash);
                return;
            case 2:
                BufferChunk srcBufferChunk = srcDataChunk.getBufferChunk();
                if (dstDataChunk != srcDataChunk) {
                    ByteChunk dstByteChunk2 = dstDataChunk.getByteChunk();
                    dstByteChunk2.allocate(srcBufferChunk.getLength(), -1);
                    decode(srcBufferChunk, dstByteChunk2, allowEncodedSlash);
                    return;
                }
                decode(srcBufferChunk, srcBufferChunk, allowEncodedSlash);
                return;
            case 3:
                dstDataChunk.setString(decodeAscii(srcDataChunk.toString(), allowEncodedSlash));
                return;
            case 4:
                CharChunk srcCharChunk = srcDataChunk.getCharChunk();
                CharChunk dstCharChunk = dstDataChunk.getCharChunk();
                if (dstDataChunk != srcDataChunk) {
                    dstCharChunk.ensureCapacity(srcCharChunk.getLength());
                    decodeAscii(srcCharChunk, dstCharChunk, allowEncodedSlash);
                } else {
                    decodeAscii(srcCharChunk, srcCharChunk, allowEncodedSlash);
                }
                dstDataChunk.setChars(dstCharChunk.getChars(), dstCharChunk.getStart(), dstCharChunk.getEnd());
                return;
            default:
                throw new NullPointerException();
        }
    }

    public static void decode(ByteChunk byteChunk, boolean allowEncodedSlash) {
        decode(byteChunk, byteChunk, allowEncodedSlash);
    }

    public static void decode(ByteChunk srcByteChunk, ByteChunk dstByteChunk, boolean allowEncodedSlash) {
        byte[] srcBuffer = srcByteChunk.getBuffer();
        int srcStart = srcByteChunk.getStart();
        int srcEnd = srcByteChunk.getEnd();
        byte[] dstBuffer = dstByteChunk.getBuffer();
        int idx = dstByteChunk.getStart();
        int j = srcStart;
        while (j < srcEnd) {
            byte b = srcBuffer[j];
            if (b == 43) {
                dstBuffer[idx] = 32;
            } else if (b != 37) {
                dstBuffer[idx] = b;
            } else if (j + 2 < srcEnd) {
                byte b1 = srcBuffer[j + 1];
                byte b2 = srcBuffer[j + 2];
                if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + ((char) b1) + "" + ((char) b2));
                }
                j += 2;
                int res = x2c(b1, b2);
                if (allowEncodedSlash || res != 47) {
                    dstBuffer[idx] = (byte) res;
                } else {
                    throw new CharConversionException("Encoded slashes are not allowed");
                }
            } else {
                throw new IllegalStateException("Unexpected termination");
            }
            j++;
            idx++;
        }
        dstByteChunk.setEnd(idx);
    }

    public static void decode(BufferChunk srcBufferChunk, ByteChunk dstByteChunk, boolean allowEncodedSlash) {
        Buffer srcBuffer = srcBufferChunk.getBuffer();
        int srcStart = srcBufferChunk.getStart();
        int srcEnd = srcBufferChunk.getEnd();
        byte[] dstBuffer = dstByteChunk.getBuffer();
        int idx = dstByteChunk.getStart();
        int j = srcStart;
        while (j < srcEnd) {
            byte b = srcBuffer.get(j);
            if (b == 43) {
                dstBuffer[idx] = 32;
            } else if (b != 37) {
                dstBuffer[idx] = b;
            } else if (j + 2 < srcEnd) {
                byte b1 = srcBuffer.get(j + 1);
                byte b2 = srcBuffer.get(j + 2);
                if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + ((char) b1) + "" + ((char) b2));
                }
                j += 2;
                int res = x2c(b1, b2);
                if (allowEncodedSlash || res != 47) {
                    dstBuffer[idx] = (byte) res;
                } else {
                    throw new CharConversionException("Encoded slashes are not allowed");
                }
            } else {
                throw new IllegalStateException("Unexpected termination");
            }
            j++;
            idx++;
        }
        dstByteChunk.setEnd(idx);
    }

    public static void decode(ByteChunk srcByteChunk, BufferChunk dstBufferChunk, boolean allowEncodedSlash) {
        byte[] srcBuffer = srcByteChunk.getBuffer();
        int srcStart = srcByteChunk.getStart();
        int srcEnd = srcByteChunk.getEnd();
        Buffer dstBuffer = dstBufferChunk.getBuffer();
        int idx = dstBufferChunk.getStart();
        int j = srcStart;
        while (j < srcEnd) {
            byte b = srcBuffer[j];
            if (b == 43) {
                dstBuffer.put(idx, (byte) 32);
            } else if (b != 37) {
                dstBuffer.put(idx, b);
            } else if (j + 2 < srcEnd) {
                byte b1 = srcBuffer[j + 1];
                byte b2 = srcBuffer[j + 2];
                if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + ((char) b1) + "" + ((char) b2));
                }
                j += 2;
                int res = x2c(b1, b2);
                if (allowEncodedSlash || res != 47) {
                    dstBuffer.put(idx, (byte) res);
                } else {
                    throw new CharConversionException("Encoded slashes are not allowed");
                }
            } else {
                throw new IllegalStateException("Unexpected termination");
            }
            j++;
            idx++;
        }
        dstBufferChunk.setEnd(idx);
    }

    public static void decode(BufferChunk bufferChunk, boolean allowEncodedSlash) {
        decode(bufferChunk, bufferChunk, allowEncodedSlash);
    }

    public static void decode(BufferChunk srcBufferChunk, BufferChunk dstBufferChunk, boolean allowEncodedSlash) {
        Buffer srcBuffer = srcBufferChunk.getBuffer();
        int srcStart = srcBufferChunk.getStart();
        int srcEnd = srcBufferChunk.getEnd();
        Buffer dstBuffer = dstBufferChunk.getBuffer();
        int idx = dstBufferChunk.getStart();
        int j = srcStart;
        while (j < srcEnd) {
            byte b = srcBuffer.get(j);
            if (b == 43) {
                dstBuffer.put(idx, (byte) 32);
            } else if (b != 37) {
                dstBuffer.put(idx, b);
            } else if (j + 2 < srcEnd) {
                byte b1 = srcBuffer.get(j + 1);
                byte b2 = srcBuffer.get(j + 2);
                if (!HexUtils.isHexDigit(b1) || !HexUtils.isHexDigit(b2)) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + ((char) b1) + "" + ((char) b2));
                }
                j += 2;
                int res = x2c(b1, b2);
                if (allowEncodedSlash || res != 47) {
                    dstBuffer.put(idx, (byte) res);
                } else {
                    throw new CharConversionException("Encoded slashes are not allowed");
                }
            } else {
                throw new IllegalStateException("Unexpected termination");
            }
            j++;
            idx++;
        }
        dstBufferChunk.setEnd(idx);
    }

    public static void decode(CharChunk charChunk, boolean allowEncodedSlash) {
        decodeAscii(charChunk, charChunk, allowEncodedSlash);
    }

    public static void decode(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash) {
        decodeAscii(srcCharChunk, dstCharChunk, allowEncodedSlash);
    }

    public static void decode(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash, String enc) {
        char[] srcBuffer = srcCharChunk.getBuffer();
        int srcStart = srcCharChunk.getStart();
        int srcEnd = srcCharChunk.getEnd();
        int srcLen = srcEnd - srcStart;
        char[] dstBuffer = dstCharChunk.getBuffer();
        int j = srcStart;
        int idx = dstCharChunk.getStart();
        byte[] bytes = null;
        while (j < srcEnd) {
            char c = srcBuffer[j];
            if (c == '+') {
                dstBuffer[idx] = ' ';
                j++;
                String str = enc;
                idx++;
            } else if (c != '%') {
                dstBuffer[idx] = c;
                j++;
                String str2 = enc;
                idx++;
            } else {
                if (bytes == null) {
                    try {
                        bytes = new byte[((srcLen - j) / 3)];
                    } catch (NumberFormatException e) {
                        e = e;
                        String str3 = enc;
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                    } catch (UnsupportedEncodingException e2) {
                        String str4 = enc;
                    }
                }
                int pos = 0;
                while (j + 2 < srcLen && c == '%') {
                    char c1 = srcBuffer[j + 1];
                    char c2 = srcBuffer[j + 2];
                    if (!HexUtils.isHexDigit((int) c1) || !HexUtils.isHexDigit((int) c2)) {
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + c1 + "" + c2);
                    }
                    int v = x2c((int) c1, (int) c2);
                    if (v >= 0) {
                        int pos2 = pos + 1;
                        bytes[pos] = (byte) v;
                        j += 3;
                        if (j < srcLen) {
                            c = srcBuffer[j];
                        }
                        pos = pos2;
                    } else {
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                    }
                }
                if (j < srcLen) {
                    if (c == '%') {
                        throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                    }
                }
                try {
                    String decodedChunk = new String(bytes, 0, pos, enc);
                    if (!allowEncodedSlash) {
                        if (decodedChunk.indexOf(47) != -1) {
                            throw new CharConversionException("Encoded slashes are not allowed");
                        }
                    }
                    int chunkLen = decodedChunk.length();
                    decodedChunk.getChars(0, chunkLen, dstBuffer, idx);
                    idx += chunkLen;
                } catch (NumberFormatException e3) {
                    e = e3;
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                } catch (UnsupportedEncodingException e4) {
                }
            }
        }
        String str5 = enc;
        dstCharChunk.setEnd(idx);
    }

    public static void decodeAscii(CharChunk srcCharChunk, CharChunk dstCharChunk, boolean allowEncodedSlash) {
        char[] srcBuffer = srcCharChunk.getBuffer();
        int srcStart = srcCharChunk.getStart();
        int srcEnd = srcCharChunk.getEnd();
        char[] dstBuffer = dstCharChunk.getBuffer();
        int idx = dstCharChunk.getStart();
        int j = srcStart;
        while (j < srcEnd) {
            char c = srcBuffer[j];
            if (c == '+') {
                dstBuffer[idx] = ' ';
            } else if (c != '%') {
                dstBuffer[idx] = c;
            } else if (j + 2 < srcEnd) {
                char c1 = srcBuffer[j + 1];
                char c2 = srcBuffer[j + 2];
                if (!HexUtils.isHexDigit((int) c1) || !HexUtils.isHexDigit((int) c2)) {
                    throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - %" + c1 + "" + c2);
                }
                j += 2;
                int res = x2c((int) c1, (int) c2);
                if (allowEncodedSlash || res != 47) {
                    dstBuffer[idx] = (char) res;
                } else {
                    throw new CharConversionException("Encoded slashes are not allowed");
                }
            } else {
                throw new IllegalStateException("Unexpected termination");
            }
            j++;
            idx++;
        }
        dstCharChunk.setEnd(idx);
    }

    public static String decode(String str) {
        return decodeAscii(str, true);
    }

    public static String decode(String str, boolean allowEncodedSlash) {
        return decodeAscii(str, allowEncodedSlash);
    }

    public static String decode(String s, boolean allowEncodedSlash, String enc) {
        boolean needToChange = false;
        int numChars = s.length();
        StringBuilder sb = new StringBuilder(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;
        byte[] bytes = null;
        while (i < numChars) {
            char c = s.charAt(i);
            switch (c) {
                case '%':
                    if (bytes == null) {
                        try {
                            bytes = new byte[((numChars - i) / 3)];
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                        } catch (UnsupportedEncodingException e2) {
                        }
                    }
                    int pos = 0;
                    while (i + 2 < numChars && c == '%') {
                        int v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                        if (v >= 0) {
                            int pos2 = pos + 1;
                            bytes[pos] = (byte) v;
                            i += 3;
                            if (i < numChars) {
                                c = s.charAt(i);
                            }
                            pos = pos2;
                        } else {
                            throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                        }
                    }
                    if (i < numChars) {
                        if (c == '%') {
                            throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                        }
                    }
                    String decodedChunk = new String(bytes, 0, pos, enc);
                    if (!allowEncodedSlash) {
                        if (decodedChunk.indexOf(47) != -1) {
                            throw new CharConversionException("Encoded slashes are not allowed");
                        }
                    }
                    sb.append(decodedChunk);
                    needToChange = true;
                    break;
                case '+':
                    sb.append(' ');
                    i++;
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }
        return needToChange ? sb.toString() : s;
    }

    public static String decodeAscii(String str, boolean allowEncodedSlash) {
        if (str == null) {
            return null;
        }
        int mPos = 0;
        int strPos = 0;
        int strLen = str.length();
        StringBuilder dec = null;
        while (strPos < strLen) {
            char metaChar = str.charAt(strPos);
            boolean isNorm = false;
            boolean isPlus = metaChar == '+';
            if (!isPlus && metaChar != '%') {
                isNorm = true;
            }
            if (isNorm) {
                strPos++;
            } else {
                if (dec == null) {
                    dec = new StringBuilder(strLen);
                }
                if (mPos < strPos) {
                    dec.append(str, mPos, strPos);
                }
                if (isPlus) {
                    dec.append(' ');
                    strPos++;
                } else {
                    char res = (char) Integer.parseInt(str.substring(strPos + 1, strPos + 3), 16);
                    if (allowEncodedSlash || res != '/') {
                        dec.append(res);
                        strPos += 3;
                    } else {
                        throw new CharConversionException("Encoded slashes are not allowed");
                    }
                }
                mPos = strPos;
            }
        }
        if (dec == null) {
            return str;
        }
        if (mPos < strPos) {
            dec.append(str, mPos, strPos);
        }
        return dec.toString();
    }

    private static int x2c(byte b1, byte b2) {
        return (HexUtils.hexDigit2Dec(b1) << 4) + HexUtils.hexDigit2Dec(b2);
    }

    private static int x2c(int c1, int c2) {
        return (HexUtils.hexDigit2Dec(c1) << 4) + HexUtils.hexDigit2Dec(c2);
    }
}
