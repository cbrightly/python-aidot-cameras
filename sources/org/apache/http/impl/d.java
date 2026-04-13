package org.apache.http.impl;

import org.apache.http.ParseException;
import org.apache.http.a;
import org.apache.http.g;
import org.apache.http.message.p;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.t;
import org.apache.http.v;
import org.apache.http.z;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: DefaultConnectionReuseStrategy */
public class d implements a {
    public static final d a = new d();

    public boolean a(q response, f context) {
        org.apache.http.util.a.i(response, "HTTP response");
        org.apache.http.util.a.i(context, "HTTP context");
        o request = (o) context.getAttribute("http.request");
        if (request != null) {
            try {
                z ti = new p(request.o("Connection"));
                while (ti.hasNext()) {
                    if ("Close".equalsIgnoreCase(ti.nextToken())) {
                        return false;
                    }
                }
            } catch (ParseException e) {
                return false;
            }
        }
        v ver = response.j().getProtocolVersion();
        org.apache.http.d teh = response.u(Constants.TRANSFERENCODING);
        if (teh != null) {
            if (!"chunked".equalsIgnoreCase(teh.getValue())) {
                return false;
            }
        } else if (b(request, response)) {
            org.apache.http.d[] clhs = response.c("Content-Length");
            if (clhs.length != 1) {
                return false;
            }
            try {
                if (Integer.parseInt(clhs[0].getValue()) < 0) {
                    return false;
                }
            } catch (NumberFormatException e2) {
                return false;
            }
        }
        g headerIterator = response.o("Connection");
        if (!headerIterator.hasNext()) {
            headerIterator = response.o("Proxy-Connection");
        }
        if (headerIterator.hasNext()) {
            try {
                z ti2 = new p(headerIterator);
                boolean keepalive = false;
                while (ti2.hasNext()) {
                    String token = ti2.nextToken();
                    if ("Close".equalsIgnoreCase(token)) {
                        return false;
                    }
                    if ("Keep-Alive".equalsIgnoreCase(token)) {
                        keepalive = true;
                    }
                }
                if (keepalive) {
                    return true;
                }
            } catch (ParseException e3) {
                return false;
            }
        }
        return !ver.lessEquals(t.HTTP_1_0);
    }

    private boolean b(o request, q response) {
        int status;
        if ((request != null && request.r().getMethod().equalsIgnoreCase(Constants.HEAD)) || (status = response.j().getStatusCode()) < 200 || status == 204 || status == 304 || status == 205) {
            return false;
        }
        return true;
    }
}
