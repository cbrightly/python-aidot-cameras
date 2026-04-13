package org.apache.http.conn.params;

import org.apache.http.conn.routing.b;
import org.apache.http.params.HttpParams;

@Deprecated
public final class ConnManagerParams {
    private static final a DEFAULT_CONN_PER_ROUTE = new a();
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

    @Deprecated
    public static long getTimeout(HttpParams params) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        return params.getLongParameter("http.conn-manager.timeout", 0);
    }

    @Deprecated
    public static void setTimeout(HttpParams params, long timeout) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        params.setLongParameter("http.conn-manager.timeout", timeout);
    }

    public static final class a implements a {
        a() {
        }

        public int getMaxForRoute(b route) {
            return 2;
        }
    }

    public static void setMaxConnectionsPerRoute(HttpParams params, a connPerRoute) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        params.setParameter("http.conn-manager.max-per-route", connPerRoute);
    }

    public static a getMaxConnectionsPerRoute(HttpParams params) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        a connPerRoute = (a) params.getParameter("http.conn-manager.max-per-route");
        if (connPerRoute == null) {
            return DEFAULT_CONN_PER_ROUTE;
        }
        return connPerRoute;
    }

    public static void setMaxTotalConnections(HttpParams params, int maxTotalConnections) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        params.setIntParameter("http.conn-manager.max-total", maxTotalConnections);
    }

    public static int getMaxTotalConnections(HttpParams params) {
        org.apache.http.util.a.i(params, "HTTP parameters");
        return params.getIntParameter("http.conn-manager.max-total", 20);
    }
}
