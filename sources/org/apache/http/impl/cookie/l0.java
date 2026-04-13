package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.message.q;
import org.apache.http.u;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: RFC2965Spec */
public class l0 extends e0 {
    public l0() {
        this((String[]) null, false);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public l0(java.lang.String[] r4, boolean r5) {
        /*
            r3 = this;
            r0 = 10
            org.apache.http.cookie.b[] r0 = new org.apache.http.cookie.b[r0]
            org.apache.http.impl.cookie.n0 r1 = new org.apache.http.impl.cookie.n0
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            org.apache.http.impl.cookie.l0$a r1 = new org.apache.http.impl.cookie.l0$a
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            org.apache.http.impl.cookie.j0 r1 = new org.apache.http.impl.cookie.j0
            r1.<init>()
            r2 = 2
            r0[r2] = r1
            org.apache.http.impl.cookie.k0 r1 = new org.apache.http.impl.cookie.k0
            r1.<init>()
            r2 = 3
            r0[r2] = r1
            org.apache.http.impl.cookie.h r1 = new org.apache.http.impl.cookie.h
            r1.<init>()
            r2 = 4
            r0[r2] = r1
            org.apache.http.impl.cookie.j r1 = new org.apache.http.impl.cookie.j
            r1.<init>()
            r2 = 5
            r0[r2] = r1
            org.apache.http.impl.cookie.e r1 = new org.apache.http.impl.cookie.e
            r1.<init>()
            r2 = 6
            r0[r2] = r1
            org.apache.http.impl.cookie.g r1 = new org.apache.http.impl.cookie.g
            if (r4 == 0) goto L_0x0047
            java.lang.Object r2 = r4.clone()
            java.lang.String[] r2 = (java.lang.String[]) r2
            goto L_0x0049
        L_0x0047:
            java.lang.String[] r2 = org.apache.http.impl.cookie.e0.b
        L_0x0049:
            r1.<init>(r2)
            r2 = 7
            r0[r2] = r1
            r1 = 8
            org.apache.http.impl.cookie.h0 r2 = new org.apache.http.impl.cookie.h0
            r2.<init>()
            r0[r1] = r2
            r1 = 9
            org.apache.http.impl.cookie.i0 r2 = new org.apache.http.impl.cookie.i0
            r2.<init>()
            r0[r1] = r2
            r3.<init>((boolean) r5, (org.apache.http.cookie.b[]) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.l0.<init>(java.lang.String[], boolean):void");
    }

    /* compiled from: RFC2965Spec */
    public class a extends i {
        a() {
        }

        public void a(c cookie, f origin) {
            if (!b(cookie, origin)) {
                throw new CookieRestrictionViolationException("Illegal 'path' attribute \"" + cookie.getPath() + "\". Path of origin: \"" + origin.b() + "\"");
            }
        }
    }

    l0(boolean oneHeader, b... handlers) {
        super(oneHeader, handlers);
    }

    public List<c> c(d header, f origin) {
        org.apache.http.util.a.i(header, "Header");
        org.apache.http.util.a.i(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(HttpHeaders.HEAD_KEY_SET_COOKIE2)) {
            return p(header.getElements(), o(origin));
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    /* access modifiers changed from: protected */
    public List<c> j(e[] elems, f origin) {
        return p(elems, o(origin));
    }

    private List<c> p(e[] elems, f origin) {
        e[] arr$;
        List<Cookie> cookies = new ArrayList<>(elems.length);
        e[] arr$2 = elems;
        int len$ = arr$2.length;
        int i$ = 0;
        while (i$ < len$) {
            e headerelement = arr$2[i$];
            String name = headerelement.getName();
            String value = headerelement.getValue();
            if (name == null || name.isEmpty()) {
                e[] eVarArr = arr$2;
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            c cookie = new c(name, value);
            cookie.setPath(p.i(origin));
            cookie.setDomain(p.h(origin));
            cookie.setPorts(new int[]{origin.c()});
            u[] attribs = headerelement.getParameters();
            Map<String, NameValuePair> attribmap = new HashMap<>(attribs.length);
            for (int j = attribs.length - 1; j >= 0; j--) {
                u param = attribs[j];
                attribmap.put(param.getName().toLowerCase(Locale.ROOT), param);
            }
            for (Map.Entry<String, NameValuePair> entry : attribmap.entrySet()) {
                u attrib = (u) entry.getValue();
                String s = attrib.getName().toLowerCase(Locale.ROOT);
                cookie.setAttribute(s, attrib.getValue());
                org.apache.http.cookie.d handler = f(s);
                if (handler != null) {
                    arr$ = arr$2;
                    handler.d(cookie, attrib.getValue());
                } else {
                    arr$ = arr$2;
                }
                e[] eVarArr2 = elems;
                arr$2 = arr$;
            }
            e[] eVarArr3 = arr$2;
            cookies.add(cookie);
            i$++;
            e[] eVarArr4 = elems;
        }
        return cookies;
    }

    public void a(c cookie, f origin) {
        org.apache.http.util.a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        org.apache.http.util.a.i(origin, "Cookie origin");
        super.a(cookie, o(origin));
    }

    public boolean b(c cookie, f origin) {
        org.apache.http.util.a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        org.apache.http.util.a.i(origin, "Cookie origin");
        return super.b(cookie, o(origin));
    }

    /* access modifiers changed from: protected */
    public void m(org.apache.http.util.d buffer, c cookie, int version) {
        String s;
        int[] ports;
        super.m(buffer, cookie, version);
        if ((cookie instanceof org.apache.http.cookie.a) && (s = ((org.apache.http.cookie.a) cookie).getAttribute(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT)) != null) {
            buffer.append("; $Port");
            buffer.append("=\"");
            if (!s.trim().isEmpty() && (ports = cookie.getPorts()) != null) {
                int len = ports.length;
                for (int i = 0; i < len; i++) {
                    if (i > 0) {
                        buffer.append(",");
                    }
                    buffer.append(Integer.toString(ports[i]));
                }
            }
            buffer.append("\"");
        }
    }

    private static f o(f origin) {
        String host = origin.a();
        boolean isLocalHost = true;
        int i = 0;
        while (true) {
            if (i >= host.length()) {
                break;
            }
            char ch = host.charAt(i);
            if (ch == '.' || ch == ':') {
                isLocalHost = false;
            } else {
                i++;
            }
        }
        if (!isLocalHost) {
            return origin;
        }
        return new f(host + ".local", origin.c(), origin.b(), origin.d());
    }

    public int getVersion() {
        return 1;
    }

    public d d() {
        org.apache.http.util.d buffer = new org.apache.http.util.d(40);
        buffer.append(HttpHeaders.HEAD_KEY_COOKIE2);
        buffer.append(": ");
        buffer.append("$Version=");
        buffer.append(Integer.toString(getVersion()));
        return new q(buffer);
    }

    public String toString() {
        return "rfc2965";
    }
}
