package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;
import org.apache.http.conn.routing.e;
import org.apache.http.l;
import org.apache.http.util.a;
import org.apache.http.util.j;

/* compiled from: URIUtils */
public class d {
    public static URI e(URI uri, l target, boolean dropFragment) {
        a.i(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        c uribuilder = new c(uri);
        if (target != null) {
            uribuilder.t(target.getSchemeName());
            uribuilder.q(target.getHostName());
            uribuilder.s(target.getPort());
        } else {
            uribuilder.t((String) null);
            uribuilder.q((String) null);
            uribuilder.s(-1);
        }
        if (dropFragment) {
            uribuilder.p((String) null);
        }
        if (j.c(uribuilder.j())) {
            uribuilder.r("/");
        }
        return uribuilder.b();
    }

    public static URI d(URI uri) {
        a.i(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        c uribuilder = new c(uri);
        if (uribuilder.l() != null) {
            uribuilder.u((String) null);
        }
        if (j.c(uribuilder.j())) {
            uribuilder.r("/");
        }
        if (uribuilder.i() != null) {
            uribuilder.q(uribuilder.i().toLowerCase(Locale.ROOT));
        }
        uribuilder.p((String) null);
        return uribuilder.b();
    }

    public static URI f(URI uri, e route) {
        if (uri == null) {
            return null;
        }
        if (route.c() == null || route.b()) {
            if (uri.isAbsolute()) {
                return e(uri, (l) null, true);
            }
            return d(uri);
        } else if (!uri.isAbsolute()) {
            return e(uri, route.e(), true);
        } else {
            return d(uri);
        }
    }

    public static URI c(URI baseURI, URI reference) {
        URI resolved;
        a.i(baseURI, "Base URI");
        a.i(reference, "Reference URI");
        String s = reference.toASCIIString();
        if (s.startsWith("?")) {
            String baseUri = baseURI.toASCIIString();
            int i = baseUri.indexOf(63);
            String baseUri2 = i > -1 ? baseUri.substring(0, i) : baseUri;
            return URI.create(baseUri2 + s);
        }
        if (s.isEmpty()) {
            String resolvedString = baseURI.resolve(URI.create("#")).toASCIIString();
            resolved = URI.create(resolvedString.substring(0, resolvedString.indexOf(35)));
        } else {
            resolved = baseURI.resolve(reference);
        }
        try {
            return b(resolved);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    static URI b(URI uri) {
        if (uri.isOpaque() || uri.getAuthority() == null) {
            return uri;
        }
        a.a(uri.isAbsolute(), "Base URI must be absolute");
        c builder = new c(uri);
        String path = builder.j();
        if (path != null && !path.equals("/")) {
            String[] inputSegments = path.split("/");
            Stack<String> outputSegments = new Stack<>();
            for (String inputSegment : inputSegments) {
                if (!inputSegment.isEmpty() && !".".equals(inputSegment)) {
                    if (!"..".equals(inputSegment)) {
                        outputSegments.push(inputSegment);
                    } else if (!outputSegments.isEmpty()) {
                        outputSegments.pop();
                    }
                }
            }
            StringBuilder outputBuffer = new StringBuilder();
            Iterator i$ = outputSegments.iterator();
            while (i$.hasNext()) {
                outputBuffer.append('/');
                outputBuffer.append((String) i$.next());
            }
            if (path.lastIndexOf(47) == path.length() - 1) {
                outputBuffer.append('/');
            }
            builder.r(outputBuffer.toString());
        }
        if (builder.k() != null) {
            builder.t(builder.k().toLowerCase(Locale.ROOT));
        }
        if (builder.i() != null) {
            builder.q(builder.i().toLowerCase(Locale.ROOT));
        }
        return builder.b();
    }

    public static l a(URI uri) {
        int colon;
        if (uri == null || !uri.isAbsolute()) {
            return null;
        }
        int port = uri.getPort();
        String host = uri.getHost();
        if (host == null && (host = uri.getAuthority()) != null) {
            int at = host.indexOf(64);
            if (at >= 0) {
                if (host.length() > at + 1) {
                    host = host.substring(at + 1);
                } else {
                    host = null;
                }
            }
            if (host != null && (colon = host.indexOf(58)) >= 0) {
                int pos = colon + 1;
                int len = 0;
                int i = pos;
                while (i < host.length() && Character.isDigit(host.charAt(i))) {
                    len++;
                    i++;
                }
                if (len > 0) {
                    try {
                        port = Integer.parseInt(host.substring(pos, pos + len));
                    } catch (NumberFormatException e) {
                    }
                }
                host = host.substring(0, colon);
            }
        }
        String scheme = uri.getScheme();
        if (j.b(host)) {
            return null;
        }
        try {
            return new l(host, port, scheme);
        } catch (IllegalArgumentException e2) {
            return null;
        }
    }
}
