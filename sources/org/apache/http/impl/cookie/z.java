package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.d;
import org.apache.http.e;
import org.apache.http.message.q;
import org.apache.http.message.v;
import org.apache.http.util.a;

/* compiled from: NetscapeDraftSpec */
public class z extends p {
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public z(java.lang.String[] r4) {
        /*
            r3 = this;
            r0 = 5
            org.apache.http.cookie.b[] r0 = new org.apache.http.cookie.b[r0]
            org.apache.http.impl.cookie.i r1 = new org.apache.http.impl.cookie.i
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            org.apache.http.impl.cookie.x r1 = new org.apache.http.impl.cookie.x
            r1.<init>()
            r2 = 1
            r0[r2] = r1
            org.apache.http.impl.cookie.j r1 = new org.apache.http.impl.cookie.j
            r1.<init>()
            r2 = 2
            r0[r2] = r1
            org.apache.http.impl.cookie.e r1 = new org.apache.http.impl.cookie.e
            r1.<init>()
            r2 = 3
            r0[r2] = r1
            org.apache.http.impl.cookie.g r1 = new org.apache.http.impl.cookie.g
            if (r4 == 0) goto L_0x002e
            java.lang.Object r2 = r4.clone()
            java.lang.String[] r2 = (java.lang.String[]) r2
            goto L_0x0034
        L_0x002e:
            java.lang.String r2 = "EEE, dd-MMM-yy HH:mm:ss z"
            java.lang.String[] r2 = new java.lang.String[]{r2}
        L_0x0034:
            r1.<init>(r2)
            r2 = 4
            r0[r2] = r1
            r3.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.z.<init>(java.lang.String[]):void");
    }

    z(b... handlers) {
        super(handlers);
    }

    public z() {
        this((String[]) null);
    }

    public List<c> c(d header, f origin) {
        v cursor;
        org.apache.http.util.d buffer;
        a.i(header, "Header");
        a.i(origin, "Cookie origin");
        if (header.getName().equalsIgnoreCase(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
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
            return j(new e[]{parser.a(buffer, cursor)}, origin);
        }
        throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
    }

    public List<d> e(List<c> cookies) {
        a.f(cookies, "List of cookies");
        org.apache.http.util.d buffer = new org.apache.http.util.d(cookies.size() * 20);
        buffer.append(HttpHeaders.HEAD_KEY_COOKIE);
        buffer.append(": ");
        for (int i = 0; i < cookies.size(); i++) {
            c cookie = cookies.get(i);
            if (i > 0) {
                buffer.append("; ");
            }
            buffer.append(cookie.getName());
            String s = cookie.getValue();
            if (s != null) {
                buffer.append("=");
                buffer.append(s);
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
        return "netscape";
    }
}
