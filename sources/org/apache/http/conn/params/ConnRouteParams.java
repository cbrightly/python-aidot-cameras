package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.conn.routing.b;
import org.apache.http.l;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
public class ConnRouteParams {
    public static final l NO_HOST;
    public static final b NO_ROUTE;

    static {
        l lVar = new l("127.0.0.255", 0, "no-host");
        NO_HOST = lVar;
        NO_ROUTE = new b(lVar);
    }

    private ConnRouteParams() {
    }

    public static l getDefaultProxy(HttpParams params) {
        a.i(params, "Parameters");
        l proxy = (l) params.getParameter("http.route.default-proxy");
        if (proxy == null || !NO_HOST.equals(proxy)) {
            return proxy;
        }
        return null;
    }

    public static void setDefaultProxy(HttpParams params, l proxy) {
        a.i(params, "Parameters");
        params.setParameter("http.route.default-proxy", proxy);
    }

    public static b getForcedRoute(HttpParams params) {
        a.i(params, "Parameters");
        b route = (b) params.getParameter("http.route.forced-route");
        if (route == null || !NO_ROUTE.equals(route)) {
            return route;
        }
        return null;
    }

    public static void setForcedRoute(HttpParams params, b route) {
        a.i(params, "Parameters");
        params.setParameter("http.route.forced-route", route);
    }

    public static InetAddress getLocalAddress(HttpParams params) {
        a.i(params, "Parameters");
        return (InetAddress) params.getParameter("http.route.local-address");
    }

    public static void setLocalAddress(HttpParams params, InetAddress local) {
        a.i(params, "Parameters");
        params.setParameter("http.route.local-address", local);
    }
}
