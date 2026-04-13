package org.apache.httpcore.protocol;

import org.apache.httpcore.r;
import org.apache.httpcore.util.a;

/* compiled from: ResponseDate */
public class p implements r {
    private static final f a = new f();

    public void a(org.apache.httpcore.p response, d context) {
        a.g(response, "HTTP response");
        if (response.j().getStatusCode() >= 200 && !response.containsHeader("Date")) {
            response.setHeader("Date", a.a());
        }
    }
}
