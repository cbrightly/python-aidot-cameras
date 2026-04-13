package org.glassfish.grizzly.http.util;

import io.netty.util.internal.StringUtil;
import java.nio.BufferOverflowException;
import java.util.Date;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.Cookie;

public class CookieSerializerUtils {
    public static void serializeServerCookie(StringBuilder buf, Cookie cookie) {
        serializeServerCookie(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED, CookieUtils.ALWAYS_ADD_EXPIRES, cookie);
    }

    public static void serializeServerCookie(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, boolean alwaysAddExpires, Cookie cookie) {
        serializeServerCookie(buf, versionOneStrictCompliance, rfc6265Support, alwaysAddExpires, cookie.getName(), cookie.getValue(), cookie.getVersion(), cookie.getPath(), cookie.getDomain(), cookie.getComment(), cookie.getMaxAge(), cookie.isSecure(), cookie.isHttpOnly());
    }

    public static void serializeServerCookie(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, boolean alwaysAddExpires, String name, String value, int version, String path, String domain, String comment, int maxAge, boolean isSecure, boolean isHttpOnly) {
        String path2;
        StringBuilder sb = buf;
        boolean z = versionOneStrictCompliance;
        boolean z2 = rfc6265Support;
        String str = path;
        String str2 = domain;
        String str3 = comment;
        int i = maxAge;
        sb.append(name);
        sb.append('=');
        int version2 = maybeQuote2(version, sb, value, true, z2);
        if (version2 == 1) {
            sb.append("; Version=1");
            if (str3 != null) {
                sb.append("; Comment=");
                maybeQuote2(version2, sb, str3, z, z2);
            }
        }
        if (str2 != null) {
            sb.append("; Domain=");
            maybeQuote2(version2, sb, str2, z, z2);
        }
        if (i >= 0) {
            if (version2 > 0) {
                sb.append("; Max-Age=");
                sb.append(i);
            }
            if (version2 == 0 || alwaysAddExpires) {
                sb.append("; Expires=");
                if (i == 0) {
                    sb.append(CookieUtils.ancientDate);
                } else {
                    sb.append(CookieUtils.OLD_COOKIE_FORMAT.get().format(new Date(System.currentTimeMillis() + (((long) i) * 1000))));
                }
            }
        }
        if (str != null) {
            sb.append("; Path=");
            UEncoder encoder = new UEncoder();
            encoder.addSafeCharacter('/');
            encoder.addSafeCharacter(StringUtil.DOUBLE_QUOTE);
            String path3 = encoder.encodeURL(str, true);
            if (version2 == 0) {
                maybeQuote2(version2, sb, path3, z, z2);
                path2 = path3;
            } else {
                UEncoder uEncoder = encoder;
                path2 = path3;
                maybeQuote2(version2, buf, path3, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance, rfc6265Support);
            }
            String str4 = path2;
        }
        if (isSecure) {
            sb.append("; Secure");
        }
        if (isHttpOnly) {
            sb.append("; HttpOnly");
        }
    }

    public static void serializeServerCookie(Buffer buf, Cookie cookie) {
        serializeServerCookie(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.ALWAYS_ADD_EXPIRES, cookie);
    }

    public static void serializeServerCookie(Buffer buf, boolean versionOneStrictCompliance, boolean alwaysAddExpires, Cookie cookie) {
        serializeServerCookie(buf, versionOneStrictCompliance, alwaysAddExpires, cookie.getName(), cookie.getValue(), cookie.getVersion(), cookie.getPath(), cookie.getDomain(), cookie.getComment(), cookie.getMaxAge(), cookie.isSecure(), cookie.isHttpOnly());
    }

