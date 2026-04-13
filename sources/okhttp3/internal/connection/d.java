package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.google.android.libraries.places.api.model.PlaceTypes;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.a;
import okhttp3.f0;
import okhttp3.internal.b;
import okhttp3.internal.connection.j;
import okhttp3.internal.http.g;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.r;
import okhttp3.v;
import okhttp3.z;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: ExchangeFinder.kt */
public final class d {
    private j.b a;
    private j b;
    private int c;
    private int d;
    private int e;
    private f0 f;
    private final h g;
    @NotNull
    private final a h;
    private final e i;
    private final r j;

    public d(@NotNull h connectionPool, @NotNull a address, @NotNull e call, @NotNull r eventListener) {
        k.f(connectionPool, "connectionPool");
        k.f(address, PlaceTypes.ADDRESS);
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(eventListener, "eventListener");
        this.g = connectionPool;
        this.h = address;
        this.i = call;
        this.j = eventListener;
    }

    @NotNull
    public final a d() {
        return this.h;
    }

    @NotNull
    public final okhttp3.internal.http.d a(@NotNull z client, @NotNull g chain) {
        k.f(client, "client");
        k.f(chain, "chain");
        try {
            return c(chain.f(), chain.i(), chain.k(), client.B(), client.I(), !k.a(chain.j().h(), Constants.GET)).x(client, chain);
        } catch (RouteException e2) {
            h(e2.getLastConnectException());
            throw e2;
        } catch (IOException e3) {
            h(e3);
            throw new RouteException(e3);
        }
    }

    private final f c(int connectTimeout, int readTimeout, int writeTimeout, int pingIntervalMillis, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) {
        while (true) {
            f candidate = b(connectTimeout, readTimeout, writeTimeout, pingIntervalMillis, connectionRetryEnabled);
            if (candidate.v(doExtensiveHealthChecks)) {
                return candidate;
            }
            candidate.A();
            if (this.f == null) {
                j.b bVar = this.a;
                boolean routesSelectionLeft = true;
                if (!(bVar != null ? bVar.b() : true)) {
                    j jVar = this.b;
                    if (jVar != null) {
                        routesSelectionLeft = jVar.b();
                    }
                    if (!routesSelectionLeft) {
                        throw new IOException("exhausted all routes");
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private final f b(int connectTimeout, int readTimeout, int writeTimeout, int pingIntervalMillis, boolean connectionRetryEnabled) {
        f0 route;
        List routes;
        if (!this.i.isCanceled()) {
            f callConnection = this.i.l();
            boolean z = true;
            if (callConnection != null) {
                Socket socket = null;
                synchronized (callConnection) {
                    if (callConnection.r() || !g(callConnection.a().a().l())) {
                        socket = this.i.v();
                    }
                    x xVar = x.a;
                }
                if (this.i.l() != null) {
                    if (socket != null) {
                        z = false;
                    }
                    if (z) {
                        return callConnection;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (socket != null) {
                    b.k(socket);
                }
                this.j.l(this.i, callConnection);
            }
            this.c = 0;
            this.d = 0;
            this.e = 0;
            if (this.g.a(this.h, this.i, (List<f0>) null, false)) {
                f result = this.i.l();
                if (result == null) {
                    k.n();
                }
                this.j.k(this.i, result);
                return result;
            }
            f0 f0Var = this.f;
            if (f0Var != null) {
                routes = null;
                if (f0Var == null) {
                    k.n();
                }
                route = f0Var;
                this.f = null;
            } else {
                j.b bVar = this.a;
                if (bVar != null) {
                    if (bVar == null) {
                        k.n();
                    }
                    if (bVar.b()) {
                        routes = null;
                        j.b bVar2 = this.a;
                        if (bVar2 == null) {
                            k.n();
                        }
                        route = bVar2.c();
                    }
                }
                j localRouteSelector = this.b;
                if (localRouteSelector == null) {
                    localRouteSelector = new j(this.h, this.i.k().t(), this.i, this.j);
                    this.b = localRouteSelector;
                }
                j.b localRouteSelection = localRouteSelector.d();
                this.a = localRouteSelection;
                List routes2 = localRouteSelection.a();
                if (this.i.isCanceled()) {
                    throw new IOException("Canceled");
                } else if (this.g.a(this.h, this.i, routes2, false)) {
                    f result2 = this.i.l();
                    if (result2 == null) {
                        k.n();
                    }
                    this.j.k(this.i, result2);
                    return result2;
                } else {
                    List list = routes2;
                    route = localRouteSelection.c();
                    routes = list;
                }
            }
            f newConnection = new f(this.g, route);
            this.i.y(newConnection);
            try {
                newConnection.h(connectTimeout, readTimeout, writeTimeout, pingIntervalMillis, connectionRetryEnabled, this.i, this.j);
                this.i.y((f) null);
                this.i.k().t().a(newConnection.a());
                if (this.g.a(this.h, this.i, routes, true)) {
                    f result3 = this.i.l();
                    if (result3 == null) {
                        k.n();
                    }
                    this.f = route;
                    b.k(newConnection.E());
                    this.j.k(this.i, result3);
                    return result3;
                }
                synchronized (newConnection) {
                    this.g.e(newConnection);
                    this.i.c(newConnection);
                    x xVar2 = x.a;
                }
                this.j.k(this.i, newConnection);
                return newConnection;
            } catch (Throwable th) {
                this.i.y((f) null);
                throw th;
            }
        } else {
            throw new IOException("Canceled");
        }
    }

    public final void h(@NotNull IOException e2) {
        k.f(e2, "e");
        this.f = null;
        if ((e2 instanceof StreamResetException) && ((StreamResetException) e2).errorCode == okhttp3.internal.http2.a.REFUSED_STREAM) {
            this.c++;
        } else if (e2 instanceof ConnectionShutdownException) {
            this.d++;
        } else {
            this.e++;
        }
    }

    public final boolean e() {
        j localRouteSelector;
        if (this.c == 0 && this.d == 0 && this.e == 0) {
            return false;
        }
        if (this.f != null) {
            return true;
        }
        f0 retryRoute = f();
        if (retryRoute != null) {
            this.f = retryRoute;
            return true;
        }
        j.b bVar = this.a;
        if ((bVar == null || !bVar.b()) && (localRouteSelector = this.b) != null) {
            return localRouteSelector.b();
        }
        return true;
    }

    private final f0 f() {
        f connection;
        if (this.c > 1 || this.d > 1 || this.e > 0 || (connection = this.i.l()) == null) {
            return null;
        }
        synchronized (connection) {
            if (connection.s() != 0) {
                return null;
            }
            if (!b.g(connection.a().a().l(), this.h.l())) {
                return null;
            }
            f0 a2 = connection.a();
            return a2;
        }
    }

    public final boolean g(@NotNull v url) {
        k.f(url, "url");
        v routeUrl = this.h.l();
        return url.p() == routeUrl.p() && k.a(url.j(), routeUrl.j());
    }
}
