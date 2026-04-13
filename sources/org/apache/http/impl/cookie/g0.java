package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import meshsdk.ConfigUtil;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: RFC2109VersionHandler */
public class g0 extends a implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (value == null) {
            throw new MalformedCookieException("Missing value for version attribute");
        } else if (!value.trim().isEmpty()) {
            try {
                cookie.setVersion(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new MalformedCookieException("Invalid version: " + e.getMessage());
            }
        } else {
            throw new MalformedCookieException("Blank value for version attribute");
        }
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (cookie.getVersion() < 0) {
            throw new CookieRestrictionViolationException("Cookie version may not be negative");
        }
    }

    public String c() {
        return ConfigUtil.VERSION_FILE;
    }
}
