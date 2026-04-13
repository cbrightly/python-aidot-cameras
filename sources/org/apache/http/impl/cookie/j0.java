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

/* compiled from: RFC2965DomainAttributeHandler */
public class j0 implements b {
    public void d(n cookie, String domain) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (domain == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        } else if (!domain.trim().isEmpty()) {
            String s = domain.toLowerCase(Locale.ROOT);
            if (!domain.startsWith(".")) {
                s = '.' + s;
            }
            cookie.setDomain(s);
        } else {
            throw new MalformedCookieException("Blank value for domain attribute");
        }
    }

    public boolean e(String host, String domain) {
        return host.equals(domain) || (domain.startsWith(".") && host.endsWith(domain));
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String a = origin.a();
        Locale locale = Locale.ROOT;
        String host = a.toLowerCase(locale);
        if (cookie.getDomain() != null) {
            String cookieDomain = cookie.getDomain().toLowerCase(locale);
            if (!(cookie instanceof org.apache.http.cookie.a) || !((org.apache.http.cookie.a) cookie).containsAttribute(SerializableCookie.DOMAIN)) {
                if (!cookie.getDomain().equals(host)) {
                    throw new CookieRestrictionViolationException("Illegal domain attribute: \"" + cookie.getDomain() + "\"." + "Domain of origin: \"" + host + "\"");
                }
            } else if (cookieDomain.startsWith(".")) {
                int dotIndex = cookieDomain.indexOf(46, 1);
                if ((dotIndex < 0 || dotIndex == cookieDomain.length() - 1) && !cookieDomain.equals(".local")) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: the value contains no embedded dots " + "and the value is not .local");
                } else if (!e(host, cookieDomain)) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host name does not " + "domain-match domain attribute.");
                } else if (host.substring(0, host.length() - cookieDomain.length()).indexOf(46) != -1) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: " + "effective host minus domain may not contain any dots");
                }
            } else {
                throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
            }
        } else {
            throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
        }
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a().toLowerCase(Locale.ROOT);
        String cookieDomain = cookie.getDomain();
        if (e(host, cookieDomain) && host.substring(0, host.length() - cookieDomain.length()).indexOf(46) == -1) {
            return true;
        }
        return false;
    }

    public String c() {
        return SerializableCookie.DOMAIN;
    }
}
