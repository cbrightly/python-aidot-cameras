package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.Header;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.d;
import org.apache.http.cookie.f;
import org.apache.http.cookie.h;
import org.apache.http.cookie.i;
import org.apache.http.message.q;
import org.apache.http.message.v;
import org.apache.http.message.w;
import org.apache.http.util.a;

/* compiled from: RFC6265CookieSpec */
public class o0 implements i {
    private static final BitSet a = w.a(61, 59);
    private static final BitSet b = w.a(59);
    private static final BitSet c = w.a(32, 34, 44, 59, 92);
    private final d[] d;
    private final Map<String, d> e;
    private final w f;

    protected o0(b... handlers) {
        this.d = (d[]) handlers.clone();
        this.e = new ConcurrentHashMap(handlers.length);
        for (b handler : handlers) {
            this.e.put(handler.c().toLowerCase(Locale.ROOT), handler);
        }
        this.f = w.a;
    }

    static String i(f origin) {
        String defaultPath = origin.b();
        int lastSlashIndex = defaultPath.lastIndexOf(47);
        if (lastSlashIndex < 0) {
            return defaultPath;
        }
        if (lastSlashIndex == 0) {
            lastSlashIndex = 1;
        }
        return defaultPath.substring(0, lastSlashIndex);
    }

    static String h(f origin) {
        return origin.a();
    }

    public final List<c> c(org.apache.http.d header, f origin) {
        v cursor;
        org.apache.http.util.d buffer;
        a.i(header, "Header");
        a.i(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
            if (header instanceof org.apache.http.c) {
                buffer = ((org.apache.http.c) header).getBuffer();
                cursor = new v(((org.apache.http.c) header).getValuePos(), buffer.length());
            } else {
                String s = header.getValue();
                if (s != null) {
                    org.apache.http.util.d buffer2 = new org.apache.http.util.d(s.length());
                    buffer2.append(s);
                    buffer = buffer2;
                    cursor = new v(0, buffer2.length());
                } else {
                    throw new MalformedCookieException("Header value is null");
                }
            }
            String name = this.f.f(buffer, cursor, a);
            if (name.length() == 0) {
                return Collections.emptyList();
            }
            if (cursor.a()) {
                return Collections.emptyList();
            }
            int valueDelim = buffer.charAt(cursor.b());
            cursor.d(cursor.b() + 1);
            if (valueDelim == 61) {
                String value = this.f.g(buffer, cursor, b);
                if (!cursor.a()) {
                    cursor.d(cursor.b() + 1);
                }
                d cookie = new d(name, value);
                cookie.setPath(i(origin));
                cookie.setDomain(h(origin));
                cookie.setCreationDate(new Date());
                Map<String, String> attribMap = new LinkedHashMap<>();
                while (!cursor.a()) {
                    String paramName = this.f.f(buffer, cursor, a).toLowerCase(Locale.ROOT);
                    String paramValue = null;
                    if (!cursor.a()) {
                        int paramDelim = buffer.charAt(cursor.b());
                        cursor.d(cursor.b() + 1);
                        if (paramDelim == 61) {
                            paramValue = this.f.f(buffer, cursor, b);
                            if (!cursor.a()) {
                                cursor.d(cursor.b() + 1);
                            }
                        }
                    }
                    cookie.setAttribute(paramName, paramValue);
                    attribMap.put(paramName, paramValue);
                }
                if (attribMap.containsKey("max-age")) {
                    attribMap.remove("expires");
                }
                for (Map.Entry<String, String> entry : attribMap.entrySet()) {
                    String paramValue2 = entry.getValue();
                    d handler = this.e.get(entry.getKey());
                    if (handler != null) {
                        handler.d(cookie, paramValue2);
                    }
                }
                return Collections.singletonList(cookie);
            }
            throw new MalformedCookieException("Cookie value is invalid: '" + header.toString() + "'");
        }
        throw new MalformedCookieException("Unrecognized cookie header: '" + header.toString() + "'");
    }

    public final void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        for (d handler : this.d) {
            handler.a(cookie, origin);
        }
    }

    public final boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        for (d handler : this.d) {
            if (!handler.b(cookie, origin)) {
                return false;
            }
        }
        return true;
    }

    public List<org.apache.http.d> e(List<c> cookies) {
        List<c> list;
        a.f(cookies, "List of cookies");
        if (cookies.size() > 1) {
            list = new ArrayList<>(cookies);
            Collections.sort(list, h.c);
        } else {
            list = cookies;
        }
        org.apache.http.util.d buffer = new org.apache.http.util.d(list.size() * 20);
        buffer.append(HttpHeaders.HEAD_KEY_COOKIE);
        buffer.append(": ");
        for (int n = 0; n < list.size(); n++) {
            c cookie = list.get(n);
            if (n > 0) {
                buffer.append(';');
                buffer.append(' ');
            }
            buffer.append(cookie.getName());
            String s = cookie.getValue();
            if (s != null) {
                buffer.append('=');
                if (g(s)) {
                    buffer.append((char) StringUtil.DOUBLE_QUOTE);
                    for (int i = 0; i < s.length(); i++) {
                        char ch = s.charAt(i);
                        if (ch == '\"' || ch == '\\') {
                            buffer.append('\\');
                        }
                        buffer.append(ch);
                    }
                    buffer.append((char) StringUtil.DOUBLE_QUOTE);
                } else {
                    buffer.append(s);
                }
            }
        }
        List<Header> headers = new ArrayList<>(1);
        headers.add(new q(buffer));
        return headers;
    }

    /* access modifiers changed from: package-private */
    public boolean g(CharSequence s) {
        return f(s, c);
    }

    /* access modifiers changed from: package-private */
    public boolean f(CharSequence s, BitSet chars) {
        for (int i = 0; i < s.length(); i++) {
            if (chars.get(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public final int getVersion() {
        return 0;
    }

    public final org.apache.http.d d() {
        return null;
    }
}
