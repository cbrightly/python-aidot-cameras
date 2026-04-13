package org.apache.http.impl.conn;

import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.t;
import org.apache.http.l;
import org.apache.http.util.a;

/* compiled from: DefaultSchemePortResolver */
public class q implements t {
    public static final q a = new q();

    public int a(l host) {
        a.i(host, "HTTP host");
        int port = host.getPort();
        if (port > 0) {
            return port;
        }
        String name = host.getSchemeName();
        if (name.equalsIgnoreCase(l.DEFAULT_SCHEME_NAME)) {
            return 80;
        }
        if (name.equalsIgnoreCase("https")) {
            return 443;
        }
        throw new UnsupportedSchemeException(name + " protocol is not supported");
    }
}
