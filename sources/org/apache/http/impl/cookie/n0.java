package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import meshsdk.ConfigUtil;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.m;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: RFC2965VersionAttributeHandler */
public class n0 implements b {
    public void d(n cookie, String value) {
        int version;
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value != null) {
            try {
                version = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                version = -1;
            }
            if (version >= 0) {
                cookie.setVersion(version);
                return;
            }
            throw new MalformedCookieException("Invalid cookie version.");
        }
        throw new MalformedCookieException("Missing value for version attribute");
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if ((cookie instanceof m) && (cookie instanceof org.apache.http.cookie.a) && !((org.apache.http.cookie.a) cookie).containsAttribute(ConfigUtil.VERSION_FILE)) {
            throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
        }
    }

    public boolean b(c cookie, f origin) {
        return true;
    }

    public String c() {
        return ConfigUtil.VERSION_FILE;
    }
}
