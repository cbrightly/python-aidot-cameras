package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.a;
import com.squareup.okhttp.internal.i;
import com.squareup.okhttp.q;
import com.squareup.okhttp.z;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: RouteSelector */
public final class o {
    private final a a;
    private final i b;
    private Proxy c;
    private InetSocketAddress d;
    private List<Proxy> e = Collections.emptyList();
    private int f;
    private List<InetSocketAddress> g = Collections.emptyList();
    private int h;
    private final List<z> i = new ArrayList();

    public o(a address, i routeDatabase) {
        this.a = address;
        this.b = routeDatabase;
        l(address.m(), address.g());
    }

    public boolean c() {
        return d() || f() || e();
    }

    public z g() {
        if (!d()) {
            if (f()) {
                this.c = j();
            } else if (e()) {
                return i();
            } else {
                throw new NoSuchElementException();
            }
        }
        InetSocketAddress h2 = h();
        this.d = h2;
        z route = new z(this.a, this.c, h2);
        if (!this.b.c(route)) {
            return route;
        }
        this.i.add(route);
        return g();
    }

    public void a(z failedRoute, IOException failure) {
        if (!(failedRoute.b().type() == Proxy.Type.DIRECT || this.a.h() == null)) {
            this.a.h().connectFailed(this.a.m().F(), failedRoute.b().address(), failure);
        }
        this.b.b(failedRoute);
    }

    private void l(q url, Proxy proxy) {
        if (proxy != null) {
            this.e = Collections.singletonList(proxy);
        } else {
            this.e = new ArrayList();
            List<Proxy> selectedProxies = this.a.h().select(url.F());
            if (selectedProxies != null) {
                this.e.addAll(selectedProxies);
            }
            this.e.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.e.add(Proxy.NO_PROXY);
        }
        this.f = 0;
    }

    private boolean f() {
        return this.f < this.e.size();
    }

    private Proxy j() {
        if (f()) {
            List<Proxy> list = this.e;
            int i2 = this.f;
            this.f = i2 + 1;
            Proxy result = list.get(i2);
            k(result);
            return result;
        }
        throw new SocketException("No route to " + this.a.k() + "; exhausted proxy configurations: " + this.e);
    }

    private void k(Proxy proxy) {
        int socketPort;
        String socketHost;
        this.g = new ArrayList();
        if (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) {
            socketHost = this.a.k();
            socketPort = this.a.l();
        } else {
            SocketAddress proxyAddress = proxy.address();
            if (proxyAddress instanceof InetSocketAddress) {
                InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
                socketHost = b(proxySocketAddress);
                socketPort = proxySocketAddress.getPort();
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
            }
        }
        if (socketPort < 1 || socketPort > 65535) {
            throw new SocketException("No route to " + socketHost + ":" + socketPort + "; port is out of range");
        }
        if (proxy.type() == Proxy.Type.SOCKS) {
            this.g.add(InetSocketAddress.createUnresolved(socketHost, socketPort));
        } else {
            List<InetAddress> addresses = this.a.d().lookup(socketHost);
            int size = addresses.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.g.add(new InetSocketAddress(addresses.get(i2), socketPort));
            }
        }
        this.h = 0;
    }

    static String b(InetSocketAddress socketAddress) {
        InetAddress address = socketAddress.getAddress();
        if (address == null) {
            return socketAddress.getHostName();
        }
        return address.getHostAddress();
    }

    private boolean d() {
        return this.h < this.g.size();
    }

    private InetSocketAddress h() {
        if (d()) {
            List<InetSocketAddress> list = this.g;
            int i2 = this.h;
            this.h = i2 + 1;
            return list.get(i2);
        }
        throw new SocketException("No route to " + this.a.k() + "; exhausted inet socket addresses: " + this.g);
    }

    private boolean e() {
        return !this.i.isEmpty();
    }

    private z i() {
        return this.i.remove(0);
    }
}
