package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.d;
import org.apache.http.cookie.f;
import org.apache.http.e;
import org.apache.http.u;
import org.apache.http.util.a;

/* compiled from: CookieSpecBase */
public abstract class p extends b {
    public p() {
    }

    protected p(b... handlers) {
        super(handlers);
    }

    protected static String i(f origin) {
        String defaultPath = origin.b();
        int lastSlashIndex = defaultPath.lastIndexOf(47);
        if (lastSlashIndex < 0) {
            return defaultPath;
        }
        if (lastSlashIndex == 0) {
            lastSlashIndex = 1;
        }
        return defaultPath.substring(0, lastSlashIndex);
    }

    protected static String h(f origin) {
        return origin.a();
    }

    /* access modifiers changed from: protected */
    public List<c> j(e[] elems, f origin) {
        List<Cookie> cookies = new ArrayList<>(elems.length);
        for (e headerelement : elems) {
            String name = headerelement.getName();
            String value = headerelement.getValue();
            if (name != null) {
                if (!name.isEmpty()) {
                    d cookie = new d(name, value);
                    cookie.setPath(i(origin));
                    cookie.setDomain(h(origin));
                    u[] attribs = headerelement.getParameters();
                    for (int j = attribs.length - 1; j >= 0; j--) {
                        u attrib = attribs[j];
                        String s = attrib.getName().toLowerCase(Locale.ROOT);
                        cookie.setAttribute(s, attrib.getValue());
                        d handler = f(s);
                        if (handler != null) {
                            handler.d(cookie, attrib.getValue());
                        }
                    }
                    cookies.add(cookie);
                }
            }
        }
        return cookies;
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        for (d handler : g()) {
            handler.a(cookie, origin);
        }
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        for (d handler : g()) {
            if (!handler.b(cookie, origin)) {
                return false;
            }
        }
        return true;
    }
}
