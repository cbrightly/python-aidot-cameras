package org.apache.httpcore.impl;

import org.apache.httpcore.ParseException;
import org.apache.httpcore.a;
import org.apache.httpcore.g;
import org.apache.httpcore.m;
import org.apache.httpcore.message.o;
import org.apache.httpcore.p;
import org.apache.httpcore.protocol.d;
import org.apache.httpcore.t;
import org.apache.httpcore.v;
import org.apache.httpcore.z;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: DefaultConnectionReuseStrategy */
public class e implements a {
    public static final e a = new e();

    public boolean a(p response, d context) {
        org.apache.httpcore.util.a.g(response, "HTTP response");
        org.apache.httpcore.util.a.g(context, "HTTP context");
        if (response.j().getStatusCode() == 204) {
            org.apache.httpcore.e clh = response.u("Content-Length");
            if (clh != null) {
                try {
                    if (Integer.parseInt(clh.getValue()) > 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                }
            }
            if (response.u(Constants.TRANSFERENCODING) != null) {
                return false;
            }
        }
        m request = (m) context.getAttribute("http.request");
        if (request != null) {
            try {
                z ti = new o(request.o("Connection"));
                while (ti.hasNext()) {
                    if ("Close".equalsIgnoreCase(ti.nextToken())) {
                        return false;
                    }
                }
            } catch (ParseException e2) {
                return false;
            }
        }
        v ver = response.j().getProtocolVersion();
        org.apache.httpcore.e teh = response.u(Constants.TRANSFERENCODING);
        if (teh != null) {
            if (!"chunked".equalsIgnoreCase(teh.getValue())) {
                return false;
            }
        } else if (b(request, response)) {
            org.apache.httpcore.e[] clhs = response.c("Content-Length");
            if (clhs.length != 1) {
                return false;
            }
            try {
                if (Long.parseLong(clhs[0].getValue()) < 0) {
                    return false;
                }
            } catch (NumberFormatException e3) {
                return false;
            }
        }
        g headerIterator = response.o("Connection");
        if (!headerIterator.hasNext()) {
            headerIterator = response.o("Proxy-Connection");
        }
        if (headerIterator.hasNext()) {
            try {
                z ti2 = new o(headerIterator);
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
            } catch (ParseException e4) {
                return false;
            }
        }
        return !ver.lessEquals(t.HTTP_1_0);
    }

    private boolean b(m request, p response) {
        int status;
        if ((request != null && request.r().getMethod().equalsIgnoreCase(Constants.HEAD)) || (status = response.j().getStatusCode()) < 200 || status == 204 || status == 304 || status == 205) {
            return false;
        }
        return true;
    }
}
