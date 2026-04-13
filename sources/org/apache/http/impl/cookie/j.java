package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: BasicSecureHandler */
public class j extends a implements b {
    public void d(n cookie, String value) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        cookie.setSecure(true);
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        return !cookie.isSecure() || origin.d();
    }

    public String c() {
        return "secure";
    }
}
