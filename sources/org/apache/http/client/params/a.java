package org.apache.http.client.params;

import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.client.config.a;
import org.apache.http.l;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: HttpClientParamConfig */
public final class a {
    public static org.apache.http.client.config.a a(HttpParams params) {
        return b(params, org.apache.http.client.config.a.c);
    }

    public static org.apache.http.client.config.a b(HttpParams params, org.apache.http.client.config.a defaultConfig) {
        a.C0091a builder = org.apache.http.client.config.a.b(defaultConfig).p(params.getIntParameter("http.socket.timeout", defaultConfig.l())).q(params.getBooleanParameter("http.connection.stalecheck", defaultConfig.u())).d(params.getIntParameter("http.connection.timeout", defaultConfig.d())).i(params.getBooleanParameter("http.protocol.expect-continue", defaultConfig.r())).b(params.getBooleanParameter("http.protocol.handle-authentication", defaultConfig.n())).c(params.getBooleanParameter("http.protocol.allow-circular-redirects", defaultConfig.o())).e((int) params.getLongParameter("http.conn-manager.timeout", (long) defaultConfig.e())).k(params.getIntParameter("http.protocol.max-redirects", defaultConfig.i())).n(params.getBooleanParameter("http.protocol.handle-redirects", defaultConfig.s())).o(!params.getBooleanParameter("http.protocol.reject-relative-redirect", !defaultConfig.t()));
        l proxy = (l) params.getParameter("http.route.default-proxy");
        if (proxy != null) {
            builder.l(proxy);
        }
        InetAddress localAddress = (InetAddress) params.getParameter("http.route.local-address");
        if (localAddress != null) {
            builder.j(localAddress);
        }
        Collection<String> targetAuthPrefs = (Collection) params.getParameter("http.auth.target-scheme-pref");
        if (targetAuthPrefs != null) {
            builder.r(targetAuthPrefs);
        }
        Collection<String> proxySuthPrefs = (Collection) params.getParameter("http.auth.proxy-scheme-pref");
        if (proxySuthPrefs != null) {
            builder.m(proxySuthPrefs);
        }
        String cookiePolicy = (String) params.getParameter("http.protocol.cookie-policy");
        if (cookiePolicy != null) {
            builder.g(cookiePolicy);
        }
        return builder.a();
    }
}
