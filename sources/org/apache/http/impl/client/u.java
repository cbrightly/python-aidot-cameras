package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.d;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.util.a;
import org.glassfish.tyrus.spi.UpgradeResponse;

@Deprecated
/* compiled from: DefaultTargetAuthenticationHandler */
public class u extends a {
    public boolean c(q response, f context) {
        a.i(response, "HTTP response");
        return response.j().getStatusCode() == 401;
    }

    public Map<String, d> a(q response, f context) {
        a.i(response, "HTTP response");
        return f(response.c(UpgradeResponse.WWW_AUTHENTICATE));
    }

    /* access modifiers changed from: protected */
    public List<String> e(q response, f context) {
        List<String> authpref = (List) response.getParams().getParameter("http.auth.target-scheme-pref");
        if (authpref != null) {
            return authpref;
        }
        return super.e(response, context);
    }
}
