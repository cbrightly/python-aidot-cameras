package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.utils.Charsets;

public class HttpRequestURIDecoder {
    protected static final boolean ALLOW_BACKSLASH = false;
    private static final boolean COLLAPSE_ADJACENT_SLASHES = Boolean.valueOf(System.getProperty("com.sun.enterprise.web.collapseAdjacentSlashes", "true")).booleanValue();
    private static final Logger LOGGER = Grizzly.logger(HttpRequestURIDecoder.class);
    private static final int STATE_CHAR = 0;
    private static final int STATE_PERCENT = 2;
    private static final int STATE_SLASH = 1;
    private static final int STATE_SLASHDOT = 3;
    private static final int STATE_SLASHDOTDOT = 4;

    public static void decode(MessageBytes decodedURI, UDecoder urlDecoder) {
        decode(decodedURI, urlDecoder, (String) null, (B2CConverter) null);
    }

    public static void decode(MessageBytes decodedURI, UDecoder urlDecoder, String encoding, B2CConverter b2cConverter) {
        urlDecoder.convert(decodedURI, false);
        if (normalize(decodedURI)) {
            if (encoding == null) {
                encoding = "utf-8";
            }
            convertURI(decodedURI, encoding, b2cConverter);
            if (!checkNormalize(decodedURI.getCharChunk())) {
                throw new IOException("Invalid URI character encoding");
            }
            return;
        }
        throw new IOException("Invalid URI character encoding");
    }

    public static void decode(DataChunk decodedURI) {
        decode(decodedURI, false, Charsets.UTF8_CHARSET);
    }

    public static void decode(DataChunk decodedURI, boolean isSlashAllowed) {
        decode(decodedURI, isSlashAllowed, Charsets.UTF8_CHARSET);
    }

    public static void decode(DataChunk decodedURI, boolean isSlashAllowed, Charset encoding) {
        decode(decodedURI, decodedURI, isSlashAllowed, encoding);
    }

    public static void decode(DataChunk originalURI, DataChunk targetDecodedURI, boolean isSlashAllowed, Charset encoding) {
        URLDecoder.decode(originalURI, targetDecodedURI, isSlashAllowed);
        if (normalize(targetDecodedURI)) {
            convertToChars(targetDecodedURI, encoding);
            return;
        }
        throw new CharConversionException("Invalid URI character encoding");
    }

    public static void convertToChars(DataChunk decodedURI, Charset encoding) {
        if (encoding == null) {
            encoding = Charsets.UTF8_CHARSET;
        }
        decodedURI.toChars(encoding);
        if (!checkNormalize(decodedURI.getCharChunk())) {
            throw new CharConversionException("Invalid URI character encoding");
        }
    }

    private static void convertURI(MessageBytes uri, String encoding, B2CConverter b2cConverter) {
        ByteChunk bc = uri.getByteChunk();
        CharChunk cc = uri.getCharChunk();
        cc.allocate(bc.getLength(), -1);
        if (!(encoding == null || encoding.trim().length() == 0 || "ISO-8859-1".equalsIgnoreCase(encoding))) {
            if (b2cConverter == null) {
                try {
                    b2cConverter = new B2CConverter(encoding);
                } catch (IOException e) {
                    LOGGER.severe("Invalid URI encoding; using HTTP default");
                }
            }
            if (b2cConverter != null) {
                try {
                    b2cConverter.convert(bc, cc);
                    uri.setChars(cc.getBuffer(), cc.getStart(), cc.getLength());
                    return;
                } catch (IOException e2) {
                    LOGGER.severe("Invalid URI character encoding; trying ascii");
                    cc.recycle();
                }
            }
        }
        byte[] bbuf = bc.getBuffer();
        char[] cbuf = cc.getBuffer();
        int start = bc.getStart();
        for (int i = 0; i < bc.getLength(); i++) {
            cbuf[i] = (char) (bbuf[i + start] & 255);
        }
        uri.setChars(cbuf, 0, bc.getLength());
    }

    public static boolean normalize(MessageBytes uriMB) {
        if (uriMB.getType() == 3) {
            return normalizeChars(uriMB.getCharChunk());
        }
        return normalizeBytes(uriMB.getByteChunk());
    }

    /* renamed from: org.glassfish.grizzly.http.util.HttpRequestURIDecoder$1  reason: invalid class name */
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