    public static void serializeServerCookie(Buffer buf, boolean versionOneStrictCompliance, boolean alwaysAddExpires, String name, String value, int version, String path, String domain, String comment, int maxAge, boolean isSecure, boolean isHttpOnly) {
        Buffer buffer = buf;
        boolean z = versionOneStrictCompliance;
        String str = path;
        String str2 = domain;
        String str3 = comment;
        int i = maxAge;
        put(buffer, name);
        put(buffer, 61);
        int version2 = maybeQuote2(version, buffer, value, true);
        if (version2 == 1) {
            put(buffer, "; Version=1");
            if (str3 != null) {
                put(buffer, "; Comment=");
                maybeQuote2(version2, buffer, str3, z);
            }
        }
        if (str2 != null) {
            put(buffer, "; Domain=");
            maybeQuote2(version2, buffer, str2, z);
        }
        if (i >= 0) {
            if (version2 > 0) {
                put(buffer, "; Max-Age=");
                putInt(buffer, i);
            }
            if (version2 == 0 || alwaysAddExpires) {
                put(buffer, "; Expires=");
                if (i == 0) {
                    put(buffer, CookieUtils.ancientDate);
                } else {
                    put(buffer, CookieUtils.OLD_COOKIE_FORMAT.get().format(new Date(System.currentTimeMillis() + (((long) i) * 1000))));
                }
            }
        }
        if (str != null) {
            put(buffer, "; Path=");
            UEncoder encoder = new UEncoder();
            encoder.addSafeCharacter('/');
            encoder.addSafeCharacter(StringUtil.DOUBLE_QUOTE);
            String path2 = encoder.encodeURL(str, true);
            if (version2 == 0) {
                maybeQuote2(version2, buffer, path2, z);
            } else {
                maybeQuote2(version2, buf, path2, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance);
            }
        } else {
            String path3 = str;
        }
        if (isSecure) {
            put(buffer, "; Secure");
        }
        if (isHttpOnly) {
            put(buffer, "; HttpOnly");
        }
    }

    public static void serializeClientCookies(StringBuilder buf, Cookie... cookies) {
        serializeClientCookies(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED, cookies);
    }

