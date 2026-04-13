package org.apache.http.impl.cookie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.conn.util.e;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.n;
import org.apache.http.util.a;

/* compiled from: PublicSuffixDomainFilter */
public class c0 implements b {
    private final b a;
    private final e b;
    private final Map<String, Boolean> c = e();

    private static Map<String, Boolean> e() {
        ConcurrentHashMap<String, Boolean> map = new ConcurrentHashMap<>();
        Boolean bool = Boolean.TRUE;
        map.put(".localhost.", bool);
        map.put(".test.", bool);
        map.put(".local.", bool);
        map.put(".local", bool);
        map.put(".localdomain", bool);
        return map;
    }

    public c0(b handler, e publicSuffixMatcher) {
        this.a = (b) a.i(handler, "Cookie handler");
        this.b = (e) a.i(publicSuffixMatcher, "Public suffix matcher");
    }

    public boolean b(c cookie, f origin) {
        String host = cookie.getDomain();
        int i = host.indexOf(46);
        if (i >= 0) {
            if (!this.c.containsKey(host.substring(i)) && this.b.e(host)) {
                return false;
            }
        } else if (!host.equalsIgnoreCase(origin.a()) && this.b.e(host)) {
            return false;
        }
        return this.a.b(cookie, origin);
    }

    public void d(n cookie, String value) {
        this.a.d(cookie, value);
    }

    public void a(c cookie, f origin) {
        this.a.a(cookie, origin);
    }

    public String c() {
        return this.a.c();
    }

    public static b f(b handler, e publicSuffixMatcher) {
        a.i(handler, "Cookie attribute handler");
        return publicSuffixMatcher != null ? new c0(handler, publicSuffixMatcher) : handler;
    }
}
