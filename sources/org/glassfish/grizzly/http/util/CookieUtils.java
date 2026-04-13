package org.glassfish.grizzly.http.util;

import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.glassfish.grizzly.Buffer;

public final class CookieUtils {
    public static final boolean ALWAYS_ADD_EXPIRES = Boolean.valueOf(System.getProperty("org.glassfish.grizzly.util.http.ServerCookie.ALWAYS_ADD_EXPIRES", "true")).booleanValue();
    public static final boolean COOKIE_VERSION_ONE_STRICT_COMPLIANCE = Boolean.getBoolean("org.glassfish.web.rfc2109_cookie_names_enforced");
    public static final ThreadLocal<SimpleDateFormat> OLD_COOKIE_FORMAT;
    static final String OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    public static final boolean RFC_6265_SUPPORT_ENABLED = Boolean.getBoolean("org.glassfish.web.rfc_6265_support_enabled");
    static final char[] SEPARATORS = {9, ' ', StringUtil.DOUBLE_QUOTE, '\'', '(', ')', StringUtil.COMMA, ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}'};
    static final String ancientDate;
    static final boolean[] separators = new boolean[128];
    static final String tspecials = ",; ";
    static final String tspecials2 = "()<>@,;:\\\"/[]?={} \t";
    static final String tspecials2NoSlash = "()<>@,;:\\\"[]?={} \t";

    static {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= 128) {
                break;
            }
            separators[i2] = false;
            i2++;
        }
        for (char SEPARATOR : SEPARATORS) {
            separators[SEPARATOR] = true;
        }
        AnonymousClass1 r0 = new ThreadLocal<SimpleDateFormat>() {
            /* access modifiers changed from: protected */
            public SimpleDateFormat initialValue() {
                SimpleDateFormat f = new SimpleDateFormat(CookieUtils.OLD_COOKIE_PATTERN, Locale.US);
                f.setTimeZone(TimeZone.getTimeZone("GMT"));
                return f;
            }
        };
        OLD_COOKIE_FORMAT = r0;
        ancientDate = ((SimpleDateFormat) r0.get()).format(new Date(10000));
    }

    public static boolean isToken(String value) {
        return isToken(value, (String) null);
    }

    public static boolean isToken(String value, String literals) {
        String ts = literals == null ? tspecials : literals;
        if (value == null) {
            return true;
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            if (ts.indexOf(value.charAt(i)) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsCTL(String value, int version) {
        if (value == null) {
            return false;
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if ((c < ' ' || c >= 127) && c != 9) {
                return true;
            }
        }
        return false;
    }

    public static boolean isToken2(String value) {
        return isToken2(value, (String) null);
    }

    public static boolean isToken2(String value, String literals) {
        String ts = literals == null ? tspecials2 : literals;
        if (value == null) {
            return true;
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            if (ts.indexOf(value.charAt(i)) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(String s, byte[] b, int start, int end) {
        int blen = end - start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (b[boff] != s.charAt(i)) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equals(String s, Buffer b, int start, int end) {
        int blen = end - start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (b.get(boff) != s.charAt(i)) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equals(String s1, String s2, int start, int end) {
        int blen = end - start;
        if (s2 == null || blen != s1.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (s2.charAt(boff) != s1.charAt(i)) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equalsIgnoreCase(String s, Buffer b, int start, int end) {
        int blen = end - start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (Ascii.toLower(b.get(boff)) != Ascii.toLower((int) s.charAt(i))) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equalsIgnoreCase(String s, byte[] b, int start, int end) {
        int blen = end - start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (Ascii.toLower((int) b[boff]) != Ascii.toLower((int) s.charAt(i))) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equalsIgnoreCase(String s1, String s2, int start, int end) {
        int blen = end - start;
        if (s2 == null || blen != s1.length()) {
            return false;
        }
        int boff = start;
        int i = 0;
        while (i < blen) {
            int boff2 = boff + 1;
            if (Ascii.toLower((int) s1.charAt(i)) != Ascii.toLower(s2.charAt(boff))) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean isSeparator(int c) {
        return isSeparator(c, true);
    }

    public static boolean isSeparator(int c, boolean parseAsVersion1) {
        if (parseAsVersion1) {
            if (c <= 0 || c >= 126 || !separators[c]) {
                return false;
            }
            return true;
        } else if (c == 59 || c == 44) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWhiteSpace(int c) {
        return c == 32 || c == 9 || c == 10 || c == 13 || c == 12;
    }

    public static int getTokenEndPosition(Buffer buffer, int off, int end) {
        return getTokenEndPosition(buffer, off, end, true);
    }

    public static int getTokenEndPosition(Buffer buffer, int off, int end, boolean parseAsVersion1) {
        int pos = off;
        while (pos < end && !isSeparator(buffer.get(pos), parseAsVersion1)) {
            pos++;
        }
        if (pos > end) {
            return end;
        }
        return pos;
    }

    public static int getTokenEndPosition(byte[] bytes, int off, int end) {
        return getTokenEndPosition(bytes, off, end, true);
    }

    public static int getTokenEndPosition(byte[] bytes, int off, int end, boolean parseAsVersion1) {
        int pos = off;
        while (pos < end && !isSeparator(bytes[pos], parseAsVersion1)) {
            pos++;
        }
        if (pos > end) {
            return end;
        }
        return pos;
    }

    public static int getTokenEndPosition(String s, int off, int end) {
        return getTokenEndPosition(s, off, end, true);
    }

    public static int getTokenEndPosition(String s, int off, int end, boolean parseAsVersion1) {
        int pos = off;
        while (pos < end && !isSeparator(s.charAt(pos), parseAsVersion1)) {
            pos++;
        }
        if (pos > end) {
            return end;
        }
        return pos;
    }

    public static int getQuotedValueEndPosition(Buffer buffer, int off, int end) {
        int pos = off;
        while (pos < end) {
            if (buffer.get(pos) == 34) {
                return pos;
            }
            if (buffer.get(pos) != 92 || pos >= end - 1) {
                pos++;
            } else {
                pos += 2;
            }
        }
        return end;
    }

    public static int getQuotedValueEndPosition(byte[] bytes, int off, int end) {
        int pos = off;
        while (pos < end) {
            if (bytes[pos] == 34) {
                return pos;
            }
            if (bytes[pos] != 92 || pos >= end - 1) {
                pos++;
            } else {
                pos += 2;
            }
        }
        return end;
    }

    public static int getQuotedValueEndPosition(String s, int off, int end) {
        int pos = off;
        while (pos < end) {
            if (s.charAt(pos) == '\"') {
                return pos;
            }
            if (s.charAt(pos) != '\\' || pos >= end - 1) {
                pos++;
            } else {
                pos += 2;
            }
        }
        return end;
    }
}
