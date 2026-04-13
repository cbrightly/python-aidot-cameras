package org.glassfish.tyrus.core;

import java.net.URI;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class Utils {
    private static final List<String> FILTERED_HEADERS = Arrays.asList(new String[]{"Authorization"});
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static abstract class Stringifier<T> {
        /* access modifiers changed from: package-private */
        public abstract String toString(T t);
    }

    public static List<String> parseHeaderValue(String headerValue) {
        List<String> values = new ArrayList<>();
        int state = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : headerValue.toCharArray()) {
            switch (state) {
                case 0:
                    if (!Character.isWhitespace(c)) {
                        if (c != '\"') {
                            sb.append(c);
                            state = 1;
                            break;
                        } else {
                            state = 2;
                            sb.append(c);
                            break;
                        }
                    } else {
                        break;
                    }
                case 1:
                    if (c == ',') {
                        values.add(sb.toString());
                        sb = new StringBuilder();
                        state = 0;
                        break;
                    } else {
                        sb.append(c);
                        break;
                    }
                case 2:
                    if (c == '\"') {
                        sb.append(c);
                        values.add(sb.toString());
                        sb = new StringBuilder();
                        state = 3;
                        break;
                    } else {
                        sb.append(c);
                        break;
                    }
                case 3:
                    if (!Character.isWhitespace(c) && c == ',') {
                        state = 0;
                        break;
                    }
            }
        }
        if (sb.length() > 0) {
            values.add(sb.toString());
        }
        return values;
    }

    public static byte[] getRemainingArray(ByteBuffer buffer) {
        if (buffer == null) {
            return new byte[0];
        }
        byte[] ret = new byte[buffer.remaining()];
        if (buffer.hasArray()) {
            System.arraycopy(buffer.array(), buffer.arrayOffset() + buffer.position(), ret, 0, ret.length);
        } else {
            buffer.get(ret);
        }
        return ret;
    }

    public static <T> String getHeaderFromList(List<T> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static <T> List<String> getStringList(List<T> list, Stringifier<T> stringifier) {
        List<String> result = new ArrayList<>();
        for (T item : list) {
            if (stringifier != null) {
                result.add(stringifier.toString(item));
            } else {
                result.add(item.toString());
            }
        }
        return result;
    }

    public static <T> String getHeaderFromList(List<T> list, Stringifier<T> stringifier) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (stringifier != null) {
                sb.append(stringifier.toString(it.next()));
            } else {
                sb.append(it.next());
            }
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static <T> void checkNotNull(T reference, String parameterName) {
        if (reference == null) {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL(parameterName));
        }
    }

    public static byte[] toArray(long value) {
        byte[] b = new byte[8];
        for (int i = 7; i >= 0 && value > 0; i--) {
            b[i] = (byte) ((int) (255 & value));
            value >>= 8;
        }
        return b;
    }

    public static long toLong(byte[] bytes, int start, int end) {
        long value = 0;
        for (int i = start; i < end; i++) {
            value = (value << 8) ^ (((long) bytes[i]) & 255);
        }
        return value;
    }

    public static List<String> toString(byte[] bytes) {
        return toString(bytes, 0, bytes.length);
    }

    public static List<String> toString(byte[] bytes, int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            list.add(Integer.toHexString(bytes[i] & 255).toUpperCase(Locale.US));
        }
        return list;
    }

    public static ByteBuffer appendBuffers(ByteBuffer buffer, ByteBuffer buffer1, int incomingBufferSize, int BUFFER_STEP_SIZE) {
        int limit = buffer.limit();
        int capacity = buffer.capacity();
        int remaining = buffer.remaining();
        int len = buffer1.remaining();
        if (len < capacity - limit) {
            buffer.mark();
            buffer.position(limit);
            buffer.limit(capacity);
            buffer.put(buffer1);
            buffer.limit(limit + len);
            buffer.reset();
            return buffer;
        } else if (remaining + len < capacity) {
            buffer.compact();
            buffer.put(buffer1);
            buffer.flip();
            return buffer;
        } else {
            int newSize = remaining + len;
            if (newSize <= incomingBufferSize) {
                int roundedSize = newSize % BUFFER_STEP_SIZE > 0 ? ((newSize / BUFFER_STEP_SIZE) + 1) * BUFFER_STEP_SIZE : newSize;
                ByteBuffer result = ByteBuffer.allocate(roundedSize > incomingBufferSize ? newSize : roundedSize);
                result.put(buffer);
                result.put(buffer1);
                result.flip();
                return result;
            }
            throw new IllegalArgumentException(LocalizationMessages.BUFFER_OVERFLOW());
        }
    }

    public static <T> T getProperty(Map<String, Object> properties, String key, Class<T> type) {
        return getProperty(properties, key, type, (Object) null);
    }

    public static <T> T getProperty(Map<String, Object> properties, String key, Class<T> type, T defaultValue) {
        Object o;
        boolean z;
        if (properties == null || (o = properties.get(key)) == null) {
            return defaultValue;
        }
        try {
            if (type.isAssignableFrom(o.getClass())) {
                return o;
            }
            if (type.equals(Integer.class)) {
                return Integer.valueOf(o.toString());
            }
            if (type.equals(Long.class)) {
                return Long.valueOf(o.toString());
            }
            if (type.equals(Boolean.class)) {
                if (!o.toString().equals("1")) {
                    if (!Boolean.valueOf(o.toString()).booleanValue()) {
                        z = false;
                        return Boolean.valueOf(z);
                    }
                }
                z = true;
                return Boolean.valueOf(z);
            } else if (!type.isEnum()) {
                return null;
            } else {
                try {
                    return Enum.valueOf(type, o.toString().trim().toUpperCase(Locale.US));
                } catch (Exception e) {
                    return defaultValue;
                }
            }
        } catch (Throwable th) {
            LOGGER.log(Level.CONFIG, String.format("Invalid type of configuration property of %s (%s), %s cannot be cast to %s", new Object[]{key, o.toString(), o.getClass().toString(), type.toString()}));
            return null;
        }
    }

    public static int getWsPort(URI uri) {
        return getWsPort(uri, uri.getScheme());
    }

    public static int getWsPort(URI uri, String scheme) {
        if (uri.getPort() != -1) {
            return uri.getPort();
        }
        if ("wss".equals(scheme)) {
            return 443;
        }
        if ("ws".equals(scheme)) {
            return 80;
        }
        return -1;
    }

    public static Date parseHttpDate(String stringValue) {
        try {
            return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(stringValue);
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat("EEE, dd-MMM-yy HH:mm:ss zzz", Locale.ENGLISH).parse(stringValue);
            } catch (ParseException e2) {
                return new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH).parse(stringValue);
            }
        }
    }

    public static String stringifyUpgradeRequest(UpgradeRequest upgradeRequest) {
        if (upgradeRequest == null) {
            return null;
        }
        StringBuilder request = new StringBuilder();
        request.append("GET ");
        request.append(upgradeRequest.getRequestUri());
        request.append("\n");
        appendHeaders(request, upgradeRequest.getHeaders());
        return request.toString();
    }

    public static String stringifyUpgradeResponse(UpgradeResponse upgradeResponse) {
        if (upgradeResponse == null) {
            return null;
        }
        StringBuilder request = new StringBuilder();
        request.append(upgradeResponse.getStatus());
        request.append("\n");
        appendHeaders(request, upgradeResponse.getHeaders());
        return request.toString();
    }

    private static void appendHeaders(StringBuilder message, Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            StringBuilder value = new StringBuilder();
            for (String valuePart : header.getValue()) {
                if (value.length() != 0) {
                    value.append(", ");
                }
                value.append(valuePart);
            }
            appendHeader(message, header.getKey(), value.toString());
        }
    }

    private static void appendHeader(StringBuilder message, String key, String value) {
        message.append(key);
        message.append(": ");
        for (String filteredHeader : FILTERED_HEADERS) {
            if (filteredHeader.equals(key)) {
                value = "*****";
            }
        }
        message.append(value);
        message.append("\n");
    }
}
