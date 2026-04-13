package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.util.a;
import org.apache.http.util.h;

@Deprecated
/* compiled from: Scheme */
public final class f {
    private final String a;
    private final k b;
    private final int c;
    private final boolean d;
    private String e;

    public f(String name, int port, k factory) {
        a.i(name, "Scheme name");
        a.a(port > 0 && port <= 65535, "Port is invalid");
        a.i(factory, "Socket factory");
        this.a = name.toLowerCase(Locale.ENGLISH);
        this.c = port;
        if (factory instanceof g) {
            this.d = true;
            this.b = factory;
        } else if (factory instanceof b) {
            this.d = true;
            this.b = new h((b) factory);
        } else {
            this.d = false;
            this.b = factory;
        }
    }

    @Deprecated
    public f(String name, m factory, int port) {
        a.i(name, "Scheme name");
        a.i(factory, "Socket factory");
        a.a(port > 0 && port <= 65535, "Port is invalid");
        this.a = name.toLowerCase(Locale.ENGLISH);
        if (factory instanceof c) {
            this.b = new i((c) factory);
            this.d = true;
        } else {
            this.b = new l(factory);
            this.d = false;
        }
        this.c = port;
    }

    public final int a() {
        return this.c;
    }

    @Deprecated
    public final m d() {
        k kVar = this.b;
        if (kVar instanceof l) {
            return ((l) kVar).c();
        }
        if (this.d) {
            return new d((b) kVar);
        }
        return new n(kVar);
    }

    public final k c() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }

    public final boolean e() {
        return this.d;
    }

    public final int f(int port) {
        return port <= 0 ? this.c : port;
    }

    public final String toString() {
        if (this.e == null) {
            this.e = this.a + ':' + Integer.toString(this.c);
        }
        return this.e;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f that = (f) obj;
        if (this.a.equals(that.a) && this.c == that.c && this.d == that.d) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return h.e(h.d(h.c(17, this.c), this.a), this.d);
    }
}
