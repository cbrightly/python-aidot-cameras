package org.apache.http.impl.client;

import org.apache.commons.logging.a;
import org.apache.http.auth.h;
import org.apache.http.client.c;
import org.apache.http.impl.auth.g;
import org.apache.http.l;
import org.apache.http.protocol.f;
import org.apache.http.q;

@Deprecated
/* compiled from: HttpAuthenticator */
public class x extends g {
    public x(a log) {
        super(log);
    }

    public boolean f(l host, q response, c authStrategy, h authState, f context) {
        return d(host, response, authStrategy, authState, context);
    }
}
