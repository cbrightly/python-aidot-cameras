package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.e;
import org.apache.http.conn.scheme.f;
import org.apache.http.conn.scheme.j;
import org.apache.http.conn.scheme.m;
import org.apache.http.impl.conn.tsccm.g;
import org.apache.http.l;
import org.apache.http.params.HttpParams;

public class ConnectionManagerFactory {
    private static final int DEFAULT_HTTPS_PORT = 443;
    private static final int DEFAULT_HTTP_PORT = 80;

    ConnectionManagerFactory() {
    }

    public static g createThreadSafeClientConnManager(ClientConfiguration config, HttpParams httpClientParams) {
        ConnManagerParams.setMaxConnectionsPerRoute(httpClientParams, new ConnPerRouteBean(config.getMaxConnections()));
        ConnManagerParams.setMaxTotalConnections(httpClientParams, config.getMaxConnections());
        org.apache.http.conn.ssl.g sslSocketFactory = org.apache.http.conn.ssl.g.l();
        sslSocketFactory.o(org.apache.http.conn.ssl.g.c);
        j registry = new j();
        registry.d(new f(l.DEFAULT_SCHEME_NAME, (m) e.h(), 80));
        registry.d(new f("https", (m) sslSocketFactory, (int) DEFAULT_HTTPS_PORT));
        g connectionManager = new g(httpClientParams, registry);
        if (config.useReaper()) {
            IdleConnectionReaper.registerConnectionManager(connectionManager);
        }
        return connectionManager;
    }
}
