package org.apache.http.impl.client;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.j;
import org.apache.http.client.methods.i;
import org.apache.http.client.methods.p;
import org.apache.http.client.utils.c;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.util.b;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: DefaultRedirectStrategy */
public class r implements j {
    public static final r a = new r();
    private static final String[] b = {Constants.GET, Constants.HEAD};
    private final a c = h.n(getClass());

    public boolean b(o request, q response, f context) {
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(response, "HTTP response");
        int statusCode = response.j().getStatusCode();
        String method = request.r().getMethod();
        d locationHeader = response.u(FirebaseAnalytics.Param.LOCATION);
        switch (statusCode) {
            case 301:
            case 307:
                return e(method);
            case 302:
                if (!e(method) || locationHeader == null) {
                    return false;
                }
                return true;
            case 303:
                return true;
            default:
                return false;
        }
    }

    public URI d(o request, q response, f context) {
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(response, "HTTP response");
        org.apache.http.util.a.i(context, "HTTP context");
        org.apache.http.client.protocol.a clientContext = org.apache.http.client.protocol.a.g(context);
        d locationHeader = response.u(FirebaseAnalytics.Param.LOCATION);
        if (locationHeader != null) {
            String location = locationHeader.getValue();
            if (this.c.isDebugEnabled()) {
                a aVar = this.c;
                aVar.debug("Redirect requested to location '" + location + "'");
            }
            org.apache.http.client.config.a config = clientContext.s();
            URI uri = c(location);
            try {
                if (!uri.isAbsolute()) {
                    if (config.t()) {
                        l target = clientContext.e();
                        b.c(target, "Target host");
                        uri = org.apache.http.client.utils.d.c(org.apache.http.client.utils.d.e(new URI(request.r().getUri()), target, false), uri);
                    } else {
                        throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
                    }
                }
                e0 redirectLocations = (e0) clientContext.getAttribute("http.protocol.redirect-locations");
                if (redirectLocations == null) {
                    redirectLocations = new e0();
                    context.setAttribute("http.protocol.redirect-locations", redirectLocations);
                }
                if (config.o() || !redirectLocations.b(uri)) {
                    redirectLocations.a(uri);
                    return uri;
                }
                throw new CircularRedirectException("Circular redirect to '" + uri + "'");
            } catch (URISyntaxException ex) {
                throw new ProtocolException(ex.getMessage(), ex);
            }
        } else {
            throw new ProtocolException("Received redirect response " + response.j() + " but no location header");
        }
    }

    /* access modifiers changed from: protected */
    public URI c(String location) {
        try {
            c b2 = new c(new URI(location).normalize());
            String host = b2.i();
            if (host != null) {
                b2.q(host.toLowerCase(Locale.ROOT));
            }
            if (org.apache.http.util.j.c(b2.j())) {
                b2.r("/");
            }
            return b2.b();
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid redirect URI: " + location, ex);
        }
    }

    /* access modifiers changed from: protected */
    public boolean e(String method) {
        for (String m : b) {
            if (m.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }

    public p a(o request, q response, f context) {
        URI uri = d(request, response, context);
        String method = request.r().getMethod();
        if (method.equalsIgnoreCase(Constants.HEAD)) {
            return new i(uri);
        }
        if (method.equalsIgnoreCase(Constants.GET)) {
            return new org.apache.http.client.methods.h(uri);
        }
        if (response.j().getStatusCode() == 307) {
            return org.apache.http.client.methods.q.b(request).d(uri).a();
        }
        return new org.apache.http.client.methods.h(uri);
    }
}
