package org.glassfish.grizzly.http.util;

import com.tencent.bugly.Bugly;
import java.io.CharConversionException;

public final class UDecoder {
    public static final boolean ALLOW_ENCODED_SLASH = Boolean.valueOf(System.getProperty(ALLOW_ENCODED_SLASH_NAME, Bugly.SDK_IS_DEV)).booleanValue();
    private static final String ALLOW_ENCODED_SLASH_NAME = "org.glassfish.grizzly.util.buf.UDecoder.ALLOW_ENCODED_SLASH";
    private boolean allowEncodedSlash;

    public UDecoder() {
        this(ALLOW_ENCODED_SLASH);
    }

    public UDecoder(boolean allowEncodedSlash2) {
        this.allowEncodedSlash = allowEncodedSlash2;
    }

    public void convert(ByteChunk mb) {
        convert(mb, true);
    }

    public void convert(ByteChunk mb, boolean query) {
        convert(mb, query, this.allowEncodedSlash);
    }

    public static void convert(ByteChunk mb, boolean query, boolean allowEncodedSlash2) {
        int start = mb.getStart();
        byte[] buff = mb.getBytes();
        int end = mb.getEnd();
        int idx = ByteChunk.indexOf(buff, start, end, '%');
        int idx2 = -1;
        if (query) {
            idx2 = ByteChunk.indexOf(buff, start, end, '+');
        }
        if (idx >= 0 || idx2 >= 0) {
            if (idx2 >= 0 && idx2 < idx) {
                idx = idx2;
            }
            if (idx < 0) {
                idx = idx2;
            }
            boolean noSlash = !allowEncodedSlash2 && !query;
            int j = idx;
            while (j < end) {
                if (buff[j] == 43 && query) {
                    buff[idx] = 32;
                } else if (buff[j] != 37) {
                    buff[idx] = buff[j];
                } else if (j + 2 < end) {
                    byte b1 = buff[j + 1];
                    byte b2 = buff[j + 2];
                    if (!isHexDigit(b1) || !isHexDigit(b2)) {
                        throw new CharConversionException("isHexDigit");
                    }
                    j += 2;
                    int res = x2c(b1, b2);
                    if (!noSlash || res != 47) {
                        buff[idx] = (byte) res;
                    } else {
                        throw new CharConversionException("Encoded slashes are not allowed by default.  To enable encodedslashes, set the property org.glassfish.grizzly.util.buf.UDecoder.ALLOW_ENCODED_SLASH to true.");
                    }
                } else {
                    throw new CharConversionException("EOF");
                }
                j++;
                idx++;
            }
            ByteChunk byteChunk = mb;
            mb.setEnd(idx);
        }
    }

    public void convert(CharChunk mb) {
        convert(mb, true);
    }

    public static void convert(CharChunk mb, boolean query) {
        int start = mb.getStart();
        char[] buff = mb.getBuffer();
        int cend = mb.getEnd();
        int idx = CharChunk.indexOf(buff, start, cend, '%');
        int idx2 = -1;
        if (query) {
            idx2 = CharChunk.indexOf(buff, start, cend, '+');
        }
        if (idx >= 0 || idx2 >= 0) {
            if (idx2 >= 0 && idx2 < idx) {
                idx = idx2;
            }
            if (idx < 0) {
                idx = idx2;
            }
            int j = idx;
            while (j < cend) {
                char c = buff[j];
                if (c == '+' && query) {
                    buff[idx] = ' ';
                } else if (c != '%') {
                    buff[idx] = c;
                } else if (j + 2 < cend) {
                    char b1 = buff[j + 1];
                    char b2 = buff[j + 2];
                    if (!isHexDigit(b1) || !isHexDigit(b2)) {
                        throw new CharConversionException("isHexDigit");
                    }
                    j += 2;
                    buff[idx] = (char) x2c(b1, b2);
                } else {
                    throw new CharConversionException("EOF");
                }
                j++;
                idx++;
            }
            mb.setEnd(idx);
        }
    }

    public void convert(MessageBytes mb) {
        convert(mb, true);
    }

    public void convert(MessageBytes mb, boolean query) {
        convert(mb, query, this.allowEncodedSlash);
    }

    public static void convert(MessageBytes mb, boolean query, boolean allowEncodingSlash) {
        switch (mb.getType()) {
            case 1:
                String strValue = mb.toString();
                if (strValue != null) {
                    mb.setString(convert(strValue, query));
                    return;
                }
                return;
            case 2:
                convert(mb.getByteChunk(), query, allowEncodingSlash);
                return;
            case 3:
                convert(mb.getCharChunk(), query);
                return;
            default:
                return;
        }
    }

    public static String convert(String str) {
        return convert(str, true);
    }

    public static String convert(String str, boolean query) {
        if (str == null) {
            return null;
        }
        if ((!query || str.indexOf(43) < 0) && str.indexOf(37) < 0) {
            return str;
        }
        StringBuilder dec = new StringBuilder();
        int strPos = 0;
        int strLen = str.length();
        dec.ensureCapacity(str.length());
        while (strPos < strLen) {
            int laPos = strPos;
            while (laPos < strLen) {
                char laChar = str.charAt(laPos);
                if ((laChar == '+' && query) || laChar == '%') {
                    break;
                }
                laPos++;
            }
            if (laPos > strPos) {
                dec.append(str.substring(strPos, laPos));
                strPos = laPos;
            }
            if (strPos >= strLen) {
                break;
            }
            char metaChar = str.charAt(strPos);
            if (metaChar == '+') {
                dec.append(' ');
                strPos++;
            } else if (metaChar == '%') {
                dec.append((char) Integer.parseInt(str.substring(strPos + 1, strPos + 3), 16));
                strPos += 3;
            }
        }
        return dec.toString();
    }

    private static boolean isHexDigit(int c) {
        return (c >= 48 && c <= 57) || (c >= 97 && c <= 102) || (c >= 65 && c <= 70);
    }

    private static int x2c(byte b1, byte b2) {
        return ((b1 >= 65 ? ((b1 & 223) - 65) + 10 : b1 - 48) * 16) + (b2 >= 65 ? ((b2 & 223) - 65) + 10 : b2 - 48);
    }

    private static int x2c(char b1, char b2) {
        return ((b1 >= 'A' ? ((b1 & 223) - 'A') + 10 : b1 - '0') * 16) + (b2 >= 'A' ? ((b2 & 223) - 'A') + 10 : b2 - '0');
    }

    public boolean isAllowEncodedSlash() {
        return this.allowEncodedSlash;
    }

    public void setAllowEncodedSlash(boolean allowEncodedSlash2) {
        this.allowEncodedSlash = allowEncodedSlash2;
    }
}
