package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.d;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.util.a;

@Deprecated
/* compiled from: DefaultProxyAuthenticationHandler */
public class p extends a {
    public boolean c(q response, f context) {
        a.i(response, "HTTP response");
        return response.j().getStatusCode() == 407;
    }

    public Map<String, d> a(q response, f context) {
        a.i(response, "HTTP response");
        return f(response.c("Proxy-Authenticate"));
    }

    /* access modifiers changed from: protected */
    public List<String> e(q response, f context) {
        List<String> authpref = (List) response.getParams().getParameter("http.auth.proxy-scheme-pref");
        if (authpref != null) {
            return authpref;
        }
        return super.e(response, context);
    }
}
