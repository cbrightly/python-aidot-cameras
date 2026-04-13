package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Locale;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: RFC2109DomainHandler */
public class d0 implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        } else if (!value.trim().isEmpty()) {
            cookie.setDomain(value);
        } else {
            throw new MalformedCookieException("Blank value for domain attribute");
        }
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a();
        String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        } else if (domain.equals(host)) {
        } else {
            if (domain.indexOf(46) == -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" does not match the host \"" + host + "\"");
            } else if (domain.startsWith(".")) {
                int dotIndex = domain.indexOf(46, 1);
                if (dotIndex < 0 || dotIndex == domain.length() - 1) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must contain an embedded dot");
                }
                String host2 = host.toLowerCase(Locale.ROOT);
                if (!host2.endsWith(domain)) {
                    throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host2 + "\"");
                } else if (host2.substring(0, host2.length() - domain.length()).indexOf(46) != -1) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: host minus domain may not contain any dots");
                }
            } else {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must start with a dot");
            }
        }
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        if (host.equals(domain) || (domain.startsWith(".") && host.endsWith(domain))) {
            return true;
        }
        return false;
    }

    public String c() {
        return SerializableCookie.DOMAIN;
    }
}
