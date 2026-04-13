package org.apache.http.impl.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.h;
import org.apache.http.auth.c;
import org.apache.http.client.a;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.t;
import org.apache.http.impl.conn.q;
import org.apache.http.l;

/* compiled from: BasicAuthCache */
public class e implements a {
    private final org.apache.commons.logging.a a;
    private final Map<l, byte[]> b;
    private final t c;

    public e(t schemePortResolver) {
        this.a = h.n(getClass());
        this.b = new ConcurrentHashMap();
        this.c = schemePortResolver != null ? schemePortResolver : q.a;
    }

    public e() {
        this((t) null);
    }

    /* access modifiers changed from: protected */
    public l d(l host) {
        if (host.getPort() > 0) {
            return host;
        }
        try {
            return new l(host.getHostName(), this.c.a(host), host.getSchemeName());
        } catch (UnsupportedSchemeException e) {
            return host;
        }
    }

    public void b(l host, c authScheme) {
        org.apache.http.util.a.i(host, "HTTP host");
        if (authScheme != null) {
            if (authScheme instanceof Serializable) {
                try {
                    ByteArrayOutputStream buf = new ByteArrayOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(buf);
                    out.writeObject(authScheme);
                    out.close();
                    this.b.put(d(host), buf.toByteArray());
                } catch (IOException ex) {
                    if (this.a.isWarnEnabled()) {
                        this.a.warn("Unexpected I/O error while serializing auth scheme", ex);
                    }
                }
            } else if (this.a.isDebugEnabled()) {
                org.apache.commons.logging.a aVar = this.a;
                aVar.debug("Auth scheme " + authScheme.getClass() + " is not serializable");
            }
        }
    }

    public c a(l host) {
        org.apache.http.util.a.i(host, "HTTP host");
        byte[] bytes = this.b.get(d(host));
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            c authScheme = (c) in.readObject();
            in.close();
            return authScheme;
        } catch (IOException ex) {
            if (this.a.isWarnEnabled()) {
                this.a.warn("Unexpected I/O error while de-serializing auth scheme", ex);
            }
            return null;
        } catch (ClassNotFoundException ex2) {
            if (this.a.isWarnEnabled()) {
                this.a.warn("Unexpected error while de-serializing auth scheme", ex2);
            }
            return null;
        }
    }

    public void c(l host) {
        org.apache.http.util.a.i(host, "HTTP host");
        this.b.remove(d(host));
    }

    public String toString() {
        return this.b.toString();
    }
}
