package org.apache.http.client.protocol;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.c;
import org.apache.http.cookie.i;
import org.apache.http.d;
import org.apache.http.g;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.s;

/* compiled from: ResponseProcessCookies */
public class l implements s {
    private final a c = h.n(getClass());

    public void a(q response, f context) {
        org.apache.http.util.a.i(response, "HTTP request");
        org.apache.http.util.a.i(context, "HTTP context");
        a clientContext = a.g(context);
        i cookieSpec = clientContext.k();
        if (cookieSpec == null) {
            this.c.debug("Cookie spec not specified in HTTP context");
            return;
        }
        org.apache.http.client.f cookieStore = clientContext.m();
        if (cookieStore == null) {
            this.c.debug("Cookie store not specified in HTTP context");
            return;
        }
        org.apache.http.cookie.f cookieOrigin = clientContext.j();
        if (cookieOrigin == null) {
            this.c.debug("Cookie origin not specified in HTTP context");
            return;
        }
        c(response.o(HttpHeaders.HEAD_KEY_SET_COOKIE), cookieSpec, cookieOrigin, cookieStore);
        if (cookieSpec.getVersion() > 0) {
            c(response.o(HttpHeaders.HEAD_KEY_SET_COOKIE2), cookieSpec, cookieOrigin, cookieStore);
        }
    }

    private void c(g iterator, i cookieSpec, org.apache.http.cookie.f cookieOrigin, org.apache.http.client.f cookieStore) {
        while (iterator.hasNext()) {
            d header = iterator.a();
            try {
                for (c cookie : cookieSpec.c(header, cookieOrigin)) {
                    try {
                        cookieSpec.a(cookie, cookieOrigin);
                        cookieStore.addCookie(cookie);
                        if (this.c.isDebugEnabled()) {
                            a aVar = this.c;
                            aVar.debug("Cookie accepted [" + b(cookie) + "]");
                        }
                    } catch (MalformedCookieException ex) {
                        if (this.c.isWarnEnabled()) {
                            a aVar2 = this.c;
                            aVar2.warn("Cookie rejected [" + b(cookie) + "] " + ex.getMessage());
                        }
                    }
                }
            } catch (MalformedCookieException ex2) {
                if (this.c.isWarnEnabled()) {
                    a aVar3 = this.c;
                    aVar3.warn("Invalid cookie header: \"" + header + "\". " + ex2.getMessage());
                }
            }
        }
    }

    private static String b(c cookie) {
        StringBuilder buf = new StringBuilder();
        buf.append(cookie.getName());
        buf.append("=\"");
        String v = cookie.getValue();
        if (v != null) {
            if (v.length() > 100) {
                v = v.substring(0, 100) + "...";
            }
            buf.append(v);
        }
        buf.append("\"");
        buf.append(", version:");
        buf.append(Integer.toString(cookie.getVersion()));
        buf.append(", domain:");
        buf.append(cookie.getDomain());
        buf.append(", path:");
        buf.append(cookie.getPath());
        buf.append(", expiry:");
        buf.append(cookie.getExpiryDate());
        return buf.toString();
    }
}
