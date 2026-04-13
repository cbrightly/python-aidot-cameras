package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.google.android.libraries.places.api.model.PlaceTypes;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import okhttp3.e;
import okhttp3.f0;
import okhttp3.r;
import org.jetbrains.annotations.NotNull;

/* compiled from: RouteSelector.kt */
public final class j {
    public static final a a = new a((DefaultConstructorMarker) null);
    private List<? extends Proxy> b = q.g();
    private int c;
    private List<? extends InetSocketAddress> d = q.g();
    private final List<f0> e = new ArrayList();
    /* access modifiers changed from: private */
    public final okhttp3.a f;
    private final i g;
    private final e h;
    private final r i;

    public j(@NotNull okhttp3.a address, @NotNull i routeDatabase, @NotNull e call, @NotNull r eventListener) {
        k.f(address, PlaceTypes.ADDRESS);
        k.f(routeDatabase, "routeDatabase");
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(eventListener, "eventListener");
        this.f = address;
        this.g = routeDatabase;
        this.h = call;
        this.i = eventListener;
        g(address.l(), address.g());
    }

    public final boolean b() {
        return c() || (this.e.isEmpty() ^ true);
    }

    @NotNull
    public final b d() {
        if (b()) {
            List routes = new ArrayList();
            while (c()) {
                Proxy proxy = e();
                for (InetSocketAddress inetSocketAddress : this.d) {
                    f0 route = new f0(this.f, proxy, inetSocketAddress);
                    if (this.g.c(route)) {
                        this.e.add(route);
                    } else {
                        routes.add(route);
                    }
                }
                if (!routes.isEmpty()) {
                    break;
                }
            }
            if (routes.isEmpty()) {
                v.x(routes, this.e);
                this.e.clear();
            }
            return new b(routes);
        }
        throw new NoSuchElementException();
    }

    private final void g(okhttp3.v url, Proxy proxy) {
        c $fun$selectProxies$1 = new c(this, proxy, url);
        this.i.p(this.h, url);
        List<? extends Proxy> invoke = $fun$selectProxies$1.invoke();
        this.b = invoke;
        this.c = 0;
        this.i.o(this.h, url, invoke);
    }

    /* compiled from: RouteSelector.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<List<? extends Proxy>> {
        final /* synthetic */ Proxy $proxy;
        final /* synthetic */ okhttp3.v $url;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, Proxy proxy, okhttp3.v vVar) {
            super(0);
            this.this$0 = jVar;
            this.$proxy = proxy;
            this.$url = vVar;
        }

        @NotNull
        public final List<Proxy> invoke() {
            Proxy proxy = this.$proxy;
            if (proxy != null) {
                return p.b(proxy);
            }
            URI uri = this.$url.u();
            if (uri.getHost() == null) {
                return okhttp3.internal.b.t(Proxy.NO_PROXY);
            }
            List proxiesOrNull = this.this$0.f.i().select(uri);
            if (!(proxiesOrNull == null || proxiesOrNull.isEmpty())) {
                return okhttp3.internal.b.R(proxiesOrNull);
            }
            return okhttp3.internal.b.t(Proxy.NO_PROXY);
        }
    }

    private final boolean c() {
        return this.c < this.b.size();
    }

    private final Proxy e() {
        if (c()) {
            List<? extends Proxy> list = this.b;
            int i2 = this.c;
            this.c = i2 + 1;
            Proxy result = (Proxy) list.get(i2);
            f(result);
            return result;
        }
        throw new SocketException("No route to " + this.f.l().j() + "; exhausted proxy configurations: " + this.b);
    }

    private final void f(Proxy proxy) {
        int socketPort;
        String socketHost;
        List mutableInetSocketAddresses = new ArrayList();
        this.d = mutableInetSocketAddresses;
        if (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) {
            socketHost = this.f.l().j();
            socketPort = this.f.l().p();
        } else {
            SocketAddress proxyAddress = proxy.address();
            if (proxyAddress instanceof InetSocketAddress) {
                socketHost = a.a((InetSocketAddress) proxyAddress);
                socketPort = ((InetSocketAddress) proxyAddress).getPort();
            } else {
                throw new IllegalArgumentException(("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass()).toString());
            }
        }
        if (1 > socketPort || 65535 < socketPort) {
            throw new SocketException("No route to " + socketHost + ':' + socketPort + "; port is out of range");
        } else if (proxy.type() == Proxy.Type.SOCKS) {
            mutableInetSocketAddresses.add(InetSocketAddress.createUnresolved(socketHost, socketPort));
        } else {
            this.i.n(this.h, socketHost);
            List<InetAddress> addresses = this.f.c().lookup(socketHost);
            if (!addresses.isEmpty()) {
                this.i.m(this.h, socketHost, addresses);
                for (InetAddress inetAddress : addresses) {
                    mutableInetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
                }
                return;
            }
            throw new UnknownHostException(this.f.c() + " returned no addresses for " + socketHost);
        }
    }

    /* compiled from: RouteSelector.kt */
    public static final class b {
        private int a;
        @NotNull
        private final List<f0> b;

        public b(@NotNull List<f0> routes) {
            k.f(routes, "routes");
            this.b = routes;
        }

        @NotNull
        public final List<f0> a() {
            return this.b;
        }

        public final boolean b() {
            return this.a < this.b.size();
        }

        @NotNull
        public final f0 c() {
            if (b()) {
                List<f0> list = this.b;
                int i = this.a;
                this.a = i + 1;
                return list.get(i);
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: RouteSelector.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final String a(@NotNull InetSocketAddress $this$socketHost) {
            k.f($this$socketHost, "$this$socketHost");
            InetAddress address = $this$socketHost.getAddress();
            if (address != null) {
                String hostAddress = address.getHostAddress();
                k.b(hostAddress, "address.hostAddress");
                return hostAddress;
            }
            String hostName = $this$socketHost.getHostName();
            k.b(hostName, "hostName");
            return hostName;
        }
    }
}
