package org.apache.httpcore.protocol;

import org.apache.httpcore.e;
import org.apache.httpcore.j;
import org.apache.httpcore.m;
import org.apache.httpcore.p;
import org.apache.httpcore.r;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.v;

/* compiled from: ResponseConnControl */
public class n implements r {
    public void a(p response, d context) {
        a.g(response, "HTTP response");
        e corecontext = e.a(context);
        int status = response.j().getStatusCode();
        if (status == 400 || status == 408 || status == 411 || status == 413 || status == 414 || status == 503 || status == 501) {
            response.setHeader("Connection", "Close");
            return;
        }
        e explicit = response.u("Connection");
        if (explicit == null || !"Close".equalsIgnoreCase(explicit.getValue())) {
            j entity = response.a();
            if (entity != null) {
                v ver = response.j().getProtocolVersion();
                if (entity.getContentLength() < 0 && (!entity.isChunked() || ver.lessEquals(t.HTTP_1_0))) {
                    response.setHeader("Connection", "Close");
                    return;
                }
            }
            m request = corecontext.c();
            if (request != null) {
                e header = request.u("Connection");
                if (header != null) {
                    response.setHeader("Connection", header.getValue());
                } else if (request.getProtocolVersion().lessEquals(t.HTTP_1_0)) {
                    response.setHeader("Connection", "Close");
                }
            }
        }
    }
}
