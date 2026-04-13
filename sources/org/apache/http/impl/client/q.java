package org.apache.http.impl.client;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.i;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;
import org.apache.http.util.b;
import org.glassfish.grizzly.http.server.Constants;

@Deprecated
/* compiled from: DefaultRedirectHandler */
public class q implements i {
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final a log = h.n(getClass());

    public boolean isRedirectRequested(org.apache.http.q response, f context) {
        org.apache.http.util.a.i(response, "HTTP response");
        switch (response.j().getStatusCode()) {
            case 301:
            case 302:
            case 307:
                String method = ((o) context.getAttribute("http.request")).r().getMethod();
                if (method.equalsIgnoreCase(Constants.GET) || method.equalsIgnoreCase(Constants.HEAD)) {
                    return true;
                }
                return false;
            case 303:
                return true;
            default:
                return false;
        }
    }

    public URI getLocationURI(org.apache.http.q response, f context) {
        URI redirectURI;
        org.apache.http.util.a.i(response, "HTTP response");
        d locationHeader = response.u(FirebaseAnalytics.Param.LOCATION);
        if (locationHeader != null) {
            String location = locationHeader.getValue();
            if (this.log.isDebugEnabled()) {
                a aVar = this.log;
                aVar.debug("Redirect requested to location '" + location + "'");
            }
            try {
                URI uri = new URI(location);
                HttpParams params = response.getParams();
                if (!uri.isAbsolute()) {
                    if (!params.isParameterTrue("http.protocol.reject-relative-redirect")) {
                        l target = (l) context.getAttribute("http.target_host");
                        b.c(target, "Target host");
                        o request = (o) context.getAttribute("http.request");
                        try {
                            uri = org.apache.http.client.utils.d.c(org.apache.http.client.utils.d.e(new URI(request.r().getUri()), target, true), uri);
                            o oVar = request;
                        } catch (URISyntaxException ex) {
                            throw new ProtocolException(ex.getMessage(), ex);
                        }
                    } else {
                        throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
                    }
                }
                if (params.isParameterFalse("http.protocol.allow-circular-redirects")) {
                    e0 redirectLocations = (e0) context.getAttribute(REDIRECT_LOCATIONS);
                    if (redirectLocations == null) {
                        redirectLocations = new e0();
                        context.setAttribute(REDIRECT_LOCATIONS, redirectLocations);
                    }
                    if (uri.getFragment() != null) {
                        try {
                            redirectURI = org.apache.http.client.utils.d.e(uri, new l(uri.getHost(), uri.getPort(), uri.getScheme()), true);
                        } catch (URISyntaxException ex2) {
                            throw new ProtocolException(ex2.getMessage(), ex2);
                        }
                    } else {
                        redirectURI = uri;
                    }
                    if (!redirectLocations.b(redirectURI)) {
                        redirectLocations.a(redirectURI);
                    } else {
                        throw new CircularRedirectException("Circular redirect to '" + redirectURI + "'");
                    }
                }
                return uri;
            } catch (URISyntaxException ex3) {
                throw new ProtocolException("Invalid redirect URI: " + location, ex3);
            }
        } else {
            throw new ProtocolException("Received redirect response " + response.j() + " but no location header");
        }
    }
}
