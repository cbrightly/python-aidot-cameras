package org.apache.http.auth;

import java.util.Locale;
import org.apache.http.l;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: AuthScope */
public class g {
    public static final String a = null;
    public static final String b = null;
    public static final String c = null;
    public static final g d = new g((String) null, -1, (String) null, (String) null);
    private final String e;
    private final String f;
    private final String g;
    private final int h;
    private final l i;

    public g(String host, int port, String realm, String schemeName) {
        this.g = host == null ? a : host.toLowerCase(Locale.ROOT);
        this.h = port < 0 ? -1 : port;
        this.f = realm == null ? b : realm;
        this.e = schemeName == null ? c : schemeName.toUpperCase(Locale.ROOT);
        this.i = null;
    }

    public g(l origin, String realm, String schemeName) {
        a.i(origin, "Host");
        String hostName = origin.getHostName();
        Locale locale = Locale.ROOT;
        this.g = hostName.toLowerCase(locale);
        this.h = origin.getPort() < 0 ? -1 : origin.getPort();
        this.f = realm == null ? b : realm;
        this.e = schemeName == null ? c : schemeName.toUpperCase(locale);
        this.i = origin;
    }

    public g(l origin) {
        this(origin, b, c);
    }

    public g(String host, int port) {
        this(host, port, b, c);
    }

    public l b() {
        return this.i;
    }

    public String a() {
        return this.g;
    }

    public int c() {
        return this.h;
    }

    public String d() {
        return this.e;
    }

    public int e(g that) {
        int factor = 0;
        if (h.a(this.e, that.e)) {
            factor = 0 + 1;
        } else {
            String str = this.e;
            String str2 = c;
            if (!(str == str2 || that.e == str2)) {
                return -1;
            }
        }
        if (h.a(this.f, that.f)) {
            factor += 2;
        } else {
            String str3 = this.f;
            String str4 = b;
            if (!(str3 == str4 || that.f == str4)) {
                return -1;
            }
        }
        int i2 = this.h;
        int i3 = that.h;
        if (i2 == i3) {
            factor += 4;
        } else if (!(i2 == -1 || i3 == -1)) {
            return -1;
        }
        if (h.a(this.g, that.g)) {
            return factor + 8;
        }
        String str5 = this.g;
        String str6 = a;
        if (str5 == str6 || that.g == str6) {
            return factor;
        }
        return -1;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof g)) {
            return super.equals(o);
        }
        g that = (g) o;
        if (!h.a(this.g, that.g) || this.h != that.h || !h.a(this.f, that.f) || !h.a(this.e, that.e)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        String str = this.e;
        if (str != null) {
            buffer.append(str.toUpperCase(Locale.ROOT));
            buffer.append(' ');
        }
        if (this.f != null) {
            buffer.append('\'');
            buffer.append(this.f);
            buffer.append('\'');
        } else {
            buffer.append("<any realm>");
        }
        if (this.g != null) {
            buffer.append('@');
            buffer.append(this.g);
            if (this.h >= 0) {
                buffer.append(':');
                buffer.append(this.h);
            }
        }
        return buffer.toString();
    }

    public int hashCode() {
        return h.d(h.d(h.c(h.d(17, this.g), this.h), this.f), this.e);
    }
}
