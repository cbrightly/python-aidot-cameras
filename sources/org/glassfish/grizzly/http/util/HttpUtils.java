package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.utils.Charsets;

public class HttpUtils {
    private static final float[] MULTIPLIERS = {0.1f, 0.01f, 0.001f};

    public static String composeContentType(String contentType, String characterEncoding) {
        int index;
        String newContentType;
        if (characterEncoding == null) {
            return contentType;
        }
        boolean hasCharset = false;
        int semicolonIndex = -1;
        int index2 = contentType.indexOf(59);
        while (true) {
            if (index == -1) {
                break;
            }
            int len = contentType.length();
            semicolonIndex = index;
            index++;
            while (index < len && contentType.charAt(index) == ' ') {
                index++;
            }
            if (index + 8 < len && contentType.charAt(index) == 'c' && contentType.charAt(index + 1) == 'h' && contentType.charAt(index + 2) == 'a' && contentType.charAt(index + 3) == 'r' && contentType.charAt(index + 4) == 's' && contentType.charAt(index + 5) == 'e' && contentType.charAt(index + 6) == 't' && contentType.charAt(index + 7) == '=') {
                hasCharset = true;
                break;
            }
            index2 = contentType.indexOf(59, index);
        }
        if (hasCharset) {
            newContentType = contentType.substring(0, semicolonIndex);
            String tail = contentType.substring(index + 8);
            int nextParam = tail.indexOf(59);
            if (nextParam != -1) {
                newContentType = newContentType + tail.substring(nextParam);
            }
        } else {
            newContentType = contentType;
        }
        StringBuilder sb = new StringBuilder(newContentType.length() + characterEncoding.length() + 9);
        sb.append(newContentType);
        sb.append(";charset=");
        sb.append(characterEncoding);
        return sb.toString();
    }