    public static boolean normalize(DataChunk dataChunk) {
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dataChunk.getType().ordinal()]) {
            case 1:
                return normalizeBytes(dataChunk.getByteChunk());
            case 2:
                return normalizeBuffer(dataChunk.getBufferChunk());
            case 3:
                try {
                    dataChunk.toChars((Charset) null);
                    break;
                } catch (CharConversionException unexpected) {
                    throw new IllegalStateException("Unexpected exception", unexpected);
                }
            case 4:
                break;
            default:
                throw new NullPointerException();
        }
        return normalizeChars(dataChunk.getCharChunk());
    }

    public static boolean checkNormalize(CharChunk uriCC) {
        char[] c = uriCC.getChars();
        int start = uriCC.getStart();
        int end = uriCC.getEnd();
        for (int pos = start; pos < end; pos++) {
            if (c[pos] == '\\' || c[pos] == 0) {
                return false;
            }
        }
        if (COLLAPSE_ADJACENT_SLASHES) {
            for (int pos2 = start; pos2 < end - 1; pos2++) {
                if (c[pos2] == '/' && c[pos2 + 1] == '/') {
                    return false;
                }
            }
        }
        if ((end - start < 2 || c[end - 1] != '.' || (c[end - 2] != '/' && (c[end - 2] != '.' || c[end - 3] != '/'))) && uriCC.indexOf("/./", 0, 3, 0) < 0) {
            return true;
        }
        return false;
    }

    public static boolean normalizeChars(CharChunk uriCC) {
        char[] c = uriCC.getChars();
        int start = uriCC.getStart();
        int end = uriCC.getEnd();
        if (end - start == 1 && c[start] == '*') {
            return true;
        }
        for (int pos = start; pos < end; pos++) {
            if (c[pos] == '\\' || c[pos] == 0) {
                return false;
            }
        }
        if (c[start] != '/') {
            return false;
        }
        if (COLLAPSE_ADJACENT_SLASHES) {
            int pos2 = start;
            while (pos2 < end - 1) {
                if (c[pos2] == '/') {
                    while (pos2 + 1 < end && c[pos2 + 1] == '/') {
                        copyChars(c, pos2, pos2 + 1, (end - pos2) - 1);
                        end--;
                    }
                }
                pos2++;
            }
        }
        if (end - start > 2 && c[end - 1] == '.' && (c[end - 2] == '/' || (c[end - 2] == '.' && c[end - 3] == '/'))) {
            c[end] = '/';
            end++;
        }
        uriCC.setEnd(end);
        int index = 0;
        while (true) {
            index = uriCC.indexOf("/./", 0, 3, index);
            if (index < 0) {
                break;
            }
            copyChars(c, start + index, start + index + 2, ((end - start) - index) - 2);
            end -= 2;
            uriCC.setEnd(end);
        }
        int index2 = 0;
        while (true) {
            int index3 = uriCC.indexOf("/../", 0, 4, index2);
            if (index3 < 0) {
                uriCC.setChars(c, start, end);
                return true;
            } else if (index3 == 0) {
                return false;
            } else {
                int index22 = -1;
                for (int pos3 = (start + index3) - 1; pos3 >= 0 && index22 < 0; pos3--) {
                    if (c[pos3] == '/') {
                        index22 = pos3;
                    }
                }
                copyChars(c, start + index22, start + index3 + 3, ((end - start) - index3) - 3);
                end = ((end + index22) - index3) - 3;
                uriCC.setEnd(end);
                index2 = index22;
            }
        }
    }

    protected static void copyBytes(byte[] b, int dest, int src, int len) {
        System.arraycopy(b, src, b, dest, len);
    }

    private static void copyChars(char[] c, int dest, int src, int len) {
        System.arraycopy(c, src, c, dest, len);
    }

    /* access modifiers changed from: protected */
    public void log(String message) {
        LOGGER.info(message);
    }

    /* access modifiers changed from: protected */
    public void log(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }

    /* access modifiers changed from: protected */
    public void convertMB(MessageBytes mb) {
        if (mb.getType() == 2) {
            ByteChunk bc = mb.getByteChunk();
            CharChunk cc = mb.getCharChunk();
            cc.allocate(bc.getLength(), -1);
            byte[] bbuf = bc.getBuffer();
            char[] cbuf = cc.getBuffer();
            int start = bc.getStart();
            for (int i = 0; i < bc.getLength(); i++) {
                cbuf[i] = (char) (bbuf[i + start] & 255);
            }
            mb.setChars(cbuf, 0, bc.getLength());
        }
    }

    public static boolean normalizeBytes(ByteChunk bc) {
        byte[] bs = bc.getBytes();
        int start = bc.getStart();
        int end = bc.getEnd();
        if (start == end) {
            return false;
        }
        if (end - start == 1 && bs[start] == 42) {
            return true;
        }
        if (end - start > 2 && bs[end - 1] == 46 && (bs[end - 2] == 47 || (bs[end - 2] == 46 && bs[end - 3] == 47))) {
            bs[end] = 47;
            end++;
        }
        int state = 0;
        int srcPos = start;
        int lastSlash = -1;
        int parentSlash = -1;
        for (int pos = start; pos < end; pos++) {
            if (bs[pos] == 0 || bs[pos] == 92) {
                return false;
            }
            if (bs[pos] == 47) {
                if (state == 0) {
                    state = 1;
                    bs[srcPos] = bs[pos];
                    parentSlash = lastSlash;
                    lastSlash = srcPos;
                    srcPos++;
                } else if (state == 1) {
                    if (!COLLAPSE_ADJACENT_SLASHES) {
                        srcPos++;
                    }
                } else if (state == 3) {
                    srcPos--;
                } else if (state != 4) {
                    continue;
                } else if (parentSlash == -1) {
                    return false;
                } else {
                    lastSlash = parentSlash;
                    int srcPos2 = parentSlash;
                    parentSlash = -1;
                    int i = lastSlash - 1;
                    while (true) {
                        if (i < start) {
                            break;
                        } else if (bs[i] == 47) {
                            parentSlash = i;
                            break;
                        } else {
                            i--;
                        }
                    }
                    state = 1;
                    bs[srcPos2] = bs[pos];
                    srcPos = srcPos2 + 1;
                }
            } else if (bs[pos] != 46) {
                state = 0;
                bs[srcPos] = bs[pos];
                srcPos++;
            } else if (state == 0) {
                bs[srcPos] = bs[pos];
                srcPos++;
            } else if (state == 1) {
                state = 3;
                bs[srcPos] = bs[pos];
                srcPos++;
            } else if (state == 3) {
                state = 4;
                bs[srcPos] = bs[pos];
                srcPos++;
            }
        }
        bc.setEnd(srcPos);
        return true;
    }

    public static boolean normalizeBuffer(BufferChunk bc) {
        byte b;
        Buffer bs = bc.getBuffer();
        int start = bc.getStart();
        int end = bc.getEnd();
        if (start == end) {
            return false;
        }
        if (end - start == 1 && bs.get(start) == 42) {
            return true;
        }
        if (end - start > 2 && bs.get(end - 1) == 46 && ((b = bs.get(end - 2)) == 47 || (b == 46 && bs.get(end - 3) == 47))) {
            bs.put(end, (byte) 47);
            end++;
        }
        int state = 0;
        int srcPos = start;
        int lastSlash = -1;
        int parentSlash = -1;
        for (int pos = start; pos < end; pos++) {
            byte b2 = bs.get(pos);
            if (b2 == 0 || b2 == 92) {
                return false;
            }
            if (b2 == 47) {
                if (state == 0) {
                    state = 1;
                    bs.put(srcPos, b2);
                    parentSlash = lastSlash;
                    lastSlash = srcPos;
                    srcPos++;
                } else if (state == 1) {
                    if (!COLLAPSE_ADJACENT_SLASHES) {
                        srcPos++;
                    }
                } else if (state == 3) {
                    srcPos--;
                } else if (state != 4) {
                    continue;
                } else if (parentSlash == -1) {
                    return false;
                } else {
                    lastSlash = parentSlash;
                    int srcPos2 = parentSlash;
                    parentSlash = -1;
                    int i = lastSlash - 1;
                    while (true) {
                        if (i < start) {
                            break;
                        } else if (bs.get(i) == 47) {
                            parentSlash = i;
                            break;
                        } else {
                            i--;
                        }
                    }
                    state = 1;
                    bs.put(srcPos2, b2);
                    srcPos = srcPos2 + 1;
                }
            } else if (b2 != 46) {
                state = 0;
                bs.put(srcPos, b2);
                srcPos++;
            } else if (state == 0) {
                bs.put(srcPos, b2);
                srcPos++;
            } else if (state == 1) {
                state = 3;
                bs.put(srcPos, b2);
                srcPos++;
            } else if (state == 3) {
                state = 4;
                bs.put(srcPos, b2);
                srcPos++;
            }
        }
        bc.setEnd(srcPos);
        return true;
    }
}
