package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.g;
import org.apache.http.d;
import org.apache.http.message.q;

/* compiled from: RFC2109Spec */
public class e0 extends p {
    static final String[] b = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"};
    private final boolean c;

    /* compiled from: RFC2109Spec */
    public class a extends i {
        a() {
        }

        public void a(c cookie, f origin) {
            if (!b(cookie, origin)) {
                throw new CookieRestrictionViolationException("Illegal 'path' attribute \"" + cookie.getPath() + "\". Path of origin: \"" + origin.b() + "\"");
            }
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public e0(java.lang.String[] r4, boolean r5) {
        /*
            r3 = this;
            r0 = 7
            org.apache.http.cookie.b[] r0 = new org.apache.http.cookie.b[r0]
            org.apache.http.impl.cookie.g0 r1 = new org.apache.http.impl.cookie.g0
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            org.apache.http.impl.cookie.e0$a r1 = new org.apache.http.impl.cookie.e0$a
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            org.apache.http.impl.cookie.d0 r1 = new org.apache.http.impl.cookie.d0
            r1.<init>()
            r2 = 2
            r0[r2] = r1
            org.apache.http.impl.cookie.h r1 = new org.apache.http.impl.cookie.h
            r1.<init>()
            r2 = 3
            r0[r2] = r1
            org.apache.http.impl.cookie.j r1 = new org.apache.http.impl.cookie.j
            r1.<init>()
            r2 = 4
            r0[r2] = r1
            org.apache.http.impl.cookie.e r1 = new org.apache.http.impl.cookie.e
            r1.<init>()
            r2 = 5
            r0[r2] = r1
            org.apache.http.impl.cookie.g r1 = new org.apache.http.impl.cookie.g
            if (r4 == 0) goto L_0x003e
            java.lang.Object r2 = r4.clone()
            java.lang.String[] r2 = (java.lang.String[]) r2
            goto L_0x0040
        L_0x003e:
            java.lang.String[] r2 = b
        L_0x0040:
            r1.<init>(r2)
            r2 = 6
            r0[r2] = r1
            r3.<init>(r0)
            r3.c = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.e0.<init>(java.lang.String[], boolean):void");
    }

    public e0() {
        this((String[]) null, false);
    }

    protected e0(boolean oneHeader, b... handlers) {
        super(handlers);
        this.c = oneHeader;
    }

    public List<c> c(d header, f origin) {
        org.apache.http.util.a.i(header, "Header");
        org.apache.http.util.a.i(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
            return j(header.getElements(), origin);
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    public void a(c cookie, f origin) {
        org.apache.http.util.a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        String name = cookie.getName();
        if (name.indexOf(32) != -1) {
            throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
        } else if (!name.startsWith("$")) {
            super.a(cookie, origin);
        } else {
            throw new CookieRestrictionViolationException("Cookie name may not start with $");
        }
    }

    public List<d> e(List<c> cookies) {
        List<c> list;
        org.apache.http.util.a.f(cookies, "List of cookies");
        if (cookies.size() > 1) {
            list = new ArrayList<>(cookies);
            Collections.sort(list, g.INSTANCE);
        } else {
            list = cookies;
        }
        if (this.c) {
            return l(list);
        }
        return k(list);
    }

    private List<d> l(List<c> cookies) {
        int version = Integer.MAX_VALUE;
        for (c cookie : cookies) {
            if (cookie.getVersion() < version) {
                version = cookie.getVersion();
            }
        }
        org.apache.http.util.d buffer = new org.apache.http.util.d(cookies.size() * 40);
        buffer.append(HttpHeaders.HEAD_KEY_COOKIE);
        buffer.append(": ");
        buffer.append("$Version=");
        buffer.append(Integer.toString(version));
        for (c cooky : cookies) {
            buffer.append("; ");
            m(buffer, cooky, version);
        }
        List<Header> headers = new ArrayList<>(1);
        headers.add(new q(buffer));
        return headers;
    }

    private List<d> k(List<c> cookies) {
        List<Header> headers = new ArrayList<>(cookies.size());
        for (c cookie : cookies) {
            int version = cookie.getVersion();
            org.apache.http.util.d buffer = new org.apache.http.util.d(40);
            buffer.append("Cookie: ");
            buffer.append("$Version=");
            buffer.append(Integer.toString(version));
            buffer.append("; ");
            m(buffer, cookie, version);
            headers.add(new q(buffer));
        }
        return headers;
    }

    /* access modifiers changed from: protected */
    public void n(org.apache.http.util.d buffer, String name, String value, int version) {
        buffer.append(name);
        buffer.append("=");
        if (value == null) {
            return;
        }
        if (version > 0) {
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
            buffer.append(value);
            buffer.append((char) StringUtil.DOUBLE_QUOTE);
            return;
        }
        buffer.append(value);
    }

    /* access modifiers changed from: protected */
    public void m(org.apache.http.util.d buffer, c cookie, int version) {
        n(buffer, cookie.getName(), cookie.getValue(), version);
        if (cookie.getPath() != null && (cookie instanceof org.apache.http.cookie.a) && ((org.apache.http.cookie.a) cookie).containsAttribute("path")) {
            buffer.append("; ");
            n(buffer, "$Path", cookie.getPath(), version);
        }
        if (cookie.getDomain() != null && (cookie instanceof org.apache.http.cookie.a) && ((org.apache.http.cookie.a) cookie).containsAttribute(SerializableCookie.DOMAIN)) {
            buffer.append("; ");
            n(buffer, "$Domain", cookie.getDomain(), version);
        }
    }

    public int getVersion() {
        return 1;
    }

    public d d() {
        return null;
    }

    public String toString() {
        return "rfc2109";
    }
}
