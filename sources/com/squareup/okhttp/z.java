package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;

/* compiled from: Route */
public final class z {
    final a a;
    final Proxy b;
    final InetSocketAddress c;

    public z(a address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress != null) {
            this.a = address;
            this.b = proxy;
            this.c = inetSocketAddress;
        } else {
            throw new NullPointerException("inetSocketAddress == null");
        }
    }

    public a a() {
        return this.a;
    }

    public Proxy b() {
        return this.b;
    }

    public InetSocketAddress c() {
        return this.c;
    }

    public boolean d() {
        return this.a.i != null && this.b.type() == Proxy.Type.HTTP;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof z)) {
            return false;
        }
        z other = (z) obj;
        if (!this.a.equals(other.a) || !this.b.equals(other.b) || !this.c.equals(other.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((17 * 31) + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }
}
