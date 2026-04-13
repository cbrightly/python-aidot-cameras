package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: BasicPathHandler */
public class i implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        cookie.setPath(!j.b(value) ? value : "/");
    }

    public void a(c cookie, f origin) {
    }

    static boolean e(String uriPath, String cookiePath) {
        String normalizedCookiePath = cookiePath;
        if (normalizedCookiePath == null) {
            normalizedCookiePath = "/";
        }
        if (normalizedCookiePath.length() > 1 && normalizedCookiePath.endsWith("/")) {
            normalizedCookiePath = normalizedCookiePath.substring(0, normalizedCookiePath.length() - 1);
        }
        return uriPath.startsWith(normalizedCookiePath) && (normalizedCookiePath.equals("/") || uriPath.length() == normalizedCookiePath.length() || uriPath.charAt(normalizedCookiePath.length()) == '/');
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        return e(origin.b(), cookie.getPath());
    }

    public String c() {
        return "path";
    }
}
