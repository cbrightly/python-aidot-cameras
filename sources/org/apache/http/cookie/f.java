package org.apache.http.cookie;

import java.util.Locale;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: CookieOrigin */
public final class f {
    private final String a;
    private final int b;
    private final String c;
    private final boolean d;

    public f(String host, int port, String path, boolean secure) {
        a.d(host, "Host");
        a.g(port, "Port");
        a.i(path, "Path");
        this.a = host.toLowerCase(Locale.ROOT);
        this.b = port;
        if (!j.b(path)) {
            this.c = path;
        } else {
            this.c = "/";
        }
        this.d = secure;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.c;
    }

    public int c() {
        return this.b;
    }

    public boolean d() {
        return this.d;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        if (this.d) {
            buffer.append("(secure)");
        }
        buffer.append(this.a);
        buffer.append(':');
        buffer.append(Integer.toString(this.b));
        buffer.append(this.c);
        buffer.append(']');
        return buffer.toString();
    }
}
