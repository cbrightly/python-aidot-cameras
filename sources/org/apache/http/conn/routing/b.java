package org.apache.http.conn.routing;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.conn.routing.e;
import org.apache.http.l;
import org.apache.http.util.a;
import org.apache.http.util.h;

/* compiled from: HttpRoute */
public final class b implements e, Cloneable {
    private final l c;
    private final InetAddress d;
    private final List<l> f;
    private final e.b q;
    private final e.a x;
    private final boolean y;

    private b(l target, InetAddress local, List<l> proxies, boolean secure, e.b tunnelled, e.a layered) {
        a.i(target, "Target host");
        this.c = j(target);
        this.d = local;
        if (proxies == null || proxies.isEmpty()) {
            this.f = null;
        } else {
            this.f = new ArrayList(proxies);
        }
        if (tunnelled == e.b.TUNNELLED) {
            a.a(this.f != null, "Proxy required if tunnelled");
        }
        this.y = secure;
        this.q = tunnelled != null ? tunnelled : e.b.PLAIN;
        this.x = layered != null ? layered : e.a.PLAIN;
    }

    private static int h(String schemeName) {
        if (l.DEFAULT_SCHEME_NAME.equalsIgnoreCase(schemeName)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(schemeName)) {
            return 443;
        }
        return -1;
    }

    private static l j(l target) {
        if (target.getPort() >= 0) {
            return target;
        }
        InetAddress address = target.getAddress();
        String schemeName = target.getSchemeName();
        if (address != null) {
            return new l(address, h(schemeName), schemeName);
        }
        return new l(target.getHostName(), h(schemeName), schemeName);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public b(l target, InetAddress local, l[] proxies, boolean secure, e.b tunnelled, e.a layered) {
        this(target, local, (List<l>) proxies != null ? Arrays.asList(proxies) : null, secure, tunnelled, layered);
    }

    public b(l target, InetAddress local, boolean secure) {
        this(target, local, (List<l>) Collections.emptyList(), secure, e.b.PLAIN, e.a.PLAIN);
    }

    public b(l target) {
        this(target, (InetAddress) null, (List<l>) Collections.emptyList(), false, e.b.PLAIN, e.a.PLAIN);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public b(l target, InetAddress local, l proxy, boolean secure) {
        this(target, local, (List<l>) Collections.singletonList(a.i(proxy, "Proxy host")), secure, secure ? e.b.TUNNELLED : e.b.PLAIN, secure ? e.a.LAYERED : e.a.PLAIN);
    }

    public final l e() {
        return this.c;
    }

    public final InetAddress getLocalAddress() {
        return this.d;
    }

    public final InetSocketAddress i() {
        if (this.d != null) {
            return new InetSocketAddress(this.d, 0);
        }
        return null;
    }

    public final int a() {
        List<l> list = this.f;
        if (list != null) {
            return 1 + list.size();
        }
        return 1;
    }

    public final l d(int hop) {
        a.g(hop, "Hop index");
        int hopcount = a();
        a.a(hop < hopcount, "Hop index exceeds tracked route length");
        if (hop < hopcount - 1) {
            return this.f.get(hop);
        }
        return this.c;
    }

    public final l c() {
        List<l> list = this.f;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.f.get(0);
    }

    public final boolean b() {
        return this.q == e.b.TUNNELLED;
    }

    public final boolean f() {
        return this.x == e.a.LAYERED;
    }

    public final boolean isSecure() {
        return this.y;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b that = (b) obj;
        if (this.y != that.y || this.q != that.q || this.x != that.x || !h.a(this.c, that.c) || !h.a(this.d, that.d) || !h.a(this.f, that.f)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hash = h.d(h.d(17, this.c), this.d);
        List<l> list = this.f;
        if (list != null) {
            for (l element : list) {
                hash = h.d(hash, element);
            }
        }
        return h.d(h.d(h.e(hash, this.y), this.q), this.x);
    }

    public final String toString() {
        StringBuilder cab = new StringBuilder((a() * 30) + 50);
        InetAddress inetAddress = this.d;
        if (inetAddress != null) {
            cab.append(inetAddress);
            cab.append("->");
        }
        cab.append('{');
        if (this.q == e.b.TUNNELLED) {
            cab.append('t');
        }
        if (this.x == e.a.LAYERED) {
            cab.append('l');
        }
        if (this.y) {
            cab.append('s');
        }
        cab.append("}->");
        List<l> list = this.f;
        if (list != null) {
            for (l aProxyChain : list) {
                cab.append(aProxyChain);
                cab.append("->");
            }
        }
        cab.append(this.c);
        return cab.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
