package org.apache.http.impl.client;

import org.apache.http.impl.d;
import org.apache.http.message.e;
import org.apache.http.message.p;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.z;

/* compiled from: DefaultClientConnectionReuseStrategy */
public class l extends d {
    public static final l b = new l();

    public boolean a(q response, f context) {
        o request = (o) context.getAttribute("http.request");
        if (request != null) {
            org.apache.http.d[] connHeaders = request.c("Connection");
            if (connHeaders.length != 0) {
                z ti = new p(new e(connHeaders, (String) null));
                while (ti.hasNext()) {
                    if ("Close".equalsIgnoreCase(ti.nextToken())) {
                        return false;
                    }
                }
            }
        }
        return super.a(response, context);
    }
}
