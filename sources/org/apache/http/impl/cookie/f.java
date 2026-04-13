package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Locale;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.n;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: BasicDomainHandler */
public class f implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (j.b(value)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        } else if (!value.endsWith(".")) {
            String domain = value;
            if (domain.startsWith(".")) {
                domain = domain.substring(1);
            }
            cookie.setDomain(domain.toLowerCase(Locale.ROOT));
        }
    }

    public void a(c cookie, org.apache.http.cookie.f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a();
        String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie 'domain' may not be null");
        } else if (!host.equals(domain) && !e(domain, host)) {
            throw new CookieRestrictionViolationException("Illegal 'domain' attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
        }
    }

    static boolean e(String domain, String host) {
        if (org.apache.http.conn.util.b.a(host) || org.apache.http.conn.util.b.b(host)) {
            return false;
        }
        String normalizedDomain = domain.startsWith(".") ? domain.substring(1) : domain;
        if (host.endsWith(normalizedDomain)) {
            int prefix = host.length() - normalizedDomain.length();
            if (prefix == 0) {
                return true;
            }
            if (prefix <= 1 || host.charAt(prefix - 1) != '.') {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean b(c cookie, org.apache.http.cookie.f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        if (domain.startsWith(".")) {
            domain = domain.substring(1);
        }
        String domain2 = domain.toLowerCase(Locale.ROOT);
        if (host.equals(domain2)) {
            return true;
        }
        if (!(cookie instanceof org.apache.http.cookie.a) || !((org.apache.http.cookie.a) cookie).containsAttribute(SerializableCookie.DOMAIN)) {
            return false;
        }
        return e(domain2, host);
    }

    public String c() {
        return SerializableCookie.DOMAIN;
    }
}
