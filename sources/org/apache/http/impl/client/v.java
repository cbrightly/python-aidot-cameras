package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.auth.c;
import org.apache.http.auth.h;
import org.apache.http.auth.l;
import org.apache.http.client.n;
import org.apache.http.client.protocol.a;
import org.apache.http.conn.r;
import org.apache.http.i;
import org.apache.http.protocol.f;

/* compiled from: DefaultUserTokenHandler */
public class v implements n {
    public static final v a = new v();

    public Object a(f context) {
        SSLSession sslsession;
        a clientContext = a.g(context);
        Principal userPrincipal = null;
        h targetAuthState = clientContext.t();
        if (targetAuthState != null && (userPrincipal = b(targetAuthState)) == null) {
            userPrincipal = b(clientContext.q());
        }
        if (userPrincipal != null) {
            return userPrincipal;
        }
        i conn = clientContext.c();
        if (!conn.isOpen() || !(conn instanceof r) || (sslsession = ((r) conn).S0()) == null) {
            return userPrincipal;
        }
        return sslsession.getLocalPrincipal();
    }

    private static Principal b(h authState) {
        l creds;
        c scheme = authState.b();
        if (scheme == null || !scheme.isComplete() || !scheme.isConnectionBased() || (creds = authState.c()) == null) {
            return null;
        }
        return creds.getUserPrincipal();
    }
}