    public static float convertQValueToFloat(DataChunk dc, int startIdx, int stopIdx) {
        try {
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[dc.getType().ordinal()]) {
                case 1:
                    return convertQValueToFloat(dc.toString(), startIdx, stopIdx);
                case 2:
                    BufferChunk bc = dc.getBufferChunk();
                    int offs = bc.getStart();
                    return convertQValueToFloat(bc.getBuffer(), offs + startIdx, offs + stopIdx);
                case 3:
                    CharChunk cc = dc.getCharChunk();
                    int offs2 = cc.getStart();
                    return convertQValueToFloat(cc.getChars(), offs2 + startIdx, offs2 + stopIdx);
                default:
                    return 0.0f;
            }
        } catch (Exception e) {
            return 0.0f;
        }
    }

    /* renamed from: org.glassfish.grizzly.http.util.HttpUtils$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[DataChunk.Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[DataChunk.Type.String.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Buffer.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Chars.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static float convertQValueToFloat(Buffer buffer, int startIdx, int stopIdx) {
        float result = 0.0f;
        boolean firstDigitProcessed = false;
        int multIdx = -1;
        int i = 0;
        int len = stopIdx - startIdx;
        while (i < len) {
            char c = (char) buffer.get(i + startIdx);
            if (multIdx == -1) {
                if (firstDigitProcessed && c != '.') {
                    throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
                } else if (c == '.') {
                    multIdx = 0;
                    i++;
                }
            }
            if (Character.isDigit(c)) {
                if (multIdx == -1) {
                    result += (float) Character.digit(c, 10);
                    firstDigitProcessed = true;
                    if (result > 1.0f) {
                        throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
                    }
                } else {
                    float[] fArr = MULTIPLIERS;
                    if (multIdx < fArr.length) {
                        result += ((float) Character.digit(c, 10)) * fArr[multIdx];
                        multIdx++;
                    } else {
                        throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
                    }
                }
                i++;
            } else {
                throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
            }
        }
        return result;
    }

    public static float convertQValueToFloat(String string, int startIdx, int stopIdx) {
        float result = 0.0f;
        boolean firstDigitProcessed = false;
        int multIdx = -1;
        int i = 0;
        int len = stopIdx - startIdx;
        while (i < len) {
            char c = string.charAt(i + startIdx);
            if (multIdx == -1) {
                if (firstDigitProcessed && c != '.') {
                    throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
                } else if (c == '.') {
                    multIdx = 0;
                    i++;
                }
            }
            if (Character.isDigit(c)) {
                if (multIdx == -1) {
                    result += (float) Character.digit(c, 10);
                    firstDigitProcessed = true;
                    if (result > 1.0f) {
                        throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
                    }
                } else {
                    float[] fArr = MULTIPLIERS;
                    if (multIdx < fArr.length) {
                        result += ((float) Character.digit(c, 10)) * fArr[multIdx];
                        multIdx++;
                    } else {
                        throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
                    }
                }
                i++;
            } else {
                throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
            }
        }
        return result;
    }

    public static float convertQValueToFloat(char[] chars, int startIdx, int stopIdx) {
        float result = 0.0f;
        boolean firstDigitProcessed = false;
        int multIdx = -1;
        int i = 0;
        int len = stopIdx - startIdx;
        while (i < len) {
            char c = chars[i + startIdx];
            if (multIdx == -1) {
                if (firstDigitProcessed && c != '.') {
                    throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
                } else if (c == '.') {
                    multIdx = 0;
                    i++;
                }
            }
            if (Character.isDigit(c)) {
                if (multIdx == -1) {
                    result += (float) Character.digit(c, 10);
                    firstDigitProcessed = true;
                    if (result > 1.0f) {
                        throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
                    }
                } else {
                    float[] fArr = MULTIPLIERS;
                    if (multIdx < fArr.length) {
                        result += ((float) Character.digit(c, 10)) * fArr[multIdx];
                        multIdx++;
                    } else {
                        throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
                    }
                }
                i++;
            } else {
                throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
            }
        }
        return result;
    }

    public static int longToBuffer(long value, byte[] buffer) {
        boolean negative;
        long j;
        int i = buffer.length;
        if (value == 0) {
            int i2 = i - 1;
            buffer[i2] = 48;
            return i2;
        }
        if (value < 0) {
            negative = true;
            value = -value;
        } else {
            negative = false;
        }
        do {
            i--;
            buffer[i] = (byte) (((int) (value % 10)) + 48);
            j = value / 10;
            value = j;
        } while (j != 0);
        if (!negative) {
            return i;
        }
        int i3 = i - 1;
        buffer[i3] = 45;
        return i3;
    }

    public static void longToBuffer(long value, Buffer buffer) {
        boolean negative;
        long j;
        if (value == 0) {
            buffer.put(0, (byte) 48);
            buffer.limit(1);
            return;
        }
        if (value < 0) {
            negative = true;
            value = -value;
        } else {
            negative = false;
        }
        int position = buffer.limit();
        do {
            position--;
            buffer.put(position, (byte) (((int) (value % 10)) + 48));
            j = value / 10;
            value = j;
        } while (j != 0);
        if (negative) {
            position--;
            buffer.put(position, (byte) 45);
        }
        buffer.position(position);
    }

    public static DataChunk filterNonPrintableCharacters(DataChunk message) {
        if (message == null || message.isNull()) {
            return null;
        }
        try {
            message.toChars(Charsets.ASCII_CHARSET);
        } catch (CharConversionException e) {
        }
        CharChunk charChunk = message.getCharChunk();
        char[] content = charChunk.getChars();
        int start = charChunk.getStart();
        int end = charChunk.getEnd();
        for (int i = start; i < end; i++) {
            char c = content[i];
            if ((c <= 31 && c != 9) || c == 127 || c > 255) {
                content[i] = ' ';
            }
        }
        return message;
    }

    public static DataChunk filter(DataChunk message) {
        if (message == null || message.isNull()) {
            return null;
        }
        try {
            message.toChars(Charsets.ASCII_CHARSET);
        } catch (CharConversionException e) {
        }
        CharChunk charChunk = message.getCharChunk();
        char[] content = charChunk.getChars();
        StringBuilder result = null;
        int end = charChunk.getEnd();
        for (int i = charChunk.getStart(); i < end; i++) {
            switch (content[i]) {
                case '\"':
                    if (result == null) {
                        result = new StringBuilder(content.length + 50);
                        result.append(content, 0, i);
                    }
                    result.append("&quot;");
                    break;
                case '&':
                    if (result == null) {
                        result = new StringBuilder(content.length + 50);
                        result.append(content, 0, i);
                    }
                    result.append("&amp;");
                    break;
                case '<':
                    if (result == null) {
                        result = new StringBuilder(content.length + 50);
                        result.append(content, 0, i);
                    }
                    result.append("&lt;");
                    break;
                case '>':
                    if (result == null) {
                        result = new StringBuilder(content.length + 50);
                        result.append(content, 0, i);
                    }
                    result.append("&gt;");
                    break;
                default:
                    char c = content[i];
                    if ((c > 31 || c == 9) && c != 127 && c <= 255) {
                        if (result == null) {
                            break;
                        } else {
                            result.append(c);
                            break;
                        }
                    } else {
                        if (result == null) {
                            result = new StringBuilder(content.length + 50);
                            result.append(content, 0, i);
                        }
                        result.append("&#");
                        result.append(c);
                        result.append(';');
                        break;
                    }
                    break;
            }
        }
        if (result != null) {
            int len = result.length();
            char[] finalResult = new char[len];
            result.getChars(0, len, finalResult, 0);
            message.setChars(finalResult, 0, finalResult.length);
        }
        return message;
    }

    public static String filter(String message) {
        if (message == null) {
            return null;
        }
        StringBuilder result = null;
        int len = message.length();
        for (int i = 0; i < len; i++) {
            char c = message.charAt(i);
            switch (c) {
                case '\"':
                    if (result == null) {
                        result = new StringBuilder(len + 50);
                        result.append(message, 0, i);
                    }
                    result.append("&quot;");
                    break;
                case '&':
                    if (result == null) {
                        result = new StringBuilder(len + 50);
                        result.append(message, 0, i);
                    }
                    result.append("&amp;");
                    break;
                case '<':
                    if (result == null) {
                        result = new StringBuilder(len + 50);
                        result.append(message, 0, i);
                    }
                    result.append("&lt;");
                    break;
                case '>':
                    if (result == null) {
                        result = new StringBuilder(len + 50);
                        result.append(message, 0, i);
                    }
                    result.append("&gt;");
                    break;
                default:
                    if ((c > 31 || c == 9) && c != 127 && c <= 255) {
                        if (result == null) {
                            break;
                        } else {
                            result.append(c);
                            break;
                        }
                    } else {
                        if (result == null) {
                            result = new StringBuilder(len + 50);
                            result.append(message, 0, i);
                        }
                        result.append("&#");
                        result.append(c);
                        result.append(';');
                        break;
                    }
            }
        }
        return result == null ? message : result.toString();
    }
}
