package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.List;
import meshsdk.ConfigUtil;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.i;
import org.apache.http.cookie.m;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.message.v;
import org.apache.http.util.a;

/* compiled from: DefaultCookieSpec */
public class q implements i {
    private final l0 a;
    private final e0 b;
    private final z c;

    q(l0 strict, e0 obsoleteStrict, z netscapeDraft) {
        this.a = strict;
        this.b = obsoleteStrict;
        this.c = netscapeDraft;
    }

    public q(String[] datepatterns, boolean oneHeader) {
        this.a = new l0(oneHeader, new n0(), new i(), new j0(), new k0(), new h(), new j(), new e(), new h0(), new i0());
        this.b = new e0(oneHeader, new g0(), new i(), new d0(), new h(), new j(), new e());
        b[] bVarArr = new b[5];
        bVarArr[0] = new f();
        bVarArr[1] = new i();
        bVarArr[2] = new j();
        bVarArr[3] = new e();
        bVarArr[4] = new g(datepatterns != null ? (String[]) datepatterns.clone() : new String[]{"EEE, dd-MMM-yy HH:mm:ss z"});
        this.c = new z(bVarArr);
    }

    public List<c> c(d header, f origin) {
        v cursor;
        org.apache.http.util.d buffer;
        a.i(header, "Header");
        a.i(origin, "Cookie origin");
        e[] helems = header.getElements();
        boolean versioned = false;
        boolean netscape = false;
        for (e helem : helems) {
            if (helem.c(ConfigUtil.VERSION_FILE) != null) {
                versioned = true;
            }
            if (helem.c("expires") != null) {
                netscape = true;
            }
        }
        if (netscape || !versioned) {
            y parser = y.a;
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
            return this.c.j(new e[]{parser.a(buffer, cursor)}, origin);
        } else if (HttpHeaders.HEAD_KEY_SET_COOKIE2.equals(header.getName())) {
            return this.a.j(helems, origin);
        } else {
            return this.b.j(helems, origin);
        }
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        if (cookie.getVersion() <= 0) {
            this.c.a(cookie, origin);
        } else if (cookie instanceof m) {
            this.a.a(cookie, origin);
        } else {
            this.b.a(cookie, origin);
        }
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        if (cookie.getVersion() <= 0) {
            return this.c.b(cookie, origin);
        }
        if (cookie instanceof m) {
            return this.a.b(cookie, origin);
        }
        return this.b.b(cookie, origin);
    }

    public List<d> e(List<c> cookies) {
        a.i(cookies, "List of cookies");
        int version = Integer.MAX_VALUE;
        boolean isSetCookie2 = true;
        for (c cookie : cookies) {
            if (!(cookie instanceof m)) {
                isSetCookie2 = false;
            }
            if (cookie.getVersion() < version) {
                version = cookie.getVersion();
            }
        }
        if (version <= 0) {
            return this.c.e(cookies);
        }
        if (isSetCookie2) {
            return this.a.e(cookies);
        }
        return this.b.e(cookies);
    }

    public int getVersion() {
        return this.a.getVersion();
    }

    public d d() {
        return null;
    }

    public String toString() {
        return "default";
    }
}
