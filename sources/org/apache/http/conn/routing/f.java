package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.conn.routing.e;
import org.apache.http.l;
import org.apache.http.util.a;
import org.apache.http.util.b;
import org.apache.http.util.h;

/* compiled from: RouteTracker */
public final class f implements e, Cloneable {
    private final l c;
    private final InetAddress d;
    private boolean f;
    private l[] q;
    private e.b x;
    private e.a y;
    private boolean z;

    public f(l target, InetAddress local) {
        a.i(target, "Target host");
        this.c = target;
        this.d = local;
        this.x = e.b.PLAIN;
        this.y = e.a.PLAIN;
    }

    public void l() {
        this.f = false;
        this.q = null;
        this.x = e.b.PLAIN;
        this.y = e.a.PLAIN;
        this.z = false;
    }

    public f(b route) {
        this(route.e(), route.getLocalAddress());
    }

    public final void i(boolean secure) {
        b.a(!this.f, "Already connected");
        this.f = true;
        this.z = secure;
    }

    public final void h(l proxy, boolean secure) {
        a.i(proxy, "Proxy host");
        b.a(!this.f, "Already connected");
        this.f = true;
        this.q = new l[]{proxy};
        this.z = secure;
    }

    public final void o(boolean secure) {
        b.a(this.f, "No tunnel unless connected");
        b.c(this.q, "No tunnel without proxy");
        this.x = e.b.TUNNELLED;
        this.z = secure;
    }

    public final void n(l proxy, boolean secure) {
        a.i(proxy, "Proxy host");
        b.a(this.f, "No tunnel unless connected");
        b.c(this.q, "No tunnel without proxy");
        l[] lVarArr = this.q;
        l[] proxies = new l[(lVarArr.length + 1)];
        System.arraycopy(lVarArr, 0, proxies, 0, lVarArr.length);
        proxies[proxies.length - 1] = proxy;
        this.q = proxies;
        this.z = secure;
    }

    public final void k(boolean secure) {
        b.a(this.f, "No layered protocol unless connected");
        this.y = e.a.LAYERED;
        this.z = secure;
    }

    public final l e() {
        return this.c;
    }

    public final InetAddress getLocalAddress() {
        return this.d;
    }

    public final int a() {
        if (!this.f) {
            return 0;
        }
        l[] lVarArr = this.q;
        if (lVarArr == null) {
            return 1;
        }
        return lVarArr.length + 1;
    }

    public final l d(int hop) {
        a.g(hop, "Hop index");
        int hopcount = a();
        a.a(hop < hopcount, "Hop index exceeds tracked route length");
        if (hop < hopcount - 1) {
            return this.q[hop];
        }
        return this.c;
    }

    public final l c() {
        l[] lVarArr = this.q;
        if (lVarArr == null) {
            return null;
        }
        return lVarArr[0];
    }

    public final boolean j() {
        return this.f;
    }

    public final boolean b() {
        return this.x == e.b.TUNNELLED;
    }

    public final boolean f() {
        return this.y == e.a.LAYERED;
    }

    public final boolean isSecure() {
        return this.z;
    }

    public final b m() {
        if (!this.f) {
            return null;
        }
        return new b(this.c, this.d, this.q, this.z, this.x, this.y);
    }

    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof f)) {
            return false;
        }
        f that = (f) o;
        if (this.f == that.f && this.z == that.z && this.x == that.x && this.y == that.y && h.a(this.c, that.c) && h.a(this.d, that.d) && h.b(this.q, that.q)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hash = h.d(h.d(17, this.c), this.d);
        if (this.q != null) {
            for (l element : this.q) {
                hash = h.d(hash, element);
            }
        }
        return h.d(h.d(h.e(h.e(hash, this.f), this.z), this.x), this.y);
    }

    public final String toString() {
        StringBuilder cab = new StringBuilder((a() * 30) + 50);
        cab.append("RouteTracker[");
        InetAddress inetAddress = this.d;
        if (inetAddress != null) {
            cab.append(inetAddress);
            cab.append("->");
        }
        cab.append('{');
        if (this.f) {
            cab.append('c');
        }
        if (this.x == e.b.TUNNELLED) {
            cab.append('t');
        }
        if (this.y == e.a.LAYERED) {
            cab.append('l');
        }
        if (this.z) {
            cab.append('s');
        }
        cab.append("}->");
        if (this.q != null) {
            for (l element : this.q) {
                cab.append(element);
                cab.append("->");
            }
        }
        cab.append(this.c);
        cab.append(']');
        return cab.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
