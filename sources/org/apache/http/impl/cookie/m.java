package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import meshsdk.ConfigUtil;
import org.apache.http.Header;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.message.q;
import org.apache.http.message.v;
import org.apache.http.u;

@Deprecated
/* compiled from: BrowserCompatSpec */
public class m extends p {
    private static final String[] b = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public m(java.lang.String[] r5, org.apache.http.impl.cookie.n.a r6) {
        /*
            r4 = this;
            r0 = 7
            org.apache.http.cookie.b[] r0 = new org.apache.http.cookie.b[r0]
            org.apache.http.impl.cookie.o r1 = new org.apache.http.impl.cookie.o
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            org.apache.http.impl.cookie.f r1 = new org.apache.http.impl.cookie.f
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            org.apache.http.impl.cookie.n$a r1 = org.apache.http.impl.cookie.n.a.SECURITYLEVEL_IE_MEDIUM
            if (r6 != r1) goto L_0x001d
            org.apache.http.impl.cookie.m$a r1 = new org.apache.http.impl.cookie.m$a
            r1.<init>()
            goto L_0x0022
        L_0x001d:
            org.apache.http.impl.cookie.i r1 = new org.apache.http.impl.cookie.i
            r1.<init>()
        L_0x0022:
            r2 = 2
            r0[r2] = r1
            r1 = 3
            org.apache.http.impl.cookie.h r2 = new org.apache.http.impl.cookie.h
            r2.<init>()
            r0[r1] = r2
            r1 = 4
            org.apache.http.impl.cookie.j r2 = new org.apache.http.impl.cookie.j
            r2.<init>()
            r0[r1] = r2
            r1 = 5
            org.apache.http.impl.cookie.e r2 = new org.apache.http.impl.cookie.e
            r2.<init>()
            r0[r1] = r2
            r1 = 6
            org.apache.http.impl.cookie.g r2 = new org.apache.http.impl.cookie.g
            if (r5 == 0) goto L_0x0049
            java.lang.Object r3 = r5.clone()
            java.lang.String[] r3 = (java.lang.String[]) r3
            goto L_0x004b
        L_0x0049:
            java.lang.String[] r3 = b
        L_0x004b:
            r2.<init>(r3)
            r0[r1] = r2
            r4.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.m.<init>(java.lang.String[], org.apache.http.impl.cookie.n$a):void");
    }

    /* compiled from: BrowserCompatSpec */
    public class a extends i {
        a() {
        }

        public void a(c cookie, f origin) {
        }
    }

    public List<c> c(d header, f origin) {
        v cursor;
        org.apache.http.util.d buffer;
        m mVar = this;
        d dVar = header;
        f fVar = origin;
        org.apache.http.util.a.i(dVar, "Header");
        org.apache.http.util.a.i(fVar, "Cookie origin");
        if (header.getName().equalsIgnoreCase(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
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
            if (!netscape && versioned) {
                return mVar.j(helems, fVar);
            }
            y parser = y.a;
            if (dVar instanceof org.apache.http.c) {
                buffer = ((org.apache.http.c) dVar).getBuffer();
                cursor = new v(((org.apache.http.c) dVar).getValuePos(), buffer.length());
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
            e elem = parser.a(buffer, cursor);
            String name = elem.getName();
            String value = elem.getValue();
            if (name == null || name.isEmpty()) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            d cookie = new d(name, value);
            cookie.setPath(p.i(origin));
            cookie.setDomain(p.h(origin));
            u[] attribs = elem.getParameters();
            int j = attribs.length - 1;
            while (j >= 0) {
                u attrib = attribs[j];
                String s2 = attrib.getName().toLowerCase(Locale.ROOT);
                cookie.setAttribute(s2, attrib.getValue());
                org.apache.http.cookie.d handler = mVar.f(s2);
                if (handler != null) {
                    handler.d(cookie, attrib.getValue());
                }
                j--;
                mVar = this;
                d dVar2 = header;
                f fVar2 = origin;
            }
            if (netscape) {
                cookie.setVersion(0);
            }
            return Collections.singletonList(cookie);
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    private static boolean k(String s) {
        return s != null && s.startsWith("\"") && s.endsWith("\"");
    }

    public List<d> e(List<c> cookies) {
        org.apache.http.util.a.f(cookies, "List of cookies");
        org.apache.http.util.d buffer = new org.apache.http.util.d(cookies.size() * 20);
        buffer.append(HttpHeaders.HEAD_KEY_COOKIE);
        buffer.append(": ");
        for (int i = 0; i < cookies.size(); i++) {
            c cookie = cookies.get(i);
            if (i > 0) {
                buffer.append("; ");
            }
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (cookie.getVersion() <= 0 || k(cookieValue)) {
                buffer.append(cookieName);
                buffer.append("=");
                if (cookieValue != null) {
                    buffer.append(cookieValue);
                }
            } else {
                org.apache.http.message.f.b.e(buffer, new org.apache.http.message.c(cookieName, cookieValue), false);
            }
        }
        List<Header> headers = new ArrayList<>(1);
        headers.add(new q(buffer));
        return headers;
    }

    public int getVersion() {
        return 0;
    }

    public d d() {
        return null;
    }

    public String toString() {
        return "compatibility";
    }
}
