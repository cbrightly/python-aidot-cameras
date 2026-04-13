package org.apache.http.impl.conn;

import java.net.InetAddress;
import org.apache.http.HttpException;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.routing.d;
import org.apache.http.conn.scheme.j;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

@Deprecated
/* compiled from: DefaultHttpRoutePlanner */
public class m implements d {
    protected final j a;

    public m(j schreg) {
        a.i(schreg, "Scheme registry");
        this.a = schreg;
    }

    public b a(l target, o request, f context) {
        a.i(request, "HTTP request");
        b route = ConnRouteParams.getForcedRoute(request.getParams());
        if (route != null) {
            return route;
        }
        org.apache.http.util.b.c(target, "Target host");
        InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
        l proxy = ConnRouteParams.getDefaultProxy(request.getParams());
        try {
            boolean secure = this.a.b(target.getSchemeName()).e();
            if (proxy == null) {
                return new b(target, local, secure);
            }
            return new b(target, local, proxy, secure);
        } catch (IllegalStateException ex) {
            throw new HttpException(ex.getMessage());
        }
    }
}
