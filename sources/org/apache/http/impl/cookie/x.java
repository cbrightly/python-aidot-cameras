package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: NetscapeDomainHandler */
public class x extends f {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (!j.b(value)) {
            cookie.setDomain(value);
            return;
        }
        throw new MalformedCookieException("Blank or null value for domain attribute");
    }

    public void a(c cookie, f origin) {
        String host = origin.a();
        String domain = cookie.getDomain();
        if (!host.equals(domain) && !f.e(domain, host)) {
            throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
        } else if (host.contains(".")) {
            int domainParts = new StringTokenizer(domain, ".").countTokens();
            if (f(domain)) {
                if (domainParts < 2) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification for " + "special domains");
                }
            } else if (domainParts < 3) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification");
            }
        }
    }

    private static boolean f(String domain) {
        String ucDomain = domain.toUpperCase(Locale.ROOT);
        return ucDomain.endsWith(".COM") || ucDomain.endsWith(".EDU") || ucDomain.endsWith(".NET") || ucDomain.endsWith(".GOV") || ucDomain.endsWith(".MIL") || ucDomain.endsWith(".ORG") || ucDomain.endsWith(".INT");
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        String host = origin.a();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        return host.endsWith(domain);
    }

    public String c() {
        return SerializableCookie.DOMAIN;
    }
}
