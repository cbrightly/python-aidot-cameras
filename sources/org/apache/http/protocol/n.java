package org.apache.http.protocol;

import java.net.InetAddress;
import org.apache.http.ProtocolException;
import org.apache.http.i;
import org.apache.http.l;
import org.apache.http.m;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;

/* compiled from: RequestTargetHost */
public class n implements p {
    public void b(o request, f context) {
        a.i(request, "HTTP request");
        g corecontext = g.a(context);
        v ver = request.r().getProtocolVersion();
        if ((!request.r().getMethod().equalsIgnoreCase("CONNECT") || !ver.lessEquals(t.HTTP_1_0)) && !request.containsHeader("Host")) {
            l targethost = corecontext.e();
            if (targethost == null) {
                i conn = corecontext.c();
                if (conn instanceof m) {
                    InetAddress address = ((m) conn).w();
                    int port = ((m) conn).I0();
                    if (address != null) {
                        targethost = new l(address.getHostName(), port);
                    }
                }
                if (targethost == null) {
                    if (!ver.lessEquals(t.HTTP_1_0)) {
                        throw new ProtocolException("Target host missing");
                    }
                    return;
                }
            }
            request.addHeader("Host", targethost.toHostString());
        }
    }
}
