package org.apache.http.impl.cookie;

import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.m;
import org.apache.http.cookie.n;

/* compiled from: RFC2965DiscardAttributeHandler */
public class i0 implements b {
    public void d(n cookie, String commenturl) {
        if (cookie instanceof m) {
            ((m) cookie).setDiscard(true);
        }
    }

    public void a(c cookie, f origin) {
    }

    public boolean b(c cookie, f origin) {
        return true;
    }

    public String c() {
        return "discard";
    }
}
