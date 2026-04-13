package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.conn.routing.b;
import org.apache.http.l;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ConnRouteParamBean extends HttpAbstractParamBean {
    public ConnRouteParamBean(HttpParams params) {
        super(params);
    }

    public void setDefaultProxy(l defaultProxy) {
        this.params.setParameter("http.route.default-proxy", defaultProxy);
    }

    public void setLocalAddress(InetAddress address) {
        this.params.setParameter("http.route.local-address", address);
    }

    public void setForcedRoute(b route) {
        this.params.setParameter("http.route.forced-route", route);
    }
}
