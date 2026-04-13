package org.apache.http.client.protocol;

import java.util.Collection;
import org.apache.http.d;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

/* compiled from: RequestDefaultHeaders */
public class g implements p {
    private final Collection<? extends d> c;

    public g(Collection<? extends d> defaultHeaders) {
        this.c = defaultHeaders;
    }

    public g() {
        this((Collection<? extends d>) null);
    }

    public void b(o request, f context) {
        a.i(request, "HTTP request");
        if (!request.r().getMethod().equalsIgnoreCase("CONNECT")) {
            Collection<? extends d> collection = (Collection) request.getParams().getParameter("http.default-headers");
            if (collection == null) {
                collection = this.c;
            }
            if (collection != null) {
                for (d defHeader : collection) {
                    request.I(defHeader);
                }
            }
        }
    }
}
