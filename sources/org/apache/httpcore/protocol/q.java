package org.apache.httpcore.protocol;

import org.apache.httpcore.p;
import org.apache.httpcore.r;
import org.apache.httpcore.util.a;

/* compiled from: ResponseServer */
public class q implements r {
    private final String a;

    public q(String originServer) {
        this.a = originServer;
    }

    public void a(p response, d context) {
        String str;
        a.g(response, "HTTP response");
        if (!response.containsHeader("Server") && (str = this.a) != null) {
            response.addHeader("Server", str);
        }
    }
}