    public static void serializeClientCookies(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, Cookie... cookies) {
        StringBuilder sb = buf;
        boolean z = rfc6265Support;
        Cookie[] cookieArr = cookies;
        if (cookieArr.length != 0) {
            int version = cookieArr[0].getVersion();
            if (!z && version == 1) {
                sb.append("$Version=\"1\"; ");
            }
            for (int i = 0; i < cookieArr.length; i++) {
                Cookie cookie = cookieArr[i];
                sb.append(cookie.getName());
                sb.append('=');
                maybeQuote2(version, sb, cookie.getValue(), true, z);
                if (z || version != 1) {
                    boolean z2 = versionOneStrictCompliance;
                } else {
                    String domain = cookie.getDomain();
                    if (domain != null) {
                        sb.append("; $Domain=");
                        maybeQuote2(version, sb, domain, versionOneStrictCompliance, z);
                    } else {
                        boolean z3 = versionOneStrictCompliance;
                    }
                    String path = cookie.getPath();
                    if (path != null) {
                        sb.append("; $Path=");
                        UEncoder encoder = new UEncoder();
                        encoder.addSafeCharacter('/');
                        encoder.addSafeCharacter(StringUtil.DOUBLE_QUOTE);
                        UEncoder uEncoder = encoder;
                        maybeQuote2(version, buf, encoder.encodeURL(path, true), "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance, rfc6265Support);
                    }
                }
                if (i < cookieArr.length - 1) {
                    sb.append("; ");
                }
            }
            boolean z4 = versionOneStrictCompliance;
        }
    }

    public static void serializeClientCookies(Buffer buf, Cookie... cookies) {
        serializeClientCookies(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, cookies);
    }

    public static void serializeClientCookies(Buffer buf, boolean versionOneStrictCompliance, Cookie... cookies) {
        if (cookies.length != 0) {
            int version = cookies[0].getVersion();
            if (version == 1) {
                put(buf, "$Version=\"1\"; ");
            }
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                put(buf, cookie.getName());
                put(buf, "=");
                maybeQuote2(version, buf, cookie.getValue(), true);
                if (version == 1) {
                    String domain = cookie.getDomain();
                    if (domain != null) {
                        put(buf, "; $Domain=");
                        maybeQuote2(version, buf, domain, versionOneStrictCompliance);
                    }
                    String path = cookie.getPath();
                    if (path != null) {
                        put(buf, "; $Path=");
                        UEncoder encoder = new UEncoder();
                        encoder.addSafeCharacter('/');
                        encoder.addSafeCharacter(StringUtil.DOUBLE_QUOTE);
                        maybeQuote2(version, buf, encoder.encodeURL(path, true), "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance);
                    }
                }
                if (i < cookies.length - 1) {
                    put(buf, "; ");
                }
            }
        }
    }

    public static int maybeQuote2(int version, StringBuilder buf, String value, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
        return maybeQuote2(version, buf, value, false, versionOneStrictCompliance, rfc6265Enabled);
    }

    public static int maybeQuote2(int version, StringBuilder buf, String value, boolean allowVersionSwitch, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
        return maybeQuote2(version, buf, value, (String) null, allowVersionSwitch, versionOneStrictCompliance, rfc6265Enabled);
    }

    public static int maybeQuote2(int version, StringBuilder buf, String value, String literals, boolean allowVersionSwitch, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
        if (value == null || value.length() == 0) {
            buf.append("\"\"");
            return version;
        } else if (CookieUtils.containsCTL(value, version)) {
            throw new IllegalArgumentException("Control character in cookie value, consider BASE64 encoding your value");
        } else if (alreadyQuoted(value)) {
            buf.append(StringUtil.DOUBLE_QUOTE);
            buf.append(escapeDoubleQuotes(value, 1, value.length() - 1));
            buf.append(StringUtil.DOUBLE_QUOTE);
            return version;
        } else if (allowVersionSwitch && versionOneStrictCompliance && version == 0 && !CookieUtils.isToken2(value, literals)) {
            buf.append(StringUtil.DOUBLE_QUOTE);
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append(StringUtil.DOUBLE_QUOTE);
            return 1;
        } else if (version == 0 && !CookieUtils.isToken(value, literals)) {
            buf.append(StringUtil.DOUBLE_QUOTE);
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append(StringUtil.DOUBLE_QUOTE);
            return version;
        } else if (version == 1 && !CookieUtils.isToken2(value, literals)) {
            buf.append(StringUtil.DOUBLE_QUOTE);
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append(StringUtil.DOUBLE_QUOTE);
            return version;
        } else if (version >= 0 || !rfc6265Enabled) {
            buf.append(value);
            return version;
        } else {
            buf.append(StringUtil.DOUBLE_QUOTE);
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append(StringUtil.DOUBLE_QUOTE);
            return version;
        }
    }

    public static int maybeQuote2(int version, Buffer buf, String value, boolean versionOneStrictCompliance) {
        return maybeQuote2(version, buf, value, false, versionOneStrictCompliance);
    }

    public static int maybeQuote2(int version, Buffer buf, String value, boolean allowVersionSwitch, boolean versionOneStrictCompliance) {
        return maybeQuote2(version, buf, value, (String) null, allowVersionSwitch, versionOneStrictCompliance);
    }

    public static int maybeQuote2(int version, Buffer buf, String value, String literals, boolean allowVersionSwitch, boolean versionOneStrictCompliance) {
        if (value == null || value.length() == 0) {
            put(buf, "\"\"");
            return version;
        } else if (CookieUtils.containsCTL(value, version)) {
            throw new IllegalArgumentException("Control character in cookie value, consider BASE64 encoding your value");
        } else if (alreadyQuoted(value)) {
            put(buf, 34);
            put(buf, escapeDoubleQuotes(value, 1, value.length() - 1));
            put(buf, 34);
            return version;
        } else if (allowVersionSwitch && versionOneStrictCompliance && version == 0 && !CookieUtils.isToken2(value, literals)) {
            put(buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put(buf, 34);
            return 1;
        } else if (version == 0 && !CookieUtils.isToken(value, literals)) {
            put(buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put(buf, 34);
            return version;
        } else if (version != 1 || CookieUtils.isToken2(value, literals)) {
            put(buf, value);
            return version;
        } else {
            put(buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put(buf, 34);
            return version;
        }
    }

    private static String escapeDoubleQuotes(String s, int beginIndex, int endIndex) {
        if (s == null || s.length() == 0 || s.indexOf(34) == -1) {
            return s;
        }
        StringBuilder b = new StringBuilder();
        int i = beginIndex;
        while (i < endIndex) {
            char c = s.charAt(i);
            if (c == '\\') {
                b.append(c);
                i++;
                if (i < endIndex) {
                    b.append(s.charAt(i));
                } else {
                    throw new IllegalArgumentException("Invalid escape character in cookie value.");
                }
            } else if (c == '\"') {
                b.append('\\');
                b.append(StringUtil.DOUBLE_QUOTE);
            } else {
                b.append(c);
            }
            i++;
        }
        return b.toString();
    }

    public static boolean alreadyQuoted(String value) {
        return value != null && value.length() != 0 && value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"';
    }

    static void put(Buffer dstBuffer, int c) {
        dstBuffer.put((byte) c);
    }

    static void putInt(Buffer dstBuffer, int intValue) {
        put(dstBuffer, Integer.toString(intValue));
    }

    static void put(Buffer dstBuffer, String s) {
        int size = s.length();
        if (dstBuffer.remaining() >= size) {
            for (int i = 0; i < size; i++) {
                dstBuffer.put((byte) s.charAt(i));
            }
            return;
        }
        throw new BufferOverflowException();
    }

    static void put(StringBuilder dstBuffer, int c) {
        dstBuffer.append((char) c);
    }

    static void putInt(StringBuilder dstBuffer, int intValue) {
        dstBuffer.append(intValue);
    }

    static void put(StringBuilder dstBuffer, String s) {
        dstBuffer.append(s);
    }
}
