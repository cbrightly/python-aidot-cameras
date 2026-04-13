package org.apache.http.impl.conn;

import java.net.InetAddress;
import org.apache.http.HttpException;
import org.apache.http.ProtocolException;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.routing.d;
import org.apache.http.conn.t;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

/* compiled from: DefaultRoutePlanner */
public class p implements d {
    private final t a;

    public p(t schemePortResolver) {
        this.a = schemePortResolver != null ? schemePortResolver : q.a;
    }

    public b a(l host, o request, f context) {
        l target;
        a.i(request, "Request");
        if (host != null) {
            org.apache.http.client.config.a config = org.apache.http.client.protocol.a.g(context).s();
            InetAddress local = config.h();
            l proxy = config.j();
            if (proxy == null) {
                proxy = b(host, request, context);
            }
            if (host.getPort() <= 0) {
                try {
                    target = new l(host.getHostName(), this.a.a(host), host.getSchemeName());
                } catch (UnsupportedSchemeException ex) {
                    throw new HttpException(ex.getMessage());
                }
            } else {
                target = host;
            }
            boolean secure = target.getSchemeName().equalsIgnoreCase("https");
            if (proxy == null) {
                return new b(target, local, secure);
            }
            return new b(target, local, proxy, secure);
        }
        throw new ProtocolException("Target host is not specified");
    }

    /* access modifiers changed from: protected */
    public l b(l target, o request, f context) {
        return null;
    }
}
